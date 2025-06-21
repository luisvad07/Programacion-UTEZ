<?php

use App\Http\Controllers\AssingController;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\AuthController;
use App\Http\Controllers\EventsController;
use App\Http\Controllers\IncidentsController;
use App\Http\Controllers\UsersController;
use App\Http\Controllers\MapColorimetryController;
use App\Http\Controllers\CategoriesController;
use App\Http\Controllers\PublicController;
use App\Http\Controllers\NewsController;
use Illuminate\Support\Facades\Log;

/************************************ GESTION DE INICIO DE SESIÓN ******************************************************/

//Pantalla de Error
Route::fallback(function () {
    return response()->view('errors.404', [], 404);
});

Route::get('/noticias-diarias', [PublicController::class, 'showNews'])->name('public.news');

// Redirigir la raíz a /login
Route::get('/', function () {
    return redirect()->route('public.news');  // Redirige al login
})->name('home');

Route::get('/login', function () {
    return view('auth.login');
})->name('login');

Route::get('/login', [AuthController::class, 'showLoginForm'])->name('login.form');

Route::post('/login', [AuthController::class, 'login'])->name('login');
Route::post('/logout', [AuthController::class, 'logout'])->name('logout');

Route::get('/register', function () {
    return view('auth.register');
})->name('register.form');

Route::post('/register', [AuthController::class, 'register'])->name('register');

Route::get('/forgot-password', [AuthController::class, 'showRequestResetPasswordForm'])->name('password.request.form');
Route::post('/forgot-password', [AuthController::class, 'requestPasswordReset'])->name('password.request');
Route::get('/reset-password/{token}', [AuthController::class, 'showResetPasswordForm'])->name('password.reset.form');
Route::post('/password/reset', [AuthController::class, 'resetPassword'])->name('password.reset');

Route::get('/session-expired', function () {
    return view('errors.session_expired');
})->name('session.expired');

