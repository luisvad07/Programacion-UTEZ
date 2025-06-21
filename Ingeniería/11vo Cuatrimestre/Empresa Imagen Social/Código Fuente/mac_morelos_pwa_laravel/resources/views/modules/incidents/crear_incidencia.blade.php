<!-- resources/views/incidents/create.blade.php -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Crear Incidencia</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Leaflet CSS & JavaScript -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>

    <style>
        #map { height: 300px; } /* Adjust height as needed */
    </style>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">
            <div class="bg-white p-8 rounded-md w-full">
                <div class=" flex items-center justify-between pb-6">
                    <div>
                        <h2 class="text-gray-600 font-semibold">Crear Incidencia</h2>
                        <span class="text-xs">Ingrese los detalles de la nueva incidencia</span>
                    </div>
                </div>

                <form action="{{ route('incidents.create') }}" method="POST">
                    @csrf

                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label for="promoter_userId" class="block text-gray-700 text-sm font-bold mb-2">ID Promotor (Usuario en Sesión):</label>
                            <input type="text" id="promoter_userId" name="promoter[userId]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                   value="{{ $userProfile['data']['firstName'] }} {{ $userProfile['data']['lastName'] }}" readonly>
                            <input type="hidden" name="promoter[userId]" value="{{ $userProfile['data']['userId'] }}">
                        </div>                        

                        <div>
                            <label for="assignedTo_userId" class="block text-gray-700 text-sm font-bold mb-2">Enviar Incidencia A:</label>
                            <select id="assignedTo_userId" name="assignedTo[userId]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                @foreach($admins['data'] as $admin) 
                                    <option value="{{ $admin['userId'] }}">{{ $admin['firstName'] }} {{ $admin['lastName'] }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div>
                            <label for="incidentName" class="block text-gray-700 text-sm font-bold mb-2">Nombre de la Incidencia:</label>
                            <input type="text" id="incidentName" name="incidentName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="description" class="block text-gray-700 text-sm font-bold mb-2">Descripción:</label>
                            <textarea id="description" name="description" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required></textarea>
                        </div>

                        <div>
                            <label for="address_street" class="block text-gray-700 text-sm font-bold mb-2">Calle:</label>
                            <input type="text" id="address_street" name="address[street]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_number" class="block text-gray-700 text-sm font-bold mb-2">Número:</label>
                            <input type="text" id="address_number"  name="address[number]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_interiorNumber" class="block text-gray-700 text-sm font-bold mb-2">Número Interior:</label>
                            <input type="text" id="address_interiorNumber" name="address[interiorNumber]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                        </div>

                        <div>
                            <label for="address_neighborhood" class="block text-gray-700 text-sm font-bold mb-2">Colonia:</label>
                            <input type="text" id="address_neighborhood" name="address[neighborhood]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_zipCode" class="block text-gray-700 text-sm font-bold mb-2">Código Postal:</label>
                            <input type="text" id="address_zipCode" name="address[zipCode]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_city" class="block text-gray-700 text-sm font-bold mb-2">Ciudad:</label>
                            <input type="text" id="address_city" name="address[city]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_state" class="block text-gray-700 text-sm font-bold mb-2">Estado:</label>
                            <input type="text" id="address_state" name="address[state]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            <label for="address_country" class="block text-gray-700 text-sm font-bold mb-2">País:</label>
                            <input type="text" id="address_country" name="address[country]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        </div>

                        <div>
                            {{-- <label for="address_latitude" class="block text-gray-700 text-sm font-bold mb-2">Latitud:</label> --}}
                            <input type="hidden" step="any" id="address_latitude" name="address[latitude]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                        </div>

                        <div>
                            {{-- <label for="address_longitude" class="block text-gray-700 text-sm font-bold mb-2">Longitud:</label> --}}
                            <input type="hidden" step="any" id="address_longitude" name="address[longitude]" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                        </div>

                    </div>

                    <div class="mb-4">
                        <label class="block text-gray-700 text-sm font-bold mb-2">Ubicación en el Mapa:</label>
                        <div id="map"></div>
                    </div>

                    <div class="mt-6 flex justify-end">
                        <button type="submit" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                            <i class="material-icons mr-2">add</i>
                            Crear Incidencia
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

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
            // --- INICIALIZACIÓN DEL MAPA ---
            var map = L.map('map', {
                 // Opciones adicionales si son necesarias
            }).setView([18.91860000, -99.23420000], 13); // Coordenadas de Cuernavaca
    
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
    
            var marker;
    
            function updateCoordinates(lat, lng) {
                document.getElementById('address_latitude').value = lat.toFixed(6); // Usar toFixed para precisión consistente
                document.getElementById('address_longitude').value = lng.toFixed(6);
            }
    
            map.on('click', function(e) {
                let lat = e.latlng.lat;
                let lng = e.latlng.lng;
    
                if (marker) {
                    map.removeLayer(marker); // También puedes usar marker.setLatLng([lat, lng]); si prefieres mover el existente
                }
    
                marker = L.marker([lat, lng]).addTo(map);
                updateCoordinates(lat, lng);
                // Opcional: llamar a reverseGeocode aquí si quieres que la dirección se actualice al hacer clic
                // reverseGeocode(lat, lng);
            });
    
            // Función para geocodificación inversa (Coordenadas -> Dirección)
            function reverseGeocode(latitude, longitude) {
                // Limpia los campos antes de la nueva búsqueda para evitar datos viejos si falla
                clearAddressFields();
    
                const url = `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${latitude}&lon=${longitude}&addressdetails=1`; // addressdetails=1 puede dar más info
    
                fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        console.log("Reverse Geocode Response:", data); // Log para depuración
                        if (data && data.address) {
                            const address = data.address;
    
                            document.getElementById('address_street').value = address.road || '';
                            document.getElementById('address_number').value = address.house_number || '';
                            // Nominatim a veces usa 'neighbourhood', otras 'suburb', o 'quarter'
                            document.getElementById('address_neighborhood').value = address.neighbourhood || address.suburb || address.quarter || '';
                            document.getElementById('address_zipCode').value = address.postcode || '';
                            document.getElementById('address_city').value = address.city || address.town || address.village || ''; // Más opciones
                            document.getElementById('address_state').value = address.state || '';
                            document.getElementById('address_country').value = address.country || '';
                        } else {
                             console.warn("Reverse geocoding did not return a valid address for:", latitude, longitude);
                        }
                    })
                    .catch(error => {
                        console.error('Error reverse geocoding:', error);
                        Swal.fire('Error', 'No se pudo obtener la dirección para las coordenadas seleccionadas.', 'error');
                    });
            }
    
            // Función para geocodificación (Dirección -> Coordenadas)
            function geocodeAddress() {
                const street = document.getElementById('address_street').value;
                const number = document.getElementById('address_number').value;
                const neighborhood = document.getElementById('address_neighborhood').value;
                const zipCode = document.getElementById('address_zipCode').value;
                const city = document.getElementById('address_city').value;
                const state = document.getElementById('address_state').value;
                const country = document.getElementById('address_country').value;
    
                // Construir una query más específica puede ayudar a Nominatim
                const queryParts = [
                    street, number, neighborhood, city, state, zipCode, country
                ].filter(Boolean).join(', '); // Filtra partes vacías y une con comas
    
                if (!queryParts) {
                    console.log("No address parts provided for geocoding.");
                    return; // No hacer nada si no hay dirección
                }
    
                const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(queryParts)}&addressdetails=1&limit=1`; // limit=1 para obtener solo el mejor resultado
    
                fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        console.log("Geocode Response:", data); // Log para depuración
                        if (data && data.length > 0) {
                            const latitude = parseFloat(data[0].lat);
                            const longitude = parseFloat(data[0].lon);
    
                            if (!isNaN(latitude) && !isNaN(longitude)) {
                                updateCoordinates(latitude, longitude); // Actualiza los inputs hidden
                                map.setView([latitude, longitude], 16); // Zoom más cercano al encontrar dirección
    
                                if (marker) {
                                    map.removeLayer(marker);
                                }
                                marker = L.marker([latitude, longitude]).addTo(map);
                            } else {
                                 console.warn("Geocoding returned invalid coordinates:", data[0]);
                            }
                        } else {
                            console.log("Dirección no encontrada por geocodificación:", queryParts);
                            // Podrías mostrar un mensaje al usuario aquí
                            // Swal.fire('Info', 'No se encontraron coordenadas exactas para la dirección ingresada.', 'info');
                        }
                    })
                    .catch(error => {
                        console.error('Error geocoding:', error);
                        Swal.fire('Error', 'Ocurrió un error al buscar las coordenadas de la dirección.', 'error');
                    });
            }
    
            function clearAddressFields() {
                 document.getElementById('address_street').value = '';
                 document.getElementById('address_number').value = '';
                 document.getElementById('address_neighborhood').value = '';
                 document.getElementById('address_zipCode').value = '';
                 document.getElementById('address_city').value = '';
                 document.getElementById('address_state').value = '';
                 document.getElementById('address_country').value = '';
            }
    
    
            // --- EVENT LISTENERS PARA INPUTS ---
    
            // Añadir event listeners a los campos de dirección (usar 'blur' es a menudo mejor que 'change' para evitar llamadas múltiples mientras se escribe)
            const addressFields = [
                'address_street', 'address_number', 'address_neighborhood',
                'address_zipCode', 'address_city', 'address_state', 'address_country'
            ];
            addressFields.forEach(fieldId => {
                const element = document.getElementById(fieldId);
                if (element) {
                     element.addEventListener('blur', geocodeAddress); // Llamar a geocodeAddress cuando el campo pierde el foco
                }
            });
    
            // Añadir listeners a los campos de lat/lon (si fueran visibles y editables)
            // document.getElementById('address_latitude').addEventListener('change', function() { /* ... código para actualizar mapa y llamar reverseGeocode ... */ });
            // document.getElementById('address_longitude').addEventListener('change', function() { /* ... código para actualizar mapa y llamar reverseGeocode ... */ });
    
    
            // --- LÓGICA DEL SIDEBAR ---
            const toggleButton = document.getElementById("toggleSidebar");
            const sidebar = document.getElementById("sidebar");
            const mainContent = document.getElementById("mainContent");
            // const logoutButton = document.getElementById('logout-button-p'); // Descomentar si usas logoutButton
    
            if (toggleButton && sidebar && mainContent) { // Verificar que los elementos existen
                toggleButton.addEventListener("click", function() {
                    sidebar.classList.toggle("-translate-x-full");
    
                    // Ajustar margen del contenido principal
                    if (sidebar.classList.contains("-translate-x-full")) {
                        mainContent.classList.remove("ml-80");
                        mainContent.classList.add("ml-0"); // O ajusta según tu layout base sin sidebar
                    } else {
                        mainContent.classList.remove("ml-0");
                        mainContent.classList.add("ml-80");
                    }
    
                    // Esperar a que la transición CSS termine (aprox) y luego invalidar el tamaño del mapa
                    // El timeout debe ser un poco mayor que la duración de la transición CSS (duration-300 = 300ms)
                    setTimeout(() => {
                        if (map) { // Asegurarse que la variable map existe
                            console.log("Invalidating map size due to sidebar toggle.");
                            map.invalidateSize();
                        }
                    }, 350); // Ajusta este valor si tu transición es diferente
                });
            } else {
                console.error("Sidebar toggle elements not found!");
            }
    
            // Pequeño delay inicial y invalidateSize por si acaso el layout inicial tarda en estabilizarse
            setTimeout(() => {
                if (map) {
                     map.invalidateSize();
                }
            }, 100);
    
    
        });
    </script>
</body>
</html>