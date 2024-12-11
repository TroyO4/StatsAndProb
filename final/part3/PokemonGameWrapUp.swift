import Foundation

class PokemonGameWrapUp: ObservableObject {
    @Published var deck: [Card] = []
    @Published var hand: [Card] = []
    @Published var opponentHand: [Card] = []
    @Published var prizes: [Card] = []
    @Published var activePokemon: Pokemon?
    @Published var opponentActivePokemon: Pokemon?
    @Published var playerPrizesTaken = 0
    @Published var opponentPrizesTaken = 0
    @Published var opponentHasBenchPokemon = true
    @Published var isPlayerTurn = true
    @Published var gameOverMessage: String?
    @Published var lastAttackMessage: String = ""

    private var opponentActiveHealth = 0
    private var energyAttachedThisTurn = false
    private var isFirstTurn = true
    private var trainerCardActions: [String: () -> Void] = [:]

    init() {
        setupTrainerCardActions()
        setupGame()
    }

    // MARK: - Setup Methods

    private func setupTrainerCardActions() {
        trainerCardActions["Draw 2 Cards"] = { self.drawTwoCards() }
        trainerCardActions["Heal 20 Health"] = { self.healPokemon(self.activePokemon, amount: 20) }
        trainerCardActions["Switch Pokemon"] = { self.switchActivePokemon() }
    }

    func fillDeck() {
        deck.removeAll()

        for _ in 0..<6 {
            deck.append(Charmander())
            deck.append(Pikachu())
        }

        for _ in 0..<5 {
            deck.append(Bulbasaur())
        }

        for _ in 0..<5 {
            deck.append(FireEnergy())
            deck.append(WaterEnergy())
            deck.append(GrassEnergy())
            deck.append(ElectricEnergy())
            deck.append(PsychicEnergy())
        }

        for _ in 0..<6 {
            deck.append(Trainer(name: "Draw 2 Cards", action: "drawTwoCards"))
            deck.append(Trainer(name: "Heal 20 Health", action: "healPokemon"))
            deck.append(Trainer(name: "Switch Pokemon", action: "switchPokemon"))
        }

        deck.shuffle()
    }

    func setupGame() {
        fillDeck()
        drawHand()

        activePokemon = hand.first { $0 is Pokemon } as? Pokemon
        opponentActivePokemon = opponentHand.first { $0 is Pokemon } as? Pokemon

        activePokemon?.isActive = true
        opponentActivePokemon?.isActive = true
        opponentActiveHealth = opponentActivePokemon?.health ?? 0

        for _ in 0..<6 {
            prizes.append(deck.removeFirst())
        }
    }

    private func drawHand() {
        hand.removeAll()
        opponentHand.removeAll()

        guard drawCards(count: 7, destination: &hand),
              drawCards(count: 7, destination: &opponentHand) else {
            print("Failed to draw hands. Game over.")
            exit(0)
        }

        if !hasActivePokemon(hand) {
            print("You have no Pokémon in your hand. Please start over.")
            exit(0)
        }

        if !hasActivePokemon(opponentHand) {
            print("Opponent has no Pokémon in their hand. Please start over.")
            exit(0)
        }
    }

    func drawCards(count: Int, destination: inout [Card]) -> Bool {
        for _ in 0..<count {
            guard !deck.isEmpty else {
                let message = (destination as AnyObject === hand as AnyObject)
                    ? "Your deck is empty! You lose!"
                    : "Opponent's deck is empty! You win!"
                print(message)
                return false
            }
            destination.append(deck.removeFirst())
        }
        return true
    }


    private func hasActivePokemon(_ hand: [Card]) -> Bool {
        hand.contains { $0 is Pokemon }
    }

    // MARK: - Player Actions

    func drawCard() {
        if drawCards(count: 1, destination: &hand) {
            print("You drew a card. Hand: \(hand.map { $0.name })")
        }
    }

