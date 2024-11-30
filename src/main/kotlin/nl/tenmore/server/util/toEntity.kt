package nl.tenmore.server.util

import nl.tenmore.server.dto.RegisterRequestDto
import nl.tenmore.server.entities.User
import org.springframework.security.crypto.password.PasswordEncoder

fun RegisterRequestDto.toEntity(passwordEncoder: PasswordEncoder?): User {
    val user = User(
        firstName = firstName,
        lastName = lastName,
        username = username,
    )
    user.password = if(passwordEncoder != null) passwordEncoder.encode(password) else password
    return user
}