<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class RoleMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Closure(\Illuminate\Http\Request): (\Symfony\Component\HttpFoundation\Response)  $next
     */
    public function handle(Request $request, Closure $next, $role)
    {
        $user = session('user');

        if (!$user || strtoupper($user['role']['name'] ?? '') !== strtoupper($role)) {
            session(['dash_error' => 'Acceso denegado. Ese no era tu dashboard.']);
            return redirect()->route('dashboard.redirect');
        }

        return $next($request);
    }
}
