<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        //
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(): void
    {
        // Evitar que el usuario pueda volver a una página después del logout
        header('Cache-Control: no-store, no-cache, must-revalidate, max-age=0');
    }
}
