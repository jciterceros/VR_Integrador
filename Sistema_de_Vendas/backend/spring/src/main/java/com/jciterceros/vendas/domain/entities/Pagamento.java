package com.jciterceros.vendas.domain.entities;

import com.jciterceros.vendas.application.abstractions.MetodoPagamentoStrategy;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroPedido;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private LocalDate dataConfirmacaoPagamento;

    private StatusPagamento statusPagamento;
    private MetodoPagamentoStrategy metodoPagamento;

    @Autowired
    public StatusPagamento processarPagamento(MetodoPagamentoStrategy metodo, StatusPagamento statusAtual) {
        validateInputs(metodo, statusAtual);
        statusAtual = metodo.processar(statusAtual);

        if (statusAtual == StatusPagamento.PENDENTE) {
            this.dataPagamento = LocalDate.now();
        }
        if (statusAtual == StatusPagamento.DISPONIVEL) {
            this.dataConfirmacaoPagamento = LocalDate.now();
        }

        return statusAtual;
    }

    private void validateInputs(MetodoPagamentoStrategy metodo, StatusPagamento statusAtual) {
        if (metodo == null) {
            throw new IllegalArgumentException("Método de pagamento não pode ser nulo");
        }
        if (statusAtual == null) {
            throw new IllegalArgumentException("Status de pagamento não pode ser nulo");
        }
        if (statusAtual==StatusPagamento.ERROR){
            throw new IllegalArgumentException("Status de pagamento não pode ser ERROR");
        }
    }
}
