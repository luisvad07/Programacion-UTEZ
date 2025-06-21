package mx.edu.utez.firebase4a20213tn002

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.edu.utez.firebase4a20213tn002.model.User

class MainActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference

        //Inserción
        val user = User("Luis Valladolid", "luis@gmail.com")
        database.child("users").child("1").setValue(user)

        //Actualización
        val user2 = mapOf(
            "username" to "Luis Strasbourg"
        )
        database.child("users").child("1").updateChildren(user2)

        //Eliminación
        //database.child("users").child("1").removeValue()

        //Autenticación
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword("luis@gmail.com","123456").addOnCompleteListener (this) { task ->
            if (task.isSuccessful){
                Toast.makeText(this@MainActivity, "Inicio de Sesión correcto", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Nombre de Usuario o Contraseña incorrectos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}