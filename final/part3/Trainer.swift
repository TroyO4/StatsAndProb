class Trainer: Card {
    var action: String

    init(name: String, action: String) {
        self.action = action
        super.init(name: name)
    }
}
