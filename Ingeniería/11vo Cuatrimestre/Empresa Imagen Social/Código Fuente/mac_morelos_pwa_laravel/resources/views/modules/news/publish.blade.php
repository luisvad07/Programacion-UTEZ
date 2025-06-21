<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Publicación de Noticias</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    {{-- DataTables no es necesario aquí si no hay tabla --}}
     <style>
        /* Estilos adicionales para modales si son necesarios */
        .modal { display: none; /* Oculto por defecto */ }
        .modal.active { display: flex; /* Mostrar como flexbox */ }
    </style>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        {{-- Contenido Principal --}}
        <div id="mainContent" class="flex-grow p-6 md:p-12 ml-0 md:ml-80 transition-all duration-300">
            <div class="bg-white p-6 md:p-8 rounded-lg shadow-md w-full mx-auto">

                {{-- Encabezado --}}
                <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between pb-6 gap-4 border-b mb-6">
                    <div>
                        {{-- Título ajustado si es necesario --}}
                        <h2 class="text-gray-700 text-2xl font-semibold">Gestionar Publicación (Borradores y Programadas)</h2>
                        <span class="text-sm text-gray-500">Noticias pendientes de publicar o ya programadas</span>
                    </div>
                </div>

                @if(!isset($errorMessage) && empty($newsToShow))
                    <div class="bg-yellow-100 border-l-4 border-yellow-500 text-yellow-700 p-4" role="alert">
                        <p class="font-bold">Información</p>
                        <p>No hay noticias en borrador ni programadas.</p>
                    </div>
                @elseif(!empty($newsToShow)) 
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {{-- ***** CAMBIO: Iterar sobre $newsToShow ***** --}}
                        @foreach ($newsToShow as $item)
                            {{-- Determinar estado para la lógica condicional --}}
                            @php
                                $status = $item['statusNews'] ?? null;
                                $publishDate = $item['publishDate'] ?? null;
                                $scheduledDate = $item['scheduledDate'] ?? null;
                                // Inferir BORRADOR si falta status pero no hay fecha de publicación
                                if (is_null($status) && is_null($publishDate)) {
                                    $status = 'BORRADOR';
                                }
                                $mexicoCityTimezone = 'America/Mexico_City';
                            @endphp

                            {{-- Cambiar color de fondo de la tarjeta según estado (opcional) --}}
                            <div class="rounded-lg shadow-md overflow-hidden flex flex-col
                                {{ $status === 'BORRADOR' ? 'bg-amber-50' : '' }} 
                                {{ $status === 'PROGRAMADO' ? 'bg-blue-50' : '' }}
                                {{ $status === 'PUBLICADO' ? 'bg-green-50' : '' }}">
                                <div class="p-6 flex-grow">
                                    <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ $item['title'] ?? 'Sin título' }}</h2>
                                    <p class="text-sm text-gray-500 mb-1">
                                        Categoría: {{ $categoryMap[$item['categories'][0]['categoryId']] ?? ($item['categories'][0]['name'] ?? 'Sin categoría'); }}
                                    </p>
                                     <p class="text-sm text-gray-500 mb-3">
                                        Creado: {{ isset($item['createdAt']) ? \Carbon\Carbon::parse($item['createdAt'])->tz($mexicoCityTimezone)->format('d/m/Y H:i') : 'N/A' }}
                                    </p>
                                    <p class="text-gray-600 text-sm line-clamp-4">
                                        {{ strip_tags($item['content'] ?? 'Sin contenido.') }}
                                    </p>
                                </div>

                                <div class="p-4 border-t flex flex-col sm:flex-row justify-between items-center space-y-2 sm:space-y-0 sm:space-x-2 min-h-[60px] {{-- Altura mínima para alinear --}}
                                    {{ $status === 'BORRADOR' ? 'bg-amber-100 border-amber-200' : '' }}
                                    {{ $status === 'PROGRAMADO' ? 'bg-blue-100 border-blue-200' : '' }}
                                    {{ $status === 'PUBLICADO' ? 'bg-green-100 border-green-200' : '' }}">
                                    @if ($status === 'BORRADOR')
                                        {{-- Mostrar botones de Publicar y Programar --}}
                                        <form id="publish-now-form-{{ $item['newsId'] }}" action="{{ route('publish.now', $item['newsId']) }}" method="POST" class="w-full sm:w-auto">
                                            @csrf
                                            @method('PUT')
                                            <button type="button" class="publish-now-btn w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded inline-flex items-center justify-center transition duration-150 ease-in-out"
                                                    data-id="{{ $item['newsId'] }}">
                                                <span class="material-icons mr-1">publish</span>
                                                Publicar
                                            </button>
                                        </form>
                                        <button type="button" class="schedule-btn w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded inline-flex items-center justify-center transition duration-150 ease-in-out"
                                                data-id="{{ $item['newsId'] }}">
                                            <span class="material-icons mr-1">schedule</span>
                                            Programar
                                        </button>
                                    @elseif ($status === 'PROGRAMADO')
                                        {{-- Mostrar estado Programado --}}
                                        <div class="text-blue-700 text-sm font-semibold flex items-center w-full justify-center sm:justify-start">
                                            <span class="material-icons mr-1 text-blue-500">schedule</span>
                                            Programado para: {{ $scheduledDate ? \Carbon\Carbon::parse($scheduledDate)->tz($mexicoCityTimezone)->format('d/m/Y H:i') : 'Fecha inválida' }}
                                        </div>
                                         {{-- Opcional: Botón para cancelar programación (necesitaría nueva ruta/método) --}}
                                         {{-- <button type="button" class="cancel-schedule-btn w-full sm:w-auto bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-3 rounded text-xs" data-id="{{ $item['newsId'] }}">Cancelar</button> --}}
                                         {{-- Opcional: Botón para publicar ahora una programada --}}
                                          <form id="publish-now-scheduled-form-{{ $item['newsId'] }}" action="{{ route('publish.now', $item['newsId']) }}" method="POST" class="w-full sm:w-auto">
                                                @csrf
                                                @method('PUT')
                                                <button type="button" class="publish-now-btn w-full bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-1 px-3 rounded inline-flex items-center justify-center transition duration-150 ease-in-out text-xs"
                                                        data-id="{{ $item['newsId'] }}">
                                                    <span class="material-icons mr-1 text-xs">publish</span>
                                                    Publicar Ahora
                                                </button>
                                            </form>
                                    @elseif ($status === 'PUBLICADO')
                                        {{-- Mostrar estado Publicado (si decides incluirlos en el controller) --}}
                                        <div class="text-green-700 text-sm font-semibold flex items-center w-full justify-center sm:justify-start">
                                             <span class="material-icons mr-1 text-green-500">check_circle</span>
                                             Publicado el: {{ $publishDate ? \Carbon\Carbon::parse($publishDate)->tz($mexicoCityTimezone)->format('d/m/Y H:i') : 'Fecha inválida' }}
                                        </div>
                                    @else
                                        {{-- Estado desconocido o inesperado --}}
                                         <div class="text-gray-500 text-sm w-full text-center sm:text-left">
                                             Estado: {{ $status ?? 'Desconocido' }}
                                         </div>
                                    @endif
                                </div> {{-- Fin del div condicional de botones/estado --}}
                            </div> {{-- Fin de la tarjeta de noticia --}}
                        @endforeach
                    </div> {{-- Fin del grid --}}
                @endif
            </div> {{-- Fin del contenedor principal blanco --}}
        </div> {{-- Fin del mainContent --}}
    </div>

    {{-- Modal Programar Publicación --}}
    <div id="scheduleNewsModal" class="modal fixed hidden inset-0 z-50 bg-gray-900 bg-opacity-50 items-center justify-center">
        <div class="modal-content relative w-full max-w-md mx-auto p-6 bg-white rounded-lg shadow-lg mt-24">
            <div class="relative w-full max-w-md mx-auto p-6 bg-white rounded-lg shadow-lg">
                <div class="flex justify-between items-center border-b pb-3 mb-5">
                    <h3 class="text-lg font-medium text-gray-900">Programar Publicación</h3>
                    <button class="close-modal-btn text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-id="scheduleNewsModal">
                        <span class="material-icons">close</span>
                    </button>
                </div>
                <form id="scheduleNewsForm" action="" method="POST">
                    @csrf
                    @method('PUT')
                    <div class="space-y-4">
                        <div>
                            <label for="publish_date" class="block mb-2 text-sm font-medium text-gray-900">Fecha y Hora de Publicación</label>
                            <input type="datetime-local" name="publish_date" id="publish_date"
                                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                required
                                min="{{ now()->format('Y-m-d\TH:i') }}">
                            <p class="mt-1 text-xs text-gray-500">La fecha y hora deben ser futuras.</p>
                        </div>
                    </div>
                    <div class="flex items-center justify-end pt-6 border-t border-gray-200 rounded-b mt-5">
                        <button type="button" class="close-modal-btn text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-blue-300 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 mr-3" data-modal-id="scheduleNewsModal">Cancelar</button>
                        <button type="submit" class="text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">Programar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    {{-- Scripts base y de mensajes --}}
    @if(session('success'))
        <script>
            Swal.fire({ title: '¡Éxito!', text: '{{ session('success') }}', icon: 'success', confirmButtonText: 'Aceptar' });
        </script>
    @endif
    @if(session('error'))
        <script>
            Swal.fire({ title: '¡Error!', text: '{{ session('error') }}', icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#d33' });
        </script>
    @endif
    @if ($errors->any())
    <script>
        let errorMessages = @json($errors->all());
        let errorText = "Se encontraron los siguientes errores:\n\n" + errorMessages.map(e => `- ${e}`).join('\n');
        Swal.fire({ title: 'Error de Validación', text: errorText, icon: 'error', confirmButtonText: 'Entendido', confirmButtonColor: '#d33' });
    </script>
    @endif

    <script>
        // Tu script JS existente aquí (asegúrate de que los selectores y lógica sigan funcionando)
        $(document).ready(function() {
            // --- Funciones para Modales ---
            function openModal(modalId) {
                const modal = $('#' + modalId);
                modal.removeClass('hidden');
                modal.addClass('flex items-center justify-center');
            }

            function closeModal(modalId) {
                const modal = $('#' + modalId);
                modal.addClass('hidden');
                modal.removeClass('flex items-center justify-center');

                if (modalId === 'scheduleNewsModal') {
                    $('#scheduleNewsForm')[0].reset();
                    const now = new Date();
                    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
                    $('#publish_date').attr('min', now.toISOString().slice(0, 16));
                     // localStorage.removeItem('lastScheduledNewsId'); // Limpiar ID guardado
                }
            }

            // --- Eventos para Modales ---
            $('.close-modal-btn').on('click', function() {
                closeModal($(this).data('modal-id'));
            });

            $('.modal').on('click', function(e) {
                if ($(e.target).hasClass('modal')) {
                    closeModal($(this).attr('id'));
                }
            });

            // --- Evento para Publicar Ahora (funciona para BORRADOR y PROGRAMADO si usas la misma clase) ---
            $('.publish-now-btn').on('click', function() {
                const id = $(this).data('id');
                // Encuentra el formulario correcto (podría haber dos por tarjeta ahora)
                const form = $(this).closest('form'); // Encuentra el form padre más cercano

                Swal.fire({
                    title: '¿Publicar esta noticia ahora?',
                    text: "La noticia será visible públicamente.",
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#28a745',
                    cancelButtonColor: '#6c757d',
                    confirmButtonText: 'Sí, publicar',
                    cancelButtonText: 'Cancelar',
                    reverseButtons: true,
                }).then((result) => {
                    if (result.isConfirmed) {
                        form.submit();
                    }
                });
            });

            // --- Evento para Abrir Modal de Programación (solo para BORRADOR) ---
            $('.schedule-btn').on('click', function() {
                const id = $(this).data('id');
                $('#scheduleNewsForm').attr('action', `/news/${id}/programar`);
                const now = new Date(); now.setMinutes(now.getMinutes() - now.getTimezoneOffset()); $('#publish_date').attr('min', now.toISOString().slice(0,16)); $('#publish_date').val('');
                openModal('scheduleNewsModal');
            });

             // --- Sidebar Toggle ---
            const toggleButton = document.getElementById("toggleSidebar");
            const sidebar = document.getElementById("sidebar");
            const mainContent = document.getElementById("mainContent");
             if (toggleButton && sidebar && mainContent) {
                 // ... (tu código de sidebar toggle sin cambios) ...
                 toggleButton.addEventListener("click", function() {
                    sidebar.classList.toggle("-translate-x-full");
                    mainContent.classList.toggle("md:ml-80");
                    mainContent.classList.toggle("ml-0");
                 });
                  // Ajustar margen inicial
                 if (!sidebar.classList.contains("-translate-x-full")) {
                     mainContent.classList.add("md:ml-80");
                     mainContent.classList.remove("ml-0");
                 } else {
                     mainContent.classList.add("ml-0");
                     mainContent.classList.remove("md:ml-80");
                 }
             }

        });
    </script>
</body>
</html>