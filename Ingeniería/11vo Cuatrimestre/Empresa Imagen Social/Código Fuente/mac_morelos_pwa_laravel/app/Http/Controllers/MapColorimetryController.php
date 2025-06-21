<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

class MapColorimetryController extends Controller
{
    private $apiBaseUrl = 'http://localhost:8081/mac-morelos-api/map-colorimetries';

    private function getAuthHeaders()
    {
        // Asume que el token de autenticación se guarda en la sesión.
        // Ajusta esto si usas otro método de autenticación (ej: cookies, headers)
        return [
            'Authorization' => 'Bearer ' . session('auth_token'),
            'Content-Type' => 'application/json',
        ];
    }

    public function index()
    {
        $mapColorimetries = $this->getAllMapColorimetries();

        //dd($mapColorimetries);

        if ($mapColorimetries === null) {
            // Manejar el error, por ejemplo, redirigir con un mensaje de error
            return redirect()->route('dashboard.redirect')->with('error', 'No se pudieron cargar los datos de colorimetría.');
        }

        return view('layouts.mapa_morelos', compact('mapColorimetries'));
    }

    private function getAllMapColorimetries()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/getAll");

            //dd($response->status(), $response->body());

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener todas las colorimetrías: ' . $response->body());
                return null; // Indica un error
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener todas las colorimetrías: ' . $e->getMessage());
            return null; // Indica un error
        }
    }

    public function show($municipality)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/municipality/{$municipality}");

            if ($response->successful()) {
                return response()->json($response->json()['data']);
            } else {
                return response()->json(['error' => 'Municipio no encontrado o error en la API'], 404);
            }
        } catch (\Exception $e) {
            Log::error('Error al obtener la colorimetría del municipio: ' . $e->getMessage());
            return response()->json(['error' => 'Error al obtener datos del municipio'], 500);
        }
    }


    private function getMapColorimetryById($id)
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->get("{$this->apiBaseUrl}/{$id}");

            if ($response->successful()) {
                return $response->json()['data'];
            } else {
                Log::error('Error al obtener la colorimetría con ID ' . $id . ': ' . $response->body());
                return null;
            }
        } catch (\Exception $e) {
            Log::error('Error al obtener la colorimetría con ID ' . $id . ': ' . $e->getMessage());
            return null;
        }
    }

    // public function update(Request $request, $id)
    // {
    //     $validatedData = $request->validate([
    //         'municipality' => 'required|string',
    //         'color' => 'required|string', // Ajusta las validaciones según los campos de tu entidad MapColorimetry
    //     ]);

    //     try {
    //         $response = Http::withHeaders($this->getAuthHeaders())->put("{$this->apiBaseUrl}/{$id}", $validatedData);

    //         if ($response->successful()) {
    //             return redirect()->route('map-colorimetries.index')
    //                 ->with('success', 'Colorimetría actualizada exitosamente.');
    //         } else {
    //             Log::error('Error al actualizar la colorimetría: ' . $response->body());
    //             return redirect()->back()
    //                 ->with('error', 'Error al actualizar la colorimetría.')
    //                 ->withErrors($response->json()); // Devuelve los errores de validación de la API
    //         }
    //     } catch (\Exception $e) {
    //         Log::error('Error al actualizar la colorimetría: ' . $e->getMessage());
    //         return redirect()->back()
    //             ->with('error', 'Error al actualizar la colorimetría: ' . $e->getMessage());
    //     }
    // }

    public function loadInitial()
    {
        try {
            $response = Http::withHeaders($this->getAuthHeaders())->post("{$this->apiBaseUrl}/load-initial");

            if ($response->successful()) {
                return redirect()->route('map-colorimetries.index')
                    ->with('success', 'Datos iniciales cargados exitosamente.');
            } else {
                Log::error('Error al cargar los datos iniciales: ' . $response->body());
                return redirect()->back()
                    ->with('error', 'Error al cargar los datos iniciales.');
            }
        } catch (\Exception $e) {
            Log::error('Error al cargar los datos iniciales: ' . $e->getMessage());
            return redirect()->back()
                ->with('error', 'Error al cargar los datos iniciales: ' . $e->getMessage());
        }
    }
}