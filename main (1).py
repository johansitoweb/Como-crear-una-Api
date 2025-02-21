import requests

API_URL = 'http://localhost:3000/api/items' # Reemplaza con tu URL

def get_item(endpoint, id):
  url = f"{API_URL}/{endpoint}/{id}"
  response = requests.get(url)

  if response.status_code == 200:
    return response.json()
  else
    raise f"Error al obtener datos de la API: {response.status_code}"

# Obtener imagen
image = get_item('images', '1')
print(f"URL de la imagen: {image['image_url']}")

# Obtener definición
definition = get_item('definitions', '1')
print(f"Definición en español: {definition['definition']['es']}")
print(f"Definición en inglés: {definition['definition']['en']}")