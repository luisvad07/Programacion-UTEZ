<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Perfil de Usuario</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">
            <div class="profile-container bg-white p-8 rounded-lg shadow-lg max-w-lg text-center">
                <div class="mb-4">
                    <img src="{{ Str::contains($profileImageUrl, 'null') ? asset('assets/default.png') : $profileImageUrl }}" alt="Perfil" class="profile-image mx-auto rounded-full border-4 border-gray-300">

                    <!-- Button to trigger the modal -->
                    <button id="changeProfilePictureButton" class="mt-2 bg-emerald-500 hover:bg-emerald-700 text-white font-bold py-2 px-4 rounded-lg transition inline-flex items-center">
                        <span class="material-icons">photo_camera</span> Cambiar Foto
                    </button>

                    <h1 class="profile-heading text-2xl font-bold mt-4">
                        {{ $user['firstName'] ?? 'Sin nombre' }} {{ $user['lastName'] ?? '' }}
                    </h1>
                </div>

                <div class="profile-info text-left mb-4">
                    <p><i class="material-icons">person</i> <strong>Nombre:</strong> <span>{{ $user['firstName'] ?? 'Sin nombre' }} {{ $user['lastName'] ?? '' }}</span></p>
                    <p><i class="material-icons">email</i> <strong>Email:</strong> <span>{{ $user['email'] }}</span></p>
                    <p><i class="material-icons">badge</i> <strong>Rol:</strong> <span>{{ $user['role']['name'] ?? 'Sin rol' }}</span></p>
                    <p><i class="material-icons">phone</i> <strong>Teléfono:</strong> <span>{{ $user['phone']  ?? 'Sin télefono' }}</span></p>
                    <p><i class="material-icons">assignment</i> <strong>RFC:</strong> <span>{{ $user['rfc']  ?? 'Sin RFC' }}</span></p>
                    <p><i class="material-icons">assignment_ind</i> <strong>CURP:</strong> <span>{{ $user['curp']  ?? 'Sin CURP'}}</span></p>
                    <p><i class="material-icons">cake</i> <strong>Fecha de Nacimiento:</strong> <span>
                        @if($user['birthdate'])
                            {{ \Carbon\Carbon::parse($user['birthdate'])->format('d/m/Y') }}
                        @else
                            Sin fecha
                        @endif
                    </span></p>
                    <p>
                        <i class="material-icons">home</i> <strong>Dirección:</strong>
                        <span>
                            @if(isset($user['address']))
                                Calle {{ $user['address']['street'] ?? '' }}
                                #{{ $user['address']['number'] ?? '' }},
                                Col. {{ $user['address']['neighborhood'] ?? '' }},
                                {{ $user['address']['city'] ?? '' }},
                                {{ $user['address']['state'] ?? '' }},
                                {{ $user['address']['country'] ?? '' }}
                            @else
                                Sin dirección
                            @endif
                        </span>
                    </p>
                    <p>
                      <i class="material-icons">verified_user</i> <strong>Estado:</strong>
                      <span class="{{ $user['status'] ? 'text-green-500' : 'text-red-500' }}">
                        {{ $user['status'] ? 'Activo' : 'Inactivo' }}
                      </span>
                    </p>
                    <p><i class="material-icons">schedule</i> <strong>Creado:</strong> <span>
                        @if($user['createdAt'])
                            {{ \Carbon\Carbon::parse($user['createdAt'])->setTimezone('America/Mexico_City')->format('d/m/Y H:i') }}
                        @else
                            Sin fecha
                        @endif
                    </span></p>
                    <p><i class="material-icons">update</i> <strong>Actualizado:</strong> <span>
                        @if($user['updatedAt'])
                            {{ \Carbon\Carbon::parse($user['updatedAt'])->setTimezone('America/Mexico_City')->format('d/m/Y H:i') }}
                        @else
                            Sin actualizar
                        @endif
                    </span></p>
                </div>

                <div class="flex justify-center">
                    <button id="editProfileButton" type="button" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition inline-flex items-center mr-4">
                        <span class="material-icons">edit</span> Editar
                    </button>

                    <button id="showMapButton" type="button" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-lg transition inline-flex items-center mr-4">
                        <span class="material-icons">location_on</span> Ver Ubicación en Mapa
                    </button>

                    <div id="logout-button-p" role="button" tabindex="0" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-lg transition inline-flex items-center">
                        <span class="material-icons mr-2">logout</span> Cerrar Sesión
                    </div>
                    <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                        @csrf
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Edit Profile -->
    <div id="editProfileModal" class="fixed z-50 inset-0 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-center justify-center min-h-screen text-center sm:block sm:p-0">
            <!-- Fondo oscuro -->
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <!-- Centrado vertical -->
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">​</span>
            <!-- El modal -->
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-2xl sm:w-full">
            <form action="{{ route('profile.update') }}" method="POST">
                @csrf
                <input type="hidden" name="_method" value="PUT">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <h3 class="text-lg leading-6 font-medium text-gray-900">Editar Perfil</h3>
                    <div class="mt-2">
                        <div class="mb-4">
                            <label for="firstName" class="block text-gray-700 text-sm font-bold mb-2">Nombre:</label>
                            <input type="text" id="firstName" name="firstName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['firstName'] ?? '' }}">
                        </div>
    
                        <div class="mb-4">
                            <label for="lastName" class="block text-gray-700 text-sm font-bold mb-2">Apellido:</label>
                            <input type="text" id="lastName" name="lastName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['lastName'] ?? '' }}">
                        </div>
    
                        <div class="mb-4">
                            <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email:</label>
                            <input type="email" id="email" name="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['email'] }}">
                        </div>
    
                        <div class="mb-4">
                            <label for="role" class="block text-gray-700 text-sm font-bold mb-2">Rol:</label>
                            <input type="text" id="role" name="role" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['role']['name'] ?? '' }}" readonly>
                        </div>
    
                         <div class="mb-4">
                            <label for="phone" class="block text-gray-700 text-sm font-bold mb-2">Teléfono:</label>
                            <input type="text" id="phone" name="phone" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['phone']  ?? '' }}">
                        </div>
    
                        <div class="mb-4">
                            <label for="rfc" class="block text-gray-700 text-sm font-bold mb-2">RFC:</label>
                            <input type="text" id="rfc" name="rfc" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['rfc']  ?? '' }}">
                        </div>
    
                        <div class="mb-4">
                            <label for="curp" class="block text-gray-700 text-sm font-bold mb-2">CURP:</label>
                            <input type="text" id="curp" name="curp" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['curp']  ?? ''}}">
                        </div>
    
                        <div class="mb-4">
                            <label for="birthdate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Nacimiento:</label>
                            <input type="date" id="birthdate" name="birthdate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" value="{{ $user['birthdate'] ? \Carbon\Carbon::parse($user['birthdate'])->format('Y-m-d') : '' }}">
                        </div>
    
                        <div class="mb-4">
                            <label class="block text-gray-700 text-sm font-bold mb-2">Dirección:</label>
                            <div class="grid grid-cols-2 gap-2"> <!-- Modificado a grid de 2 columnas -->
                                <div>
                                    <label for="street" class="block text-gray-700 text-xs font-bold mb-1">Calle/Avenida:</label>
                                    <input type="text" id="street" name="address[street]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['street'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="number" class="block text-gray-700 text-xs font-bold mb-1">Número:</label>
                                    <input type="text" id="number" name="address[number]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['number'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="neighborhood" class="block text-gray-700 text-xs font-bold mb-1">Colonia:</label>
                                    <input type="text" id="neighborhood" name="address[neighborhood]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['neighborhood'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="zipCode" class="block text-gray-700 text-xs font-bold mb-1">Código Postal:</label>
                                    <input type="text" id="zipCode" name="address[zipCode]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['zipCode'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="city" class="block text-gray-700 text-xs font-bold mb-1">Ciudad:</label>
                                    <input type="text" id="city" name="address[city]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['city'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="state" class="block text-gray-700 text-xs font-bold mb-1">Estado:</label>
                                    <input type="text" id="state" name="address[state]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['state'] ?? '' }}">
                                </div>
                                <div>
                                    <label for="country" class="block text-gray-700 text-xs font-bold mb-1">País:</label>
                                    <input type="text" id="country" name="address[country]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline text-xs" value="{{ $user['address']['country'] ?? '' }}">
                                </div>
                            </div>
                        </div>
                        <div class="mb-4">
                            <label class="block text-gray-700 text-sm font-bold mb-2">Ubicación en el Mapa:</label>
                            <div id="editMap" style="height: 300px;"></div>
                            <input type="hidden" id="latitude" name="address[latitude]" value="{{ $user['address']['latitude'] ?? '' }}">
                            <input type="hidden" id="longitude" name="address[longitude]" value="{{ $user['address']['longitude'] ?? '' }}">
                        </div>
                    </div>
                </div>
    
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="submit" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:ml-3 sm:w-auto sm:text-sm">
                        Guardar Cambios
                    </button>
                    <button type="button" id="cancelEditProfileModal" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                        Cancelar
                    </button>
                </div>
            </form>
            </div>
        </div>
    </div>

    <!-- Modal Profile Picture -->
    <div id="profilePictureModal" class="fixed z-50 inset-0 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-center justify-center min-h-screen text-center sm:block sm:p-0">
            <!-- Fondo oscuro que cubre toda la pantalla -->
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
    
            <!-- Filler para mantener el modal centrado -->
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">​</span>
    
            <!-- Modal principal -->
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full z-60">
                <form action="{{ route('profile.updatePicture') }}" method="POST" enctype="multipart/form-data">
                    @csrf
                    <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <h3 class="text-xl font-semibold text-gray-700 mb-4">Cambiar Foto de Perfil</h3>
                        <label for="profile_image" class="block text-sm font-medium text-gray-700">Seleccionar nueva foto de perfil</label>
                        <div class="mt-2 flex items-center space-x-4">
                            <input type="file" name="profile_image" id="profile_image" class="hidden" onchange="previewImage(this)">
                            <label for="profile_image" class="cursor-pointer bg-white border border-gray-300 rounded-md font-medium text-indigo-700 hover:text-indigo-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 px-4 py-2">
                                Subir imagen
                            </label>
                            <span id="file-chosen" class="ml-3 text-gray-600 flex-grow truncate"></span>
                        </div>
                        <div class="mt-2">
                            <img id="image-preview" src="#" alt="Vista previa de la imagen" style="max-width: 100%; height: auto; display:none;" class="rounded-md shadow-md">
                        </div>
                    </div>
    
                    <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                        <button type="submit" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:ml-3 sm:w-auto sm:text-sm">
                            Guardar
                        </button>
                        <button type="button" id="modalCancel" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- Modal del Mapa -->
    <div id="locationModal" class="fixed z-50 inset-0 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-center justify-center min-h-screen text-center sm:block sm:p-0">
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">​</span>
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <h3 class="text-lg leading-6 font-medium text-gray-900">Ubicación del Usuario</h3>
                    <div id="map" style="height: 400px;"></div>  <!-- Contenedor del Mapa -->
                </div>
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="button" id="closeLocationModal" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    </div>

