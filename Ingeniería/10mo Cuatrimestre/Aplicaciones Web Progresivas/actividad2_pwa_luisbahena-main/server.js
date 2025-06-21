const express = require('express')
const path = require('path')

const httpPort = 3000

const app = express()

app.use(express.static(path.join(__dirname, 'public')))

app.get('/', function(req, res) {
  res.sendFile(path.join(__dirname, 'public/index.html'))
})

app.listen(httpPort, function () {
  console.log(`Listening on port ${httpPort}!`)
})

app.get('/api/data', (req, res) => {
  // Este endpoint puede devolver datos de la base de datos
  res.json({ message: 'Prueba de mensaje para PouchDB 10C' });
});