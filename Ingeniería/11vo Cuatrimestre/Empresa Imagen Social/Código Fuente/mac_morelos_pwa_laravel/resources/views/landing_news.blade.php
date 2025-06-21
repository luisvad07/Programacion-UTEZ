<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Noticias - SIMAC Morelos</title>

    {{-- Incluye el CSS y JS compilado por Vite --}}
    @vite(['resources/css/app.css', 'resources/js/app.js'])

    {{-- Favicon (ajusta la ruta si es necesario) --}}
    <link rel="icon" type="image/png" href="{{ asset('assets/maiz.png') }}?v=1">

    {{-- Google Material Icons --}}
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    {{-- Swiper.js CSS --}}
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

    {{-- Estilos adicionales --}}
    <style>
        /* Ensure body takes full viewport height and uses flex column */
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* Changed from height: 100% */
            padding-top: 4rem; /* Equivalent to h-16 for the fixed navbar */
            margin: 0; /* Reset default margin */
        }

        /* Make the main content area grow to fill available space */
        main {
            flex-grow: 1;
        }

        /* Footer base styles (Tailwind classes will override if needed) */
        .footer {
            background-color: #333; /* Example background */
            color: white;
            padding: 1.5rem; /* p-6 */
            text-align: center;
            /* flex-shrink: 0; */ /* Not strictly needed when main has flex-grow */
            /* Tailwind classes below will handle layout */
        }

        .logo-estado, .logo-pie {
            height: 4rem; /* h-16 */
            object-fit: contain;
            margin-bottom: 0.5rem; /* Add some spacing below logos on small screens */
        }

        @media (min-width: 768px) { /* md breakpoint */
            .logo-estado, .logo-pie {
                 margin-bottom: 0; /* Reset margin on larger screens */
            }
        }

        /* Swiper adjustments */
        .news-carousel .swiper-slide {
            height: auto; /* Let slide height be determined by content */
        }
        .news-carousel .news-card {
            height: 100%; /* Make card fill the slide height */
            display: flex;
            flex-direction: column;
        }
        .news-carousel .news-card-content {
            flex-grow: 1; /* Allow content area to expand */
        }
        .news-carousel .swiper-pagination {
            position: static; /* Reset position */
            margin-top: 1rem; /* Add space above pagination */
        }
         /* Style for the 'No News' message */
         .no-news-message {
            text-align: center;
            padding: 2.5rem 1rem; /* py-10 px-4 */
            color: #4a5568; /* text-gray-700 */
            font-size: 1.125rem; /* text-lg */
            border: 1px dashed #cbd5e0; /* border border-dashed border-gray-300 */
            border-radius: 0.375rem; /* rounded-md */
            background-color: #f7fafc; /* bg-gray-50 */
        }
    </style>
