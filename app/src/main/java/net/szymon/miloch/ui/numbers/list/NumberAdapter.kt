package net.szymon.miloch.ui.numbers.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.szymon.miloch.data.NumberBase
import net.szymon.miloch.ui.recyclerview.createViewHolder

class NumbersAdapter(
    var numbers: List<NumberBase> = emptyList(),
    private val onNumberClick: (numberName: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentSelection: Int = NON_SELECTED
        set(value) {
            notifyItemChanged(field)
            field = value
            notifyItemChanged(value)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val numberData = numbers[position]
        (holder.itemView as NumberItemView).dataModel = NumberItemDataModel(
            numberBase = numberData,
            selected = currentSelection == position
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = NumberItemView(parent.context)
        val viewHolder = createViewHolder(view)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.onNumberClick = {
            val adapterPosition = viewHolder.adapterPosition
            val number = numbers[adapterPosition]
            currentSelection = adapterPosition
            this.onNumberClick(number.name)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = numbers.size

    fun updateItems(newNumbers: List<NumberBase>) {
        numbers = newNumbers
        notifyDataSetChanged()
    }

    companion object {
        private const val NON_SELECTED = -1
    }
}