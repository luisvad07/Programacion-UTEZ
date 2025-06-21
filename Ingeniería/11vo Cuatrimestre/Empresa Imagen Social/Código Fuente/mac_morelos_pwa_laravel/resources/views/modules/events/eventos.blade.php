<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Eventos</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">
            <div class="bg-white p-8 rounded-md w-full">
                <div class=" flex items-center justify-between pb-6">
                    <div>
                        <h2 class="text-gray-600 font-semibold">Gestión de Eventos</h2>
                        <span class="text-xs">Todos los eventos del Sistema</span>
                    </div>
                    <div class="flex items-center justify-between">

                        <div class="lg:ml-40 ml-10 space-x-8">
                            @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                                <!-- Botones de Nueva Reporte y Crear Evento podrían ir aquí -->
                                <button class="bg-green-600 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer flex items-center" onclick="openCreateModal()">
                                    <i class="material-icons mr-2">add</i>
                                    Crear Evento
                                </button>
                            @endif
                        </div>
                    </div>
                </div>
                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px:8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                            <table id="eventTable" class="min-w-full leading-normal">
                                <thead>
                                    <tr style="background-color: #f2f2f2;">
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            ID
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Nombre
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Descripción
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Fecha Inicio
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Fecha Fin
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Ubicación
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Estado
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Acciones
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="eventTableBody">
                                    @if(isset($invalidInput) && $invalidInput)
                                        <tr>
                                            <td colspan="8" class="px-5 py-5 bg-white text-sm text-center">
                                                <p class="text-red-900 whitespace-no-wrap">Entrada no válida.</p>
                                            </td>
                                        </tr>
                                    @elseif(isset($notFoundMessage))
                                        <tr>
                                            <td colspan="8" class="px-5 py-5 bg-white text-sm text-center">
                                                <p class="text-gray-900 whitespace-no-wrap">{{ $notFoundMessage }}</p>
                                            </td>
                                        </tr>
                                    @elseif(!empty($events))
                                        @foreach($events as $event)
                                            <tr>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ $event['eventId'] }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ $event['name'] }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ $event['description'] }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ \Carbon\Carbon::parse($event['startDate'])->format('d/m/Y H:i') }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ \Carbon\Carbon::parse($event['endDate'])->format('d/m/Y H:i') }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="text-gray-900 whitespace-no-wrap">{{ $event['location'] }}</p>
                                                </td>
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <p class="whitespace-no-wrap 
                                                        @if($event['statusEvent'] == 'ACTIVO') 
                                                            status-activeEvent
                                                        @elseif($event['statusEvent'] == 'FINALIZADO') 
                                                            status-finished
                                                        @elseif($event['statusEvent'] == 'CANCELADO') 
                                                            status-canceled
                                                        @elseif($event['statusEvent'] == 'PENDIENTE') 
                                                            status-pending
                                                        @endif">
                                                        {{ $event['statusEvent'] }}
                                                    </p>
                                                </td>                                                                                             
                                                <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                                    <div class="flex items-center space-x-2">
                                                        <button class="text-gray-600 hover:text-gray-900"
                                                                onclick="openViewModal(this)"
                                                                data-id="{{ $event['eventId'] }}"
                                                                data-name="{{ $event['name'] }}"
                                                                data-description="{{ $event['description'] }}"
                                                                data-start-date="{{ $event['startDate'] }}"
                                                                data-end-date="{{ $event['endDate'] }}"
                                                                data-address="{{ json_encode($event['address'] ?? []) }}"
                                                                data-location="{{ $event['location'] }}"
                                                                data-status="{{ $event['statusEvent'] }}"
                                                                data-created-by="{{ json_encode($event['createdBy'] ?? []) }}"
                                                                data-created-at="{{ $event['createdAt'] }}"
                                                                data-updated-at="{{ $event['updatedAt'] }}">
                                                            <i class="material-icons">visibility</i>
                                                        </button>
                                                        @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                                                            <!-- Botón de Cambiar Estado -->
                                                            <button class="text-emerald-600 hover:text-emerald-900"
                                                                    onclick="openChangeStatusModal(this)"
                                                                    data-id="{{ $event['eventId'] }}"
                                                                    data-status="{{ $event['statusEvent'] }}">
                                                                <i class="material-icons">flag</i>
                                                            </button>
                                                        @endif
                                                        <!-- Icono de Mapa -->
                                                        <button class="text-blue-600 hover:text-blue-900"
                                                                onclick="openLocationModal(this)"
                                                                data-latitude="{{ $event['address']['latitude'] ?? 0.0 }}"
                                                                data-longitude="{{ $event['address']['longitude'] ?? 0.0 }}"
                                                                data-name="{{ $event['name'] }}"  >
                                                            <i class="material-icons">location_on</i>
                                                        </button>
                                                        @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                                                            <!-- Botón de Editar -->
                                                            <button class="text-indigo-600 hover:text-indigo-900"
                                                                    onclick="openEditModal(this)"
                                                                    data-id="{{ $event['eventId'] }}"
                                                                    data-name="{{ $event['name'] }}"
                                                                    data-description="{{ $event['description'] }}"
                                                                    data-start-date="{{ $event['startDate'] }}"
                                                                    data-end-date="{{ $event['endDate'] }}"
                                                                    data-address="{{ json_encode($event['address'] ?? []) }}"
                                                                    data-location="{{ $event['location'] }}"
                                                                    data-latitude="{{ $event['address']['latitude'] ?? 0.0 }}"
                                                                    data-longitude="{{ $event['address']['longitude'] ?? 0.0 }}"
                                                                    data-status="{{ $event['statusEvent'] }}"
                                                                    data-created-by="{{ json_encode($event['createdBy'] ?? []) }}"
                                                                    data-created-at="{{ $event['createdAt'] }}"
                                                                    data-updated-at="{{ $event['updatedAt'] }}">
                                                                <i class="material-icons">edit</i>
                                                            </button>
                                                            <!-- Botón de Eliminar (si es necesario) -->
                                                            <form action="{{ route('events.delete', $event['eventId']) }}" method="POST" style="display: inline;">
                                                                @csrf
                                                                @method('DELETE')
                                                                <button type="submit" class="text-red-600 hover:text-red-900" onclick="confirmDelete(event, this)">
                                                                    <i class="material-icons">delete</i>
                                                                </button>
                                                            </form>
                                                        @endif
                                                    </div>
                                                </td>
                                            </tr>
                                        @endforeach
                                    @endif
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Crear Evento -->
            <div id="eventCreateModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentCreate" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl max-h-[90vh] overflow-y-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Crear Evento</h2>
                        <button onclick="closeCreateModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Creación -->
                    <form id="createEventForm" action="{{ route('events.create') }}" method="POST">
                        @csrf

                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label for="create_name" class="block text-gray-700 text-sm font-bold mb-2">Nombre:</label>
                                <input type="text" id="create_name" name="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="create_location" class="block text-gray-700 text-sm font-bold mb-2">Ubicación:</label>
                                <input type="text" id="create_location" name="location" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="create_startDate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Inicio:</label>
                                <input type="datetime-local" id="create_startDate" name="startDate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="create_endDate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Fin:</label>
                                <input type="datetime-local" id="create_endDate" name="endDate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div class="col-span-2">
                                <label for="create_description" class="block text-gray-700 text-sm font-bold mb-2">Descripción:</label>
                                <textarea id="create_description" name="description" rows="3" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required></textarea>
                            </div>
                        </div>

                        <div class="mt-4">
                            <h3 class="text-lg font-semibold mb-2">Dirección</h3>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label for="create_street" class="block text-gray-700 text-sm font-bold mb-2">Calle:</label>
                                    <input type="text" id="create_street" name="street" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_number" class="block text-gray-700 text-sm font-bold mb-2">Número:</label>
                                    <input type="text" id="create_number" name="number" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_interiorNumber" class="block text-gray-700 text-sm font-bold mb-2">Número Interior:</label>
                                    <input type="text" id="create_interiorNumber" name="interiorNumber" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                </div>
                                <div>
                                    <label for="create_neighborhood" class="block text-gray-700 text-sm font-bold mb-2">Colonia:</label>
                                    <input type="text" id="create_neighborhood" name="neighborhood" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_zipCode" class="block text-gray-700 text-sm font-bold mb-2">Código Postal:</label>
                                    <input type="text" id="create_zipCode" name="zipCode" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_city" class="block text-gray-700 text-sm font-bold mb-2">Ciudad:</label>
                                    <input type="text" id="create_city" name="city" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_state" class="block text-gray-700 text-sm font-bold mb-2">Estado:</label>
                                    <input type="text" id="create_state" name="state" value="Morelos" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="create_country" class="block text-gray-700 text-sm font-bold mb-2">País:</label>
                                    <input type="text" id="create_country" name="country" value="México" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                            </div>
                        </div>

                        <div class="mt-4">
                            <h3 class="text-lg font-semibold mb-2">Ubicación en el Mapa</h3>
                            <div id="createMapContainer" style="height: 300px; width: 100%; z-index: 1;"></div>
                            <input type="hidden" id="create_latitude" name="latitude">
                            <input type="hidden" id="create_longitude" name="longitude">
                        </div>

                        <div class="mt-6 flex justify-end">
                            <button id="createButton" type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                                <i class="material-icons mr-2">save</i>
                                Crear Evento
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Modal Ver Evento -->
            <div id="eventViewModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentView" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl overflow-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Detalles del Evento</h2>
                        <button onclick="closeViewModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <div class="space-y-4">
                        <div class="flex items-center">
                            <i class="material-icons mr-2">event</i>
                            <strong>Nombre:</strong>
                            <span id="eventNameView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">description</i>
                            <strong>Descripción:</strong>
                            <span id="eventDescriptionView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">calendar_today</i>
                            <strong>Fecha de Inicio:</strong>
                            <span id="eventStartDateView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">calendar_today</i>
                            <strong>Fecha de Fin:</strong>
                            <span id="eventEndDateView" class="ml-2"></span>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-2">location_on</i>
                            <strong>Dirección:</strong>
                            <span id="eventAddressView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">place</i>
                            <strong>Ubicación:</strong>
                            <span id="eventLocationView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">event_available</i>
                            <strong>Estado:</strong>
                            <span id="eventStatusView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">account_circle</i>
                            <strong>Creado por:</strong>
                            <span id="eventCreatedByView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">event</i>
                            <strong>Fecha de Creación:</strong>
                            <span id="eventCreatedAtView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">update</i>
                            <strong>Fecha de Modificación:</strong>
                            <span id="eventUpdatedAtView" class="ml-2"></span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Mapa Estático -->
            <div id="eventLocationModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentLocation" class="bg-white rounded-lg p-8 w-11/12 max-w-2xl transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Ubicación del Evento: <span id="locationEventName"></span></h2>
                        <button onclick="closeLocationModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <div id="staticMapContainer" style="height: 400px; width: 100%; z-index: 1;"></div>
                </div>
            </div>

            <!-- Modal Editar Evento -->
            <div id="eventEditModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentEdit" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl max-h-[90vh] overflow-y-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Editar Evento</h2>
                        <button onclick="closeEditModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Edición -->
                    <form id="editEventForm" action="" method="POST">
                        @csrf
                        @method('PUT')

                        <input type="hidden" id="eventId" name="eventId">

                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label for="name" class="block text-gray-700 text-sm font-bold mb-2">Nombre:</label>
                                <input type="text" id="name" name="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="description" class="block text-gray-700 text-sm font-bold mb-2">Descripción:</label>
                                <textarea id="description" name="description" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required></textarea>
                            </div>
                            <div>
                                <label for="startDate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Inicio:</label>
                                <input type="datetime-local" id="startDate" name="startDate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="endDate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Fin:</label>
                                <input type="datetime-local" id="endDate" name="endDate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="location" class="block text-gray-700 text-sm font-bold mb-2">Ubicación:</label>
                                <input type="text" id="location" name="location" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="statusEvent" class="block text-gray-700 text-sm font-bold mb-2">Estado del Evento:</label>
                                <select id="statusEvent" name="statusEvent" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                    <option value="PENDIENTE">PENDIENTE</option>
                                    <option value="ACTIVO">ACTIVO</option>
                                    <option value="FINALIZADO">FINALIZADO</option>
                                    <option value="CANCELADO">CANCELADO</option>
                                </select>
                            </div>
                        </div>

                        <div class="mt-4">
                            <h3 class="text-lg font-semibold mb-2">Dirección</h3>
                            <input type="hidden" id="addressId" name="addressId">
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label for="street" class="block text-gray-700 text-sm font-bold mb-2">Calle/Avenida:</label>
                                    <input type="text" id="street" name="street" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="number" class="block text-gray-700 text-sm font-bold mb-2">Número:</label>
                                    <input type="text" id="number" name="number" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="interiorNumber" class="block text-gray-700 text-sm font-bold mb-2">Número Interior:</label>
                                    <input type="text" id="interiorNumber" name="interiorNumber" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                </div>
                                <div>
                                    <label for="neighborhood" class="block text-gray-700 text-sm font-bold mb-2">Colonia:</label>
                                    <input type="text" id="neighborhood" name="neighborhood" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="zipCode" class="block text-gray-700 text-sm font-bold mb-2">Código Postal:</label>
                                    <input type="text" id="zipCode" name="zipCode" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="city" class="block text-gray-700 text-sm font-bold mb-2">Ciudad:</label>
                                    <input type="text" id="city" name="city" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="state" class="block text-gray-700 text-sm font-bold mb-2">Estado:</label>
                                    <input type="text" id="state" name="state" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                                <div>
                                    <label for="country" class="block text-gray-700 text-sm font-bold mb-2">País:</label>
                                    <input type="text" id="country" name="country" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                </div>
                            </div>
                        </div>

                        <div class="mt-4">
                            <h3 class="text-lg font-semibold mb-2">Ubicación en el Mapa</h3>
                            <div id="editMapContainer" style="height: 300px; width: 100%; z-index: 1;"></div>
                            <input type="hidden" id="edit_latitude" name="latitude">
                            <input type="hidden" id="edit_longitude" name="longitude">
                        </div>


                        <div class="mt-6 flex justify-end">
                            <button id="saveButton" type="submit" class="bg-yellow-600 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                                <i class="material-icons mr-2">update</i>
                                <span id="buttonText">Guardar Cambios</span>
                                <span id="loadingSpinner" class="hidden ml-2">
                                    <i class="material-icons animate-spin">hourglass_empty</i>
                                </span>
                            </button>
                        </div>

                    </form>
                </div>
            </div>

            <!-- Modal Cambiar Estado -->
            <div id="eventChangeStatusModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentChangeStatus" class="bg-white rounded-lg p-8 w-11/12 max-w-md overflow-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Cambiar Estado del Evento</h2>
                        <button onclick="closeChangeStatusModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <form id="changeStatusForm" action="" method="POST">
                        @csrf
                        @method('PATCH')

                        <input type="hidden" id="changeStatusEventId" name="eventId">

                        <div class="mb-4">
                            <label for="statusEventSelect" class="block text-gray-700 text-sm font-bold mb-2">Nuevo Estado:</label>
                            <select id="statusEventSelect" name="statusEvent" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                <option value="PENDIENTE">PENDIENTE</option>
                                <option value="ACTIVO">ACTIVO</option>
                                <option value="FINALIZADO">FINALIZADO</option>
                                <option value="CANCELADO">CANCELADO</option>
                            </select>
                        </div>

                        <div class="mt-6 flex justify-end">
                            <button type="button" onclick="confirmChangeStatus()" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                                <i class="material-icons mr-2">save</i>
                                Guardar Estado
                            </button>
                        </div>
                    </form>
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
    let createMap;
    let editMap;
    let staticMap;

    $(document).ready(function() {
        $('#eventTable').DataTable({
            "language": {
                "url": "js/Spanish.json",
                "emptyTable": "No hay eventos disponibles."
            },
        });

         // Inicializar el mapa al cargar la página (opcional, dependiendo de tu lógica)
        initializeCreateMap();
    });

    function submitFormWithConfirmation() {
        // Usar SweetAlert2 para confirmar la acción
        Swal.fire({
            title: '¿Está seguro de que desea actualizar este Evento?',
            text: "Esta acción no se puede deshacer.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, actualizar',
            cancelButtonText: 'No, cancelar',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Mostrar el loading spinner y el texto de "Guardando"
                document.getElementById("saveButton").disabled = true;
                document.getElementById("buttonText").textContent = "Guardando...";
                document.getElementById("loadingSpinner").classList.remove("hidden");

                // Aquí, el formulario se va a enviar
                document.getElementById("editEventForm").submit();
            }
        });
    }

    let formatStartEndDate = (dateStr) => {
        let date = new Date(dateStr);
        if (!(date instanceof Date) || isNaN(date)) return 'No Disponible';

        let day = date.getUTCDate().toString().padStart(2, '0');  
        let month = (date.getUTCMonth() + 1).toString().padStart(2, '0');  
        let year = date.getUTCFullYear();
        let hours = date.getUTCHours();
        let minutes = date.getUTCMinutes().toString().padStart(2, '0');
        let seconds = date.getUTCSeconds().toString().padStart(2, '0');
        let ampm = hours >= 12 ? 'p.m.' : 'a.m.';
        
        hours = hours % 12;
        hours = hours ? hours : 12; 

        return `${day}/${month}/${year}, ${hours}:${minutes}:${seconds} ${ampm}`;
    };

    let formatDate = (dateStr) => {
        let date = new Date(dateStr);
        if (!(date instanceof Date) || isNaN(date)) return 'No Disponible';

        let day = date.getDate().toString().padStart(2, '0');  // Día con 2 dígitos
        let month = (date.getMonth() + 1).toString().padStart(2, '0');  // Mes con 2 dígitos
        let year = date.getFullYear();  // Año
        let hours = date.getHours();
        let minutes = date.getMinutes().toString().padStart(2, '0');
        let seconds = date.getSeconds().toString().padStart(2, '0');
        let ampm = hours >= 12 ? 'p.m.' : 'a.m.';
        hours = hours % 12;
        hours = hours ? hours : 12; // El '12' debe ser tratado como '12' en vez de '0'

        return `${day}/${month}/${year}, ${hours}:${minutes}:${seconds} ${ampm}`;
    };

    function openCreateModal() {
        const modal = document.getElementById('eventCreateModal');
        $(modal).removeClass('hidden').addClass('flex');

        const modalContent = document.getElementById('modalContentCreate');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)');

        setTimeout(() => {
            if (createMap) {
                createMap.invalidateSize();
            }
        }, 100);

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300);
        }, 50);
    }

    function closeCreateModal() {
        const modal = document.getElementById('eventCreateModal');
        const modalContent = document.getElementById('modalContentCreate');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    function openViewModal(button) {
        document.getElementById('eventNameView').innerText = button.getAttribute('data-name');
        document.getElementById('eventDescriptionView').innerText = button.getAttribute('data-description');
        document.getElementById('eventStartDateView').innerText = formatStartEndDate(button.getAttribute('data-start-date'));
        document.getElementById('eventEndDateView').innerText = formatStartEndDate(button.getAttribute('data-end-date'));
        document.getElementById('eventLocationView').innerText = button.getAttribute('data-location');
        document.getElementById('eventStatusView').innerText = button.getAttribute('data-status');

        // Formatear y mostrar la dirección
        let address = JSON.parse(button.getAttribute('data-address') || '{}');
        document.getElementById('eventAddressView').innerText =
            `${address.street ?? 'No Disponible'} ${address.number ?? 'S/N'}, ` +
            `Col. ${address.neighborhood ?? 'No Disponible'}, ${address.zipCode ?? 'No Disponible'} ${address.city ?? 'No Disponible'}, ` +
            `${address.state ?? 'No Disponible'}, ${address.country ?? 'No Disponible'}`;

        // Formatear y mostrar quién creó el evento
        let createdBy = JSON.parse(button.getAttribute('data-created-by') || '{}');
        document.getElementById('eventCreatedByView').innerText = `${createdBy.firstName ?? 'No Disponible'} ${createdBy.lastName ?? ''}`;

        document.getElementById('eventCreatedAtView').innerText = formatDate(button.getAttribute('data-created-at'));
        document.getElementById('eventUpdatedAtView').innerText = formatDate(button.getAttribute('data-updated-at'));

        // Mostrar el modal
        const modal = document.getElementById('eventViewModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentView');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)');

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300);
        }, 50);
    }

    function closeViewModal() {
        const modal = document.getElementById('eventViewModal');
        const modalContent = document.getElementById('modalContentView');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    function formatEditStartEndDate(dateStr) {
        if (!dateStr) return ''; // Si la fecha no está disponible

        let date = new Date(dateStr);
        if (isNaN(date)) return ''; // Verifica que la fecha sea válida

        let year = date.getUTCFullYear();
        let month = (date.getUTCMonth() + 1).toString().padStart(2, '0'); // Mes con 2 dígitos
        let day = date.getUTCDate().toString().padStart(2, '0'); // Día con 2 dígitos
        let hours = date.getUTCHours().toString().padStart(2, '0'); // Hora con 2 dígitos
        let minutes = date.getUTCMinutes().toString().padStart(2, '0'); // Minutos con 2 dígitos

        // Formatear la fecha para datetime-local sin segundos
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }

    function openEditModal(button) {
        // Obtener datos del botón
        let eventId = button.getAttribute('data-id');
        //console.log(eventId)
        let name = button.getAttribute('data-name');
        let description = button.getAttribute('data-description');
        let startDate = button.getAttribute('data-start-date');
        console.log(startDate);
        let endDate = button.getAttribute('data-end-date');
        console.log(endDate);
        let location = button.getAttribute('data-location');
        let statusEvent = button.getAttribute('data-status');
        let latitude = button.getAttribute('data-latitude');
        let longitude = button.getAttribute('data-longitude');

        console.log("Latitud:", latitude);
        console.log("Longitud:", longitude);

        // Formatear las fechas para que sean compatibles con el campo de entrada datetime-local
        let formattedStartDate = formatEditStartEndDate(startDate);
        console.log(formattedStartDate);
        let formattedEndDate = formatEditStartEndDate(endDate);
        console.log(formattedEndDate);

        let address = JSON.parse(button.getAttribute('data-address') || '{}');
        let addressId = address.addressId || '';

        // Establecer los valores en el formulario
        document.getElementById('name').value = name;
        document.getElementById('description').value = description;
        document.getElementById('startDate').value = formattedStartDate;
        document.getElementById('endDate').value = formattedEndDate;
        document.getElementById('location').value = location;
        document.getElementById('statusEvent').value = statusEvent;

        document.getElementById('street').value = address.street || '';
        document.getElementById('number').value = address.number || '';
        document.getElementById('interiorNumber').value = address.interiorNumber || '';
        document.getElementById('neighborhood').value = address.neighborhood || '';
        document.getElementById('zipCode').value = address.zipCode || '';
        document.getElementById('city').value = address.city || '';
        document.getElementById('state').value = address.state || '';
        document.getElementById('country').value = address.country || '';
        document.getElementById('addressId').value = addressId;

        // Construir la URL de actualización con el ID del evento
        let form = document.getElementById('editEventForm');
        form.action = `/events/update/${eventId}`; // Asumiendo que tienes una ruta definida para la actualización

        // Mostrar el modal
        const modal = document.getElementById('eventEditModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentEdit');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)'); // Establecer estado inicial

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300); // Animación
        }, 50);

        // Inicializar el mapa de edición y establecer la vista con las coordenadas del evento
        initializeEditMap(latitude, longitude);

        document.getElementById('editEventForm').onsubmit = function(event) {
            event.preventDefault(); // Prevenir el submit normal

            submitFormWithConfirmation(); // Llamar a la función para mostrar el confirm y enviar
        };
    }

    function closeEditModal() {
        const modal = document.getElementById('eventEditModal');
        const modalContent = document.getElementById('modalContentEdit');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    function openLocationModal(button) {
        let latitude = button.getAttribute('data-latitude');
        let longitude = button.getAttribute('data-longitude');
        let eventName = button.getAttribute('data-name');

        document.getElementById('locationEventName').innerText = eventName;

        // Mostrar el modal
        const modal = document.getElementById('eventLocationModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentLocation');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)');

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300);
        }, 50);

        // Inicializar y mostrar el mapa
        initializeStaticMap(latitude, longitude);
    }

    function closeLocationModal() {
        const modal = document.getElementById('eventLocationModal');
        const modalContent = document.getElementById('modalContentLocation');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    // Función para confirmar eliminación de evento
    function confirmDelete(event, button) {
        event.preventDefault(); // Evitar que el formulario se envíe inmediatamente

        Swal.fire({
            title: '¿Estás seguro?',
            text: "¡No podrás revertir esta acción!",
            icon: 'error',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminarlo!',
            cancelButtonText: 'Cancelar',
            reverseButtons: true,
        }).then((result) => {
            if (result.isConfirmed) {
                // Si confirma, enviamos el formulario
                button.closest('form').submit();
            }
        });
    }

    function openChangeStatusModal(button) {
        let eventId = button.getAttribute('data-id');
        let currentStatus = button.getAttribute('data-status');

        // Establecer el ID del evento en el formulario
        document.getElementById('changeStatusEventId').value = eventId;

        // Construir la URL para cambiar el estado (usando la ruta definida)
        let form = document.getElementById('changeStatusForm');
        form.action = `/events/change-status/${eventId}`;

        // Seleccionar el estado actual en el select (opcional)
        document.getElementById('statusEventSelect').value = currentStatus;

        // Mostrar el modal
        const modal = document.getElementById('eventChangeStatusModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentChangeStatus');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)');

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300);
        }, 50);
    }

    function closeChangeStatusModal() {
        const modal = document.getElementById('eventChangeStatusModal');
        const modalContent = document.getElementById('modalContentChangeStatus');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    function confirmChangeStatus() {
        Swal.fire({
            title: '¿Estás seguro de cambiar el estado?',
            text: "Se actualizará el estado del evento.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, cambiar!',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Si confirma, enviamos el formulario
                document.getElementById('changeStatusForm').submit();
            }
        });
    }

    document.addEventListener("DOMContentLoaded", function() {

        const toggleButton = document.getElementById("toggleSidebar");
        const sidebar = document.getElementById("sidebar");
        const mainContent = document.getElementById("mainContent");
        const logoutButton = document.getElementById('logout-button-p');

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
                if (createMap) {
                    createMap.invalidateSize();
                }
                if (editMap) {
                    editMap.invalidateSize();
                }
            }, 100);
        });
    });

    function initializeCreateMap() {
        const mapContainer = document.getElementById('createMapContainer');

        if (!mapContainer) {
            console.error("Map container not found.");
            return;
        }
        if (createMap) {
            // Si el mapa ya está inicializado, simplemente actualiza su tamaño
            createMap.invalidateSize();
            return;
        }

        createMap = L.map('createMapContainer').setView([18.9261, -99.23075], 13); // Coordenadas de Morelos

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
        }).addTo(createMap);

        let marker = L.marker([18.9261, -99.23075], { draggable: true }).addTo(createMap);

        marker.on('dragend', function(event) {
            let markerLatLng = marker.getLatLng();
            document.getElementById('create_latitude').value = markerLatLng.lat;
            document.getElementById('create_longitude').value = markerLatLng.lng;
        });

        // Set initial values
        document.getElementById('create_latitude').value = 18.9261;
        document.getElementById('create_longitude').value = -99.23075;

        // Ajustar el tamaño del mapa después de que el modal sea visible
        setTimeout(() => {
            createMap.invalidateSize();
        }, 500);
    }

    function initializeEditMap(latitude, longitude) {
        const mapContainer = document.getElementById('editMapContainer');

        if (!mapContainer) {
            console.error("Edit Map container not found.");
            return;
        }

        if (editMap) {
            editMap.remove(); // Elimina la instancia anterior del mapa
        }


        // Coerce latitude and longitude to numbers
        latitude = parseFloat(latitude);
        longitude = parseFloat(longitude);

        // Validate latitude and longitude
        if (isNaN(latitude) || isNaN(longitude)) {
            console.error("Invalid latitude or longitude:", latitude, longitude);
            latitude = 18.9261;   // Use a default latitude
            longitude = -99.23075; // Use a default longitude
        }

        editMap = L.map('editMapContainer').setView([latitude, longitude], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
        }).addTo(editMap);


        let marker = L.marker([latitude, longitude], { draggable: true }).addTo(editMap);

        marker.on('dragend', function(event) {
            let markerLatLng = marker.getLatLng();
            document.getElementById('edit_latitude').value = markerLatLng.lat;
            document.getElementById('edit_longitude').value = markerLatLng.lng;
        });


        // Set initial values
        document.getElementById('edit_latitude').value = latitude;
        document.getElementById('edit_longitude').value = longitude;

        // Ajustar el tamaño del mapa después de que el modal sea visible
        setTimeout(() => {
            editMap.invalidateSize();
        }, 500);
    }

    function initializeStaticMap(latitude, longitude) {
        const mapContainer = document.getElementById('staticMapContainer');

        if (!mapContainer) {
            console.error("Static Map container not found.");
            return;
        }

        if (staticMap) {
            staticMap.off();
            staticMap.remove();
        }

        // Coerce latitude and longitude to numbers
        latitude = parseFloat(latitude);
        longitude = parseFloat(longitude);

        // Validate latitude and longitude
        if (isNaN(latitude) || isNaN(longitude)) {
            console.error("Invalid latitude or longitude:", latitude, longitude);
            latitude = 18.9261;   // Use a default latitude
            longitude = -99.23075; // Use a default longitude
        }

        staticMap = L.map('staticMapContainer', {
            dragging: false,         // Deshabilita el arrastre del mapa
            touchZoom: false,        // Deshabilita el zoom táctil
            scrollWheelZoom: false,  // Deshabilita el zoom con la rueda del ratón
            doubleClickZoom: false,   // Deshabilita el zoom con doble clic
            boxZoom: false,           // Deshabilita el zoom con caja
            keyboard: false           // Deshabilita la navegación con teclado
        }).setView([latitude, longitude], 15);  // 15 es un nivel de zoom adecuado

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
        }).addTo(staticMap);

        L.marker([latitude, longitude]).addTo(staticMap);

        // Ajustar el tamaño del mapa después de que el modal sea visible
        setTimeout(() => {
            staticMap.invalidateSize();
        }, 100);
    }
</script>
</html>