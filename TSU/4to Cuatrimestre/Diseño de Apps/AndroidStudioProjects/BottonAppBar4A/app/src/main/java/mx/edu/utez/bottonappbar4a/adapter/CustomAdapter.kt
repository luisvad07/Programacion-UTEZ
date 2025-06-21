package mx.edu.utez.bottonappbar4a.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.edu.utez.bottonappbar4a.R
import mx.edu.utez.bottonappbar4a.model.Elemento

class CustomAdapter(val context: Context, val layout: Int, val datos:List<Elemento>):
    RecyclerView.Adapter<CustomAdapter.ElementoViewHolder>() {

    class ElementoViewHolder(inflater: LayoutInflater, parent: ViewGroup, layout: Int) //Crea las instancias de todo
        : RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)){
        //Inicializar lo de la vista
            val imgImage = itemView.findViewById<ImageView>(R.id.imgImage)
            val txtTexto = itemView.findViewById<TextView>(R.id.txtTexto)

            fun bindData(elemento: Elemento){
                imgImage.setImageResource(elemento.imagen)
                txtTexto.text = elemento.texto
            }
    }


    //Pinta los elementos de la lista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementoViewHolder {
        //Crea una instancia del viewHolder y se lo asigna al RecyclerView (al inicio, solo tiene elementos vacios)
        val inflater = LayoutInflater.from(parent.context)
        return ElementoViewHolder(inflater,parent,layout)
    }

    override fun onBindViewHolder(holder: ElementoViewHolder, position: Int) {
        //Llena las instancias del viewHolder y ya se ven los datos
        val elemento = datos[position]
        holder.bindData(elemento)

        val animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
        holder.itemView.startAnimation(animation)

    }

    override fun getItemCount(): Int { //Obtiene la cantidad de elementos
        return datos.size
    }
}