// IS_APPLICABLE: false
fun main(args: Array<String>) {
    val foo = null
    val bar = "bar"
    if (foo > null<caret>) {
        foo
    }
    else {
        bar
    }
}
