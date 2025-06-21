package mx.edu.utez.ejerciciowidgets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.edu.utez.ejerciciowidgets.databinding.ActivityActivity4ChiquitoBinding

class Activity4Chiquito : AppCompatActivity() {
    lateinit var binding : ActivityActivity4ChiquitoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivity4ChiquitoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url= "https://www.google.com.mx/"
        binding.wbWeb?.loadUrl(url)

        binding.btnL4.setOnClickListener {
            val intent = Intent(this@Activity4Chiquito, MainActivity5::class.java)
            startActivity(intent)
        }
    }
}