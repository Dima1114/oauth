package oauth.config

import oauth.security.JwtTokenEnhancer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

import javax.sql.DataSource
import java.util.Arrays
import javax.crypto.Cipher.PRIVATE_KEY

@Configuration
@EnableAuthorizationServer
class JwtAuthorizationServerConfig(private val authenticationManager: AuthenticationManager,
                                   @Qualifier("clientDataSource") private val clientDataSource: DataSource) : AuthorizationServerConfigurerAdapter() {

    @Bean
    fun tokenConverter(): JwtAccessTokenConverter =
            JwtAccessTokenConverter().apply {
                setSigningKey(PRIVATE_KEY)
            }

    @Bean
    fun tokenEnhancer(): TokenEnhancer = JwtTokenEnhancer()

    @Bean
    fun tokenStore(): JwtTokenStore = JwtTokenStore(tokenConverter())

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices =
            DefaultTokenServices().apply {
                setTokenStore(tokenStore())
                setSupportRefreshToken(true)
            }

    @Bean
    fun tokenEnhancerChain(): TokenEnhancerChain =
            TokenEnhancerChain().apply {
                setTokenEnhancers(listOf(tokenEnhancer(), tokenConverter()))
            }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain())
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .jdbc(clientDataSource)
                .passwordEncoder(BCryptPasswordEncoder())
    }

    companion object {
        private const val PRIVATE_KEY = "auth_private_key"
    }
}
