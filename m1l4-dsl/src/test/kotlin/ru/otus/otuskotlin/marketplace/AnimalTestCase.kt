package ru.otus.otuskotlin.marketplace

import org.junit.Test
import ru.otus.otuskotlin.marketplace.animal.dsl.buildAnimal

class AnimalTestCase {
    @Test
    fun animalTest() {
        val animal = buildAnimal {
            characteristics {
                type = "cat"
                name = "Murka"
            }
            appearance {
                color = "white"
                fur = true
            }
            actions {
                makeSound("meow")
                makeAnotherSound("Woof!")
            }
        }
    }
}
