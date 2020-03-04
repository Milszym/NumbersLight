package net.szymon.miloch.ui.common.view.nocontent

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.no_content_view.view.*
import net.szymon.miloch.R

class NoContentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    var onRetryClick: (() -> Unit)? = null

    init {
        inflate(context, R.layout.no_content_view, this)
        retryButton.setOnClickListener {
            onRetryClick?.invoke()
        }
    }

    var noContentMode: NoContentMode? = null
        set(value) {
            field = value
            if (value != null) {
                setContentMode(value)
            }
        }

    private fun setContentMode(noContentMode: NoContentMode) {
        val stringResId = when (noContentMode) {
            NoContentMode.ERROR -> R.string.error_label
            NoContentMode.NO_CONTENT -> R.string.no_content_label
        }
        noContentTitle.text = context.getString(stringResId)
    }
}