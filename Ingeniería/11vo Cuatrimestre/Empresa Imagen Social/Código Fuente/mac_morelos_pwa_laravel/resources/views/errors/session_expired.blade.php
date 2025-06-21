<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sesión Expirada</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        .icon-spin {
            animation: spin 2s linear infinite;
        }

        @keyframes spin {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }
    </style>
</head>
<body class="h-screen flex items-center justify-center body-error">

    <div class="container text-center p-8 rounded-lg shadow-xl bg-white max-w-lg">
        <i class="fas fa-clock fa-5x text-yellow-500 icon-spin mb-4"></i>
        <h1 class="text-4xl font-bold text-gray-700 mb-2">Sesión Expirada</h1>
        <p class="text-gray-600 mb-6">Su sesión ha expirado. Por favor, vuelva a iniciar sesión.</p>

        <div class="loading-bar mb-4"></div>

        <p class="text-gray-600 text-sm">Redirigiendo a la página de inicio de sesión en <span id="countdown">5</span> segundos...</p>

        <a href="/login" class="inline-block mt-4 py-2 px-4 rounded-md bg-green-800 text-white hover:bg-green-900 focus:outline-none focus:ring-2 focus:ring-green-500">
            Iniciar Sesión
        </a>
    </div>

    <script>
        let countdown = 5;
        const countdownElement = document.getElementById('countdown');

        const redirect = () => {
            window.location.href = '/login';
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