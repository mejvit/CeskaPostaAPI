package cz.utb.fai.ceskapostaapi.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.ceskapostaapi.R
import cz.utb.fai.ceskapostaapi.databinding.HistoryFragmentBinding
import cz.utb.fai.ceskapostaapi.databinding.ParcelItemBinding
import cz.utb.fai.ceskapostaapi.models.ParcelRecord

class HistoryFragment : Fragment() {
    private var _binding: HistoryFragmentBinding? = null
    private lateinit var viewModel: HistoryViewModel

    private val binding get() = _binding!!

    private var viewModelAdapter: ParcelRecordAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.records.observe(viewLifecycleOwner,Observer<List<ParcelRecord>> { items ->
            items?.apply {
                viewModelAdapter?.parcelItems = items
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModelAdapter = ParcelRecordAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}

class ParcelRecordAdapter(): RecyclerView.Adapter<ParcelRecordViewHolder>() {
    var parcelItems: List<ParcelRecord> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParcelRecordViewHolder {
        val withDataBinding: ParcelItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ParcelRecordViewHolder.LAYOUT,
            parent,
            false
        )

        return ParcelRecordViewHolder(withDataBinding)
    }

    override fun getItemCount() = parcelItems.size

    override fun onBindViewHolder(holder: ParcelRecordViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.parcelRecord = parcelItems[position]
        }
    }
}

class ParcelRecordViewHolder(val viewDataBinding: ParcelItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.parcel_item
    }
}