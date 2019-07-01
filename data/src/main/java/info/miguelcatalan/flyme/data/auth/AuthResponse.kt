package info.miguelcatalan.flyme.data.auth

import com.google.gson.annotations.SerializedName
import info.miguelcatalan.flyme.domain.auth.Auth


data class AuthResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expiresIn: Long
) {

    fun toDomain(): Auth =
        Auth(
            accessToken = accessToken,
            expiresIn = expiresIn + System.currentTimeMillis()
        )
}