</body>
@if(session('success'))
    <script>
        Swal.fire({
            title: '¡Éxito!',
            text: '{{ session('success') }}',
            icon: 'success',
            confirmButtonText: 'Aceptar'
        });
    </script>
@endif

@if(session('error'))
    <script>
        Swal.fire({
            title: '¡Error!',
            text: '{{ session('error') }}',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        });
    </script>
@endif
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Declarar variables de elementos del DOM
        const toggleButton = document.getElementById("toggleSidebar");
        const sidebar = document.getElementById("sidebar");
        const mainContent = document.getElementById("mainContent");
        const logoutButton = document.getElementById('logout-button-p');

        const showMapButton = document.getElementById('showMapButton');
        const locationModal = document.getElementById('locationModal');
        const closeLocationModalButton = document.getElementById('closeLocationModal');
        let map; // Variable del mapa

        // Función para inicializar el mapa
        function initializeMap() {
            const latitude = {{ $user['address']['latitude'] ?? 'null' }};
            const longitude = {{ $user['address']['longitude'] ?? 'null' }};

            if (latitude === null || longitude === null) {
                Swal.fire({
                    title: 'Ubicación no disponible',
                    text: 'No se encontraron coordenadas para este usuario.',
                    icon: 'warning',
                    confirmButtonText: 'Aceptar'
                });
                return;
            }

            // Verificar si el contenedor del mapa existe
            const mapElement = document.getElementById('map');
            if (!mapElement) {
                console.error('Map element not found');
                return;
            }

            // Si el mapa ya está inicializado, evitar reinicializarlo
            if (map) {
                map.setView([latitude, longitude], 15);
                return;
            }

            map = L.map('map').setView([latitude, longitude], 15);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            L.marker([latitude, longitude]).addTo(map)
                .bindPopup('Ubicación del usuario')
                .openPopup();

            // Deshabilitar interacciones con el mapa
            map.dragging.disable();
            map.scrollWheelZoom.disable();
            map.touchZoom.disable();
            map.doubleClickZoom.disable();
            map.boxZoom.disable();
        }

        // Evento para mostrar el modal y cargar el mapa
        if (showMapButton) {
            showMapButton.addEventListener('click', () => {
                locationModal.classList.remove('hidden');
                setTimeout(initializeMap, 300); // Pequeño delay para que el mapa se renderice correctamente
            });
        }

        // Evento para cerrar el modal de ubicación
        if (closeLocationModalButton) {
            closeLocationModalButton.addEventListener('click', () => {
                locationModal.classList.add('hidden');
                if (map) {
                    map.remove();
                    map = null; // Reiniciar la variable del mapa
                }
            });
        }

        // Evento para alternar la barra lateral
        if (toggleButton) {
            toggleButton.addEventListener("click", function() {
                sidebar.classList.toggle("-translate-x-full");

                if (sidebar.classList.contains("-translate-x-full")) {
                    mainContent.classList.remove("ml-80");
                    mainContent.classList.add("ml-0");
                } else {
                    mainContent.classList.add("ml-80");
                    mainContent.classList.remove("ml-0");
                }
            });
        }

        // Evento para cerrar sesión con SweetAlert2
        if (logoutButton) {
            logoutButton.addEventListener('click', function() {
                Swal.fire({
                    title: '¿Estás seguro?',
                    text: '¿Deseas cerrar sesión?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Sí, cerrar sesión',
                    cancelButtonText: 'Cancelar',
                    reverseButtons: true,
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

                        document.getElementById('logout-form').submit();
                    }
                });
            });
        }

        // Lógica para el modal de cambiar foto de perfil
        const changeProfilePictureButton = document.getElementById('changeProfilePictureButton');
        const profilePictureModal = document.getElementById('profilePictureModal');
        const modalCancelButton = document.getElementById('modalCancel');

        if (changeProfilePictureButton) {
            changeProfilePictureButton.addEventListener('click', () => {
                profilePictureModal.classList.remove('hidden');
            });
        }

        if (modalCancelButton) {
            modalCancelButton.addEventListener('click', () => {
                profilePictureModal.classList.add('hidden');
            });
        }

        // Previsualización de imagen
        window.previewImage = function(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('image-preview').src = e.target.result;
                    document.getElementById('image-preview').style.display = 'block';
                }
                reader.readAsDataURL(input.files[0]);

                document.getElementById('file-chosen').textContent = input.files[0].name;
            }
        }

        // Lógica para el modal de edición de perfil
        const editProfileButton = document.getElementById('editProfileButton');
        const editProfileModal = document.getElementById('editProfileModal');
        const cancelEditProfileModalButton = document.getElementById('cancelEditProfileModal');
        let editMap;
        let marker;

        editProfileButton.addEventListener('click', () => {
            editProfileModal.classList.remove('hidden');
            initializeEditMap(); // Initialize map when modal is opened
        });

        cancelEditProfileModalButton.addEventListener('click', () => {
            editProfileModal.classList.add('hidden');
            if (editMap) {
                editMap.remove();
                editMap = null; // Reset map to null when modal is closed
            }
        });

        function initializeEditMap() {
            const latitude = {{ $user['address']['latitude'] ?? '18.94897600' }};
            const longitude = {{ $user['address']['longitude'] ?? '-99.20009100' }};
            const latInput = document.getElementById('latitude');
            const lngInput = document.getElementById('longitude');

            // Check if map element exists before initializing
            const mapElement = document.getElementById('editMap');
            if (!mapElement) {
                console.error('Map element not found');
                return;
            }

            editMap = L.map('editMap').setView([latitude, longitude], 15);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(editMap);

            marker = L.marker([latitude, longitude], { draggable: true }).addTo(editMap);

            marker.on('dragend', function (event) {
                const markerLatLng = marker.getLatLng();
                latInput.value = markerLatLng.lat;
                lngInput.value = markerLatLng.lng;
            });
        }
    });
</script>
</html>