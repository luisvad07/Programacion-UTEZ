<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class AdminMiddleware
{
    public function handle(Request $request, Closure $next)
    {
        //dd('Middleware Admin ejecutado');  

        $user = session('user');

        // Validar que el usuario está autenticado y es ADMINISTRADOR
        $role = is_array($user['role']) ? ($user['role']['name'] ?? '') : $user['role'];

        if (!$user || strtoupper($role) !== 'ADMINISTRADOR') {
            session(['allow_error' => 'Acceso denegado. Solo administradores pueden acceder.']);
            //dd(session()->all());
            return redirect()->route('dashboard.redirect');
        }

        return $next($request);
    }
}