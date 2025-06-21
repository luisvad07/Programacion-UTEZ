package mx.edu.utez.apirest4a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import mx.edu.utez.apirest4a.`interface`.IService
import mx.edu.utez.apirest4a.databinding.ActivityMainRetrofitBinding
import mx.edu.utez.apirest4a.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRetrofit : AppCompatActivity() {
    lateinit var binding: ActivityMainRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(IService::class.java)

        service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                //println(response.toString())
                if (response.code() == 200) {
                    var elementos = response.body()

                    println("546")
                    var lista = ArrayList<String>()
                    for (elemento in elementos!!){
                        lista.add(elemento.username + "\n" + elemento.email)
                    }

                    val adaptador = ArrayAdapter(
                        this@MainRetrofit,
                        android.R.layout.simple_list_item_1,
                        lista
                    )

                    binding.lvLista2.adapter = adaptador
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainRetrofit, "Algo Paso!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnAdd -> {
                val intent = Intent(this@MainRetrofit,Registro_Retrofit::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}