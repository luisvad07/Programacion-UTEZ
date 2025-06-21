<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cambiar Contraseña - SIMAC MORELOS</title>
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
                        <h1 class="mb-2 text-2xl">Cambiar Contraseña</h1>
                        <span class="text-gray-300">Ingresa tu nueva contraseña</span>
                    </div>

                    <form id="reset-password-form" method="POST" action="{{ route('password.reset') }}">
                        @csrf

                        {{-- Campo oculto para el token --}}
                        <input type="hidden" name="token" value="{{ $token }}">

                        <div class="mb-4 text-lg relative password-container">
                            <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">lock</span>
                            <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full login-input" type="password" name="password" id="password" placeholder="Nueva Contraseña" />
                            <i class="material-icons toggle-password" onclick="togglePassword('password')" >visibility</i>
                        </div>

                        <div class="mb-4 text-lg relative password-container">
                            <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">lock</span>
                            <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full login-input" type="password" name="password_confirmation" id="password_confirmation" placeholder="Confirmar Nueva Contraseña" />
                            <i class="material-icons toggle-password" onclick="togglePassword('password_confirmation')">visibility</i>
                        </div>

                        <div class="mt-8 flex justify-center text-lg text-black">
                            <button type="submit" class="rounded-3xl px-10 py-2 text-white shadow-xl backdrop-blur-md transition-colors duration-300 hover:text-white login-button flex items-center">
                                <span class="material-icons mr-2">refresh</span> Cambiar Contraseña
                            </button>
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

            // Remove existing event listener to prevent duplicates
            const form = document.getElementById('reset-password-form');
            form.removeEventListener('submit', submitHandler);

            // Add the event listener
            form.addEventListener('submit', submitHandler);

            async function submitHandler(event) {
                event.preventDefault();

                const newPassword = document.getElementById('password').value.trim();
                const confirmPassword = document.getElementById('password_confirmation').value.trim();
                // const email = document.getElementById('email').value.trim(); // Get the email  <-- AQUÍ se usará el campo "email"

                const token = document.querySelector('input[name="token"]').value;

                if (!newPassword) {
                    Swal.fire({
                        title: 'Error',
                        text: 'Por favor, ingresa la nueva contraseña.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                    return;
                }

                if (newPassword.length < 8) {
                    Swal.fire({
                        title: 'Error',
                        text: 'La contraseña debe tener al menos 8 caracteres.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                    return;
                }

                if (newPassword !== confirmPassword) {
                    Swal.fire({
                        title: 'Error',
                        text: 'Las contraseñas no coinciden.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                    return;
                }

                Swal.fire({
                    title: 'Espere...',
                    text: 'Cambiando la contraseña...',
                    icon: 'info',
                    allowOutsideClick: false,
                    showConfirmButton: false,
                    didOpen: () => {
                        Swal.showLoading();
                    }
                });

                try {
                    const formData = new FormData(form); // Get the form data
                    const response = await fetch(form.action, { // Use the form's action attribute
                        method: 'POST',
                        body: formData, // Send form data
                    });

                    const data = await response.json();

                    console.log('Respuesta del servidor:', data);

                    if (!response.ok) {
                        let errorMessage = data.message || 'Error al cambiar la contraseña.';

                         if (data.errors) { //Handle validation errors
                            errorMessage = Object.values(data.errors).join('<br>');
                        }

                        Swal.fire({
                            title: 'Error',
                            html: errorMessage, // Mostrar errores de validación
                            icon: 'error',
                            confirmButtonText: 'Aceptar'
                        });
                    }
                     else {
                         if(data.success){
                             Swal.fire({
                                 title: '¡Contraseña Cambiada!',
                                 text: data.message,
                                 icon: 'success',
                                 confirmButtonText: 'Aceptar'
                             }).then(() => {
                                 window.location.href = '{{ route('login') }}'; // Redirige al login
                             });
                         } else {
                            Swal.fire({
                                title: '¡Error!',
                                text: data.message,
                                icon: 'error',
                                confirmButtonText: 'Aceptar'
                            })
                         }
                    }
                } catch (error) {
                    console.error('Error:', error);
                    Swal.fire({
                        title: 'Error',
                        text: 'Ocurrió un error inesperado.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                }
            }
        });

        function togglePassword(inputId) {
            const passwordInput = document.getElementById(inputId);
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // Alternar el icono
            const toggleIcon = passwordInput.parentElement.querySelector('.toggle-password');
            toggleIcon.textContent = type === 'password' ? 'visibility' : 'visibility_off';
        }

    </script>
</body>
</html>