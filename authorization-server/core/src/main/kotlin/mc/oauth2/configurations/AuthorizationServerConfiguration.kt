package mc.oauth2.configurations

import mc.oauth2.annotations.EnableAuthorizationServerCustomized
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

/**
 * @author Michael Chalabine
 */
@Configuration
@EnableAuthorizationServerCustomized
class AuthorizationServerConfiguration : AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        super.configure(security)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        super.configure(clients)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        super.configure(endpoints)
    }

}