    func drawTwoCards() {
        if drawCards(count: 2, destination: &hand) {
            print("You drew 2 cards. Hand: \(hand.map { $0.name })")
        }
    }

    func healPokemon(_ target: Pokemon?, amount: Int) {
        guard let target = target else {
            print("No active Pokémon to heal.")
            return
        }
        target.heal(amount)
        print("Healed \(target.name) by \(amount). Current health: \(target.health).")
    }

    func attachEnergy(to target: Pokemon?) {
        guard let target = target else {
            print("No active Pokémon to attach energy.")
            return
        }
        guard let energyCard = hand.first(where: { $0 is Energy }) as? Energy else {
            print("No energy cards in hand.")
            return
        }
        hand.removeAll { $0.name == energyCard.name }
        target.attachEnergy(energyCard)
        print("Attached \(energyCard.name) to \(target.name).")
    }

    func switchActivePokemon() {
        let pokemonInHand = hand.compactMap { $0 as? Pokemon }
        guard !pokemonInHand.isEmpty else {
            print("No Pokémon in hand to switch to.")
            return
        }

        for (index, pokemon) in pokemonInHand.enumerated() {
            print("\(index + 1): \(pokemon.name)")
        }

        if let choice = readLine(), let selectedIndex = Int(choice), selectedIndex > 0, selectedIndex <= pokemonInHand.count {
            let selectedPokemon = pokemonInHand[selectedIndex - 1]
            hand.removeAll { $0.name == selectedPokemon.name }
            if let active = activePokemon {
                hand.append(active)
            }
            activePokemon = selectedPokemon
            print("Switched to \(selectedPokemon.name).")
        } else {
            print("Invalid choice.")
        }
    }

    func attack() {
        guard let attacker = activePokemon, let defender = opponentActivePokemon else {
            lastAttackMessage = "No Pokémon to attack."
            return
        }

        if attacker.hasSufficientEnergy() {
            let damage = attacker.attackDamage
            defender.takeDamage(damage)
            lastAttackMessage = "\(attacker.name) dealt \(damage) damage to \(defender.name). Remaining health: \(defender.health)"

            if defender.health <= 0 {
                lastAttackMessage += "\n\(defender.name) has been knocked out!"
                claimPrize()
                replaceOpponentActivePokemon()
            }
        } else {
            lastAttackMessage = "\(attacker.name) does not have enough energy to attack."
        }
    }





    func useTrainerCard() {
        let trainersInHand = hand.compactMap { $0 as? Trainer }
        guard !trainersInHand.isEmpty else {
            print("No trainer cards in hand.")
            return
        }

        for (index, trainer) in trainersInHand.enumerated() {
            print("\(index + 1): \(trainer.name)")
        }

        if let choice = readLine(), let selectedIndex = Int(choice), selectedIndex > 0, selectedIndex <= trainersInHand.count {
            let selectedTrainer = trainersInHand[selectedIndex - 1]
            hand.removeAll { $0.name == selectedTrainer.name }
            trainerCardActions[selectedTrainer.name]?()
        } else {
            print("Invalid choice.")
        }
    }

    // MARK: - Opponent Actions

    func opponentAttachEnergy() {
        guard let target = opponentActivePokemon else {
            print("Opponent has no active Pokémon to attach energy.")
            return
        }

        if let energyCard = opponentHand.first(where: { $0 is Energy }) as? Energy {
            opponentHand.removeAll { $0.name == energyCard.name }
            target.attachEnergy(energyCard)
            print("Opponent attached \(energyCard.name) to \(target.name).")
        } else {
            print("Opponent has no energy cards.")
        }
    }

    func performOpponentTurn() {
        print("\n--- Opponent's Turn ---")
        if drawCards(count: 1, destination: &opponentHand) {
            print("Opponent drew a card.")
        }
        opponentAttachEnergy()

        if let opponentPokemon = opponentActivePokemon, opponentPokemon.hasSufficientEnergy() {
            attackOpponent()
        } else {
            print("Opponent cannot attack.")
        }
    }

