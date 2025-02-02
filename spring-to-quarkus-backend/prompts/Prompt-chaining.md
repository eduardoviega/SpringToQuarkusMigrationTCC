# Passo 1: Obter Informações do Projeto Original

Você é um especialista em frameworks Java e migração de projetos backend. Antes de realizar a migração, analise os arquivos do projeto Spring enviados. Responda às seguintes perguntas com base no projeto original:

1. Quais são os módulos principais do projeto e suas responsabilidades (ex.: autenticação, gestão de usuários, etc.)?
2. Liste todas as dependências do arquivo `pom.xml` e descreva brevemente o propósito de cada uma.
3. Quais propriedades principais estão configuradas em `application.yml` ou `application.properties` (ex.: datasources, mensagens, portas)?
4. O projeto utiliza algum recurso avançado do Spring (ex.: AOP, Spring Security, Spring Batch)?

Após essa análise, me informe o que foi identificado.

---

# Passo 2: Planejamento da Migração

Com base nas informações fornecidas, crie um plano detalhado para a migração. O plano deve incluir:

1. Quais anotações Spring serão substituídas por equivalentes Quarkus (inclua exemplos se possível)?
2. Dependências específicas a serem ajustadas ou substituídas no `pom.xml`.
3. Ajustes esperados no arquivo de configuração para se adaptar ao formato Quarkus.
4. Soluções alternativas para qualquer funcionalidade do Spring não diretamente suportada no Quarkus.

Responda com o plano de migração.

---

# Passo 3: Realizar a Migração de um Módulo

Escolha um módulo do projeto identificado no passo 1. Realize a migração desse módulo seguindo as etapas abaixo e as repita para todos os módulos encontrados:

1. Converta as classes desse módulo para Quarkus, ajustando as anotações e imports.
2. Atualize as dependências no `pom.xml` necessárias para esse módulo.
3. Adapte as configurações relacionadas no arquivo de configuração.

Repita o **Passo 3** para cada módulo identificado no projeto.

---

# Passo 4: Revisão e Finalização

Após concluir a migração de todos os módulos:

1. Verifique que o projeto completo está funcional no Quarkus.
2. Crie um arquivo `README.md` descrevendo:
   - As principais mudanças realizadas.
   - As dependências alteradas ou adicionadas.
   - As soluções alternativas implementadas.
3. Envie o projeto final completo, incluindo todas as classes convertidas, arquivos de configuração e o `README.md`.

