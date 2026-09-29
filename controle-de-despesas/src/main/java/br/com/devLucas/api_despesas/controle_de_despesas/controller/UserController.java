package br.com.devLucas.api_despesas.controle_de_despesas.controller;

import br.com.devLucas.api_despesas.controle_de_despesas.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String welcome(){
        return "Welcome to Lucas!";
    }
}

