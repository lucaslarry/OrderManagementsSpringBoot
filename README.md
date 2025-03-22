# Projeto Spring Boot: API de Gerenciamento Pedidos

Este projeto é uma API desenvolvida em Spring Boot 2.7.1 para gerenciar usuários, produtos, categorias e pedidos. A API utiliza autenticação JWT para garantir a segurança das operações e está documentada com Swagger para facilitar o uso e a integração.

---

## Entidades Principais

### 1. **User**
Representa um usuário do sistema. Implementa a interface `UserDetails` do Spring Security para integração com autenticação JWT. Um usuário pode ter vários cargos (`roles`) e fazer vários pedidos (`orders`).

- **Relacionamentos**:
    - `@ManyToMany` com `Role`: Um usuário pode ter vários cargos, e um cargo pode pertencer a vários usuários.
    - `@OneToMany` com `Order`: Um usuário pode fazer vários pedidos.

---

### 2. **Role**
Representa um cargo (role) que um usuário pode ter no sistema, como ADMIN ou USER.

- **Relacionamentos**:
    - `@ManyToMany` com `User`: Um cargo pode ser atribuído a vários usuários.

---

### 3. **Categoria**
Representa uma categoria de produtos, como "Eletrônicos" ou "Roupas".

- **Relacionamentos**:
    - `@OneToMany` com `Produto`: Uma categoria pode ter vários produtos.

---

### 4. **Produto**
Representa um produto disponível para venda, como um smartphone ou uma camiseta.

- **Relacionamentos**:
    - `@ManyToOne` com `Categoria`: Um produto pertence a uma categoria.
    - `@ManyToMany` com `Order`: Um produto pode estar em vários pedidos.

---

### 5. **Order**
Representa um pedido feito por um usuário, contendo uma lista de produtos.

- **Relacionamentos**:
    - `@ManyToOne` com `User`: Um pedido é feito por um usuário.
    - `@ManyToMany` com `Produto`: Um pedido pode conter vários produtos.

---

## DTOs (Data Transfer Objects)

Os DTOs são utilizados para transferir dados entre as camadas da aplicação, garantindo que apenas as informações necessárias sejam expostas.

### 1. **User**
- **UserCreateDTO**: Usado para criar um novo usuário.
- **UserDTO**: Usado para retornar informações sobre um usuário.

### 2. **Categoria**
- **CategoriaCreateDTO**: Usado para criar uma nova categoria.
- **CategoriaDTO**: Usado para retornar informações sobre uma categoria.

### 3. **Produto**
- **ProdutoCreateDTO**: Usado para criar um novo produto.
- **ProdutoDTO**: Usado para retornar informações sobre um produto.

### 4. **Order**
- **OrderCreateDTO**: Usado para criar um novo pedido.
- **OrderDTO**: Usado para retornar informações sobre um pedido.

---

## Endpoints da API

A API está organizada nos seguintes endpoints:

### Autenticação
- **POST /auth/login**: Realiza o login e retorna um token JWT.

### Usuários
- **POST /users**: Cria um novo usuário.
- **GET /users/{id}**: Retorna informações sobre um usuário específico.

### Categorias
- **POST /categories**: Cria uma nova categoria.
- **GET /categories/{id}**: Retorna informações sobre uma categoria específica.

### Produtos
- **POST /products**: Cria um novo produto.
- **GET /products/{id}**: Retorna informações sobre um produto específico.

### Pedidos
- **POST /orders**: Cria um novo pedido.
- **GET /orders/{id}**: Retorna informações sobre um pedido específico.

---
