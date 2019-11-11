package oauth.client

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.security.access.prepost.PreAuthorize

@RepositoryRestResource
interface ClientDetailsRepository : JpaRepository<ClientDetails, String> {

    @RestResource
    @PreAuthorize("#oauth2.hasScope('oauth_admin')")
    override fun findAll(pageable: Pageable): Page<ClientDetails>

    @PreAuthorize("#oauth2.hasScope('oauth_admin')")
    @RestResource
    override fun <S : ClientDetails> save(s: S): S

    @PreAuthorize("#oauth2.hasScope('oauth_admin')")
    @RestResource
    override fun delete(clientDetails: ClientDetails)
}
