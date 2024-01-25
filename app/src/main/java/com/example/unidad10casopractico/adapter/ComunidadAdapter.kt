package com.example.unidad10casopractico.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unidad10casopractico.Comunidad
import com.example.unidad10casopractico.R
import com.example.unidad10casopractico.diffUtil.ComunidadDiffUtil

class ComunidadAdapter(var comuniLista:List<Comunidad>, private val onClickListener: (Comunidad) -> Unit):
    RecyclerView.Adapter<ComunidadViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComunidadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_bandera, parent, false)
        return ComunidadViewHolder(itemView)
    }

    override fun getItemCount():Int{
        return comuniLista.size
    }

    override fun onBindViewHolder(holder: ComunidadViewHolder, position:Int){
        val item=comuniLista[position]
        holder.render(item, onClickListener)
    }

    fun updateList(newList: List<Comunidad>){
        val comunidadAutonomaDiff = ComunidadDiffUtil(comuniLista, newList)
        val result = DiffUtil.calculateDiff(comunidadAutonomaDiff)
        comuniLista = newList
        result.dispatchUpdatesTo(this)
    }
}