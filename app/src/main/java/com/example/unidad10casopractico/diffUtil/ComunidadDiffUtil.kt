package com.example.unidad10casopractico.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.unidad10casopractico.Comunidad

class ComunidadDiffUtil(
    private val oldList: List<Comunidad>,
    private val newList: List<Comunidad>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}