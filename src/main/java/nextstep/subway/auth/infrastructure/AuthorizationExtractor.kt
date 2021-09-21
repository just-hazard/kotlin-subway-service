package nextstep.subway.auth.infrastructure

import javax.servlet.http.HttpServletRequest
import java.util.Enumeration
import nextstep.subway.auth.infrastructure.AuthorizationExtractor

object AuthorizationExtractor {
    const val AUTHORIZATION = "Authorization"
    var BEARER_TYPE = "Bearer"
    val ACCESS_TOKEN_TYPE = AuthorizationExtractor::class.java.simpleName + ".ACCESS_TOKEN_TYPE"
    @JvmStatic
    fun extract(request: HttpServletRequest): String? {
        val headers = request.getHeaders(AUTHORIZATION)
        while (headers.hasMoreElements()) {
            val value = headers.nextElement()
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                var authHeaderValue = value.substring(BEARER_TYPE.length).trim { it <= ' ' }
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length).trim { it <= ' ' })
                val commaIndex = authHeaderValue.indexOf(',')
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex)
                }
                return authHeaderValue
            }
        }
        return null
    }
}
