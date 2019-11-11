// package com.partygames.partygamesservice.config;

// import com.partygames.partygamesservice.filter.AuthenticationFilter;

// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public FilterRegistrationBean<AuthenticationFilter> authFilter() {
//         FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//         registrationBean.setFilter(new AuthenticationFilter());
//         registrationBean.addUrlPatterns("/api/*");
//         return registrationBean;
//     }
// }