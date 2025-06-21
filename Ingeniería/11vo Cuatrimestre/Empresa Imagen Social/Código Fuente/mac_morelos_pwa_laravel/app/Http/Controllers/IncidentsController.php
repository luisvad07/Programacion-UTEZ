<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

class IncidentsController extends Controller
{
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/incidents';
    private $apiBaseCreateUrl = 'http://localhost:8081/mac-morelos-api/users';

    private function getAuthHeaders()
    {
        // Asume que el token de autenticación se guarda en la sesión.
        // Ajusta esto si usas otro método de autenticación (ej: cookies, headers)
        return [
            'Authorization' => 'Bearer ' . session('auth_token'),
            'Content-Type' => 'application/json',
        ];

        Log::info('Token de sesión: ' . session('auth_token'));

    }

    public function index()
    {
        $incidents = $this->getAllIncidents();

        if ($incidents === null) {
            // Manejar el error, por ejemplo, redirigir con un mensaje de error
            return redirect()->route('dashboard.redirect')->with('error', 'No se pudieron cargar los datos de incidencias.');
        }

        return view('modules.incidents.incidentes', compact('incidents'));  // Ajusta la vista según corresponda
    }

    // Método privado refactorizado para uso interno
    private function getIncidentsByAssignedToInternal($userId)
    {
         $apiData = [
             'userId' => $userId,
         ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->get("{$this->apiBaseUrl}/assigned-to", $apiData); // Asume que la API lee el body en GET

            if ($response->successful()) {
                $data = $response->json();
                // Verifica si la estructura esperada 'data' existe
                 if (isset($data['data']) && is_array($data['data'])) {
                    Log::info("Incidencias obtenidas para userId {$userId}: " . count($data['data']));
                    return $data['data'];
                } else {
                     Log::warning("La respuesta de la API para getIncidentsByAssignedToInternal no contiene la clave 'data' esperada o no es un array. Respuesta: " . $response->body());
                     return [];
                 }
            } else {
                Log::error("Error al obtener las incidencias asignadas a userId {$userId}: " . $response->status() . " - " . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error("Excepción al obtener las incidencias asignadas a userId {$userId}: " . $e->getMessage());
            return null; // Indica un error
        }
    }

    private function getAllIncidents()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/getAll");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener las incidencias: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las incidencias: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    public function show($id)
    {
        $incident = $this->getIncidentById($id);

        if (!$incident) {
            return redirect()->route('incidents')->with('error', 'Incidencia no encontrada');
        }

        return view('modules.incidents.incident', compact('incident')); // Ajusta la vista según corresponda
    }

    private function getIncidentById($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener la incidencia con ID ' . $id . ': ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Error al obtener la incidencia con ID ' . $id . ': ' . $e->getMessage());
            return null;
        }
    }

    public function createForm()
    {
        // Obtener el token de la sesión
        $token = session('auth_token');

        if (!$token) {
            // Si no hay token, redirige al usuario a la página de inicio de sesión
            return redirect()->route('login');
        }

        try {
            // Hacer la solicitud para obtener el perfil del usuario con el token de autenticación
            $response = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token // Agregar el token a las cabeceras
            ])->get('http://localhost:8081/mac-morelos-api/users/getProfile');

            if ($response->successful()) {
                // Obtener los datos del usuario de la respuesta de la API
                $userProfile = $response->json(); 

                Log::info('Perfil del usuario: ' . json_encode($userProfile));

                // Obtener la lista de administradores como en tu código original
                $adminsResponse = Http::withHeaders([
                    'Authorization' => 'Bearer ' . $token
                ])->get("{$this->apiBaseCreateUrl}/role/3"); // Obtener administradores

                if ($adminsResponse->successful()) {
                    $admins = $adminsResponse->json(); // Lista de administradores
                    return view('modules.incidents.crear_incidencia', compact('admins', 'userProfile'));
                } else {
                    $errorMessage = $adminsResponse->json()['message'] ?? 'Error al obtener la lista de administradores.';
                    return redirect()->route('incidents.create.form')->with('error', $errorMessage);
                }
            } else {
                return redirect()->route('login')->with('error', 'No se pudo obtener el perfil del usuario.');
            }
        } catch (\Exception $e) {
            return redirect()->route('incidents.create.form')->with('error', 'Error al conectar con la API: ' . $e->getMessage());
        }
    }


     // Crear un incidente
    public function create(Request $request)
    {

        // Validate the incoming data (add more validation rules as needed)
        $validatedData = $request->validate([
            'promoter' => ['required', 'array'],
            'promoter.userId' => ['required', 'integer'],
            'assignedTo' => ['required', 'array'],
            'assignedTo.userId' => ['required', 'integer'],
            'incidentName' => 'required|string|max:255',
            'description' => 'required|string',
            'address' => 'required|array',
            'address.street' => 'required|string|max:255',
            'address.number' => 'required|string|max:20',
            'address.interiorNumber' => 'nullable|string|max:20',
            'address.neighborhood' => 'required|string|max:255',
            'address.zipCode' => 'required|string|max:10',
            'address.city' => 'required|string|max:255',
            'address.state' => 'required|string|max:255',
            'address.country' => 'required|string|max:255',
            'address.latitude' => 'nullable|numeric',
            'address.longitude' => 'nullable|numeric',
        ]);


        // Build the request body for your API
        $apiData = [
            'promoter' => ['userId' => $validatedData['promoter']['userId']], 
            'assignedTo' => ['userId' => $validatedData['assignedTo']['userId']],
            'incidentName' => $validatedData['incidentName'],
            'description' => $validatedData['description'],
            'address' => [
                'street' => $validatedData['address']['street'],
                'number' => $validatedData['address']['number'],
                'interiorNumber' => $validatedData['address']['interiorNumber'],
                'neighborhood' => $validatedData['address']['neighborhood'],
                'zipCode' => $validatedData['address']['zipCode'],
                'city' => $validatedData['address']['city'],
                'state' => $validatedData['address']['state'],
                'country' => $validatedData['address']['country'],
                'latitude' => $validatedData['address']['latitude'],
                'longitude' => $validatedData['address']['longitude'],
            ],
        ];

        // Send the data to your API
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->post("{$this->apiBaseUrl}/createIncident", $apiData);

                Log::info("Respuesta completa de la API: " . json_encode($response->json()));

            if ($response->successful()) {
                return redirect()->route('incidents.create.form')->with('success', 'Incidencia creada exitosamente');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error desconocido';

                return redirect()->route('incidents.create.form')->with('error', 'Error al crear la incidencia: ' . $errorMessage);
            }
        } catch (\Exception $e) {
            return redirect()->route('incidents.create.form')->with('error', 'Error al crear la incidencia: ' . $e->getMessage());
        } catch (\Illuminate\Validation\ValidationException $e) {
            Log::error("Errores de validación: " . json_encode($e->errors()));
        }
    }

    // Actualizar SOLO el estado de un incidente (para el modal de estado)
    public function updateStatus(Request $request, $id)
    {
        $validatedData = $request->validate([
            'statusIncident' => 'required|string|in:PENDIENTE,EN_PROGRESO,RESUELTO,NO_RESUELTO,CANCELADO',
        ]);

        $apiData = [
            'statusIncident' => $validatedData['statusIncident'],
        ];

        Log::info("Intentando actualizar estado de incidencia ID {$id} a {$validatedData['statusIncident']}");

        try {
            // Usamos PUT como en tu código original, asumiendo que la API lo soporta
            $response = Http::withHeaders($this->getAuthHeaders())
                            ->put("{$this->apiBaseUrl}/{$id}", $apiData);

            Log::info("Respuesta de API " . $response->status() . " - " . $response->body());

            if ($response->successful()) {
                // Redirige a la lista principal
                return redirect()->route('incidents')->with('success', 'Estado de la incidencia actualizado exitosamente.');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error desconocido al actualizar estado.';
                Log::error("Error API al actualizar estado de incidencia ID {$id}: " . $errorMessage);
                // Redirige de vuelta a la lista con error
                return redirect()->route('incidents')->with('error', 'Error al actualizar estado: ' . $errorMessage);
            }
        } catch (\Exception $e) {
             Log::error("Excepción al actualizar estado de incidencia ID {$id}: " . $e->getMessage());
            return redirect()->route('incidents')->with('error', 'Error de conexión al actualizar estado: ' . $e->getMessage());
        }
    }

    // Eliminar un incidente
    public function delete($id)
    {
        Log::info("Intentando eliminar incidencia ID {$id}");
        try {

            $response = Http::withHeaders($this->getAuthHeaders())->delete("{$this->apiBaseUrl}/delete/{$id}"); // O la URL que use tu API

             Log::info("Respuesta de API /delete/{$id}: " . $response->status() . " - " . $response->body());

            if ($response->successful() || $response->status() == 204) { // 204 No Content también es éxito para DELETE
                return redirect()->route('incidents')->with('success', 'Incidencia eliminada exitosamente.');
            } else {
                 $errorMessage = $response->json()['message'] ?? 'Error desconocido al eliminar.';
                 Log::error("Error API al eliminar incidencia ID {$id}: " . $errorMessage);
                return redirect()->route('incidents')->with('error', 'Error al eliminar la incidencia: ' . $errorMessage);
            }
        } catch (\Exception $e) {
             Log::error("Excepción al eliminar incidencia ID {$id}: " . $e->getMessage());
            return redirect()->route('incidents')->with('error', 'Error de conexión al eliminar la incidencia: ' . $e->getMessage());
        }
    }

    // Ejemplo de método público que usa el interno
    public function getIncidentsByPromoter(Request $request)
    {
        $validatedData = $request->validate(['userId' => 'required|integer']);
        $incidents = $this->getIncidentsByPromoterInternal($validatedData['userId']);
        // Decidir cómo devolver la respuesta (JSON, vista, etc.)
        return response()->json(['data' => $incidents]); // Ejemplo de respuesta JSON
    }

    // Método interno refactorizado
    private function getIncidentsByPromoterInternal($userId) {
         $apiData = ['userId' => $userId];
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/promoter", $apiData);
            return $response->successful() ? $response->json()['data'] : null;
        } catch (\Exception $e) {
            Log::error('Excepción getIncidentsByPromoterInternal: ' . $e->getMessage());
            return null;
        }
    }

     // Método público que usa el interno (si tienes una ruta para esto)
    public function getIncidentsByAssignedTo(Request $request)
    {
         $validatedData = $request->validate(['userId' => 'required|integer']);
         // Llama al método interno ya existente y usado por index()
         $incidents = $this->getIncidentsByAssignedToInternal($validatedData['userId']);
         // Devuelve la respuesta adecuada para la ruta (probablemente JSON)
         if ($incidents !== null) {
            return response()->json(['data' => $incidents]);
        } else {
            return response()->json(['error' => 'No se pudieron obtener las incidencias'], 500); // O 404 si es apropiado
        }
    }

     // Buscar incidentes por estado - Método interno
    private function getIncidentsByStatusInternal($status)
    {
        $apiData = ['statusIncident' => $status];
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/status", $apiData);
            return $response->successful() ? $response->json()['data'] : null;
        } catch (\Exception $e) {
            Log::error("Excepción getIncidentsByStatusInternal: " . $e->getMessage());
            return null;
        }
    }

    // Método público para la ruta (si existe)
    public function getIncidentsByStatus(Request $request)
    {
        $validatedData = $request->validate([
            'statusIncident' => 'required|string|in:PENDIENTE,EN_PROGRESO,RESUELTO,CERRADO',
        ]);
        $incidents = $this->getIncidentsByStatusInternal($validatedData['statusIncident']);
        return response()->json(['data' => $incidents]); // O manejar el error
    }


    // Buscar incidentes creados antes de una fecha específica
    public function getIncidentsByCreatedAtBefore(Request $request)
    {
        $validatedData = $request->validate([
            'createdAt' => 'required|date',
        ]);

        $apiData = [
            'createdAt' => $validatedData['createdAt'],
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->get("{$this->apiBaseUrl}/created-before", $apiData);

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener las incidencias: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las incidencias: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    // Buscar incidentes resueltos después de una fecha específica
    public function getIncidentsByResolvedAtAfter(Request $request)
    {
        $validatedData = $request->validate([
            'resolvedAt' => 'required|date',
        ]);

        $apiData = [
            'resolvedAt' => $validatedData['resolvedAt'],
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->get("{$this->apiBaseUrl}/resolved-after", $apiData);

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener las incidencias: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las incidencias: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    // Buscar incidentes por promotor y estado
    public function getIncidentsByPromoterAndStatus(Request $request)
    {
        $validatedData = $request->validate([
            'promoter.userId' => 'required|integer',
            'statusIncident' => 'required|string|in:PENDIENTE,EN_PROGRESO,RESUELTO,CERRADO',  // Ajusta los valores permitidos según tu API
        ]);

        $apiData = [
            'promoter' => ['userId' => $validatedData['promoter.userId']],
            'statusIncident' => $validatedData['statusIncident'],
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->get("{$this->apiBaseUrl}/promoter-status", $apiData);

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener las incidencias: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las incidencias: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

     // Verificar si existe un incidente asignado a un usuario y con un estado específico
    public function existsIncidentByAssignedToAndStatus(Request $request)
    {
        $validatedData = $request->validate([
            'assignedTo.userId' => 'required|integer',
            'statusIncident' => 'required|string|in:PENDIENTE,EN_PROGRESO,RESUELTO,CERRADO',  // Ajusta los valores permitidos según tu API
        ]);

        $apiData = [
            'assignedTo' => ['userId' => $validatedData['assignedTo.userId']],
            'statusIncident' => $validatedData['statusIncident'],
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->get("{$this->apiBaseUrl}/exists-assigned-status", $apiData);

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener las incidencias: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener las incidencias: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

}