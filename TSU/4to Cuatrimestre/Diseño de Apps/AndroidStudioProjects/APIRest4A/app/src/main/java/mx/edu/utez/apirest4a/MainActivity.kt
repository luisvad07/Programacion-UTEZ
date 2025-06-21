package mx.edu.utez.apirest4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.edu.utez.apirest4a.databinding.ActivityMainBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var queue: RequestQueue
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this@MainActivity)

        val url = "https://jsonplaceholder.typicode.com/users"
        val metodo = Request.Method.GET
        val listener = Response.Listener<JSONArray> { response ->
            Log.e("RESPONSE", response.toString())

            var datos = mutableListOf<String>()
            for (i in 0 until response.length()) {
                datos.add(response.getJSONObject(i).getString("name") + "\n" +
                    response.getJSONObject(i).getString("email")
                )
            }

            val adaptador = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_list_item_1,
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnAgregar -> {
                val intent = Intent(this@MainActivity,Registro::class.java)
                startActivity(intent)
            }
            R.id.btnAcceder -> {
                val intent = Intent(this@MainActivity,MainRetrofit::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}