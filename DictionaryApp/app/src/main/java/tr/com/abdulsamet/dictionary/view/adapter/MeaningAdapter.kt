package tr.com.abdulsamet.dictionary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.custom.model.ProcessedMeaning
import tr.com.abdulsamet.dictionary.databinding.ItemMeaningBinding
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class MeaningAdapter @Inject constructor() :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    class MeaningViewHolder(val itemBinding: ItemMeaningBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ProcessedMeaning.Meaning>() {
        override fun areItemsTheSame(oldItem: ProcessedMeaning.Meaning, newItem: ProcessedMeaning.Meaning): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProcessedMeaning.Meaning,
            newItem: ProcessedMeaning.Meaning
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)
    var meaningList: List<ProcessedMeaning.Meaning>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        return MeaningViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_meaning, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.itemBinding.meaning = meaningList[position]
    }
}