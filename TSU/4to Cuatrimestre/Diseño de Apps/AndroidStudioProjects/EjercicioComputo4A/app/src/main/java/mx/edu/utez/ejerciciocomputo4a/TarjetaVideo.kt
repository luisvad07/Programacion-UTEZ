package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityTarjetaVideoBinding

class TarjetaVideo : AppCompatActivity() {
    lateinit var binding : ActivityTarjetaVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarjetaVideoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_memoria)
        setContentView(binding.root)

        var memoria = intent.getStringExtra("memoria")
        var procesador = intent.getStringExtra("procesador")

        binding.btnSiguiente3.setOnClickListener{
            val seleccion = binding.rgTarjetaVideo.checkedRadioButtonId
            var tarjeta_v = ""
            when(seleccion){
                R.id.rbTar1 -> {tarjeta_v="NVIDIA GTX"}
                R.id.rbTar2 -> {tarjeta_v="RTX"}
                R.id.rbTar3 -> {tarjeta_v="QUADRO"}
                else -> {tarjeta_v="Sin seleccionar"}
            }
            val intent = Intent(this@TarjetaVideo, Monitor::class.java)
            intent.putExtra("memoria", memoria)
            intent.putExtra("procesador", procesador)
            intent.putExtra("tarjeta_v", tarjeta_v)
            startActivity(intent)
        }
    }
}