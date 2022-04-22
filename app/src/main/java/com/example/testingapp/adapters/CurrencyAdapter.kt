package com.example.testingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.R
import com.example.testingapp.entities.Rate
import java.text.DecimalFormat

class CurrencyAdapter(private val context: Context?,
                      private val list: List<Rate>,
                      private val onCurrencyItemClickListener: OnCurrencyItemClickListener
): ListAdapter<Rate, CurrencyAdapter.ViewHolder>(Callback()) {


    class Callback: DiffUtil.ItemCallback<Rate>(){
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem == newItem
        }

    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val name: TextView = view.findViewById(R.id.name_tv)
        private val value: TextView = view.findViewById(R.id.value_tv)
        private val imageAdd: ImageView = view.findViewById(R.id.add_icon)

        fun bind(data: Rate, onCurrencyItemClickListener: OnCurrencyItemClickListener){
            var added = false

            name.text = data.nameCurrency
            imageAdd.setImageResource(R.drawable.ic_baseline_star_border_24)
            value.text = (DecimalFormat("#,##").format(data.valueCurrency)).toString()

            for(rate in list){
                if(rate.id == data.id){
                   added = true
                   imageAdd.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }

            imageAdd.setOnClickListener {
                if(added)
                    onCurrencyItemClickListener.deleteItem(data)
                else
                    onCurrencyItemClickListener.addItem(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_currency, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onCurrencyItemClickListener)
    }

}
interface OnCurrencyItemClickListener{
    fun deleteItem(rate: Rate)
    fun addItem(rate: Rate)
}