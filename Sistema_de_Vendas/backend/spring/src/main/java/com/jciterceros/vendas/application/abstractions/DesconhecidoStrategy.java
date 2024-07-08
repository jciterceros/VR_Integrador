package com.jciterceros.vendas.application.abstractions;

import com.jciterceros.vendas.domain.entities.enums.MetodoPagamento;
import com.jciterceros.vendas.domain.entities.enums.StatusPagamento;

public class DesconhecidoStrategy implements MetodoPagamentoStrategy{
    @Override
    public MetodoPagamento getMetodoPagamento() {
        return MetodoPagamento.DESCONHECIDO;
    }

    @Override
    public StatusPagamento processar(StatusPagamento statusAtual) {
        return StatusPagamento.ERROR;
    }
}
