# Sistema de Vendas

## Diagrama de Classes

Definindo cinco entidades principais para o sistema de vendas:

- Cliente, Produto, Pedido, ItemPedido e Pagamento

### Cliente

- **Atributos**:

  - `nome`: String
  - `email`: String
  - `telefone`: String

- **Métodos**:
  - `realizarPedido()`
  - `cancelarPedido()`

### Produto

- **Atributos**:

  - `id`: int
  - `nome`: String
  - `descricao`: String
  - `preco`: float

- **Métodos**:
  - `atualizarEstoque()`
  - `aplicarDesconto()`

### Pedido

- **Atributos**:

  - `id`: int
  - `data`: Date
  - `total`: float

- **Métodos**:
  - `calcularTotal()`
  - `adicionarItem()`
  - `removerItem()`

### ItemPedido

- **Atributos**:

  - `quantidade`: int
  - `precoUnitario`: float

- **Métodos**:
  - `calcularSubtotal()`

### Pagamento

- **Atributos**:

  - `id`: int
  - `metodo`: String
  - `status`: String

- **Métodos**:
  - `processarPagamento()`
  - `confirmarPagamento()`

<div align="center">
<img alt="Imagem de um Diagrama de Classes para um Sistema de Vendas" title="Diagrama de Classes Sistema de Vendas" width="350px" src="https://github.com/jciterceros/VR_Integrador/blob/6f7e62a3f2ae96bca2973fb1b3ae9b7e519d278a/Sistema_de_Vendas/Diagrama%20de%20classes.png">
</div>

## Diagramas de Caso de Uso

### Caso de Uso: Realizar Pedido

- **Ator**: Cliente
- **Descrição**: O cliente deseja realizar um pedido, selecionando produtos e efetuando o pagamento.

### Caso de Uso: Atualizar Produto

- **Ator**: Administrador
- **Descrição**: O administrador deseja atualizar as informações dos produtos, como preço e descrição.

### Definir quem é o usuário desse sistema

Os usuários principais do sistema são:

1. **Cliente**: Pessoa que compra produtos.
2. **Administrador**: Pessoa que gerencia o estoque e as informações dos produtos.

### O que o usuário deseja fazer no sistema? (2 casos)

Ações dos Usuários no Sistema

1. O **Cliente**: Deseja realizar um pedido.
2. O **Administrador**: Deseja atualizar as informações dos produtos.

## Diagrama de Sequência (Com base no caso de uso "Realizar Pedido")

<div align="center">
<img alt="Imagem de um Diagrama de Sequencia" title="Diagrama de Sequencias" width="550px" src="https://github.com/jciterceros/VR_Integrador/blob/29ba0a4e876308ecca858e8c7a4ee4e09c101df3/Sistema_de_Vendas/Diagrama%20de%20sequencia.png">
</div>

#### Classes e Métodos no Caso de Uso "Realizar Pedido"

No caso de uso "Realizar Pedido", o usuário (Cliente) interage com as seguintes classes:

1. **Cliente**

   - `realizarPedido()`

2. **Pedido**

   - `adicionarItem()`
   - `calcularTotal()`

3. **ItemPedido**

   - `calcularSubtotal()`

4. **Pagamento**
   - `processarPagamento()`

Essas são as sequências de ações necessárias para realizar um pedido no sistema de vendas.

## Linguagem Ubíqua presentes no sistema:

A seguir temos descrição das entidades principais, seus atributos e métodos, os casos de uso, os atores envolvidos e os objetivos dos usuários no sistema de vendas, utilizando a linguagem Ubíqua.

### Entidades Principais

#### Cliente

- **Descrição**: Pessoa que realiza compras no sistema.
- **Atributos**:
  - `nome`: Nome do cliente.
  - `email`: Endereço de email do cliente.
  - `telefone`: Número de telefone do cliente.
- **Métodos**:
  - `realizarPedido()`: Método para o cliente realizar um novo pedido.
  - `cancelarPedido()`: Método para o cliente cancelar um pedido existente.

#### Produto

- **Descrição**: Item disponível para compra no sistema.
- **Atributos**:
  - `id`: Identificador único do produto.
  - `nome`: Nome do produto.
  - `descricao`: Descrição detalhada do produto.
  - `preco`: Preço do produto.
- **Métodos**:
  - `atualizarEstoque()`: Método para atualizar a quantidade disponível do produto.
  - `aplicarDesconto()`: Método para aplicar um desconto ao preço do produto.

#### Pedido

- **Descrição**: Representa uma compra realizada por um cliente.
- **Atributos**:
  - `id`: Identificador único do pedido.
  - `data`: Data em que o pedido foi realizado.
  - `total`: Valor total do pedido.
- **Métodos**:
  - `calcularTotal()`: Método para calcular o valor total do pedido.
  - `adicionarItem()`: Método para adicionar um item ao pedido.
  - `removerItem()`: Método para remover um item do pedido.

#### ItemPedido

- **Descrição**: Representa um produto específico dentro de um pedido.
- **Atributos**:
  - `quantidade`: Quantidade do produto no pedido.
  - `precoUnitario`: Preço unitário do produto no pedido.
- **Métodos**:
  - `calcularSubtotal()`: Método para calcular o subtotal do item no pedido.

#### Pagamento

- **Descrição**: Representa o pagamento de um pedido.
- **Atributos**:
  - `id`: Identificador único do pagamento.
  - `metodo`: Método utilizado para o pagamento (ex: cartão de crédito, boleto).
  - `status`: Status atual do pagamento (ex: pendente, confirmado).
- **Métodos**:
  - `processarPagamento()`: Método para processar o pagamento do pedido.
  - `confirmarPagamento()`: Método para confirmar o pagamento.

### Casos de Uso

#### Realizar Pedido

- **Ator**: Cliente
- **Descrição**: O cliente seleciona produtos, adiciona ao carrinho e realiza o pagamento para efetuar a compra.
- **Sequência de Interações**:
  1 - Cliente chama `realizarPedido()`.
  2 - Pedido chama `adicionarItem()`.
  3 - ItemPedido chama `calcularSubtotal()`.
  4 - Pedido chama `calcularTotal()`.
  5 - Pagamento chama `processarPagamento()`.

#### Atualizar Produto

- **Ator**: Administrador
- **Descrição**: O administrador atualiza as informações de um produto, como preço e descrição.
- **Sequência de Interações**:
  1 - Administrador chama `atualizarEstoque()`.
  2 - Administrador chama `aplicarDesconto()`.

### Usuários do Sistema

- **Cliente**: Pessoa que compra produtos.
- **Administrador**: Pessoa que gerencia o estoque e as informações dos produtos.

### Objetivos dos Usuários

1. **Cliente**
   - Realizar um pedido.
   - Cancelar um pedido.
2. **Administrador**
   - Atualizar as informações dos produtos.
   - Gerenciar o estoque.
