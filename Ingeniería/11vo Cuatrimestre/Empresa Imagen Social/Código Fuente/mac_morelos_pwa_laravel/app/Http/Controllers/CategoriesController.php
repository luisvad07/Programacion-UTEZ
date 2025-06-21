<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\Rule; // Importa Rule para validaciones únicas (si es necesario en el frontend)

class CategoriesController extends Controller
{
    // Asegúrate de que la URL base y el prefijo de la API sean correctos
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/categories';

    /**
     * Obtiene las cabeceras de autenticación.
     * Asume que el token se guarda en la sesión.
     *
     * @return array
     */
    private function getAuthHeaders()
    {
        // Ajusta según tu método de autenticación real
        $token = session('auth_token');
        if (!$token) {
            // Podrías lanzar una excepción o redirigir al login si no hay token
            Log::warning('Intento de acceso a API sin token de autenticación.');
            // throw new \Exception('No autenticado.'); // O manejar de otra forma
            return ['Content-Type' => 'application/json']; // Devuelve solo content-type si no hay token
        }
        return [
            'Authorization' => 'Bearer ' . $token,
            'Content-Type' => 'application/json',
            'Accept' => 'application/json', // Es buena práctica incluir Accept
        ];
    }

    /**
     * Muestra la lista de categorías.
     *
     * @return \Illuminate\View\View|\Illuminate\Http\RedirectResponse
     */
    public function index()
    {
        $categories = $this->getAllCategories();

        if ($categories === null) {
            // Manejar el error, por ejemplo, redirigir con un mensaje de error
            // Ajusta la ruta de redirección si es necesario
            return redirect()->route('dashboard.redirect') // O alguna ruta de dashboard/error genérica
                   ->with('error', 'No se pudieron cargar los datos de categorías desde la API.');
        }

        // Ajusta la ruta de la vista según tu estructura
        return view('modules.categories.categorias_noticias', compact('categories'));
    }

    /**
     * Obtiene todas las categorías desde la API.
     *
     * @return array|null Null en caso de error.
     */
    private function getAllCategories()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->get("{$this->apiBaseUrl}/getAll");

            if ($response->successful()) {
                return $response->json()['data'] ?? []; // Devuelve array vacío si 'data' no existe
            } else {
                Log::error('Error al obtener las categorías desde API: ' . $response->status() . ' - ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las categorías desde API: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    /**
     * Almacena una nueva categoría llamando a la API.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\RedirectResponse
     */
    public function create(Request $request)
    {
        // Validar los datos de entrada
        $validatedData = $request->validate([
            'name' => 'required|string|max:50',
            'description' => 'nullable|string', // TEXT puede ser grande, no limitar longitud aquí
        ]);

        // Preparar datos para la API
        $apiData = [
            'name' => $validatedData['name'],
            'description' => $validatedData['description'],
        ];

        // Enviar datos a la API
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->post("{$this->apiBaseUrl}/createCategory", $apiData);

            if ($response->successful()) {
                return redirect()->route('categories.index')->with('success', 'Categoría creada exitosamente.');
            } else {
                // Intenta obtener un mensaje de error específico de la API
                $errorMessage = $response->json()['message'] ?? 'Error desconocido al crear la categoría.';
                Log::error('Error al crear categoría vía API: ' . $response->status() . ' - ' . $response->body());
                return redirect()->route('categories.index')->with('error', 'Error al crear la categoría: ' . $errorMessage);

            }
        } catch (\Exception $e) {
            Log::error('Excepción al crear categoría vía API: ' . $e->getMessage());
            return redirect()->route('categories.index')->with('error', 'Error de conexión al crear la categoría.');
        }
    }

    /**
     * Actualiza una categoría existente llamando a la API.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int|string  $id  El ID de la categoría a actualizar
     * @return \Illuminate\Http\RedirectResponse
     */
    public function update(Request $request, $id)
    {
         // Validar los datos de entrada
         $validatedData = $request->validate([
            'name' => 'required|string|max:50',
            'description' => 'nullable|string',
        ]);

        // Preparar datos para la API
        $apiData = [
            'name' => $validatedData['name'],
            'description' => $validatedData['description'],
            // 'categoryId' no se envía en el body, va en la URL
        ];

        // Enviar datos a la API
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->put("{$this->apiBaseUrl}/{$id}", $apiData);

            if ($response->successful()) {
                return redirect()->route('categories.index')->with('success', 'Categoría actualizada exitosamente.');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error desconocido al actualizar la categoría.';
                Log::error('Error al actualizar categoría vía API (ID: '.$id.'): ' . $response->status() . ' - ' . $response->body());
                 // return redirect()->back()->withErrors(['api_error' => $errorMessage])->withInput();
                return redirect()->route('categories.index')->with('error', 'Error al actualizar la categoría: ' . $errorMessage);
            }
        } catch (\Exception $e) {
            Log::error('Excepción al actualizar categoría vía API (ID: '.$id.'): ' . $e->getMessage());
            return redirect()->route('categories.index')->with('error', 'Error de conexión al actualizar la categoría.');
        }
    }

    /**
     * Elimina una categoría llamando a la API.
     *
     * @param  int|string  $id El ID de la categoría a eliminar
     * @return \Illuminate\Http\RedirectResponse
     */
    public function delete($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->delete("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return redirect()->route('categories.index')->with('success', 'Categoría eliminada exitosamente.');
            } else {
                 $errorMessage = $response->json()['message'] ?? 'Error desconocido al eliminar la categoría.';
                 // Un código 404 podría significar que ya fue eliminada o no existe
                 if ($response->status() == 404) {
                     $errorMessage = 'Categoría no encontrada para eliminar.';
                 }
                 // Otras validaciones, ej: 409 Conflict si tiene elementos asociados y no se puede borrar
                 elseif ($response->status() == 409) {
                    $errorMessage = $response->json()['message'] ?? 'No se puede eliminar la categoría, puede tener elementos asociados.';
                 }

                Log::error('Error al eliminar categoría vía API (ID: '.$id.'): ' . $response->status() . ' - ' . $response->body());
                return redirect()->route('categories.index')->with('error', 'Error al eliminar la categoría: ' . $errorMessage);
            }
        } catch (\Exception $e) {
             Log::error('Excepción al eliminar categoría vía API (ID: '.$id.'): ' . $e->getMessage());
            return redirect()->route('categories.index')->with('error', 'Error de conexión al eliminar la categoría.');
        }
    }
}
