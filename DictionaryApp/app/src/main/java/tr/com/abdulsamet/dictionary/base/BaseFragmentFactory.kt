package tr.com.abdulsamet.dictionary.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import tr.com.abdulsamet.dictionary.view.adapter.MeaningAdapter
import tr.com.abdulsamet.dictionary.view.adapter.SearchHistoryAdapter
import tr.com.abdulsamet.dictionary.view.adapter.SynonymAdapter
import tr.com.abdulsamet.dictionary.view.fragment.DetailFragment
import tr.com.abdulsamet.dictionary.view.fragment.SearchFragment
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class BaseFragmentFactory @Inject constructor(
    private val searchHistoryAdapter: SearchHistoryAdapter,
    private val meaningAdapter: MeaningAdapter,
    private val synonymAdapter: SynonymAdapter,
    private val speechAdapter: SynonymAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SearchFragment::class.java.name -> SearchFragment(
                searchHistoryAdapter
            )
            DetailFragment::class.java.name -> DetailFragment(
                meaningAdapter, synonymAdapter, speechAdapter
            )

            else -> super.instantiate(classLoader, className)
        }
    }
}