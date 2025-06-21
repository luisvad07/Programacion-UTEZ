<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Incidencias</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    {{-- Leaflet CSS (SOLO para el modal de mapa de ubicación) --}}
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    {{-- Leaflet JS (SOLO para el modal de mapa de ubicación) --}}
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-6 md:p-12 ml-0 md:ml-80 transition-all duration-300">
            <div class="bg-white p-8 rounded-md shadow-lg w-full">
                <div class=" flex items-center justify-between pb-6 border-b mb-6">
                    <div>
                        <h2 class="text-gray-700 font-semibold text-2xl">Gestión de Incidencias</h2>
                        <span class="text-sm text-gray-500">Incidencias asignadas a usted</span>
                    </div>
                </div>

                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow-md rounded-lg overflow-hidden">
                            <table id="incidentTable" class="min-w-full leading-normal">
                                <thead>
                                     <tr class="bg-gray-100">
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Nombre</th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider hidden md:table-cell">Descripción Breve</th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Promotor</th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Estado</th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="incidentTableBody">
                                    @foreach($incidents as $incident)
                                        @php
                                            $createdAtFormatted = isset($incident['createdAt']) ? \Carbon\Carbon::parse($incident['createdAt'])->isoFormat('DD MMM YYYY, h:mm A') : 'N/A';
                                            $updatedAtFormatted = isset($incident['updatedAt']) ? \Carbon\Carbon::parse($incident['updatedAt'])->isoFormat('DD MMM YYYY, h:mm A') : 'N/A';
                                            $resolvedAtFormatted = isset($incident['resolvedAt']) ? \Carbon\Carbon::parse($incident['resolvedAt'])->isoFormat('DD MMM YYYY, h:mm A') : null;
                                            $promoterJson = json_encode($incident['promoter'] ?? null);
                                            $assignedToJson = json_encode($incident['assignedTo'] ?? null);
                                            $addressJson = json_encode($incident['address'] ?? null);
                                            // Añadir URL de evidencia si viene de la API (ajusta el nombre del campo)
                                            $evidenceUrl = $incident['evidenceImageUrl'] ?? null;
                                        @endphp
                                        <tr class="hover:bg-gray-50">
                                                {{-- ... (celdas de datos sin cambios) ... --}}
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm"><p class="text-gray-900 whitespace-no-wrap">{{ $incident['incidentId'] }}</p></td>
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm"><p class="text-gray-900 whitespace-no-wrap font-medium">{{ $incident['incidentName'] }}</p></td>
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm hidden md:table-cell"><p class="text-gray-700 whitespace-no-wrap">{{ \Illuminate\Support\Str::limit($incident['description'], 50) }}</p></td>
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-900 whitespace-no-wrap">{{ $incident['promoter']['firstName'] ?? 'N/A' }} {{ $incident['promoter']['lastName'] ?? '' }}</p>
                                            </td>
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                <span class="relative inline-block px-3 py-1 font-semibold leading-tight rounded-full whitespace-no-wrap
                                                    @switch($incident['statusIncident'])
                                                        @case('PENDIENTE') status-pending @break
                                                        @case('EN_PROGRESO') status-in-progress @break
                                                        @case('RESUELTO') status-resolved @break
                                                        @case('CERRADO') status-closed @break
                                                        @default bg-gray-100 text-gray-700 @break
                                                    @endswitch">
                                                    <span class="relative">{{ str_replace('_', ' ', $incident['statusIncident']) }}</span>
                                                </span>
                                            </td>                                                
                                            {{-- Celda de Acciones --}}
                                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm text-center">
                                                <div class="flex justify-center items-center space-x-2 md:space-x-3">
                                                    {{-- 1. Botón Ver Detalles (Ojo) --}}
                                                    <button type="button" class="text-blue-600 hover:text-blue-900"
                                                            title="Ver Detalles"
                                                            onclick="openShowModal(this)"
                                                            {{-- Pasar datos, incluyendo URL de evidencia si existe --}}
                                                            data-id="{{ $incident['incidentId'] }}"
                                                            data-name="{{ htmlspecialchars($incident['incidentName'], ENT_QUOTES) }}"
                                                            data-description="{{ htmlspecialchars($incident['description'], ENT_QUOTES) }}"
                                                            data-status="{{ $incident['statusIncident'] }}"
                                                            data-created_at="{{ $createdAtFormatted }}"
                                                            data-updated_at="{{ $updatedAtFormatted }}"
                                                            data-resolved_at="{{ $resolvedAtFormatted ?? '' }}"
                                                            data-promoter='{!! htmlspecialchars($promoterJson, ENT_QUOTES) !!}'
                                                            data-assigned_to='{!! htmlspecialchars($assignedToJson, ENT_QUOTES) !!}'
                                                            data-address='{!! htmlspecialchars($addressJson, ENT_QUOTES) !!}'
                                                            data-evidence_url="{{ $evidenceUrl ? htmlspecialchars($evidenceUrl, ENT_QUOTES) : '' }}"
                                                            >
                                                        <i class="material-icons text-lg">visibility</i>
                                                    </button>

                                                    {{-- 2. Botón Mapa --}}
                                                    <button type="button" class="text-green-600 hover:text-green-900"
                                                            title="Ver Ubicación"
                                                            onclick="openMapModal('{{ $incident['address']['latitude'] ?? '' }}', '{{ $incident['address']['longitude'] ?? '' }}', '{{ htmlspecialchars($incident['incidentName'], ENT_QUOTES) }}', '{{ htmlspecialchars(($incident['address']['street'] ?? '') . ' ' . ($incident['address']['number'] ?? '') . ', ' . ($incident['address']['neighborhood'] ?? ''), ENT_QUOTES) }}')"
                                                            {{ empty($incident['address']['latitude']) || empty($incident['address']['longitude']) ? 'disabled class=text-gray-400' : '' }}>
                                                        <i class="material-icons text-lg">place</i>
                                                    </button>

                                                        {{-- 3. Botón Cambiar Estado (Modal) --}}
                                                    <button type="button" class="text-yellow-600 hover:text-yellow-900"
                                                            title="Cambiar Estado"
                                                            onclick="openStatusModal(this)"
                                                            data-id="{{ $incident['incidentId'] }}"
                                                            data-current-status="{{ $incident['statusIncident'] }}">
                                                        <i class="material-icons text-lg">published_with_changes</i>
                                                    </button>

                                                    {{-- 4. Botón Eliminar --}}
                                                    <form action="{{ route('incidents.delete', $incident['incidentId']) }}" method="POST" class="inline" onsubmit="return confirmDelete(event, this);">
                                                        @csrf
                                                        @method('DELETE')
                                                        <button type="submit" class="text-red-600 hover:text-red-900" title="Eliminar Incidencia">
                                                            <i class="material-icons text-lg">delete</i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    @endforeach
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div> 

            <!-- Modal Ver Detalles Incidencia -->
            <div id="showIncidentModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-600 bg-opacity-50 transition-opacity duration-300" onclick="closeShowModalOnClickOutside(event)">
                 <div class="bg-white rounded-lg shadow-xl p-6 w-11/12 max-w-3xl max-h-[90vh] overflow-y-auto transform transition-transform duration-300 scale-95" onclick="event.stopPropagation()">
                    <div class="flex justify-between items-center mb-4 border-b pb-2">
                        <h2 class="text-xl font-semibold text-gray-700">Detalle de Incidencia #<span id="showModalIncidentId"></span></h2>
                        <button onclick="closeShowModal()" class="text-gray-400 hover:text-gray-700">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-4 text-sm">
                        <div>
                            <h3 class="text-base font-semibold text-gray-600 mb-2">Información General</h3>
                            <p class="mb-1"><strong>Nombre:</strong> <span id="showModalIncidentName"></span></p>
                            <p class="mb-2"><strong>Descripción:</strong> <span id="showModalDescription"> </p>
                            <p class="mb-1"><strong>Estado:</strong> <span id="showModalStatus" class="font-semibold"></span></p>
                            <p class="mb-1"><strong>Creada el:</strong> <span id="showModalCreatedAt"></span></p>
                            <p class="mb-1"><strong>Última Actualización:</strong> <span id="showModalUpdatedAt"></span></p>
                            <p id="showModalResolvedAtContainer" class="mb-1 hidden"><strong>Resuelto el:</strong> <span id="showModalResolvedAt"></span></p>

                            <h3 class="text-base font-semibold text-gray-600 mt-4 mb-2">Evidencia</h3>
                            <div id="showModalEvidenceContainer">
                                <img id="showModalEvidenceImage" src="" alt="Evidencia de incidencia" class="hidden"> {{-- Oculta por defecto --}}
                                <div id="showModalEvidencePlaceholder">
                                    <span>No hay evidencia adjunta</span>
                                </div>
                            </div>
                        </div>

                        <div>
                            <h3 class="text-base font-semibold text-gray-600 mb-2">Personas Involucradas</h3>
                            <div class="mb-4">
                                <p class="mb-1"><strong>Promotor:</strong></p>
                                <div class="ml-4 p-2 bg-gray-50 border rounded text-xs">
                                    <p>Nombre: <span id="showModalPromoterName"></span></p>
                                    <p>Email: <span id="showModalPromoterEmail"></span></p>
                                </div>
                            </div>
                            <div class="mb-4">
                                <p class="mb-1"><strong>Asignado a:</strong></p>
                                <div class="ml-4 p-2 bg-gray-50 border rounded text-xs">
                                    <p>Nombre: <span id="showModalAssignedToName"></span></p>
                                    <p>Email: <span id="showModalAssignedToEmail"></span></p>
                                </div>
                            </div>

                            <h3 class="text-base font-semibold text-gray-600 mb-2">Ubicación</h3>
                            <div id="showModalAddressContainer" class="space-y-1 text-xs"></div>
                        </div>
                    </div> 
                 </div>
            </div>


            <!-- Modal Cambiar Estado -->
            <div id="statusChangeModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-600 bg-opacity-50 transition-opacity duration-300" onclick="closeStatusModalOnClickOutside(event)">
                <div class="bg-white rounded-lg shadow-xl p-6 w-full max-w-md transform transition-transform duration-300 scale-95" onclick="event.stopPropagation()">
                     <div class="flex justify-between items-center mb-4 border-b pb-2">
                         <h2 class="text-xl font-semibold text-gray-700">Cambiar Estado de Incidencia</h2>
                         <button onclick="closeStatusModal()" class="text-gray-400 hover:text-gray-700">
                             <i class="material-icons">close</i>
                         </button>
                     </div>
                     <form id="statusChangeForm" action="" method="POST">
                         @csrf
                         @method('PUT')
                         <div class="mb-4">
                             <label for="statusIncidentSelect" class="block text-gray-700 text-sm font-bold mb-2">Nuevo Estado:</label>
                             <select id="statusIncidentSelect" name="statusIncident" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-blue-500" required>
                                <option value="PENDIENTE">PENDIENTE</option>
                                <option value="EN_PROGRESO">EN PROGRESO</option>
                                <option value="RESUELTO">RESUELTO</option>
                                <option value="NO_RESUELTO">NO_RESUELTO</option>
                                <option value="CANCELADO">CANCELADO</option>
                             </select>
                         </div>
                         <div class="mt-6 flex justify-end space-x-3">
                             <button type="button" onclick="closeStatusModal()" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                                 Cancelar
                             </button>
                             <button type="submit" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                                 <i class="material-icons mr-2">save</i>
                                 Guardar Estado
                             </button>
                         </div>
                     </form>
                 </div>
            </div>

            <!-- Modal Mapa (SOLO para el botón de ubicación) -->
            <div id="mapModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-600 bg-opacity-50 transition-opacity duration-300" onclick="closeMapModalOnClickOutside(event)">
                  <div class="bg-white rounded-lg shadow-xl p-4 md:p-6 w-11/12 max-w-3xl transform transition-transform duration-300 scale-95" onclick="event.stopPropagation()">
                    <div class="flex justify-between items-center mb-4 border-b pb-2">
                        <h2 id="mapModalTitle" class="text-xl font-semibold text-gray-700">Ubicación de la Incidencia</h2>
                        <button onclick="closeMapModal()" class="text-gray-400 hover:text-gray-700">
                            <i class="material-icons">close</i>
                        </button>
                    </div>
                    <div class="mb-3">
                        <p class="text-sm text-gray-600"><strong>Incidencia:</strong> <span id="mapIncidentName"></span></p>
                        <p class="text-sm text-gray-600"><strong>Dirección:</strong> <span id="mapIncidentAddress"></span></p>
                    </div>
                    <div id="mapContainerWrapper">
                        <div id="mapContainer" class="rounded border border-gray-300"></div> {{-- Aquí SÍ va el mapa --}}
                        <div id="mapErrorMessage" class="hidden p-4 text-center text-red-700 bg-red-100 rounded mt-2"></div>
                    </div>
                     <div class="mt-4 flex justify-end">
                         <button type="button" onclick="closeMapModal()" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                             Cerrar
                         </button>
                     </div>
                </div>
            </div>

        </div>
    </div>

     @if(session('success'))
         <script> Swal.fire({ title: '¡Éxito!', text: '{{ session('success') }}', icon: 'success', timer: 3000, showConfirmButton: false, toast: true, position: 'top-end' }); </script>
    @endif
    @if(session('error'))
         <script> Swal.fire({ title: '¡Error!', text: '{{ session('error') }}', icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#d33' }); </script>
    @endif

    <script>
        var mapInstance = null; // Instancia para el modal de ubicación (el único mapa ahora)

        // --- Inicialización DataTables ---
        $(document).ready(function() {
            $('#incidentTable').DataTable({
                 "language": {
                    "url": "js/Spanish.json",
                    "emptyTable": "No hay incidencias asignadas para mostrar."
                 },
            });
        });

        // --- Funciones para Modal Ver Detalles (ACTUALIZADO SIN MAPA INTERNO) ---
        function openShowModal(button) {
            const modal = document.getElementById('showIncidentModal');
            const modalContent = modal.querySelector('.transform');

            // Resetear estado de evidencia
            $('#showModalEvidenceImage').addClass('hidden').attr('src', '');
            $('#showModalEvidencePlaceholder').removeClass('hidden');


            try {
                // Obtener datos del botón
                const id = button.dataset.id;
                const name = button.dataset.name;
                const description = button.dataset.description;
                const status = button.dataset.status;
                const createdAt = button.dataset.created_at;
                const updatedAt = button.dataset.updated_at;
                const resolvedAt = button.dataset.resolved_at;
                const promoter = JSON.parse(button.dataset.promoter || 'null');
                const assignedTo = JSON.parse(button.dataset.assigned_to || 'null');
                const address = JSON.parse(button.dataset.address || 'null');
                const evidenceUrl = button.dataset.evidence_url; // Obtener URL de evidencia

                 console.log("Datos para modal detalle (sin mapa):", { id, name, status, evidenceUrl});

                // --- Llenar campos básicos (igual que antes) ---
                $('#showModalIncidentId').text(id || 'N/A');
                $('#showModalIncidentName').text(name || 'N/A');
                $('#showModalDescription').text(description || 'Sin descripción.');
                $('#showModalCreatedAt').text(createdAt || 'N/A');
                $('#showModalUpdatedAt').text(updatedAt || 'N/A');

                // --- Llenar estado con estilo (igual que antes) ---
                const statusSpan = $('#showModalStatus');
                statusSpan.text(status ? status.replace('_', ' ') : 'N/A');
                statusSpan.removeClass('status-pending status-in-progress status-resolved status-closed bg-yellow-100 text-yellow-900 bg-blue-100 text-blue-900 bg-green-100 text-green-900 bg-gray-100 text-gray-700 px-2 py-0.5 rounded');
                switch(status) {
                    case 'PENDIENTE': statusSpan.addClass('status-pending bg-yellow-100 text-yellow-900 px-2 py-0.5 rounded'); break;
                    case 'EN_PROGRESO': statusSpan.addClass('status-in-progress bg-blue-100 text-blue-900 px-2 py-0.5 rounded'); break;
                    case 'RESUELTO': statusSpan.addClass('status-resolved bg-green-100 text-green-900 px-2 py-0.5 rounded'); break;
                    case 'CERRADO': statusSpan.addClass('status-closed bg-gray-100 text-gray-700 px-2 py-0.5 rounded'); break;
                    default: statusSpan.addClass('bg-gray-100 text-gray-700 px-2 py-0.5 rounded'); break;
                }

                // --- Mostrar/Ocultar fecha de resolución (igual que antes) ---
                if (resolvedAt) {
                    $('#showModalResolvedAt').text(resolvedAt);
                    $('#showModalResolvedAtContainer').removeClass('hidden');
                } else {
                    $('#showModalResolvedAtContainer').addClass('hidden');
                }

                // --- Llenar datos Promotor y Asignado A (igual que antes) ---
                $('#showModalPromoterName').text(promoter ? `${promoter.firstName || ''} ${promoter.lastName || ''}`.trim() || 'N/A' : 'N/A');
                $('#showModalPromoterEmail').text(promoter?.email || 'N/A');
                $('#showModalAssignedToName').text(assignedTo ? `${assignedTo.firstName || ''} ${assignedTo.lastName || ''}`.trim() || 'N/A' : 'N/A');
                $('#showModalAssignedToEmail').text(assignedTo?.email || 'N/A');

                // --- Llenar Dirección (SIN MAPA) ---
                const addressContainer = $('#showModalAddressContainer');
                addressContainer.empty();
                if (address) {
                    addressContainer.append(`<p><strong>Calle:</strong> ${address.street || 'N/A'}</p>`);
                    addressContainer.append(`<p><strong>Número Ext:</strong> ${address.number || 'N/A'}</p>`);
                    if (address.interiorNumber) {
                        addressContainer.append(`<p><strong>Número Int:</strong> ${address.interiorNumber}</p>`);
                    }
                    addressContainer.append(`<p><strong>Colonia:</strong> ${address.neighborhood || 'N/A'}</p>`);
                    addressContainer.append(`<p><strong>C.P.:</strong> ${address.zipCode || 'N/A'}</p>`);
                    addressContainer.append(`<p><strong>Ciudad:</strong> ${address.city || 'N/A'}</p>`);
                    addressContainer.append(`<p><strong>Estado:</strong> ${address.state || 'N/A'}</p>`);
                    addressContainer.append(`<p><strong>País:</strong> ${address.country || 'N/A'}</p>`);
                } else {
                    addressContainer.append('<p>No hay información de dirección disponible.</p>');
                }

                // --- Manejar Evidencia ---
                if (evidenceUrl) {
                    $('#showModalEvidenceImage').attr('src', evidenceUrl).removeClass('hidden');
                    $('#showModalEvidencePlaceholder').addClass('hidden');
                } else {
                    $('#showModalEvidenceImage').addClass('hidden').attr('src', '');
                    $('#showModalEvidencePlaceholder').removeClass('hidden');
                }


                // --- Mostrar modal (igual que antes) ---
                $(modal).removeClass('hidden').addClass('flex');
                setTimeout(() => {
                $(modalContent).removeClass('scale-95').addClass('scale-100');
                $(modal).removeClass('opacity-0').addClass('opacity-100');
                }, 50);

             } catch (e) {
                console.error("Error al abrir o parsear datos para el modal de detalles:", e);
                Swal.fire('Error', 'No se pudo cargar la información detallada de la incidencia.', 'error');
                closeShowModal(); // Intenta cerrar si hubo error
             }
        }

        function closeShowModal() {
            const modal = document.getElementById('showIncidentModal');
            const modalContent = modal.querySelector('.transform');
            $(modalContent).removeClass('scale-100').addClass('scale-95');
            $(modal).removeClass('opacity-100').addClass('opacity-0');
            setTimeout(() => {
                $(modal).removeClass('flex').addClass('hidden');
                // No hay mapa que destruir aquí
            }, 300);
        }

        function closeShowModalOnClickOutside(event) {
             if (event.target.id === 'showIncidentModal') {
                 closeShowModal();
             }
        }

        // --- Funciones para Modal de Estado (sin cambios) ---
        function openStatusModal(button) { 
            let incidentId = button.getAttribute('data-id');
            let currentStatus = button.getAttribute('data-current-status');
            document.getElementById('statusIncidentSelect').value = currentStatus;
            let form = document.getElementById('statusChangeForm');
            form.action = `/incidents/status/${incidentId}`;
            const modal = document.getElementById('statusChangeModal');
            const modalContent = modal.querySelector('.transform');
            $(modal).removeClass('hidden').addClass('flex');
            setTimeout(() => { $(modalContent).removeClass('scale-95').addClass('scale-100'); $(modal).removeClass('opacity-0').addClass('opacity-100'); }, 50);
        }

        function closeStatusModal() { 
            const modal = document.getElementById('statusChangeModal');
            const modalContent = modal.querySelector('.transform');
            $(modalContent).removeClass('scale-100').addClass('scale-95');
            $(modal).removeClass('opacity-100').addClass('opacity-0');
            setTimeout(() => { $(modal).removeClass('flex').addClass('hidden'); }, 300);
        }

        function closeStatusModalOnClickOutside(event) { 
             if (event.target.id === 'statusChangeModal') { closeStatusModal(); }
        }

        // --- Funciones para Modal de Mapa (Ubicación - sin cambios) ---
        function openMapModal(latitude, longitude, incidentName, incidentAddress) { 
            const modal = document.getElementById('mapModal');
            const modalContent = modal.querySelector('.transform');
            const mapCont = document.getElementById('mapContainer');
            const mapErrorMsg = document.getElementById('mapErrorMessage');
             if (mapInstance) { mapInstance.remove(); mapInstance = null; }
            $(mapCont).show(); $(mapErrorMsg).hide().text('');
            $('#mapIncidentName').text(incidentName || 'N/A');
            $('#mapIncidentAddress').text(incidentAddress || 'N/A');
            $(modal).removeClass('hidden').addClass('flex');
            setTimeout(() => { $(modalContent).removeClass('scale-95').addClass('scale-100'); $(modal).removeClass('opacity-0').addClass('opacity-100'); }, 50);
            const lat = parseFloat(latitude); const lon = parseFloat(longitude);
            if (!isNaN(lat) && !isNaN(lon) && lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180) {
                setTimeout(() => {
                    try {
                        mapInstance = L.map('mapContainer').setView([lat, lon], 15);
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19, attribution: '© OpenStreetMap' }).addTo(mapInstance);
                        L.marker([lat, lon]).addTo(mapInstance).bindPopup(`<b>${incidentName}</b><br>${incidentAddress}`).openPopup();
                        mapInstance.invalidateSize();
                    } catch (e) { $(mapCont).hide(); $(mapErrorMsg).show().text('Error al cargar el mapa.'); }
                }, 150);
            } else { $(mapCont).hide(); $(mapErrorMsg).show().text('No se proporcionaron coordenadas válidas.'); }
        }

        function closeMapModal() { 
             const modal = document.getElementById('mapModal');
             const modalContent = modal.querySelector('.transform');
             $(modalContent).removeClass('scale-100').addClass('scale-95');
             $(modal).removeClass('opacity-100').addClass('opacity-0');
             setTimeout(() => {
                 $(modal).removeClass('flex').addClass('hidden');
                 if (mapInstance) { try { mapInstance.remove(); } catch(e){} mapInstance = null; }
             }, 300);
        }

        function closeMapModalOnClickOutside(event) { 
             if (event.target.id === 'mapModal') { closeMapModal(); }
        }

        // --- Función para Confirmación de Eliminación (sin cambios) ---
        function confirmDelete(event, form) { 
            event.preventDefault();
            Swal.fire({ title: '¿Estás seguro?', text: "¡Esta acción no se puede revertir!", icon: 'warning',
                 showCancelButton: true, confirmButtonColor: '#d33', cancelButtonColor: '#3085d6',
                 confirmButtonText: '<i class="material-icons mr-2">delete_forever</i> Sí, ¡eliminar!', cancelButtonText: 'Cancelar', reverseButtons: true,
             }).then((result) => {
                 if (result.isConfirmed) {
                     Swal.fire({ title: 'Eliminando...', text: 'Por favor espera.', allowOutsideClick: false, didOpen: () => { Swal.showLoading() } });
                     setTimeout(() => form.submit(), 300);
                 }
             });
            return false;
        }

        // --- Script para Sidebar (sin cambios) ---
        document.addEventListener("DOMContentLoaded", function() { 
            const toggleButton = document.getElementById("toggleSidebar");
            const sidebar = document.getElementById("sidebar");
            const mainContent = document.getElementById("mainContent");
            if(toggleButton && sidebar && mainContent) {
                 toggleButton.addEventListener("click", function() {
                    sidebar.classList.toggle("-translate-x-full");
                     if (sidebar.classList.contains("-translate-x-full")) { mainContent.classList.remove("md:ml-80"); mainContent.classList.add("ml-0"); }
                     else { mainContent.classList.add("md:ml-80"); mainContent.classList.remove("ml-0"); }
                 });
                 if (window.innerWidth < 768 && !sidebar.classList.contains("-translate-x-full")) {
                    sidebar.classList.add("-translate-x-full"); mainContent.classList.remove("md:ml-80"); mainContent.classList.add("ml-0");
                 }
             }
        });
    </script>

</body>
</html>