package com.example.demo.security.config;

import com.example.demo.security.exception.handler.authentication.MyAuthenticationEntryPoint;
import com.example.demo.security.exception.handler.authentication.MyAuthenticationFailureHandler;
import com.example.demo.security.exception.handler.authorization.MyAccessDeniedHandler;
import com.example.demo.security.filter.MyPreAuthenticatedProcessingFilter;
import com.example.demo.security.service.MyAuthenticationUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@EnableWebSecurity
class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(getMyPreAuthenticatedProcessingFilter());
        http.authorizeRequests()
                .antMatchers("/world").hasAuthority("WORLD")
                .anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getPreAuthenticatedAuthenticationProvider());
    }

    public AbstractPreAuthenticatedProcessingFilter getMyPreAuthenticatedProcessingFilter() throws Exception {
        var myPreAuthenticatedProcessingFilter = new MyPreAuthenticatedProcessingFilter();
        myPreAuthenticatedProcessingFilter.setAuthenticationManager(authenticationManager());
        // myPreAuthenticatedProcessingFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        myPreAuthenticatedProcessingFilter.setCheckForPrincipalChanges(true);
        myPreAuthenticatedProcessingFilter.setInvalidateSessionOnPrincipalChange(true);
        return myPreAuthenticatedProcessingFilter;
    }

    public PreAuthenticatedAuthenticationProvider getPreAuthenticatedAuthenticationProvider() {
        var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new MyAuthenticationUserDetailsService());
        return preAuthenticatedAuthenticationProvider;
    }
}
