import SwiftUI

struct ContentView: View {
    @StateObject private var game = PokemonGameWrapUp()

    var body: some View {
        ScrollView { // Wrap everything in a ScrollView
            VStack(spacing: 20) {
                // Active Pokémon Display
                VStack(spacing: 10) {
                    Text("Active Pokémon: \(game.activePokemon?.name ?? "None")")
                        .font(.headline)
                    if let activePokemon = game.activePokemon {
                        Text("Health: \(activePokemon.health)")
                        Text("Energy: \(activePokemon.totalEnergy)")
                    }
                }
                .padding()
                .border(Color.blue, width: 2)

                // Opponent Active Pokémon Display
                VStack(spacing: 10) {
                    Text("Opponent Pokémon: \(game.opponentActivePokemon?.name ?? "None")")
                        .font(.headline)
                    if let opponentPokemon = game.opponentActivePokemon {
                        Text("Health: \(opponentPokemon.health)")
                    }
                }
                .padding()
                .border(Color.red, width: 2)

                // Hand Display
                VStack(spacing: 10) {
                    Text("Your Hand")
                        .font(.headline)
                    ScrollView(.horizontal) { // Horizontal scroll for hand display
                        HStack(spacing: 10) {
                            ForEach(game.hand, id: \.name) { card in
                                Text(card.name)
                                    .font(.subheadline)
                                    .padding(5)
                                    .background(Color.gray.opacity(0.2))
                                    .cornerRadius(5)
                            }
                        }
                    }
                    .frame(height: 50)
                }
                .padding()

                // Game Controls (Buttons)
                VStack(spacing: 15) {
                    Button("Attach Energy") {
                        if let activePokemon = game.activePokemon {
                            game.attachEnergy(to: activePokemon)
                        } else {
                            print("No active Pokémon to attach energy.")
                        }
                    }
                    .padding()
                    .background(Color.green.opacity(0.7))
                    .foregroundColor(.white)
                    .cornerRadius(10)

                    Button("Attack") {
                        game.attack()
                    }
                    .padding()
                    .background(Color.red.opacity(0.7))
                    .foregroundColor(.white)
                    .cornerRadius(10)

                    Button("Draw Card") {
                        if !game.drawCards(count: 1, destination: &game.hand) {
                            print("Deck is empty!")
                        }
                    }
                    .padding()
                    .background(Color.blue.opacity(0.7))
                    .foregroundColor(.white)
                    .cornerRadius(10)

                    Button("End Turn") {
                        game.isPlayerTurn.toggle()
                    }
                    .padding()
                    .background(Color.gray.opacity(0.7))
                    .foregroundColor(.white)
                    .cornerRadius(10)
                }

                // Attack Message Display
                Text(game.lastAttackMessage)
                    .padding()
                    .foregroundColor(.blue)
            }
            .padding()
        }
    }
}
