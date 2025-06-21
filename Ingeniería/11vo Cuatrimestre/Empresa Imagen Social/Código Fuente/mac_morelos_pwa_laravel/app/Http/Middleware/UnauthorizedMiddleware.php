<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Cookie;

class UnauthorizedMiddleware
{
    public function handle(Request $request, Closure $next)
    {
        $user = session('user');
        $authToken = session('auth_token');
        $logoutManual = $request->cookie('logoutManual');

        // // Caso 1: Si el usuario cerró sesión manualmente, redirigir a la página de sesión expirada
        // if ($logoutManual === 'true') {
        //     // Eliminar la cookie (si la estás usando)
        //     Cookie::queue(Cookie::forget('logoutManual'));

        //     // Redirigir a la página de sesión expirada
        //     return redirect()->route('session.expired');
        // }

        // Caso 2: Si hay un token pero no hay usuario, la sesión expiró.
        if ($authToken && !$user) {
            return redirect()->route('session.expired');
        }

        // Caso 2: Si no hay usuario ni token (nunca inició sesión), mostrar error 403.
        if (!$authToken && !$user) {
            return response()->view('errors.unauthorized', [], 403);
        }

        return $next($request);
    }
}
