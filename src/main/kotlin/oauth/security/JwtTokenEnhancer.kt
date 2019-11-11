package oauth.security


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class JwtTokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken =
            (accessToken as DefaultOAuth2AccessToken).apply {
                additionalInformation = mapOf("id" to (authentication.principal as JwtUserDetails).id)
            }
}
