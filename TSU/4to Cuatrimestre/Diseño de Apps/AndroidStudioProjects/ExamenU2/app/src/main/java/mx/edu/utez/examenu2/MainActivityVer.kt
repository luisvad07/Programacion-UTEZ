package mx.edu.utez.examenu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.examenu2.databinding.ActivityMainVerBinding

class MainActivityVer : AppCompatActivity() {
    lateinit var binding: ActivityMainVerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("Pokemon", MODE_PRIVATE)

        var nompokemon = intent.getStringExtra("nomPokemon")
        var seleccion = intent.getStringExtra("seleccion")
        var tamaño= intent.getStringExtra("tamaño")

        binding.lbResult1.text = "Nombre del Pokemon: " + nompokemon
        binding.lbResult2.text = "Tipo: " + seleccion
        binding.lbResult3.text = "Tamaño: " + tamaño

        binding.btnRegresar.setOnClickListener {
            val editor = sp.edit()
            editor.remove("nomPokemon")
            editor.remove("seleccion")
            editor.remove("tamaño")
            editor.commit()

            val intent = Intent(this@MainActivityVer, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}