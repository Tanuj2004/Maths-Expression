/**
 * A utility class to evaluate mathematical expressions using the BODMAS rule.
 * @author--> Tanuj Yadav
 * @date-->25/11/24
 */
package math;
import java.util.Stack;
public class EvaluateExpression {
    /**
     * Evaluates a mathematical expression and returns the result.
     * @param expression the mathematical expression to evaluate
     * @return the result of the evaluated expression
     * @throws IllegalArgumentException if the expression is invalid
     * @throws ArithmeticException if there is a division by zero
     */
    public static int evaluate(String expression) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        boolean lastWasOperator = true;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (Character.isWhitespace(ch)) {
                continue;
            }
            if (Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num.append(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(Integer.parseInt(num.toString()));
                lastWasOperator = false;
            } else if (ch == Constants.OPEN_PARENTHESIS) {
                if (!lastWasOperator) {
                    operators.push(Constants.MULTIPLICATION);
                }
                operators.push(ch);
                lastWasOperator = true;
            } else if (ch == Constants.CLOSE_PARENTHESIS) {
                while (!operators.isEmpty() && operators.peek() != Constants.OPEN_PARENTHESIS) {
                    operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                }
                if (!operators.isEmpty() && operators.peek() == Constants.OPEN_PARENTHESIS) {
                    operators.pop();
                } else {
                    throw new IllegalArgumentException(Constants.ERROR_MESSAGE_UNBALANCED);
                }
                lastWasOperator = false;
            } else if (isOperator(ch)) {
                if (lastWasOperator) {
                    throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INVALID_EXPRESSION);
                }
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(ch);
                lastWasOperator = true;
            } else {
                throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INVALID);
            }
        }
        while (!operators.isEmpty()) {
            if (operators.peek() == Constants.OPEN_PARENTHESIS || operators.peek() == Constants.CLOSE_PARENTHESIS) {
                throw new IllegalArgumentException(Constants.ERROR_MESSAGE_UNBALANCED);
            }
            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
        }
        if (operands.size() != 1) {
            throw new IllegalArgumentException(Constants.ERROR_MESSAGE_INVALID_EXPRESSION);
        }
        return operands.pop();
    }
    /**
     * Checks if the character is a valid operator.
     * @param ch the character to check
     * @return true if the character is an operator, false otherwise
     */
    private static boolean isOperator(char ch) {
        return ch == Constants.ADDITION || ch == Constants.SUBTRACTION || ch == Constants.MULTIPLICATION || ch == Constants.DIVISION || ch == Constants.MODULUS;
    }
    /**
     * Returns the precedence of an operator.
     * @param op the operator
     * @return the precedence value
     */
    private static int precedence(char op) {
        switch (op) {
            case Constants.ADDITION:
            case Constants.SUBTRACTION:
                return 1;
            case Constants.MULTIPLICATION:
            case Constants.DIVISION:
            case Constants.MODULUS:
                return 2;
            default:
                return 0;
        }
    }
    /**
     * Applies the given operator to two operands.
     * @param op the operator
     * @param b the second operand
     * @param a the first operand
     * @return the result of the operation
     * @throws ArithmeticException if there is a division by zero
     */
    private static int applyOperator(char op, int b, int a) {
        switch (op) {
            case Constants.ADDITION:
                return a + b;
            case Constants.SUBTRACTION:
                return a - b;
            case Constants.MULTIPLICATION:
                return a * b;
            case Constants.DIVISION:
                if (b == 0) {
                    throw new ArithmeticException(Constants.ERROR_MESSAGE_DIVISION_BY_ZERO);
                }
                return a / b;
            case Constants.MODULUS:
                if (b == 0) {
                    throw new ArithmeticException(Constants.ERROR_MESSAGE_DIVISION_BY_ZERO);
                }
                return a % b;
        }
        return 0;
    }
}
