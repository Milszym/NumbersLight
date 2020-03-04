package net.szymon.miloch.ui.numbers.list

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.numbers_list_item.view.*
import net.szymon.miloch.R

class NumberItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    var onNumberClick: (() -> Unit)? = null

    init {
        inflate(context, R.layout.numbers_list_item, this)
        setBackgroundResource(R.drawable.number_item_background)

        setOnClickListener {
            onNumberClick?.invoke()
        }
    }

    var dataModel: NumberItemDataModel? = null
        set(value) {
            field = value
            if (value != null) {
                initializeView(value)
            }
        }

    private fun initializeView(dataModel: NumberItemDataModel) {
        numberName.text = dataModel.numberBase.name
        Picasso.get().load(dataModel.numberBase.image).into(numberImage)
        isSelected = dataModel.selected
    }
}
