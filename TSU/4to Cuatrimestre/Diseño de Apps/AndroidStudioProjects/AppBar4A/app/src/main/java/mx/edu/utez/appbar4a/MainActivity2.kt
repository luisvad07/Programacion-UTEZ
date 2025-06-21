package mx.edu.utez.appbar4a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.edu.utez.appbar4a.databinding.ActivityDeuxBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityDeuxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeuxBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_deux)
        setContentView(binding.root)

        binding.btn2.setOnClickListener {
            val intent = Intent(this@MainActivity2, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}