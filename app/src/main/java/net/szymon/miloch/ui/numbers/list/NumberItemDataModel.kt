package net.szymon.miloch.ui.numbers.list

import net.szymon.miloch.data.NumberBase

data class NumberItemDataModel(
    val numberBase: NumberBase,
    val selected: Boolean
)