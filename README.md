# Projeto Viterbo - API de Gerenciamento de Eventos
## Sobre
O Projeto Viterbo é uma API REST desenvolvida para gerenciar eventos e atividades relacionadas. Com esta API, é possível criar, editar, visualizar e excluir eventos, além de gerenciar atividades e usuários. Este sistema é ideal para organizações que necessitam de uma solução eficaz para o gerenciamento de eventos e atividades.

## Criadores
Jonathan Silva de Sá
Marcio Aguiar Brito
Márcio de Amorim Machado Ferreira
Pedro Paulo Sobral de Moraes
Victor Correa da Silva Moreira

## Tecnologias Utilizadas
Spring Boot
MySQL
Hibernate
Spring Security

## Configuração Local
Para executar esta API localmente:

Clone o repositório: git clone URL_DO_REPOSITORIO
Entre na pasta do projeto: cd projeto-viterbo
Configure o banco de dados MySQL conforme as especificações do projeto
Execute o projeto: ./mvnw spring-boot:run (Unix) ou mvnw spring-boot:run (Windows)

## Funcionalidades e Endpoints
Gerenciamento de Eventos: Criação, atualização e exclusão de eventos.
GET /api/eventos - Lista todos os eventos
POST /api/eventos - Cria um novo evento
PUT /api/eventos/{id} - Atualiza um evento existente
DELETE /api/eventos/{id} - Exclui um evento

Gerenciamento de Atividades: Criação, atualização e exclusão de atividades, bem como marcação de atividades favoritas.
GET /api/atividades - Lista todas as atividades
POST /api/atividades - Cria uma nova atividade
PUT /api/atividades/{id} - Atualiza uma atividade existente
DELETE /api/atividades/{id} - Exclui uma atividade
POST /api/atividades/{id}/favoritar - Marca uma atividade como favorita
DELETE /api/atividades/{id}/desfavoritar - Remove a marcação de favorita
Gerenciamento de Usuários: Criação de usuários e gerenciamento de seus papéis e atividades favoritas.
POST /api/users - Cadastra um novo usuário
GET /api/users - Lista todos os usuários (acesso restrito ao administrador)

## Documentação da API
A documentação completa da API, incluindo todos os endpoints e detalhes dos parâmetros, está disponível no arquivo swagger.yaml. Utilize o Swagger Editor para visualizar a documentação de forma interativa.

## Contribuições
Contribuições são bem-vindas para aprimorar a funcionalidade e eficácia da API. Para contribuir, por favor, faça um fork do repositório, crie um branch para suas alterações e envie um pull request para revisão.

## Licença
Não definido

## Contato
Para dúvidas, sugestões ou colaborações, entre em contato com um dos desenvolvedores listados acima.
