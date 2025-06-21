package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityMonitorBinding

class Monitor : AppCompatActivity() {
    lateinit var binding : ActivityMonitorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitorBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_memoria)
        setContentView(binding.root)

        var memoria = intent.getStringExtra("memoria")
        var procesador = intent.getStringExtra("procesador")
        var tarjeta_v = intent.getStringExtra("tarjeta_v")

        binding.btnSiguiente4.setOnClickListener{
            val seleccion = binding.rgMonitor.checkedRadioButtonId
            var monitor = ""
            when(seleccion){
                R.id.rb19Pulgadas-> {monitor="19 Pulgadas"}
                R.id.rb21Pulgadas -> {monitor="21 Pulgadas"}
                R.id.rb24Pulgadas -> {monitor="24 Pulgadas"}
                else -> {monitor="Sin seleccionar"}
            }
            val intent = Intent(this@Monitor, Resumen::class.java)
            intent.putExtra("memoria", memoria)
            intent.putExtra("procesador", procesador)
            intent.putExtra("tarjeta_v", tarjeta_v)
            intent.putExtra("monitor", monitor)
            startActivity(intent)
        }
    }
}