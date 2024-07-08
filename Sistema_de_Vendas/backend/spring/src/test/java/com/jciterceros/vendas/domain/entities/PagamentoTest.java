package com.jciterceros.vendas.domain.entities;

import com.jciterceros.vendas.application.abstractions.*;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PagamentoTest {
    private MetodoPagamentoStrategy cartaoCreditoStrategy;
    private MetodoPagamentoStrategy cartaoDebitoStrategy;
    private MetodoPagamentoStrategy pixStrategy;
    private MetodoPagamentoStrategy boletoBancarioStrategy;
    private MetodoPagamentoStrategy dinheiroStrategy;
    private MetodoPagamentoStrategy bitCoinStrategy;

    @BeforeEach
    void setUp() {
        cartaoCreditoStrategy = new CartaoCreditoStrategy();
        cartaoDebitoStrategy = new CartaoDebitoStrategy();
        pixStrategy = new PixStrategy();
        boletoBancarioStrategy = new BoletoBancarioStrategy();
        dinheiroStrategy = new DinheiroStrategy();
        bitCoinStrategy = new BitCoinStrategy();
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        String numeroPedido = "123456";
        BigDecimal valor = new BigDecimal("100.00");
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataConfirmacaoPagamento = LocalDate.now().plusDays(1);
        StatusPagamento statusPagamento = StatusPagamento.INICIADO;
        MetodoPagamentoStrategy metodoPagamento = cartaoCreditoStrategy;

        Pagamento pagamento = new Pagamento(id, numeroPedido, valor, dataPagamento, dataConfirmacaoPagamento, statusPagamento, metodoPagamento);

        assertEquals(id, pagamento.getId());
        assertEquals(numeroPedido, pagamento.getNumeroPedido());
        assertEquals(valor, pagamento.getValor());
        assertEquals(dataPagamento, pagamento.getDataPagamento());
        assertEquals(dataConfirmacaoPagamento, pagamento.getDataConfirmacaoPagamento());
        assertEquals(statusPagamento, pagamento.getStatusPagamento());
        assertEquals(metodoPagamento, pagamento.getMetodoPagamento());
    }

    @Test
    void testSettersAndGetters() {
        Pagamento pagamento = new Pagamento();

        Long id = 1L;
        String numeroPedido = "123456";
        BigDecimal valor = new BigDecimal("100.00");
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataConfirmacaoPagamento = LocalDate.now().plusDays(1);
        StatusPagamento statusPagamento = StatusPagamento.INICIADO;
        MetodoPagamentoStrategy metodoPagamento = cartaoCreditoStrategy;

        pagamento.setId(id);
        pagamento.setNumeroPedido(numeroPedido);
        pagamento.setValor(valor);
        pagamento.setDataPagamento(dataPagamento);
        pagamento.setDataConfirmacaoPagamento(dataConfirmacaoPagamento);
        pagamento.setStatusPagamento(statusPagamento);
        pagamento.setMetodoPagamento(metodoPagamento);

        assertEquals(id, pagamento.getId());
        assertEquals(numeroPedido, pagamento.getNumeroPedido());
        assertEquals(valor, pagamento.getValor());
        assertEquals(dataPagamento, pagamento.getDataPagamento());
        assertEquals(dataConfirmacaoPagamento, pagamento.getDataConfirmacaoPagamento());
        assertEquals(statusPagamento, pagamento.getStatusPagamento());
        assertEquals(metodoPagamento, pagamento.getMetodoPagamento());
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com cartão de crédito")
    void testProcessarCartaoCredito() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(cartaoCreditoStrategy);
        StatusPagamento statusAtual;

        statusAtual = StatusPagamento.INICIADO;
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.AGUARDANDO_PAGAMENTO;
        assertEquals(StatusPagamento.PENDENTE, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.APROVADO;
        assertEquals(StatusPagamento.PAGO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.PAGO;
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.REPROVADO;
        assertEquals(StatusPagamento.FALHOU, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.FALHOU;
        assertEquals(StatusPagamento.REVISAO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.CANCELADO;
        assertEquals(StatusPagamento.DEVOLVIDO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.DEVOLVIDO;
        assertEquals(StatusPagamento.ESTORNADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.ESTORNADO;
        assertEquals(StatusPagamento.REEMBOLSADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com cartão de débito")
    void testProcessarCartaoDebito() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(cartaoDebitoStrategy);
        StatusPagamento statusAtual;

        statusAtual = StatusPagamento.INICIADO;
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.AGUARDANDO_PAGAMENTO;
        assertEquals(StatusPagamento.PENDENTE, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.PAGO;
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.FALHOU;
        assertEquals(StatusPagamento.CANCELADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.DEVOLVIDO;
        assertEquals(StatusPagamento.ESTORNADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com PIX")
    void testProcessarPix() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(pixStrategy);
        StatusPagamento statusAtual;

        statusAtual = StatusPagamento.INICIADO;
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.AGUARDANDO_PAGAMENTO;
        assertEquals(StatusPagamento.PENDENTE, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.PAGO;
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.FALHOU;
        assertEquals(StatusPagamento.CANCELADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.DEVOLVIDO;
        assertEquals(StatusPagamento.ESTORNADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com boleto bancário")
    void testProcessarBoleto() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(boletoBancarioStrategy);
        StatusPagamento statusAtual;

        statusAtual = StatusPagamento.INICIADO;
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.AGUARDANDO_PAGAMENTO;
        assertEquals(StatusPagamento.PENDENTE, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.APROVADO;
        assertEquals(StatusPagamento.PAGO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.PAGO;
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.REPROVADO;
        assertEquals(StatusPagamento.FALHOU, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.FALHOU;
        assertEquals(StatusPagamento.CANCELADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.DEVOLVIDO;
        assertEquals(StatusPagamento.ESTORNADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com dinheiro")
    void testProcessarDinheiro() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(dinheiroStrategy);
        StatusPagamento statusAtual;

        statusAtual = StatusPagamento.INICIADO;
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.AGUARDANDO_PAGAMENTO;
        assertEquals(StatusPagamento.PENDENTE, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.PAGO;
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));

        statusAtual = StatusPagamento.FALHOU;
        assertEquals(StatusPagamento.CANCELADO, pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com método de pagamento nulo")
    void testProcessarPagamentoMetodoNulo() {
        Pagamento pagamento = new Pagamento();
        StatusPagamento statusInicial = StatusPagamento.INICIADO;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pagamento.processarPagamento(null, statusInicial));
        assertEquals("Método de pagamento não pode ser nulo", exception.getMessage());
    }

    private Executable getProcessarPagamento(Pagamento pagamento, StatusPagamento statusAtual) {
        return () -> pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual);
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com status de pagamento nulo")
    void testProcessarPagamentoStatusNulo() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(cartaoCreditoStrategy);

        Exception exception = assertThrows(IllegalArgumentException.class, getProcessarPagamento(pagamento, null));
        assertEquals("Status de pagamento não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com status de pagamento desconhecido")
    void testProcessarPagamentoStatusDesconhecido() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(bitCoinStrategy);
        StatusPagamento statusAtual = StatusPagamento.ERROR;

        Exception exception = assertThrows(IllegalArgumentException.class, getProcessarPagamento(pagamento, statusAtual));
        assertEquals("Status de pagamento não pode ser ERROR", exception.getMessage());
    }
}