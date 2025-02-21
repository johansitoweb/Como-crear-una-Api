import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

@Serializable
data class Item(val image_url: String?, val definition: Definition?)

@Serializable
data class Definition(val es: String?, val en: String?)

val API_URL = "http://localhost:3000/api/items" // Reemplaza con tu URL

suspend fun getItem(endpoint: String, id: String): Item? {
    val client = HttpClient(CIO)
    return try {
        val response: HttpResponse = client.get("$API_URL/$endpoint/$id")
        if (response.status.value == 200) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<Item>(response.bodyAsText())
        } else {
            println("Error al obtener datos de la API: ${response.status.value}")
            null
        }
    } catch (e: Exception) {
        println("Error al obtener datos de la API: ${e.message}")
        null
    } finally {
        client.close()
    }
}

fun main() = runBlocking {
    // Obtener imagen
    val image = getItem("images", "1")
    println("URL de la imagen: ${image?.image_url}")

    // Obtener definición
    val definition = getItem("definitions", "1")
    println("Definición en español: ${definition?.definition?.es}")
    println("Definición en inglés: ${definition?.definition?.en}")
}