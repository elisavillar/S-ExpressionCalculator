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
			System.exit(0);
		}

		Stack<Integer> values = new Stack<Integer>();
		Stack<String> operations = new Stack<String>();

		// Correct the expression for the use in the application and create an array
		// with each one of the elements of the expression
		String[] input = correctExpression(expression).split(" ");

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
				while (!(operations.peek().contains("("))) {
					values.push(resolve(operations.pop(), values.pop(), values.pop()));
				}
				operations.pop();
			}

		}

		return values.pop();

	}

	/****************************************************************************
	 * Add spaces between the brackets and the operands and convert to upper case
	 ****************************************************************************/
	public static String correctExpression(String expression) {
		String exprModified = null;

		exprModified = expression.replaceAll("\\)", " )");
		exprModified = exprModified.replaceAll("\\(", "( ").toUpperCase();

		return exprModified;
	}

	/*************************************************************************
	 * Check if the number of opening brackets is the same as the closing ones
	 *************************************************************************/
	public static Boolean checkExpression(String expression) {

		Long countOpen = expression.chars().filter(ch -> ch == '(').count();
		Long countClose = expression.chars().filter(ch -> ch == ')').count();

		if (!countOpen.equals(countClose)) {
			System.out.println("Error -- The number of opening brackets should match the closing brakets");
			return false;
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

		System.out.println("        ***************************\n"
				+ "        * S-EXPRESSION CALCULATOR *\n"
				+ "        ***************************\n"
				+ "Please enter an expression with the format (FUNCTION EXPR EXPR)\n"
				+ "The FUNCTION is one of add, minus, multiply, divide or exponent.\n"
				+ "Exactly one space should be used to separate each term.\n" + "For example:\n" + "(add 123 456) or\n"
				+ "(multiply (add 1 2) 3)");

		input = console.nextLine();
		
		System.out.println("Result = " + calculate(input));

		System.out.println("Thank you for using our calculator. Have a good day!!");

		console.close();
	}

	/***************************************************************
	 * Main program where it can be tested with different scenarios
	 ***************************************************************/
	public static void main(String[] args) {

		System.out.println("(add 1 (add (add 2 2) 4)) = " + calculate("(add 1 (add (add 2 2) 4))"));
		System.out.println("(add 123 456) = " + calculate("(add 123 456)"));
		System.out.println("(multiply (add 1 2) 3) = " + calculate("(multiply (add 1 2) 3)"));
		System.out.println("(add 1 1) = " + calculate("(add 1 1)"));
		System.out.println("(add 0 (add 3 4)) = " + calculate("(add 0 (add 3 4))"));
		System.out.println("(add 3 (add (add 3 3) 3)) = " + calculate("(add 3 (add (add 3 3) 3))"));
		System.out.println("(multiply 1 1) = " + calculate("(multiply 1 1)"));
		System.out.println("(multiply 0 (multiply 3 4)) = " + calculate("(multiply 0 (multiply 3 4))"));
		System.out.println("(multiply 2 (multiply 3 4)) = " + calculate("(multiply 2 (multiply 3 4))"));
		System.out.println(
				"(multiply 3 (multiply (multiply 3 3) 3)) = " + calculate("(multiply 3 (multiply (multiply 3 3) 3))"));
		System.out.println("(add 1 (multiply 2 3)) = " + calculate("(add 1 (multiply 2 3))"));
		System.out.println("(multiply 2 (add (multiply 2 3) 8)) = " + calculate("(multiply 2 (add (multiply 2 3) 8))"));
		System.out.println("(multiply 10 -2) = " + calculate("(multiply 10 -2)"));
		
		calculatorApp();

	}

}
