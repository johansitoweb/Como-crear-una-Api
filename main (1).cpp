#include <iostream>
#include <cpprestsdk/http.h>
#include <cpprestsdk/json.h>

using namespace web;
using namespace web::http;
using namespace web::json;

const std::wstring API_URL = L"http://localhost:3000/api/items"; // Reemplaza con tu URL

pplx::task<json::value> get_item(const std::wstring& endpoint, const std::wstring& id) {
    uri_builder builder(API_URL);
    builder.append_path(endpoint).append_path(id);

    return http_client(builder.to_string()).request(methods::GET).then([](http_response response) {
        if (response.status_code() == status_codes::OK) {
            return response.extract_json();
        } else {
            throw std::runtime_error("Error al obtener datos de la API: " + std::to_string(response.status_code()));
        }
    });
}

int main() {
    try {
        // Obtener imagen
        auto image_task = get_item(L"images", L"1");
        auto image_json = image_task.get();
        std::wcout << L"URL de la imagen: " << image_json.at(L"image_url").as_string() << std::endl;

        // Obtener definición
        auto definition_task = get_item(L"definitions", L"1");
        auto definition_json = definition_task.get();
        std::wcout << L"Definición en español: " << definition_json.at(L"definition").at(L"es").as_string() << std::endl;
        std::wcout << L"Definición en inglés: " << definition_json.at(L"definition").at(L"en").as_string() << std::endl;

    } catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
    }

    return 0;
}