<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http; // Facade para peticiones HTTP
use Illuminate\Support\Facades\Log; // Para registrar errores
use Carbon\Carbon; // Para formatear fechas

class AssingController extends Controller
{
    private $apiBaseUrl;

    public function __construct()
    {
        // Obtener la URL base de la API desde el archivo .env
        // Asegúrate de tener API_BASE_URL=http://localhost:8081/mac-morelos-api en tu .env
        $this->apiBaseUrl = rtrim(env('API_BASE_URL', 'http://localhost:8081/mac-morelos-api'), '/');
    }

    private function getAuthHeaders()
    {
        // Asume que el token de autenticación se guarda en la sesión.
        $token = session('auth_token');
        if (!$token) {
            // Manejar caso donde no hay token (ej: redirigir a login)
            // Por ahora, lanzaremos una excepción o retornaremos un array vacío
            // dependiendo de cómo quieras manejar la seguridad.
             Log::warning('Intento de acceso a API sin token de autenticación.');
             // throw new \Exception("Token de autenticación no encontrado en la sesión.");
             return ['Content-Type' => 'application/json']; // O manejar de otra forma
        }
        return [
            'Authorization' => 'Bearer ' . $token,
            'Accept' => 'application/json', // Es buena práctica indicar que aceptas JSON
            'Content-Type' => 'application/json',
        ];
    }

    /**
     * Muestra la lista de asignaciones y carga los datos para el modal de creación.
     */
    public function index()
    {
        $assignments = [];
        $promoters = [];
        $citizens = [];
        $error = null;

        try {
            // 1. Obtener todas las asignaciones existentes
            $responseAssignments = Http::withHeaders($this->getAuthHeaders())
                ->get($this->apiBaseUrl . '/promoterAssignments/getAll');

            if ($responseAssignments->successful() && isset($responseAssignments->json()['data'])) {
                $assignments = $responseAssignments->json()['data'];
            } else {
                 Log::error('Error al obtener asignaciones de la API', [
                    'status' => $responseAssignments->status(),
                    'body' => $responseAssignments->body()
                 ]);
                $error = 'No se pudieron cargar las asignaciones existentes. ' . ($responseAssignments->json()['message'] ?? '');
            }

            // 2. Obtener lista de Promotores (roleId = 2)
            $responsePromoters = Http::withHeaders($this->getAuthHeaders())
                ->get($this->apiBaseUrl . '/users/role/2'); // Rol Promotor

            if ($responsePromoters->successful() && isset($responsePromoters->json()['data'])) {
                $promoters = $responsePromoters->json()['data'];
            } else {
                Log::error('Error al obtener promotores de la API', [
                    'status' => $responsePromoters->status(),
                    'body' => $responsePromoters->body()
                 ]);
                 // Añadir al error general o manejar específicamente
                $error = ($error ? $error . ' ' : '') . 'No se pudieron cargar los promotores. ' . ($responsePromoters->json()['message'] ?? '');
            }

            // 3. Obtener lista de Ciudadanos (roleId = 1)
            $responseCitizens = Http::withHeaders($this->getAuthHeaders())
                ->get($this->apiBaseUrl . '/users/role/1'); // Rol Ciudadano

            if ($responseCitizens->successful() && isset($responseCitizens->json()['data'])) {
                $citizens = $responseCitizens->json()['data'];
            } else {
                 Log::error('Error al obtener ciudadanos de la API', [
                    'status' => $responseCitizens->status(),
                    'body' => $responseCitizens->body()
                 ]);
                $error = ($error ? $error . ' ' : '') . 'No se pudieron cargar los ciudadanos. ' . ($responseCitizens->json()['message'] ?? '');
            }

        } catch (\Exception $e) {
            Log::error('Excepción al contactar la API de asignaciones/usuarios: ' . $e->getMessage());
            $error = 'Ocurrió un error inesperado al comunicarse con el servicio. Por favor, inténtalo más tarde.';
        }

        // Pasar los datos a la vista
        return view('modules.assign.asignacion_promotores', [
            'assignments' => $assignments,
            'promoters' => $promoters,
            'citizens' => $citizens,
            'apiError' => $error // Pasar el mensaje de error a la vista si existe
        ]);
    }

