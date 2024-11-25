/**
 * A console application that evaluates mathematical expressions entered by the user.
 * @author--> Tanuj Yadav
 * @date-->25/11/24
 */
package math;
import java.util.Scanner;
public class ExpressionEvaluatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(Constants.PROMPT_MESSAGE);
            String expression = scanner.nextLine();
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                int result = EvaluateExpression.evaluate(expression);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println(Constants.EXIT_MESSAGE);
    }
}
