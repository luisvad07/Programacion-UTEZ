package com.imagen_social.mac_morelos_api.utils;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


@Service
public class DatabaseBackupService {

    private static final String DB_NAME = "smacmor_api";
    private static final String DB_USER = "luisvad";
    private static final String DB_PASSWORD = "luisvad";
    
    // Datos de GitHub
    private static final String GITHUB_TOKEN = "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"; // Usa un token de acceso personal
    private static final String REPO_OWNER = "luisvad07";
    private static final String REPO_NAME = "mac_morelos_respaldos_db";
    private static final String BRANCH = "main"; // O la rama que uses

    @Scheduled(cron = "0 0 * * * ?") // Cada hora
    public void backupDatabase() {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String backupFileName = "backup_" + timestamp + ".sql";

            // 1. Generar respaldo de PostgreSQL en formato de texto plano
            ProcessBuilder pb = new ProcessBuilder(
                    "pg_dump", "-U", DB_USER, "-d", DB_NAME, "-F", "p", "-f", backupFileName);
            pb.environment().put("PGPASSWORD", DB_PASSWORD);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

            // 2. Leer el archivo generado
            byte[] fileContent = Files.readAllBytes(Paths.get(backupFileName));
            String encodedContent = Base64.getEncoder().encodeToString(fileContent);

            // 3. Verificar si el archivo ya existe en GitHub
            String sha = getFileSha(backupFileName);

            // 4. Subir archivo a GitHub (crear o actualizar)
            uploadToGitHub(encodedContent, backupFileName, sha);

            // 5. Eliminar archivo local después de subirlo a GitHub
            Files.deleteIfExists(Paths.get(backupFileName));
            System.out.println("Archivo local eliminado: " + backupFileName);

            System.out.println("Respaldo subido a GitHub exitosamente: " + backupFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileSha(String fileName) {
        try {
            String apiUrl = "https://api.github.com/repos/" + REPO_OWNER + "/" + REPO_NAME + "/contents/backups/" + fileName;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "token " + GITHUB_TOKEN);
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                String responseBody = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONObject json = new JSONObject(responseBody);
                return json.getString("sha"); // Obtener el SHA del archivo existente
            }
        } catch (Exception e) {
            System.out.println("Archivo no existe en GitHub, se creará uno nuevo.");
        }
        return null; // Si el archivo no existe, devuelve null
    }

    private void uploadToGitHub(String encodedContent, String fileName, String sha) throws IOException {
        String apiUrl = "https://api.github.com/repos/" + REPO_OWNER + "/" + REPO_NAME + "/contents/backups/" + fileName;
        
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("message", "Backup: " + fileName);
        jsonPayload.put("content", encodedContent);
        jsonPayload.put("branch", BRANCH);

        if (sha != null) {
            jsonPayload.put("sha", sha); // Si el archivo existe, se actualiza en lugar de crearse
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "token " + GITHUB_TOKEN);
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonPayload.toString().getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Archivo subido correctamente a GitHub.");
        } else {
            Scanner scanner = new Scanner(connection.getErrorStream());
            String errorResponse = scanner.useDelimiter("\\A").next();
            scanner.close();
            System.err.println("Error al subir archivo: " + errorResponse);
        }
    }
}
