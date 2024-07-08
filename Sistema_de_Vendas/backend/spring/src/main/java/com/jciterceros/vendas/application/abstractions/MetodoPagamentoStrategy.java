package com.jciterceros.vendas.application.abstractions;

import com.jciterceros.vendas.domain.entities.Pagamento;
import com.jciterceros.vendas.domain.entities.enums.MetodoPagamento;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;

public interface MetodoPagamentoStrategy {

    MetodoPagamento getMetodoPagamento();
//    StatusPagamento processar(StatusPagamento statusAtual, Pagamento pagamento);
    StatusPagamento processar(StatusPagamento statusAtual);
}
