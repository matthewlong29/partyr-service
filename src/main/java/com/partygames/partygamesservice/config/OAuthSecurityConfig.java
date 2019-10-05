package com.partygames.partygamesservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
// import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
// import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.oauth2.client.OAuth2ClientContext;
// import org.springframework.security.oauth2.client.OAuth2RestTemplate;
// import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
// import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

// @Order(99)
// @Configurable
// @EnableWebSecurity
public class OAuthSecurityConfig {

  // @Autowired
  // OAuth2ClientContext oAuth2ClientContext;

  // @Autowired
  // AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

  // @Autowired
  // ResourceServerProperties resourceServerProperties;

  // /**
  // * configure: for overriding the default AuthenticationManagerBuilder. can
  // * specify how the user details are kept in the application. It may be in a
  // * database, LDAP or in memory.
  // */
  // @Override
  // protected void configure(AuthenticationManagerBuilder auth) throws Exception
  // {
  // super.configure(auth);
  // }

  // /**
  // * configure: overriding some configuration of the WebSecurity. ignore some
  // * request or request patterns then you can specify that inside this method.
  // */
  // @Override
  // public void configure(WebSecurity webSecurity) throws Exception {
  // super.configure(webSecurity);
  // }

  // /**
  // * filter: create filter for OAuth authentication.
  // */
  // private OAuth2ClientAuthenticationProcessingFilter filter() {
  // OAuth2ClientAuthenticationProcessingFilter oAuth2Filter = new
  // OAuth2ClientAuthenticationProcessingFilter(
  // "/google/login");

  // OAuth2RestTemplate oAuth2RestTemplate = new
  // OAuth2RestTemplate(authorizationCodeResourceDetails,
  // oAuth2ClientContext);
  // oAuth2Filter.setRestTemplate(oAuth2RestTemplate);
  // oAuth2Filter.setTokenServices(
  // new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(),
  // resourceServerProperties.getClientId()));

  // return oAuth2Filter;
  // }

  // /**
  // * configure: used for override HttpSecurity of the web Application. specify
  // our
  // * authorization criteria inside this method.
  // */
  // @Override
  // protected void configure(HttpSecurity httpSecurity) throws Exception {
  // httpSecurity.authorizeRequests().antMatchers("/", "/**.html",
  // "/**.js").permitAll().anyRequest()
  // .fullyAuthenticated().and().logout().logoutSuccessUrl("/").permitAll().and()
  // .addFilterAt(filter(), BasicAuthenticationFilter.class).csrf()
  // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  // }
}