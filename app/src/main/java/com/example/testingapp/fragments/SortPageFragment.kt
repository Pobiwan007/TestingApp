package com.example.testingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testingapp.databinding.FragmentSortPageBinding
import com.example.testingapp.models.viewModels.CurrencyViewModel
import com.example.testingapp.models.viewModels.Sort
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [SortPageFragment] factory method to
 * create an instance of this fragment.
 */
class SortPageFragment : Fragment() {

    private lateinit var binding: FragmentSortPageBinding
    private val viewModel: CurrencyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSortPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sortValue.collect {
                checkSortActivated(it)
            }
        }

        setItemSortClickListener(binding.nameDescending, Sort.SortByCharDown)
        setItemSortClickListener(binding.nameAscending, Sort.SortByCharUp)
        setItemSortClickListener(binding.valueAscending, Sort.SortByValueUp)
        setItemSortClickListener(binding.valueDescending, Sort.SortByValueDown)

    }

    private fun setItemSortClickListener(textView: TextView, sort: Sort){
        textView.setOnClickListener {
            viewModel.setSortValue(sort)
        }
    }

    private fun checkSortActivated(sort: Sort){
        when(sort){
            is Sort.SortByValueDown -> activateItemSortUI(binding.valueDescending)
            is Sort.SortByValueUp -> activateItemSortUI(binding.valueAscending)
            is Sort.SortByCharUp -> activateItemSortUI(binding.nameAscending)
            is Sort.SortByCharDown -> activateItemSortUI(binding.nameDescending)
        }
    }

    private fun activateItemSortUI(textView: TextView){
        binding.nameAscending.isActivated = false
        binding.nameDescending.isActivated = false
        binding.valueAscending.isActivated = false
        binding.valueDescending.isActivated = false
        textView.isActivated = true
    }
}