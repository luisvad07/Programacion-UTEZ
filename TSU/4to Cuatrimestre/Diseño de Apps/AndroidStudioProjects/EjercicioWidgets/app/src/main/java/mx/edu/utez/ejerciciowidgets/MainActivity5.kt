package mx.edu.utez.ejerciciowidgets

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.ejerciciowidgets.databinding.ActivityMain5Binding

class MainActivity5 : AppCompatActivity() {
    lateinit var binding : ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoView2!!.setVideoURI(Uri.parse("https://giphy.com/embed/8CYD7zQOYECt2UolVp/video"))

        binding.videoView2!!.start()


        binding.btnReiniciar.setOnClickListener {
            val intent = Intent(this@MainActivity5, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK //Limpia el comportamiento de una pantalla como pila y lo reinicia automaticamente
            startActivity(intent)
        }
    }
}