package com.elton.algamoney_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ROLE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/categorias").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter()); // Configura o conversor personalizado
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKeyString = "3032885ba9cd6621bcc4e7d6b6c35c2b3032885ba9cd6621"; // Chave secreta usada para assinar o token
        var secretKey = new SecretKeySpec(secretKeyString.getBytes(), "HmacSHA256"); // Cria uma chave secreta
        return NimbusJwtDecoder.withSecretKey(secretKey).build(); // Cria e retorna o JwtDecoder
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                authorities = Collections.emptyList();
            }

            JwtGrantedAuthoritiesConverter scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

            grantedAuthorities.addAll(authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));

            return grantedAuthorities;
        });

        return jwtAuthenticationConverter;
    }
}