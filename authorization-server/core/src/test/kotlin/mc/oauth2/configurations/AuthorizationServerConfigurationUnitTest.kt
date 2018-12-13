package mc.oauth2.configurations

import io.mockk.every
import io.mockk.mockk
import mc.oauth2.MSG_AUTHENTICATION_FAILURE
import mc.oauth2.Profiles
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_ROLES
import mc.oauth2.config.TEST_USERNAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.context.support.GenericApplicationContext
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerConfiguration::class,
    AuthorizationServerTestConfiguration::class])
internal class AuthorizationServerConfigurationUnitTest(
        private val applicationContext: GenericApplicationContext) {

    @Test
    fun `assert context forbids bean override`() {
        val actual = getIsBeanOverrideAllowed()
        assertThat(actual).isFalse()
    }

    @Test
    fun `assert can load context`() {
        assertThat(applicationContext).isNotNull
    }

    private fun getIsBeanOverrideAllowed(): Boolean {
        val beanFactory = applicationContext.defaultListableBeanFactory
        return beanFactory.isAllowBeanDefinitionOverriding
    }
}

@TestConfiguration
@Profile(Profiles.TEST)
@EnableWebSecurity
class AuthorizationServerTestConfiguration {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        val provider = mockk<AuthenticationProvider>()
        return stageAuthenticationProvider(provider)
    }

    @Bean
    fun delegatingClientDetailsService(): ClientDetailsService {
        return mockk()
    }

    private fun stageAuthenticationProvider(
            provider: AuthenticationProvider): AuthenticationProvider {
        stageAuthenticate(provider)
        stageSupports(provider)
        return provider
    }

    private fun stageAuthenticate(provider: AuthenticationProvider) {
        stageAuthenticateFailure(provider)
        stageAuthenticateSuccess(provider)
    }

    private fun stageAuthenticateSuccess(provider: AuthenticationProvider) {
        every { provider.authenticate(getValidAuthentication()) }
                .returns(getValidAuthenticationToken())
    }

    private fun stageAuthenticateFailure(provider: AuthenticationProvider) {
        every { provider.authenticate(any()) }
                .throws(AuthenticationServiceException(MSG_AUTHENTICATION_FAILURE))
    }

    private fun getValidAuthentication(): Authentication {
        val token = UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
        token.details = WebAuthenticationDetails(MockHttpServletRequest())
        return token
    }

    private fun getValidAuthenticationToken(): Authentication =
            UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_ROLES)

    private fun stageSupports(provider: AuthenticationProvider) {
        every { provider.supports(any()) }.returns(true)
    }
}