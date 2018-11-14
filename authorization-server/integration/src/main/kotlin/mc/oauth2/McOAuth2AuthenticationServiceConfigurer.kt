package mc.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Michael Chalabine
 */
@Configuration
class McOAuth2AuthenticationServiceConfigurer {

    @Bean
    fun authenticationService(): AuthenticationService {
        return McOAuth2InMemoryAuthenticationService()
    }

}
