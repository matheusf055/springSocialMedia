# Projeto Rede Social

Este repositório contém o código-fonte do projeto "Rede Social", O projeto utiliza várias tecnologias modernas para construir uma aplicação de mídia social com funcionalidades como gerenciamento de usuários, postagens, reposts e comentários.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Java 17**: Linguagem de programação principal utilizada.
- **Docker**: Para empacotamento e distribuição da aplicação em containers.
- **PostgreSQL**: Banco de dados relacional para persistência de dados.
- **JWT**: Para autenticação e autorização de usuários.
- **Swagger**: Para documentação e testes da API.
- **Maven**: Gerenciamento de dependências e build do projeto.
- **JUnit**: Framework para testes unitários em Java.
- **ModelMapper**: Para conversão entre entidades e DTOs.

# Estrutura do Projeto

## Funcionalidades

- **Registro e Login**: Usuários podem se registrar e fazer login para acessar a aplicação.
- **Gerenciamento de Perfil**: Usuários podem gerenciar suas informações de perfil e seguir outros usuários.
- **Postagens**: Usuários podem criar e gerenciar postagens.
- **Reposts e Comentários**: Usuários podem repostar e comentar em postagens existentes.

## Documentação da API

A documentação da API está disponível através do Swagger. Após iniciar a aplicação, acesse http://localhost:8080/swagger-ui.html para visualizar e testar os endpoints da API.

## Execução do Projeto

Para executar o projeto localmente, siga os passos abaixo:

1. Clone este repositório.
2. Configure o banco de dados PostgreSQL e ajuste o arquivo `application.yml` conforme necessário.
3. Utilize Docker para empacotar a aplicação em um container.
4. Execute os containers utilizando `docker-compose up --build` para facilitar a inicialização da aplicação.
