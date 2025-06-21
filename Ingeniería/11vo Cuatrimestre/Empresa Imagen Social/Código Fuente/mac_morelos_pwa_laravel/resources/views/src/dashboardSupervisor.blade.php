<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Dashboard Supervisor</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">

            <h1 class="text-2xl font-semibold mb-6">Dashboard Principal del Supervisor</h1>

            @if (session()->has('dash_error'))
                <div id="error-message" class="bg-red-500 text-white p-6 rounded-md mb-8 text-center mx-auto w-full flex items-center justify-center" style="z-index: 1200;">
                    <i class="fa-solid fa-circle-xmark mr-2"></i>
                    <p>{{ session('dash_error') }}</p>
                </div>
                {{ session()->forget('dash_error') }}
            @endif

            @if (session()->has('allow_error'))
                <div id="error-message" class="bg-red-500 text-white p-6 rounded-md mb-8 text-center mx-auto w-full flex items-center justify-center" style="z-index: 1200;">
                    <i class="fa-solid fa-circle-xmark mr-2"></i>
                    <p>{{ session('allow_error') }}</p>
                </div>
                {{ session()->forget('allow_error') }}
            @endif

            @if (session('login_message'))
                <div class="bg-green-500 text-white p-6 rounded-md mb-8 text-center mx-auto w-full flex items-center justify-center" style="z-index: 1200;">
                    <i class="fa-solid fa-circle-check mr-2"></i>  <!-- Icono a la izquierda con margen derecho -->
                    <p>{{ session('login_message') }}</p>
                </div>
            @endif

            <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">

                <!-- Statistics Column -->
                <div class="space-y-6">
                    <!-- Chronometer Card -->
                    <div class="bg-white shadow-md rounded-lg overflow-hidden">
                        <div class="px-6 py-4">
                            <div class="flex items-center justify-center mb-2">
                                <i class="material-icons text-gray-500 mr-2">timer</i>
                                <p class="text-xl font-semibold text-gray-700">Tiempo de Gobierno</p>
                            </div>
                            <div id="chronometer" class="text-3xl font-bold text-center" style="color: #70795B;">0 días, 00:00:00</div>
                            <p class="text-center text-sm italic text-gray-500 mt-1">Desde el 1 de Octubre de 2024</p>
                        </div>
                    </div>

                    <!-- Data Cards (Grid) -->
                    <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
                         <!-- Total Ciudadanos Asignados -->
                         <div class="bg-white shadow-md rounded-lg p-4 flex items-center justify-between" style="background-color: #404534; color: white;">
                            <div>
                                <p class="font-semibold">Ciudadanos Registrados</p>
                                <p class="text-3xl font-bold">{{ $totalCiudadanosAsignados ?? 0 }}</p>
                            </div>
                            <i class="material-icons text-4xl">people</i>
                        </div>

                        <!-- Eventos En Curso -->
                        <div class="bg-white shadow-md rounded-lg p-4 flex items-center justify-between" style="background-color: #70795B; color: white;">
                            <div>
                                <p class="font-semibold">Eventos En Curso</p>
                                <p class="text-3xl font-bold">{{ $eventosEnCurso ?? 0 }}</p>
                            </div>
                            <i class="material-icons text-4xl">event</i>
                        </div>
                    </div>
                      <!-- Segunda Fila de Data Cards (Grid) -->
                    <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">

                        <!-- Próximo Evento -->
                        <div class="bg-white shadow-md rounded-lg p-4 flex items-center justify-between" style="background-color: #5B6479; color: white;">
                            <div>
                                <p class="font-semibold">Próximo Evento</p>
                                <p class="text-3xl font-bold">???</p>
                            </div>
                            <i class="material-icons text-4xl">help</i>
                        </div>
                        <!-- Incidencia Destacada -->
                        <div class="bg-white shadow-md rounded-lg p-4 flex items-center justify-between" style="background-color: #E57373; color: white;">
                            <div>
                                <p class="font-semibold">Eventos con Baja Asistencia</p>
                                <p class="text-3xl font-bold">???</p>
                            </div>
                            <i class="material-icons text-4xl">warning</i>
                        </div>
                    </div>

                </div>

                <!-- Map Column -->
                <div class="bg-white shadow-md rounded-lg overflow-hidden">
                    <div class="px-6 py-4">
                        <p class="text-xl font-semibold text-gray-700 mb-2">Mapa de Morelos</p>
                        <div id="map"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        var map;

        document.addEventListener("DOMContentLoaded", function() {

            const toggleButton = document.getElementById("toggleSidebar");
            const sidebar = document.getElementById("sidebar");
            const mainContent = document.getElementById("mainContent");

            toggleButton.addEventListener("click", function() {
                sidebar.classList.toggle("-translate-x-full");

                if (sidebar.classList.contains("-translate-x-full")) {
                    mainContent.classList.remove("ml-80");
                    mainContent.classList.add("ml-0");
                } else {
                    mainContent.classList.add("ml-80");
                    mainContent.classList.remove("ml-0");
                }

                setTimeout(() => {
                    map.invalidateSize();
                }, 100);
            });

             // Chronometer Functionality
            function startChronometer() {
                const startDate = new Date("2024-10-01T00:00:00"); // 01 de octubre de 2024
                let now = new Date();
                let difference = now.getTime() - startDate.getTime();

                let days = Math.floor(difference / (1000 * 60 * 60 * 24));
                let hours = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                let minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
                let seconds = Math.floor((difference % (1000 * 60)) / 1000);

                hours = (hours < 10) ? "0" + hours : hours;
                minutes = (minutes < 10) ? "0" + minutes : minutes;
                seconds = (seconds < 10) ? "0" + seconds : seconds;

                document.getElementById("chronometer").textContent = days + " días, " + hours + ":" + minutes + ":" + seconds;
            }
            startChronometer();
            setInterval(startChronometer, 1000); // Update every second

            // Inicialización del mapa
            map = L.map('map').setView([18.7333, -99.2167], 9); // Coordenadas centrales de Morelos

            // Agregar capa de mapa base
            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }).addTo(map);

            // Datos de ejemplo (reemplaza con tus datos reales)
            const dataPoints = [
                { lat: 18.8, lng: -99.1, type: 'ciudadano', info: 'Ciudadano 1' },
                { lat: 18.6, lng: -99.3, type: 'incidencia', info: 'Incidencia 1: Baches' },
                { lat: 18.7, lng: -99.2, type: 'evento', info: 'Evento: Festival Cultural' }
            ];

            dataPoints.forEach(point => {
                let icon;

                switch (point.type) {
                    case 'ciudadano':
                        icon = L.divIcon({className: 'leaflet-div-icon', html: '<i class="fa fa-user marker-icon"></i>'}); // FontAwesome
                        break;
                    case 'incidencia':
                        icon = L.divIcon({className: 'leaflet-div-icon', html: '<i class="fa fa-exclamation-triangle marker-icon"></i>'}); // FontAwesome
                        break;
                    case 'evento':
                        icon = L.divIcon({className: 'leaflet-div-icon', html: '<i class="fa fa-calendar marker-icon"></i>'}); // FontAwesome
                        break;
                    default:
                        icon = L.divIcon({className: 'leaflet-div-icon', html: '<i class="fa fa-map-marker marker-icon"></i>'}); // Icono por defecto
                }

                L.marker([point.lat, point.lng], {icon: icon})
                    .addTo(map)
                    .bindPopup(`<b>${point.type.toUpperCase()}</b>: ${point.info}`); // Popup con información
            });

            // Agregar contorno de Morelos desde el archivo GeoJSON en la carpeta 'public/geojson'
            fetch('/geojson/morelos-boundary.geojson')
                .then(response => response.json())
                .then(data => {
                    L.geoJSON(data, {
                        style: {
                            color: "#21362C", // Color del contorno
                            weight: 3, // Grosor del contorno
                            opacity: 1, // Opacidad del contorno
                            fillOpacity: 0 // Sin relleno
                        }
                    }).addTo(map);
                })
                .catch(error => console.error('Error cargando el archivo GeoJSON:', error));

            setTimeout(function() {
                var message = document.getElementById('error-message');
                if (message) {
                    message.style.transition = 'opacity 0.5s ease';
                    message.style.opacity = '0';
                    setTimeout(() => message.remove(), 500); // Elimina el elemento después de la transición
                }
            }, 5000);
        });

        window.addEventListener("load", function() {
            map.invalidateSize();
        });
    </script>
</body>
</html>