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