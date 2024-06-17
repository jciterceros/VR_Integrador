package com.jciterceros.vendas.model;

import com.jciterceros.vendas.model.enums.MetodoPagamento;
import com.jciterceros.vendas.model.enums.StatusPagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
    void testProcessarPagamento() {
        Pagamento pagamento = new Pagamento();

        // PAGO
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_DEBITO);
        pagamento.setStatusPagamento(StatusPagamento.INICIADO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.PAGO, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.INICIADO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.PAGO, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.DINHEIRO);
        pagamento.setStatusPagamento(StatusPagamento.INICIADO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.PAGO, pagamento.getStatusPagamento());

        // AGUARDANDO_PAGAMENTO
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
        pagamento.setStatusPagamento(StatusPagamento.INICIADO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.BOLETO_BANCARIO);
        pagamento.setStatusPagamento(StatusPagamento.INICIADO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, pagamento.getStatusPagamento());

        // REVISAO
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
        pagamento.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.REVISAO, pagamento.getStatusPagamento());

        // DISPONIVEL
        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_DEBITO);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.BOLETO_BANCARIO);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.getStatusPagamento());

        pagamento.setMetodoPagamento(MetodoPagamento.DINHEIRO);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        pagamento.processarPagamento(pagamento.getMetodoPagamento(), pagamento.getStatusPagamento());
        assertEquals(StatusPagamento.DISPONIVEL, pagamento.getStatusPagamento());

        // TODO: DEVOLVIDO, CANCELADO, ESTORNO_VENDEDOR

    }

}