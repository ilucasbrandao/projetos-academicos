package br.com.devLucas.api_despesas.controle_de_despesas.infra.security;

import br.com.devLucas.api_despesas.controle_de_despesas.model.UserModel;
import br.com.devLucas.api_despesas.controle_de_despesas.repository.UserRepository;
import br.com.devLucas.api_despesas.controle_de_despesas.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    TokenService tokenService;
    UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recorverToken(request);

        var login = tokenService.validadeToken(token);

        if(login != null) {
            UserModel user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recorverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
