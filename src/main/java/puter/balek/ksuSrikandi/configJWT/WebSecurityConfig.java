package puter.balek.ksuSrikandi.configJWT;

//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import puter.balek.ksuSrikandi.configJWT.JwtAuthenticationEntryPoint;
import puter.balek.ksuSrikandi.configJWT.JwtRequestFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(101) // add this line
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http
                .antMatcher("/api/v1/**").cors().and().csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                // .antMatcher("/api/**").cors().and().csrf().disable()
                // dont authenticate this particular request
                .antMatchers(HttpMethod.POST, "/api/v1/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/staff/add").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/manajer/add").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/pengajuan-anggota/add").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/pembayaran/add").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and().
//                .anyRequest().permitAll().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://c01-fe.vercel.app","https://ksu-srikandi.vercel.app"));
            cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
            cors.setAllowedHeaders(Arrays.asList("*"));
            cors.setAllowCredentials(true);
            cors.setMaxAge(3600L);
            return cors;
        });
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // http.cors().configurationSource(request -> {
        //     var cors = new CorsConfiguration();
        //     cors.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //     cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        //     cors.setAllowedHeaders(Arrays.asList("*"));
        //     cors.setAllowCredentials(true);
        //     cors.setMaxAge(3600L);
        //     return cors;
        // });
        //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}