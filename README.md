Executar o docker-compose para subir o ambiente do kafka e zookeeper

Caso o projeto nao rode em sua maquina, deve-se adicionar ao host o seguinte endereço:

127.0.0.1 kafka localhost localhost.localdomain localhost4 localhost4.localdomain4

::1 localhost localhost.localdomain localhost6 localhost6.localdomain6

Modificar o conteiner docker do docker-compose
echo 'listeners=PLAINTEXT://kafka:9092' | tee -a /opt/bitnami/kafka/config/server.properties

Para enviar mensagens a partir do console-producer
kafka-console-producer --broker-list localhost:9092 --topic my-topic

Exemplos de JSON para body do POST - producer kafka:
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

Para construir o projeto:
-Na pasta raiz do projeto, executar o comando:
docker build -t wallet-consumer-image .
