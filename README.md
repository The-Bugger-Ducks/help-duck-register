# API - microsserviço de usuarios
Microsserviço responsável por controlar o cadastro de usuários (3º DSM - 2022)

Neste guia iremos configurar o ambiente de desenvolvimento, clonando o projeto e instalando suas dependências. Ainda neste guia é possível encontrar uma breve explicação da estrutura das pastas adotada para a construção em equipe desse projeto.

### Links úteis (para antes de clonar o repositório)
- [Instalação das ferramentas](https://uttermost-apricot-1bb.notion.site/Instala-o-das-Ferramentas-Spring-29c3794b88b0460782f454d1c31249d8): como instalar o Java, MongoDB e o Docker (opcional)

## Clonando projeto
No arquivo `application-dev.properties`, complete a variavel "spring.data.mongodb.uri" com a URI que o mongo disponibiliza ao logar na plataforma do MongoDB (Deployment -> Database -> connect -> connect your application). Se for localhost, utilize suas credenciais no formato da URI ( mongodb://<username>:<password>@localhost/ ):

>Print do mongoDB atlas connect URI

![image](https://user-images.githubusercontent.com/55204419/162738729-580b22f4-ea41-4d94-a9b2-d20c790458f7.png)

```cl
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.rpjin.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
```
Com todas as ferramentas necessárias devidamente instaladas (Java, Git...), baixe ou clone este repositório pelo terminal seguindo passo a passo descrito abaixo:

```bash
# Baixe este repositório ou clone pelo Git usando o comando:
$ git clone https://github.com/The-Bugger-Ducks/help-duck-users-microservice.git

# Acesse a pasta do projeto
$ cd help-duck-users-microservice

# Espere o Maven carregar as dependências do projeto (são listadas no arquivo pom.xml)

# Execute o projeto utilizando sua IDE preferida (Eclipse, VS Code, IntelliJ, etc.)
```

Agora o servidor deste projeto está ativo. É só acessar pelo localhost na porta 8081: [https://localhost:8081]!

## Explicação da estrutura das pastas

| Pasta                                        | Definição                                                                                            |
| -------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| :open_file_folder: main/ .../               | Arquivos com o código fonte do projeto |
| :open_file_folder: main/ .../ config        | Configuração de cors, csrf, etc|
| :open_file_folder: main/ .../ controller    | Arquivos com os métodos de requisição das rotas|
| :open_file_folder: main/ .../ entity        | Arquivos com funções mais especificas, ex: atualizador, adicionador de links para HATEOAS, etc|
| :open_file_folder: main/ .../ enums         | Arquivos de padronização de entrada para campos específicos no banco de dados|
| :open_file_folder: main/ .../ repository    | Arquivo para utilização de CRUD de entidades (collection - mongodb) do projeto|
| :page_facing_up: main/ .../ HelpduckusersApplication.java | Arquivo principal do projeto| 
| :open_file_folder: main/ resources/         | Arquivos para configurações globais do projeto (ex: credenciais em banco de dados)|
| :page_facing_up: docker-compose             | Arquivo usado para "conteinerizar" um banco mongo local|
| :page_facing_up: pom.xml                    | Arquivo usado gerenciar as dependencias do projeto com o Maven|

### Tecnologias utilizadas
As seguintes tecnologias e ferramentas estão sendo utilizadas neste projeto:

- [Java](https://www.java.com/pt-BR/)
- [Spring](https://spring.io/)
- [MongoDB](https://www.mongodb.com/)
- [Docker](https://www.docker.com/)