// Rutas Protegidas por Middleware
Route::middleware(['unauthorized', 'check.session'])->group(function () {
    Route::get('/profile', function () {
        return view('layouts/profile_user');
    })->name('profile');

    Route::post('/profile/update-picture', [AuthController::class, 'updateProfilePicture'])->name('profile.updatePicture');
    Route::get('/profile', [AuthController::class, 'showProfile'])->name('profile');
    Route::put('/profile/update', [AuthController::class, 'updateProfile'])->name('profile.update');
    Route::get('/profile-nav', [AuthController::class, 'showProfileNavbar'])->name('profile-navbar');

    Route::get('/map-colorimetries', [MapColorimetryController::class, 'index'])->name('map-colorimetries.index');
    Route::post('/map-colorimetries/load-initial', [MapColorimetryController::class, 'loadInitial'])->name('map-colorimetries.load-initial');
    Route::get('/map-colorimetries/municipality/{municipality}', [MapColorimetryController::class, 'show'])->name('map-colorimetries.show');

    Route::get('/events', [EventsController::class, 'index'])->name('events');
    Route::post('/events/create', [EventsController::class, 'create'])->name('events.create');
    Route::get('/events/{id}', [EventsController::class, 'showUser'])->name('events.show');
    Route::put('/events/update/{id}', [EventsController::class, 'update'])->name('events.update');
    Route::patch('/events/change-status/{id}', [EventsController::class, 'changeStatus'])->name('events.change-status');
    Route::delete('/events/delete/{id}', [EventsController::class, 'delete'])->name('events.delete');

    Route::get('/dashboard', function () {
        $user = session('user');

        if (!$user || !isset($user['role']['name'])) {
            return redirect()->route('login')->withErrors(['error' => 'No se encontró información del usuario o rol.']);
        }

        // Extraer el rol correctamente
        $role = strtoupper($user['role']['name']); 

        switch ($role) {
            case 'ADMINISTRADOR':
                return redirect()->route('dashboard.admin');
            case 'PROMOTOR':
                return redirect()->route('dashboard.promotor');
            case 'SUPERVISOR':
                return redirect()->route('dashboard.supervisor');
            case 'PERIODISTA':
                return redirect()->route('dashboard.periodista');
            default:
                return redirect()->route('login')->withErrors(['error' => 'Rol no autorizado.']);
        }
    })->name('dashboard.redirect');

    /************************************ GESTIÓN DE ADMINISTRADOR ******************************************************/
    Route::middleware(['role:ADMINISTRADOR'])->group(function () {
        Route::get('/dashboard/admin', function () {
            return view('src.dashboardAdmin');
        })->name('dashboard.admin');
    });

    Route::middleware(['admin'])->group(function () {

        Route::get('/dashboard/admin', function () {
            return view('src.dashboardAdmin');
        })->name('dashboard.admin');

        Route::get('/users/busqueda-avanzada', [UsersController::class, 'busquedaAvanzada'])->name('users.busqueda_avanzada');
        Route::get('/users/busqueda-rol-estado', [UsersController::class, 'busquedaAvanzadaRolEstado'])->name('users.busqueda_rol_estado');
        Route::post('/users/resultados-busqueda', [UsersController::class, 'resultadosBusqueda'])->name('users.resultados_busqueda');

        Route::get('/asignaciones', [AssingController::class, 'index'])->name('assignments.index');
        Route::post('/asignaciones', [AssingController::class, 'store'])->name('assignments.store');
        Route::delete('/asignaciones/{id}', [AssingController::class, 'destroy'])->name('assignments.destroy');

        Route::get('/users', [UsersController::class, 'index'])->name('users');
        Route::get('/users/{id}', [UsersController::class, 'showUser'])->name('users.show');
        Route::put('/users/update/{id}', [UsersController::class, 'updateUser'])->name('users.update');
        Route::patch('/users/change-status/{id}', [UsersController::class, 'changeStatus'])->name('users.change-status');
        Route::delete('/users/delete/{id}', [UsersController::class, 'delete'])->name('users.delete');

        // Rutas para los métodos de búsqueda
        Route::get('/incidents', [IncidentsController::class, 'index'])->name('incidents');
        Route::put('/incidents/status/{id}', [IncidentsController::class, 'updateStatus'])->name('update.status');
        Route::delete('/incidents/{id}', [IncidentsController::class, 'delete'])->name('incidents.delete');
        // Route::get('/incidents/promoter', [IncidentsController::class, 'getIncidentsByPromoter'])->name('incidents.promoter');
        // Route::get('/incidents/assigned-to', [IncidentsController::class, 'getIncidentsByAssignedTo'])->name('incidents.assigned-to');
        // Route::get('/incidents/status', [IncidentsController::class, 'getIncidentsByStatus'])->name('incidents.status');
        // Route::get('/incidents/created-before', [IncidentsController::class, 'getIncidentsByCreatedAtBefore'])->name('incidents.created-before');
        // Route::get('/incidents/resolved-after', [IncidentsController::class, 'getIncidentsByResolvedAtAfter'])->name('incidents.resolved-after');
        // Route::get('/incidents/promoter-status', [IncidentsController::class, 'getIncidentsByPromoterAndStatus'])->name('incidents.promoter-status');
        // Route::get('/incidents/exists-assigned-status', [IncidentsController::class, 'existsIncidentByAssignedToAndStatus'])->name('incidents.exists-assigned-status');

        Route::get('/categories', [CategoriesController::class, 'index'])->name('categories.index');
        Route::post('/categories/create', [CategoriesController::class, 'create'])->name('categories.create');
        Route::put('/categories/update/{id}', [CategoriesController::class, 'update'])->name('categories.update');
        Route::delete('/categories/delete/{id}', [CategoriesController::class, 'delete'])->name('categories.delete');
    });

    /************************************ GESTIÓN DE PROMOTOR ******************************************************/

    Route::middleware(['promotor'])->group(function () {

        Route::get('/dashboard/promotor', function () {
            return view('src.dashboardPromotor');
        })->name('dashboard.promotor');

        Route::get('/incidents/create', [IncidentsController::class, 'createForm'])->name('incidents.create.form');
        Route::post('/incidents/create', [IncidentsController::class, 'create'])->name('incidents.create');
    });

    /************************************ GESTIÓN DE SUPERVISOR ******************************************************/
    Route::middleware(['role:SUPERVISOR'])->group(function () {
        Route::get('/dashboard/supervisor', function () {
            return view('src.dashboardSupervisor');
        })->name('dashboard.supervisor');
    });

    /************************************ GESTIÓN DE PERIODISTA ******************************************************/

    Route::middleware(['role:PERIODISTA'])->group(function () {
        Route::get('/dashboard/periodista', function () {
            return view('src.dashboardPeriodista');
        })->name('dashboard.periodista');

        Route::get('/noticias', [NewsController::class, 'index'])->name('news.index');
        Route::get('/news/{id}', [NewsController::class, 'show'])->name('news.show'); 
        Route::post('/createNoticias', [NewsController::class, 'create'])->name('news.create'); 
        Route::put('/news/{id}', [NewsController::class, 'update'])->name('news.update');
        Route::delete('/{id}', [NewsController::class, 'delete'])->name('news.delete');
        Route::post('/news/{id}/upload-image', [NewsController::class, 'uploadImage'])->name('news.uploadImage');
        Route::get('/publicar', [NewsController::class, 'publishIndex'])->name('publish.index');
        Route::put('/news/{id}/publicarAhora', [NewsController::class, 'publishNow'])->name('publish.now');
        Route::put('/news/{id}/programar', [NewsController::class, 'schedule'])->name('publish.schedule');
    });
});
