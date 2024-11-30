package nl.tenmore.server.services

import nl.tenmore.server.dto.RegisterRequestDto
import nl.tenmore.server.entities.User
import nl.tenmore.server.repositories.UserRepository
import nl.tenmore.server.util.toEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val jwtService: JwtService
) {

    fun createUser(createUserReq: RegisterRequestDto): User {
        val user = createUserReq.toEntity(passwordEncoder)
        val savedUser = userRepository.save(user)

        return savedUser
    }

    fun generateTokenForUser(username: String): String? {
        val user = userRepository.findByUsername(username)
            .orElseThrow {
                UsernameNotFoundException("Username not found")
            }
        return jwtService.generateToken(user, )
    }
}