package com.adobe.prj.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.exception.ExceptionTokenExpired;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AdminService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	try {
	        final String authorizationHeader = request.getHeader("Authorization");
	        String username = null;
	        String jwt = null;
	
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            username = jwtUtil.extractUsername(jwt);
	        }
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            
		            if (jwtUtil.validateToken(jwt, userDetails)) {
		                UsernamePasswordAuthenticationToken token = 
		                		new UsernamePasswordAuthenticationToken(
		                				userDetails, null, userDetails.getAuthorities());
		                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(token);
		            }
	        }
        }
        catch(ExpiredJwtException e) {     	
        	throw new ExceptionTokenExpired(e.getLocalizedMessage());
        	
        }
    	catch(io.jsonwebtoken.SignatureException e) {
    		throw new ExceptionNotFound(e.getLocalizedMessage());
    	}
    	catch(MalformedJwtException e) {
    		throw new ExceptionNotFound(e.getLocalizedMessage());
    	}
    	catch(IllegalArgumentException e) {
    		throw new ExceptionNotFound("JWT claim string is empty");
    	}
        chain.doFilter(request, response);
    }

}
