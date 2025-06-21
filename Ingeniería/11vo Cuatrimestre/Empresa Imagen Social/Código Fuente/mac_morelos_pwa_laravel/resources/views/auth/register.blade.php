<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale: 1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registro de Usuario - SIMAC MORELOS</title>
    @vite(['resources/css/app.css', 'resources/js/app.js'])
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
</head>
<body>
    <div class="flex h-screen w-full items-center justify-center bg-gray-900 bg-cover bg-no-repeat" style="background-image:var(--fondo-login)">
        <div class="relative">
            <div class="rounded-xl px-16 py-10 shadow-lg backdrop-blur-md max-sm:px-8 register-modal">
                <div class="text-white">
                    <div class="mb-8 flex flex-col items-center">
                        <img id="logo-image" src="" width="150" alt="Logo" />
                        <h1 class="mb-2 text-2xl">SIMAC MORELOS - Registro</h1>
                        <span class="text-gray-300">Crea una nueva cuenta</span>
                    </div>

                    @if ($errors->any())
                    <div class="bg-red-500 text-white p-3 rounded-md mb-4 text-center">
                        @foreach ($errors->all() as $error)
                        <p>{{ $error }}</p>
                        @endforeach
                    </div>
                    @endif

                    <form action="{{ route('register') }}" method="POST" id="registerForm">
                        @csrf

                        <!-- Step Indicators -->
                        <div class="step-indicators">
                            <div class="step-indicator active" data-step="1"><i class="material-icons">person</i> Datos Personales</div>
                            <div class="step-indicator" data-step="2"><i class="material-icons">location_on</i> Ubicación (1/2)</div>
                            <div class="step-indicator" data-step="3"><i class="material-icons">location_on</i> Ubicación (2/2)</div>
                            <div class="step-indicator" data-step="4"><i class="material-icons">map</i> Mapa</div>
                            <div class="step-indicator" data-step="5"><i class="material-icons">lock</i> Credenciales y Rol</div>
                            <div class="step-indicator" data-step="6"><i class="material-icons">assignment</i> Campos Adicionales</div>
                        </div>

                        <!-- Step 1: Datos Personales -->
                        <div class="step active" data-step="1">

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">account_circle</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="firstName" placeholder="Nombre(s)" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">account_circle</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="lastName" placeholder="Apellido(s)" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">email</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="email" name="email" placeholder="Correo Electrónico" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">phone</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="tel" name="phone" placeholder="Teléfono" required data-required="true" />
                            </div>
                        </div>

                        <!-- Step 2: Ubicación 1/2 -->
                        <div class="step" data-step="2">
                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="street" placeholder="Calle/Avenida" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="number" placeholder="Número" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="interiorNumber" placeholder="Número Interior (Opcional)" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="neighborhood" placeholder="Colonia" required data-required="true" />
                            </div>

                        </div>

                        <!-- Step 3: Ubicación 2/2 -->
                        <div class="step" data-step="3">
                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="zipCode" placeholder="Código Postal" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">location_on</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="city" placeholder="Ciudad/Municipio" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">map</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="state" value="Morelos" placeholder="Estado" required data-required="true" readonly/>
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">flag</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="country" value="México" placeholder="País" required data-required="true" readonly/>
                            </div>

                        </div>

                        <!-- Step 4: Mapa -->
                        <div class="step" data-step="4">
                            <div class="mb-4 text-lg flex items-center space-x-2 bg-gray-800 text-white p-3 rounded-lg shadow-lg">
                                <span class="material-icons text-white text-2xl">location_on</span>
                                <p class="text-white font-medium">Ubica en el mapa tu dirección</p>
                            </div>                            
                            <div id="mapContainer"></div>  <!-- Contenedor del mapa -->
                            <input type="hidden" name="latitude" id="latitude" required>
                            <input type="hidden" name="longitude" id="longitude" required>
                        </div>

                        <!-- Step 5: Credenciales y Rol -->
                        <div class="step" data-step="5">

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">cake</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="date" name="birthdate" placeholder="Fecha de Nacimiento" required data-required="true" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">lock</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="password" name="password" id="password" placeholder="Contraseña" required data-required="true" />
                                <span class="material-icons absolute right-2 top-1/2 transform -translate-y-1/2 text-white cursor-pointer" id="togglePassword">visibility_off</span>
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">lock</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="password" name="password_confirmation" id="password_confirmation" placeholder="Confirmar Contraseña" required data-required="true" />
                                <span class="material-icons absolute right-2 top-1/2 transform -translate-y-1/2 text-white cursor-pointer" id="togglePasswordConfirmation">visibility_off</span>
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">supervisor_account</span>
                                <select class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" name="role" required data-required="true">
                                    <option value="" disabled selected>Seleccionar Rol</option>
                                    <option value="PROMOTOR">PROMOTOR</option>
                                    <option value="ADMINISTRADOR">ADMINISTRADOR</option>
                                    <option value="SUPERVISOR">SUPERVISOR</option>
                                    <option value="PERIODISTA">PERIODISTA</option>
                                </select>
                            </div>
                        </div>

                        <!-- Step 6: Campos Adicionales -->
                        <div class="step" data-step="6">

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">assignment_ind</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="curp" placeholder="CURP (Opcional)" />
                            </div>

                            <div class="mb-4 text-lg relative">
                                <span class="material-icons absolute left-2 top-1/2 transform -translate-y-1/2 text-white">assignment</span>
                                <input class="rounded-3xl border-none pl-12 px-6 py-2 text-center text-inherit placeholder-slate-200 shadow-lg outline-none backdrop-blur-md w-full register-input" type="text" name="rfc" placeholder="RFC (Opcional)" />
                            </div>

                            <div class="mt-8 flex justify-center text-lg text-black">
                                <button type="submit" class="rounded-3xl px-10 py-2 text-white shadow-xl backdrop-blur-md transition-colors duration-300 hover:text-white register-button flex items-center"
                                    id="registerButton">
                                    <span class="material-icons mr-2">upgrade</span> Registrar
                                </button>
                            </div>
                        </div>
                    </form>

                    <div class="mt-4 text-center">
                        <a href="{{ route('login') }}" class="text-gray-300 hover:text-white transition-colors duration-300">¿Ya tienes una cuenta? Inicia sesión</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {

            const accessPassword = "simac2025mormx$";

            // Initialize logo image here before the Swal.fire
            const logoImg = document.getElementById('logo-image');
            const logoUrl = getComputedStyle(document.documentElement).getPropertyValue('--logo-url');
            if (logoUrl) {
                logoImg.src = logoUrl.replace(/^url\(['"]?/, '').replace(/['"]?\)$/, ''); // Eliminar 'url()'
            } else {
                console.error("La variable CSS '--logo-url' no está definida.");
            }

            Swal.fire({
                title: 'Acceso Requerido',
                text: 'Por favor, ingrese la contraseña de acceso:',
                input: 'password',
                confirmButtonText: 'Verificar',
                confirmButtonColor: '#70795B',
                cancelButtonText: 'Cancelar',
                showCancelButton: true,
                reverseButtons: true,
                preConfirm: (password) => {
                    if (password === accessPassword) {
                        return true;
                    } else {
                        Swal.showValidationMessage('Contraseña incorrecta');
                        return false;
                    }
                },
                allowOutsideClick: false,
                allowEscapeKey: false
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Acceso Concedido',
                        text: 'Registro habilitado.',
                        icon: 'success',
                        timer: 2000,
                        showConfirmButton: false
                    });

                    initializeApp(); // Llama a la función para inicializar la app.
                } else {
                    Swal.fire({
                        title: 'Acceso Denegado',
                        text: 'Redirigiendo a la página de inicio de sesión.',
                        icon: 'error',
                        timer: 2000,
                        showConfirmButton: false
                    }).then(() => {
                        window.location.href = "{{ route('login') }}"; // Redirige al login.
                    });
                }
            });

            function initializeApp() {
                 // Logo
                
        
                // Function to show SweetAlert
                function showSweetAlert(title, text, icon) {
                    Swal.fire({
                        title: title,
                        text: text,
                        icon: icon,
                        confirmButtonText: 'OK'
                    });
                }
        
                // Function to get step name
                function getStepName(stepNumber) {
                    switch (stepNumber) {
                        case 1:
                            return "Datos Personales";
                        case 2:
                            return "Ubicación (1/2)";
                        case 3:
                            return "Ubicación (2/2)";
                        case 4:
                            return "Mapa";
                        case 5:
                            return "Credenciales y Rol";
                        case 6:
                            return "Campos Adicionales";
                        default:
                            return "Paso Desconocido";
                    }
                }
        
                // Function to check if a field is empty
                function isFieldEmpty(field) {
                    return !field.value.trim();
                }
        
                // Function to validate a step
                function validateStep(stepNumber) {
                    const step = document.querySelector(`.step[data-step="${stepNumber}"]`);  // Corrected querySelector
                    const requiredFields = step.querySelectorAll('input[required], select[required]');
                    let isValid = true;
        
                    requiredFields.forEach(field => {
                        if (isFieldEmpty(field)) {
                            isValid = false;
                            showSweetAlert('Campos Requeridos', `Por favor, completa todos los campos requeridos en ${getStepName(stepNumber)}.`, 'error');
                            field.focus();
                            return false; // Break out of the forEach loop
                        }
                    });
        
                    if (!isValid) {
                        return false; // Step is invalid
                    }
        
                    if (stepNumber === 5) { // Paso de las credenciales, donde está el cumpleaños
                        const birthdateInput = document.querySelector('input[name="birthdate"]');
                        const birthdate = new Date(birthdateInput.value);
                        const today = new Date();
                        let age = today.getFullYear() - birthdate.getFullYear();
                        const monthDiff = today.getMonth() - birthdate.getMonth();
        
                        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthdate.getDate())) {
                            age--;
                        }
        
                        if (age < 18) {
                            showSweetAlert('Edad Incorrecta', 'Debes ser mayor de 18 años para registrarte.', 'error');
                            birthdateInput.focus();
                            return false; // La validación falla
                        }
                    }
        
        
                    return isValid;
                }
        
                // Password visibility
                const togglePassword = document.getElementById('togglePassword');
                const password = document.getElementById('password');
                const togglePasswordConfirmation = document.getElementById('togglePasswordConfirmation');
                const passwordConfirmation = document.getElementById('password_confirmation');
        
        
                togglePassword.addEventListener('click', function(e) {
                    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                    password.setAttribute('type', type);
                    this.innerText = type === 'password' ? 'visibility_off' : 'visibility';
                });
        
                togglePasswordConfirmation.addEventListener('click', function(e) {
                    const type = passwordConfirmation.getAttribute('type') === 'password' ? 'text' : 'password';
                    passwordConfirmation.setAttribute('type', type);
                    this.innerText = type === 'password' ? 'visibility_off' : 'visibility';
                });
        
                // Stepper functionality
                const steps = document.querySelectorAll('.step');
                const stepIndicators = document.querySelectorAll('.step-indicator');
                let currentStep = 1;
        
                function showStep(stepNumber) {
                    steps.forEach(step => step.classList.remove('active'));
                    stepIndicators.forEach(indicator => indicator.classList.remove('active'));
        
                    document.querySelector(`.step[data-step="${stepNumber}"]`).classList.add('active'); // Corrected querySelector
                    stepIndicators[stepNumber - 1].classList.add('active');
                }
        
                stepIndicators.forEach(indicator => {
                    indicator.addEventListener('click', function(event) {
                        event.preventDefault(); // Prevent default link behavior
        
                        const stepToGo = parseInt(this.dataset.step);
        
                        // Validate the current step before moving to the next
                        let canMove = true;
                        for (let i = 1; i < stepToGo; i++) {  // Validate steps leading up to the target
                           if (!validateStep(i)) {
                               canMove = false;
                               break; // Stop validating if any step fails
                           }
                        }
        
                        if (!canMove) {
                            return;  // Stop if any previous step is invalid
                        }
        
        
                        if (!validateStep(currentStep)) {
                            return; // Stop if validation fails
                        }
        
                        // Validar si las contraseñas coinciden antes de cambiar de paso
                        if (currentStep === 5) {
                            const passwordValue = document.querySelector('input[name="password"]').value;
                            const passwordConfirmationValue = document.querySelector('input[name="password_confirmation"]').value;
        
                            if (passwordValue !== passwordConfirmationValue) {
                                showSweetAlert('Error', 'Las contraseñas no coinciden. Por favor, inténtelo de nuevo.', 'error');
                                return;
                            }
                        }
        
                        currentStep = stepToGo;
                        showStep(stepToGo);
                    });
                });
        
                // Intercept form submission to perform final validation
                document.getElementById('registerForm').addEventListener('submit', function(event) {
                    event.preventDefault(); // Prevent default form submission
        
                    // Validate all steps before submission
                    for (let i = 1; i <= steps.length; i++) {
                        if (!validateStep(i)) {
                            currentStep = i;
                            showStep(i); // Show the invalid step
                            return;  // Stop submission if any step fails
                        }
                    }
        
                    const passwordValue = document.querySelector('input[name="password"]').value;
                    const passwordConfirmationValue = document.querySelector('input[name="password_confirmation"]').value;
        
                    if (passwordValue !== passwordConfirmationValue) {
                        showSweetAlert('Error', 'Las contraseñas no coinciden. Por favor, inténtelo de nuevo.', 'error');
                        return;
                    }
        
        
                    // Show a SweetAlert confirmation before submitting
                    Swal.fire({
                        title: '¿Estás seguro de que deseas registrarte?',
                        text: 'Por favor, revisa la información antes de confirmar.',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Sí, registrar',
                        cancelButtonText: 'Cancelar',
                        reverseButtons: true,
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Proceed with form submission
                            this.submit();
                        }
                    });
                });
    
                let mapContainer = document.getElementById('mapContainer');
                let map;
    
                function initializeMap() {
                    if (!map) { // Evita inicializarlo múltiples veces
                        map = L.map('mapContainer').setView([18.9261, -99.23075], 13);
    
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        }).addTo(map);
    
                        let marker = L.marker([18.9261, -99.23075], { draggable: true }).addTo(map);
    
                        marker.on('dragend', function (event) {
                            let markerLatLng = marker.getLatLng();
                            document.getElementById('latitude').value = markerLatLng.lat;
                            document.getElementById('longitude').value = markerLatLng.lng;
                        });
    
                        // Set initial values
                        document.getElementById('latitude').value = 18.9261;
                        document.getElementById('longitude').value = -99.23075;
                    }
    
                    setTimeout(() => {
                        map.invalidateSize(); // Corrige el problema de visualización
                    }, 300);
                }
    
                // Detectar cuando el paso 4 se vuelve visible
                const step4 = document.querySelector('.step[data-step="4"]');
    
                const observer = new MutationObserver(() => {
                    if (step4.style.display !== 'none' && step4.offsetParent !== null) {
                        initializeMap();
                    }
                });
    
                observer.observe(step4, { attributes: true, childList: true, subtree: true });
    
                // Si el paso ya es visible al inicio, inicializar el mapa
                if (step4.style.display !== 'none' && step4.offsetParent !== null) {
                    initializeMap();
                }
            }


        });
    </script>
</body>
</html>