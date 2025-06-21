package mx.edu.utez.mensajes4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import mx.edu.utez.mensajes4a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //Lee un archivo XML y genera una instancia en el Binding
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        //val txttexto = findViewById<TextView>(R.id.txtTexto);
        //val btntoast = findViewById<Button>(R.id.btnToast);
        //val btnalertdialog = findViewById<Button>(R.id.btnAlertdialog);
        //val btnlog = findViewById<Button>(R.id.btnLog);
        //val btnsnackbar = findViewById<Button>(R.id.btnSnackbar);
        //viewBinding <- Crear una clase por cada layout que contenga los controles como atributos de clase
        binding.btnToast.setOnClickListener {
            Toast.makeText(this@MainActivity,
                binding.txtTexto.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnAlertdialog.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Este es el titulo")
            builder.setMessage(binding.txtTexto.text.toString())
            builder.setPositiveButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setNegativeButton("Si") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setNeutralButton("Mas tarde") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        binding.btnLog.setOnClickListener {
            Log.e("ERROR",binding.txtTexto.text.toString())
            Log.e("INFO",binding.txtTexto.text.toString())
            Log.e("WARNING",binding.txtTexto.text.toString())
        }

        binding.btnSnackbar.setOnClickListener {
            val snack = Snackbar.make(
                findViewById(R.id.constraint), // <- No se usa contexto
                binding.txtTexto.text.toString(),
                Snackbar.LENGTH_SHORT
            )
            snack.setAction("Boton") {
                println("Hice algo!!!!")
            }
            snack.show()
        }
    }
}