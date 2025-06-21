package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityResumenBinding

class Resumen : AppCompatActivity() {
    lateinit var binding : ActivityResumenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_memoria)
        setContentView(binding.root)

        var memoria = intent.getStringExtra("memoria")
        var procesador = intent.getStringExtra("procesador")
        var tarjeta_v = intent.getStringExtra("tarjeta_v")
        var monitor = intent.getStringExtra("monitor")

        binding.lbResult1.text = "Memoria: " + memoria
        binding.lbResult2.text = "Procesador: " + procesador
        binding.lbResult3.text = "Tarjeta de Video: " + tarjeta_v
        binding.lbResult4.text = "Monitor: " + monitor

        binding.btnReiniciar.setOnClickListener{
            val intent = Intent(this@Resumen, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK //Limpia el comportamiento de una pantalla como pila y lo reinicia automaticamente
            startActivity(intent)
        }
    }
}