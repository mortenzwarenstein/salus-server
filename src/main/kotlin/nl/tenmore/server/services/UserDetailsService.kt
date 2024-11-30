package nl.tenmore.server.services

import nl.tenmore.server.entities.User
import nl.tenmore.server.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    @Autowired private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): User? {
        return username?.let {
            this.userRepository.findByUsername(it)
        }?.orElseThrow {
            UsernameNotFoundException("Username not found")
        }
    }

}