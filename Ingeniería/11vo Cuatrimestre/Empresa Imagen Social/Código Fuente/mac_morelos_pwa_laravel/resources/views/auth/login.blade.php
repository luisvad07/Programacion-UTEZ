<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SIMAC MORELOS</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <div class="flex h-screen w-full items-center justify-center bg-gray-900 bg-cover bg-no-repeat" style="background-image:var(--fondo-login)">
        <div class="relative">

            <div class="rounded-xl px-16 py-10 shadow-lg backdrop-blur-md max-sm:px-8 login-modal">
              <div class="text-white">
                <div class="mb-8 flex flex-col items-center">
                  <img id="logo-image" src="" width="150" alt="Logo" />
                  <h1 class="mb-2 text-2xl">SIMAC MORELOS</h1>
                  <span class="text-gray-300">Ingrese sus credenciales</span>
                </div>

                <form action="{{ route('login') }}" method="POST" id="login-form">
                  @csrf
                    <div class="mb-4 text-lg relative">
                        <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">email</span>
                        <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full login-input" type="email" name="identifier" placeholder="Ingresa tu Correo" />
                    </div>

                    <div class="mb-4 text-lg relative">
                        <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">lock</span>
                        <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full login-input" type="password" name="password" id="password" placeholder="Ingresa tu Contraseña" />
                        <span id="togglePassword" class="material-icons absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer">visibility_off</span>
                    </div>

                    <div class="mt-4 text-center">
                        <a href="{{ route('password.request.form') }}" class="text-gray-300 hover:text-white transition-colors duration-300 text-sm">
                            ¿Olvidaste la contraseña? <span class="font-semibold">Accede aquí para recuperarlo</span>
                        </a>
                    </div>

                    <div class="mt-8 flex justify-center text-lg text-black">
                        <button type="submit" class="rounded-3xl px-10 py-2 text-white shadow-xl backdrop-blur-md transition-colors duration-300 hover:text-white login-button flex items-center" id="login-button">
                        <span class="material-icons mr-2">login</span> Iniciar Sesión
                        </button>
                    </div>

                    <div class="mt-4 text-center">
                        <a href="{{ route('register') }}" class="text-gray-300 hover:text-white transition-colors duration-300 text-sm">
                            ¿Eres nuevo? <span class="font-semibold">Regístrate al sistema</span>
                        </a>
                    </div>                
                </form>
              </div>
            </div>
        </div>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

@if(session('message'))
    <script>
        Swal.fire({
            title: '¡Éxito!',
            text: '{{ session('message') }}',
            icon: 'success',
            confirmButtonText: 'Ok'
        });
    </script>
@endif

@if(session('email_success'))
    <script>
        Swal.fire({
            title: '¡Éxito!',
            text: '{{ session('email_success') }}',
            icon: 'success',
            confirmButtonText: 'Ok'
        });
    </script>
@endif

@if(session('register_success'))
    <script>
        Swal.fire({
            title: '¡Éxito!',
            text: '{{ session('register_success') }}',
            icon: 'success',
            confirmButtonText: 'Ok'
        });
    </script>
@endif

@if(session('error'))
    <script>
        Swal.fire({
            title: 'Error',
            text: '{{ session('error') }}',
            icon: 'error',
            confirmButtonText: 'Ok'
        });
    </script>
@endif

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Logo
        const logoImg = document.getElementById('logo-image');
        const logoUrl = getComputedStyle(document.documentElement).getPropertyValue('--logo-url');
        if (logoUrl) {
            logoImg.src = logoUrl.replace(/^url\(['"]?/, '').replace(/['"]?\)$/, ''); // Eliminar 'url()'
        } else {
            console.error("La variable CSS '--logo-url' no está definida.");
        }

        // Password visibility
        const togglePassword = document.getElementById('togglePassword');
        const password = document.getElementById('password');

        togglePassword.addEventListener('click', function () {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            this.innerText = type === 'password' ? 'visibility_off' : 'visibility';
        });

        // Para evitar ir hacia atrás en la sesión
        if (window.history.replaceState) {
            window.history.replaceState(null, null, window.location.href);
        }

        // Funciones para manejar cookies
        function setCookie(name, value, days) {
            let expires = "";
            if (days) {
                let date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                expires = "; expires=" + date.toUTCString();
            }
            document.cookie = name + "=" + value + "; path=/" + expires;
        }

        function getCookie(name) {
            let nameEQ = name + "=";
            let cookies = document.cookie.split(';');
            for (let i = 0; i < cookies.length; i++) {
                let c = cookies[i].trim();
                if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
            }
            return null;
        }

        function deleteCookie(name) {
            document.cookie = name + "=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
        }

        // Mostrar alerta de bienvenida si es la primera vez
        let bienvenidaMostrada = getCookie('bienvenidaMostrada');
        if (!bienvenidaMostrada) {
            Swal.fire({
                title: '¡Bienvenido a SIMAC!',
                text: 'Este es el Sistema de Módulo de Atención Ciudadana del Estado de Morelos.',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });

            setCookie('bienvenidaMostrada', 'true', 1);
        }

        // Manejo del formulario de inicio de sesión
        const form = document.getElementById('login-form');
        const loginButton = document.getElementById('login-button');

        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Evita el envío del formulario

            const email = form.querySelector('input[name="identifier"]').value.trim();
            const password = form.querySelector('input[name="password"]').value.trim();

            if (!email || !password) {
                Swal.fire({
                    title: 'Error',
                    text: 'Por favor, completa todos los campos.',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
                return;
            }

            // Deshabilita el botón y muestra loading
            loginButton.innerHTML = '<span class="material-icons animate-spin">refresh</span> Cargando...';
            loginButton.disabled = true;

             // Muestra el SweetAlert de "espere"
            Swal.fire({
                title: 'Espere...',
                text: 'Iniciando sesión...',
                icon: 'info',
                allowOutsideClick: false,
                showConfirmButton: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });
            // Envía el formulario *normalmente*
            form.submit();
        });

        // Escucha los errores de validación
        @if($errors->any())
            Swal.fire({
                title: 'Error',
                text: '@foreach($errors->all() as $error){{ $error }} @endforeach',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            }).then(() => {
                // Reactivar botón SOLO si hay errores
                loginButton.innerHTML = '<span class="material-icons mr-2">login</span> Iniciar Sesión';
                loginButton.disabled = false;
            });
        @endif
    });
</script>
</html>