# Playlist Service API 🎵

Bem-vindo ao **Playlist Service API**! Este é um serviço backend construído com **Spring Boot** para gerenciamento de listas de reprodução (Playlists) e Músicas. O projeto foi desenhado focando fortemente em boas práticas de engenharia de software, separação de responsabilidades e código limpo.

---

## 🚀 Funcionalidades

- **Gerenciamento de Autenticação (Auth):**
  - Registro de novos usuários com senha criptografada (BCrypt).
  - Login e autenticação via tokens JWT (JSON Web Token) sem estado (Stateless).

- **Gerenciamento de Playlists (Lists):**
  - Criação de novas playlists.
  - Listagem de todas as playlists disponíveis.
  - Busca de playlist por nome.
  - Exclusão de playlist.
  - Remoção de músicas específicas de dentro de uma playlist.

- **Gerenciamento de Músicas (Musics):**
  - Cadastro de novas músicas (Títúlo, Artista, Gênero, Ano, Álbum).
  - Busca flexível de músicas pelo título.
  - Edição completa dos dados de uma música existente.
  - Exclusão de músicas.

---

## 🛠️ Tecnologias e Dependências

Este projeto foi construído utilizando o ecossistema Java moderno e as seguintes dependências principais:

- **Java 21**: Versão LTS mais recente, aproveitando recursos modernos como *Records*.
- **Spring Boot 3.x**: Framework base da aplicação.
- **Spring Web**: Para criação dos endpoints RESTful.
- **Spring Data JPA / Hibernate**: Para persistência e ORM.
- **Spring Security**: Para proteção das rotas e implementação de regras de acesso.
- **H2 Database**: Banco de dados relacional em memória, configurado para facilitar a execução local (Runtime DB) sem configurações complexas.
- **Lombok**: Para redução de código boilerplate (Getters, Setters, Builders).
- **Jakarta Validation**: Para validação rigorosa de dados de entrada via DTOs (ex: `@NotBlank`, `@NotNull`, `@Valid`).
- **Mockito / JUnit 5**: Para testes unitários isolados e orientados a comportamento (BDD).
- **Maven**: Ferramenta de build e gerenciamento de pacotes.

---

## 🏗️ Arquitetura

O projeto foi estruturado seguindo conceitos avançados de design de software:

### 1. Screaming Architecture (Arquitetura que "Grita")
Ao abrir as pastas do projeto (`core/usecase/List`, `core/usecase/Music`, `core/dto`), a intenção do sistema "grita" diretamente para o desenvolvedor. Você não vê logo de cara "Controllers" ou "Services" genéricos misturados, mas sim o que a aplicação **faz**: *ListCreator*, *MusicDeleteById*, *ListMusicRemover*. O domínio do negócio é o protagonista da estrutura de pastas.

### 2. Clean Architecture / Hexagonal Architecture (Ports and Adapters)
O código está nitidamente isolado em camadas de dentro para fora:
- **`core/`**: Onde mora a verdadeira regra de negócio e os objetos de transferência (`dto`). Essa camada é completamente isolada e agnóstica de frameworks externos (exceto anotações simples de injeção e validação).
- **`infrastructure/`**: Onde residem os detalhes técnicos (A "periferia" do hexágono). Engloba as configurações do Spring (`config/`), a modelagem do banco e adaptadores JPA (`entity/` e `repositories/`) e a porta de entrada da web (`web/controller/`).

---

## 📐 Princípios SOLID Aplicados

- **S - Single Responsibility Principle (Princípio da Responsabilidade Única):**
  Cada classe dentro de `usecase/` tem um único motivo para mudar e faz exatamente uma coisa. Por exemplo, `ListCreator` cuida **apenas** da criação, enquanto `ListFinder` cuida da busca.
- **O - Open/Closed Principle (Princípio do Aberto/Fechado):**
  Uso de injeções de dependência através de interfaces (como `MusicRepository` estendendo `JpaRepository`). É fácil criar novas implementações de banco de dados sem alterar a lógica de negócios.
- **I - Interface Segregation Principle (Princípio da Segregação de Interfaces):**
  Interfaces finas e injetadas sob demanda. Controllers não recebem um "God Service" gigante; eles recebem instâncias apenas dos Use Cases exatos que precisam para executar suas rotas.
- **D - Dependency Inversion Principle (Princípio da Inversão de Dependência):**
  As regras de negócio (camada `core`) não instanciam repositórios concretos ou controllers. O `MusicController` depende de abstrações (`MusicCreator`), e o framework injeta essas dependências via construtor (Inversão de Controle).

---

## 🧪 Testes Unitários

Uma robusta suíte de **19 testes unitários** foi implementada com **JUnit 5** e **Mockito** (utilizando a vertente *BDDMockito* com `given/willReturn/then`).
Os testes cobrem:
- Todos os Casos de Uso (`Use Cases`) de Playlists e Músicas.
- Simulações de sucesso (Happy Path) e fluxos de falha e exceções (ex: tentar criar um recurso duplicado ou deletar um ID inexistente).
- Lógica de autenticação do `AuthController` (simulando a geração de JWT e o gerenciador de login).

*O projeto possui 100% de sucesso nas execuções de teste isoladas, garantindo que as regras de negócio puras estão íntegras antes da aplicação rodar.*

---

## ⚙️ Como Executar a Aplicação

A aplicação possui um banco de dados embutido em memória e tudo o que você precisa é do Java 21 instalado (ou simplesmente utilizar o Maven Wrapper incluído).

### Passo 1: Rodar a aplicação
Na raiz do projeto, execute o comando:

**No Windows:**
```cmd
.\mvnw.cmd spring-boot:run
```

**No Linux/Mac:**
```bash
./mvnw spring-boot:run
```
*(A aplicação estará rodando na porta `http://localhost:8080`)*

### Passo 2: Acessar o Banco de Dados H2 (Opcional)
Se quiser visualizar as tabelas do H2:
- **URL do Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:playlistdb`
- **User Name**: `SA`
- **Password**: *(deixe em branco)*

### Passo 3: Executar a Suíte de Testes
Para garantir que todos os testes estão passando:

**No Windows:**
```cmd
.\mvnw.cmd test
```

**No Linux/Mac:**
```bash
./mvnw test
```

### Passo 4: Testar os Endpoints via Postman
Na pasta `docs/` na raiz do projeto, existe um arquivo chamado **`Playlist_Service.postman_collection.json`**. 
1. Abra o seu Postman.
2. Clique em `Import` e arraste esse arquivo.
3. Você terá todas as requisições (Auth, Playlists e Musics) pré-configuradas e prontas para uso. 

*(**Dica:** Lembre-se de primeiro rodar o endpoint de **Register**, depois o de **Login** para gerar o Token JWT. O script da collection vai injetar esse token automaticamente como variável de ambiente em todas as requisições seguidas!)*
