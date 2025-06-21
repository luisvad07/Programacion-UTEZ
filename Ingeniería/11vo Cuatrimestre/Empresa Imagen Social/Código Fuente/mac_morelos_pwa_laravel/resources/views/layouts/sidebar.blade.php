@php
    $user = session('user', []);
    $role = is_array($user['role'] ?? '') ? ($user['role']['name'] ?? '') : $user['role'];
@endphp

@vite(['resources/css/app.css', 'resources/js/app.js'])
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js" defer></script>  
<!-- Sidebar -->
<aside id="sidebar" class="w-80 bg-[var(--color-base)] text-white fixed left-0 z-30 h-[calc(100vh-1.75rem)] mt-8 overflow-y-auto transition-transform duration-300">
    <nav class="flex flex-col gap-1 min-w-[240px] p-2 font-sans text-base font-normal text-white">
        <a href="{{ route('dashboard.redirect') }}">
            <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-gray-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                <div class="grid place-items-center mr-4">
                    <i class="fa-solid fa-gauge"></i>
                </div>
                Dashboard
            </div>
        </a>        
        @if (!empty($user) && strtoupper($role) === 'PROMOTOR')
            <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                <div class="grid place-items-center mr-4">
                    <i class="fa-solid fa-user"></i>
                </div>
                Lista de Ciudadanos
            </div>

            <a href="{{ route('incidents.create.form') }}">
                <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-file-export"></i>
                    </div>
                    Crear una incidencia
                </div>
            </a>

            <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                <div class="grid place-items-center mr-4">
                    <i class="fa-solid fa-calendar-days"></i>
                </div>
                Aprobación de Eventos
            </div>

            <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                <div class="grid place-items-center mr-4">
                    <i class="fa-solid fa-clipboard"></i>
                </div>
                Reportes Generales
            </div>
        @endif

        @if (!empty($user) && strtoupper($role) === 'ADMINISTRADOR')

            <!-- Gestión de Promotores -->
            <div x-data="{ openUsuarios: false }" class="w-full">
                <button @click="openUsuarios = !openUsuarios"
                    class="flex items-center w-full px-3 py-2 rounded-lg hover:bg-[--color-bland] text-start ">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-arrow-up-from-bracket"></i>
                    </div>
                    Gestión de Promotores
                    <i :class="openUsuarios ? 'fa-solid fa-chevron-right' : 'fa-solid fa-chevron-down'" class="ml-auto transition-transform"></i>
                </button>

                <div x-show="openUsuarios" x-cloak class="mt-1 space-y-1 pl-4 transition-all" x-collapse>
                    <a href=#" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Todos los Promotores</a>
                    <a href="{{route('assignments.index')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Asignar Ciudadanos a Promotor</a>
                </div>
            </div>

            <a href="{{ route('incidents') }}">
                <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-file-export"></i>
                    </div>
                    Notificación de Incidencias
                </div>
            </a>

            <a href="{{ route('events') }}">
                <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-calendar-days"></i>
                    </div>
                    Lista de Eventos
                </div>
            </a>

            <a href="{{ route('categories.index') }}">
                <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-newspaper"></i>
                    </div>
                    Categorías de Noticias
                </div>
            </a>

            <!-- Gestión de Usuarios -->
            <div x-data="{ openUsuarios: false }" class="w-full">
                <button @click="openUsuarios = !openUsuarios"
                    class="flex items-center w-full px-3 py-2 rounded-lg hover:bg-[--color-bland] text-start ">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-user"></i>
                    </div>
                    Gestión de Usuarios
                    <i :class="openUsuarios ? 'fa-solid fa-chevron-right' : 'fa-solid fa-chevron-down'" class="ml-auto transition-transform"></i>
                </button>

                <div x-show="openUsuarios" x-cloak class="mt-1 space-y-1 pl-4 transition-all" x-collapse>
                    <a href="{{route('users')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Todos los Usuarios</a>
                    <a href="{{route('users.busqueda_avanzada')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Búsqueda Avanzada</a>
                    <a href="{{route('users.busqueda_rol_estado')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Búsqueda Rol/Estado</a>
                </div>
            </div>
        @endif

        @if (!empty($user) && strtoupper($role) === 'SUPERVISOR')

            <!-- Gestión de Eventos -->
            <div x-data="{ openEventos: false }" class="w-full">
                <button @click="openEventos = !openEventos"
                    class="flex items-center w-full px-3 py-2 rounded-lg hover:bg-[--color-bland] text-start ">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-calendar-days"></i>
                    </div>
                    Gestión de Eventos
                    <i :class="openEventos ? 'fa-solid fa-chevron-right' : 'fa-solid fa-chevron-down'" class="ml-auto transition-transform"></i>
                </button>

                <div x-show="openEventos" x-cloak class="mt-1 space-y-1 pl-4 transition-all" x-collapse>
                    <a href="{{route('events')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Todos los Eventos</a>
                    <a href="#" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Información General de Eventos</a>
                    <a href="#" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Reportes Generales</a>
                </div>
            </div>
        @endif

        @if (!empty($user) && strtoupper($role) === 'PERIODISTA')
            <!-- Gestión de Usuarios -->
            <div x-data="{ openNoticias: false }" class="w-full">
                <button @click="openNoticias = !openNoticias"
                    class="flex items-center w-full px-3 py-2 rounded-lg hover:bg-[--color-bland] text-start ">
                    <div class="grid place-items-center mr-4">
                        <i class="fa-solid fa-newspaper"></i>
                    </div>
                    Gestión de Noticias
                    <i :class="openNoticias ? 'fa-solid fa-chevron-right' : 'fa-solid fa-chevron-down'" class="ml-auto transition-transform"></i>
                </button>

                <div x-show="openNoticias" x-cloak class="mt-1 space-y-1 pl-4 transition-all" x-collapse>
                    <a href="{{route('news.index')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Todas las Noticias</a>
                    <a href="{{route('publish.index')}}" class="block px-3 py-2 rounded-lg hover:bg-[--color-bland] text-center">Publicar/Subir Noticias</a>
                </div>
            </div>
        @endif

        <a href="{{ route('profile') }}">
            <div role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
                <div class="grid place-items-center mr-4">
                    <i class="fa-solid fa-id-card"></i>
                </div>
                Perfil del Usuario
            </div>
        </a>

        <a href="{{ route('map-colorimetries.index') }}" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
            <div class="grid place-items-center mr-4">
                <i class="fa-solid fa-map"></i>
            </div>
            Acceso al Mapa
        </a>
        
        <div id="logout-button" role="button" tabindex="0" class="flex items-center w-full p-3 rounded-lg text-start leading-tight transition-all hover:bg-blue-50 hover:bg-opacity-80 focus:bg-blue-50 focus:bg-opacity-80 active:bg-blue-50 active:bg-opacity-80 hover:text-[--color-base] focus:text-[--color-base] active:text-[--color-base] outline-none">
            <div class="grid place-items-center mr-4">
                <i class="fa-solid fa-arrow-right-from-bracket"></i>
            </div>
            Cerrar Sesión
        </div>
        <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
            @csrf
        </form>

    </nav>
