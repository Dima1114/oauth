package oauth.client

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_client_details")
class ClientDetails {

    @Id
    @Column(name = "client_id")
    private var clientId: String? = null

    @Column(name = "resource_ids")
    private var resourceIds: String? = null

    @Column(name = "client_secret")
    private var clientSecret: String? = null

    @Column(name = "scope")
    private var scope: String? = null

    @Column(name = "authorized_grant_types")
    private var authorizedGrantTypes: String? = null

    @Column(name = "web_server_redirect_uri")
    private var redirectUti: String? = null

    @Column(name = "authorities")
    private var authorities: String? = null

    @Column(name = "access_token_validity")
    private var accessTokenValidity: Int? = null

    @Column(name = "refresh_token_validity")
    private var refreshTokenValidity: Int? = null

    @Column(name = "additional_information")
    private var additionalInformation: String? = null

    @Column(name = "autoapprove")
    private var autoApprove: String? = null

    companion object {

        private val encoder = BCryptPasswordEncoder()
    }
}
