<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Cookie;
use GuzzleHttp\Client;
use Illuminate\Support\Facades\Validator;
use Firebase\JWT\JWT;

class AuthController extends Controller
{
    public function showLoginForm()
    {
        return view('auth.login');
    }

    public function register(Request $request)
    {
        // Validar los datos de entrada
        $request->validate([
            'firstName' => 'required|string',
            'lastName' => 'required|string',
            'email' => 'required|email',
            'phone' => 'required|string',
            'password' => 'required|string|min:6',
            'rfc' => 'nullable|string',
            'curp' => 'nullable|string',
            'birthdate' => 'required|date',
            'street' => 'required|string',
            'number' => 'required|string',
            'interiorNumber' => 'nullable|string',
            'neighborhood' => 'required|string',
            'zipCode' => 'required|string',
            'city' => 'required|string',
            'state' => 'required|string',
            'country' => 'required|string',
            'latitude' => 'required|numeric',
            'longitude' => 'required|numeric',
            'imgProfile' => 'nullable|string',
            'username' => 'nullable|string',
            'role' => 'required|in:PROMOTOR,ADMINISTRADOR,SUPERVISOR,PERIODISTA', // Validar el rol
        ]);

        // Determinar el roleId basado en el role seleccionado
        $roleId = match ($request->input('role')) {
            'PROMOTOR' => 2,  // Reemplaza con los IDs correctos de tu API
            'ADMINISTRADOR' => 3, // Reemplaza con los IDs correctos de tu API
            'SUPERVISOR' => 4, // Reemplaza con los IDs correctos de tu API
            'PERIODISTA' => 5, // Reemplaza con los IDs correctos de tu API
            default => 0, // Manejar un caso inesperado
        };

        // Construir el payload de la solicitud
        $userData = [
            "role" => [
                "roleId" => $roleId,
                "name" => $request->input('role'),
            ],
            "firstName" => $request->input('firstName'),
            "lastName" => $request->input('lastName'),
            "email" => $request->input('email'),
            "phone" => $request->input('phone'),
            "password" => $request->input('password'),
            "rfc" => $request->input('rfc'),
            "curp" => $request->input('curp'),
            "birthdate" => $request->input('birthdate'),
            "address" => [
                "street" => $request->input('street'),
                "number" => $request->input('number'),
                "interiorNumber" => $request->input('interiorNumber'),
                "neighborhood" => $request->input('neighborhood'),
                "zipCode" => $request->input('zipCode'),
                "city" => $request->input('city'),
                "state" => $request->input('state'),
                "country" => $request->input('country'),
                "latitude" => $request->input('latitude', 0),
                "longitude" => $request->input('longitude', 0),
            ],
            "status" => true,
            "imgProfile" => $request->input('imgProfile'),
        ];

        try {
            // Realizar la solicitud HTTP a la API externa
            $response = Http::withHeaders([
                'Content-Type' => 'application/json',
            ])->post('http://localhost:8081/mac-morelos-api/users/create', $userData);

            $data = $response->json();

            if (!$response->successful() || ($data['error'] ?? true)) {
                return back()->withErrors(['error' => $data['message'] ?? 'Error al registrar el usuario.']);
            }

            return redirect()->route('login')->with('register_success', 'Usuario registrado exitosamente, ya puedes iniciar sesión.');
        } catch (\Exception $e) {
            return back()->withErrors(['error' => 'Ocurrió un error al comunicarse con la API.']);
        }
    }

    public function login(Request $request)
    {
        $request->validate([
            'identifier' => 'required|string',
            'password' => 'required|string',
        ]);

        try {

            // Hacer la solicitud de inicio de sesión a la API
            $response = Http::withHeaders([
                'Content-Type' => 'application/json',
            ])->timeout(5)->post('http://localhost:8081/mac-morelos-api/auth/login', [
                'identifier' => $request->input('identifier'),
                'password' => $request->input('password')
            ]);

            if (!$response->successful()) {
                $data = $response->json();
                return back()->withErrors(['error' => $data['message'] ?? 'Error al iniciar sesión.']);
            }

            $data = $response->json();

            // Si la API devuelve error, mostramos el mensaje correcto en la vista
            if (!$response->successful()) {
                $errorMessage = $data['message'] ?? 'Error en la API.';
                return back()->withErrors(['error' => $errorMessage]);
            }

            // Si la API responde correctamente, extraemos el token
            if (!isset($data['data']['token'])) {
                return back()->withErrors(['error' => 'Error al obtener el token de autenticación.']);
            }

            //Extraer el token y el rol del usuario
            $token = $data['data']['token'];
            $role = $data['data']['role'] ?? null;

            if (!$role) {
                return back()->withErrors(['error' => 'No se pudo obtener el rol del usuario.']);
            }

            // Obtener perfil con el token
            $profileResponse = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token
            ])->get('http://localhost:8081/mac-morelos-api/users/getProfile');

