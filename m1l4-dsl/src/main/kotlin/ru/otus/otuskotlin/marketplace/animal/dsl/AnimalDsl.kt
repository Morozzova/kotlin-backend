package ru.otus.otuskotlin.marketplace.animal.dsl

import ru.otus.otuskotlin.marketplace.animal.models.Animal

@AnimalDsl
class CharacteristicsContext {
    var type: String = ""
    var name: String = ""
}

@AnimalDsl
class AppearanceContext {
    var color: String = ""
    var fur: Boolean = false
}

@AnimalDsl
class ActionsContext {
    private val _actions: MutableSet<String> = mutableSetOf()
    val actions: Set<String>
        get() = _actions.toSet()

    fun makeSound(value: String) {
        _actions.add(value)
        println(value)
    }

    fun makeAnotherSound(value: String) {
        _actions.add(value)
        println(value)
    }
}

@AnimalDsl
class AnimalBuilder {
    private var type: String = ""
    private var name: String = ""

    private var color: String = ""
    private var fur: Boolean = false

    private var actions: Set<String> = emptySet()

    @AnimalDsl
    fun characteristics(block: CharacteristicsContext.() -> Unit) {
        val ctx = CharacteristicsContext().apply(block)
        type = ctx.type
        name = ctx.name
    }

    @AnimalDsl
    fun appearance(block: AppearanceContext.() -> Unit) {
        val ctx = AppearanceContext().apply(block)
        color = ctx.color
        fur = ctx.fur
    }

    @AnimalDsl
    fun actions(block: ActionsContext.() -> Unit) {
        val ctx = ActionsContext().apply(block)
        actions = ctx.actions
    }

    fun build() = Animal(type, name, color, fur, actions)
}

@AnimalDsl1
fun buildAnimal(block: AnimalBuilder.() -> Unit) = AnimalBuilder().apply(block).build()

@DslMarker
annotation class AnimalDsl

@DslMarker
annotation class AnimalDsl1