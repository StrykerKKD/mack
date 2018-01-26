package com.kkdstryker.functional

val stack = emptyList<Int>()

sealed class InstructionSet
data class PSH(val value: Int) : InstructionSet()
object ADD : InstructionSet()
object POP : InstructionSet()
object HLT : InstructionSet()

val program = listOf(
        PSH(5),
        PSH(6),
        ADD,
        POP,
        HLT)

fun eval(stack: List<Int>, instruction: InstructionSet): List<Int> {
    when (instruction) {
        HLT -> {
            println("done")
            return stack
        }
        is PSH -> {
            return stack + instruction.value
        }
        POP -> {
            val popped = stack.first()
            println("popped $popped\n")
            return stack.drop(1)
        }
        ADD -> {
            val a = stack[0]
            val b = stack[1]
            val result = a + b
            return stack.drop(2) + result
        }
    }
}

tailrec fun foldUntilHLT(eval: (List<Int>, InstructionSet) -> List<Int>, stack: List<Int>, program: List<InstructionSet>): List<Int> {
    if(program.isEmpty()) {
        return stack
    }
    val head = program[0]
    val tail = program.drop(1)
    return when (head) {
        HLT -> {
            println("done")
            return stack
        }
        else ->
            foldUntilHLT(eval, eval(stack, head), tail)
    }
}

fun main(args: Array<String>) {
    val result = foldUntilHLT(::eval, stack, program)
    println("result is $result")
}