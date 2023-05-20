package ru.kpfu.itis.customrecyclerlistener.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.customrecyclerlistener.R
import ru.kpfu.itis.customrecyclerlistener.databinding.ItemBinding

class Adapter(
    private val itemsCount: Int,
) : RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return Holder(ItemBinding.bind(view))
    }

    override fun getItemCount(): Int = itemsCount

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(position)
    }
}

class Holder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root){
    fun onBind(position: Int){
        with(binding){
            val layoutParams = root.layoutParams
            layoutParams.height = 300 * (position%3 + 1)
            root.layoutParams = layoutParams
        }
    }
}