![programacion johansitoweb](https://github.com/user-attachments/assets/a6906ca2-4410-4b6a-a3e8-89d7ced69e7e)


# Construcción de una API con Node.js, Express y MongoDB para servir imágenes y definiciones en español e inglés
Esta guía te guiará a través del proceso de creación de una API robusta utilizando Node.js, Express y MongoDB. La API permitirá a los usuarios obtener imágenes y definiciones en español e inglés.

1. Configuración del entorno
Node.js y npm (Node Package Manager): Asegúrate de tener Node.js y npm instalados en tu sistema. Puedes descargarlos desde nodejs.org.
MongoDB: Necesitarás una instancia de MongoDB. Puedes instalar MongoDB Community Server localmente o usar un servicio en la nube como MongoDB Atlas (mongodb.com/atlas).

2. Inicialización del proyecto
Crea una carpeta para tu proyecto:

```
mkdir mi-api
cd mi-api
```

Inicializa un nuevo proyecto de Node.js:
```
npm init -y
```

Instala las dependencias necesarias:
```
npm install express mongoose body-parser
```
express: Para crear el servidor web
mongoose: Para interactuar con MongoDB.
body-parser: Para analizar los datos de las solicitudes.

3. Estructura del proyecto
Crea los siguientes archivos y carpetas:

```
mi-api/
├── index.js      // Archivo principal de la API
├── models/       // Modelos de datos
│   └── item.js   // Modelo para imágenes y definiciones
└── routes/       // Rutas de la API
    └── items.js  // Rutas para imágenes y definiciones

```
4. Modelo de datos (models/item.js)
```
const mongoose = require('mongoose');

const itemSchema = new mongoose.Schema({
  image_url: String,
  definition: {
    es: String,
    en: String
  }
});

module.exports = mongoose.model('Item', itemSchema);
```
Rutas de la API (routes/items.js)
```
const express = require('express');
const router = express.Router();
const Item = require('../models/item');

// Obtener imagen por ID
router.get('/images/:id', async (req, res) => {
  try {
    const item = await Item.findById(req.params.id);
    if (!item) {
      return res.status(404).json({ message: 'Imagen no encontrada' });
    }
    res.json({ image_url: item.image_url });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Obtener definición por ID
router.get('/definitions/:id', async (req, res) => {
  try {
    const item = await Item.findById(req.params.id);
    if (!item) {
      return res.status(404).json({ message: 'Definición no encontrada' });
    }
    res.json({ definition: item.definition });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;
```
Archivo principal (index.js)
```
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const itemsRoutes = require('./routes/items');

const app = express();
const PORT = process.env.PORT || 3000;

// Conexión a MongoDB (reemplaza con tu URI)
mongoose.connect('mongodb://localhost:27017/tu_base_de_datos', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => console.log('Conectado a MongoDB')).catch(err => console.error('Error de conexión:', err));

app.use(bodyParser.json());
app.use('/api/items', itemsRoutes);

app.listen(PORT, () => console.log(`Servidor escuchando en el puerto ${PORT}`));
```
7. Ejecutar la API
```
node index.js
```
8. Uso de la API
Obtener imagen: GET /api/items/images/:id
Obtener definición: GET /api/items/definitions/:id
Reemplaza :id con el ID del elemento que deseas obtener.

9. Ejemplo de datos en MongoDB
Puedes insertar datos de ejemplo en tu base de datos MongoDB usando MongoDB Compass o la línea de comandos:

```
[
  {
    "image_url": "https://example.com/image1.jpg",
    "definition": {
      "es": "Definición 1 en español",
      "en": "Definition 1 in English"
    }
  },
  {
    "image_url": "https://example.com/image2.jpg",
    "definition": {
      "es": "Definición 2 en español",
      "en": "Definition 2 in English"
    }
  }
]
```

cómo consumirla desde el frontend usando JavaScript y el método fetch.

1. Preparación del frontend
HTML: Crea un archivo index.html donde mostrarás la información.

```
<!DOCTYPE html>
<html>
<head>
    <title>Mi App</title>
</head>
<body>
    <h1>Información</h1>
    <div id="image-container"></div>
    <div id="definition-container"></div>
    <script src="script.js"></script> </body>
</html>
```
2. Consumiendo la API con fetch
```
// Obtener datos al cargar la página (ejemplo con ID 1)
window.addEventListener('DOMContentLoaded', () => {
  fetchData('1');
});

async function fetchData(id) {
  try {
    // Obtener imagen
    const imageResponse = await fetch(`/api/items/images/${id}`);
    const imageData = await imageResponse.json();
    if (imageResponse.ok) {
      displayImage(imageData.image_url);
    } else {
      handleError(imageData.message);
    }

    // Obtener definición
    const definitionResponse = await fetch(`/api/items/definitions/${id}`);
    const definitionData = await definitionResponse.json();
    if (definitionResponse.ok) {
      displayDefinition(definitionData.definition);
    } else {
      handleError(definitionData.message);
    }
  } catch (error) {
    handleError(error);
  }
}

function displayImage(imageUrl) {
  const imgElement = document.createElement('img');
  imgElement.src = imageUrl;
  document.getElementById('image-container').appendChild(imgElement);
}

function displayDefinition(definition) {
  const definitionES = document.createElement('p');
  definitionES.textContent = `Español: ${definition.es}`;
  const definitionEN = document.createElement('p');
  definitionEN.textContent = `Inglés: ${definition.en}`;
  const container = document.getElementById('definition-container');
  container.appendChild(definitionES);
  container.appendChild(definitionEN);
}

function handleError(error) {
  console.error('Error:', error);
  // Muestra un mensaje de error en la página o realiza otra acción
}
```
```
