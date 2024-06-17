package com.jciterceros.vendas.model;

import com.jciterceros.vendas.model.enums.MetodoPagamento;
import com.jciterceros.vendas.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private MetodoPagamento metodoPagamento;


    public void processarPagamento(MetodoPagamento metodo, StatusPagamento statusAtual) {
        StatusPagamento novoStatus = statusAtual;

        switch (metodo) {
            case CARTAO_CREDITO:
                novoStatus = processarCartaoCredito(statusAtual);
                break;
            case CARTAO_DEBITO:
                novoStatus = processarCartaoDebito(statusAtual);
                break;
            case PIX:
                novoStatus = processarPix(statusAtual);
                break;
            case BOLETO_BANCARIO:
                novoStatus = processarBoleto(statusAtual);
                break;
            case DINHEIRO:
                novoStatus = processarDinheiro(statusAtual);
                break;
            default:
                throw new IllegalArgumentException("Método de pagamento desconhecido: " + metodo);
        }

        System.out.println("Status do pagamento atualizado para: " + novoStatus);
        statusPagamento = novoStatus;
    }

    private StatusPagamento processarCartaoCredito(StatusPagamento statusAtual) {
        switch (statusAtual) {
            case INICIADO:
                return StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO:
                return StatusPagamento.REVISAO;
            case REVISAO:
                return StatusPagamento.PAGO;
            case PAGO:
                return StatusPagamento.DISPONIVEL;
            default:
                return statusAtual;
        }
    }

    private StatusPagamento processarCartaoDebito(StatusPagamento statusAtual) {
        switch (statusAtual) {
            case INICIADO:
                return StatusPagamento.PAGO;
            case PAGO:
                return StatusPagamento.DISPONIVEL;
            default:
                return statusAtual;
        }
    }

    private StatusPagamento processarPix(StatusPagamento statusAtual) {
        switch (statusAtual) {
            case INICIADO:
                return StatusPagamento.PAGO;
            case PAGO:
                return StatusPagamento.DISPONIVEL;
            default:
                return statusAtual;
        }
    }

    private StatusPagamento processarBoleto(StatusPagamento statusAtual) {
        switch (statusAtual) {
            case INICIADO:
                return StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO:
                return StatusPagamento.PAGO;
            case PAGO:
                return StatusPagamento.DISPONIVEL;
            default:
                return statusAtual;
        }
    }

    private StatusPagamento processarDinheiro(StatusPagamento statusAtual) {
        switch (statusAtual) {
            case INICIADO:
                return StatusPagamento.PAGO;
            case PAGO:
                return StatusPagamento.DISPONIVEL;
            default:
                return statusAtual;
        }
    }

    public void confirmarPagamento() {
    }
}
