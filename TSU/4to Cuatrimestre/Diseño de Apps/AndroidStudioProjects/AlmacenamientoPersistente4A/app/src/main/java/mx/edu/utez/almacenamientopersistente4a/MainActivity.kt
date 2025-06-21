package mx.edu.utez.almacenamientopersistente4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mx.edu.utez.almacenamientopersistente4a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val sp = getSharedPreferences("Sesion", MODE_PRIVATE)
        val usuario = sp.getString("usuario","@")
        if (usuario != "@") {
            val intent = Intent(this@MainActivity, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        binding.btnSesion.setOnClickListener {
            val usuario = binding.edtUsuario.text.toString()
            val password = binding.edtPass.text.toString()

            if (usuario == "Luis Valladolid" && password == "Valladolid07") {

                val editor = sp.edit()
                editor.putString("usuario", usuario)
                editor.putString("password", password)
                editor.commit()
                val intent = Intent(this@MainActivity, Home::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Sesion Incorrecta!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}