import Foundation

class Pokemon: Card {
    var health: Int
    var attackDamage: Int
    var energyRequired: Int
    var isActive: Bool = false
    private var attachedEnergy: [Energy]
    private var currentEnergy: Int

    init(name: String, health: Int, attackDamage: Int, energyRequired: Int) {
        self.health = health
        self.attackDamage = attackDamage
        self.energyRequired = energyRequired
        self.isActive = false
        self.attachedEnergy = []
        self.currentEnergy = 0
        super.init(name: name)
    }

    // Computed property for health
    var healthStatus: Int {
        return health
    }

    // Computed property for total energy
    var totalEnergy: Int {
        return attachedEnergy.count
    }

    func heal(_ amount: Int) {
        health += amount
        print("\(name) healed by \(amount). Current health: \(health)")
    }

    func takeDamage(_ damage: Int) {
        health -= damage
        if health < 0 {
            health = 0
        }
        print("\(name) took \(damage) damage. Remaining health: \(health)")
    }

    func attachEnergy(_ energy: Energy) {
        attachedEnergy.append(energy)
        print("Attached \(energy.name) to \(name). Total energy: \(totalEnergy)")
    }

    func hasSufficientEnergy() -> Bool {
        return attachedEnergy.count >= energyRequired
    }

    func clearEnergy() {
        attachedEnergy.removeAll()
        print("\(name)'s energy has been cleared.")
    }

}
