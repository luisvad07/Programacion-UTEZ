<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Carbon\Carbon;

class NewsController extends Controller
{
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/news';
    private $apiBaseUrlCategories = 'http://localhost:8081/mac-morelos-api/categories';
    private $apiBaseUrlUsers = 'http://localhost:8081/mac-morelos-api/users';

    /**
     * Obtiene las cabeceras de autenticación.
     *
     * @return array
     */
    private function getAuthHeaders()
    {
        $token = session('auth_token');
        if (!$token) {
            Log::warning('Intento de acceso a API sin token de autenticación.');
            return ['Content-Type' => 'application/json'];
        }
        return [
            'Authorization' => 'Bearer ' . $token,
            'Content-Type' => 'application/json',
            'Accept' => 'application/json',
        ];
    }

    private function getAuthHeadersImage()
    {
        $token = session('auth_token');
        if (!$token) {
            Log::warning('Intento de acceso a API sin token de autenticación.');
            return ['Content-Type' => 'application/json'];
        }
        return [
            'Authorization' => 'Bearer ' . $token,
            'Accept' => 'application/json',
        ];
    }

    /**
     * Obtiene el perfil del usuario actual desde la API.
     *
     * @return array|null El perfil del usuario o null si falla.
     */
    private function getUserProfile() {
        $headers = $this->getAuthHeaders();
        if (!$headers) {
            Log::error('Error al obtener perfil: No hay token de autenticación.');
            return null; // No hay token
        }

        try {
            $response = Http::withHeaders($headers)->get("{$this->apiBaseUrlUsers}/getProfile");

            if ($response->successful()) {
                // Asumiendo que la API devuelve el perfil directamente o dentro de una clave 'data'
                $profileData = $response->json()['data'] ?? $response->json();
                Log::info('Perfil de usuario obtenido: ', $profileData);
                // Asegúrate de que el perfil contenga 'userId'
                if (!isset($profileData['userId'])) {
                    Log::error('El perfil de usuario obtenido no contiene "userId". Respuesta API: ' . $response->body());
                    return null;
                }
                return $profileData;
            } else {
                Log::error('Error al obtener perfil de usuario desde API: ' . $response->status() . ' - ' . $response->body());
                return null; // Falló la llamada API
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener perfil de usuario: ' . $e->getMessage());
            return null; // Excepción de conexión u otra
        }
    }

    /**
     * Muestra la lista de noticias.
     *
     * @return \Illuminate\View\View|\Illuminate\Http\RedirectResponse
     */
    public function index()
    {
        $news = $this->getAllNews(); // Podrías filtrar por estado 'BORRADOR' si la API lo permite
        //dd($news);
        $categories = $this->getAllCategories(); // Necesario para mostrar nombres de categoría en la tabla

        if ($news === null) {
            return redirect()->route('news.index')->with('error', 'No se pudieron cargar las noticias.');
            $news = [];
        }
        
        if ($categories === null) {
            return redirect()->route('news.index')->with('error', 'No se pudieron cargar las categorías.');
            $categories = []; // Evita error en la vista si es null
        }

         // Mapear categoryId a categoryName para la tabla
         $categoryMap = collect($categories)->pluck('name', 'categoryId')->all();


        return view('modules.news.noticias', compact('news', 'categoryMap'));
    }

    /**
     * Obtiene todas las noticias desde la API.
     *
     * @return array|null
     */
    private function getAllNews()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/getAll");

            if ($response->successful()) {
                return $response->json()['data'] ?? [];
            } else {
                Log::error('Error al obtener noticias: ' . $response->status() . ' - ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener noticias: ' . $e->getMessage());
            return null;
        }
    }

