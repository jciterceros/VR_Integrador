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


    public StatusPagamento processarPagamento(MetodoPagamento metodo, StatusPagamento statusAtual) {
        validateInputs(metodo, statusAtual);
        return switch (metodo) {
            case CARTAO_CREDITO -> processarCartaoCredito(statusAtual);
            case CARTAO_DEBITO -> processarCartaoDebito(statusAtual);
            case PIX -> processarPix(statusAtual);
            case BOLETO_BANCARIO -> processarBoleto(statusAtual);
            case DINHEIRO -> processarDinheiro(statusAtual);
            default -> throw new IllegalArgumentException("Método de pagamento desconhecido: " + metodo);
        };
    }

    private void validateInputs(MetodoPagamento metodo, StatusPagamento statusAtual) {
        if (metodo == null) {
            throw new IllegalArgumentException("Método de pagamento não pode ser nulo");
        }
        if (statusAtual == null) {
            throw new IllegalArgumentException("Status de pagamento não pode ser nulo");
        }
    }

    private StatusPagamento processarCartaoCredito(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> {
                this.dataPagamento = LocalDate.now();
                yield StatusPagamento.PENDENTE;
            }
            case APROVADO -> StatusPagamento.PAGO;
            case PAGO -> {
                this.dataConfirmacaoPagamento = LocalDate.now();
                yield StatusPagamento.DISPONIVEL;
            }
            case REPROVADO -> StatusPagamento.FALHOU;
            case FALHOU -> StatusPagamento.REVISAO;
            case CANCELADO -> StatusPagamento.DEVOLVIDO;
            case DEVOLVIDO -> StatusPagamento.ESTORNADO;
            case ESTORNADO -> StatusPagamento.REEMBOLSADO;
            default -> statusAtual;
        };
    }

    private StatusPagamento processarCartaoDebito(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> {
                this.dataPagamento = LocalDate.now();
                yield StatusPagamento.PENDENTE;
            }
            case PAGO -> {
                this.dataConfirmacaoPagamento = LocalDate.now();
                yield StatusPagamento.DISPONIVEL;
            }
            case FALHOU -> StatusPagamento.CANCELADO;
            case DEVOLVIDO -> StatusPagamento.ESTORNADO;
            default -> statusAtual;
        };
    }

    private StatusPagamento processarPix(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> {
                this.dataPagamento = LocalDate.now();
                yield StatusPagamento.PENDENTE;
            }
            case PAGO -> {
                this.dataConfirmacaoPagamento = LocalDate.now();
                yield StatusPagamento.DISPONIVEL;
            }
            case FALHOU -> StatusPagamento.CANCELADO;
            case DEVOLVIDO -> StatusPagamento.ESTORNADO;
            default -> statusAtual;
        };
    }

    private StatusPagamento processarBoleto(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> {
                this.dataPagamento = LocalDate.now();
                yield StatusPagamento.PENDENTE;
            }
            case APROVADO -> StatusPagamento.PAGO;
            case PAGO -> {
                this.dataConfirmacaoPagamento = LocalDate.now();
                yield StatusPagamento.DISPONIVEL;
            }
            case REPROVADO -> StatusPagamento.FALHOU;
            case FALHOU -> StatusPagamento.CANCELADO;
            case DEVOLVIDO -> StatusPagamento.ESTORNADO;
            default -> statusAtual;
        };
    }

    private StatusPagamento processarDinheiro(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> {
                this.dataPagamento = LocalDate.now();
                yield StatusPagamento.PENDENTE;
            }
            case PAGO -> {
                this.dataConfirmacaoPagamento = LocalDate.now();
                yield StatusPagamento.DISPONIVEL;
            }
            case FALHOU -> StatusPagamento.CANCELADO;
            default -> statusAtual;
        };
    }

    // TODO: Verificar se o Metodo confirmarPagamento é necessário
    public void confirmarPagamento() {
        if (this.statusPagamento == StatusPagamento.PAGO) {
            this.statusPagamento = StatusPagamento.DISPONIVEL;
        }
    }
}
