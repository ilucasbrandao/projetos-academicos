package br.com.devLucas.api_despesas.controle_de_despesas.repository;

import br.com.devLucas.api_despesas.controle_de_despesas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
}
