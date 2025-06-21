<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>Búsqueda Avanzada</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
</head>
<body class="min-h-screen bg-gray-100 mt-16 sm:mt-20">
    @include('layouts.navbar')

    <div class="flex min-h-screen">
        @include('layouts.sidebar')

        <div id="mainContent" class="flex-grow p-12 ml-80 transition-all duration-300">
            <div class="bg-white p-8 rounded-md w-full">
                <div class=" flex items-center justify-between pb-6">
                    <div>
                        <h2 class="text-gray-600 font-semibold">Búsqueda Avanzada de Usuarios</h2>
                        <span class="text-xs">Resultados de la búsqueda de usuarios en el sistema.</span>
                    </div>
                    <div class="flex items-center justify-between">

                        <div class="lg:ml-40 ml-10 space-x-8">
                            <!-- Aquí puedes agregar botones si es necesario -->
                        </div>
                    </div>
                </div>

                <!-- Formulario de Búsqueda Principal -->
                <form id="searchForm" class="mb-6">
                    <div class="flex space-x-4 mb-4">
                        <select name="filter_type" id="filterType" class="border-gray-300 rounded-lg p-2">
                            <option value="email">Email</option>
                            <option value="curp">CURP</option>
                            <option value="rfc">RFC</option>
                        </select>
                        <input type="text" name="search_term" id="searchTerm" placeholder="Ingrese búsqueda" class="border-gray-300 rounded-lg p-2 flex-grow">
                        <button type="button" id="searchButton" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                            Buscar
                        </button>
                    </div>
                </form>

                <!-- Loader -->
                <div id="loader" class="hidden text-center">
                    <div class="loader ease-linear rounded-full border-4 border-t-4 border-gray-200 h-12 w-12 mx-auto"></div>
                    <p class="text-gray-600">Cargando...</p>
                </div>

                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                            <table id="resultsTable" class="min-w-full leading-normal">
                                <thead>
                                    <tr style="background-color: #f2f2f2;">
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            ID
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Nombre
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Email
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Rol
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Teléfono
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Estado
                                        </th>
                                        <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                            Acciones
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="resultsBody">
                                    <!-- Aquí se insertarán los resultados de la búsqueda -->
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal para detalles del usuario -->
            <div id="userModal" class="fixed inset-0 z-50 bg-black bg-opacity-50 items-center justify-center hidden opacity-0 pointer-events-none transition-opacity duration-300 modal-enter-active modal-leave-active">
                <div class="bg-white p-6 rounded-lg w-1/2 modal-content">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-lg font-semibold">Detalles del Usuario</h3>
                        <button id="closeModal" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>
                    <div id="userDetails"></div>
                    <div class="mt-6 flex justify-end">
                        <!-- Botón de cierre ya está en la cabecera del modal -->
                    </div>
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
    $(document).ready(function() {
        // Inicializar DataTable en la tabla de resultados
        var table = $('#resultsTable').DataTable({
            "language": {
                "url": "/js/Spanish.json"
            },
            "dom": 'rtip', // Elimina la barra de búsqueda
        });

        $('#searchButton').on('click', function() {
            performSearch($('#filterType').val(), $('#searchTerm').val());
        });

        function performSearch(filterType, searchTerm) {
            if (searchTerm.trim() === '') {
                Swal.fire('Error', 'Ingrese un término de búsqueda.', 'error');
                return;
            }

            $('#loader').removeClass('hidden');
            $('#resultsTableContainer').addClass('hidden');

            $.ajax({
                url: "{{ route('users.resultados_busqueda') }}",
                type: "POST",
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                },
                data: {
                    filter_type: filterType,
                    search_term: searchTerm,
                },
                success: function(response) {
                    console.log("Respuesta de la API (Búsqueda Normal):", response);
                    displayResults(response);
                },
                error: function(xhr, status, error) {
                    $('#loader').addClass('hidden');
                    Swal.fire('Error', 'Hubo un problema al buscar los usuarios: ' + error);
                }
            });
        }

        function displayResults(response) {
            $('#loader').addClass('hidden');
            let results = [];

            // Verificar si la respuesta contiene un array o un solo objeto
            if (response && response.results) {
                if (Array.isArray(response.results)) {
                    results = response.results;
                } else if (typeof response.results === 'object' && response.results !== null) {
                    results = [response.results]; // Convertir el objeto en un array de un solo elemento
                }
            }

            console.log("Resultados obtenidos:", results); // Agregar log para ver los resultados procesados

            let resultsBodyId = '#resultsBody';
            let tableContainerId = '#resultsTableContainer';
            let tableId = '#resultsTable';

            $(resultsBodyId).empty(); // Limpiar los resultados previos

            if (results.length > 0) {
                // Construir las filas según los datos recibidos
                let tableData = results.map(user => {
                    let statusClass = user.status ? 'status-active' : 'status-inactive';
                    let statusText = user.status ? 'Activo' : 'Inactivo';

                    return [
                        user.userId, // Primera columna: ID
                        `${user.firstName} ${user.lastName}`, // Segunda columna: Nombre
                        user.email, // Tercera columna: Email
                        user.role ? user.role.name : 'Sin rol', // Cuarta columna: Rol
                        user.phone || 'N/A', // Quinta columna: Teléfono
                        `<span class="${statusClass}">${statusText}</span>`, // Sexta columna: Estado
                        `<button class="text-gray-600 hover:text-gray-900 view-user" data-user='${JSON.stringify(user)}'><i class="material-icons">visibility</i></button>` // Séptima columna: Botón "Ver"
                    ];
                });

                table.clear().rows.add(tableData).draw(); // Pasar los datos correctamente formateados a DataTables
                $(tableContainerId).removeClass('hidden');

            } else {
                Swal.fire('Sin resultados', response.message, 'info');
            }
        }

        // Function to format date and time
        function formatBirthdate(dateStr) {
            // Si no se recibe la fecha o está en formato "No Disponible"
            if (!dateStr || dateStr === 'No Disponible') return 'No Disponible';

            // Si la fecha incluye hora, extraemos solo la parte de la fecha
            let dateParts;
            if (dateStr.includes('T')) {
                dateParts = dateStr.split('T')[0].split('-'); // "2003-06-01T00:00:00.000+00:00" -> ["2003", "06", "01"]
            } else {
                // Si la fecha ya está en formato "yyyy-mm-dd"
                dateParts = dateStr.split('-'); // "2003-06-01" -> ["2003", "06", "01"]
            }

            // Si la fecha tiene el formato correcto, la formateamos como "dd/mm/yyyy"
            return dateParts.length === 3 ? `${dateParts[2]}/${dateParts[1]}/${dateParts[0]}` : 'Formato inválido';
        }

        function formatDateTime(dateString) {
            if (!dateString) return 'Aún no se ha modificado';

            const date = new Date(dateString);
            if (isNaN(date)) return 'N/A';

            const day = String(date.getDate()).padStart(2, '0');
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const year = date.getFullYear();
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');

            return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
        }

        // Mostrar detalles en modal
        $(document).on('click', '.view-user', function() {
            let user = JSON.parse($(this).attr('data-user'));

            let userDetailsHtml = `
                <div class="space-y-2">
                    <div class="icon-label">
                        <i class="material-icons">vpn_key</i>
                        <strong>ID:</strong>
                        <span class="ml-2">${user.userId}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">person</i>
                        <strong>Nombre:</strong>
                        <span class="ml-2">${user.firstName} ${user.lastName}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">email</i>
                        <strong>Correo:</strong>
                        <span class="ml-2">${user.email}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">work</i>
                        <strong>Rol:</strong>
                        <span class="ml-2">${user.role ? user.role.name : 'Sin rol'}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">phone</i>
                        <strong>Teléfono:</strong>
                        <span class="ml-2" >${user.phone || 'N/A'}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">badge</i>
                        <strong>RFC:</strong>
                        <span class="ml-2">${user.rfc || 'N/A'}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">fingerprint</i>
                        <strong>CURP:</strong>
                        <span class="ml-2">${user.curp || 'N/A'}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">cake</i>
                        <strong>Fecha de Nacimiento:</strong>
                        <span class="ml-2">${formatBirthdate(user.birthdate)}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">home</i>
                        <strong>Dirección:</strong>
                        <span class="ml-2">
                            ${user.address ?
                                `${user.address.street || 'N/A'}, ${user.address.number || 'N/A'}, ${user.address.neighborhood || 'N/A'}, ${user.address.city || 'N/A'}, ${user.address.state || 'N/A'}, ${user.address.zipCode || 'N/A'}, ${user.address.country || 'N/A'}`
                                : 'No disponible'}
                        </span>
                    </div>
                     <div class="icon-label">
                        <i class="material-icons">account_circle</i>
                        <strong>Nombre de Usuario:</strong>
                        <span class="ml-2">${user.username || 'No aplica'}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">event</i>
                        <strong>Creado:</strong>
                        <span class="ml-2">${formatDateTime(user.createdAt)}</span>
                    </div>
                    <div class="icon-label">
                        <i class="material-icons">update</i>
                        <strong>Actualizado:</strong>
                        <span class="ml-2">${formatDateTime(user.updatedAt)}</span>
                    </div>
                </div>
            `;

            $('#userDetails').html(userDetailsHtml);

            // Mostrar el modal con la clase para la animación
            $('#userModal').removeClass('hidden').addClass('flex');
            setTimeout(() => {
                $('#userModal').removeClass('opacity-0 pointer-events-none').addClass('opacity-100 pointer-events-auto');
            }, 50); // Pequeño delay para asegurar la transición
        });


        // Cerrar modal
        $('#closeModal').on('click', function() {
            // Cerrar modal
            $('#userModal').removeClass('opacity-100 pointer-events-auto').addClass('opacity-0 pointer-events-none');
            setTimeout(() => {
                $('#userModal').addClass('hidden').removeClass('flex');
            }, 300); // Esperar a que termine la transición

        });
    });

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