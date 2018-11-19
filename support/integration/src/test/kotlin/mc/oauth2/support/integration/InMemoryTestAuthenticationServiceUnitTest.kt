package mc.oauth2.support.integration

import mc.oauth2.config.AuthenticationResult
import mc.oauth2.config.AuthenticationResult.AUTHENTICATED
import mc.oauth2.config.Credentials
import mc.oauth2.config.Principal
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val USERNAME = "my-principal"
private const val PASSWORD = "my-secret"

/**
 * @author Michael Chalabine
 */
@RunWith(JUnit4::class)
class InMemoryTestAuthenticationServiceUnitTest {

    private lateinit var authenticationService: InMemoryAuthenticationService

    private val principal: Principal = Principal()

    private val credentials: Credentials = Credentials()

    @Before
    fun setUp() {
        authenticationService = InMemoryAuthenticationService()
    }

    @Test
    fun testInstantiate() {
        assertNotNull(authenticationService)
    }

    @Test
    fun testAuthenticatesWhereUsernameAndCredentialsMatches() {
        val actual: AuthenticationResult =
                authenticationService.authenticate(principal, credentials)
        assertThat(actual, `is`(AUTHENTICATED))
    }
}
