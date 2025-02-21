package main

import (
	"encoding/json"
	"fmt"
	"net/http"
)

type Item struct {
	ImageURL string `json:"image_url"`
	Definition Definition `json:"definition"`
}

type Definition struct {
	Es string `json:"es"`
	En string `json:"en"`
}

func main() {
	// Obtener imagen
	image := getItem("images", "1")
	fmt.Println("URL de la imagen:", image.ImageURL)

	// Obtener definición
	definition := getItem("definitions", "1")
	fmt.Println("Definición en español:", definition.Definition.Es)
	fmt.Println("Definición en inglés:", definition.Definition.En)
}

func getItem(endpoint, id string) Item {
	url := fmt.Sprintf("http://localhost:3000/api/items/%s/%s", endpoint, id) // Reemplaza con tu URL
	resp, err := http.Get(url)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()

	var item Item
	if err := json.NewDecoder(resp.Body).Decode(&item); err != nil {
		panic(err)
	}

	return item
}