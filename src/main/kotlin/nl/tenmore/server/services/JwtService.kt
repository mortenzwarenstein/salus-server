package nl.tenmore.server.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import nl.tenmore.server.entities.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Service
class JwtService {

    private val SECRET = "5E4CB69BA9AF33FD6BBEE4F26913F5E4CB69BA9AF33FD6BBEE4F26913F5E4CB69BA9AF33FD6BBEE4F26913F5E4CB69BA9AF33FD6BBEE4F26913F5E4CB69BA9AF33FD6BBEE4F26913F"

    fun generateToken(claims: Map<String, Any>, user: User): String {
        return Jwts
            .builder()
            .claims(claims)
            .subject(user.id)
            .expiration(Date(System.currentTimeMillis() + 1000 * 60))
            .issuedAt(Date(System.currentTimeMillis()))
            .signWith(generateSigningKey())
            .compact()
    }

    fun generateToken(user: User): String {
        val map = mutableMapOf("username" to user.username)
        return generateToken(map, user)
    }

    fun isTokenValid(jwt: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(jwt)

        return username == userDetails.username && !isTokenExpired(jwt)
    }

    fun extractUsername(jwt: String): String? {
        return extractClaim(jwt, Claims::getSubject)
    }

    fun extractExpiration(jwt: String): Date {
        return extractClaim(jwt, Claims::getExpiration)
    }

    fun extractAllClaims(jwt: String): Claims {
        return Jwts.parser()
            .verifyWith(generateSigningKey())
            .build()
            .parseSignedClaims(jwt)
            .payload
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun generateSigningKey(): SecretKeySpec {
        return SecretKeySpec(SECRET.toByteArray(Charsets.UTF_8), "HmacSHA256")
    }

    private fun isTokenExpired(jwt: String): Boolean {
        return extractExpiration(jwt).before(Date())
    }

}
