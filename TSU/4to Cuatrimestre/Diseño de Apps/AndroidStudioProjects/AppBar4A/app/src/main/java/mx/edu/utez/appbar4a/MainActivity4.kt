package mx.edu.utez.appbar4a

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.edu.utez.appbar4a.databinding.ActivityQuatreBinding

class MainActivity4 : AppCompatActivity() {
    lateinit var binding : ActivityQuatreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuatreBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_quatre)
        setContentView(binding.root)

        binding.btn4.setOnClickListener {
            val intent = Intent(this@MainActivity4, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK //Limpia el comportamiento de una pantalla como pila y lo reinicia automaticamente
            startActivity(intent)
        }
    }
}