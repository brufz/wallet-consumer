# Instruções para Executar o Ambiente

## 1. Subir o Ambiente Kafka, Zookeeper e postgresql

Execute o seguinte comando para iniciar o ambiente Kafka e Zookeeper usando Docker Compose:

```bash
docker-compose up -d

## 2. Subir os containers do Producer e Consumer
docker build -t consumer-wallet . 
docker run -d --name consumer-wallet-container --network docker_default -p 8090:8090 consumer-wallet

docker build -t producer-wallet . 
docker run -d --name producer-wallet-container --network docker_default -p 8090:8090 producer-wallet

## Acessar o Swagger

### PRODUCER

- http://localhost:8070/swagger-ui/index.html#

**Exemplos de JSON para o corpo do POST - Producer Kafka:**

```json
{
  "cpf": "43693769800",
  "amount": 100.00,
  "transactionType": "ADICAO",
  "transactionDate": "2023-10-10"
}

{
  "cpf": "43693769800",
  "amount": 10.00,
  "transactionType": "COMPRA",
  "transactionDate": "2023-10-10"
}

##CURL PRODUCER
curl --location 'http://localhost.com:8070/wallet' \
--header 'Content-Type: application/json' \
--data '{
  "cpf": "43693769800",
  "amount": 1000.00,
  "transactionType": "ADICAO",
  "transactionDate": "2024-08-02"
}
'

### CONSUMER
- http://localhost:8090/swagger-ui/index.html#
**Token para autenticação**
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdGF0dXMiOiJBQ1RJVkUiLCJyb2xlIjoiVVNFUiJ9.rtYrujHxaPj9i0eYeRCKVWdHzn76hpZrs3fppvrSfeYeCIQb0Em5k1RbzqCXkXtCtuisgJIZmlSido6G96TJxw

### CURL CONSUMER
curl --location 'http://localhost.com:8090/wallet/balance/43693769800' \
--header 'Authorization: ••••••'

curl --location 'http://localhost.com:8090/wallet/statement/43693769800?dataInicial=2023-08-21&dataFinal=2024-08-21' \
--header 'Authorization: ••••••'

![img.png](img.png)
