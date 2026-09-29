package br.com.devLucas.api_despesas.controle_de_despesas.controller;

import br.com.devLucas.api_despesas.controle_de_despesas.dto.LoginRequestDTO;
import br.com.devLucas.api_despesas.controle_de_despesas.dto.LoginResponseDTO;
import br.com.devLucas.api_despesas.controle_de_despesas.dto.RegisterRequestDTO;
import br.com.devLucas.api_despesas.controle_de_despesas.model.UserModel;
import br.com.devLucas.api_despesas.controle_de_despesas.repository.UserRepository;
import br.com.devLucas.api_despesas.controle_de_despesas.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(body.email());

        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserModel user = userModelOptional.get();

        if(passwordEncoder.matches(body.password(),user.getPassword())){
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(body.email());

        if(userModelOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        UserModel newUser = new UserModel();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        String token = tokenService.generateToken(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDTO(newUser.getName(), token));
    }
}
