package br.com.devLucas.api_despesas.controle_de_despesas.service;

import br.com.devLucas.api_despesas.controle_de_despesas.model.UserModel;
import br.com.devLucas.api_despesas.controle_de_despesas.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),new ArrayList<>());
    }
}
