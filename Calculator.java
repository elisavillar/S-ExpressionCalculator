import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	/**************************
	 * Solve the expression
	 **************************/
	public static Integer calculate(String expression) {

		// Check if the expression has the correct brackets if not it will print an
		// error and terminate the application
		if (!checkExpression(expression)) {
			calculatorApp();
		}

		String[] input = correctExpression(expression);

		Stack<Integer> values = new Stack<Integer>();
		Stack<String> operations = new Stack<String>();

		for (int i = 0; i < input.length; i++) {

			// If is a number store the value in the value stack
			if (isNumeric(input[i])) {
				values.push(Integer.parseInt(input[i]));

			}

			// If is an operation or an opening bracket store the value in the operation
			// stack
			else if (!input[i].contains(")")) {
				operations.push(input[i]);

				// When find the closing bracket it will start to solve the operation until it
				// encounter an opening bracket.
				// It will use the first two elements of the value stack as expressions and the
				// first element of the operation stack as a function
			} else if (input[i].contains(")")) {

				while (values.size() > 1) {

					values.push(resolve(operations.peek(), values.pop(), values.pop()));

				}
				operations.pop();
				operations.pop();

			}

		}

		return values.pop();

	}

	/****************************************************************************
	 * Add spaces between the brackets and the operands and convert to upper case
	 ****************************************************************************/
	public static String[] correctExpression(String expression) {
		String exprModified = null;

		exprModified = expression.replaceAll("\\)", " )");
		exprModified = exprModified.replaceAll("\\(", "( ").toUpperCase();

		// Correct the expression for the use in the application and create an array
		// with each one of the elements of the expression
		String[] input = exprModified.split(" ");

		return input;
	}

	/*************************************************************************
	 * Check if the number of opening brackets is the same as the closing ones Check
	 * if the operation is a valid one
	 *************************************************************************/
	public static Boolean checkExpression(String expression) {

		String[] operations = new String[] { "ADD", "MULTIPLY", "DIVIDE", "EXPONENT", "(", ")" };

		Long countOpen = expression.chars().filter(ch -> ch == '(').count();
		Long countClose = expression.chars().filter(ch -> ch == ')').count();

		if (!countOpen.equals(countClose)) {
			System.out.println("\n--ERROR -- \nThe number of opening brackets should match the closing brakets\n");
			return false;
		} else if (countOpen == 0 && countClose == 0) {
			System.out.println("\n--ERROR -- \nThe expression should have opening and closing brakets\n");
			return false;
		}

		// Convert String Array to List
		List<String> list = Arrays.asList(operations);

		String[] input = correctExpression(expression);

		for (int i = 0; i < input.length; i++) {
			if (isNumeric(input[i])) {
				continue;
			} else if (!list.contains(input[i])) {
				System.out.println("\n--ERROR -- \nThe that operation is not valid\n");
				return false;
			}

		}
		return true;

	}

	/*********************************
	 * Resolve the expression entered
	 *********************************/
	public static int resolve(String function, int expr2, int expr1) {

		switch (function) {
		case "ADD":
			return expr1 + expr2;
		case "MINUS":
			return expr1 - expr2;
		case "MULTIPLY":
			return expr1 * expr2;
		case "DIVIDE":
			if (expr2 == 0) {
				System.out.print("Cannot divide by zero");
				return 0;
			}
			return expr1 / expr2;
		case "EXPONENT":
			return (int) Math.pow(expr1, expr2);
		default:
			return 0;
		}
	}

	/**********************************
	 * Check if the string is a number
	 **********************************/
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			int num = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**********************************
	 * Calculator application
	 **********************************/
	public static void calculatorApp() {
		Scanner console = new Scanner(System.in);
		String input = null;

		System.out.printf("%46s %n %45s %n %45s %n %s %n %s %n %s %n %s %s %n ", "***************************",
				"* S-EXPRESSION CALCULATOR *", "***************************",
				"Please enter an expression with the format (FUNCTION EXPR EXPR)",
				"The FUNCTION is one of add, minus, multiply, divide or exponent.",
				"Exactly one space should be used to separate each term.", "For example:",
				"(add 123 456) or (multiply (add 1 2) 3)");

		input = console.nextLine();

		while (!input.contains("Exit")) {

			System.out.println("Result = " + calculate(input));

			System.out.println("Type Exit to exit the calculator or enter another expression\n");

			input = console.nextLine();
		}

		System.out.println("Thank you for using our calculator. Have a good day!!");

		console.close();
	}

	/***************************************************************
	 * Main program where it can be tested with different scenarios
	 ***************************************************************/
	public static void main(String[] args) {

		System.out.println("(add 1 (add (add 2 2) 8 4)) = " + calculate("(add 1 (add (add 2 2) 8 4))"));
		System.out.println("(add 123 456) = " + calculate("(add 123 456)"));
		System.out.println("(multiply (add 1 2) 3) = " + calculate("(multiply (add 1 2) 3)"));
		System.out.println("(add 1 1 1) = " + calculate("(add 1 1 1)"));
		System.out.println("(add 0 (add 3 4)) = " + calculate("(add 0 (add 3 4))"));
		System.out.println("(add 3 (add (add 3 9 3) 3)) = " + calculate("(add 3 (add (add 3 9 3) 3))"));
		System.out.println("(multiply 1 1) = " + calculate("(multiply 1 1)"));
		System.out.println("(multiply 0 (multiply 3 4)) = " + calculate("(multiply 0 (multiply 3 4))"));
		System.out.println("(multiply 2 (multiply 3 4)) = " + calculate("(multiply 2 (multiply 3 4))"));
		System.out.println(
				"(multiply 3 (multiply (multiply 3 3) 3)) = " + calculate("(multiply 3 (multiply (multiply 3 3) 3))"));
		System.out.println("(add 1 (multiply 2 3)) = " + calculate("(add 1 (multiply 2 3))"));
		System.out.println("(multiply 2 (add (multiply 2 3) 8)) = " + calculate("(multiply 2 (add (multiply 2 3) 8))"));
		System.out.println("(multiply 10 -4 -2) = " + calculate("(multiply 10 -4 -2)"));

		calculatorApp();

	}

}