package info.miguelcatalan.flyme.domain.auth

import info.miguelcatalan.flyme.domain.repository.Identifiable

data class Auth(
    val accessToken: String,
    val expiresIn: Long
) : Identifiable<String> {

    companion object {
        const val KEY = "AuthData"
    }

    override val key: String
        get() = KEY

}