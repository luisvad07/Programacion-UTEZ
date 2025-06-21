<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Noticias</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20"> {{-- Ajusta márgenes si navbar tiene altura fija --}}
    @include('layouts.navbar') {{-- Asume que tienes este layout --}}

    <div class="flex min-h-screen">
        @include('layouts.sidebar') {{-- Asume que tienes este layout --}}

        {{-- Contenido Principal --}}
        <div id="mainContent" class="flex-grow p-6 md:p-12 ml-0 md:ml-80 transition-all duration-300"> {{-- Ajustado ml inicial para móvil --}}
            <div class="bg-white p-6 md:p-8 rounded-lg shadow-md w-full mx-auto"> {{-- Quitado max-width para ocupar espacio --}}

                {{-- Encabezado y Botón Crear --}}
                <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between pb-6 gap-4 border-b mb-6">
                    <div>
                        <h2 class="text-gray-700 text-2xl font-semibold">Gestión de Noticias</h2>
                        <span class="text-sm text-gray-500">Todas las noticias registradas</span>
                    </div>
                    <div class="flex items-center">
                        <button class="bg-green-600 hover:bg-green-700 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer flex items-center shadow transition duration-150 ease-in-out" onclick="openCreateModal()">
                            <i class="material-icons mr-2">add</i>
                            Nueva Noticia {{-- Cambiado texto --}}
                        </button>
                    </div>
                </div>

                {{-- Tabla de Noticias --}}
                <div class="overflow-x-auto w-full">
                    <div class="inline-block min-w-full align-middle">
                        <div class="overflow-hidden shadow-sm rounded-lg border border-gray-200">
                            <table id="newsTable" class="min-w-full divide-y divide-gray-200">
                                <thead class="bg-gray-50">
                                    <tr>
                                        {{-- Clases de encabezado ajustadas ligeramente para consistencia --}}
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Título / Ubicación</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Categoría</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Creación</th>
                                        <th scope="col" class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Estado</th>
                                        <th scope="col" class="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                    @foreach ($news as $item)
                                        <tr class="hover:bg-gray-50 transition duration-150 ease-in-out">
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{{ $item['newsId'] ?? 'N/A' }}</td>
                                            <td class="px-6 py-4 whitespace-normal max-w-xs"> {{-- Allow wrapping, limit width --}}
                                                 <div class="text-sm font-medium text-gray-900">{{ $item['title'] ?? 'Sin título' }}</div>
                                                 @if(isset($item['address']['street']))
                                                    <div class="text-xs text-gray-500 mt-1">
                                                        <i class="material-icons text-xs mr-1 align-middle">location_on</i>{{ ($item['address']['street'] ?? '') . ' ' . ($item['address']['number'] ?? '') . ', Col. ' . ($item['address']['neighborhood'] ?? '') }}
                                                    </div>
                                                @endif
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                                @php
                                                    $categoryName = $categoryMap[$item['categories'][0]['categoryId']] ?? ($item['categories'][0]['name'] ?? 'Sin categoría');
                                                @endphp
                                                {{ $categoryName }}
                                            </td>
                                             <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                                {{ isset($item['createdAt']) ? \Carbon\Carbon::parse($item['createdAt'])->tz('America/Mexico_City')->isoFormat('DD/MM/YY HH:mm') : 'N/A' }}
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm">
                                                @php
                                                    $status = $item['statusNews'] ?? $item['status'] ?? 'DESCONOCIDO';
                                                    $statusClass = match(strtoupper($status)) {
                                                        'PUBLICADO' => 'bg-green-100 text-green-800',
                                                        'BORRADOR' => 'bg-yellow-100 text-yellow-800',
                                                        'PROGRAMADO' => 'bg-blue-100 text-blue-800', // Estado adicional?
                                                        default => 'bg-gray-100 text-gray-800'
                                                    };
                                                @endphp
                                                <span class="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full {{ $statusClass }}">
                                                    {{ ucfirst(strtolower($status)) }}
                                                </span>
                                                 @if (isset($item['publishDate']) && strtoupper($status) !== 'PUBLICADO' && \Carbon\Carbon::parse($item['publishDate'])->isFuture())
                                                    <span class="text-xs text-blue-600 block mt-1">(Prog: {{ \Carbon\Carbon::parse($item['publishDate'])->isoFormat('DD/MM/YY HH:mm') }})</span>
                                                @endif
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                                                <div class="flex items-center justify-center space-x-3">

                                                    @php
                                                        // Asegúrate de que $categoryName esté definida como antes
                                                        $categoryName = $categoryMap[$item['categories'][0]['categoryId']] ?? ($item['categories'][0]['name'] ?? 'Sin categoría');
                                                        // Codifica la dirección como JSON si no lo está ya (asegura que sea un string para data attribute)
                                                        $addressJson = json_encode($item['address'] ?? null);
                                                    @endphp

                                                    {{-- Botón Ver Detalles --}}
                                                    <button class="text-blue-600 hover:text-blue-800 transition duration-150 ease-in-out"
                                                            title="Ver Detalles"
                                                            onclick="openViewModal(this)"
                                                            data-id="{{ $item['newsId'] }}"
                                                            data-title="{{ $item['title'] ?? '' }}"
                                                            data-content="{{ $item['content'] ?? '' }}"
                                                            data-category-id="{{ $item['categories'][0]['categoryId'] ?? '' }}"
                                                            data-category-name="{{ $categoryName }}"
                                                            data-status="{{ $status }}"
                                                            data-created-at="{{ $item['createdAt'] ?? '' }}"
                                                            data-updated-at="{{ $item['updatedAt'] ?? '' }}"
                                                            data-publish-date="{{ $item['publishDate'] ?? '' }}"
                                                            data-address="{{ $addressJson }}"
                                                            data-images="{{ $item['imageNews'] ?? '' }}">
                                                        <i class="material-icons text-lg">visibility</i>
                                                    </button>

                                                    {{-- Botón Editar --}}
                                                    <button class="text-yellow-600 hover:text-yellow-800 transition duration-150 ease-in-out"
                                                            title="Editar Noticia"
                                                            onclick="openEditModal(this)"
                                                            {{-- Re-check all data attributes needed for edit --}}
                                                            data-id="{{ $item['newsId'] }}"
                                                            data-title="{{ $item['title'] ?? '' }}"
                                                            data-content="{{ $item['content'] ?? '' }}"
                                                            data-category="{{ $item['categories'][0]['categoryId'] ?? '' }}"
                                                            data-address-street="{{ $item['address']['street'] ?? '' }}"
                                                            data-address-number="{{ $item['address']['number'] ?? '' }}"
                                                            data-address-interiorNumber="{{ $item['address']['interiorNumber'] ?? '' }}"
                                                            data-address-neighborhood="{{ $item['address']['neighborhood'] ?? '' }}"
                                                            data-address-zipCode="{{ $item['address']['zipCode'] ?? '' }}"
                                                            data-address-city="{{ $item['address']['city'] ?? '' }}"
                                                            data-address-state="{{ $item['address']['state'] ?? '' }}"
                                                            data-address-country="{{ $item['address']['country'] ?? '' }}"
                                                            data-address-latitude="{{ $item['address']['latitude'] ?? '' }}"
                                                            data-address-longitude="{{ $item['address']['longitude'] ?? '' }}">
                                                        <i class="material-icons text-lg">edit</i>
                                                    </button>

                                                    {{-- Botón Imágenes --}}
                                                    <button class="text-green-600 hover:text-green-800 transition duration-150 ease-in-out"
                                                             title="Gestionar Imágenes"
                                                             onclick="openUploadImageModal(this)"
                                                             data-id="{{ $item['newsId'] }}">
                                                         <i class="material-icons text-lg">image</i>
                                                    </button>

                                                     {{-- Botón Eliminar --}}
                                                     <form action="{{ route('news.delete', $item['newsId']) }}" method="POST" class="inline" onsubmit="return confirmDelete(event);">
                                                        @csrf
                                                        @method('DELETE')
                                                        <button type="submit" class="text-red-600 hover:text-red-800 transition duration-150 ease-in-out" title="Eliminar Noticia">
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
        </div> 

        <!-- Modal Crear Noticia -->
        <div id="newsCreateModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
            {{-- Usamos un ID único para el contenido de este modal específico --}}
            <div id="modalContentCreateNews" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl max-h-[90vh] overflow-y-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                {{-- Encabezado Modal consistente --}}
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-xl font-semibold">Crear Nueva Noticia</h2>
                    <button onclick="closeCreateModal()" class="text-gray-500 hover:text-gray-900">
                        <i class="material-icons">close</i>
                    </button>
                </div>

                <!-- Formulario de Creación (una sola columna) -->
                <form id="createNewsForm" action="{{ route('news.create') }}" method="POST">
                    @csrf

                    {{-- Contenedor principal para los campos en una sola columna --}}
                    <div class="space-y-4"> {{-- Espacio vertical entre elementos --}}

                        {{-- Campos Principales --}}
                        <div>
                            <label for="create_news_title" class="block text-gray-700 text-sm font-bold mb-2">Título <span class="text-red-500">*</span></label>
                            <input type="text" id="create_news_title" name="title" value="{{ old('title') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required maxlength="255">
                        </div>

                        <div>
                            <label for="create_news_category_id" class="block text-gray-700 text-sm font-bold mb-2">Categoría <span class="text-red-500">*</span></label>
                            <select id="create_news_category_id" name="category_id" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                <option value="" disabled {{ old('category_id') ? '' : 'selected' }}>Seleccione una categoría</option>
                                @foreach($categoryMap as $id => $name)
                                    <option value="{{ $id }}" {{ old('category_id') == $id ? 'selected' : '' }}>{{ $name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div>
                            <label for="create_news_content" class="block text-gray-700 text-sm font-bold mb-2">Contenido <span class="text-red-500">*</span></label>
                            <textarea id="create_news_content" name="content" rows="4" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>{{ old('content') }}</textarea>
                        </div>

                        {{-- Sección de Dirección --}}
                        <div class="mt-6"> {{-- Margen superior para separar secciones --}}
                            <h3 class="text-lg font-semibold mb-3 border-b pb-2">Dirección</h3> {{-- Título de sección consistente --}}
                            <div class="space-y-4"> {{-- Espacio entre campos de dirección --}}
                                <div>
                                    <label for="create_news_address_street" class="block text-gray-700 text-sm font-bold mb-2">Calle/Avenida <span class="text-red-500">*</span></label>
                                    <input type="text" id="create_news_address_street" name="address_street" value="{{ old('address_street') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
    
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4"> {{-- Grid solo para Núm Ext/Int para que queden lado a lado en pantallas sm+ --}}
                                    <div>
                                        <label for="create_news_address_number" class="block text-gray-700 text-sm font-bold mb-2">Núm. Ext.</label>
                                        <input type="text" id="create_news_address_number" name="address_number" value="{{ old('address_number') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        
                                    </div>
                                    <div>
                                        <label for="create_news_address_interiorNumber" class="block text-gray-700 text-sm font-bold mb-2">Núm. Int.</label>
                                        <input type="text" id="create_news_address_interiorNumber" name="address_interiorNumber" value="{{ old('address_interiorNumber') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        
                                    </div>
                                </div>
                                <div>
                                    <label for="create_news_address_neighborhood" class="block text-gray-700 text-sm font-bold mb-2">Colonia <span class="text-red-500">*</span></label>
                                    <input type="text" id="create_news_address_neighborhood" name="address_neighborhood" value="{{ old('address_neighborhood') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
    
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4"> {{-- Grid solo para CP/Ciudad --}}
                                    <div>
                                        <label for="create_news_address_zipCode" class="block text-gray-700 text-sm font-bold mb-2">C.P. <span class="text-red-500">*</span></label>
                                        <input type="text" id="create_news_address_zipCode" name="address_zipCode" value="{{ old('address_zipCode') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                    <div>
                                        <label for="create_news_address_city" class="block text-gray-700 text-sm font-bold mb-2">Ciudad <span class="text-red-500">*</span></label>
                                        <input type="text" id="create_news_address_city" name="address_city" value="{{ old('address_city') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4"> {{-- Grid solo para Estado/País --}}
                                    <div>
                                        <label for="create_news_address_state" class="block text-gray-700 text-sm font-bold mb-2">Estado <span class="text-red-500">*</span></label>
                                        <input type="text" id="create_news_address_state" name="address_state" value="{{ old('address_state', 'Morelos') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                    <div>
                                        <label for="create_news_address_country" class="block text-gray-700 text-sm font-bold mb-2">País <span class="text-red-500">*</span></label>
                                        <input type="text" id="create_news_address_country" name="address_country" value="{{ old('address_country', 'México') }}" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                </div>
                            </div>
                        </div>

                        {{-- Mapa y Coordenadas Ocultas --}}
                        <div class="mt-6"> {{-- Margen superior para separar sección --}}
                            <h3 class="text-lg font-semibold mb-2">Ubicación en el Mapa <span class="text-red-500">*</span></h3> {{-- Título consistente --}}
                            <p class="text-xs text-gray-500 mb-2">Arrastra el marcador o haz clic para definir la ubicación.</p>
                            {{-- ID único para el mapa de noticias. Altura consistente. --}}
                            <div id="mapCreate" style="height: 250px; width: 100%; z-index: 1;"></div>
                            {{-- Inputs ocultos con IDs únicos --}}
                            <input type="hidden" id="create_news_address_latitude" name="address_latitude" value="{{ old('address_latitude') }}">
                            <input type="hidden" id="create_news_address_longitude" name="address_longitude" value="{{ old('address_longitude') }}">
                        </div>

                    </div> {{-- Fin Contenedor Principal --}}

                    {{-- Botones del Modal (estilo consistente) --}}
                    <div class="mt-6 flex justify-end">
                        <button type="button" onclick="closeCreateModal()" class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-3 flex items-center">
                            <i class="material-icons mr-2 text-base">cancel</i>Cancelar
                        </button>
                        <button type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                            <i class="material-icons mr-2">save</i>
                            Guardar Noticia
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Ver Detalles Noticia -->
        <div id="newsViewModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300" onclick="closeModalOutside(event, 'newsViewModal')">
            {{-- Aumentado max-w-4xl para más espacio y max-h-[95vh] --}}
            <div id="modalContentView" class="bg-white rounded-lg p-6 md:p-8 w-11/12 max-w-4xl max-h-[95vh] overflow-y-auto transform transition-transform duration-300 relative" style="transform: scale(0.8);">
                <div class="flex justify-between items-center mb-6 pb-4 border-b">
                    <h2 class="text-xl font-semibold text-gray-800">Detalles de la Noticia</h2>
                    <button onclick="closeViewModal()" class="text-gray-500 hover:text-gray-900 absolute top-4 right-4">
                        <i class="material-icons">close</i>
                    </button>
                </div>

                {{-- Contenido del Modal (dos columnas en pantallas md+) --}}
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

                    {{-- Columna Izquierda: Datos Principales y Dirección --}}
                    <div class="space-y-4 text-sm">
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">title</i>
                            <div>
                                <strong class="block text-gray-800">Título:</strong>
                                <span id="newsTitleView" class="text-gray-700"></span>
                            </div>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">description</i>
                            <div>
                                <strong class="block text-gray-800">Contenido:</strong>
                                {{-- Usar pre-wrap para respetar saltos de línea y espacios --}}
                                <p id="newsContentView" class="text-gray-700 whitespace-pre-wrap break-words"></p>
                            </div>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">category</i>
                            <div>
                                <strong class="block text-gray-800">Categoría:</strong>
                                <span id="newsCategoryView" class="text-gray-700"></span>
                            </div>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">label</i>
                            <div>
                                <strong class="block text-gray-800">Estado:</strong>
                                <span id="newsStatusView" class="text-gray-700"></span>
                            </div>
                        </div>

                        <div class="border-t my-4"></div>

                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">location_on</i>
                            <div>
                                <strong class="block text-gray-800">Dirección:</strong>
                                <span id="newsAddressView" class="text-gray-700"></span>
                            </div>
                        </div>
                        <div class="flex items-start">
                                <i class="material-icons mr-3 text-gray-600 pt-1">map</i>
                                <div>
                                    <strong class="block text-gray-800">Coordenadas:</strong>
                                    <span id="newsCoordsView" class="text-gray-700"></span>
                                </div>
                        </div>

                        <div class="border-t my-4"></div>

                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">today</i>
                            <div>
                                <strong class="block text-gray-800">Fecha Creación:</strong>
                                <span id="newsCreatedAtView" class="text-gray-700"></span>
                            </div>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">update</i>
                            <div>
                                <strong class="block text-gray-800">Última Modificación:</strong>
                                <span id="newsUpdatedAtView" class="text-gray-700"></span>
                            </div>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-3 text-gray-600 pt-1">schedule</i>
                            <div>
                                <strong class="block text-gray-800">Fecha Publicación/Prog:</strong>
                                <span id="newsPublishDateView" class="text-gray-700"></span>
                            </div>
                        </div>
                    </div>

                    {{-- Columna Derecha: Imágenes y Mapa --}}
                    <div class="space-y-6">
                        {{-- Sección de Imágenes --}}
                        <div>
                            <strong class="block text-gray-800 text-sm mb-2"><i class="material-icons text-base mr-1 align-bottom">photo_library</i> Imagen:</strong>
                            {{-- Contenedor para las imágenes con scroll si son muchas y grid --}}
                            <div id="newsImagesViewContainer" class="grid grid-cols-2 sm:grid-cols-3 gap-3 border rounded p-3 max-h-60 overflow-y-auto bg-gray-50">
                                {{-- Las imágenes se cargarán aquí por JS --}}
                                <p class="text-gray-500 text-xs col-span-full text-center py-4">Cargando imagen...</p>
                            </div>
                        </div>

                        {{-- Sección del Mapa --}}
                        <div>
                            <strong class="block text-gray-800 text-sm mb-2"><i class="material-icons text-base mr-1 align-bottom">place</i> Ubicación en Mapa:</strong>
                            {{-- Contenedor del mapa --}}
                            <div id="mapViewContainer" class="border rounded h-64 md:h-72 bg-gray-100"> {{-- Altura definida --}}
                                {{-- El mapa se cargará aquí por JS --}}
                                <p id="mapViewPlaceholder" class="text-gray-500 text-xs text-center pt-10">Cargando mapa...</p>
                                {{-- El div real del mapa se crea dinámicamente o se puede poner aquí oculto --}}
                                <div id="mapView" style="height: 100%; width: 100%; display: none;"></div>
                            </div>
                        </div>
                    </div>

                </div> {{-- Fin Grid --}}

                <div class="mt-8 pt-4 border-t flex justify-end">
                    <button type="button" onclick="closeViewModal()" class="btn-secondary-modal">Cerrar</button>
                </div>
            </div>
        </div>

        <!-- Modal Editar Noticia -->
        <div id="newsEditModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
            {{-- Usamos un ID único para el contenido de este modal específico --}}
            <div id="modalContentEdit" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl max-h-[90vh] overflow-y-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                {{-- Encabezado Modal consistente --}}
                <div class="flex justify-between items-center mb-6">
                    <h2 class="text-xl font-semibold text-gray-800">Editar Noticia</h2>
                    <button onclick="closeEditModal()" class="text-gray-500 hover:text-gray-900">
                        <i class="material-icons">close</i>
                    </button>
                </div>

                <!-- Formulario de Edición (una sola columna) -->
                <form id="editNewsForm" action="" method="POST">
                    @csrf
                    @method('PUT')

                    {{-- Contenedor principal para los campos en una sola columna --}}
                    <div class="space-y-4"> {{-- Espacio vertical entre elementos --}}

                        {{-- Campos Principales --}}
                        <div>
                            <label for="edit_title" class="block text-gray-700 text-sm font-bold mb-2">Título <span class="text-red-500">*</span></label>
                            {{-- Clases base consistentes --}}
                            <input type="text" id="edit_title" name="title" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required maxlength="255">
                        </div>

                        <div>
                            <label for="edit_category_id" class="block text-gray-700 text-sm font-bold mb-2">Categoría <span class="text-red-500">*</span></label>
                            <select id="edit_category_id" name="category_id" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                                <option value="" disabled>Seleccione una categoría</option>
                                @foreach($categoryMap as $id => $name)
                                    {{-- La selección se manejará con JS al abrir el modal --}}
                                    <option value="{{ $id }}">{{ $name }}</option>
                                @endforeach
                            </select>
                        </div>

                        <div>
                            <label for="edit_content" class="block text-gray-700 text-sm font-bold mb-2">Contenido <span class="text-red-500">*</span></label>
                            <textarea id="edit_content" name="content" rows="4" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required></textarea>
                        </div>

                        {{-- Sección de Dirección --}}
                        <div class="mt-6">
                            <h3 class="text-lg font-semibold mb-3 border-b pb-2">Dirección</h3>
                            <div class="space-y-4">
                                <div>
                                    <label for="edit_address_street" class="block text-gray-700 text-sm font-bold mb-2">Calle/Avenida <span class="text-red-500">*</span></label>
                                    <input type="text" id="edit_address_street" name="address_street" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
    
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                    <div>
                                        <label for="edit_address_number" class="block text-gray-700 text-sm font-bold mb-2">Núm. Ext.</label>
                                        <input type="text" id="edit_address_number" name="address_number" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        
                                    </div>
                                    <div>
                                        <label for="edit_address_interiorNumber" class="block text-gray-700 text-sm font-bold mb-2">Núm. Int.</label>
                                        <input type="text" id="edit_address_interiorNumber" name="address_interiorNumber" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        
                                    </div>
                                </div>
                                <div>
                                    <label for="edit_address_neighborhood" class="block text-gray-700 text-sm font-bold mb-2">Colonia <span class="text-red-500">*</span></label>
                                    <input type="text" id="edit_address_neighborhood" name="address_neighborhood" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
    
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                    <div>
                                        <label for="edit_address_zipCode" class="block text-gray-700 text-sm font-bold mb-2">C.P. <span class="text-red-500">*</span></label>
                                        <input type="text" id="edit_address_zipCode" name="address_zipCode" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                    <div>
                                        <label for="edit_address_city" class="block text-gray-700 text-sm font-bold mb-2">Ciudad <span class="text-red-500">*</span></label>
                                        <input type="text" id="edit_address_city" name="address_city" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                </div>
                                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                    <div>
                                        <label for="edit_address_state" class="block text-gray-700 text-sm font-bold mb-2">Estado <span class="text-red-500">*</span></label>
                                        <input type="text" id="edit_address_state" name="address_state" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                    <div>
                                        <label for="edit_address_country" class="block text-gray-700 text-sm font-bold mb-2">País <span class="text-red-500">*</span></label>
                                        <input type="text" id="edit_address_country" name="address_country" class="input-shadow shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
        
                                    </div>
                                </div>
                            </div> {{-- Fin space-y-4 dirección --}}
                        </div> {{-- Fin sección dirección --}}

                        {{-- Mapa y Coordenadas Ocultas --}}
                        <div class="mt-6">
                            <h3 class="text-lg font-semibold mb-2">Ubicación en el Mapa <span class="text-red-500">*</span></h3>
                            <p class="text-xs text-gray-500 mb-2">Arrastra el marcador o haz clic para ajustar la ubicación.</p>
                            <div id="mapEdit" style="height: 250px; width: 100%; z-index: 1;"></div>
                            <input type="hidden" id="edit_address_latitude" name="address_latitude">
                            <input type="hidden" id="edit_address_longitude" name="address_longitude">
                        </div>

                    </div> {{-- Fin Contenedor Principal (space-y-4) --}}

                    {{-- Botones del Modal --}}
                    <div class="mt-6 flex justify-end space-x-3">
                        <button type="button" onclick="closeEditModal()" class="btn-secondary-modal bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center mr-3">
                            <i class="material-icons mr-2 text-base">cancel</i>Cancelar
                        </button>
                        <button id="saveButton" type="submit" class="btn-warning-modal bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center">
                            <i class="material-icons mr-2">save</i>
                            <span id="buttonTextEdit">Guardar Cambios</span>
                            <span id="loadingSpinnerEdit" class="hidden ml-2"><i class="material-icons animate-spin">sync</i></span>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Subir Imágenes -->
        <div id="uploadImageModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
            <div id="modalContentUpload" class="bg-white rounded-lg p-8 w-11/12 max-w-lg max-h-[90vh] overflow-y-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                {{-- Encabezado Modal --}}
                <div class="flex justify-between items-center mb-6">
                    <h2 class="text-xl font-semibold text-gray-800">Subir Imagen</h2>
                    <button onclick="closeUploadImageModal()" class="text-gray-500 hover:text-gray-900">
                        <i class="material-icons">close</i>
                    </button>
                </div>

                <!-- Formulario de Subida -->
                <form id="uploadImageForm" action="{{ route('news.uploadImage', ['id' => 'temp-id']) }}" method="POST" enctype="multipart/form-data">
                    @csrf
                    <div class="space-y-4">
                        <div>
                            <label for="file" class="block text-gray-700 text-sm font-bold mb-2">Seleccionar Imagen</label>
                            {{-- Input de archivo para una sola imagen --}}
                            <input type="file" id="file" name="file" required accept="image/jpeg,image/png,image/gif,image/webp"
                                class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none file:mr-4 file:py-2 file:px-4 file:rounded-l-lg file:border-0 file:text-sm file:font-semibold file:bg-gray-200 file:text-gray-700 hover:file:bg-gray-300"/>
                            <p class="mt-1 text-xs text-gray-500">Permitido: JPG, PNG, GIF, WEBP. Máx 2MB por archivo.</p>
                        </div>
                    </div>

                    {{-- Botones del Modal --}}
                    <div class="mt-8 flex justify-end space-x-3">
                        <button type="button" onclick="closeUploadImageModal()" class="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Cancelar</button>
                        <button type="submit" class="bg-yellow-600 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"><i class="material-icons mr-2 text-base">cloud_upload</i>Subir Imagen</button>
                    </div>
                </form>
            </div>
        </div>

        {{-- Scripts de Notificaciones y Errores (SweetAlert) --}}
        @if(session('success'))
            <script>
                document.addEventListener('DOMContentLoaded', () => {
                    Swal.fire({ title: '¡Éxito!', text: '{{ session('success') }}', icon: 'success', confirmButtonText: 'Aceptar', timer: 3000, timerProgressBar: true });
                });
            </script>
        @endif

        @if(session('error'))
            <script>
                document.addEventListener('DOMContentLoaded', () => {
                    Swal.fire({ title: '¡Error!', text: '{{ session('error') }}', icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#d33' });
                });
            </script>
        @endif

        {{-- Mostrar errores de validación de Laravel en SweetAlert si existen --}}
        @if ($errors->any())
            <script>
                document.addEventListener('DOMContentLoaded', () => {
                    let errorMessages = @json($errors->all());
                    let isEditError = @json($errors->hasBag('editForm') ? true : false);
                    let formattedErrors = errorMessages.map(e => `- ${e}`).join('<br>');
                    Swal.fire({
                        title: 'Error de Validación',
                        html: `<p class="text-left">Se encontraron los siguientes errores:</p><div class="text-left text-sm mt-2">${formattedErrors}</div>`,
                        icon: 'error',
                        confirmButtonText: 'Ok',
                        confirmButtonColor: '#d33'
                    });

                    let createModalErrored = false;
                    let editModalErrored = isEditError;
                    let uploadModalErrored = false;

                    @foreach ($errors->keys() as $key)
                        @if (!$errors->hasBag('editForm') && Str::startsWith($key, ['title', 'content', 'category_id', 'address_']))
                            createModalErrored = true;
                        @endif
                        @if (Str::startsWith($key, 'files'))
                            uploadModalErrored = true;
                        @endif
                    @endforeach

                    if (createModalErrored && !editModalErrored) {
                        setTimeout(() => openCreateModal(), 300); // Reabrir modal creación
                    } else if (editModalErrored) {
                        console.warn("Errores de validación en formulario de edición. Reabrir modal manualmente.");
                    } else if (uploadModalErrored) {
                        console.warn("Errores de validación en subida de imágenes. Reabrir modal manualmente.");
                    }
                });
            </script>
        @endif

        {{-- Scripts Principales de la Página --}}
        <script>
            // Variables globales para mapas y marcadores (Leaflet)
            let mapCreateInstance = null;
            let markerCreate = null;
            let mapEditInstance = null;
            let markerEdit = null;
            let mapViewInstance = null; 
            const defaultCoords = { lat: 18.9261, lng: -99.23075 };
            const defaultZoom = 11;

            // --- Inicialización General (DataTables) ---
            $(document).ready(function() {
                $('#newsTable').DataTable({
                    "language": {
                        "url": "js/Spanish.json", // Asegúrate que este archivo exista y sea accesible
                        "emptyTable": "No hay noticias para mostrar.",
                    },
                });
            });

            // --- Funciones Helper para Mapas Leaflet ---
            function initializeOrUpdateMap(mapId, latInputId, lngInputId, initialLat, initialLng) {
                let mapContainer = document.getElementById(mapId); // Usar getElementById nativo
                if (!mapContainer) {
                    return;
                }

                const startLat = !isNaN(parseFloat(initialLat)) ? parseFloat(initialLat) : defaultCoords.lat;
                const startLng = !isNaN(parseFloat(initialLng)) ? parseFloat(initialLng) : defaultCoords.lng;
                const startZoom = (initialLat && initialLng) ? 15 : defaultZoom; // Más zoom si hay coords específicas

                let isEditMap = (mapId === 'mapEdit');
                let mapInstance = isEditMap ? mapEditInstance : mapCreateInstance;
                let markerInstance = isEditMap ? markerEdit : markerCreate;

                // Si el mapa YA existe en este contenedor (p.ej. reabrir modal)
                if (mapInstance && mapContainer._leaflet_id) {
                    console.log("Actualizando mapa existente:", mapId);
                    mapInstance.setView([startLat, startLng], startZoom);
                    if (markerInstance) {
                        markerInstance.setLatLng([startLat, startLng]);
                    } else {
                        // Crear marcador si no existe (caso raro)
                        markerInstance = L.marker([startLat, startLng], { draggable: true }).addTo(mapInstance);
                        attachMarkerEvents(markerInstance, mapInstance, latInputId, lngInputId, isEditMap);
                        if (isEditMap) markerEdit = markerInstance; else markerCreate = markerInstance;
                    }
                    updateCoordinateInputs(latInputId, lngInputId, startLat, startLng);
                    // Forzar redibujo (muy importante para modales animados)
                    setTimeout(() => { mapInstance.invalidateSize(); }, 350); // Aumentar delay por animación
                } else {
                    console.log("Inicializando nuevo mapa:", mapId);
                    // Destruir instancia anterior si existiera
                    if (mapInstance) { mapInstance.remove(); mapInstance = null; markerInstance = null; }

                    mapInstance = L.map(mapId).setView([startLat, startLng], startZoom);
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        maxZoom: 19,
                        attribution: '© OpenStreetMap'
                    }).addTo(mapInstance);

                    markerInstance = L.marker([startLat, startLng], { draggable: true, title: "Arrastra para ubicar" }).addTo(mapInstance);
                    attachMarkerEvents(markerInstance, mapInstance, latInputId, lngInputId, isEditMap);

                    if (isEditMap) { mapEditInstance = mapInstance; markerEdit = markerInstance; }
                    else { mapCreateInstance = mapInstance; markerCreate = markerInstance; }

                    updateCoordinateInputs(latInputId, lngInputId, startLat, startLng);
                    // Forzar redibujo después de que el modal sea completamente visible
                    setTimeout(() => { mapInstance.invalidateSize(); console.log("Map invalidated:", mapId); }, 350); // Aumentar delay
                }
            }
            // Función separada para añadir eventos al marcador
            function attachMarkerEvents(marker, map, latInputId, lngInputId, isEditMap) {
                marker.on('dragend', function(event){
                    const position = event.target.getLatLng();
                    updateCoordinateInputs(latInputId, lngInputId, position.lat, position.lng);
                    map.panTo(position);
                });
                // Mover marcador al hacer clic en el mapa (solo para creación/edición)
                if (latInputId) { // Si hay inputs asociados, es un mapa editable
                    map.on('click', function(e) {
                        marker.setLatLng(e.latlng);
                        updateCoordinateInputs(latInputId, lngInputId, e.latlng.lat, e.latlng.lng);
                    });
                }
            }

            // Helper para actualizar los inputs ocultos de coordenadas
            function updateCoordinateInputs(latInputId, lngInputId, lat, lng) {
                const roundedLat = parseFloat(lat).toFixed(6);
                const roundedLng = parseFloat(lng).toFixed(6);
                $('#' + latInputId).val(roundedLat);
                $('#' + lngInputId).val(roundedLng);
            }

            // --- Funciones de Manejo de Modales (Estilo Eventos) ---
            function openModal(modalId) {
                const modal = document.getElementById(modalId);
                if (!modal) return;
                const modalContent = modal.querySelector('.transform'); // Selector más específico para el contenido animado

                $('body').addClass('modal-open');
                $(modal).removeClass('hidden').addClass('flex'); // Mostrar overlay

                // Animar contenido
                $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)'); // Estado inicial
                setTimeout(() => { // Pequeño delay para asegurar que el display:flex se aplique
                    $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300);
                }, 50);

                // Llamar invalidateSize para mapas DESPUÉS de la animación (o casi al final)
                if (modalId === 'newsCreateModal' || modalId === 'newsEditModal') {
                    setTimeout(() => {
                        const mapInstance = (modalId === 'newsEditModal') ? mapEditInstance : mapCreateInstance;
                        if (mapInstance) {
                            mapInstance.invalidateSize();
                        }
                    }, 350); // Despues de la animacion
                }
            }

            function closeModal(modalId) {
                const modal = document.getElementById(modalId);
                if (!modal) return;
                const modalContent = modal.querySelector('.transform');

                $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
                    $(modal).removeClass('flex').addClass('hidden'); // Ocultar overlay al terminar animación
                    $('body').removeClass('modal-open');
                });
            }

            // Cierra el modal si se hace clic en el fondo (overlay)
            function closeModalOutside(event, modalId) {
                if (event.target.id === modalId) { // Solo si el clic es directamente en el overlay
                    if (modalId === 'newsCreateModal') closeCreateModal();
                    else if (modalId === 'newsEditModal') closeEditModal();
                    else if (modalId === 'uploadImagesModal') closeUploadImagesModal();
                    else if (modalId === 'newsViewModal') closeViewModal(); // Añadido para el modal de ver
                    else closeModal(modalId); // Fallback genérico
                }
            }

            // --- Funciones Específicas para Modales de Noticias ---
            function openCreateModal() {
                $('#createNewsForm')[0].reset(); // Limpia formulario
                $('#createNewsForm .text-red-500.text-xs.mt-1').text(''); // Limpia errores inline
                $('#createNewsForm .input-shadow').removeClass('border-red-500'); // Quita borde rojo

                openModal('newsCreateModal'); // Usar la nueva función de apertura

                // Inicializa el mapa de creación con coords por defecto (se llama dentro de openModal ahora)
                setTimeout(() => { // Se asegura que el modal esté al menos parcialmente visible
                    initializeOrUpdateMap(
                        'mapCreate',
                        'create_news_address_latitude',
                        'create_news_address_longitude',
                        null, // Sin coordenadas iniciales específicas
                        null
                    );
                }, 100); // Delay corto
            }

            function closeCreateModal() {
                closeModal('newsCreateModal'); // Usar la nueva función de cierre
                // Opcional: resetear mapa si se mantiene instancia
                if (mapCreateInstance && markerCreate) {
                    mapCreateInstance.setView(defaultCoords, defaultZoom);
                    markerCreate.setLatLng(defaultCoords);
                    updateCoordinateInputs('create_address_latitude', 'create_address_longitude', defaultCoords.lat, defaultCoords.lng);
                }
            }

            function openEditModal(button) {
                const data = $(button).data();
                const form = $('#editNewsForm');

                form.find('.text-red-500.text-xs.mt-1').text('');
                form.find('.input-shadow').removeClass('border-red-500');

                // Llenar formulario
                form.find('#edit_title').val(data.title);
                form.find('#edit_content').val(data.content);
                form.find('#edit_category_id').val(data.category);
                form.find('#edit_address_street').val(data.addressStreet);
                form.find('#edit_address_number').val(data.addressNumber);
                form.find('#edit_address_interiorNumber').val(data.addressInteriornumber);
                form.find('#edit_address_neighborhood').val(data.addressNeighborhood);
                form.find('#edit_address_zipCode').val(data.addressZipcode);
                form.find('#edit_address_city').val(data.addressCity);
                form.find('#edit_address_state').val(data.addressState);
                form.find('#edit_address_country').val(data.addressCountry);
                // Los inputs de lat/lng se llenarán con el mapa

                form.attr('action', '{{ url("news") }}/' + data.id);

                // Quitar manejador anterior y añadir el nuevo para confirmación
                form.off('submit').on('submit', function(event) {
                    event.preventDefault();
                    submitEditFormWithConfirmation(this);
                });

                openModal('newsEditModal'); // Usar nueva función

                // Inicializar/actualizar mapa DESPUÉS de abrir modal
                setTimeout(() => {
                    initializeOrUpdateMap(
                        'mapEdit',
                        'edit_address_latitude',
                        'edit_address_longitude',
                        data.addressLatitude,
                        data.addressLongitude
                    );
                }, 100);
            }

            function closeEditModal() {
                closeModal('newsEditModal'); // Usar nueva función
                // Opcional: resetear mapa
                if (mapEditInstance && markerEdit) {
                    // No hacer nada o resetear a default? Mejor no hacer nada.
                }
            }

            // --- Modal Ver Detalles ---
            function openViewModal(button) {
                const data = $(button).data();
                // Parsear JSON si es string, asegurar que sean arrays/objetos vacíos si no existen
                const address = data.address ? (typeof data.address === 'string' ? JSON.parse(data.address) : data.address) : {};
                const images = data.images ? (Array.isArray(data.images) ? data.images : [data.images]) : [];

                // Poblar campos de texto (como antes)
                $('#newsTitleView').text(data.title || 'N/A');
                $('#newsContentView').text(data.content || 'N/A'); // Ya usaba pre-wrap
                $('#newsCategoryView').text(data.categoryName || 'N/A');
                $('#newsStatusView').text(data.status || 'N/A');

                let fullAddress = 'No disponible';
                if (address && address.street) {
                    fullAddress = `${address.street} ${address.number ?? ''}${address.interiorNumber ? ' Int. ' + address.interiorNumber : ''}, Col. ${address.neighborhood}, C.P. ${address.zipCode}, ${address.city}, ${address.state}, ${address.country}`;
                }
                $('#newsAddressView').text(fullAddress);

                let coords = 'No disponibles';
                if (address && address.latitude && address.longitude) {
                    coords = `Lat: ${parseFloat(address.latitude).toFixed(6)}, Lon: ${parseFloat(address.longitude).toFixed(6)}`;
                }
                $('#newsCoordsView').text(coords);

                $('#newsCreatedAtView').text(formatNewsDate(data.createdAt));
                $('#newsUpdatedAtView').text(formatNewsDate(data.updatedAt));
                $('#newsPublishDateView').text(formatNewsDate(data.publishDate, true));

                // <<< NUEVO: Poblar contenedor de imágenes >>>
                const imagesContainer = $('#newsImagesViewContainer');
                imagesContainer.empty(); // Limpiar contenido previo

                if (images.length > 0) {
                    images.forEach(imageUrl => {
                        // Crear elemento contenedor para cada imagen
                        const imgWrapper = $('<div class="bg-white p-1 rounded shadow-sm overflow-hidden"></div>');
                        // Crear la imagen
                        const img = $('<img>')
                            .attr('src', `http://localhost:8081/news_images/${imageUrl}`) // Asumir que `imageUrl` es solo el nombre del archivo
                            .attr('alt', 'Imagen de la noticia')
                            .addClass('w-full h-24 object-cover cursor-pointer') // Ajusta la altura según necesites
                            .on('click', () => window.open(`http://localhost:8081/news_images/${imageUrl}`, '_blank')); // Abrir en nueva pestaña al hacer clic
                        imgWrapper.append(img);
                        imagesContainer.append(imgWrapper);
                    });
                } else {
                    imagesContainer.html('<p class="text-gray-500 text-xs col-span-full text-center py-4">No hay imágenes disponibles.</p>');
                }

                // <<< NUEVO: Inicializar mapa de visualización >>>
                const mapContainer = $('#mapView');
                const mapPlaceholder = $('#mapViewPlaceholder');
                const lat = address?.latitude;
                const lng = address?.longitude;

                // Ocultar/Mostrar elementos del mapa
                mapPlaceholder.show();
                mapContainer.hide();

                // Destruir mapa anterior si existe (por si acaso no se cerró bien)
                if (mapViewInstance) {
                    mapViewInstance.remove();
                    mapViewInstance = null;
                }

                openModal('newsViewModal'); // Abrir el modal ANTES de inicializar el mapa

                // Intentar inicializar el mapa DESPUÉS de que el modal esté visible
                setTimeout(() => {
                    if (lat && lng && !isNaN(parseFloat(lat)) && !isNaN(parseFloat(lng))) {
                        mapPlaceholder.hide();
                        mapContainer.show(); // Mostrar el div del mapa ahora

                        try {
                            mapViewInstance = L.map('mapView').setView([parseFloat(lat), parseFloat(lng)], 15); // Zoom más cercano para vista
                            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                                maxZoom: 19,
                                attribution: '© OpenStreetMap'
                            }).addTo(mapViewInstance);

                            // Marcador no draggable para visualización
                            L.marker([parseFloat(lat), parseFloat(lng)], { title: data.title || 'Ubicación' }).addTo(mapViewInstance);

                            // Invalidar tamaño DESPUÉS de que todo esté en el DOM y visible
                            mapViewInstance.invalidateSize();
                            console.log("Mapa de vista inicializado.");

                        } catch (e) {
                            console.error("Error inicializando mapa de vista:", e);
                            mapContainer.hide();
                            mapPlaceholder.text('Error al cargar el mapa.').show();
                        }

                    } else {
                        mapContainer.hide(); // Ocultar div del mapa si no hay coords
                        mapPlaceholder.text('Ubicación no disponible en el mapa.').show();
                    }
                }, 350); // Aumentar delay para asegurar que el modal y su layout estén listos

            }

            function closeViewModal() {
                // <<< NUEVO: Destruir instancia del mapa de visualización >>>
                if (mapViewInstance) {
                    mapViewInstance.remove();
                    mapViewInstance = null;
                    console.log("Mapa de vista destruido.");
                }
                // Limpiar contenedores por si acaso
                $('#newsImagesViewContainer').empty().html('<p class="text-gray-500 text-xs col-span-full text-center py-4">Cargando imágenes...</p>');
                $('#mapViewContainer #mapView').hide(); // Ocultar el mapa
                $('#mapViewContainer #mapViewPlaceholder').text('Cargando mapa...').show(); // Mostrar placeholder

                closeModal('newsViewModal'); // Usar función genérica de cierre
            }

            // Helper para formatear fechas para la vista de detalles
            function formatNewsDate(dateString, isPublishDate = false) {
                if (!dateString) {
                    return isPublishDate ? 'No programada/publicada' : 'No disponible';
                }
                try {
                        const date = new Date(dateString);
                        if (isNaN(date)) return 'Fecha inválida';

                        // Formato más común para México DD/MM/YYYY hh:mm:ss AM/PM
                        const day = String(date.getDate()).padStart(2, '0');
                        const month = String(date.getMonth() + 1).padStart(2, '0'); // Meses son 0-indexados
                        const year = date.getFullYear();
                        let hours = date.getHours();
                        const minutes = String(date.getMinutes()).padStart(2, '0');
                        // const seconds = String(date.getSeconds()).padStart(2, '0'); // Opcional
                        const ampm = hours >= 12 ? 'PM' : 'AM';
                        hours = hours % 12;
                        hours = hours ? hours : 12; // La hora '0' debe ser '12'
                        const strTime = String(hours).padStart(2, '0') + ':' + minutes + ' ' + ampm; // + ':' + seconds

                        return `${day}/${month}/${year} ${strTime}`;
                } catch (e) {
                    console.error("Error formateando fecha:", dateString, e);
                    return 'Error al formatear';
                }
            }

            // --- Modal Subir Imagen ---
            function openUploadImageModal(button) {
                const modal = document.getElementById("uploadImageModal");
                const form = document.getElementById("uploadImageForm");

                // Obtener el ID desde el botón
                const newsId = button.getAttribute("data-id");

                // Actualiza la acción del formulario con el ID real de la noticia
                form.action = `/news/${newsId}/upload-image`;

                // Muestra el modal
                modal.classList.remove("hidden");
                modal.classList.add("flex");
            }

            // Función para cerrar el modal
            function closeUploadImageModal() {
                const modal = document.getElementById("uploadImageModal");
                modal.classList.remove("flex");  // Elimina el estilo de flexbox
                modal.classList.add("hidden");  // Vuelve a ocultar el modal
            }

            // --- Confirmaciones SweetAlert ---
            function confirmDelete(event) {
                event.preventDefault();
                const form = event.target.closest('form');

                Swal.fire({
                    title: '¿Estás seguro?',
                    text: "¡Esta acción eliminará la noticia permanentemente!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33', // Rojo
                    cancelButtonColor: '#6b7280', // Gris
                    confirmButtonText: 'Sí, ¡eliminar!',
                    cancelButtonText: 'Cancelar',
                    reverseButtons: true
                }).then((result) => {
                    if (result.isConfirmed) { form.submit(); }
                });
                return false;
            }

            function submitEditFormWithConfirmation(formElement) {
                Swal.fire({
                    title: '¿Guardar Cambios?',
                    text: "Se actualizará la información de la noticia.",
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#f59e0b', // Naranja/Amarillo
                    cancelButtonColor: '#6b7280',
                    confirmButtonText: 'Sí, guardar',
                    cancelButtonText: 'Cancelar',
                    reverseButtons: true
                }).then((result) => {
                    if (result.isConfirmed) {
                        const saveButton = formElement.querySelector('#saveButton');
                        const buttonText = formElement.querySelector('#buttonTextEdit');
                        const loadingSpinner = formElement.querySelector('#loadingSpinnerEdit');
                        if(saveButton) {
                            saveButton.disabled = true;
                            if(buttonText) buttonText.textContent = 'Guardando...';
                            if(loadingSpinner) loadingSpinner.classList.remove('hidden');
                        }
                        formElement.submit();
                    }
                });
            }

            // --- Sidebar Toggle (Revisado y simplificado) ---
            document.addEventListener("DOMContentLoaded", function() {
                const modal = document.getElementById("uploadImageModal");
                const toggleButton = document.getElementById("toggleSidebar"); // ID del botón en navbar.blade.php
                const sidebar = document.getElementById("sidebar");         // ID del sidebar en sidebar.blade.php
                const mainContent = document.getElementById("mainContent");

                const adjustLayout = () => {
                    if (!sidebar || !mainContent) return;
                    const isMobile = window.innerWidth < 768; // Tailwind 'md' breakpoint

                    if (isMobile) {
                        // En móvil, el contenido SIEMPRE ocupa todo el ancho, sidebar se superpone
                        mainContent.classList.remove("md:ml-80");
                        mainContent.classList.add("ml-0");
                        sidebar.classList.add("-translate-x-full"); // Asegurar que esté oculta por defecto en móvil
                    } else {
                        // En desktop, ajustar margen según estado del sidebar
                        if (sidebar.classList.contains("-translate-x-full")) {
                            mainContent.classList.remove("md:ml-80");
                            mainContent.classList.add("ml-0"); // O un margen menor si prefieres: ml-20
                        } else {
                            mainContent.classList.add("md:ml-80");
                            mainContent.classList.remove("ml-0");
                        }
                    }
                    // Invalidar mapas si están visibles (puede ser redundante si ya se hace al abrir modal)
                    setTimeout(() => {
                        if(mapCreateInstance && $('#newsCreateModal').is(':visible')) mapCreateInstance.invalidateSize();
                        if(mapEditInstance && $('#newsEditModal').is(':visible')) mapEditInstance.invalidateSize();
                    }, 350);
                };

                if (toggleButton && sidebar && mainContent) {
                    toggleButton.addEventListener("click", () => {
                        sidebar.classList.toggle("-translate-x-full");
                        adjustLayout();
                    });

                    window.addEventListener('resize', adjustLayout);
                    adjustLayout(); // Ajuste inicial al cargar

                } else {
                    console.warn("Elementos de Sidebar/Toggle/MainContent no encontrados.");
                    if (mainContent) { // Si no hay sidebar, quitar margen
                        mainContent.classList.remove("md:ml-80");
                        mainContent.classList.add("ml-0");
                    }
                }
            });

        </script>

</body>
</html>