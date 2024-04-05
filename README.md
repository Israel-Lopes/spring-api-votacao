# Aplicação de votação

Para inicar aplicação via docker basta seguir os passos abaixo:

1. Executar build: ``sudo docker build -t <nome-da-imagem> .``
2. Iniciar o container: ``sudo docker run -p 8080:8080 -p 9090:9090 <nome-da-imagem>``

Segue abaixo a sequencia correta:

![sequencia](documents/diagrama_sequencia1.png)

1. criar associado
2. Criar sessao/pauta
3. iniciar votacao
4. associado votar


## Rotas

1. Associado

``POST`` Criar associado
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "cpf": "98765432109"
}' http://localhost:8080/associado
```

``GET`` Listar associados
```shell
curl -X GET -H "Content-Type: application/json" -d '{
}' http://localhost:8080/associado
```

---

2. Sessao

``POST`` Criar sessao de voto
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "tempoDaVotacao": "08:00:00"
}' http://localhost:8080/sessao
```
O campo ``tempoDaVotacao`` possui valor **default** de 1 minuto caso nao seja inserido tempo nele.

``GET`` Lista as sessoes
```shell
curl -X GET -H "Content-Type: application/json" -d '{
}' http://localhost:8080/sessao
```

``GET`` Lista as sessoes por id
```shell
curl -X GET -H "Content-Type: application/json" -d '{
}' http://localhost:8080/sessao/{id}
```

``PATCH`` Inicia sessao de voto
```shell
curl -X PATCH -H "Content-Type: application/json" -d '{
  "votacaoEmAndamento": true
}' http://localhost:8080/sessao/{id}
```

---

3. Pauta

``GET`` Listar pautas
```shell
curl -X GET http://localhost:8080/pauta
```

---

4. Votacao

``POST`` Cria um voto
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "cpf": "2222222",
  "voto": true,
  "idSessao": 1
}' http://localhost:8080/votacao
```

O campo **"voto"**, determina se o associado vota contra ou a favor. true para a favor e false para contra.


Fluxo completa de criacao ate a votacao:

1. Criando associado

Entrada:
```shell
curl -X POST -H "Content-Type: application/json" -d '{
"cpf": "74525561785"
}' http://localhost:8080/associado
```

saida:
```shell
{
  "cpf": "74525561785",
  "id": 10
}
```

```Deltando associado```
```shell
curl -X DELETE -H "Content-Type: application/json" -d '{
}' http://localhost:8080/associado/2
```

2. Criando a sessao de votacao

Entrada:
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "tempoDaVotacao": "08:00:00",
    "pauta": {
      "titulo": "Pauta sobre direitos trabalhistas",
      "descricao": "Referente a demissões"
    },
    "formulario": {}
}' http://localhost:8080/sessao
```

Retorno:
```shell
{
  "id": 8,
  "tempoDaVotacao": "08:00:00",
  "votacaoEmAndamento": false,
  "inicioDaContagem": null,
  "fimDaContagem": null,
  "formulario": null,
  "pauta": {
    "id": 3,
    "titulo": "Pauta sobre direitos trabalhistas",
    "descricao": "Referente a demissões"
  }
}
```

3. Iniciado sessao de votacao

Entrada:
```shell
curl -X PATCH -H "Content-Type: application/json" -d '{
  "votacaoEmAndamento": true
}' http://localhost:8080/sessao/8
```

Saida:
```shell
{
  "id": 8,
  "tempoDaVotacao": "08:00:00",
  "votacaoEmAndamento": true,
  "inicioDaContagem": "2023-06-07T13:56:04.335076652",
  "fimDaContagem": "2023-06-07T21:56:04.335076652",
  "formulario": null,
  "pauta": {
    "id": 3,
    "titulo": "Pauta sobre direitos trabalhistas",
    "descricao": "Referente a demissões"
  }
}
```

4. Criando voto
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "cpf": "74525561785",
  "voto": true,
  "idSessao": 8
}' http://localhost:8080/votacao
```





### Fluxo correto

1. Criar associado
```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "cpf": "98765432109"
}' http://localhost:8080/associado
```

2. Criando a sessao de votacao/pauta

```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "tempoDaVotacao": "08:00:00",
  "votacaoEmAndamento": false,
  "inicioDaContagem": null,
  "fimDaContagem": null,
  "formulario": {
    "votos": [],
    "idAssociadosQueVotaram": []
  },
  "pauta": {
    "titulo": "Pauta sobre vacinas",
    "descricao": "vacinacao de bebes"
  }
}' http://localhost:8080/sessao
```

3. Iniciar sessao para votos

```shell
curl -X PATCH -H "Content-Type: application/json" -d '{
  "votacaoEmAndamento": true
}' http://localhost:8080/sessao/1
```

4. Computar voto na sessao

```shell
curl -X POST -H "Content-Type: application/json" -d '{
  "cpf": "98765432109",
  "voto": "SIM",
  "idSessao": 1
}' http://localhost:8080/votacao
```

5. Contabilizar votos da sessao

```shell
curl -X GET -H "Content-Type: application/json" -d '{
}' http://localhost:8080/votacao/total/1
```