            if (!$profileResponse->successful()) {
                return back()->withErrors(['error' => 'No se pudo obtener el perfil del usuario.']);
            }

            $profileData = $profileResponse->json();

            if ($profileResponse->successful() && isset($profileData['data'])) {
                $user = $profileData['data'];
                session([
                    'auth_token' => $token,
                    'user' => $profileData['data'],
                ]);

                // Almacenar el mensaje de bienvenida en la sesión para mostrarlo en la vista
                session(['login_message' => 'Bienvenido a MAC Morelos, ' . $user['firstName'] . ' ' . $user['lastName']]);

                // Redirigir según el rol
                switch (strtoupper($role)) {
                    case 'ADMINISTRADOR':
                        return redirect()->route('dashboard.admin');
                    case 'PROMOTOR':
                        return redirect()->route('dashboard.promotor');
                    case 'SUPERVISOR':
                        return redirect()->route('dashboard.supervisor');
                    case 'PERIODISTA':
                        return redirect()->route('dashboard.periodista');
                    default:
                        return back()->withErrors(['error' => 'Rol no autorizado: '. $role]);
                }
            }
            
        } catch (\Exception $e) {
            return back()->withErrors(['error' => 'Verifica la conexión a los servicios e intenta de nuevo.']);
        }
    }

    public function logout(Request $request)
    {
        // Eliminar variables específicas de sesión
        session()->forget(['auth_token', 'user']);

        // Opcional: Invalidar toda la sesión
        //session()->invalidate();
        session()->regenerateToken(); // Evita ataques CSRF

        // // Establecer la cookie con Laravel
        // Cookie::queue('logoutManual', 'true', 1);

        // // Redirigir a la pantalla de sesión expirada si es inactividad
        // if ($request->query('expired')) {
        //     return redirect()->route('session.expired');
        // }
        return redirect()->route('login')->with('message', 'Sesión cerrada exitosamente.');
    }

    public function updateProfilePicture(Request $request)
    {
        // Validar la entrada del archivo
        $request->validate([
            'profile_image' => 'required|image|mimes:jpeg,png,jpg|max:2048', // Asegúrate de que el campo coincida con el del formulario
        ]);

        // Obtener el token desde la sesión
        $token = session('auth_token');
        
        if (!$token) {
            return back()->withErrors(['error' => 'El token de autenticación es requerido.']);
        }

        // Obtener el perfil del usuario usando el token
        try {
            $profileResponse = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token
            ])->get('http://localhost:8081/mac-morelos-api/users/getProfile');

            // Depuración de la respuesta del perfil
            //dd($profileResponse->status(), $profileResponse->json());

            if (!$profileResponse->successful()) {
                return back()->withErrors(['error' => 'No se pudo obtener el perfil del usuario.']);
            }

            // Obtener los datos del perfil
            $profileData = $profileResponse->json();
            $userId = $profileData['data']['userId'];  // Aquí tomamos el userId del perfil obtenido

            // Obtener el archivo desde la solicitud
            $file = $request->file('profile_image');

            if (!$file) {
                return back()->withErrors(['error' => 'No se cargó ninguna imagen.']);
            }

            // Verificar los datos que se enviarán
            //dd($file->getClientOriginalName(), $file->getSize(), file_get_contents($file));

            // Enviar la imagen al API externa
            $response = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token
            ])
            ->attach('file', file_get_contents($file), $file->getClientOriginalName())
            ->post("http://localhost:8081/mac-morelos-api/users/{$userId}/profile-picture");

            // Depuración de la respuesta
            //dd($response->status(), $response->json());

            // Verificar si la respuesta fue exitosa
            if ($response->successful()) {
                return back()->with('success', 'Imagen de perfil actualizada correctamente.');
            } else {
                $data = $response->json();
                return back()->withErrors(['error' => $data['message'] ?? 'Error al actualizar la imagen de perfil.']);
            }

        } catch (\Exception $e) {
            // Capturar cualquier excepción y devolver un mensaje de error
            //dd($e->getMessage(), $e->getTrace());
            return back()->withErrors(['error' => 'Ocurrió un error al comunicarse con la API.']);
        }
    }

    public function showProfile()
    {
        $token = session('auth_token');
        $profileImageUrl = asset('assets/default.png');
        $user = null;

        if (!$token) {
            return redirect()->route('profile')->with('error', 'Token inválido. Inicia sesión de nuevo.');
        }

        if ($token) {
            try {
                $client = new Client();
                $response = $client->get('http://localhost:8081/mac-morelos-api/users/getProfile', [
                    'headers' => [
                        'Authorization' => 'Bearer ' . $token
                    ]
                ]);

                $userData = json_decode($response->getBody()->getContents(), true);
                $user = $userData['data'];

                if (isset($user['imgProfile']) && !empty($user['imgProfile'])) {
                    $profileImageUrl = 'http://localhost:8081/mac-morelos-api/users' . $user['imgProfile'];
                }

            } catch (\Exception $e) {
                Log::error('Error obteniendo perfil: ' . $e->getMessage());
                return redirect()->route('home')->with('error', 'No se pudo cargar el perfil.');
            }
        } else {
            return redirect()->route('login')->with('error', 'Token inválido.');
        }

        return view('layouts.profile_user', compact('profileImageUrl', 'user'));
    }

    public function updateProfile(Request $request)
    {
        $token = session('auth_token');

        if (!$token) {
            return redirect()->route('profile')->with('error', 'Token no encontrado.');
        }

        $userId = session('user.userId');
        $role = session('user.role');
        $previousEmail = session('user.email'); // Guardamos el email anterior
        $newEmail = $request->filled('email') ? $request->input('email') : $previousEmail;

        // Obtener datos desde el request, pero manteniendo los valores originales si están vacíos
        $firstName = $request->filled('firstName') ? $request->input('firstName') : session('user.firstName');
        $lastName = $request->filled('lastName') ? $request->input('lastName') : session('user.lastName');
        $phone = $request->filled('phone') ? $request->input('phone') : session('user.phone');
        $rfc = $request->filled('rfc') ? $request->input('rfc') : session('user.rfc');
        $curp = $request->filled('curp') ? $request->input('curp') : session('user.curp');
        $birthdate = $request->filled('birthdate') ? now()->parse($request->input('birthdate'))->toISOString() : session('user.birthdate');

        // Dirección
        $address = [
            'addressId' => session('user.address.addressId') ?? null,
            'street' => $request->filled('address.street') ? $request->input('address.street') : session('user.address.street'),
            'number' => $request->filled('address.number') ? $request->input('address.number') : session('user.address.number'),
            'neighborhood' => $request->filled('address.neighborhood') ? $request->input('address.neighborhood') : session('user.address.neighborhood'),
            'zipCode' => $request->filled('address.zipCode') ? $request->input('address.zipCode') : session('user.address.zipCode'),
            'city' => $request->filled('address.city') ? $request->input('address.city') : session('user.address.city'),
            'state' => $request->filled('address.state') ? $request->input('address.state') : session('user.address.state'),
            'country' => $request->filled('address.country') ? $request->input('address.country') : session('user.address.country'),
            'latitude' => $request->filled('address.latitude') ? (float)$request->input('address.latitude') : session('user.address.latitude'),
            'longitude' => $request->filled('address.longitude') ? (float)$request->input('address.longitude') : session('user.address.longitude'),
        ];

        // Construir payload para la API
        $payload = [
            'userId' => $userId,
            'role' => $role,
            'firstName' => $firstName,
            'lastName' => $lastName,
            'email' => $newEmail,
            'phone' => $phone,
            'rfc' => $rfc,
            'curp' => $curp,
            'birthdate' => $birthdate,
            'address' => $address
        ];    

        Log::info('Payload enviado a la API: ', $payload);
        Log::info('Token enviado: ', ['token' => $token]);

        try {
            $response = Http::withHeaders([
                'Authorization' => 'Bearer ' . $token,
                'Content-Type' => 'application/json',
            ])->put("http://localhost:8081/mac-morelos-api/users/updateProfile", $payload);
    
            $body = $response->body();
            $data = json_decode($body, true);

            if ($response->getStatusCode() == 200) {
                // Verificar si el email fue cambiado
                if ($newEmail !== $previousEmail) {
                    
                    session()->flush();
                    return redirect()->route('login')->with('email_success', 'Perfil actualizado. Inicia sesión con tu nuevo correo.');
                } 

                return redirect()->route('profile')->with('success', 'Perfil actualizado correctamente.');
            } else {
                Log::error('API Error: ' . $body);
                return redirect()->route('profile')
                    ->with('error', 'Error al actualizar el perfil: ' . ($data['message'] ?? 'Error desconocido'));
            }

        } catch (\Exception $e) {
            Log::error('Exception: ' . $e->getMessage());
            return redirect()->route('profile')
                ->with('error', 'Error al actualizar el perfil: ' . $e->getMessage());
        }
    }

    public function showRequestResetPasswordForm()
    {
        return view('auth.forgot-password');
    }

    public function requestPasswordReset(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
        ]);

        try {
            $email = $request->input('email');

            $response = Http::withHeaders([
                'Content-Type' => 'application/json',
            ])->post('http://localhost:8081/mac-morelos-api/auth/password-reset/request', [
                'email' => $email,
            ]);

            $data = $response->json();
            $statusCode = $response->status(); // Código de estado HTTP

            // DEPURACIÓN: Muestra la respuesta antes de continuar
            Log::info('Respuesta de la API:', $data); 

            // Verifica si el estado HTTP es exitoso y si `error` es false
            if ($statusCode === 200 && isset($data['error']) && $data['error'] === false) {

                return response()->json([
                    'success' => true,
                    'message' => $data['message'] ?? 'Correo enviado con éxito.',
                ]);
            } else {
                return response()->json([
                    'success' => false,
                    'message' => $data['message'] ?? 'Error al solicitar el restablecimiento de contraseña.',
                ]);
            }
        } catch (\Exception $e) {
            Log::error('Error en la solicitud de restablecimiento: ' . $e->getMessage());
            return response()->json([
                'success' => false,
                'message' => 'Ocurrió un error al comunicarse con la API.',
            ]);
        }
    }

    public function showResetPasswordForm($token)
    {
        if (empty($token)) {
            // Redirigir al login con un mensaje de error o mostrar un mensaje en la vista
            return redirect()->route('login')->with('error', 'No se ha proporcionado un token válido.');
        }

        // Decodificar el token para pasarlo a la vista
        $decodedToken = base64_decode($token);

        // Solicitar la validación del token
        $response = Http::withHeaders([
            'Content-Type' => 'application/json',
        ])->post('http://localhost:8081/mac-morelos-api/auth/validate-reset-token', [
            'token' => $decodedToken,
        ]);

        $data = $response->json();
        $statusCode = $response->status();

        Log::info('Validación de token: ', ['status' => $statusCode, 'response' => $data]);

        // Verificar si el token es válido o ha expirado
        if ($statusCode !== 200 || (isset($data['error']) && $data['error'])) {
            // Redirigir al login con un mensaje de error
            Log::info('Token inválido o expirado', ['token' => $token]);
            return redirect()->route('login')->with('error', 'El token no es válido o ha expirado. Vuelve a solicitar el restablecimiento de contraseña.');
        }

        return view('auth.reset-password-form', ['token' => $decodedToken]);
    }

    // Función para cambiar la contraseña usando el token
    public function resetPassword(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'token' => 'required',
            'password' => 'required|min:8|confirmed', // Añade 'confirmed' para la validación de confirmación
        ]);

        if ($validator->fails()) {
            return redirect()->back()->withErrors($validator)->withInput(); // Redirige con errores y datos
        }

        try {

            // Decodificar el token utilizando JWT si es un JWT y no base64
            $secret = base64_decode(env('JWT_SECRET')); 
            $decodedToken = JWT::decode($request->token, new \Firebase\JWT\Key($secret, 'HS256'));
            Log::info(env('JWT_SECRET'));

            // Verifica si el token contiene los datos necesarios, como el email del usuario
            $email = $decodedToken->sub;

            // Si necesitas hacer algo más con los datos del token:
            Log::info('Token decodificado: ', (array)$decodedToken); 

            $response = Http::withHeaders([
                'Content-Type' => 'application/json',
            ])->post('http://localhost:8081/mac-morelos-api/auth/password-reset', [
                'token' => $request->token,
                'newPassword' => $request->password,
            ]);

            $data = $response->json();
            $statusCode = $response->status(); 

            Log::info('Respuesta de la API en resetPassword:', $data);

            if ($statusCode === 200 && isset($data['error']) && $data['error'] === false) {

                return response()->json([
                    'success' => true,
                    'message' => $data['message'] ?? 'Contraseña cambiada con éxito.',
                ]);
            } else {
                return response()->json([
                    'success' => false,
                    'message' => $data['message'] ?? 'Error al cambiar la contraseña.',
                ]);
            }
        } catch (\Exception $e) {
            Log::error('Error en el cambio de contraseña: ' . $e->getMessage());
            return response()->json([
                'success' => false,
                'message' => 'Ocurrió un error al comunicarse con la API.',
            ]);
        }
    }

}
