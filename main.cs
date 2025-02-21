using System;
using System.Net.Http;
using System.Net.Http.Json; // Para usar ReadFromJsonAsync
using System.Threading.Tasks;
using System.Text.Json.Serialization; // Para usar JsonPropertyName

// Definición de clases para deserialización
public class Item
{
    [JsonPropertyName("image_url")] // Mapeo de nombres de propiedades JSON
    public string ImageUrl { get; set; }

    public Definition Definition { get; set; }
}

public class Definition
{
    public string Es { get; set; }
    public string En { get; set; }
}

public class ApiClient
{
    private static readonly HttpClient client = new HttpClient();
    private static readonly string apiUrl = "http://localhost:3000/api/items"; // Reemplaza con tu URL

    public static async Task<Item> GetItemAsync(string endpoint, string id)
    {
        try
        {
            HttpResponseMessage response = await client.GetAsync($"{apiUrl}/{endpoint}/{id}");
            response.EnsureSuccessStatusCode(); // Lanza una excepción si la respuesta no es exitosa

            return await response.Content.ReadFromJsonAsync<Item>(); // Deserializa la respuesta JSON
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error al obtener datos de la API: {ex.Message}");
            return null;
        }
    }

    public static async Task Main(string[] args)
    {
        // Obtener imagen
        Item image = await GetItemAsync("images", "1");
        Console.WriteLine($"URL de la imagen: {image?.ImageUrl}");

        // Obtener definición
        Item definition = await GetItemAsync("definitions", "1");
        Console.WriteLine($"Definición en español: {definition?.Definition?.Es}");
        Console.WriteLine($"Definición en inglés: {definition?.Definition?.En}");
    }
}