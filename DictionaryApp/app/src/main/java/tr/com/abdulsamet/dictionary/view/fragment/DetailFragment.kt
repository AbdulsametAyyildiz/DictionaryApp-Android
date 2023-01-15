package tr.com.abdulsamet.dictionary.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.base.BaseFragment
import tr.com.abdulsamet.dictionary.base.ResponseResource
import tr.com.abdulsamet.dictionary.databinding.FragmentDetailBinding
import tr.com.abdulsamet.dictionary.view.adapter.MeaningAdapter
import tr.com.abdulsamet.dictionary.view.adapter.SynonymAdapter
import tr.com.abdulsamet.dictionary.viewmodel.DetailViewModel
import javax.inject.Inject


/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class DetailFragment @Inject constructor(
    private val meaningAdapter: MeaningAdapter,
    private val synonymAdapter: SynonymAdapter,
    private val speechAdapter: SynonymAdapter
) : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    override fun getViewModel(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val word = it.getString("word", "")
            if (word.isNotEmpty()) {
                if (parentActivity.checkConnection()) {
                    viewModel.getMeaning(word)
                    viewModel.getSynonym(word)
                } else {
                    Toast.makeText(
                        parentActivity,
                        parentActivity.getText(R.string.error_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                    parentActivity.onBackPressedDispatcher.onBackPressed()
                }
            } else {
                Toast.makeText(
                    parentActivity,
                    parentActivity.getText(R.string.error_unexpected),
                    Toast.LENGTH_SHORT
                ).show()
                parentActivity.onBackPressedDispatcher.onBackPressed()
            }
        }
        setView()
        setObserver()
    }

    private fun setView() {
        dataBinding.recyclerSpeech.layoutManager =
            LinearLayoutManager(parentActivity, LinearLayoutManager.HORIZONTAL, false)
        dataBinding.recyclerSynonym.layoutManager =
            LinearLayoutManager(parentActivity, LinearLayoutManager.HORIZONTAL, false)
        dataBinding.recyclerMeaning.addItemDecoration(
            DividerItemDecoration(
                parentActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        dataBinding.recyclerMeaning.adapter = meaningAdapter
        dataBinding.recyclerSynonym.adapter = synonymAdapter
        dataBinding.recyclerSpeech.adapter = speechAdapter
        dataBinding.buttonBack.setOnClickListener {
            parentActivity.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setObserver() {
        viewModel.meaningLiveData.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    ResponseResource.Status.SUCCESS -> {
                        viewModel.synonymLiveData.value?.let { synonymData ->
                            if (synonymData.status != ResponseResource.Status.LOADING) {
                                dataBinding.progressBar.visibility = View.GONE
                            }
                        }
                        it.data?.let { meaning ->
                            dataBinding.meaningResponse = meaning[0]
                            val processedMeaning = viewModel.convertFormattedMeaningList(meaning[0])
                            meaningAdapter.meaningList = processedMeaning.meaningList
                            speechAdapter.stringList = processedMeaning.speechList
                        }
                        dataBinding.dataView.visibility = View.VISIBLE
                    }

                    ResponseResource.Status.LOADING -> {
                        dataBinding.dataView.visibility = View.GONE
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }

                    ResponseResource.Status.ERROR -> {
                        Toast.makeText(
                            parentActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        parentActivity.onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }

        viewModel.synonymLiveData.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    ResponseResource.Status.SUCCESS -> {
                        viewModel.meaningLiveData.value?.let { meaningData ->
                            if (meaningData.status != ResponseResource.Status.LOADING) {
                                dataBinding.progressBar.visibility = View.GONE
                            }
                        }
                        it.data?.let { synonym ->
                            synonymAdapter.stringList =
                                viewModel.convertFormattedSynonymList(synonym)

                        }
                        dataBinding.synonymView.visibility = View.VISIBLE
                    }

                    ResponseResource.Status.LOADING -> {
                        dataBinding.synonymView.visibility = View.GONE
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }

                    ResponseResource.Status.ERROR -> {
                        Toast.makeText(
                            parentActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        dataBinding.synonymView.visibility = View.GONE
                        dataBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

}