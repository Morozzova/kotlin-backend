package ru.otus.otuskotlin.marketplace.sql

enum class Commands(val title: String) {
    SELECT("select"),
    FROM("from"),
    WHERE("where"),
    OR("or")
}

class OrContext {

    private val _parts: MutableList<String> = mutableListOf()
    val parts: List<String>
        get() = _parts.toList()
    private fun <A>secondArgument(value: A) =
        if (value != null)
            when (value) {
                is String -> "'$value'"
                is Number -> value.toString()
                else -> ""
            } else null.toString()

    infix fun <A> String.eq(value: A) {
        val sign = if (value != null) "=" else "is"
        val part = "$this $sign ${secondArgument(value)}"
        _parts.add(part)
    }

    infix fun <A> String.nonEq(value: A) {
        val sign = if (value != null) "!=" else "!is"
        val part = "$this $sign ${secondArgument(value)}"
        _parts.add(part)
    }

    fun build() : String {
        return parts[0] + " " + Commands.OR.title + " " + parts[1]
    }
}
open class WhereContext {

    private var whereString = ""


    private fun <A>secondArgument(value: A) =
        if (value != null)
        when (value) {
            is String -> "'$value'"
            is Number -> value.toString()
            else -> ""
        } else null.toString()

    infix fun <A> String.eq(value: A) {
        val sign = if (value != null) "=" else "is"
        whereString = "$whereString$this $sign ${secondArgument(value)}"
    }

    infix fun <A> String.nonEq(value: A) {
        val sign = if (value != null) "!=" else "!is"
        whereString = whereString + "$this $sign ${secondArgument(value)}"
    }

    fun or(block: OrContext.() -> Unit) {
        val ctx = OrContext().apply(block)
        whereString = whereString + "(" + ctx.build() + ")"
    }

    open fun build(): String {
        return whereString
    }
}

class SqlSelectBuilder {

    private val _commands: MutableSet<Commands> = mutableSetOf()

    private var query: String = "select * "
    fun select(vararg values: String) {
        _commands.add(Commands.SELECT)
        query = Commands.SELECT.title + " " + values.toList().toString().trim('[', ']') + " "
    }

    fun from(value: String) {
        _commands.add(Commands.FROM)
        query = query + Commands.FROM.title + " " + value
    }

    fun where(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        query = query + " " + Commands.WHERE.title + " " + ctx.build()
    }

    fun build(): String {
        if (!_commands.contains(Commands.FROM)) throw Exception()
        return query
    }
}

fun query(block: SqlSelectBuilder.() -> Unit) = SqlSelectBuilder().apply(block)