package com.example.nca_final.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nca_final.R
import com.example.nca_final.databinding.DuckListItemBinding
import com.example.nca_final.model.entities.Duck
import com.example.nca_final.utils.DuckRarity

class DuckListAdapter: ListAdapter<Duck, DuckListAdapter.DuckViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Duck>() {
            override fun areItemsTheSame(oldItem: Duck, newItem: Duck): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Duck, newItem: Duck): Boolean {
                return oldItem == newItem
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuckViewHolder {
        return DuckViewHolder(
            DuckListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DuckViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DuckViewHolder(private val binding: DuckListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(duck: Duck) {
            val rarity = DuckRarity.values()[duck.rarity!!]
            binding.name.text = duck.name
            binding.price.text = duck.value.toString()
            binding.rarity.text = rarity.displayableName
            binding.rarity.setTextColor(Color.parseColor(rarity.color))
            binding.image.setImageResource(binding.root.context.resources.getIdentifier(duck.image, "drawable", binding.root.context.packageName))

            binding.duckLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("duck", duck)
                binding.root.findNavController().navigate(R.id.action_DuckListFragment_to_DuckDetailsFragment, bundle)
            }
        }
    }
}