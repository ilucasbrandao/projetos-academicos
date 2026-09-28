package br.com.devLucas.api_despesas.controle_de_despesas.repository;

import br.com.devLucas.api_despesas.controle_de_despesas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