    /**
     * Obtiene todas las categorías desde la API.
     * @return array|null
     */
    private function getAllCategories()
    {
        $headers = $this->getAuthHeaders();
        if (!$headers) return null; // Salir si no hay token

        try {
            $response = Http::withHeaders($headers)->get("{$this->apiBaseUrlCategories}/getAll");

            if ($response->successful()) {
                return $response->json()['data'] ?? [];
            } else {
                Log::error('Error al obtener categorías: ' . $response->status() . ' - ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener categorías: ' . $e->getMessage());
            return null;
        }
    }

    /**
     * Muestra una noticia específica.
     *
     * @param  int  $id
     * @return \Illuminate\View\View|\Illuminate\Http\RedirectResponse
     */
    public function show($id)
    {
        $newsItem = $this->getNewsById($id);

        if ($newsItem === null) {
            return redirect()->route('news.index')->with('error', 'Noticia no encontrada.');
        }

        return view('modules.news.noticias', compact('newsItem'));
    }

    /**
     * Obtiene una noticia por ID desde la API.
     *
     * @param  int  $id
     * @return array|null
     */
    private function getNewsById($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return $response->json()['data'] ?? null;
            } elseif ($response->status() == 404) {
                Log::warning('Noticia no encontrada en API con ID ' . $id);
                return null;
            } else {
                Log::error('Error al obtener noticia (ID: ' . $id . '): ' . $response->status() . ' - ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener noticia (ID: ' . $id . '): ' . $e->getMessage());
            return null;
        }
    }

