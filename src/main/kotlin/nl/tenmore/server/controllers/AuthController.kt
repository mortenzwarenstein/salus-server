package nl.tenmore.server.controllers


import nl.tenmore.server.dto.AuthRequestDto
import nl.tenmore.server.dto.AuthResponseDto
import nl.tenmore.server.dto.RegisterRequestDto
import nl.tenmore.server.entities.User
import nl.tenmore.server.services.AuthService
import nl.tenmore.server.services.JwtService
import nl.tenmore.server.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    @Autowired private val authService: AuthService,
    @Autowired private val authenticationManager: AuthenticationManager,
) {

    @PostMapping("/create")
    fun createUser(@RequestBody req: RegisterRequestDto): ResponseEntity<User> {
        val user = authService.createUser(req)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequestDto): ResponseEntity<out AuthResponseDto> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                req.username,
                req.password
            )
        )

        val token = authService.generateTokenForUser(req.username)
        val res = if (token != null) {
            ResponseEntity.ok(AuthResponseDto(token))
        } else {
            ResponseEntity.status(
                HttpStatus.UNAUTHORIZED
            ).body(null)
        }
        return res
    }
}