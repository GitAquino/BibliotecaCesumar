# Biblioteca Cesumar

Sistema de gerenciamento de biblioteca desenvolvido como projeto educacional para a Universidade Cesumar. Esta aplicação web permite o cadastro, listagem e exclusão de livros em um acervo virtual.

## Tecnologias Utilizadas

- Java 21
- Jakarta EE 10
  - Jakarta Servlet 6.0
  - Jakarta Server Faces (JSF) 4.0
  - Jakarta Contexts and Dependency Injection (CDI) 4.0
- Maven
- HTML/CSS
- JSP/JSTL
- Weld (implementação CDI)
- Mojarra (implementação JSF)

## Funcionalidades

- Cadastro de livros com validação de dados
- Listagem de todos os livros do acervo
- Exclusão de livros por ISBN
- Prevenção de cadastro duplicado (por ISBN)
- Interface responsiva e amigável

## Estrutura do Projeto

```
BibliotecaCesumar/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/cesumar/biblioteca/
│   │   │       ├── controller/
│   │   │       │   ├── LivroBean.java     # Managed Bean JSF
│   │   │       │   └── LivroServlet.java  # Servlet Controller
│   │   │       ├── dao/
│   │   │       │   └── LivroDAO.java      # Acesso a dados
│   │   │       └── model/
│   │   │           └── Livro.java         # Modelo de dados
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── beans.xml              # Configuração CDI
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── beans.xml              # Configuração CDI
│   │       │   ├── faces-config.xml       # Configuração JSF
│   │       │   ├── web.xml                # Configuração da aplicação web
│   │       │   └── views/jsp/             # Views JSP
│   │       │       ├── erro.jsp           # Página de erro
│   │       │       └── listaLivros.jsp    # Listagem de livros
│   │       └── cadastroLivro.xhtml        # Formulário JSF de cadastro
└── pom.xml                                # Configuração Maven
```

## Como Executar

### Pré-requisitos

- JDK 21 ou superior
- Apache Maven 3.8 ou superior
- Servidor de aplicação compatível com Jakarta EE 10 (como Apache Tomcat 10)

### Passos para Execução

1. Clone o repositório:
   ```
   git clone https://github.com/seu-usuario/BibliotecaCesumar.git
   ```

2. Navegue até o diretório do projeto:
   ```
   cd BibliotecaCesumar
   ```

3. Compile o projeto com Maven:
   ```
   mvn clean package
   ```

4. Implante o arquivo WAR gerado (target/BibliotecaCesumar.war) no seu servidor de aplicação.

5. Acesse a aplicação pelo navegador:
   ```
   http://localhost:8082/BibliotecaCesumar_war_exploded/
   ```
   Observações:
   - Deve definir a porta HTTP assim como configurada no servidor tomcat
   - Deve definir o artifact com o arquivo war_exploded na aba Deployment nas configurações do servidor tomcat

## Uso da Aplicação

1. **Cadastro de Livros**: Acesse a página de cadastro e preencha o formulário com os dados do livro.
   - Título: Nome do livro (2-100 caracteres)
   - Autor: Nome do autor (2-100 caracteres)
   - Ano de Publicação: Entre 1400 e o ano atual
   - ISBN: Identificador único do livro (apenas números e hífens)

2. **Listagem de Livros**: Acesse a página de listagem para ver todos os livros cadastrados.

3. **Exclusão de Livros**: Na página de listagem, clique no botão "Excluir" ao lado do livro que deseja remover.

## Implementação

- A aplicação utiliza uma arquitetura MVC (Model-View-Controller)
- Os dados são armazenados em memória (sem persistência em banco de dados)
- Combina JSF para o cadastro de livros e Servlet/JSP para listagem e exclusão
- Implementa validações tanto no cliente quanto no servidor

## Contribuição

Este é um projeto educacional. Contribuições são bem-vindas através de pull requests.
