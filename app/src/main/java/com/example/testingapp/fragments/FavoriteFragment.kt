package com.example.testingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.R
import com.example.testingapp.util.CurrencyAdapter
import com.example.testingapp.util.OnCurrencyItemClickListener
import com.example.testingapp.entities.Rate
import com.example.testingapp.room.RateRoomViewModel
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(), OnCurrencyItemClickListener {

    private lateinit var viewModel: RateRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RateRoomViewModel::class.java]
        val recyclerView = view.findViewById<RecyclerView>(R.id.favorite_list)
        val emptyLinearLayout = view.findViewById<LinearLayout>(R.id.empty_container)

        viewModel.allRates.observe(viewLifecycleOwner) {
            recyclerView.adapter = CurrencyAdapter(context, it, this@FavoriteFragment)
            val adapter = recyclerView.adapter as CurrencyAdapter
            adapter.submitList(it)
            viewModel.updateEmptyList()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isEmptyList.collect {
                if(it) emptyLinearLayout.visibility = View.VISIBLE
                else emptyLinearLayout.visibility = View.GONE
            }
        }
    }

    override fun deleteItem(rate: Rate) {
        viewModel.delete(rate)
    }

    override fun addItem(rate: Rate) {
        viewModel.add(rate)
    }

}