</head>
<body class="bg-gray-100 font-sans">

    {{-- ========================== Navbar Fijo ========================== --}}
    <nav class="bg-white shadow-md fixed top-0 left-0 right-0 z-50 h-16">
        <div class="container mx-auto px-4 h-full flex items-center justify-between">
            {{-- Logo/Imagen Navbar --}}
            <div>
                <a href="{{ url('/') }}"> {{-- Link a la página principal --}}
                    <img src="{{ asset('assets/maiz.png') }}" alt="Logo SIMAC" class="h-10 w-auto">
                </a>
            </div>

            {{-- Botón de Inicio de Sesión --}}
            <div>
                <a href="{{ route('login') }}"
                   class="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-md shadow-sm transition duration-150 ease-in-out flex items-center text-sm">
                    <i class="material-icons mr-1 text-base">login</i>
                    Iniciar Sesión
                </a>
            </div>
        </div>
    </nav>

    {{-- ======================= Contenido Principal ======================= --}}
    {{-- flex-grow ensures this section takes up available space, pushing footer down --}}
    <main class="container mx-auto px-4 py-8 flex-grow">

        {{-- Sección de Noticias --}}
        <section class="mb-12">
            <h2 class="text-2xl md:text-3xl font-bold text-gray-800 mb-6 text-center">Noticias Recientes</h2>

            {{-- Filtrar noticias publicadas ANTES de mostrarlas --}}
            @php
                $publishedNews = [];
                if (isset($newsData['data']) && is_array($newsData['data'])) {
                    $publishedNews = array_filter($newsData['data'], function($item) {
                        return isset($item['statusNews']) && $item['statusNews'] === 'PUBLICADO';
                    });
                }
            @endphp

            {{-- Comprobar si hay noticias publicadas para mostrar --}}
            @if (!empty($publishedNews))

                {{-- Contenedor de Swiper SOLO si hay noticias --}}
                <div class="swiper news-carousel relative">
                    <div class="swiper-wrapper">
                        {{-- Iterar sobre las noticias PUBLICADAS --}}
                        @foreach ($publishedNews as $newsItem)
                            <div class="swiper-slide p-2"> {{-- Padding around slide --}}
                                {{-- Tarjeta de Noticia --}}
                                <div class="bg-white rounded-lg shadow-lg overflow-hidden flex flex-col news-card">
                                    {{-- Imagen --}}
                                    @if(isset($newsItem['imageNews']) && !empty($newsItem['imageNews']))
                                        <img src="{{ 'http://localhost:8081/news_images/' . $newsItem['imageNews'] }}"
                                             alt="{{ $newsItem['title'] ?? 'Imagen de noticia' }}"
                                             class="w-full h-48 object-cover">
                                    @else
                                        <div class="w-full h-48 bg-gray-300 flex items-center justify-center">
                                            <span class="text-gray-500">Sin imagen</span>
                                        </div>
                                    @endif

                                    {{-- Contenido --}}
                                    <div class="p-4 flex flex-col flex-grow news-card-content">
                                        <h3 class="text-lg font-semibold text-gray-900 mb-2">{{ $newsItem['title'] ?? 'Sin título' }}</h3>
                                        <p class="text-gray-700 text-sm mb-3 flex-grow">
                                            {{ Str::limit($newsItem['content'] ?? 'Sin contenido.', 120) }}
                                        </p>
                                        <div class="mt-auto pt-3 border-t border-gray-200 text-xs text-gray-500 space-y-1">
                                            <div>
                                                @if(isset($newsItem['categories'][0]['name']))
                                                <span class="inline-block bg-blue-100 text-blue-800 rounded-full px-2 py-0.5 mr-2">
                                                    {{ $newsItem['categories'][0]['name'] }}
                                                </span>
                                                @endif
                                                @if(isset($newsItem['publishDate']))
                                                    <span>
                                                        Publicado:
                                                        @php
                                                            try {
                                                                echo \Carbon\Carbon::parse($newsItem['publishDate'])->locale('es')->tz(config('app.timezone', 'UTC'))->isoFormat('D MMM, YYYY');
                                                            } catch (\Exception $e) {
                                                                echo 'Fecha inválida';
                                                            }
                                                        @endphp
                                                    </span>
                                                @endif
                                            </div>
                                             @if(isset($newsItem['address']['city']) && isset($newsItem['address']['state']))
                                                <div class="flex items-center">
                                                    <i class="material-icons text-sm mr-1">location_on</i>
                                                    <span>{{ $newsItem['address']['city'] }}, {{ $newsItem['address']['state'] }}</span>
                                                </div>
                                            @endif
                                        </div>
                                    </div>
                                </div>
                            </div>
                        @endforeach
                    </div>

                    {{-- Navegación Swiper --}}
                    <div class="swiper-button-prev text-blue-600 hover:text-blue-800 transition-colors after:text-2xl after:font-extrabold"></div>
                    <div class="swiper-button-next text-blue-600 hover:text-blue-800 transition-colors after:text-2xl after:font-extrabold"></div>

                    {{-- Paginación Swiper --}}
                    <div class="swiper-pagination"></div> {{-- Se movió al final, y se estiló en CSS --}}
                </div>

            @else
                {{-- Mensaje si NO hay noticias publicadas --}}
                <div class="no-news-message">
                    <i class="material-icons text-4xl mb-2 text-gray-400">newspaper</i> <br>
                    No hay noticias publicadas para mostrar en este momento.
                </div>
            @endif

        </section>

        {{-- Puedes añadir más secciones aquí si lo necesitas --}}

    </main>

    {{-- ========================= Pie de Página ========================= --}}
    {{-- Se mantiene al fondo gracias a flex-grow en main y flex en body --}}
    <footer class="footer bg-gray-800 text-white p-6 flex flex-col md:flex-row items-center justify-around gap-4">
        <img src="{{ asset('assets/escudo_morelos.png') }}" alt="Logo ESCUDO Morelos" class="logo-estado">
        <img src="{{ asset('assets/escudo_largo.png') }}" alt="Logo Gobierno de Morelos" class="logo-pie">
        <p class="text-sm text-center md:text-left">© {{ date('Y') }} SIMAC MORELOS. Todos los derechos reservados.</p>
    </footer>

    {{-- Swiper.js JS --}}
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

    {{-- Inicialización de Swiper (SOLO si el contenedor existe) --}}
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Selecciona el contenedor del carrusel
            const carouselElement = document.querySelector('.news-carousel');

            // Inicializa Swiper solo si el contenedor existe (es decir, si hay noticias)
            if (carouselElement) {
                const swiper = new Swiper(carouselElement, {
                    loop: {{ !empty($publishedNews) && count($publishedNews) > 2 ? 'true' : 'false' }}, // Loop solo si hay suficientes slides
                    slidesPerView: 1,
                    spaceBetween: 15,
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                    breakpoints: {
                        640: {
                            slidesPerView: 2,
                            spaceBetween: 20
                        },
                        1024: {
                            slidesPerView: 3,
                            spaceBetween: 30
                        }
                    }
                });
            }
        });
    </script>

</body>
</html>