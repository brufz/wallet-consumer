# Instruções para Executar o Ambiente

## 1. Subir o Ambiente Kafka e Zookeeper

Execute o seguinte comando para iniciar o ambiente Kafka e Zookeeper usando Docker Compose:

```bash
docker-compose up -d

## 2. Rodar os Projetos Localmente
Certifique-se de rodar os seguintes projetos localmente:

wallet
wallet-producer

## Acessar o Swagger

### PRODUCER

- http://localhost:8070/swagger-ui/index.html#

**Exemplos de JSON para o corpo do POST - Producer Kafka:**

```json
{
  "cpf": "43693769800",
  "amount": 10.00,
  "transactionType": "ADICAO",
  "transactionDate": "2023-10-10"
}

{
  "cpf": "43693769800",
  "amount": 1.00,
  "transactionType": "COMPRA",
  "transactionDate": "2023-10-10"
}

### CONSUMER
- http://localhost:8090/swagger-ui/index.html#
**Token para autenticação**
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdGF0dXMiOiJBQ1RJVkUiLCJyb2xlIjoiVVNFUiJ9.rtYrujHxaPj9i0eYeRCKVWdHzn76hpZrs3fppvrSfeYeCIQb0Em5k1RbzqCXkXtCtuisgJIZmlSido6G96TJxw

