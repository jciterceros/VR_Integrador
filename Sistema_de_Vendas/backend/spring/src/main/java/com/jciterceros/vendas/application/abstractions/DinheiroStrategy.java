package com.jciterceros.vendas.application.abstractions;

import com.jciterceros.vendas.application.abstractions.MetodoPagamentoStrategy;
import com.jciterceros.vendas.domain.entities.enums.MetodoPagamento;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;

public class DinheiroStrategy implements MetodoPagamentoStrategy {

    @Override
    public MetodoPagamento getMetodoPagamento() {
        return MetodoPagamento.DINHEIRO;
    }

    @Override
    public StatusPagamento processar(StatusPagamento statusAtual) {
        return switch (statusAtual) {
            case INICIADO -> StatusPagamento.AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> StatusPagamento.PENDENTE;
            case PAGO -> StatusPagamento.DISPONIVEL;
            case FALHOU -> StatusPagamento.CANCELADO;
            default -> statusAtual;
        };
    }
}
