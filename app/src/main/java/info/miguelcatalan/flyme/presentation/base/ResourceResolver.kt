package info.miguelcatalan.flyme.presentation.base

import android.content.Context

class ResourceResolver(
    private val context: Context
) {
    fun getStringResource(stringId: Int): String {
        return context.resources.getString(stringId)
    }

    fun getStringResource(stringId: Int, variable: String): String {
        return context.resources.getString(stringId, variable)
    }

    fun getQuantityStringResource(stringId: Int, quantity: Int, variable: String): String {
        return context.resources.getQuantityString(stringId, quantity, variable)
    }
}