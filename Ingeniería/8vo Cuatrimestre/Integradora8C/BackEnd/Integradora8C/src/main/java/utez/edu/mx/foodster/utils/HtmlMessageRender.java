package utez.edu.mx.foodster.utils;

import org.springframework.stereotype.Service;

@Service
public class HtmlMessageRender {
    private static final String HTML_TEMPLATE = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Recuperacion de contraseña</title>
        <style>
            body {
                font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
                font-size: 14px;
                line-height: 1.6;
                color: #555;
            }
            .container {
                max-width: 600px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 20px;
                background-color: #fff;
            }
            .logo {
                text-align: center;
                margin-bottom: 20px;
            }
            .logo img {
                max-width: 15%;
                height: auto;
            }
            .footer {
                margin-top: 20px;
                text-align: center;
                color: #888;
            }
            .boton{
                background-color: #179275;
                border: none;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 20px;
            }
        </style>
    </head>
    <body>
    <div class="container">
        <p>¡Hola! ##NOMBRE_USUARIO##</p>
        <p>Por favor, haz click en el siguiente enlace para cambiar tu contraseña:</p>
        <a class="boton" href="##URL_TOKEN##" style='color:white;'>Cambiar contraseña</a>
        <div class="footer">
            Este correo electrónico fue enviado automáticamente. Por favor, no
            respondas a este correo electrónico.
        </div>
    </div>
    </body>
    </html>
    """;

    public String renderRecover(String userName, String tokenUrl) {
        return HTML_TEMPLATE.replace("##NOMBRE_USUARIO##", userName).replace("##URL_TOKEN##", tokenUrl);
    }
}