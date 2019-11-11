package oauth.security

import venus.utillibrary.model.base.Role
import oauth.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails : UserDetails {

    var id: Long? = null
    private var username: String? = null
    private var password: String? = null
    private var isEnabled: Boolean = false
    private var authorities: Set<Role> = setOf()
    private var isAccountNonExpired: Boolean = true
    private var isAccountNonLocked: Boolean = true
    private var isCredentialsNonExpired: Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = isEnabled

    override fun getUsername(): String? = username

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired

    override fun getPassword(): String? = password

    override fun isAccountNonExpired(): Boolean = isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = isAccountNonLocked

    companion object {

        fun create(user: User): JwtUserDetails {
            return JwtUserDetails().apply {
                id = user.id
                authorities = user.roles
                isEnabled = user.isEnabled
                username = user.username
                password = user.password
            }
        }
    }
}
