package com.jciterceros.vendas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_itempedido")
public class ItemPedido {
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public void calcularSubtotal() {}
}
