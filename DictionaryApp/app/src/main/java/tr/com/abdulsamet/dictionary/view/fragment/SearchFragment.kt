package tr.com.abdulsamet.dictionary.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.base.BaseFragment
import tr.com.abdulsamet.dictionary.data.model.SearchHistory
import tr.com.abdulsamet.dictionary.databinding.FragmentSearchBinding
import tr.com.abdulsamet.dictionary.view.adapter.SearchHistoryAdapter
import tr.com.abdulsamet.dictionary.viewmodel.SearchViewModel
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class SearchFragment @Inject constructor(
    private val searchHistoryAdapter: SearchHistoryAdapter
) : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override fun getViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setView()
    }

    private fun setView() {
        dataBinding.searchHistoryRecycler.adapter = searchHistoryAdapter

        dataBinding.buttonSearch.setOnClickListener {
            val searchedText = dataBinding.editTextSearch.text.toString()
            if (searchedText == "") {
                Toast.makeText(
                    parentActivity, parentActivity.getText(
                        R.string.warning_empty_field
                    ), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.insertSearchHistory(SearchHistory(searchedText))

            Navigation.findNavController(requireView()).navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(searchedText)
            )
        }

        searchHistoryAdapter.onItemClickListener = {
            Navigation.findNavController(requireView()).navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.searchString)
            )
        }
    }

    private fun setObserver() {
        viewModel.observedSearchHistory.observe(viewLifecycleOwner) {
            it?.let {
                searchHistoryAdapter.searchHistoryList = it
            }
        }
    }

}