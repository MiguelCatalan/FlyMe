package info.miguelcatalan.flyme.notifier

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import info.miguelcatalan.flyme.domain.notifier.MessageDuration
import info.miguelcatalan.flyme.domain.notifier.Notifier

class ToastNotifier(
    private val activity: AppCompatActivity
) : Notifier {

    override fun show(message: String, messageDuration: MessageDuration) {
        Toast.makeText(activity, message, messageDuration.toToastDuration()).show()
    }
}

fun MessageDuration.toToastDuration(): Int {
    return when (this) {
        MessageDuration.Short -> Toast.LENGTH_SHORT
        MessageDuration.Long -> Toast.LENGTH_LONG
        MessageDuration.Infinite -> Toast.LENGTH_LONG
    }
}