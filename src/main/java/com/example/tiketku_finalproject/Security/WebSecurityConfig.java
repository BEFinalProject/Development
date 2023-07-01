package com.example.tiketku_finalproject.Security;

import com.example.tiketku_finalproject.Filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers("/Airplane/**", "/Airport/**", "/HistoryTransaction/findAll", "/Routes/findAll", "/Users", "/Users/findUser/{id_user}", "/HistoryTransaction/findAll").hasRole("ADMIN  ")
                .requestMatchers("/Users/resetPassword", "/Users/updateUser", "/Users/deleteUser/{id_user}", "/Users/token", "/TempTransaction/unpaidCheckout", "/TempTransaction/paidCheckout", "/TempTransaction/cancelCheckout",
                        "/TempTransaction/refundCheckout", "/HistoryTransaction/user/{uuid_user}",
                        "/HistoryTransaction/date/{departure_date}/{uuid_user}", "/HistoryTransaction/uuid/{uuid_user}/{uuid_history}", "/HistoryTransaction/total/{uuid_user}/{created_at}", "/Ticket/{uuid_history}").hasRole("BUYER  ")
                .requestMatchers("/Users/**", "/HistoryTransaction/**", "/TempTransaction/**", "/Schedules/**", "/City/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().permitAll();

        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
        //                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/Ticket/**","/City/**","/Routes/**","/Schedules/**","/TempTransaction/addTempTransaction","/Users/register","/Users/login","/swagger-ui/**","/v3/api-docs/**").permitAll()
//                .and().authorizeHttpRequests().requestMatchers("/HistoryTransaction/**","/Users/**","/TempTransaction/**","/Airplane/**","/Airport/**").authenticated().and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    //untuk encode password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
