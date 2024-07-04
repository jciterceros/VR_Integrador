package com.jciterceros.vendas.model;

import com.jciterceros.vendas.model.enums.MetodoPagamento;
import com.jciterceros.vendas.model.enums.StatusPagamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PagamentoTest {

    @Test
    void testConstructor() {
        Long id = 1L;
        String numeroPedido = "123456";
        BigDecimal valor = new BigDecimal("100.00");
        LocalDate dataPagamento = LocalDate.now();
        LocalDate dataConfirmacaoPagamento = LocalDate.now().plusDays(1);
        StatusPagamento statusPagamento = StatusPagamento.INICIADO;
        MetodoPagamento metodoPagamento = MetodoPagamento.CARTAO_CREDITO;

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
        MetodoPagamento metodoPagamento = MetodoPagamento.CARTAO_CREDITO;

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
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
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
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_DEBITO);
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
        pagamento.setMetodoPagamento(MetodoPagamento.PIX);
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
        pagamento.setMetodoPagamento(MetodoPagamento.BOLETO_BANCARIO);
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
        pagamento.setMetodoPagamento(MetodoPagamento.DINHEIRO);
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
        pagamento.setMetodoPagamento(null);
        StatusPagamento statusAtual = StatusPagamento.INICIADO;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
        assertEquals("Método de pagamento não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com status de pagamento nulo")
    void testProcessarPagamentoStatusNulo() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
        StatusPagamento statusAtual = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
        assertEquals("Status de pagamento não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de processamento de pagamento com status de pagamento desconhecido")
    void testProcessarPagamentoStatusDesconhecido() {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(MetodoPagamento.BITCOIN);
        StatusPagamento statusAtual = StatusPagamento.FALHOU;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pagamento.processarPagamento(pagamento.getMetodoPagamento(), statusAtual));
        assertEquals("Método de pagamento desconhecido: BITCOIN", exception.getMessage());
    }
}