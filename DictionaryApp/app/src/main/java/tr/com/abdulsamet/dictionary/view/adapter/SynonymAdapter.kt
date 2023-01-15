package tr.com.abdulsamet.dictionary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.databinding.ItemSynonymBinding
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class SynonymAdapter @Inject constructor() :
    RecyclerView.Adapter<SynonymAdapter.SynonymViewHolder>() {

    class SynonymViewHolder(val itemBinding: ItemSynonymBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)
    var stringList: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SynonymViewHolder {
        return SynonymViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_synonym, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    override fun onBindViewHolder(holder: SynonymViewHolder, position: Int) {
        holder.itemBinding.str = stringList[position]
    }
}