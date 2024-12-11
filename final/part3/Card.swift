import Foundation

class Card {
    var name: String

    init(name: String) {
        self.name = name
    }

    func getName() -> String {
        return name
    }

    func description() -> String {
        return name
    }
}
