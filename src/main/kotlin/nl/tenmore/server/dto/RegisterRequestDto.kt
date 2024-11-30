package nl.tenmore.server.dto

data class RegisterRequestDto(
    val username: String,
    val password: String,

    val firstName: String?,
    val lastName: String?
)
