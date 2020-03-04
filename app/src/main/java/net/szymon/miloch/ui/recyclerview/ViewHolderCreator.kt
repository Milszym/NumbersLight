package net.szymon.miloch.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ViewHolderCreator(itemView: View) : RecyclerView.ViewHolder(itemView)

fun RecyclerView.Adapter<RecyclerView.ViewHolder>.createViewHolder(view: View) =
    ViewHolderCreator(view)