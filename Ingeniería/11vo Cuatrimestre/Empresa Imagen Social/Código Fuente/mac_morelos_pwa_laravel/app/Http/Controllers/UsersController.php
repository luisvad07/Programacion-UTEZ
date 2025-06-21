<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Carbon\Carbon;

class UsersController extends Controller
{
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/users';

    private function getAuthHeaders()
    {
        return [
            'Authorization' => 'Bearer ' . session('auth_token'),
            'Content-Type' => 'application/json',
        ];
    }

    public function index(Request $request)
    {
        $users = $this->getAllUsers();
        $notFoundMessage = null;
        $searchTerm = null;  // No search term needed
        $filterType = null; //No Filter type needed

        //dd($users);

        return view('modules.users.usuarios', compact('users', 'notFoundMessage', 'searchTerm', 'filterType'));
    }

    private function getAllUsers()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/getAll");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                return response()->json(['api_error' => 'No se pudo obtener los usuarios'], 500);
                return [];
            }
        } catch (\Exception $e) {
            return response()->json(['api_error' => 'No se pudo conectar a los servicios'], 500);
        }
    }

    // Nuevo método para obtener un usuario por ID
    public function showUser($id)
    {

        // Obtener el usuario por su ID
        $user = $this->getUserById($id);

        if (!$user) {
            // Si el usuario no se encuentra, puedes redirigir con un mensaje
            return redirect()->route('users')->with('error', 'Usuario no encontrado');
        }

        return view('modules.users.usuarios', compact('user'));
    }

    private function getUserById($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                return response()->json(['api_error' => 'No se pudo obtener el usuario'], 500);
            }
        } catch (\Exception $e) {
            return response()->json(['api_error' => 'No se pudo conectar a los servicios'], 500);
        }
    }

    public function updateUser(Request $request, $id)
    {
        $data = $request->all();

        // Construir el cuerpo de la petición para que coincida con Postman
        $userData = [
            'userId' => (int) $id, // Asegurarse de que sea un número entero
            'role' => [
                'roleId' => $data['roleId']// Si roleId es dinámico, obténlo correctamente
            ],
            'firstName' => $data['firstName'],
            'lastName' => $data['lastName'],
            'email' => $data['email'],
            'phone' => $data['phone'],
            //'password' => isset($data['password']) ? $data['password'] : null, // Enviar si es requerido
            'rfc' => $data['rfc'],
            'curp' => $data['curp'],
            'birthdate' => $data['birthdate'],
            'username' => $data['username'] ?? null, // Puede ser null

            // Enviar address como null si no hay dirección, para coincidir con Postman
            'address' => !empty($data['street']) ? [
                'addressId' => $data['addressId'],
                'street' => $data['street'],
                'number' => $data['number'],
                'interiorNumber' => $data['interiorNumber'],
                'neighborhood' => $data['neighborhood'],
                'zipCode' => $data['zipCode'],
                'city' => $data['city'],
                'state' => $data['state'],
                'country' => $data['country'],
            ] : null,

            'status' => $data['status'] ?? true, // Enviar status como true si no está definido
        ];

        // Log para depuración
        //Log::info('Datos enviados a la API:', $userData);

        // Realizar la petición PUT a la API
        $response = Http::withHeaders([
            'Content-Type' => 'application/json',
            'Accept' => 'application/json'
        ])->put("{$this->apiBaseUrl}/update/{$id}", $userData);

        //Log::info("URL de la API: {$this->apiBaseUrl}/users/update/{$id}");
        //Log::error('Respuesta de la API: ' . $response->body());

        // Manejar la respuesta de la API
        if ($response->successful()) {
            return redirect()->route('users')->with('success', 'Usuario actualizado correctamente');
        } else {
            //Log::error('Error al actualizar el usuario: ' . $response->body());
            return redirect()->route('users')->with('error', 'Error al actualizar el usuario');
        }
    }

    // Cambiar estado de usuario
    public function changeStatus($id)
    {
        // Verificar si el usuario en sesión es el mismo que el que se está intentando eliminar
        if (session('user')['userId'] == $id) {
            return redirect()->route('users')->with('error', 'No puedes cambiar el estado de tu propio usuario.');
        }

        $response = Http::withHeaders($this->getAuthHeaders())
                        ->patch("{$this->apiBaseUrl}/change-status/{$id}");

        if ($response->successful()) {
            return redirect()->route('users')->with('success', 'Estado del usuario actualizado correctamente');
        } else {
            return redirect()->route('users')->with('error', 'Hubo un error al cambiar el estado del usuario');
        }
    }

    // Eliminar usuario
    public function delete($id)
    {
        // Verificar si el usuario en sesión es el mismo que el que se está intentando cambiar
        if (session('user')['userId'] == $id) {
            return redirect()->route('users')->with('error', 'No puedes eliminar tu propio usuario.');
        }

        $response = Http::withHeaders($this->getAuthHeaders())
                        ->delete("{$this->apiBaseUrl}/delete/{$id}");
    
        if ($response->successful()) {
            return redirect()->route('users')->with('success', 'Usuario eliminado correctamente');
        } else {
            return redirect()->route('users')->with('error', 'Hubo un error al eliminar el usuario');
        }
        
    }

    public function busquedaAvanzada()
    {
        //dd("Cargando vista de búsqueda avanzada");
        return view('modules.users.busqueda_avanzada');
    }

    public function busquedaAvanzadaRolEstado()
    {
        return view('modules.users.busqueda_rol_estado');
    }

    public function resultadosBusqueda(Request $request)
    {
        // Validar la entrada
        $request->validate([
            'filter_type' => 'required|string',
            'search_term' => 'required|string',
        ]);

        $filterType = $request->input('filter_type');
        $searchTerm = $request->input('search_term');
        $results = [];
        $notFoundMessage = null;

        try {
            // Definir la URL base con headers
            $client = Http::withHeaders($this->getAuthHeaders());

            switch ($filterType) {
                case 'role':
                    $response = $client->get("{$this->apiBaseUrl}/role/{$searchTerm}");
                    break;
                case 'status':
                    $status = filter_var($searchTerm, FILTER_VALIDATE_BOOLEAN);
                    $response = $client->get("{$this->apiBaseUrl}/status/{$status}");
                    break;
                case 'email':
                    $response = $client->get("{$this->apiBaseUrl}/email/{$searchTerm}");
                    break;
                case 'curp':
                    $response = $client->get("{$this->apiBaseUrl}/curp/{$searchTerm}");
                    break;
                case 'rfc':
                    $response = $client->get("{$this->apiBaseUrl}/rfc/{$searchTerm}");
                    break;
                default:
                    return back()->with('error', 'Tipo de filtro inválido.');
            }

            if ($response->successful()) {
                $data = $response->json();
                $results = isset($data['data']) ? (is_array($data['data']) ? $data['data'] : [$data['data']]) : [];
                if (empty($results)) {
                    $notFoundMessage = 'No se encontraron resultados para su búsqueda.';
                }
            } else {
                $notFoundMessage = 'Error al obtener los resultados de la API.';
                Log::error("API Error ({$response->status()}): " . $response->body());
            }
        } catch (\Exception $e) {
            $notFoundMessage = 'Error de conexión con la API.';
            Log::error('API Connection Error: ' . $e->getMessage());
        }

        return response()->json([
            'results' => $results,
            'message' => $notFoundMessage ?? 'Resultados obtenidos con éxito',
            'searchTerm' => $searchTerm,
            'filterType' => $filterType
        ]);        
    }

}