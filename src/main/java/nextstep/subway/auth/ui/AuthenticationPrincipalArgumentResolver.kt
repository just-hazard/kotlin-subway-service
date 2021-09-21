package nextstep.subway.auth.ui

import nextstep.subway.auth.application.AuthService
import nextstep.subway.auth.domain.AuthenticationPrincipal
import nextstep.subway.auth.infrastructure.AuthorizationExtractor.extract
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class AuthenticationPrincipalArgumentResolver(private val authService: AuthService) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory
    ): Any {
        val credentials = extract(webRequest.getNativeRequest(
            HttpServletRequest::class.java))
        return authService.findMemberByToken(credentials)
    }
}
