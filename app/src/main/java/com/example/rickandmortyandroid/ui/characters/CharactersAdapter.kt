package com.example.rickandmortyandroid.ui.characters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyandroid.R
import com.example.rickandmortyandroid.models.Character
import com.squareup.picasso.Picasso

class CharactersAdapter: ListAdapter<Character, CharactersAdapter.VH>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem == newItem
        }
    }


    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvSpecies: TextView = view.findViewById(R.id.tvSpecies)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return VH(view)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = getItem(position)
        holder.tvName.text = c.name
        holder.tvSpecies.text = "${c.species} • ${c.status}"
// Cargar imagen (Picasso en este ejemplo)
        Picasso.get().load(c.imageUrl).into(holder.ivAvatar)
    }
}