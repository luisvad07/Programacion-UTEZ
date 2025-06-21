<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Asignaciones</title>
    @vite(['resources/css/app.css', 'resources/js/app.js']) {{-- Asegúrate que Vite esté configurado --}}
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1"> {{-- Ajusta path si es necesario --}}
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

    <style>
        /* Estilo para el modal y su contenido */
        .modal {
            transition: opacity 0.3s ease-in-out;
        }
        .modal-content {
            transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
        }
        /* Clases para animación de entrada/salida */
        .modal.flex { /* Estado visible */
            opacity: 1;
        }
        .modal.hidden { /* Estado oculto */
            opacity: 0;
        }
        .modal-content.scale-100 { /* Contenido visible */
            transform: scale(1);
            opacity: 1;
        }
        .modal-content.scale-95 { /* Contenido oculto/inicial */
            transform: scale(0.95);
            opacity: 0;
        }
         /* Asegurar que selects se vean bien */
        select.form-select {
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
            background-position: right 0.5rem center;
            background-repeat: no-repeat;
            background-size: 1.5em 1.5em;
            padding-right: 2.5rem;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
        }
    </style>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20"> {{-- Ajusta márgenes si navbar tiene altura fija --}}
    @include('layouts.navbar') {{-- Asume que tienes este layout --}}

    <div class="flex min-h-screen">
        @include('layouts.sidebar') {{-- Asume que tienes este layout --}}

        {{-- Contenido Principal --}}
        <div id="mainContent" class="flex-grow p-6 md:p-12 ml-0 sm:ml-80 transition-all duration-300"> {{-- Ajusta ml inicial/responsivo --}}
            <div class="bg-white p-6 md:p-8 rounded-lg shadow-md w-full">
                {{-- Encabezado y Botón Crear --}}
                <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between pb-6 gap-4">
                    <div>
                        <h2 class="text-gray-700 font-semibold text-xl">Gestión de Asignaciones</h2>
                        <span class="text-xs text-gray-500">Asignaciones de promotores a ciudadanos</span>
                    </div>
                    <div class="flex items-center">
                        <button class="bg-green-600 hover:bg-green-700 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer flex items-center shadow transition duration-150 ease-in-out" onclick="openCreateModal()">
                            <i class="material-icons mr-2">add</i>
                            Nueva Asignación
                        </button>
                    </div>
                </div>

                 {{-- Mostrar error general de la API si existe --}}
                 @if(isset($apiError) && $apiError)
                 <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                     <strong class="font-bold">Error!</strong>
                     <span class="block sm:inline">{{ $apiError }}</span>
                 </div>
                 @endif


                {{-- Tabla de Asignaciones --}}
                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                            <table id="assignmentTable" class="min-w-full leading-normal">
                                <thead>
                                    <tr class="bg-gray-100">
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            ID Asig.
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Promotor
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Ciudadano
                                        </th>
                                         <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Tel. Ciudadano
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Fecha Asignación
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Acciones
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="assignmentTableBody">
                                    @foreach($assignments as $assignment)
                                        <tr class="hover:bg-gray-50">
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-900 whitespace-no-wrap">{{ $assignment['assignmentId'] }}</p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-900 whitespace-no-wrap font-medium">
                                                    {{ $assignment['promoter']['firstName'] ?? 'N/A' }} {{ $assignment['promoter']['lastName'] ?? '' }}
                                                    <span class="text-xs text-gray-500 block">ID: {{ $assignment['promoter']['userId'] }}</span>
                                                </p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                 <p class="text-gray-900 whitespace-no-wrap font-medium">
                                                    {{ $assignment['user']['firstName'] ?? 'N/A' }} {{ $assignment['user']['lastName'] ?? '' }}
                                                     <span class="text-xs text-gray-500 block">ID: {{ $assignment['user']['userId'] }} | {{ $assignment['user']['username'] ?? $assignment['user']['email'] ?? '?' }}</span>
                                                </p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-700 whitespace-no-wrap">{{ $assignment['user']['phone'] ?? 'N/A' }}</p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-700 whitespace-no-wrap">
                                                    {{ isset($assignment['assignedAt']) ? \Carbon\Carbon::parse($assignment['assignedAt'])->setTimezone('America/Mexico_City')->format('d/m/Y H:i') : 'N/A' }}
                                                </p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <div class="flex items-center space-x-3">
                                                    {{-- Botón Ver (Puedes implementarlo si necesitas un modal de detalles) --}}
                                                    {{-- <button class="text-gray-600 hover:text-gray-900 transition duration-150 ease-in-out"
                                                            title="Ver Detalles"
                                                            onclick="openViewModal(this)"
                                                            data-id="{{ $assignment['assignmentId'] }}">
                                                        <i class="material-icons text-lg">visibility</i>
                                                    </button> --}}

                                                    {{-- Permisos para Eliminar --}}
                                                    {{-- Asume que solo el admin puede eliminar, ajusta la condición si es necesario --}}
                                                    @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                                                        {{-- Botón Eliminar (con formulario) --}}
                                                         {{-- Asegúrate que la ruta 'assignments.destroy' exista y acepte DELETE --}}
                                                        <form action="{{ route('assignments.destroy', $assignment['assignmentId']) }}" method="POST" style="display: inline;" onsubmit="return confirmDelete(event);">
                                                            @csrf
                                                            @method('DELETE')
                                                            <button type="submit" class="text-red-500 hover:text-red-600 transition duration-150 ease-in-out" title="Eliminar Asignación">
                                                                <i class="material-icons text-lg">delete</i>
                                                            </button>
                                                        </form>
                                                    @endif
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

            <!-- Modal Crear Asignación -->
            <div id="assignmentCreateModal" class="modal fixed inset-0 z-50 hidden items-center justify-center bg-gray-900 bg-opacity-60" onclick="closeModalOutside(event, 'assignmentCreateModal')">
                <div id="modalContentCreate" class="modal-content bg-white rounded-lg p-6 md:p-8 w-11/12 max-w-lg max-h-[85vh] overflow-y-auto transform scale-95 opacity-0">
                    {{-- Encabezado Modal --}}
                    <div class="flex justify-between items-center mb-4 pb-3 border-b border-gray-200">
                        <h2 class="text-lg md:text-xl font-semibold text-gray-800">Crear Nueva Asignación</h2>
                        <button onclick="closeCreateModal()" class="text-gray-400 hover:text-gray-700 transition duration-150 ease-in-out">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Creación -->
                    <form id="createAssignmentForm" action="{{ route('assignments.store') }}" method="POST">
                        @csrf
                        <div class="space-y-4">
                            {{-- Selector de Promotor --}}
                            <div>
                                <label for="create_promoter_id" class="block text-gray-700 text-sm font-bold mb-2">Promotor <span class="text-red-500">*</span></label>
                                <select id="create_promoter_id" name="promoter_id" class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent form-select" required>
                                    <option value="" disabled selected>-- Selecciona un Promotor --</option>
                                    @forelse($promoters as $promoter)
                                        <option value="{{ $promoter['userId'] }}">
                                            {{ $promoter['firstName'] ?? '?' }} {{ $promoter['lastName'] ?? '' }} ({{ $promoter['email'] ?? '?' }})
                                        </option>
                                    @empty
                                         <option value="" disabled>No hay promotores disponibles</option>
                                    @endforelse
                                </select>
                                <span id="create_promoter_id_error" class="text-red-500 text-xs mt-1"></span>
                                @error('promoter_id') <span class="text-red-500 text-xs mt-1">{{ $message }}</span> @enderror
                            </div>

                            {{-- Selector de Ciudadano --}}
                            <div>
                                <label for="create_user_id" class="block text-gray-700 text-sm font-bold mb-2">Ciudadano <span class="text-red-500">*</span></label>
                                <select id="create_user_id" name="user_id" class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent form-select" required>
                                    <option value="" disabled selected>-- Selecciona un Ciudadano --</option>
                                     @forelse($citizens as $citizen)
                                        <option value="{{ $citizen['userId'] }}">
                                            {{ $citizen['firstName'] ?? '?' }} {{ $citizen['lastName'] ?? '' }} ({{ $citizen['username'] ?? $citizen['email'] ?? '?' }})
                                        </option>
                                     @empty
                                         <option value="" disabled>No hay ciudadanos disponibles</option>
                                     @endforelse
                                </select>
                                <span id="create_user_id_error" class="text-red-500 text-xs mt-1"></span>
                                @error('user_id') <span class="text-red-500 text-xs mt-1">{{ $message }}</span> @enderror
                            </div>
                        </div>

                        {{-- Botones del Modal --}}
                        <div class="mt-6 pt-4 border-t border-gray-200 flex justify-end space-x-3">
                             <button type="button" onclick="closeCreateModal()" class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                                Cancelar
                            </button>
                            <button id="createButton" type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center transition duration-150 ease-in-out">
                                <i class="material-icons mr-2">save</i>
                                Guardar Asignación
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            {{-- Aquí podrías añadir Modales para Ver o Editar si fueran necesarios --}}
            {{-- El modal de Editar no suele aplicar a asignaciones (se borra y crea de nuevo) --}}
            {{-- El modal de Ver podría mostrar más detalles del promotor/ciudadano si la tabla es simple --}}

        </div>
    </div>

    {{-- Scripts --}}
    @if(session('success'))
        <script>
            Swal.fire({
                title: '¡Éxito!',
                text: '{{ session('success') }}',
                icon: 'success',
                timer: 3000, // Cierra automáticamente después de 3 segundos
                showConfirmButton: false // Oculta el botón de confirmar
            });
        </script>
    @endif

    @if(session('error'))
        <script>
            Swal.fire({
                title: '¡Error!',
                text: '{{ session('error') }}',
                icon: 'error',
                confirmButtonText: 'Aceptar',
                confirmButtonColor: '#d33' // Color rojo para botón de error
            });
        </script>
    @endif

    {{-- Manejo de errores de validación de Laravel (para mostrar en modal si falla la creación) --}}
    @if ($errors->any())
    <script>
        // Si hay errores de validación, abre el modal de creación automáticamente
        // para que el usuario vea los errores y corrija.
        $(document).ready(function() {
            // Primero muestra el SweetAlert con los errores generales
            let errorMessages = @json($errors->all());
            let errorText = "Por favor, corrige los siguientes errores:\n\n";
            errorMessages.forEach(function(error) {
                errorText += `- ${error}\n`;
            });

            Swal.fire({
                title: 'Error de Validación',
                html: errorText.replace(/\n/g, '<br>'), // Usa HTML para saltos de línea
                icon: 'error',
                confirmButtonText: 'Entendido',
                confirmButtonColor: '#d33'
            }).then(() => {
                 // Luego abre el modal para corregir
                 openModal('assignmentCreateModal');
            });

             // Intenta rellenar los campos con los valores antiguos (opcional)
             $('#create_promoter_id').val('{{ old('promoter_id') }}');
             $('#create_user_id').val('{{ old('user_id') }}');
        });
    </script>
    @endif


    <script>
        $(document).ready(function() {
            $('#assignmentTable').DataTable({
                "language": {
                    // Asegúrate que este archivo exista en public/js/
                    "url": "{{ asset('js/Spanish.json') }}",
                    "emptyTable": "No hay asignaciones disponibles en la tabla.",
                },
            });
        });

        // ----- Funciones para Modales -----

        function openModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content');
            if (!modal || !modalContent) return;

            modal.classList.remove('hidden');
            modal.classList.add('flex');
             // Forzar reflow
            modal.offsetHeight;

            modal.classList.add('opacity-100'); // Inicia transición de opacidad del fondo
            modalContent.classList.remove('scale-95', 'opacity-0');
            modalContent.classList.add('scale-100', 'opacity-100'); // Inicia transición de contenido
        }

        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content');
            if (!modal || !modalContent) return;

             modalContent.classList.remove('scale-100', 'opacity-100');
            modalContent.classList.add('scale-95', 'opacity-0'); // Inicia ocultación de contenido
            modal.classList.remove('opacity-100'); // Inicia ocultación de fondo

            setTimeout(() => {
                 modal.classList.add('hidden');
                 modal.classList.remove('flex');
            }, 300); // Espera a que termine la transición (0.3s)
        }

        // Cerrar modal al hacer clic fuera del contenido
        function closeModalOutside(event, modalId) {
             const modalContent = document.getElementById('modalContent' + modalId.replace('assignment', '')); // e.g., modalContentCreate
             if (event.target === event.currentTarget) { // Si el clic fue en el overlay (currentTarget) y no en un hijo
                  closeModal(modalId);
             }
        }

        // --- Funciones Específicas para cada Modal ---

        function openCreateModal() {
             // Limpiar formulario antes de abrir
            $('#createAssignmentForm')[0].reset();
            // Limpiar mensajes de error inline si los hubiera
            $('#createAssignmentForm .text-red-500.text-xs.mt-1').text('');
             // Asegurar que los selects muestren la opción por defecto
             $('#create_promoter_id').val('');
             $('#create_user_id').val('');
            openModal('assignmentCreateModal');
        }

        function closeCreateModal() {
            closeModal('assignmentCreateModal');
            // Opcional: Limpiar errores de validación si se cierra manualmente
             $('#createAssignmentForm .text-red-500.text-xs.mt-1').text('');
        }

        // Si implementas un modal de vista:
        /*
        function openViewModal(button) {
            let assignmentId = button.getAttribute('data-id');
            // Aquí iría lógica para obtener detalles (tal vez AJAX o ya están en la fila)
            // y llenar el contenido del modal de vista.
            // document.getElementById('assignmentIdView').innerText = assignmentId;
            // ... llenar otros campos ...
            openModal('assignmentViewModal'); // Asume que existe un modal con este ID
        }

        function closeViewModal() {
            closeModal('assignmentViewModal');
        }
        */

        // ----- Confirmaciones y Envíos -----

        function confirmDelete(event) {
            event.preventDefault(); // Detener el envío del formulario temporalmente
            const form = event.target.closest('form'); // Encuentra el formulario padre

            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡Esta asignación será eliminada y no podrás revertirlo!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33', // Rojo para confirmar eliminación
                cancelButtonColor: '#6b7280', // Gris para cancelar
                confirmButtonText: 'Sí, ¡eliminar!',
                cancelButtonText: 'Cancelar',
                reverseButtons: true // Poner el botón de confirmar a la derecha
            }).then((result) => {
                if (result.isConfirmed) {
                    // Si el usuario confirma, enviar el formulario
                    form.submit();
                }
            });
             return false; // Asegura que el formulario no se envíe si SweetAlert está activo
        }


        // --- Sidebar Toggle (copiado de tu ejemplo) ---
        document.addEventListener("DOMContentLoaded", function() {

            const toggleButton = document.getElementById("toggleSidebar"); // Asegúrate que este ID exista en tu sidebar.blade.php
            const sidebar = document.getElementById("sidebar"); // Asegúrate que este ID exista en tu sidebar.blade.php
            const mainContent = document.getElementById("mainContent");
            const initialMargin = 'sm:ml-80'; // El margen cuando el sidebar está visible en pantallas sm o mayores

            if (toggleButton && sidebar && mainContent) {
                toggleButton.addEventListener("click", function() {
                    sidebar.classList.toggle("-translate-x-full");

                    // Ajustar margen del contenido principal
                    if (sidebar.classList.contains("-translate-x-full")) {
                        // Sidebar oculto
                        mainContent.classList.remove(initialMargin);
                        mainContent.classList.add("ml-0"); // O el margen que deba tener cuando está oculto
                    } else {
                        // Sidebar visible
                        mainContent.classList.add(initialMargin);
                        mainContent.classList.remove("ml-0");
                    }
                });

                // Estado inicial al cargar la página (verificar si el sidebar está oculto por defecto en móvil)
                // Esto es importante para que el margen inicial sea correcto
                if (window.innerWidth < 640 && !sidebar.classList.contains('-translate-x-full')) {
                     // Si estamos en móvil y el sidebar NO está oculto inicialmente (raro, pero posible)
                     mainContent.classList.add(initialMargin); // O el margen correcto para móvil
                     mainContent.classList.remove("ml-0");
                } else if (window.innerWidth >= 640 && sidebar.classList.contains('-translate-x-full')) {
                    // Si estamos en desktop y el sidebar SÍ está oculto inicialmente
                    mainContent.classList.remove(initialMargin);
                    mainContent.classList.add("ml-0");
                } else if (window.innerWidth >= 640 && !sidebar.classList.contains('-translate-x-full')) {
                     // Estado normal en desktop (visible)
                     mainContent.classList.add(initialMargin);
                     mainContent.classList.remove("ml-0");
                } else {
                     // Estado normal en móvil (oculto)
                      mainContent.classList.remove(initialMargin);
                     mainContent.classList.add("ml-0");
                }


            } else {
                console.warn("Elementos del Sidebar o Toggle no encontrados. La funcionalidad de toggle puede no funcionar.");
            }
        });

    </script>

</body>
</html>