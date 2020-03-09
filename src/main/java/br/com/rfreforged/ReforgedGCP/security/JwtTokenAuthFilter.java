package br.com.rfreforged.ReforgedGCP.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthFilter extends OncePerRequestFilter {

    private static final Logger authFilterLogger = LoggerFactory.getLogger(JwtTokenAuthFilter.class);

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public JwtTokenAuthFilter(UserDetailsServiceImpl userDetailsService, JwtTokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!ignoreToken(httpServletRequest.getRequestURI())) {
            try {

                Cookie sessionid = WebUtils.getCookie(httpServletRequest, "SID");

                if (!httpServletRequest.getRequestURI().contains("/auth")) {

                    String jwt = sessionid == null ? "" : sessionid.getValue();

                    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt, httpServletRequest, httpServletResponse)) {

                        String emailFromJWT = tokenProvider.getUsernameFromToken(jwt);

                        UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromJWT);

                        UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception ex) {
                authFilterLogger.error("Não foi possível inserir o usuário no contexto de segurança", ex);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }


    private Boolean ignoreToken(String url) {
        return url.contains("/auth");
    }
}
