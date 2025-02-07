# Avaliação de Técnicas de Engenharia de Prompt na Migração de Framework Backend
Este repositório contém a ferramenta de migração automatizada de projetos Spring para Quarkus, desenvolvida como parte do Trabalho de Conclusão de Curso (TCC) em Engenharia de Software. O objetivo da ferramenta é após o uso dela, poder avaliar técnicas de engenharia de prompt na conversão de código utilizando inteligência artificial. O repositório abrange tanto o frontend (Angular) quanto o backend (Quarkus).

## Tecnologias Utilizadas
- **Frontend**: Angular 17.3.8.
- **Backend**: Quarkus 3.13.0 - Java 21.
- **API de IA**: OpenAI com o modelo GPT-4o.

## Funcionamento
A ferramenta recebe arquivos de código fonte de projetos Spring e utiliza o modelo de IA GPT-4o para gerar sugestões de converção dos arquivos no formato adequado para Quarkus. O usuário pode visualizar os arquivos migrados e escolher entre diferentes técnicas de engenharia de prompt para otimizar a conversão. 

## Interface da Ferramenta
![Interface da Ferramenta](./assets/Interface%20Ferramenta.png)


## Interface da Ferramenta Preenchida
![Interface da Ferramenta Preenchida](./assets/Interface%20Ferramenta%20Preenchida.png)

## Diagrama de Contexto
![Diagrama de Contexto](./assets/Diagrama%20de%20Contexto.jpeg)

## Instalação e Execução

### 1. Clonar o repositório
```sh
git clone https://github.com/eduardoviega/SpringToQuarkusMigrationTCC  
cd SpringToQuarkusMigrationTCC
```

### 2. Configuração do Backend da Ferramenta

#### 2.2. Configurar a Chave da API
No arquivo **application.properties** no backend, defina a sua chave de api:

```application.properties
api-key=sua_chave_api  
```

#### 2.3. Executar a Aplicação
```sh
cd spring-to-quarkus-backend
./mvnw quarkus:dev
```

### 3. Configuração do Frontend da Ferramenta

#### 3.1. Instalar Dependências
```sh
cd spring-to-quarkus-frontend
npm install
```

#### 3.2. Executar o Frontend
```sh
ng serve
```

## Prompts e Resultados

- Os prompts de cada técnica utilizada podem ser encontrados pelo caminho abaixo: 

    ```./spring-to-quarkus-backend/prompts```

- Os resultados da aplicação dos prompts podem ser utilizados na ferramenta pelo enum RespostasTecnicasEnum e também podem ser encontrados pelo caminho abaixo: 

    ```./experimentos/resultados-prompts```