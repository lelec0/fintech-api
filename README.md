# 💰 Personal Finance API - Sistema FinTech

API REST desenvolvida em Java com Spring Boot para gestão financeira pessoal. Este projeto demonstra conhecimentos em desenvolvimento backend, arquitetura de APIs, testes automatizados e boas práticas de desenvolvimento.

## 🎯 Objetivo do Projeto

Desenvolver uma aplicação completa de gestão financeira pessoal que permita aos usuários:
- Gerenciar contas bancárias
- Realizar transações (depósitos, saques, transferências, pagamentos)
- Consultar histórico de transações
- Visualizar saldos e extratos

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework para desenvolvimento
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados relacional (produção)
- **H2 Database** - Banco de dados em memória (desenvolvimento/testes)
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API (Swagger)
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **JUnit 5** - Testes automatizados

## 📋 Funcionalidades

### ✅ Planejadas
- [ ] CRUD de Usuários
- [ ] CRUD de Contas Bancárias
- [ ] Gestão de Transações (Depósito, Saque, Transferência, Pagamento)
- [ ] Consulta de Extratos
- [ ] Validação de Saldo
- [ ] Testes Unitários e de Integração
- [ ] Documentação da API com Swagger
- [ ] Dockerização da aplicação
- [ ] Frontend React (opcional)

## 🏗️ Arquitetura

```
src/
├── main/
│   ├── java/com/fintech/
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios Spring Data
│   │   ├── service/        # Lógica de negócio
│   │   ├── controller/     # Controllers REST
│   │   └── dto/            # Data Transfer Objects
│   └── resources/
│       ├── application.yml # Configurações
│       └── application-dev.yml
└── test/
    └── java/com/fintech/   # Testes
```

## 📦 Modelo de Dados

### User (Usuário)
- ID, Nome, Email, CPF
- Relacionamento 1:N com Account

### Account (Conta)
- ID, Número da Conta, Saldo, Tipo (Corrente/Poupança/Investimento)
- Relacionamento N:1 com User
- Relacionamento 1:N com Transaction

### Transaction (Transação)
- ID, Valor, Tipo (Depósito/Saque/Transferência/Pagamento), Descrição, Data
- Relacionamento N:1 com Account

## 🔧 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- PostgreSQL (para produção) ou H2 (para desenvolvimento)
- Docker (opcional)

### Executando localmente

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd personal-finance-api
```

2. Configure o banco de dados (PostgreSQL):
```bash
# Criar banco de dados
createdb fintech_db

# Ou usando Docker
docker run --name postgres-fintech -e POSTGRES_DB=fintech_db \
  -e POSTGRES_USER=fintech_user -e POSTGRES_PASSWORD=fintech_pass \
  -p 5432:5432 -d postgres:15
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

4. Acesse a documentação da API:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

### Executando com Docker

```bash
docker-compose up -d
```

## 📚 Endpoints da API

### Usuários
- `GET /api/users` - Listar todos os usuários
- `GET /api/users/{id}` - Buscar usuário por ID
- `POST /api/users` - Criar novo usuário
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário

### Contas
- `GET /api/accounts` - Listar todas as contas
- `GET /api/accounts/{id}` - Buscar conta por ID
- `GET /api/accounts/user/{userId}` - Listar contas de um usuário
- `POST /api/accounts` - Criar nova conta
- `PUT /api/accounts/{id}` - Atualizar conta
- `DELETE /api/accounts/{id}` - Deletar conta

### Transações
- `GET /api/transactions` - Listar todas as transações
- `GET /api/transactions/{id}` - Buscar transação por ID
- `GET /api/transactions/account/{accountId}` - Listar transações de uma conta
- `POST /api/transactions` - Criar nova transação
- `DELETE /api/transactions/{id}` - Deletar transação

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com cobertura
mvn test jacoco:report
```

## 📝 Próximos Passos

- [ ] Implementar controllers REST
- [ ] Adicionar tratamento de exceções global
- [ ] Implementar testes unitários
- [ ] Implementar testes de integração
- [ ] Adicionar validações de negócio
- [ ] Configurar Docker e docker-compose
- [ ] Adicionar autenticação e autorização (JWT)
- [ ] Implementar frontend React

## 🤝 Contribuindo

Este é um projeto pessoal para demonstração de habilidades. Sinta-se à vontade para sugerir melhorias!

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença MIT.

## 👨‍💻 Autor

Desenvolvido como projeto de portfólio para demonstrar conhecimentos em:
- Java e Spring Boot
- APIs REST
- Banco de dados relacionais
- Testes automatizados
- Docker
- Boas práticas de desenvolvimento

---

**Status do Projeto:** 🚧 Em Desenvolvimento
