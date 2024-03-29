package com.auth0.rainbow.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.auth0.rainbow.security.*;
import com.auth0.rainbow.security.jwt.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import tech.jhipster.config.JHipsterProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JHipsterProperties jHipsterProperties,
        SecurityProblemSupport problemSupport
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf(csrf -> csrf
                        .disable())
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(problemSupport)
                        .accessDeniedHandler(problemSupport))
                .headers(headers -> headers
                        .contentSecurityPolicy(jHipsterProperties.getSecurity().getContentSecurityPolicy())
                        .and()
                        .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        .and()
                        .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
                        .and()
                        .frameOptions().sameOrigin())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers("/app/**/*.{js,html}").permitAll()
                        .antMatchers("/i18n/**").permitAll()
                        .antMatchers("/content/**").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll()
                        .antMatchers("/test/**").permitAll()
                        .antMatchers("/api/authenticate").permitAll()
                        .antMatchers("/api/register").permitAll()
                        .antMatchers("/api/activate").permitAll()
                        .antMatchers("/api/account/reset-password/init").permitAll()
                        .antMatchers("/api/account/reset-password/finish").permitAll()
                        .antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                        .antMatchers("/api/**").authenticated()
                        .antMatchers("/management/health").permitAll()
                        .antMatchers("/management/health/**").permitAll()
                        .antMatchers("/management/info").permitAll()
                        .antMatchers("/management/prometheus").permitAll()
                        .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN))
                .httpBasic(withDefaults())
                .apply(securityConfigurerAdapter());
        return http.build();
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
