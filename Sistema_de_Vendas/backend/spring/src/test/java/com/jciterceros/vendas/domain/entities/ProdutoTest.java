package com.jciterceros.vendas.domain.entities;

import com.jciterceros.vendas.domain.entities.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoTest {

    @Test
    void testConstructor() {
        Long id = 1L;
        String nome = "Produto Teste";
        String descricao = "Descrição do produto teste";
        BigDecimal preco = new BigDecimal("29.99");
        int quantidadeEstoque = 100;

        Produto produto = new Produto(id, nome, descricao, preco, quantidadeEstoque);

        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(preco, produto.getPreco());
        assertEquals(quantidadeEstoque, produto.getQuantidadeEstoque());
    }

    @Test
    void testSettersAndGetters() {
        Produto produto = new Produto();

        Long id = 1L;
        String nome = "Produto Teste";
        String descricao = "Descrição do produto teste";
        BigDecimal preco = new BigDecimal("29.99");
        int quantidadeEstoque = 100;

        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);

        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(preco, produto.getPreco());
        assertEquals(quantidadeEstoque, produto.getQuantidadeEstoque());
    }

    @Test
    void testAtualizarEstoque() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(50);

        produto.atualizarEstoque(10);
        assertEquals(60, produto.getQuantidadeEstoque());

        produto.atualizarEstoque(-10);
        assertEquals(50, produto.getQuantidadeEstoque());
    }

    @Test
    void testAplicarDesconto() {
        Produto produto = new Produto();
        produto.setPreco(new BigDecimal("100.00"));

        produto.aplicarDesconto(new BigDecimal("10.00"));
        assertEquals(new BigDecimal("90.00"), produto.getPreco());

        produto.aplicarDesconto(new BigDecimal("5.00"));
        assertEquals(new BigDecimal("85.00"), produto.getPreco());
    }
}