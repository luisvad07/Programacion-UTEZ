<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Log;
use Symfony\Component\HttpFoundation\Response;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;

class CheckSession
{
    public function handle(Request $request, Closure $next)
    {
        if (!session()->has('auth_token')) {
            Log::warning('Sesión expirada, redirigiendo...');
            return redirect()->route('session.expired');
        }
    
        try {
            $token = session('auth_token');
    
            // Extraer el payload del JWT
            $tokenParts = explode(".", $token);
            if (count($tokenParts) < 2) {
                throw new \Exception("Token inválido, no tiene el formato adecuado");
            }
    
            // Decodificar el payload (parte 1 del JWT) sin verificar la firma
            $payload = json_decode(base64_decode($tokenParts[1]), true);
    
            // Validar si el token ha expirado
            if (isset($payload['exp']) && $payload['exp'] < time()) {
                Log::warning('Token expirado. Redirigiendo...');
                session()->forget('auth_token'); // Limpiar la sesión
                return redirect()->route('session.expired');
            }
    
        } catch (\Exception $e) {
            Log::warning('Error al procesar el token: ' . $e->getMessage());
            session()->forget('auth_token');
            return redirect()->route('session.expired');
        }
    
        return $next($request);
    }
}
