<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error 403 - No Autorizado</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body class="h-screen flex items-center justify-center body-error">

    <div class="container text-center p-8 rounded-lg shadow-xl bg-white max-w-lg">
        <i class="fas fa-ban fa-5x text-red-500 icon-shaking mb-4"></i>
        <h1 class="text-4xl font-bold text-gray-700 mb-2">Error 403 - No Autorizado</h1>
        <p class="text-gray-600 mb-6">Lo sentimos, no tienes permiso para acceder a esta página.</p>

        <div class="loading-bar mb-4"></div>

        <p class="text-gray-600 text-sm">Redirigiendo a la página de inicio en <span id="countdown">5</span> segundos...</p>

        <a href="/" class="inline-block mt-4 py-2 px-4 rounded-md bg-green-800 text-white hover:bg-green-900 focus:outline-none focus:ring-2 focus:ring-green-500">
            Volver al Inicio
        </a>
    </div>

    <script>
        let countdown = 5;
        const countdownElement = document.getElementById('countdown');

        const redirect = () => {
            window.location.href = '/login'; // Redirigiendo al login
        };

        const updateCountdown = () => {
            countdown--;
            countdownElement.textContent = countdown;

            if (countdown <= 0) {
                redirect();
            } else {
                setTimeout(updateCountdown, 1000);
            }
        };

        setTimeout(updateCountdown, 1000); // Iniciar el conteo regresivo despues de 1 segundo
    </script>
</body>
</html>