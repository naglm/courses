package com.example.courses.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
/*import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;*/


//@EnableWebSecurity
public class SecurityConfiguration {

    /*@Bean
    @Profile({"dev", "test"})
    WebSecurityConfigurerAdapter noAuth() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeRequests().anyRequest().permitAll();
                httpSecurity.csrf().disable();
                httpSecurity.headers().frameOptions().disable();
            }
        };
    }

    @Bean
    @Profile("default")
    WebSecurityConfigurerAdapter basic() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                        .authorizeRequests()
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .httpBasic();
                httpSecurity.csrf().disable();
                httpSecurity.headers().frameOptions().disable();
            }

            @Override
            public void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication()
                        .withUser("admin").password("admin").roles("USER");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence != null && charSequence.toString().equals(s);
            }
        };
    }*/
}
