package nextstep.subway.auth.infrastructure

import nextstep.subway.auth.application.AuthService
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import nextstep.subway.auth.ui.AuthenticationPrincipalArgumentResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@Configuration
open class AuthenticationPrincipalConfig(private val authService: AuthService) : WebMvcConfigurer {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver())
    }

    @Bean
    open fun createAuthenticationPrincipalArgumentResolver(): AuthenticationPrincipalArgumentResolver {
        return AuthenticationPrincipalArgumentResolver(authService)
    }
}
