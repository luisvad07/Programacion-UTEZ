package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityProcesadorBinding

class Procesador : AppCompatActivity() {
    lateinit var binding : ActivityProcesadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcesadorBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_memoria)
        setContentView(binding.root)

        var memoria = intent.getStringExtra("memoria")

        binding.btnSiguiente2.setOnClickListener{
            val seleccion = binding.rgProcesador.checkedRadioButtonId
            var procesador = ""
            when(seleccion){
                R.id.rbProc1 -> {procesador="Intel Pentium-Celeron / AMD Ryzen 3 / APU"}
                R.id.rbProc2 -> {procesador="Intel Core i3 / AMD Ryzen 5 de cuatro núcleos"}
                R.id.rbProc3 -> {procesador="Intel Core i5 / Intel Core i7 y AMD Ryzen 7"}
                else -> {procesador="Sin seleccionar"}
            }
            val intent = Intent(this@Procesador, TarjetaVideo::class.java)
            intent.putExtra("memoria", memoria)
            intent.putExtra("procesador", procesador)
            startActivity(intent)
        }

    }
}