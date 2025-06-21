@vite(['resources/css/app.css', 'resources/js/app.js'])

<nav class="fixed top-0 left-0 z-50 w-full bg-white py-6 shadow-md lg:py-8">
    <div class="container flex items-center justify-between px-4">

      <!-- Logo -->
      <div class="flex items-center space-x-3">
          <button id="toggleSidebar" class="p-2 text-gray-700 hover:text-gray-900 focus:outline-none">
            <span class="material-icons text-[--color-base]">menu</span>
          </button>
          <img id="logo-image" alt="Logo"  width="150" height="100" class="ml-3" aria-label="Logo">
      </div>

      <!-- Espacio central (puedes agregar texto si es necesario) -->
      <div class="flex flex-grow justify-center">
        <h1 class="text-center text-[--color-base]"><strong>SIMAC MORELOS</strong></h1>
      </div>

      <!-- Input de búsqueda -->
      <div class="flex items-center space-x-2 flex-grow justify-center">
        <div class="relative w-full sm:w-96">
          <input type="search" class="w-full rounded-full border border-gray-300 px-4 py-2 pl-10 text-sm text-gray-500 focus:ring-2 focus:ring-blue-200 focus:outline-none" placeholder="Buscar">
          <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
            <span class="material-icons text-gray-500">search</span>
          </div>
        </div>
      </div>

      @php
          // Obtener el token desde la sesión
          $token = session('auth_token');

          // Inicializar la variable de la imagen por defecto
          $profileImageUrl = asset('assets/default.png');

          if ($token) {
              try {
                  // Hacer la petición a la API para obtener el perfil del usuario
                  $client = new \GuzzleHttp\Client();
                  $response = $client->get('http://localhost:8081/mac-morelos-api/users/getProfile', [
                      'headers' => [
                          'Authorization' => 'Bearer ' . $token
                      ]
                  ]);

                  // Decodificar la respuesta JSON
                  $userData = json_decode($response->getBody()->getContents(), true);

                  //dd($userData);

                  // Verificar si la respuesta es válida y tiene la imagen de perfil
                  if (isset($userData['data']['imgProfile']) && !empty($userData['data']['imgProfile'])) {
                      $profileImageUrl = 'http://localhost:8081/mac-morelos-api/users' . $userData['data']['imgProfile'];
                      //dd($profileImageUrl);
                  }
              } catch (\Exception $e) {
                  // Si hay un error en la petición, se usa la imagen por defecto
                  \Log::error('Error obteniendo la imagen de perfil: ' . $e->getMessage());
              }
          }
      @endphp

      <!-- Foto de perfil -->
      <div class="flex items-center">
        <img src="{{ Str::contains($profileImageUrl, 'null') ? asset('assets/default.png') : $profileImageUrl }}" alt="Profile" class="rounded-full w-12 h-12 object-cover border-4 border-black">
      </div>
    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
@if(session('api_error'))
    <script>
        Swal.fire({
            title: 'Error',
            text: '{{ session('api_error') }}',
            icon: 'error',
            willClose: () => {
                window.location.href = '{{ route('logout') }}'; // Redirige a la ruta de logout
            }
        });
    </script>
@endif

<script>
document.addEventListener('DOMContentLoaded', () => {
    const logoImg = document.getElementById('logo-image');
    const logoUrl = getComputedStyle(document.documentElement).getPropertyValue('--logo-estado');

    if (logoUrl) {
        const absoluteLogoUrl = new URL(logoUrl.trim().slice(4, -1).replace(/"/g, ''), window.location.origin);
        logoImg.src = absoluteLogoUrl.href;
    } else {
        //logoImg.src = '/assets/simac.png'; // Ruta de respaldo
        console.error("La variable CSS '--logo-estado' no está definida o no es válida.");
    }
});
</script>