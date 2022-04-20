package com.example.testingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testingapp.R
import com.example.testingapp.util.CurrencyAdapter
import com.example.testingapp.util.OnCurrencyItemClickListener
import com.example.testingapp.appComponent
import com.example.testingapp.databinding.FragmentCurrencyBinding
import com.example.testingapp.entities.Rate
import com.example.testingapp.room.RateRoomViewModel
import com.example.testingapp.models.viewModels.CurrencyViewModel
import com.example.testingapp.models.viewModels.SharedViewModelFactory
import com.example.testingapp.models.viewModels.State
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyFragment] factory method to
 * create an instance of this fragment.
 */
class CurrencyFragment : Fragment(), OnCurrencyItemClickListener {

    private lateinit var binding: FragmentCurrencyBinding
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var roomViewModel: RateRoomViewModel
    private lateinit var adapter: CurrencyAdapter
    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.appComponent?.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[CurrencyViewModel::class.java]
        roomViewModel = ViewModelProvider(this)[RateRoomViewModel::class.java]

        roomViewModel.allRates.observe(viewLifecycleOwner) {
            setAdapterRecyclerView(it)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                updateResponseState(it)
            }
        }

        binding.sortButton.setOnClickListener {
            navigateToSortPage()
        }

        binding.searchEt.addTextChangedListener {
            viewModel.searchRateByName(it.toString())
        }
    }

    private fun setAdapterRecyclerView(list: List<Rate>){
        binding.currencyRv.adapter = CurrencyAdapter(
            context, list, this@CurrencyFragment
        )
        adapter = binding.currencyRv.adapter as CurrencyAdapter
        setDataToRecyclerView()
    }

    private fun setDataToRecyclerView(){
        lifecycleScope.launchWhenStarted {
            viewModel.currencyList.collect {listResponse ->
                adapter.submitList(listResponse)
                adapter.notifyDataSetChanged()
            }
        }
    }


    private fun updateResponseState(state: State){
        when(state){
            is State.SuccessState -> successResponseState()
            is State.SendingState -> loadingResponseState()
            is State.ErrorState<*> -> {
                when(state.message){
                    is Int -> errorResponseState(state.message)
                    is String -> errorResponseState(state.message)
                }
            }
        }
    }

    private fun navigateToSortPage(){
        findNavController().navigate(R.id.action_currencyFragment_to_sortPageFragment)
    }

    private fun errorResponseState(message: Int){
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    private fun errorResponseState(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadingResponseState(){
        binding.avlLoading.visibility = View.VISIBLE
        binding.currencyRv.visibility = View.GONE
    }

    private fun successResponseState(){
        binding.avlLoading.visibility = View.GONE
        binding.currencyRv.visibility = View.VISIBLE
    }

    override fun deleteItem(rate: Rate) {
        roomViewModel.delete(rate)
    }

    override fun addItem(rate: Rate) {
        roomViewModel.add(rate)
    }
}