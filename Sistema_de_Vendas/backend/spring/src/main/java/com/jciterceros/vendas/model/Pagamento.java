package com.jciterceros.vendas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    private Long id;
    private String status;
    private String metodo;

    public void processarPagamento() {}
    public void confirmarPagamento() {}
}
