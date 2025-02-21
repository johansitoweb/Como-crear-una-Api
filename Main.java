import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson; // Si usas Gson

public class ApiClient {

    private static final String API_URL = "http://localhost:3000/api/items"; // Reemplaza con tu URL

    public static void main(String[] args) throws Exception {
        // Obtener imagen
        Item image = getItem("images", "1");
        System.out.println("URL de la imagen: " + image.image_url);

        // Obtener definición
        Item definition = getItem("definitions", "1");
        System.out.println("Definición en español: " + definition.definition.es);
        System.out.println("Definición en inglés: " + definition.definition.en);
    }

    private static Item getItem(String endpoint, String id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%s/%s", API_URL, endpoint, id)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson(); // Si usas Gson
            return gson.fromJson(response.body(), Item.class); // Si usas Gson
        } else {
            throw new Exception("Error al obtener datos de la API: " + response.statusCode());
        }
    }

    // Clase para representar los datos (usando Gson)
    private static class Item {
        String image_url;
        Definition definition;
    }

    private static class Definition {
        String es;
        String en;
    }
}