    private func attackOpponent() {
        guard let attacker = opponentActivePokemon, let defender = activePokemon else { return }
        let damage = attacker.attackDamage
        defender.takeDamage(damage)
        print("Opponent's \(attacker.name) attacked! \(defender.name) has \(defender.health) health remaining.")

        if defender.health <= 0 {
            print("\(defender.name) has been knocked out!")
            claimOpponentPrize()
            replaceActivePokemon()
        }
    }

    func claimPrize() {
        guard !prizes.isEmpty else { return }
        let prize = prizes.removeFirst()
        hand.append(prize)
        playerPrizesTaken += 1
        print("Claimed prize card: \(prize.name).")
    }

    func claimOpponentPrize() {
        opponentPrizesTaken += 1
        print("Opponent claimed a prize card.")
    }

    func replaceOpponentActivePokemon() {
        guard let knockedOutPokemon = opponentActivePokemon else { return }

        // Reset the knocked-out Pokémon's energy
        knockedOutPokemon.clearEnergy()

        // Add the knocked-out Pokémon back to the opponent's hand
        opponentHand.append(knockedOutPokemon)

        // Find a new active Pokémon
        if let newActivePokemon = opponentHand.first(where: { $0 is Pokemon }) as? Pokemon {
            opponentActivePokemon = newActivePokemon
            opponentHand.removeAll { $0.name == newActivePokemon.name }
            print("Opponent's new active Pokémon is \(newActivePokemon.name).")
        } else {
            // If no Pokémon are available, end the opponent's ability to fight
            opponentActivePokemon = nil
            opponentHasBenchPokemon = false
            print("Opponent has no Pokémon left to play!")
        }
    }


    private func replaceActivePokemon() {
        activePokemon = hand.compactMap { $0 as? Pokemon }.first
        if let newActive = activePokemon {
            hand.removeAll { $0.name == newActive.name }
            print("Switched to \(newActive.name).")
        } else {
            print("No Pokémon left to play!")
        }
    }

    // MARK: - Game State

    func checkGameState() -> Bool {
        if playerPrizesTaken == 6 {
            print("You win! All prizes claimed.")
            return true
        }

        if opponentPrizesTaken == 6 || !opponentHasBenchPokemon {
            print("You lose! Opponent won.")
            return true
        }

        return false
    }

    func startGame() {
        setupGame()

        while true {
            performPlayerTurn()
            if checkGameState() { break }
            performOpponentTurn()
            if checkGameState() { break }
        }
    }

    func performPlayerTurn() {
        print("\n--- Your Turn ---")

        if !isFirstTurn {
            drawCard()
        }

        isFirstTurn = false
        energyAttachedThisTurn = false

        if let active = activePokemon {
            print("Your active Pokémon is \(active.name).")
            print("Energy attached: \(active.totalEnergy)")
            print("Health: \(active.health)")
        } else {
            print("You have no active Pokémon.")
            return
        }

        while true {
            print("\nChoose an action:")
            print("(1) Attach Energy")
            print("(2) Attack")
            print("(3) Use Trainer Card")
            print("(4) End Turn")
            print("Your hand: \(hand.map { $0.name })")

            if let choice = Int(readLine() ?? "0") {
                switch choice {
                case 1:
                    if !energyAttachedThisTurn {
                        attachEnergy(to: activePokemon)
                        energyAttachedThisTurn = true
                    } else {
                        print("You can only attach one Energy card per turn.")
                    }
                case 2:
                    attack()
                    return
                case 3:
                    useTrainerCard()
                    return
                case 4:
                    print("Ending your turn.")
                    isPlayerTurn.toggle()
                    return
                default:
                    print("Invalid choice. Please choose a valid action.")
                }
            } else {
                print("Invalid input. Please enter a number.")
            }
        }
    }
}
