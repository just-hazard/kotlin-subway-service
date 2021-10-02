package nextstep.subway

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.beans.factory.annotation.Autowired
import nextstep.subway.utils.DatabaseCleanup
import org.junit.jupiter.api.BeforeEach
import io.restassured.RestAssured

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {
    @LocalServerPort
    var port = 0

    @Autowired
    private val databaseCleanup: DatabaseCleanup? = null
    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        databaseCleanup!!.execute()
    }
}
