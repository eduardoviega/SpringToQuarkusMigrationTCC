# ToDoList Application 📝🛒

## Descrição

Este é um projeto de uma aplicação de lista de tarefas (ToDoList) desenvolvida em Spring Boot. Ele permite aos usuários adicionar, remover e marcar como concluídas as tarefas em sua lista. 

## Arquitetura

O projeto segue um padrão arquitetural em camadas, com as seguintes camadas:

- **Controller:** Responsável por receber as requisições HTTP e chamar os métodos apropriados do serviço.
- **Service:** Contém a lógica de negócios da aplicação, processando os dados recebidos dos controllers e interagindo com o repositório.
- **Repository:** Fornece uma interface para interagir com o banco de dados, executando as consultas SQL.

## Endpoints

- **GET /getList:** Retorna todas as tarefas da lista.
- **POST /addItem:** Adiciona uma nova tarefa à lista.
- **DELETE /delete:** Remove uma tarefa da lista.
- **PUT /checkItem:** Marca ou desmarca uma tarefa como concluída.