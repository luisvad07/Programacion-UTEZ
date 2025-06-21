<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http; // Importa el cliente HTTP
use Illuminate\Support\Facades\Log;   // Importa el Log para depuración

class PublicController extends Controller
{
    public function showNews()
    {
        $apiUrl = 'http://localhost:8081/mac-morelos-api/news/getAll';
        $newsData = []; // Inicializa como array vacío

        try {
            $response = Http::timeout(10)->get($apiUrl); // Timeout de 10 segundos

            if ($response->successful()) {
                $newsData = $response->json(); // Obtiene los datos como array asociativo
                // Verifica si la estructura esperada existe
                if (!isset($newsData['data']) || !is_array($newsData['data'])) {
                    Log::warning("La respuesta de la API de noticias no tiene la estructura esperada.", ['url' => $apiUrl, 'response_body' => $response->body()]);
                    $newsData = ['data' => [], 'error' => true, 'message' => 'Formato de respuesta inesperado.']; // Resetea si la estructura es incorrecta
                }
            } else {
                // La API respondió pero con un error (4xx o 5xx)
                Log::error("Error al obtener noticias de la API.", [
                    'url' => $apiUrl,
                    'status_code' => $response->status(),
                    'response_body' => $response->body() // Loguea el cuerpo para depurar
                ]);
                 $newsData = ['data' => [], 'error' => true, 'message' => 'No se pudieron obtener las noticias (Error ' . $response->status() . ').'];
            }

        } catch (\Illuminate\Http\Client\ConnectionException $e) {
            // Error de conexión (no se pudo conectar al servidor)
            Log::error("Error de conexión al intentar obtener noticias de la API.", [
                'url' => $apiUrl,
                'error_message' => $e->getMessage()
            ]);
            $newsData = ['data' => [], 'error' => true, 'message' => 'No se pudo conectar al servicio de noticias. Intente más tarde.'];

        } catch (\Exception $e) {
            // Cualquier otro error inesperado
            Log::error("Error inesperado al procesar la respuesta de la API de noticias.", [
                'url' => $apiUrl,
                'error_message' => $e->getMessage()
            ]);
            $newsData = ['data' => [], 'error' => true, 'message' => 'Ocurrió un error inesperado.'];
        }

        // Pasa los datos (o el array vacío con error) a la vista
        return view('landing_news', compact('newsData'));
    }
}
