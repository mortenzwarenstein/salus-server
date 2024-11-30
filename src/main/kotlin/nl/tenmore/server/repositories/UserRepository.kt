package nl.tenmore.server.repositories

import nl.tenmore.server.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByUsername(username: String): Optional<User>
}