const express = require('express');
const app = express();
const validaCPF = require('./validaCPF');

app.get('/cpf/:cpf', (req, res) => {
  const cpf = req.params.cpf;

  if (validaCPF(cpf)) {
    res.json({ status: 'ABLE_TO_VOTE' });
  } else {
    res.json({ status: 'UNABLE_TO_VOTE' });
  }
});

app.listen(9090, () => {
  console.log('Servidor JSON rodando em http://localhost:9090');
});
