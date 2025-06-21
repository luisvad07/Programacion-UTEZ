package mx.edu.utez.examenu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import mx.edu.utez.examenu2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datos =  listOf("Planta","Agua","Acero", "Hada")

        val adaptador = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, datos)
        binding.spPokemon.adapter = adaptador

        val sp = getSharedPreferences("Pokemon", MODE_PRIVATE)

        binding.bottomBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.btnVer -> {
                    val nomPokemon = binding.edtNomPokemon.text.toString()
                    val seleccion = binding.spPokemon.selectedItem.toString()
                    val tamaño = binding.edtTam.text.toString()
                    val editor = sp.edit()

                    editor.putString("nomPokemon", nomPokemon)
                    editor.putString("seleccion", seleccion)
                    editor.putString("tamaño", tamaño)
                    editor.commit()

                    intent.putExtra("nomPokemon", nomPokemon)
                    intent.putExtra("seleccion", seleccion)
                    intent.putExtra("tamaño", tamaño)

                    val intent = Intent(this@MainActivity, MainActivityVer::class.java)
                    startActivity(intent)
                }
                R.id.btnVisualizar -> {
                    val intent = Intent(this@MainActivity, MainActivityVisualizar::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        binding.btnFoto.setOnClickListener {
            Toast.makeText(this@MainActivity, "Ya te tome foto!", Toast.LENGTH_SHORT).show()
        }
    }
}