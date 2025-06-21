package mx.edu.utez.examenu1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.edu.utez.examenu1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        //val btnjugar = findViewById<Button>(R.id.btnJugar);
        //val btnacercade = findViewById<Button>(R.id.btnAcercade);
        //val btnsalir = findViewById<Button>(R.id.btnsalir);

        binding.btnJugar.setOnClickListener {
            val intent = Intent(this@MainActivity, PantallaJugar::class.java)
            startActivity(intent)
        }

        binding.btnAcercade.setOnClickListener {
            val intent = Intent(this@MainActivity, PantallaAcercadelAutor::class.java)
            startActivity(intent)
        }

        binding.btnsalir.setOnClickListener {
            finish()
        }
    }
}