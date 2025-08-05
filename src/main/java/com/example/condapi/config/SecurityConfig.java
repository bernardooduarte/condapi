package com.example.condapi.config;

import com.example.condapi.security.JwtAuthFilter;
import com.example.condapi.security.JwtService;
import com.example.condapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/alunos/**")
                .permitAll()
                //.authenticated()
                .antMatchers("/api/v1/areasComuns/**")
                .permitAll()
                .antMatchers("/api/v1/blocos/**")
                .permitAll()
                .antMatchers("/api/v1/condominios/**")
                .permitAll()
                .antMatchers("/api/v1/encomendas/**")
                .permitAll()
                .antMatchers("/api/v1/funcionarios/**")
                .permitAll()
                .antMatchers("/api/v1/moradores/**")
                .permitAll()
                .antMatchers("/api/v1/moradoresUnidades/**")
                .permitAll()
                .antMatchers("/api/v1/obras/**")
                .permitAll()
                .antMatchers("/api/v1/porteiros/**")
                .permitAll()
                .antMatchers("/api/v1/prestadoresServicos/**")
                .permitAll()
                .antMatchers("/api/v1/requisicoesObras/**")
                .permitAll()
                .antMatchers("/api/v1/reservas/**")
                .permitAll()
                .antMatchers("/api/v1/unidades/**")
                .permitAll()
                .antMatchers("/api/v1/veiculos/**")
                .permitAll()
                .antMatchers( "/api/v1/usuarios/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}