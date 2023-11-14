import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

open class Calculator {

    companion object {
        const val OPERATORS = "+-*/"
        const val PRECEDENCE = 4

        //=-=-=-=- Using Extensions=-=-=-=-=-
        fun Char.isOperator(): Boolean {
            return OPERATORS.contains(this)
        }

        fun getPrecedence(c: Char): Int {
            return when (c) {
                '+', '-' -> 1
                '*', '/' -> 2
                else -> 0
            }
        }

        fun shuntingYard(infixExpression: String): String {
            val output = StringBuilder()
            val operators = Stack<Char>()

            var i = 0
            while (i < infixExpression.length) {
                val char = infixExpression[i]
                if (char.isDigit()) {
                    val number = StringBuilder()
                    while (i < infixExpression.length && (infixExpression[i].isDigit() || infixExpression[i] == '.')) {
                        number.append(infixExpression[i])
                        i++
                    }
                    output.append(number).append(" ")
                    i--
                } else if (char.isOperator()) {
                    while (operators.isNotEmpty() && operators.peek() != '(' && getPrecedence(operators.peek()) >= getPrecedence(
                            char
                        )
                    ) {
                        output.append(operators.pop()).append(" ")
                    }
                    operators.push(char)
                } else if (char == '(') {
                    operators.push(char)
                } else if (char == ')') {
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        output.append(operators.pop()).append(" ")
                    }
                    operators.pop()
                }
                i++
            }

            while (operators.isNotEmpty()) {
                output.append(operators.pop()).append(" ")
            }

            return output.toString().trim()
        }

        fun evaluatePostfix(postfixExpression: String): Double {
            val stack = MyStack<Double>()
            // =-=-==-=-Using Lambda=-=-=-=-
            postfixExpression.split(" ")
                .stream()
                .filter(String::isNotEmpty)
                .forEach { item -> processToken(item, stack) }
            return -stack
        }

        private fun processToken(token: String, stack: MyStack<Double>) {
            if (token[0].isDigit() || (token.length > 1 && token[0] == '-' && token[1].isDigit())) {
                stack + (token.toDouble())
            } else if (token[0].isOperator()) {
                val operand2 = -stack
                val operand1 = -stack
                when (token[0]) {
                    '+' -> stack + round(operand1 + operand2)
                    '-' -> stack + round(operand1 - operand2)
                    '*' -> stack + round(operand1 * operand2)
                    '/' -> stack + round(operand1 / operand2)
                }
            }
        }

        private fun round(value: Double): Double {
            val bd = BigDecimal(value)
            return bd.setScale(PRECEDENCE, RoundingMode.HALF_UP).toDouble()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            print("Enter a mathematical expression: ")
            val expression = readLine()
            if (expression != null) {
                val postfixExpression = shuntingYard(expression)
                // =-=-=-Using String Templates =-=-=-=-=-=-
                val message = "Postfix notation: $postfixExpression"
                println(message)
                val result = evaluatePostfix(postfixExpression)
                val resultMessage = "Result: $result"
                println(resultMessage)
            } else {
                println("Invalid input.")
            }
        }
    }
}

//=-=-=-Using Data class=-=-=-
data class MyStack<T>(val stack: Stack<T> = Stack()) {
    //=-=-=-=-=-=-Using Operator Overloading=-=-=-
    operator fun plus(item: T) {
        stack.push(item)
    }

    operator fun unaryMinus(): T {
        return stack.pop()
    }
}