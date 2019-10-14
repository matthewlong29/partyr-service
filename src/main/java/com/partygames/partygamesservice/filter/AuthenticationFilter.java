package com.partygames.partygamesservice.filter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {
  /**
   * shouldNotFilter.
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    List<String> excludeList = new ArrayList<>();
    excludeList.add("/api/google-authenticate");

    return excludeList.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }

  /**
   * doFilterInternal.
   */
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    PartyLogger.info("filtering");
    Cookie[] cookies = request.getCookies();
    String requestTokenHeader = "";

    try {
      for (Cookie cookie : cookies)
        if (cookie.getName().equalsIgnoreCase("AUTH_ID_TOKEN"))
          requestTokenHeader = cookie.getValue();
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error finding id token");
    }

    NetHttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(
            Collections.singletonList("276174427953-o7q6mv623adttteep82an71rs4bgge0r.apps.googleusercontent.com"))
        .build();

    try {
      GoogleIdToken idToken = verifier.verify(requestTokenHeader);
      if (idToken != null) {
        chain.doFilter(request, response);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
      }
    } catch (GeneralSecurityException | IOException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error authenticating id token");
    }
  }
}