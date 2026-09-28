package br.com.devLucas.api_despesas.controle_de_despesas.model;

import br.com.devLucas.api_despesas.controle_de_despesas.enums.CategoriaDespesa;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "despesas")
public class DespesaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    private OffsetDateTime dataDaDespesa;
    private CategoriaDespesa categoria;

    @ManyToOne
    private UserModel user;
}
