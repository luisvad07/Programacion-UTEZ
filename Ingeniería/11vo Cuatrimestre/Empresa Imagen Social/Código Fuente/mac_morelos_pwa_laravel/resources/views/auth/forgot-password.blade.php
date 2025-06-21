<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Recuperar Contraseña - SIMAC MORELOS</title>
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
                        <h1 class="mb-2 text-2xl">Recuperar Contraseña</h1>
                        <span class="text-gray-300">Ingresa tu correo electrónico</span>
                    </div>

                    <form id="forgot-password-form">
                        @csrf
                        <div class="mb-4 text-lg relative">
                            <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">email</span>
                            <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full login-input" type="email" name="email" placeholder="Ingresa tu Correo" required />
                        </div>

                        <div class="mt-8 flex justify-center text-lg text-black">
                            <button type="submit" class="rounded-3xl px-10 py-2 text-white shadow-xl backdrop-blur-md transition-colors duration-300 hover:text-white login-button flex items-center">
                                <span class="material-icons mr-2">send</span> Enviar Correo de Recuperación
                            </button>
                        </div>

                        <div class="mt-4 text-center">
                            <a href="{{ route('login') }}" class="text-gray-300 hover:text-white transition-colors duration-300 text-sm">
                                Volver al <span class="font-semibold">inicio de sesión</span>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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

            const form = document.getElementById('forgot-password-form');

            form.addEventListener('submit', async function(event) {
                event.preventDefault();

                const email = form.querySelector('input[name="email"]').value.trim();

                if (!email) {
                    Swal.fire({
                        title: 'Error',
                        text: 'Por favor, ingresa tu correo electrónico.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                    return;
                }

                Swal.fire({
                    title: 'Espere...',
                    text: 'Enviando correo de recuperación...',
                    icon: 'info',
                    allowOutsideClick: false,
                    showConfirmButton: false,
                    didOpen: () => {
                        Swal.showLoading();
                    }
                });

                try {
                    const response = await fetch('{{ route('password.request') }}', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': '{{ csrf_token() }}'
                        },
                        body: JSON.stringify({ email: email })
                    });

                    const data = await response.json();

                    console.log('Respuesta del servidor:', data);

                    if (!response.ok || !data.success) {
                        Swal.fire({
                            title: 'Error',
                            text: data.message || 'Error al solicitar el restablecimiento de contraseña.',
                            icon: 'error',
                            confirmButtonText: 'Aceptar'
                        });
                    } else {
                        Swal.fire({
                            title: '¡Correo Enviado!',
                            text: data.message,
                            icon: 'success',
                            confirmButtonText: 'Aceptar'
                        }).then(() => {
                            window.location.reload();
                        });
                    }
                } catch (error) {
                    //console.error('Error:', error);
                    Swal.fire({
                        title: 'Error',
                        text: 'Ocurrió un error inesperado.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                }
            });
        });
    </script>
</body>
</html>