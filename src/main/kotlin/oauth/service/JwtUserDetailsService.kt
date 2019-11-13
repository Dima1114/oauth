package oauth.service

import oauth.security.JwtUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import venus.utillibrary.repository.base.UserRepository

@Service("detailsService")
class JwtUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username)
                .orElseThrow { UsernameNotFoundException("User not found with username : $username") }

        return JwtUserDetails.create(user)
    }
}
