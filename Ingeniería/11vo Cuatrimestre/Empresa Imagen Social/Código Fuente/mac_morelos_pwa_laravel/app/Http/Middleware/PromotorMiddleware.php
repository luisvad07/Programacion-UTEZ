<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;
use Illuminate\Support\Facades\Log;

class PromotorMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Closure(\Illuminate\Http\Request): (\Symfony\Component\HttpFoundation\Response)  $next
     */
    public function handle(Request $request, Closure $next): Response
    {
        //dd('Middleware Promotor ejecutado');  

        $user = session('user');

        //dd($user);

        // Verifica si el usuario está autenticado y si su rol es PROMOTOR
        $role = $user['role']['name'] ?? null;

        // Validar que el usuario está autenticado y es ADMINISTRADOR
        $role = is_array($user['role']) ? ($user['role']['name'] ?? '') : $user['role'];

        //dd($role);

        if (!$user || strtoupper($role) !== 'PROMOTOR') {
            session(['allow_error' => 'Acceso denegado. Solo promotores pueden acceder.']);
            dd(session()->all());
            return redirect()->route('dashboard.redirect');
        }

        //Log::info('Middleware Promotor ejecutado', ['user' => session('user')]);

        return $next($request);
    }
}
