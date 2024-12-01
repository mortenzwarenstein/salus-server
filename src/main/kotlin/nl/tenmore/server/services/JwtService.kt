package nl.tenmore.server.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import nl.tenmore.server.entities.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Service
class JwtService(@Value("\${spring.application.key}") private val key: String) {

    fun generateToken(claims: Map<String, Any>, user: User): String {
        return Jwts
            .builder()
            .claims(claims)
            .subject(user.username)
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 2))
            .issuedAt(Date(System.currentTimeMillis()))
            .signWith(generateSigningKey())
            .compact()
    }

    fun generateToken(user: User): String {
        return generateToken(mutableMapOf(),  user)
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
        return SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA256")
    }

    private fun isTokenExpired(jwt: String): Boolean {
        return extractExpiration(jwt).before(Date())
    }

}
