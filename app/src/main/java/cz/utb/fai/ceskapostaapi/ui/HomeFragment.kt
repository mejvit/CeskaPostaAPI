package cz.utb.fai.ceskapostaapi.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.utb.fai.ceskapostaapi.R
import cz.utb.fai.ceskapostaapi.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private lateinit var viewModel: HomeViewModel

    private val binding get() = _binding!!

  //  companion object {
        //fun newInstance() = HomeFragment()
  //  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.selectedParcelId.observe(this, Observer{
            if (it != null) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToParcelDetailFragment(viewModel.selectedParcelId.value.orEmpty()))
                viewModel.selectedParcelId.value = null
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parcelId.setSelectAllOnFocus(true);

    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    super.onActivityCreated(savedInstanceState)
    //    viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    //    // TODO: Use the ViewModel
    //}

}