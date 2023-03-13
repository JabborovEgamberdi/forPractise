//package com.example.forphonenumberauthenticationusingkeycloak.config;
//
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
//import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
//import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//
//@Configuration
//public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
//
////    @Autowired
////    private KeycloakClientRequestFactory keycloakClientRequestFactory;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }
//
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    @Bean
//    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    @Bean
//    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
//            KeycloakAuthenticationProcessingFilter filter) {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
//        registrationBean.setEnabled(false);
//        return registrationBean;
//    }
//
////    @Bean
////    public KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
////        return new KeycloakAuthenticationProcessingFilter(
////                authenticationManagerBean(), new SimpleUrlAuthenticationFailureHandler(),
////                new SMSCodeAuthenticator(keycloakSession()));
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/protected/**").hasRole("user")
//                .anyRequest().permitAll();
//    }
//
////    @Override
////    protected void configure(KeycloakSecurityContextRequestFilter keycloakSecurityContextRequestFilter) {
////        super.configure(keycloakSecurityContextRequestFilter);
////        keycloakSecurityContextRequestFilter.setCorsAllowedMethods("GET,POST");
////    }
////
////    @Override
////    protected void configure(KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter) {
////        super.configure(keycloakAuthenticationProcessingFilter);
////        keycloakAuthenticationProcessingFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
////        keycloakAuthenticationProcessingFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
////        keycloakAuthenticationProcessingFilter.setAuthenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
////        keycloakAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
////        keycloakAuthenticationProcessingFilter.setKeycloakClientRequestFactory(keycloakClientRequestFactory);
////        keycloakAuthenticationProcessingFilter.setAuthenticationFlowBindingStrategy(new SMSCodeFlowBindingStrategy());
////    }
//
//}