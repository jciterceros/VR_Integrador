package com.jciterceros.vendas.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    public void atualizarEstoque(Integer quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    public void aplicarDesconto(BigDecimal desconto) {
        this.preco = this.preco.subtract(desconto);
    }
}