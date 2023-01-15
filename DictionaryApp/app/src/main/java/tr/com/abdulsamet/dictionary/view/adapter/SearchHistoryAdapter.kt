package tr.com.abdulsamet.dictionary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.data.model.SearchHistory
import tr.com.abdulsamet.dictionary.databinding.ItemSearchHistoryBinding
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class SearchHistoryAdapter @Inject constructor() :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    class SearchHistoryViewHolder(val itemBinding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<SearchHistory>() {
        override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SearchHistory,
            newItem: SearchHistory
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)
    var searchHistoryList: List<SearchHistory>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
    var onItemClickListener: ((SearchHistory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_history, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.itemBinding.searchHistory = searchHistoryList[position]
        holder.itemBinding.root.setOnClickListener {
            onItemClickListener?.let {
                it(searchHistoryList[position])
            }
        }
    }
}