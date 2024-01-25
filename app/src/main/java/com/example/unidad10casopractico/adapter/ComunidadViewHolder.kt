package com.example.unidad10casopractico.adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.unidad10casopractico.Comunidad
import com.example.unidad10casopractico.R
import com.example.unidad10casopractico.databinding.ItemBanderaBinding

class ComunidadViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val binding= ItemBanderaBinding.bind(view)

    private lateinit var comunidad: Comunidad


    fun render(item: Comunidad, OnClickListener: (Comunidad)->Unit){
        comunidad = item
        binding.tvnombre.text=item.nombre
        binding.ivbandera.setImageResource(item.imagen)

        itemView.setOnClickListener{
            OnClickListener(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        menu!!.setHeaderTitle(comunidad.nombre)
        menu.add(this.adapterPosition, R.id.context_delete,0,"Eliminar")
        menu.add(this.adapterPosition, R.id.context_edit,1, "Editar")
        menu.add(this.adapterPosition, R.id.context_make_photo, 2, "Hacer foto")
        menu.add(this.adapterPosition, R.id.context_see_photo, 3, "Ver foto")


    }
}