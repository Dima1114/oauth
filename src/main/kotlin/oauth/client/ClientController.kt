package oauth.client

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientController {

    @GetMapping("/client")
    @PreAuthorize("#oauth2.hasScope('oauth_admin')")
    fun getClient(): String = "test client"
}
