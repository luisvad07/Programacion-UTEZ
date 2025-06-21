package mx.edu.utez.ejerciciocomputo4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciocomputo4a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.btnAcceder.setOnClickListener{
            val intent = Intent(this@MainActivity, Memoria::class.java)
            startActivity(intent)
        }

    }
}