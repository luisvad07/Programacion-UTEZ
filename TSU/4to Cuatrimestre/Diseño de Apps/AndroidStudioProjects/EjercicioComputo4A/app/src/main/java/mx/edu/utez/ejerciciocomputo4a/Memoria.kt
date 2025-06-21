package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityMemoriaBinding

class Memoria : AppCompatActivity() {
    lateinit var binding : ActivityMemoriaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoriaBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_memoria)
        setContentView(binding.root)


        binding.btnSiguiente.setOnClickListener{
            val seleccion = binding.rgMemoria.checkedRadioButtonId
            var memoria = ""
            when(seleccion){
                R.id.rb4GB -> {memoria="4GB"}
                R.id.rb8GB -> {memoria="8GB"}
                R.id.rb16GB -> {memoria="16GB"}
                else -> {memoria="Sin seleccionar"}
            }
            val intent = Intent(this@Memoria, Procesador::class.java)
            intent.putExtra("memoria", memoria)
            startActivity(intent)
        }

    }
}