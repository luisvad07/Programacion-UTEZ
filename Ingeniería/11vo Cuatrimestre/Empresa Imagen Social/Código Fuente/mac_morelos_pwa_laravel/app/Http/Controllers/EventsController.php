<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

class EventsController extends Controller
{
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/events';

    private function getAuthHeaders()
    {
        // Asume que el token de autenticación se guarda en la sesión.
        // Ajusta esto si usas otro método de autenticación (ej: cookies, headers)
        return [
            'Authorization' => 'Bearer ' . session('auth_token'),
            'Content-Type' => 'application/json',
        ];
    }

    public function index()
    {
        $events = $this->getAllEvents();

        //dd($mapColorimetries);

        if ($events === null) {
            // Manejar el error, por ejemplo, redirigir con un mensaje de error
            return redirect()->route('dashboard.redirect')->with('error', 'No se pudieron cargar los datos de eventos.');
        }

        return view('modules.events.eventos', compact('events'));
    }

    private function getAllEvents()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/getAll");

            //dd($response->status(), $response->body());

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener los eventos: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener los eventos: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    public function show($id)
    {
        $event = $this->getEventById($id);

        if (!$event) {
            return redirect()->route('events.index')->with('error', 'Evento no encontrado');
        }

        return view('modules.events.eventos', compact('event'));
    }

    private function getEventById($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener el evento con ID ' . $id . ': ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Error al obtener el evento con ID ' . $id . ': ' . $e->getMessage());
            return null;
        }
    }

    // Crear un evento
    public function create(Request $request)
    {
        // Validate the incoming data (add more validation rules as needed)
        $validatedData = $request->validate([
            'name' => 'required|string|max:255',
            'description' => 'required|string',
            'startDate' => 'required|date',
            'endDate' => 'required|date',
            'location' => 'required|string|max:255',
            'street' => 'required|string|max:255',
            'number' => 'required|string|max:20',
            'interiorNumber' => 'nullable|string|max:20',
            'neighborhood' => 'required|string|max:255',
            'zipCode' => 'required|string|max:10',
            'city' => 'required|string|max:255',
            'state' => 'required|string|max:255',
            'country' => 'required|string|max:255',
            'latitude' => 'nullable|numeric',
            'longitude' => 'nullable|numeric',
        ]);

        // Build the request body for your API
        $apiData = [
            'name' => $validatedData['name'],
            'description' => $validatedData['description'],
            'startDate' => $validatedData['startDate'],  // Make sure this is in the correct format!
            'endDate' => $validatedData['endDate'],      // Make sure this is in the correct format!
            'address' => [
                'street' => $validatedData['street'],
                'number' => $validatedData['number'],
                'interiorNumber' => $validatedData['interiorNumber'],
                'neighborhood' => $validatedData['neighborhood'],
                'zipCode' => $validatedData['zipCode'],
                'city' => $validatedData['city'],
                'state' => $validatedData['state'],
                'country' => $validatedData['country'],
                'latitude' => $validatedData['latitude'],
                'longitude' => $validatedData['longitude'],
            ],
            'location' => $validatedData['location'],
        ];

        // Send the data to your API
        try {
            $response = Http::withHeaders($this->getAuthHeaders())
                ->post("{$this->apiBaseUrl}/createEvent", $apiData);

            if ($response->successful()) {
                return redirect()->route('events')->with('success', 'Evento creado exitosamente');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error desconocido';

                return redirect()->route('events')->with('error', 'Error al crear el evento: ' . $errorMessage);
            }
        } catch (\Exception $e) {
            return redirect()->route('events')->with('error', 'Error al crear el evento: ' . $e->getMessage());
        }
    }

    // Actualizar un evento
    public function update(Request $request, $id)
    {
        // Validar datos
        $data = $request->all();

        $eventData = [
            'name' => $data['name'],
            'description' => $data['description'],
            'startDate' => $data['startDate'],
            'endDate' => $data['endDate'],
            'location' => $data['location'],
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
                'latitude' => $data['latitude'],
                'longitude' => $data['longitude'],
            ] : null,
            'statusEvent' => $data['statusEvent']
        ];    

        $response = Http::withHeaders($this->getAuthHeaders())->put("{$this->apiBaseUrl}/{$id}", $eventData);

        if ($response->successful()) {
            return redirect()->route('events')->with('success', 'Evento actualizado exitosamente');
        } else {
            $errorMessage = $response->json()['message'] ?? 'Error desconocido';

            return redirect()->route('events')->with('error', 'Error al actualizar el evento: ' . $errorMessage);
        }
    }

    // Eliminar un evento
    public function delete($id)
    {
        $response = Http::withHeaders($this->getAuthHeaders())->delete("{$this->apiBaseUrl}/{$id}");

        if ($response->successful()) {
            return redirect()->route('events')->with('success', 'Evento eliminado exitosamente');
        } else {
            return redirect()->route('events')->with('error', 'Error al eliminar el evento');
        }
    }

    // Cambiar el estado del evento
    public function changeStatus($id)
    {
        $statusEvent = request('statusEvent');

        $response = Http::withHeaders($this->getAuthHeaders())
                    ->patch("{$this->apiBaseUrl}/{$id}/status", [
                        'statusEvent' => $statusEvent
                    ]);

        if ($response->successful()) {
            return redirect()->route('events')->with('success', 'Estado del evento actualizado exitosamente');
        }

        $errorMessage = 'Error al cambiar el estado del evento'; // Mensaje por defecto
        if ($response->status() == 400 || $response->status() == 403 || $response->status() == 404) {
            $errorMessage = $response->json()['message'] ?? $errorMessage;
        }

        // Redirigir con el mensaje de error
        return redirect()->route('events')->with('error', $errorMessage);
    }

}
