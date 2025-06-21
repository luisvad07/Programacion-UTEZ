<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gestión de Usuarios</title>
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
                        <h2 class="text-gray-600 font-semibold">Gestión de Usuarios</h2>
                        <span class="text-xs">Todos los usuarios del Sistema de Módulo de Atención Ciudadano del Estado de Morelos</span>
                    </div>
                    <div class="flex items-center justify-between">

                        <div class="lg:ml-40 ml-10 space-x-8">
                            <!--<button class="bg-indigo-600 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer">Nuevo Reporte</button>
                            <button
                                class="bg-indigo-600 px-4 py-2 rounded-md text-white font-semibold tracking-wide cursor-pointer">Crear
                                Usuario</button>-->
                        </div>
                    </div>
                </div>
                <div>
                    <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                        <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                            <table id="userTable" class="min-w-full leading-normal">
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
                                <tbody id="userTableBody">
                                    @if(isset($invalidInput) && $invalidInput)
                                    <tr>
                                        <td colspan="7" class="px-5 py-5 bg-white text-sm text-center">
                                            <p class="text-red-900 whitespace-no-wrap">Entrada no válida.</p>
                                        </td>
                                    </tr>
                                    @elseif(isset($notFoundMessage))
                                    <tr>
                                        <td colspan="7" class="px-5 py-5 bg-white text-sm text-center">
                                            <p class="text-gray-900 whitespace-no-wrap">{{ $notFoundMessage }}</p>
                                        </td>
                                    </tr>
                                    @elseif(!empty($users))
                                    @foreach($users as $user)
                                    <tr>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <p class="text-gray-900 whitespace-no-wrap">{{ $user['userId'] }}</p>
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <div class="flex items-center">
                                                <!--<div class="mr-2">
                                                    <img class="rounded-full h-8 w-8" src="https://via.placeholder.com/50" alt="User Avatar">
                                                </div>-->
                                                <div class="ml-3">
                                                    <p class="text-gray-900 whitespace-no-wrap">
                                                        {{ $user['firstName'] }} {{ $user['lastName'] }}
                                                    </p>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <p class="text-gray-900 whitespace-no-wrap">{{ $user['email'] }}</p>
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <p class="text-gray-900 whitespace-no-wrap">{{ $user['role']['name'] }}</p>
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <p class="text-gray-900 whitespace-no-wrap">{{ $user['phone'] }}</p>
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            @if($user['status'])
                                            <span class="status-active">Activo</span>
                                            @else
                                            <span class="status-inactive">Inactivo</span>
                                            @endif
                                        </td>
                                        <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                            <div class="flex items-center space-x-2">
                                                <button class="text-gray-600 hover:text-gray-900"
                                                    onclick="openViewModal(this)"
                                                    data-id="{{ $user['userId'] }}"
                                                    data-firstname="{{ $user['firstName'] }}"
                                                    data-lastname="{{ $user['lastName'] }}"
                                                    data-email="{{ $user['email'] }}"
                                                    data-phone="{{ $user['phone'] }}"
                                                    data-rfc="{{ $user['rfc'] ?? 'No Disponible' }}"
                                                    data-curp="{{ $user['curp'] ?? 'No Disponible' }}"
                                                    data-birthdate="{{ $user['birthdate'] ?? 'No Disponible' }}"
                                                    data-address="{{ json_encode($user['address'] ?? []) }}"
                                                    data-role="{{ $user['role']['name'] ?? 'No Disponible' }}"
                                                    data-username="{{ $user['username'] ?? 'No Aplica' }}"
                                                    data-created="{{ $user['createdAt'] ?? 'No Disponible' }}"
                                                    data-modified="{{ $user['updatedAt'] ?? 'No Disponible' }}">
                                                    <i class="material-icons">visibility</i>
                                                </button>
                                                <!-- Botón de Editar -->
                                                <button class="text-indigo-600 hover:text-indigo-900"
                                                        onclick="openEditModal(this)"
                                                        data-id="{{ $user['userId'] }}"
                                                        data-firstname="{{ $user['firstName'] }}"
                                                        data-lastname="{{ $user['lastName'] }}"
                                                        data-email="{{ $user['email'] }}"
                                                        data-phone="{{ $user['phone'] }}"
                                                        data-rfc="{{ $user['rfc'] ?? '' }}"
                                                        data-curp="{{ $user['curp'] ?? '' }}"
                                                        data-birthdate="{{ $user['birthdate'] ?? '' }}"
                                                        data-address="{{ json_encode($user['address'] ?? []) }}"
                                                        data-role="{{ $user['role']['name'] ?? '' }}"
                                                        data-role-id="{{ $user['role']['roleId'] ?? '' }}">
                                                    <i class="material-icons">edit</i>
                                                </button>
                                                <form action="{{ route('users.change-status', $user['userId']) }}" method="POST" style="display: inline;">
                                                    @csrf
                                                    @method('PATCH')
                                                    <button type="submit" class="text-sm px-3 py-2 rounded {{ $user['status'] ? 'text-green-600 hover:text-green-900' : 'text-red-600 hover:text-red-900' }}" onclick="confirmStatusChange(event, this)">
                                                        <i class="material-icons">{{ $user['status'] ? 'check_circle' : 'cancel' }}</i>
                                                    </button>
                                                </form>                                        
                                                <form action="{{ route('users.delete', $user['userId']) }}" method="POST" style="display: inline;">
                                                    @csrf
                                                    @method('DELETE')
                                                    <button type="submit" class="text-red-600 hover:text-red-900" onclick="confirmDelete(event, this)">
                                                        <i class="material-icons">delete</i>
                                                    </button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                    @endforeach
                                    @else
                                    <tr>
                                        <td colspan="7" class="px-5 py-5 bg-white text-sm text-center">
                                            <p class="text-gray-900 whitespace-no-wrap">No hay usuarios disponibles.</p>
                                        </td>
                                    </tr>
                                    @endif
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Ver Usuario -->
            <div id="userViewModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentView" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl overflow-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Detalles del Usuario</h2>
                        <button onclick="closeViewModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <div class="space-y-4">
                        <div class="flex items-center">
                            <i class="material-icons mr-2">person</i>
                            <strong>Nombre:</strong>
                            <span id="userFullNameView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">email</i>
                            <strong>Correo:</strong>
                            <span id="userEmailView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">phone</i>
                            <strong>Teléfono:</strong>
                            <span id="userPhoneView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">badge</i>
                            <strong>RFC:</strong>
                            <span id="userRfcView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">fingerprint</i>
                            <strong>CURP:</strong>
                            <span id="userCurpView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">cake</i>
                            <strong>Fecha de nacimiento:</strong>
                            <span id="userBirthdateView" class="ml-2"></span>
                        </div>
                        <div class="flex items-start">
                            <i class="material-icons mr-2">home</i>
                            <strong>Dirección:</strong>
                            <span id="userAddressView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">assignment_ind</i>
                            <strong>Rol:</strong>
                            <span id="userRoleView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">account_circle</i>
                            <strong>Nombre de Usuario:</strong>
                            <span id="usernameView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">event</i>
                            <strong>Fecha de Creación:</strong>
                            <span id="userCreatedView" class="ml-2"></span>
                        </div>
                        <div class="flex items-center">
                            <i class="material-icons mr-2">update</i>
                            <strong>Fecha de Modificación:</strong>
                            <span id="userModifiedView" class="ml-2"></span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Editar Usuario -->
            <div id="userEditModal" class="fixed inset-0 z-50 hidden items-center justify-center bg-gray-500 bg-opacity-75 transition-opacity duration-300">
                <div id="modalContentEdit" class="bg-white rounded-lg p-8 w-11/12 max-w-4xl overflow-auto transform transition-transform duration-300" style="transform: scale(0.8);">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-xl font-semibold">Editar Usuario</h2>
                        <button onclick="closeEditModal()" class="text-gray-500 hover:text-gray-900">
                            <i class="material-icons">close</i>
                        </button>
                    </div>

                    <!-- Formulario de Edición -->
                    <form id="editUserForm" action="" method="POST">
                        @csrf
                        @method('PUT') <!-- Usar PUT para actualizar -->

                        <input type="hidden" id="userId" name="userId"> <!-- Hidden input for user ID -->

                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label for="firstName" class="block text-gray-700 text-sm font-bold mb-2">Nombre:</label>
                                <input type="text" id="firstName" name="firstName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="lastName" class="block text-gray-700 text-sm font-bold mb-2">Apellido:</label>
                                <input type="text" id="lastName" name="lastName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email:</label>
                                <input type="email" id="email" name="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="phone" class="block text-gray-700 text-sm font-bold mb-2">Teléfono:</label>
                                <input type="text" id="phone" name="phone" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="rfc" class="block text-gray-700 text-sm font-bold mb-2">RFC:</label>
                                <input type="text" id="rfc" name="rfc" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="curp" class="block text-gray-700 text-sm font-bold mb-2">CURP:</label>
                                <input type="text" id="curp" name="curp" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="birthdate" class="block text-gray-700 text-sm font-bold mb-2">Fecha de Nacimiento:</label>
                                <input type="date" id="birthdate" name="birthdate" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                            </div>
                            <div>
                                <label for="username" class="block text-gray-700 text-sm font-bold mb-2">Nombre de Usuario:</label>
                                <input type="text" id="username" name="username" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" readonly>
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
                                    <input type="text" id="interiorNumber" name="interiorNumber" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
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
                            <input type="hidden" id="roleId" name="roleId">
                            <label for="role" class="block text-gray-700 text-sm font-bold mb-2">Rol:</label>
                            <input type="text" id="roleName" name="roleName" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" readonly>

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
        $('#userTable').DataTable({
            "language": {
                "url": "js/Spanish.json"
            }
        });
    });

    function submitFormWithConfirmation() {
        // Usar SweetAlert2 para confirmar la acción
        Swal.fire({
            title: '¿Está seguro de que desea actualizar este usuario?',
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
                document.getElementById("editUserForm").submit();
            }
        });
    }

    // Nueva función para formatear la fecha de nacimiento
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

    function openViewModal(button) {

        document.getElementById('userFullNameView').innerText =
            button.getAttribute('data-firstname') + " " + button.getAttribute('data-lastname');
        document.getElementById('userEmailView').innerText = button.getAttribute('data-email');
        document.getElementById('userPhoneView').innerText = button.getAttribute('data-phone');
        document.getElementById('userRfcView').innerText = button.getAttribute('data-rfc');
        document.getElementById('userCurpView').innerText = button.getAttribute('data-curp');
        let birthdate = button.getAttribute('data-birthdate');
        document.getElementById('userBirthdateView').innerText = formatBirthdate(birthdate);

        // Obtener la dirección desde JSON
        let address = JSON.parse(button.getAttribute('data-address') || '{}');
        document.getElementById('userAddressView').innerText =
            `${address.street ?? 'No Disponible'} ${address.number ?? 'S/N'} ${address.interiorNumber ?? 'S/N'} , ` +
            `Col. ${address.neighborhood ?? 'No Disponible'}, ${address.zipCode ?? 'No Disponible'} ${address.city ?? 'No Disponible'}, ` +
            `${address.state ?? 'No Disponible'}, ${address.country ?? 'No Disponible'}`;

            if ((!address.street && !address.number && !address.neighborhood && !address.city && !address.state && !address.country) || Object.keys(address).length === 0) {
                document.getElementById('userAddressView').innerText = "Dirección no disponible";
            }

        document.getElementById('userRoleView').innerText = button.getAttribute('data-role');
        document.getElementById('usernameView').innerText = button.getAttribute('data-username');


        // Mostrar las fechas formateadas
        let createdDate = button.getAttribute('data-created');
        let modifiedDate = button.getAttribute('data-modified');

        document.getElementById('userCreatedView').innerText = formatDate(createdDate);
        document.getElementById('userModifiedView').innerText = formatDate(modifiedDate);

        // Mostrar el modal
        const modal = document.getElementById('userViewModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentView');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)'); // Establecer estado inicial

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300); // Animación
        }, 50);
    }

    function closeViewModal() {
        const modal = document.getElementById('userViewModal');
        const modalContent = document.getElementById('modalContentView');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    function formatForInputDate(dateStr) {
        if (!dateStr) return '';  // Si la fecha no está disponible

        // Si la fecha incluye hora, extraemos solo la parte de la fecha
        if (dateStr.includes('T')) {
            dateStr = dateStr.split('T')[0]; // "2003-06-01T00:00:00.000+00:00" -> "2003-06-01"
        }
        
        return dateStr;  // Asegurándonos de que está en formato "yyyy-mm-dd"
    }

    function openEditModal(button) {

        // Obtener datos del botón
        let userId = button.getAttribute('data-id');
        let firstName = button.getAttribute('data-firstname');
        let lastName = button.getAttribute('data-lastname');
        let email = button.getAttribute('data-email');
        let phone = button.getAttribute('data-phone');
        let rfc = button.getAttribute('data-rfc');
        let curp = button.getAttribute('data-curp');
        let birthdate = button.getAttribute('data-birthdate');
        // Formatear la fecha para que sea compatible con el campo de entrada
        let formattedBirthdate = formatForInputDate(birthdate);
        let address = JSON.parse(button.getAttribute('data-address') || '{}');
        let addressId = address.addressId || '';
        let roleId = button.getAttribute('data-role-id');
        let roleName = button.getAttribute('data-role');
        let username = button.getAttribute('data-username');


        // Establecer los valores en el formulario
        document.getElementById('firstName').value = firstName;
        document.getElementById('lastName').value = lastName;
        document.getElementById('email').value = email;
        document.getElementById('phone').value = phone;
        document.getElementById('rfc').value = rfc;
        document.getElementById('curp').value = curp;
        document.getElementById('birthdate').value = formattedBirthdate;
        document.getElementById('street').value = address.street || '';
        document.getElementById('number').value = address.number || '';
        document.getElementById('interiorNumber').value = address.interiorNumber || '';
        document.getElementById('neighborhood').value = address.neighborhood || '';
        document.getElementById('zipCode').value = address.zipCode || '';
        document.getElementById('city').value = address.city || '';
        document.getElementById('state').value = address.state || '';
        document.getElementById('country').value = address.country || '';
        document.getElementById('roleId').value = roleId;
        document.getElementById('roleName').value = roleName;
        document.getElementById('username').value = username;
        document.getElementById('addressId').value = addressId;

        // Construir la URL de actualización con el ID del usuario
        let form = document.getElementById('editUserForm');
        form.action = `/users/update/${userId}`; // Asumiendo que tienes una ruta definida para la actualización

        // Mostrar el modal
        const modal = document.getElementById('userEditModal');
        $(modal).removeClass('hidden').addClass('flex');

        // Animación de entrada
        const modalContent = document.getElementById('modalContentEdit');
        $(modalContent).css('opacity', 0).css('transform', 'scale(0.8)'); // Establecer estado inicial

        setTimeout(() => {
            $(modalContent).animate({ opacity: 1, transform: 'scale(1)' }, 300); // Animación
        }, 50);

        document.getElementById('editUserForm').onsubmit = function(event) {
            event.preventDefault(); // Prevenir el submit normal

            submitFormWithConfirmation(); // Llamar a la función para mostrar el confirm y enviar
        };
    }

    function closeEditModal() {
        const modal = document.getElementById('userEditModal');
        const modalContent = document.getElementById('modalContentEdit');

        $(modalContent).animate({ opacity: 0, transform: 'scale(0.8)' }, 300, function() {
            $(modal).removeClass('flex').addClass('hidden');
        });
    }

    // Función para confirmar cambio de estado
    function confirmStatusChange(event, button) {
        event.preventDefault(); // Evitar que el formulario se envíe inmediatamente

        Swal.fire({
            title: '¿Estás seguro?',
            text: "¿Quieres cambiar el estado de este usuario?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Cambiar Estado',
            cancelButtonText: 'Cancelar',
            reverseButtons: true,
        }).then((result) => {
            if (result.isConfirmed) {
                // Si confirma, enviamos el formulario
                button.closest('form').submit();
            }
        });
    }

    // Función para confirmar eliminación de usuario
    function confirmDelete(event, button) {
        event.preventDefault(); // Evitar que el formulario se envíe inmediatamente

        Swal.fire({
            title: '¿Estás seguro de eliminar este usuario?',
            text: "¡No podrás revertir esta acción!",
            icon: 'error',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Eliminar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true,
        }).then((result) => {
            if (result.isConfirmed) {
                // Si confirma, enviamos el formulario
                button.closest('form').submit();
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
        });
    });
</script>
</html>