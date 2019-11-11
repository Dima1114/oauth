package oauth.config

import oauth.security.RoleSecured
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.access.annotation.AnnotationMetadataExtractor
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.access.method.MethodSecurityMetadataSource
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class GlobalMethodSecurityConfig : GlobalMethodSecurityConfiguration() {

    override fun createExpressionHandler(): MethodSecurityExpressionHandler =
            OAuth2MethodSecurityExpressionHandler()

    public override fun customMethodSecurityMetadataSource(): MethodSecurityMetadataSource =
            SecuredAnnotationSecurityMetadataSource(RoleSecuredAnnotationMetadataExtractor())

    private inner class RoleSecuredAnnotationMetadataExtractor : AnnotationMetadataExtractor<RoleSecured> {

        override fun extractAttributes(securityAnnotation: RoleSecured): Collection<ConfigAttribute> =
                securityAnnotation.value.asSequence()
                        .map { it.authority }
                        .map { SecurityConfig(it) }
                        .toList()

    }
}