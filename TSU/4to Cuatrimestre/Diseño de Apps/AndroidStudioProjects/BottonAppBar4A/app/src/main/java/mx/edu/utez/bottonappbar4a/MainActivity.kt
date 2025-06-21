package mx.edu.utez.bottonappbar4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import mx.edu.utez.bottonappbar4a.adapter.CustomAdapter
import mx.edu.utez.bottonappbar4a.databinding.ActivityMainBinding
import mx.edu.utez.bottonappbar4a.model.Elemento

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datos = listOf(
            Elemento(R.drawable.amongus, "Among Us"),
            Elemento(R.drawable.siuuu, "Patricio"),
            Elemento(R.drawable.abel, "The Weeknd"),
            Elemento(R.drawable.barca, "Barcelona"),
        )

        val adaptador = CustomAdapter(this@MainActivity, R.layout.layout_elemento, datos)

        binding.rvLista.adapter = adaptador
        binding.rvLista.layoutManager =
            GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)

        binding.bottomBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.mnuPlay -> {
                    Toast.makeText(this@MainActivity,
                    "Presionaste Play!",
                    Toast.LENGTH_SHORT).show()
                }
                R.id.mnuPause -> {
                    Toast.makeText(this@MainActivity,
                        "Presionaste Pause!",
                        Toast.LENGTH_SHORT).show()
                }
                R.id.mnuForward -> {
                    Toast.makeText(this@MainActivity,
                        "Presionaste Velocidad!",
                        Toast.LENGTH_SHORT).show()
                }

            }
            true
        }
        binding.btnCall.setOnClickListener {
            Toast.makeText(this@MainActivity,
                "Llamando.....",
                Toast.LENGTH_SHORT).show()
        }
    }
}