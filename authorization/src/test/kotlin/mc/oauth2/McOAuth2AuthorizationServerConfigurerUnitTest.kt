package mc.oauth2

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McOAuth2AuthorizationServerConfigurer::class))
class McOAuth2AuthorizationServerConfigurerUnitTest {

    @Test
    fun testRunning() {


    }
}