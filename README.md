# Wishlist API

API REST responsável por gerenciar **Wishlists de clientes**, permitindo adicionar, remover, consultar produtos e verificar se um produto está presente na wishlist.

O projeto foi pensado para ser **simples de rodar**, **fácil de avaliar** e seguindo **boas práticas modernas** de desenvolvimento backend com Java e Spring.

---

## Funcionalidades

* Criar wishlist automaticamente ao adicionar o primeiro produto
* Adicionar produto à wishlist do cliente
* Remover produto da wishlist
* Consultar wishlist completa de um cliente
* Verificar se um produto está presente na wishlist
* Limite máximo de produtos por wishlist

---

## Stack utilizada

### Backend

* **Java 21**
* **Spring Boot 3.5.8**
* **Spring Web**
* **Spring Data MongoDB**
* **Bean Validation**
* **Lombok**

### Banco de dados

* **MongoDB 7**
* **Mongo Express** – Interface web para inspeção do banco

### Testes

* **JUnit 5**
* **Spock Framework (Groovy)**
* **Testcontainers**

### Build & Infra

* **Maven**
* **Docker & Docker Compose**

---

## Estrutura do projeto

```
src
 ├── main
 │   ├── java
 │   │   └── com.wishlist
 │   │       ├── controller
 │   │       ├── service
 │   │       ├── repository
 │   │       └── domain
 │   └── resources
 │       └── application.yml
 └── test
     └── java
         └── com.wishlist
             ├── controller      # Testes unitários (JUnit)
             ├── repository      # Testes integrados (Testcontainers)
             └── service         # Testes unitários (Spock)
```

---

## Como rodar a aplicação (forma mais simples)

### Pré-requisitos

* **Docker**
* **Docker Compose**

---

### Subindo tudo com Docker Compose

Na raiz do projeto:

```bash
docker compose up --build -d
```

Isso irá subir:

* Wishlist API
* MongoDB
* Mongo Express

---

### Acessos

| Serviço       | URL                                            |
| ------------- | ---------------------------------------------- |
| API           | [http://localhost:8080](http://localhost:8080) |
| Mongo Express | [http://localhost:8081](http://localhost:8081) |

**Mongo Express – credenciais:**

* Usuário: `dev`
* Senha: `dev`

---

## Testes

O projeto separa claramente **testes unitários** e **testes integrados**.

---

### Testes unitários

Incluem:

* Testes de service (Spock / Groovy)
* Testes de controller (JUnit + MockMvc)

Executar:

```bash
mvn test
```

---

### Testes integrados

Incluem:

* Testes de repositório com **MongoDB real** via Testcontainers

Executar:

```bash
mvn verify
```

> `verify` executa automaticamente os testes unitários antes dos integrados.

