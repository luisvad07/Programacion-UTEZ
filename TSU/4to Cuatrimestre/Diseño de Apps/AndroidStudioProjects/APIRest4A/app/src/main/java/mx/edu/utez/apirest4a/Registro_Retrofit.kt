package mx.edu.utez.apirest4a

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import mx.edu.utez.apirest4a.`interface`.IService
import mx.edu.utez.apirest4a.databinding.ActivityRegistroRetrofitBinding
import mx.edu.utez.apirest4a.model.Address
import mx.edu.utez.apirest4a.model.Company
import mx.edu.utez.apirest4a.model.Geo
import mx.edu.utez.apirest4a.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Registro_Retrofit : AppCompatActivity() {
    lateinit var binding: ActivityRegistroRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(IService::class.java)

        binding.btnRegistrar.setOnClickListener{
            val name = binding.edtNombre.text.toString()
            val username = binding.edtUsuario.text.toString()
            val email = binding.edtCorreo.text.toString()

            val usuario = User(0,name, username, email, Address("Av. Universidad ","1","Emiliano Zapata","62780",
                Geo(-19.0, 44.0)),"7773681165","utez.edu.mx", Company("UTEZ","Universidad","bs"))

            service.addUser(usuario).enqueue(object: Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    //println(response.toString())
                    if (response.code() == 201 && response.body()!!.id ==11) {
                        Toast.makeText(this@Registro_Retrofit, "Usuario Registrado!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                }
            })
        }
    }
}