package com.kkdstryker.sealedclass


var running = true
var ip = 0
var sp = -1

var stack = Array(256, { 0 })

sealed class InstructionSet
data class PSH(val value: Int) : InstructionSet()
object ADD : InstructionSet()
object POP : InstructionSet()
object HLT : InstructionSet()

var program = arrayOf(
        PSH(5),
        PSH(6),
        ADD,
        POP,
        HLT
)

fun fetch(): InstructionSet {
    return program[ip]
}

fun eval(instruction: InstructionSet) = when (instruction) {
    HLT -> {
        running = false
        println("done")
    }
    is PSH -> {
        sp++
        stack[sp] = instruction.value
    }
    POP -> {
        val popped = stack[sp--]
        println("popped " + popped)
    }
    ADD -> {
        val a = stack[sp--]
        val b = stack[sp--]
        val result = b + a
        sp++
        stack[sp] = result
    }
}

fun main(args: Array<String>) {
    while (running) {
        eval(fetch())
        ip++
    }
}