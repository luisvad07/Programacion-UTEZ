package mx.edu.utez.ejerciciowidgets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciowidgets.databinding.ActivityMain4Binding

class MainActivity4 : AppCompatActivity() {
    lateinit var binding : ActivityMain4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.btnL3.setOnClickListener {
            val intent = Intent(this@MainActivity4, Activity4Chiquito::class.java)
            startActivity(intent)
        }
    }


}