    /**
     * Almacena una nueva asignación creada a través del modal.
     */
    public function store(Request $request)
    {
        // 1. Validar los datos del formulario
        $validated = $request->validate([
            'promoter_id' => 'required|integer',
            'user_id' => 'required|integer',
        ]);

        // 2. Preparar el payload para la API
        $payload = [
            'promoter' => [
                'userId' => (int)$validated['promoter_id']
            ],
            'user' => [
                'userId' => (int)$validated['user_id']
            ]
        ];

        try {
            // 3. Realizar la petición POST a la API
            $response = Http::withHeaders($this->getAuthHeaders())
                ->post($this->apiBaseUrl . '/promoterAssignments/create', $payload);

            // 4. Verificar la respuesta de la API
            if ($response->successful()) {
                // Éxito: Redirigir con mensaje de éxito
                return redirect()->route('assignments.index')
                                 ->with('success', $response->json()['message'] ?? 'Asignación creada con éxito.');
            } else {
                // Error: Redirigir con mensaje de error de la API
                $errorMessage = $response->json()['message'] ?? 'Error desconocido al crear la asignación.';
                Log::error('Error API al crear asignación', [
                    'status' => $response->status(),
                    'body' => $response->body(),
                    'payload' => $payload // Loguea lo que se envió
                 ]);
                return redirect()->route('assignments.index')
                                 ->with('error', 'Error al crear la asignación: ' . $errorMessage);
            }

        } catch (\Illuminate\Http\Client\ConnectionException $e) {
             Log::error('Error de conexión al crear asignación API: ' . $e->getMessage());
             return redirect()->route('assignments.index')
                              ->with('error', 'No se pudo conectar con el servicio para crear la asignación. Intenta más tarde.');
        } catch (\Exception $e) {
            Log::error('Excepción al crear asignación API: ' . $e->getMessage());
             return redirect()->route('assignments.index')
                              ->with('error', 'Ocurrió un error inesperado al crear la asignación.');
        }
    }

     /**
      * Elimina una asignación específica.
      * (Necesitarás una API endpoint para DELETE, ej: /promoterAssignments/delete/{id})
      */
     public function destroy($id)
     {
         // Asumiendo que tienes un endpoint DELETE /promoterAssignments/delete/{assignmentId}
         $apiUrl = $this->apiBaseUrl . '/promoterAssignments/delete/' . $id; // Ajusta si la URL es diferente

         try {
             $response = Http::withHeaders($this->getAuthHeaders())->delete($apiUrl);

             if ($response->successful()) {
                 return redirect()->route('assignments.index')
                                  ->with('success', $response->json()['message'] ?? 'Asignación eliminada con éxito.');
             } else {
                 $errorMessage = $response->json()['message'] ?? 'Error desconocido al eliminar.';
                 Log::error('Error API al eliminar asignación', [
                    'id' => $id,
                    'status' => $response->status(),
                    'body' => $response->body()
                 ]);
                 return redirect()->route('assignments.index')
                                  ->with('error', 'Error al eliminar la asignación: ' . $errorMessage);
             }
         } catch (\Illuminate\Http\Client\ConnectionException $e) {
             Log::error('Error de conexión al eliminar asignación API: ' . $e->getMessage());
             return redirect()->route('assignments.index')
                              ->with('error', 'No se pudo conectar con el servicio para eliminar la asignación.');
         } catch (\Exception $e) {
             Log::error('Excepción al eliminar asignación API: ' . $e->getMessage());
             return redirect()->route('assignments.index')
                              ->with('error', 'Ocurrió un error inesperado al eliminar la asignación.');
         }
     }

}
