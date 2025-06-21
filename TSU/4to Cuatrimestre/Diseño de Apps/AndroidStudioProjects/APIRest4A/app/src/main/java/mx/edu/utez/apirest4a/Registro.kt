package mx.edu.utez.apirest4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import mx.edu.utez.apirest4a.databinding.ActivityRegistroBinding
import org.json.JSONObject

class Registro : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    lateinit var queue : RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this@Registro)

        binding.btnGuardar.setOnClickListener{
            var name = binding.edtName.text.toString()
            var username = binding.edtUsername.text.toString()
            var email = binding.edtEmail.text.toString()

            var metodo = Request.Method.POST
            var url = "https://jsonplaceholder.typicode.com/users"

            var body = JSONObject()
            body.put("name", name)
            body.put("username", username)
            body.put("email", email)

            val listener = Response.Listener<JSONObject> { response ->
                Log.e("RESPONSE", response.toString())
            }
            val error = Response.ErrorListener { error ->
                Log.e("ERROR", error.message.toString())
            }

            val peticion = JsonObjectRequest(metodo, url, body, listener, error)
            queue.add(peticion)
        }
    }
}