package cz.utb.fai.ceskapostaapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.ceskapostaapi.R
import cz.utb.fai.ceskapostaapi.databinding.HistoryFragmentBinding
import cz.utb.fai.ceskapostaapi.databinding.ParcelDetailFragmentBinding
import cz.utb.fai.ceskapostaapi.databinding.ParcelItemBinding
import cz.utb.fai.ceskapostaapi.databinding.StateItemBinding
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.models.ParcelRecordState

class ParcelDetailFragment : Fragment() {

    //companion object {
    //    fun newInstance() = ParcelDetailFragment()
    //}
    private var _binding: ParcelDetailFragmentBinding? = null
    private lateinit var viewModel: ParcelDetailViewModel

    private val binding get() = _binding!!

    private var viewModelAdapter: ParcelStateAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.states.observe(viewLifecycleOwner, Observer<List<ParcelRecordState>> { items ->
            items?.apply {
                viewModelAdapter?.parcelStates = items
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val application = requireNotNull(activity).application
        val binding = ParcelDetailFragmentBinding.inflate(inflater)
        val parcelDetailId = ParcelDetailFragmentArgs.fromBundle(arguments!!).parcelRecordId
        val viewModelFactory = ParcelDetailViewModelFactory(parcelDetailId, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ParcelDetailViewModel::class.java)
        _binding = ParcelDetailFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModelAdapter = ParcelStateAdapter()

        binding.root.findViewById<RecyclerView>(R.id.parcel_states).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }
        return binding.root
    }


}

class ParcelStateAdapter(): RecyclerView.Adapter<ParcelStateViewHolder>() {
    var parcelStates: List<ParcelRecordState> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParcelStateViewHolder {
        val withDataBinding: StateItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ParcelStateViewHolder.LAYOUT,
            parent,
            false
        )

        return ParcelStateViewHolder(withDataBinding)
    }

    override fun getItemCount() = parcelStates.size

    override fun onBindViewHolder(holder: ParcelStateViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.parcelState = parcelStates[position]
        }
    }
}

class ParcelStateViewHolder(val viewDataBinding: StateItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.state_item
    }
}