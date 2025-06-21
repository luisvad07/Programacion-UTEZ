package mx.edu.utez.listas4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //List datos = new ArrayList<String>();
        val datos =  listOf("Elemento 1","Elemento 2","Elemento 3")
        val spMenu = findViewById<Spinner>(R.id.spMenu)

        //1. Contexto, 2. Estilo (Diseño), 3. Datos
        val adaptador = ArrayAdapter(this@MainActivity,
            android.R.layout.simple_list_item_1,
            datos)
        spMenu.adapter = adaptador
    }
}