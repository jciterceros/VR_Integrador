package com.jciterceros.vendas.application.abstractions;

import com.jciterceros.vendas.application.abstractions.MetodoPagamentoStrategy;
import com.jciterceros.vendas.domain.entities.enums.MetodoPagamento;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;

public class CartaoCreditoStrategy implements MetodoPagamentoStrategy {

    @Override
    public MetodoPagamento getMetodoPagamento() {
        return MetodoPagamento.CARTAO_CREDITO;
    }

    @Override
    public StatusPagamento processar(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> StatusPagamento.PENDENTE;
            case APROVADO -> StatusPagamento.PAGO;
            case PAGO -> StatusPagamento.DISPONIVEL;
            case REPROVADO -> StatusPagamento.FALHOU;
            case FALHOU -> StatusPagamento.REVISAO;
            case CANCELADO -> StatusPagamento.DEVOLVIDO;
            case DEVOLVIDO -> StatusPagamento.ESTORNADO;
            case ESTORNADO -> StatusPagamento.REEMBOLSADO;
            default -> statusAtual;
        };
    }
}
