package info.miguelcatalan.flyme.domain.notifier

interface Notifier {
    fun show(message: String, messageDuration: MessageDuration = MessageDuration.Short)
}

sealed class MessageDuration {
    object Short : MessageDuration()
    object Long : MessageDuration()
    object Infinite : MessageDuration()
}