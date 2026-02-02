# Personal Finance API - Sistema FinTech

Aplicação completa de gestão financeira pessoal desenvolvida em Java com Spring Boot (Backend) e Angular (Frontend - planejado). Este projeto demonstra conhecimentos em desenvolvimento full-stack, arquitetura de APIs REST, testes automatizados e boas práticas de desenvolvimento.

## Objetivo do Projeto

Desenvolver uma aplicação completa de gestão financeira pessoal que permita aos usuários:
- Gerenciar contas bancárias
- Realizar transações (depósitos, saques, transferências, pagamentos)
- Consultar histórico de transações
- Visualizar saldos e extratos
- Frontend em Angular (planejado)

## Tecnologias Utilizadas

### Backend
- Java 17 - Linguagem de programação
- Spring Boot 3.2.0 - Framework para desenvolvimento
- Spring Data JPA - Persistência de dados
- H2 Database - Banco de dados em memória (desenvolvimento)
- PostgreSQL - Banco de dados relacional (produção - se der tempo)
- Lombok - Redução de boilerplate
- SpringDoc OpenAPI - Documentação da API (Swagger)
- Maven - Gerenciamento de dependências
- JUnit 5 - Testes automatizados
- Docker - Containerização (planejado)

### Frontend (Planejado)
- Angular - Framework frontend
- TypeScript - Linguagem
- RxJS - Programação reativa

## Funcionalidades Implementadas

### Backend
- CRUD completo de Usuários
- CRUD completo de Contas Bancárias
- CRUD completo de Transações
- Gestão de Transações (Depósito, Saque, Transferência, Pagamento)
- Consulta de Extratos por conta
- Validação de Saldo (impede saque/transferência sem saldo)
- Validação de sintaxe de email
- Testes de Integração (User, Account, Transaction)
- Dados fake para facilitar testes
- Documentação da API com Swagger
- Arquitetura em camadas (Controller → Service → Repository → Model)
- DTOs para transferência de dados
- Configuração H2 para desenvolvimento

### Frontend
- Planejado: Interface Angular para consumir a API

## Roadmap - Pendências

### Backend
- Tratamento global de exceções (@ControllerAdvice)
- Validações adicionais (CPF, etc)
- Docker e docker-compose
- Configuração PostgreSQL (se der tempo)

### Frontend
- Estrutura inicial do projeto Angular
- Componentes de listagem (usuários, contas, transações)
- Formulários de criação/edição
- Integração com API backend

## Estrutura do Projeto

```
fintech-api/
├── backend/                    # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fintech/
│   │   │   │   ├── config/     # Configurações (DataLoader)
│   │   │   │   ├── controller/ # Controllers REST
│   │   │   │   ├── service/    # Lógica de negócio
│   │   │   │   ├── repository/ # Repositórios JPA
│   │   │   │   ├── model/      # Entidades JPA
│   │   │   │   └── dto/        # Data Transfer Objects
│   │   │   └── resources/
│   │   │       ├── application.yml      # Config H2 (dev)
│   │   │       └── application-prod.yml # Config PostgreSQL (prod)
│   │   └── test/               # Testes de integração
│   └── pom.xml
├── frontend/                   # Frontend Angular (planejado)
└── README.md
```

## Modelo de Dados

### User (Usuário)
- ID, Nome, Email, CPF
- Relacionamento 1:N com Account
- Validações: email único, CPF único, sintaxe de email

### Account (Conta)
- ID, Número da Conta (gerado automaticamente), Saldo, Tipo (Corrente/Poupança/Investimento)
- Relacionamento N:1 com User
- Relacionamento 1:N com Transaction

### Transaction (Transação)
- ID, Valor, Tipo (Depósito/Saque/Transferência/Pagamento), Descrição, Data
- Relacionamento N:1 com Account
- Atualiza saldo automaticamente baseado no tipo

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Node.js e Angular CLI (para frontend - quando implementado)

### Executando o Backend

1. Clone o repositório:
```bash
git clone https://github.com/lelec0/fintech-api.git
cd fintech-api/backend
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação iniciará em: http://localhost:8080

Nota: A aplicação usa H2 (banco em memória) por padrão. Dados fake são inseridos automaticamente ao iniciar.

3. Acesse a documentação da API:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

### Executando os Testes

```bash
cd backend
mvn test
```

### Executando com Docker (Planejado)

```bash
docker-compose up -d
```

## Endpoints da API

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
- `GET /api/transactions/account/{accountId}` - Listar transações de uma conta (extrato)
- `POST /api/transactions` - Criar nova transação (atualiza saldo automaticamente)
- `DELETE /api/transactions/{id}` - Deletar transação

## Testes

O projeto possui testes de integração que testam fluxos completos:

- UserControllerIntegrationTest - Testa CRUD completo de usuários
- AccountControllerIntegrationTest - Testa CRUD completo de contas
- TransactionControllerIntegrationTest - Testa criação de transações e extratos

```bash
cd backend
mvn test
```

## Dados Fake

Ao iniciar a aplicação, dados fake são inseridos automaticamente para facilitar testes:

- 2 Usuários: João Silva e Maria Santos
- 3 Contas: Contas com saldos iniciais
- 3 Transações: Histórico de movimentações

Você pode testar os endpoints imediatamente sem precisar criar dados manualmente.

## Próximos Passos

### Backend
- Implementar tratamento global de exceções
- Adicionar validações adicionais (CPF, etc)
- Configurar Docker e docker-compose
- Configurar PostgreSQL para produção (se der tempo)

### Frontend
- Criar projeto Angular
- Implementar componentes de listagem
- Implementar formulários
- Integrar com API backend

## Contribuindo

Este é um projeto pessoal para demonstração de habilidades. Sinta-se à vontade para sugerir melhorias.

## Licença

Este projeto é de código aberto e está disponível sob a licença MIT.

## Autor

Desenvolvido como projeto de portfólio para demonstrar conhecimentos em:
- Java e Spring Boot
- APIs REST
- Arquitetura em camadas (MVC)
- Banco de dados relacionais (H2/PostgreSQL)
- Testes automatizados
- Docker (planejado)
- Angular (planejado)
- Boas práticas de desenvolvimento

---

Status do Projeto: Backend Funcional | Frontend Planejado
