import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CalcTest : Spek({

    describe("shuntingYard function") {

        it("should convert a simple expression") {
            val infixExpression = "2 + 3 * 4"
            val expectedPostfix = "2 3 4 * +"
            assertEquals(expectedPostfix, Calculator.shuntingYard(infixExpression))
        }

        it("should handle complex expressions") {
            val infixExpression = "(2 + 3) * 4 - 5 / 2"
            val expectedPostfix = "2 3 + 4 * 5 2 / -"
            assertEquals(expectedPostfix, Calculator.shuntingYard(infixExpression))
        }

        it("should handle expressions with parentheses") {
            val infixExpression = "(2 + 3) * (4 - 1)"
            val expectedPostfix = "2 3 + 4 1 - *"
            assertEquals(expectedPostfix, Calculator.shuntingYard(infixExpression))
        }
        it("should skip unsupported operation char") {
            val infixExpression = "(2 ^ 3)"
            val expectedPostfix = "2 3"
            assertEquals(expectedPostfix, Calculator.shuntingYard(infixExpression))
        }


    }

    describe("evaluatePostfix function") {

        it("should calculate a simple expression    2 + 3 * 4") {
            val postfixExp = "2 3 4 * +"
            assertEquals(14.0, Calculator.evaluatePostfix(postfixExp))
        }

        it("should calculate complex expressions   (2 + 3) * (4 - 1)") {
            val postfixExp = "2 3 + 4 * 5 2 / -"
            assertEquals(17.5, Calculator.evaluatePostfix(postfixExp))
        }

        it("should calculate with precedence 4 ->calculate   2/3") {
            val postfixExp = "2 3 / "
            assertEquals(0.6667, Calculator.evaluatePostfix(postfixExp))
        }
        it("should calculate expressions with parentheses") {
            // "(2 + 3) * (4 - 1)"
            val postfixExp = "2 3 + 4 1 - *"
            assertEquals(15.0, Calculator.evaluatePostfix(postfixExp))
        }

    }

})

