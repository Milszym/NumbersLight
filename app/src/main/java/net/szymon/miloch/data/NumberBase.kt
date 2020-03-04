package net.szymon.miloch.data

import net.szymon.miloch.api.data.NumberBaseDto

data class NumberBase(
    val name: String,
    val image: String
) {
    constructor(numberBaseDto: NumberBaseDto) : this(
        name = numberBaseDto.name,
        image = numberBaseDto.image
    )
}
