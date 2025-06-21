<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Categorías</title>
    @vite(['resources/css/app.css', 'resources/js/app.js']) {{-- Asegúrate que Vite esté configurado --}}
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1"> {{-- Ajusta path si es necesario --}}
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20"> {{-- Ajusta márgenes si navbar tiene altura fija --}}
    @include('layouts.navbar') {{-- Asume que tienes este layout --}}

    <div class="flex min-h-screen">
        @include('layouts.sidebar') {{-- Asume que tienes este layout --}}

        {{-- Contenido Principal --}}
        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300"> {{-- Ajusta ml inicial/responsivo --}}
            <div class="bg-white p-6 md:p-8 rounded-lg shadow-md w-full">
                {{-- Encabezado y Botón Crear --}}
                <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between pb-6 gap-4">
                    <div>
                        <h2 class="text-gray-700 font-semibold">Gestión de Categorías de Noticias</h2>
                        <span class="text-xs text-gray-500">Todas las categorías de noticias registradas</span>
                    </div>
                    <div class="flex items-center">
                        <button class="bg-green-600 hover:bg-green-700 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer flex items-center shadow transition duration-150 ease-in-out" onclick="openCreateModal()">
                            <i class="material-icons mr-2">add</i>
                            Nueva Categoría
                        </button>
                    </div>
                </div>

                {{-- Tabla de Categorías --}}
                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                            <table id="categoryTable" class="min-w-full leading-normal">
                                <thead>
                                    <tr class="bg-gray-100">
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            ID
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Nombre
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Descripción
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Acciones
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="categoryTableBody">
                                    @foreach($categories as $category)
                                        <tr class="hover:bg-gray-50">
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-900 whitespace-no-wrap">{{ $category['categoryId'] }}</p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <p class="text-gray-900 whitespace-no-wrap font-medium">{{ $category['name'] }}</p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                {{-- Truncar descripción larga opcionalmente --}}
                                                <p class="text-gray-700 whitespace-normal">{{ \Illuminate\Support\Str::limit($category['description'] ?? 'N/A', 100) }}</p>
                                            </td>
                                            <td class="px-5 py-4 border-b border-gray-200 bg-white text-sm">
                                                <div class="flex items-center space-x-3">
                                                    {{-- Botón Ver --}}
                                                    <button class="text-gray-600 hover:text-gray-900 transition duration-150 ease-in-out"
                                                            title="Ver Detalles"
                                                            onclick="openViewModal(this)"
                                                            data-id="{{ $category['categoryId'] }}"
                                                            data-name="{{ $category['name'] }}"
                                                            data-description="{{ $category['description'] ?? '' }}">
                                                        <i class="material-icons text-lg">visibility</i>
                                                    </button>

                                                    {{-- Permisos para Editar/Eliminar --}}
                                                    @if(session('user') && isset(session('user')['role']['name']) && strtoupper(session('user')['role']['name']) === 'ADMINISTRADOR')
                                                        {{-- Botón Editar --}}
                                                        <button class="text-yellow-500 hover:text-yellow-600 transition duration-150 ease-in-out"
                                                                title="Editar Categoría"
                                                                onclick="openEditModal(this)"
                                                                data-id="{{ $category['categoryId'] }}"
                                                                data-name="{{ $category['name'] }}"
                                                                data-description="{{ $category['description'] ?? '' }}">
                                                            <i class="material-icons text-lg">edit</i>
                                                        </button>

                                                        {{-- Botón Eliminar (con formulario) --}}
                                                        <form action="{{ route('categories.delete', $category['categoryId']) }}" method="POST" style="display: inline;" onsubmit="return confirmDelete(event);">
                                                            @csrf
                                                            @method('DELETE')
                                                            <button type="submit" class="text-red-500 hover:text-red-600 transition duration-150 ease-in-out" title="Eliminar Categoría">
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

            <!-- Modal Crear Categoría -->
            <div id="categoryCreateModal" class="modal fixed inset-0 z-50 hidden items-center justify-center bg-gray-900 bg-opacity-60 transition-opacity duration-300" onclick="closeModalOutside(event, 'categoryCreateModal')">
                <div id="modalContentCreate" class="modal-content bg-white rounded-lg p-6 md:p-8 w-11/12 max-w-lg max-h-[85vh] overflow-y-auto transform transition-transform duration-300 scale-95 opacity-0">
                    {{-- Encabezado Modal --}}
                    <div class="flex justify-between items-center mb-4 pb-3 border-b border-gray-200">
                        <h2 class="text-lg md:text-xl font-semibold text-gray-800">Crear Nueva Categoría</h2>
                        <button onclick="closeCreateModal()" class="text-gray-400 hover:text-gray-700 transition duration-150 ease-in-out">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Creación -->
                    <form id="createCategoryForm" action="{{ route('categories.create') }}" method="POST">
                        @csrf
                        <div class="space-y-4">
                            <div>
                                <label for="create_name" class="block text-gray-700 text-sm font-bold mb-2">Nombre <span class="text-red-500">*</span></label>
                                <input type="text" id="create_name" name="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" required maxlength="50">
                                <span id="create_name_error" class="text-red-500 text-xs mt-1"></span>
                            </div>
                            <div>
                                <label for="create_description" class="block text-gray-700 text-sm font-bold mb-2">Descripción</label>
                                <textarea id="create_description" name="description" rows="4" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"></textarea>
                                <span id="create_description_error" class="text-red-500 text-xs mt-1"></span>
                            </div>
                        </div>

                        {{-- Botones del Modal --}}
                        <div class="mt-6 pt-4 border-t border-gray-200 flex justify-end space-x-3">
                             <button type="button" onclick="closeCreateModal()" class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                                Cancelar
                            </button>
                            <button id="createButton" type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center transition duration-150 ease-in-out">
                                <i class="material-icons mr-2">save</i>
                                Guardar Categoría
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Modal Ver Categoría -->
            <div id="categoryViewModal" class="modal fixed inset-0 z-50 hidden items-center justify-center bg-gray-900 bg-opacity-60 transition-opacity duration-300" onclick="closeModalOutside(event, 'categoryViewModal')">
                 <div id="modalContentView" class="modal-content bg-white rounded-lg p-6 md:p-8 w-11/12 max-w-lg max-h-[85vh] overflow-y-auto transform transition-transform duration-300 scale-95 opacity-0">
                    {{-- Encabezado Modal --}}
                    <div class="flex justify-between items-center mb-4 pb-3 border-b border-gray-200">
                        <h2 class="text-lg md:text-xl font-semibold text-gray-800">Detalles de la Categoría</h2>
                        <button onclick="closeViewModal()" class="text-gray-400 hover:text-gray-700 transition duration-150 ease-in-out">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    {{-- Contenido del Modal Ver --}}
                    <div class="space-y-4">
                        <div class="flex items-center border-b pb-2">
                             <i class="material-icons mr-3 text-gray-500">fingerprint</i>
                             <div class="flex-grow">
                                <strong class="block text-sm text-gray-500">ID:</strong>
                                <span id="categoryIdView" class="text-gray-800"></span>
                             </div>
                        </div>
                        <div class="flex items-center border-b pb-2">
                            <i class="material-icons mr-3 text-gray-500">label</i>
                             <div class="flex-grow">
                                <strong class="block text-sm text-gray-500">Nombre:</strong>
                                <span id="categoryNameView" class="text-gray-800 font-semibold"></span>
                            </div>
                        </div>
                        <div class="flex items-start border-b pb-2">
                            <i class="material-icons mr-3 text-gray-500 pt-1">description</i>
                             <div class="flex-grow">
                                <strong class="block text-sm text-gray-500">Descripción:</strong>
                                <p id="categoryDescriptionView" class="text-gray-700 whitespace-pre-wrap"></p> {{-- whitespace-pre-wrap para respetar saltos de línea --}}
                            </div>
                        </div>
                    </div>

                     {{-- Botón Cerrar --}}
                     <div class="mt-6 pt-4 border-t border-gray-200 flex justify-end">
                         <button onclick="closeViewModal()" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                            Cerrar
                         </button>
                     </div>
                </div>
            </div>

            <!-- Modal Editar Categoría -->
            <div id="categoryEditModal" class="modal fixed inset-0 z-50 hidden items-center justify-center bg-gray-900 bg-opacity-60 transition-opacity duration-300" onclick="closeModalOutside(event, 'categoryEditModal')">
                <div id="modalContentEdit" class="modal-content bg-white rounded-lg p-6 md:p-8 w-11/12 max-w-lg max-h-[85vh] overflow-y-auto transform transition-transform duration-300 scale-95 opacity-0">
                    {{-- Encabezado Modal --}}
                    <div class="flex justify-between items-center mb-4 pb-3 border-b border-gray-200">
                        <h2 class="text-lg md:text-xl font-semibold text-gray-800">Editar Categoría</h2>
                        <button onclick="closeEditModal()" class="text-gray-400 hover:text-gray-700 transition duration-150 ease-in-out">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Edición -->
                    {{-- La action se establecerá dinámicamente con JS --}}
                    <form id="editCategoryForm" action="" method="POST">
                        @csrf
                        @method('PUT')

                        {{-- ID oculto para referencia --}}
                        <input type="hidden" id="edit_categoryId" name="categoryId">

                         <div class="space-y-4">
                            <div>
                                <label for="edit_name" class="block text-gray-700 text-sm font-bold mb-2">Nombre <span class="text-red-500">*</span></label>
                                <input type="text" id="edit_name" name="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" required maxlength="50">
                                <span id="edit_name_error" class="text-red-500 text-xs mt-1"></span>
                            </div>
                            <div>
                                <label for="edit_description" class="block text-gray-700 text-sm font-bold mb-2">Descripción</label>
                                <textarea id="edit_description" name="description" rows="4" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"></textarea>
                                <span id="edit_description_error" class="text-red-500 text-xs mt-1"></span>
                            </div>
                        </div>

                        {{-- Botones del Modal --}}
                        <div class="mt-6 pt-4 border-t border-gray-200 flex justify-end space-x-3">
                             <button type="button" onclick="closeEditModal()" class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                                Cancelar
                            </button>
                            <button id="saveButton" type="submit" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline flex items-center transition duration-150 ease-in-out">
                                <i class="material-icons mr-2">update</i>
                                <span id="buttonText">Guardar Cambios</span>
                                {{-- Spinner de carga (opcional) --}}
                                <span id="loadingSpinner" class="hidden ml-2">
                                    <i class="material-icons animate-spin">sync</i>
                                </span>
                            </button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div> 

    {{-- Scripts --}}
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
                confirmButtonText: 'Aceptar',
                confirmButtonColor: '#d33' // Color rojo para botón de error
            });
        </script>
    @endif

    {{-- Manejo de errores de validación de Laravel (opcional, mejora UX en modales) --}}
    @if ($errors->any())
    <script>
        let errorMessages = @json($errors->all());
        let errorText = "Se encontraron los siguientes errores:\n\n";
        errorMessages.forEach(function(error) {
            errorText += `- ${error}\n`;
        });

        Swal.fire({
            title: 'Error de Validación',
            text: errorText,
            icon: 'error',
            confirmButtonText: 'Entendido',
             confirmButtonColor: '#d33'
        });
    </script>
    @endif


    <script>
        $(document).ready(function() {
            $('#categoryTable').DataTable({
                "language": {
                    "url": "js/Spanish.json", // Asegúrate que este archivo exista
                    "emptyTable": "No hay categorías disponibles en la tabla.",
                },
            });
        });

        // ----- Funciones para Modales -----

        function openModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content'); // Busca el contenido dentro del modal
            if (!modal || !modalContent) return;

            $(modal).removeClass('hidden').addClass('flex');
            // Forzar reflow para asegurar que la transición se aplique
            modal.offsetHeight;

            $(modal).css('opacity', 1);
            $(modalContent).css('opacity', 1).css('transform', 'scale(1)');
        }

        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            const modalContent = modal.querySelector('.modal-content');
             if (!modal || !modalContent) return;

            $(modalContent).css('opacity', 0).css('transform', 'scale(0.95)');
            $(modal).css('opacity', 0);

            setTimeout(() => {
                 $(modal).removeClass('flex').addClass('hidden');
            }, 300); // Espera a que termine la transición (0.3s)
        }

        // Cerrar modal al hacer clic fuera del contenido
        function closeModalOutside(event, modalId) {
             const modalContent = document.getElementById('modalContent' + modalId.replace('category', '')); // e.g., modalContentCreate
             if (event.target !== event.currentTarget && !modalContent.contains(event.target)) {
                 // Clic fuera del contenido (en el overlay)
                 // Opcional: No cerrar si el clic fue en el contenido del modal
             } else if (event.target === event.currentTarget) {
                  // Clic directo en el overlay
                  closeModal(modalId);
             }
        }


        // --- Funciones Específicas para cada Modal ---

        function openCreateModal() {
             // Limpiar formulario antes de abrir
            $('#createCategoryForm')[0].reset();
            $('.text-red-500.text-xs.mt-1').text(''); // Limpiar mensajes de error inline
            openModal('categoryCreateModal');
        }

        function closeCreateModal() {
            closeModal('categoryCreateModal');
        }

        function openViewModal(button) {
            // Obtener datos del botón
            let id = button.getAttribute('data-id');
            let name = button.getAttribute('data-name');
            let description = button.getAttribute('data-description');

            // Llenar modal de vista
            document.getElementById('categoryIdView').innerText = id;
            document.getElementById('categoryNameView').innerText = name;
            document.getElementById('categoryDescriptionView').innerText = description || 'N/A'; // Mostrar N/A si es null/vacío

            openModal('categoryViewModal');
        }

        function closeViewModal() {
            closeModal('categoryViewModal');
        }

        function openEditModal(button) {
            // Obtener datos del botón
            let id = button.getAttribute('data-id');
            let name = button.getAttribute('data-name');
            let description = button.getAttribute('data-description');

             // Limpiar errores previos
             $('.text-red-500.text-xs.mt-1').text('');

            // Llenar formulario de edición
            document.getElementById('edit_categoryId').value = id;
            document.getElementById('edit_name').value = name;
            document.getElementById('edit_description').value = description || '';

            // Establecer la URL de acción del formulario
            let form = document.getElementById('editCategoryForm');
             // Usa la ruta nombrada de Laravel para generar la URL
            form.action = '{{ route("categories.update", ["id" => ":id"]) }}'.replace(':id', id);

            // Configurar el envío con confirmación
            form.onsubmit = function(event) {
                event.preventDefault(); // Prevenir el envío normal
                submitEditFormWithConfirmation(form); // Llamar a la función de confirmación
            };


            openModal('categoryEditModal');
        }

        function closeEditModal() {
            closeModal('categoryEditModal');
        }

        // ----- Confirmaciones y Envíos -----

        function confirmDelete(event) {
            event.preventDefault(); // Detener el envío del formulario temporalmente
            const form = event.target.closest('form'); // Encuentra el formulario padre

            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡No podrás revertir esta acción!",
                icon: 'warning', // Cambiado a warning para eliminación
                showCancelButton: true,
                confirmButtonColor: '#d33', // Rojo para confirmar eliminación
                cancelButtonColor: '#3085d6', // Azul para cancelar
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

        function submitEditFormWithConfirmation(form) {
            Swal.fire({
                title: '¿Confirmar Cambios?',
                text: "Se actualizarán los datos de la categoría.",
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#f59e0b', // Amarillo/Naranja para editar
                cancelButtonColor: '#6b7280', // Gris para cancelar
                confirmButtonText: 'Sí, guardar',
                cancelButtonText: 'No, cancelar',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    // Opcional: Mostrar estado de carga en el botón
                    const saveButton = document.getElementById('saveButton');
                    const buttonText = document.getElementById('buttonText');
                    const loadingSpinner = document.getElementById('loadingSpinner');

                    if (saveButton && buttonText && loadingSpinner) {
                        saveButton.disabled = true;
                        buttonText.textContent = "Guardando...";
                        loadingSpinner.classList.remove("hidden");
                    }

                    // Enviar el formulario
                    form.submit();
                }
            });
        }

        // --- Sidebar Toggle (si usas el mismo layout de eventos) ---
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
            });
        });

    </script>

</body>
</html>