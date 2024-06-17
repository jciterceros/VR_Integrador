package com.jciterceros.vendas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    public void calcularTotal() {}
    public void adicionarItem() {}
    public void removerItem() {}
}
