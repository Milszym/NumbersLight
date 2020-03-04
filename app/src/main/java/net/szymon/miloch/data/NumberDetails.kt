package net.szymon.miloch.data

import net.szymon.miloch.api.data.NumberDetailsDto

data class NumberDetails(
    val name: String,
    val text: String,
    val image: String
) {
    constructor(numberDetailsDto: NumberDetailsDto) : this(
        name = numberDetailsDto.name,
        text = numberDetailsDto.text,
        image = numberDetailsDto.image
    )
}
