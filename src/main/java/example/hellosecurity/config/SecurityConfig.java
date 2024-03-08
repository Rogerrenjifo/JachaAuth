package example.hellosecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebMvcSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // talvez nos sirva en lugar de encriptar la informacion
                .csrf(csrf -> csrf.disable())
                // configurar autorizaciones a request.
                .authorizeHttpRequests(requestAuthorized -> {
                    requestAuthorized.requestMatchers("/endpoint/notsecure")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                // que pasa antes y despues de logearse
                .formLogin(formLogin -> {
                    formLogin
                            .successHandler(
                                    (request, response, authentication) -> response.sendRedirect("/endpoint/secure"))
                            .permitAll();
                })
                .sessionManagement(session -> {
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .invalidSessionUrl("/login")
                            .maximumSessions(1)
                            .expiredUrl("/login")
                            .sessionRegistry(sessionRegistry());
                    session
                            .sessionFixation(custom -> custom.migrateSession());
                            // .migrateSession();
                })
                .build();
    }

    @Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
}
