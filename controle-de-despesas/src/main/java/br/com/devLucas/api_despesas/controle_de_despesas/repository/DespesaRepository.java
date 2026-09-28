package br.com.devLucas.api_despesas.controle_de_despesas.repository;

import br.com.devLucas.api_despesas.controle_de_despesas.model.DespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {
}
