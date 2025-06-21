package mx.edu.utez.almacenamientopersistente4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.almacenamientopersistente4a.databinding.ActivityHomeBinding
import mx.edu.utez.almacenamientopersistente4a.databinding.ActivityMainBinding

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        setContentView(binding.root)

        val sp = getSharedPreferences("Sesion", MODE_PRIVATE)

        binding.btnCerrar.setOnClickListener {
            val editor = sp.edit()
            editor.remove("usuario")
            editor.remove("password")
            editor.commit()

            val intent = Intent(this@Home, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}