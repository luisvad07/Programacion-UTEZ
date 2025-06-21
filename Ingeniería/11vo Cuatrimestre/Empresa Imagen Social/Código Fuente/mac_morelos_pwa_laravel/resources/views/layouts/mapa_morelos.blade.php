<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mapa Morelos</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
        integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">

            @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                <!-- Botón para cargar datos iniciales (Solo visible para administradores) -->
                <div class="mt-4 mb-6 flex justify-center">
                    <form action="{{ route('map-colorimetries.load-initial') }}" method="POST">
                        @csrf
                        <button type="submit"
                            class="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-700 transition duration-300">
                            <i class="fas fa-sync-alt mr-2"></i> Cargar Datos
                        </button>
                    </form>
                </div>
            @endif

            <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">

                <!-- Municipality List with Symbology -->
                <div class="bg-white shadow-md rounded-lg overflow-hidden">
                    <div class="px-6 py-4">
                        <p class="text-xl font-semibold text-gray-700 mb-4">Municipios de Morelos</p>
                        <ul class="space-y-2 municipality-list">
                            @if(isset($mapColorimetries) && is_array($mapColorimetries))
                            @foreach ($mapColorimetries as $colorimetry)
                            <li class="flex items-center cursor-pointer hover:text-blue-500"
                                onclick="showMunicipalityInfo('{{ $colorimetry['municipality'] }}')">
                                <span class="municipality-color"
                                    style="background-color: {{ $colorimetry['colorHex'] }};"></span>
                                <span>{{ $colorimetry['municipality'] }}</span>
                            </li>
                            @endforeach
                            @else
                            <li>No hay municipios disponibles.</li>
                            @endif
                        </ul>
                    </div>
                </div>

                <!-- Map Card -->
                <div class="bg-white shadow-md rounded-lg overflow-hidden relative">
                    <div class="px-6 py-4">
                        <p class="text-xl font-semibold text-gray-700 mb-2">Mapa de Morelos</p>
                        <div id="mapMorelos" style="height: 500px;">
                            <div class="loading-overlay" id="mapLoadingOverlay">
                                <div class="spinner"></div>
                            </div>
                        </div>
                    </div>
                    <div class="px-6 py-2">  <!-- Container for progress bar -->
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar"></div>
                        </div>
                        <p id="progressText" class="text-sm text-gray-500 text-center">Cargando municipios: 0%</p>
                    </div>
                </div>

            </div>

        </div>
    </div>

    <!-- Modal -->
    <div id="municipalityModal" class="fixed inset-0 z-50 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">​</span>
            <div
                class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4 modal-content">
                    <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                        Información del Municipio
                    </h3>
                    <div class="mt-2">
                        <div id="municipalityInfo"></div>
                    </div>
                </div>
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="button"
                        class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
                        onclick="closeModal()">
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script>
        
        var mapColorimetries = @json($mapColorimetries);

        var map;

        document.addEventListener("DOMContentLoaded", function() {

            const toggleButton = document.getElementById("toggleSidebar");
            const sidebar = document.getElementById("sidebar");
            const mainContent = document.getElementById("mainContent");
            const mapLoadingOverlay = document.getElementById("mapLoadingOverlay");

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

            // Inicialización del mapa
            map = L.map('mapMorelos').setView([18.7333, -99.2167], 9); // Coordenadas centrales de Morelos

            map.invalidateSize();

            // Agregar capa de mapa base
            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'.$mapColorimetries,
                maxBounds: null
            }).addTo(map);

            // Ocultar el overlay después de que el GeoJSON base del contorno se cargue
            // Cargar el GeoJSON y luego pintar los municipios
            loadGeoJson().then(() => {
                loadMapColorimetries();
                mapLoadingOverlay.style.display = "none"; // Hide loading overlay
            }).catch((error) => {
                mapLoadingOverlay.style.display = "none"; // Hide loading overlay even on error
            });

        });

        function updateProgressBar(percentage) {
            const progressBar = document.getElementById('progressBar');
            const progressText = document.getElementById('progressText');

            const integerPercentage = Math.floor(percentage);

            progressBar.style.width = `${integerPercentage}%`;
            progressText.textContent = `Cargando municipios: ${integerPercentage}%`;
        }

        function loadMapColorimetries() {
            // Asume que $mapColorimetries está disponible en la vista
            //const mapColorimetries = @json($mapColorimetries);
            const totalMunicipalities = mapColorimetries.length;
            let loadedCount = 0;

            if (mapColorimetries && mapColorimetries.length > 0) {
                mapColorimetries.forEach(colorimetry => {
                    // Define un color predeterminado si no hay colorimetría
                    let color = colorimetry.color || '#0000FF'; // Default to blue if no color
                    addMunicipalityLayer(colorimetry.municipality, color)
                        .then(() => {
                            loadedCount++;
                            const percentage = (loadedCount / totalMunicipalities) * 100;
                            updateProgressBar(percentage);

                            if (loadedCount === totalMunicipalities) {
                                console.log('All municipalities loaded.');
                            }
                        });
                });
            } else {
                //console.log('No map colorimetries data available.');
                Swal.fire({
                    title: '¡Hubo un Error!',
                    text: 'No hay datos de colorimetría de mapas disponibles',
                    icon: 'warning',
                    confirmButtonText: 'Aceptar'
                });
            }
        }

        // Cargar el archivo GeoJSON
        function loadGeoJson() {
            return fetch('/geojson/municipios_morelos.geojson')
                .then(response => {
                    if (!response.ok) {
                        throw new Error("No se pudo cargar el archivo GeoJSON");
                    }
                    return response.json();
                })
                .then(data => {
                    window.morelosGeoJson = data; // Guardar datos en una variable global para reutilizar
                    console.log("GeoJSON cargado correctamente.");
                    Swal.fire({
                        title: '¡Éxito!',
                        text: 'GeoJSON cargado correctamente',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
                })
                .catch(error => console.error("Error cargando GeoJSON:", error));
        }

       function addMunicipalityLayer(municipality, defaultColor) {
            return new Promise((resolve) => {
                if (!window.morelosGeoJson) {
                    Swal.fire({
                        title: '¡Hubo un Error!',
                        text: 'El archivo GeoJSON aún no se ha cargado.',
                        icon: 'warning',
                        confirmButtonText: 'Aceptar'
                    });
                    resolve();  // Resolve la promesa incluso si hay un error
                    return;
                }

                let municipalityData = {
                    type: "FeatureCollection",
                    features: window.morelosGeoJson.features.filter(
                        feature => feature.properties.NOM_MUN.toLowerCase().trim() === municipality.toLowerCase().trim()
                    )
                };

                if (municipalityData.features.length === 0) {
                    Swal.fire({
                        title: '¡Hubo un Error!',
                        text: `No se encontró el municipio ${municipality} en el GeoJSON.`,
                        icon: 'warning',
                        confirmButtonText: 'Aceptar'
                    });
                    resolve();  // Resolve la promesa incluso si hay un error
                    return;
                }

                fetch(`{{ route('map-colorimetries.show', ['municipality' => '__MUNICIPALITY__']) }}`.replace('__MUNICIPALITY__', encodeURIComponent(municipality)))
                    .then(response => response.json())
                    .then(data => {
                        if (!data || !data.colorHex) {
                            data = {
                                colorHex: defaultColor,
                                yearCreated: 'N/D',
                                description: 'Sin información'
                            };
                        }

                        let color = data.colorHex;
                        let yearCreated = data.yearCreated;
                        let description = data.description || 'Sin descripción';

                        L.geoJSON(municipalityData, {
                            style: {
                                fillColor: color,
                                weight: 2,
                                opacity: 1,
                                color: 'white',
                                fillOpacity: 0.7
                            },
                            onEachFeature: function(feature, layer) {
                                layer.bindPopup(`<strong>${feature.properties.NOM_MUN}</strong><br>Descripción: ${description}<br>Año de Creación: ${yearCreated}`);
                            }
                        }).addTo(map);
                        resolve();  // Resolve la promesa después de agregar el municipio al mapa
                    })
                    .catch(error => {
                        Swal.fire({
                            title: '¡Hubo un Error!',
                            text: `Error al cargar la colorimetría para ${municipality}`,
                            icon: 'warning',
                            confirmButtonText: 'Aceptar'
                        });
                        resolve();  // Resolve la promesa incluso si hay un error
                    });
            });
        }



        function showMunicipalityInfo(municipality) {
            // Buscar en el array de colorimetrías
            const data = mapColorimetries.find(c => c.municipality.toLowerCase() === municipality.toLowerCase());

            let infoHTML = '';

            if (data) {
                // Mostrar la información en el modal with better style

                infoHTML = `
                    <div class="municipality-info">
                        <i class="fas fa-map-marker-alt"></i>
                        <span><strong>Municipio:</strong> ${data.municipality}</span>
                    </div>
                    <div class="flex items-center gap-2">
                        <i class="fas fa-palette"></i>
                        <strong>Color:</strong>
                        <span class="w-5 h-5 rounded-full border border-gray-300" style="background-color:${data.colorHex};"></span>
                        <span>${data.colorHex}</span>
                    </div>
                    <div class="municipality-info">
                        <i class="fas fa-ruler-combined"></i>
                        <span><strong>Área:</strong> ${data.valueArea || 'No disponible'} km²</span>
                    </div>
                    <div class="municipality-info">
                        <i class="far fa-calendar-alt"></i>
                        <span><strong>Año:</strong> ${data.yearCreated}</span>
                    </div>
                    <div class="municipality-info">
                        <i class="fas fa-info-circle"></i>
                        <span><strong>Descripción:</strong> ${data.description || 'Sin descripción'}</span>
                    </div>
                `;

            } else {
                infoHTML = 'No se encontró información.';
            }

            document.getElementById('municipalityInfo').innerHTML = infoHTML;
            openModal();
        }

        function openModal() {
            document.getElementById('municipalityModal').classList.remove('hidden');
        }

        function closeModal() {
            document.getElementById('municipalityModal').classList.add('hidden');
        }

        window.addEventListener("load", function() {
            map.invalidateSize();
        });
    </script>
</body>
</html>