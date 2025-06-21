package mx.edu.utez.appbar4a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.edu.utez.appbar4a.databinding.ActivityTroisBinding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding : ActivityTroisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTroisBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_trois)
        setContentView(binding.root)

        binding.btn3.setOnClickListener {
            val intent = Intent(this@MainActivity3, MainActivity4::class.java)
            startActivity(intent)
        }
    }
}