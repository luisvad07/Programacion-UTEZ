package mx.edu.utez.login4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_linear) // Ejecuta el esquema de la distribución de los elementos dentro de una aplicacion web

        //Declaracion de Campos por id como Visual Basic
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnIniciarsesion = findViewById<Button>(R.id.btnIniciarsesion)
        val btnForgot = findViewById<Button>(R.id.btnForgot)

        //Evento que hace al dar el boton clic
        btnIniciarsesion.setOnClickListener {
            /*edtUsuario.setText("Hola Mundo!")
            btnForgot.text = "Otro texto"
            println("Algo")*/

            Toast.makeText( //Es objeto de vista que se despliega como un elemento emergente en la interfaz del usuario
                this@MainActivity,
                "Bienvenido!",
                Toast.LENGTH_SHORT
            ).show()

            //Clase para abrir componentes nuevos: Intent <- 1. Origen, 2. Destino
            val intent = Intent(this@MainActivity,
            Home::class.java)
            startActivity(intent)
        }

    }
}