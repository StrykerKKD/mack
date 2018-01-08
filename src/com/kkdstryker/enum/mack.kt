package com.kkdstryker.enum

var running = true
var ip = 0
var sp = -1

var stack = Array(256, { 0 })

enum class InstructionSet {
    PSH, ADD, POP, HLT
}

var program = arrayOf(
        InstructionSet.PSH.ordinal, 5,
        InstructionSet.PSH.ordinal, 6,
        InstructionSet.ADD.ordinal,
        InstructionSet.POP.ordinal,
        InstructionSet.HLT.ordinal
)

fun fetch(): Int {
    return program[ip]
}

fun eval(instruction: Int) = when (instruction) {
    InstructionSet.HLT.ordinal -> {
        running = false
        println("done")
    }
    InstructionSet.PSH.ordinal -> {
        sp++
        stack[sp] = program[++ip]
    }
    InstructionSet.POP.ordinal -> {
        val popped = stack[sp--]
        println("popped " + popped)
    }
    InstructionSet.ADD.ordinal -> {
        val a = stack[sp--]
        val b = stack[sp--]
        val result = b + a
        sp++
        stack[sp] = result
    }
    else -> throw IllegalArgumentException("Illegal instruction!")
}


fun main(args: Array<String>) {
    while (running) {
        eval(fetch())
        ip++
    }
}