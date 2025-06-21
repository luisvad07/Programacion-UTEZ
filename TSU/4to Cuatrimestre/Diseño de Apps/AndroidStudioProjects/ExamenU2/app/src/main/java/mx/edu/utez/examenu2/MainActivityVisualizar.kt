package mx.edu.utez.examenu2

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.edu.utez.examenu2.databinding.ActivityMainVisualizarBinding
import org.json.JSONArray

class MainActivityVisualizar : AppCompatActivity() {
    lateinit var queue: RequestQueue
    lateinit var binding: ActivityMainVisualizarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVisualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this@MainActivityVisualizar)

        val url = "https://jsonplaceholder.typicode.com/albums"
        val metodo = Request.Method.GET
        val listener = Response.Listener<JSONArray> { response ->
            Log.e("RESPONSE", response.toString())

            var datos = mutableListOf<String>()
            for (i in 0 until response.length()) {
                datos.add(response.getJSONObject(i).getString("id") + "\n" +
                        response.getJSONObject(i).getString("title")
                )
            }

            val adaptador = ArrayAdapter(this@MainActivityVisualizar,
                R.layout.simple_list_item_1,
                datos
            )
            binding.lvLista.adapter = adaptador
        }
        val error = Response.ErrorListener { error ->
            Log.e("ERROR", error.message.toString())
        }

        val peticion = JsonArrayRequest(metodo,url,null,listener,error)
        queue.add(peticion)
    }
}