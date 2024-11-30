package nl.tenmore.server.dto

import com.fasterxml.jackson.annotation.JsonAlias

data class AuthResponseDto(

    @JsonAlias("access_token")
    val accessToken: String
)
