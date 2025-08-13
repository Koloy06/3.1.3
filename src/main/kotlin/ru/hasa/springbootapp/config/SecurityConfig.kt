package ru.hasa.springbootapp.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import ru.hasa.springbootapp.config.handler.LoginSuccessHandler
import ru.hasa.springbootapp.service.UserService

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userService: UserService

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
    }

    @Configuration
    @Order(2)
    class ApiWebSecurityConfigurationAdapter : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http
                .antMatcher("/api/v1/**")
                .authorizeRequests()
                .anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
    }

    override fun configure(http: HttpSecurity) {
        http.formLogin()
            // указываем страницу с формой логина
            .loginPage("/login")
            //указываем логику обработки при логине
            .successHandler(LoginSuccessHandler())
            // указываем action с формы логина
            .loginProcessingUrl("/login")
            // Указываем параметры логина и пароля с формы логина
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            // даем доступ к форме логина всем
            .permitAll()

        http.logout()
            // разрешаем делать логаут всем
            .permitAll()
            // указываем URL логаута
            .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            // указываем URL при удачном логауте
            .logoutSuccessUrl("/login?logout")
            // выключаем кроссдоменную секьюрность (на этапе обучения неважна)
            .and().csrf().disable()

        http
            // делаем страницу регистрации недоступной для авторизированных пользователей
            .authorizeRequests()
            // страница аутентификации доступна всем
            .antMatchers("/login", "/").anonymous()
            .antMatchers("/users/user.html").access("hasAnyRole('USER', 'ADMIN')")
            .antMatchers("/users/**").access("hasAnyRole('ADMIN')")
            .antMatchers("/admin/**").access("hasAnyRole('ADMIN')").anyRequest().authenticated()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }
}
