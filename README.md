# desafio-cielo
Desafio Cielo Bootcamp

### Desafio 1

- [x] Criação da classe abstrata Cliente e duas subclasses
- [x] Criação das validações dos campos com as anotações 
- [x] Criação da validação Cadastrar Cliente
- [x] Criação da validação Alterar Cliente
- [x] Criação da validação Consultar Cliente
- [x] Testes Unitários Cobrindo 70% do código mínimo

SWAGGER - Link - http://localhost:8081/swagger-ui/index.html#/
H2 - Link - http://localhost:8081/h2-console

### Desafio 2

- [x] Implementar Classe para Fila FIFO de forma primitiva
- [x] Acrescentar na API desafio 1 um endpoint para retirada de um cliente da fila 
- [x] Testes Unitários Cobrindo 70% do código mínimo

### Desafio 3

- [x] Implementar envio e recebimento de mensagens via SQL AWS

spring.cloud.aws.region.static                                        = us-east-1
spring.cloud.aws.credentials.access-key                               = AKIASNG5BBTTBXGMW3PC
spring.cloud.aws.credentials.secret-key                               = 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

spring.cloud.aws.sqs.endpoint                                         = https://sqs.us-east-1.amazonaws.com/165819780326/desafio-cielo-sqs.fifo
aws.topic-arn                                                         = arn:aws:sqs:us-east-1:165819780326:desafio-cielo-sqs.fifo
aws.queue-name                                                        = desafio-cielo-sqs.fifo
spring.cloud.aws.messaging.defaultDestination                         = https://sqs.us-east-1.amazonaws.com/165819780326/desafio-cielo-sqs.fifo

Para funcionamento, deverá ser criado um usuário que tenha acesso a leitura de SQS para retirada e um com acesso SQL FULL, substituir nas configurações acima
dentro de aplication.properties
