package com.example.projava.service;

import com.example.projava.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class FilterToken extends OncePerRequestFilter {


    @Autowired
    private  TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token;
        var authorizationHeader = request.getHeader( "Authorization");

        try {
            if(authorizationHeader != null){
                token = authorizationHeader.replace("Bearer ", "");
                System.out.println("Token recebido ->" + token);
                var subject = this.tokenService.getSubject(token);
                var usuario = this.userRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,
                        null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            LOGGER.error(" OCORREU UM ERRO INESPERADO  E:{}",e.getLocalizedMessage());
        }

        }

}