    /**
     * Crea una nueva noticia.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\RedirectResponse
     */
    public function create(Request $request)
    {

        // 1. Obtener perfil del usuario
        $userProfile = $this->getUserProfile();
        if (!$userProfile || !isset($userProfile['userId'])) {
             // Usar back() para mantener los datos del formulario
            return back()->withInput()->with('error', 'No se pudo verificar la identidad del usuario para crear la noticia.');
        }
        $userId = $userProfile['userId'];

        $validatedData = $request->validate([
            'title' => 'required|string|max:255',
            'content' => 'required|string',
            'category_id' => 'required|integer',
            'address_street' => 'required|string|max:255',
            'address_number' => 'nullable|string|max:50', // Puede ser nulo o no requerido
            'address_interiorNumber' => 'nullable|string|max:50',
            'address_neighborhood' => 'required|string|max:100',
            'address_zipCode' => 'required|string|max:10',
            'address_city' => 'required|string|max:100',
            'address_state' => 'required|string|max:100',
            'address_country' => 'required|string|max:100',
            'address_latitude' => ['required', 'numeric', 'regex:/^[-]?(([0-8]?[0-9])\.(\d+))|(90(\.0+)?)$/'], // Regex para latitud válida
            'address_longitude' => ['required', 'numeric', 'regex:/^[-]?((((1[0-7][0-9])|([0-9]?[0-9]))\.(\d+))|180(\.0+)?)$/'], // Regex para longitud válida
        ], [
            // Mensajes de error personalizados si lo deseas
            'address_street.required' => 'El campo calle es obligatorio.',
            'address_latitude.required' => 'La latitud es obligatoria (marca un punto en el mapa).',
            'address_longitude.required' => 'La longitud es obligatoria (marca un punto en el mapa).',
            'address_latitude.numeric' => 'La latitud debe ser un número.',
            'address_longitude.numeric' => 'La longitud debe ser un número.',
            'address_latitude.regex' => 'El formato de la latitud no es válido.',
            'address_longitude.regex' => 'El formato de la longitud no es válido.',
        ]);

        $payload = [
            'title' => $validatedData['title'],
            'content' => $validatedData['content'],
            'statusNews' => 'BORRADOR', // Hardcoded para creación
            'createdBy' => [
                'userId' => $userId
            ],
            'address' => [
                'street' => $validatedData['address_street'],
                'number' => $validatedData['address_number'] ?? null, // Usa null si no viene
                'interiorNumber' => $validatedData['address_interiorNumber'] ?? null,
                'neighborhood' => $validatedData['address_neighborhood'],
                'zipCode' => $validatedData['address_zipCode'],
                'city' => $validatedData['address_city'],
                'state' => $validatedData['address_state'],
                'country' => $validatedData['address_country'],
                // Convertir a float explícitamente
                'latitude' => (float) $validatedData['address_latitude'],
                'longitude' => (float) $validatedData['address_longitude'],
            ],
            'categories' => [
                [
                    'categoryId' => (int) $validatedData['category_id']
                ]
            ]
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())->post("{$this->apiBaseUrl}/createNews", $payload);

            if ($response->successful()) {
                return redirect()->route('news.index')->with('success', 'Noticia creada correctamente.');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error al crear la noticia.';
                Log::error('Error al crear noticia: ' . $response->status() . ' - ' . $response->body());
                return redirect()->route('news.index')->with('error', $errorMessage);
            }
        } catch (\Exception $e) {
            Log::error('Excepción al crear noticia: ' . $e->getMessage());
            return redirect()->route('news.index')->with('error', 'Error de conexión al crear la noticia.');
        }
    }

    /**
     * Actualiza una noticia existente.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\RedirectResponse
     */
    public function update(Request $request, $id)
    {
        $validatedData = $request->validate([
            'title' => 'required|string|max:255',
            'content' => 'required|string',
            'category_id' => 'required|integer',
            'address_street' => 'required|string|max:255',
            'address_number' => 'nullable|string|max:50',
            'address_interiorNumber' => 'nullable|string|max:50',
            'address_neighborhood' => 'required|string|max:100',
            'address_zipCode' => 'required|string|max:10',
            'address_city' => 'required|string|max:100',
            'address_state' => 'required|string|max:100',
            'address_country' => 'required|string|max:100',
            'address_latitude' => ['required', 'numeric', 'regex:/^[-]?(([0-8]?[0-9])\.(\d+))|(90(\.0+)?)$/'],
            'address_longitude' => ['required', 'numeric', 'regex:/^[-]?((((1[0-7][0-9])|([0-9]?[0-9]))\.(\d+))|180(\.0+)?)$/'],
        ]);

        $payload = [
            'title' => $validatedData['title'],
            'content' => $validatedData['content'],
            'address' => [
                'street' => $validatedData['address_street'],
                'number' => $validatedData['address_number'] ?? null,
                'interiorNumber' => $validatedData['address_interiorNumber'] ?? null,
                'neighborhood' => $validatedData['address_neighborhood'],
                'zipCode' => $validatedData['address_zipCode'],
                'city' => $validatedData['address_city'],
                'state' => $validatedData['address_state'],
                'country' => $validatedData['address_country'],
                'latitude' => (float) $validatedData['address_latitude'],
                'longitude' => (float) $validatedData['address_longitude'],
            ],
            'categories' => [
                [
                    'categoryId' => (int) $validatedData['category_id']
                ]
            ]
        ];

        try {
            $response = Http::withHeaders($this->getAuthHeaders())->put("{$this->apiBaseUrl}/{$id}", $payload);

            if ($response->successful()) {
                return redirect()->route('news.index')->with('success', 'Noticia actualizada correctamente.');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error al actualizar la noticia.';
                Log::error('Error al actualizar noticia (ID: '.$id.'): ' . $response->status() . ' - ' . $response->body());
                return redirect()->route('news.index')->with('error', $errorMessage);
            }
        } catch (\Exception $e) {
            Log::error('Excepción al actualizar noticia (ID: '.$id.'): ' . $e->getMessage());
            return redirect()->route('news.index')->with('error', 'Error de conexión al actualizar la noticia.');
        }
    }

    /**
     * Elimina una noticia.
     *
     * @param  int  $id
     * @return \Illuminate\Http\RedirectResponse
     */
    public function delete($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->delete("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return redirect()->route('news.index')->with('success', 'Noticia eliminada correctamente.');
            } else {
                $errorMessage = $response->json()['message'] ?? 'Error al eliminar la noticia.';
                if ($response->status() == 404) {
                    $errorMessage = 'Noticia no encontrada.';
                }
                Log::error('Error al eliminar noticia (ID: '.$id.'): ' . $response->status() . ' - ' . $response->body());
                return redirect()->route('news.index')->with('error', $errorMessage);
            }
        } catch (\Exception $e) {
            Log::error('Excepción al eliminar noticia (ID: '.$id.'): ' . $e->getMessage());
            return redirect()->route('news.index')->with('error', 'Error de conexión al eliminar la noticia.');
        }
    }

    /**
     * Sube imágenes a una noticia.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\RedirectResponse
     */
    public function uploadImage(Request $request, $id)
    {
        // Validar que venga una imagen
        $request->validate([
            'file' => 'required|image|max:2048', // 2MB
        ]);

        // Obtener token desde sesión
        $token = session('auth_token');

        if (!$token) {
            return back()->withErrors(['error' => 'El token de autenticación es requerido.']);
        }

        try {
            $file = $request->file('file');

            if (!$file) {
                return back()->withErrors(['error' => 'No se cargó ninguna imagen.']);
            }

            // Log: info de archivo y ruta
            Log::info("Subiendo imagen para noticia ID: {$id}", [
                'filename' => $file->getClientOriginalName(),
                'size_kb' => $file->getSize() / 1024,
            ]);

            // Enviar imagen al backend
            $response = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token,
                'Accept' => 'application/json',
            ])
            ->attach('file', file_get_contents($file), $file->getClientOriginalName())
            ->post("http://localhost:8081/mac-morelos-api/news/{$id}/upload-image");

            // Log URL destino
            Log::info("URL del endpoint de imagen:", [
                'url' => "http://localhost:8081/mac-morelos-api/news/{$id}/upload-image",
                'status' => $response->status()
            ]);

            if ($response->successful()) {
                return redirect()->route('news.index')->with('success', 'Imagen subida correctamente.');
            } else {
                $error = $response->json()['message'] ?? $response->body();
                return redirect()->route('news.index')->withErrors(['error' => 'Error al subir imagen: ' . $error]);
            }

        } catch (\Exception $e) {
            Log::error('Excepción al subir imagen: ' . $e->getMessage());
            return redirect()->route('news.index')->withErrors(['error' => 'Ocurrió un error al subir la imagen.']);
        }
    }


    /**
     * Muestra la vista de publicación con tarjetas de noticias (borradores).
     */
    public function publishIndex()
    {
        $allNews = $this->getAllNews(); // Obtiene TODAS las noticias
        $newsToShow = []; // Inicializa como array vacío

        if ($allNews === null) { // Comprueba si la llamada a la API falló o devolvió null
             Log::error('Error al obtener noticias desde la API en publishIndex. getAllNews devolvió null.');
             // Opción 1: Mostrar vista con mensaje de error
             $errorMessage = 'No se pudieron cargar las noticias desde la API.';
             $categoryMap = []; // Evitar error si $categories es null
             return view('modules.news.publish', compact('newsToShow', 'categoryMap', 'errorMessage'));

        }  elseif (empty($allNews)) { // Comprueba si la API devolvió un array vacío
            $newsToShow = []; // Ya está vacío, solo por claridad
            Log::info('No se encontraron noticias en la API.');
        } else {
            $newsToShow = $allNews;

            // Opcional: Ordenar las noticias (Ej: Borradores primero, luego Programadas, luego Publicadas, y dentro por fecha creación desc)
            usort($newsToShow, function($a, $b) {
                $statusOrder = ['BORRADOR' => 1, 'PROGRAMADO' => 2, 'PUBLICADO' => 3];

                // Manejar casos donde 'status' podría no existir o ser null
                $statusA = $a['status'] ?? 'BORRADOR'; // Asumir BORRADOR si falta status? O tratarlo diferente?
                $statusB = $b['status'] ?? 'BORRADOR';

                // Si el status es desconocido, trátalo como publicado (o como prefieras)
                $orderA = $statusOrder[$statusA] ?? 4;
                $orderB = $statusOrder[$statusB] ?? 4;

                if ($orderA !== $orderB) {
                    return $orderA <=> $orderB; // Ordenar por estado
                }

                // Si el estado es el mismo, ordenar por fecha de creación descendente (más nuevo primero)
                $dateA = $a['createdAt'] ?? 0;
                $dateB = $b['createdAt'] ?? 0;
                // Convertir a timestamp para comparación segura si son strings ISO8601
                 try {
                     $tsA = Carbon::parse($dateA)->timestamp;
                     $tsB = Carbon::parse($dateB)->timestamp;
                     return $tsB <=> $tsA; // Descendente (más nuevo primero)
                 } catch (\Exception $e) {
                     // Si las fechas no son parseables, no ordenar por fecha
                     return 0;
                 }
            });
        }

        // Obtener categorías (manejar posible null)
        $categories = $this->getAllCategories();
        $categoryMap = []; // Inicializar
        if ($categories) {
        $categoryMap = collect($categories)->pluck('name', 'categoryId')->all();
        } else {
        Log::warning('No se pudieron cargar las categorías en publishIndex.');
        // Puedes pasar un mensaje de error de categorías a la vista si es crítico
        }


        // Pasamos la variable con todas las noticias (ordenadas o no) a la vista
        return view('modules.news.publish', compact('newsToShow', 'categoryMap')); // No pasar 'errorMessage' si no ocurrió error
    }

    /**
     * Publica una noticia inmediatamente.
     */
    public function publishNow($id)
    {
        // Llamada a la API para actualizar el estado a 'PUBLICADO'
        return $this->updatePublicationStatus($id, Carbon::now()->toIso8601String(), 'PUBLICADO'); // Fecha/hora actual
    }

    /**
     * Programa la publicación de una noticia.
     */
    public function schedule(Request $request, $id)
    {
        $validated = $request->validate([
            'publish_date' => 'required|date|after_or_equal:now', // Fecha debe ser ahora o futura
        ],[
            'publish_date.required' => 'Debes seleccionar una fecha y hora para programar.',
            'publish_date.date' => 'El formato de fecha no es válido.',
            'publish_date.after_or_equal' => 'La fecha de programación no puede ser en el pasado.',
        ]);

        // Convierte la fecha a formato ISO 8601 UTC (o el que espere tu API)
        $publishDate = Carbon::parse($validated['publish_date'])->toIso8601String();

        // Llamada a la API para actualizar el estado y programar la publicación
        return $this->updatePublicationStatus($id, $publishDate, 'PROGRAMADO');
    }


    /**
     * Método helper para actualizar el estado y/o fecha de publicación vía API.
     */
    private function updatePublicationStatus($id, $publishDate, $status)
    {
        $headers = $this->getAuthHeaders();
        if (!$headers) {
            return redirect()->route('publish.index')->with('error', 'Error de autenticación.');
        }

        // Payload para la API - ajusta según lo que espere tu API
        $payloadPublish = [
            'publishDate' => $publishDate,
            'status' => $status, // 'PUBLICADO' o 'PROGRAMADO'
            // Puedes agregar otros campos si es necesario
        ];

        $payloadSchedule = [
            'scheduledDate' => $publishDate
        ];

        try {
            // Dependiendo del estado, hacemos el PUT a los endpoints correspondientes
            if ($status == 'PROGRAMADO') {
                // Si se va a programar, usamos el endpoint de programación
                $response = Http::withHeaders($headers)->put("{$this->apiBaseUrl}/{$id}/schedule", $payloadSchedule);

                Log::info('Respuesta de la API al programar noticia:', [
                    'status' => $response->status(),
                    'body' => $response->body(),
                    'payload_sent' => $payloadSchedule
                ]);

            } else {
                // Si se va a publicar, usamos el endpoint de actualización de estado
                $response = Http::withHeaders($headers)->put("{$this->apiBaseUrl}/{$id}/status?newStatus={$status}", $payloadPublish);

                Log::info('Respuesta de la API al publicar noticia:', [
                    'status' => $response->status(),
                    'body' => $response->body(),
                    'payload_sent' => $payloadPublish
                ]);
            }        

            // Verifica la respuesta de la API
            if ($response->successful()) {
                $message = optional($response->json())['message'] ?? 'Operación realizada con éxito.';
                return redirect()->route('publish.index')->with('success', $message);
            } else {
                $errorMessage = optional($response->json())['message'] ?? 'Error desconocido al actualizar estado de publicación.';
                return redirect()->route('publish.index')->with('error', 'Error API: ' . $errorMessage);
            }            
        } catch (\Exception $e) {
            Log::error('Excepción al publicar/programar noticia (ID: '.$id.'): ' . $e->getMessage());
            return redirect()->route('publish.index')->with('error', 'Error de conexión al actualizar estado.');
        }
    }

}