</aside>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    // Función para manejar la alerta de salida
    function showExitAlert() {
        Swal.fire({
            title: "¿Seguro que quieres salir?",
            text: "Puede que pierdas los cambios no guardados.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Sí, salir",
            cancelButtonText: "No, permanecer",
            reverseButtons: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('logout-form').submit();
            } else {
                history.pushState(null, "", location.href); // Mantiene al usuario en la página
            }
        });
    }
    
    document.addEventListener('DOMContentLoaded', function() {
        // Verificar si el usuario está en el dashboard
        if (window.location.pathname.includes('/dashboard')) {

            // Mostrar la alerta de bienvenida siempre que esté en el dashboard
            Swal.fire({
                title: '¡Bienvenido al Dashboard!',
                text: 'Estás dentro de tu panel de administración.',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });

            // Evitar la navegación hacia atrás usando la función de history.pushState
            history.pushState(null, "", location.href);

            // Detectar la acción de retroceder y mostrar la alerta de confirmación
            window.addEventListener("popstate", function (event) {
                showExitAlert();
            });

            // Bloquear la navegación con ALT + Flechas
            document.addEventListener("keydown", function (event) {
                if (event.altKey && (event.key === "ArrowLeft" || event.key === "ArrowRight")) {
                    Swal.fire({
                        title: '¡Cuidado!',
                        text: 'Esta función ha sido desactivada.',
                        icon: 'warning',
                        confirmButtonText: 'Aceptar'
                    });
                    event.preventDefault(); // Bloquear la navegación
                }
            });
        }

        // Funcionalidad de logout
        document.getElementById('logout-button').addEventListener('click', function() {
            Swal.fire({
                title: '¿Estás seguro?',
                text: '¿Deseas cerrar sesión?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Sí, cerrar sesión',
                cancelButtonText: 'Cancelar',
                reverseButtons: true,
                allowOutsideClick: false,
                allowEscapeKey: false,
                cancelButtonColor: '#d33',
                confirmButtonColor: '#3085d6',
                preConfirm: () => {

                    Swal.fire({
                        title: 'Cerrando sesión...',
                        html: 'Estamos procesando tu solicitud',
                        timer: 2000,
                        didOpen: () => {
                            Swal.showLoading();
                        }
                    });

                    // Enviar el formulario de cierre de sesión
                    document.getElementById('logout-form').submit();
                }
            });
        });

        // Función para detectar inactividad y cerrar sesión después de un tiempo
        let timeout;
        const sessionTimeout = 3600 * 1000; // 1 hora en milisegundos

        function resetTimer() {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                document.getElementById('logout-form').submit();
                window.location.href = "{{ route('session.expired') }}"; // Ruta a la vista de sesión expirada
            }, sessionTimeout);
        }

        // Eventos que reinician el temporizador
        document.addEventListener("mousemove", resetTimer);
        document.addEventListener("keypress", resetTimer);
        document.addEventListener("click", resetTimer);
        document.addEventListener("scroll", resetTimer);

        // Iniciar temporizador al cargar la página
        resetTimer();
    });
</script>