package tr.com.abdulsamet.dictionary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tr.com.abdulsamet.dictionary.view.activity.MainActivity

abstract class BaseFragment<VM : ViewModel, VDB : ViewDataBinding> : Fragment() {

    protected lateinit var dataBinding: VDB
    protected lateinit var viewModel: VM
    protected lateinit var parentActivity: MainActivity

    protected abstract fun getViewModel(): Class<VM>

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewModel = ViewModelProvider(requireActivity())[getViewModel()]
        parentActivity = (requireActivity() as MainActivity)
        return dataBinding.root
    }
}