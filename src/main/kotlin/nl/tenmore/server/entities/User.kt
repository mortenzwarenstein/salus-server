package nl.tenmore.server.entities

import jakarta.persistence.*
import nl.tenmore.server.enums.Role
import org.hibernate.proxy.HibernateProxy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = "",

    var firstName: String? = "",
    var lastName: String? = "",
    private var username: String = "",
    private var password: String = "",

    @Enumerated(EnumType.STRING)
    private var role: Role = Role.User,

    ) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return username
    }

}
