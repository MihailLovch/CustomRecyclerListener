package ru.kpfu.itis.customrecyclerlistener.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.kpfu.itis.customrecyclerlistener.R
import ru.kpfu.itis.customrecyclerlistener.databinding.ItemBinding
import ru.kpfu.itis.customrecyclerlistener.databinding.RecyclerItemBinding

class Adapter(
    private val itemsCount: Int,
    private val boolean: Boolean = true
) : RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        if (viewType == 2){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            return Holder(RecyclerItemBinding.bind(view))
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return Holder(ItemBinding.bind(view))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 3 && boolean){
            2
        }else{
            1
        }
    }

    override fun getItemCount(): Int = itemsCount

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(position)
    }
}

class Holder(
    private val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root){
    fun onBind(position: Int){
//            val layoutParams = root.layoutParams
//            layoutParams.height = 300 * (position%3 + 1)
//            root.layoutParams = layoutParams
        if (binding is RecyclerItemBinding){
            binding.innerRv.adapter = Adapter(12,false)
        }
    }
}