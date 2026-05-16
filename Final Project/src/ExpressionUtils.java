public class ExpressionUtils {

    // function check if the token is number(digit || dicimal) or not
    public static boolean isNumber(String s) {

        if (s == null || s.isEmpty()) { // empty string is not a number
            return false;
        }

        try {
            Double.parseDouble(s); // if it can be parsed as a double, it's a number
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // function to check if the parentheses in the expression are balanced or not
    public static void validateParentheses(String exp) {
        Stack<Integer> stack = new Stack<>();

        int i = 0;
        while (i < exp.length()) {
            char c = exp.charAt(i);

            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Syntax error: unexpected ')' at index " + i);
                }
                stack.pop();
            }
            i++;
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException(
                    "Syntax error: missing ')' for '(' at index " + stack.pop());
        }
    }

    // function to check if the char is an operator or not
    private static boolean isOperator(String token) {
        return token.equals("+") ||
                token.equals("-") ||
                token.equals("*") ||
                token.equals("/") ||
                token.equals("^") ||
                token.equals("%");
    }

    // function to create a copy of the queue (used to preserve original queue)
    public static Queue<String> copyQueue(Queue<String> original) {

        Queue<String> copy = new Queue<>();
        Queue<String> temp = new Queue<>();

        while (!original.isEmpty()) {
            String x = original.deQueue();
            copy.enQueue(x);
            temp.enQueue(x);
        }

        while (!temp.isEmpty()) {
            original.enQueue(temp.deQueue());
        }

        return copy;
    }

    // function that set the precedence (priority) of the operations
    public static int precedence(String op) {

        if (op.equals("^"))
            return 3;

        if (op.equals("*") || op.equals("/") || op.equals("%"))
            return 2;

        if (op.equals("+") || op.equals("-"))
            return 1;

        return -1;
    }

    // -----------------------------------------------------------------------------------------------------------------------------------

    // || step 1 : Parsing && Tokenize||
    public static Queue<String> tokenize(String exp) {

        Queue<String> queue = new Queue<>();
        StringBuilder num = new StringBuilder();

        for (int i = 0; i < exp.length(); i++) {

            char ch = exp.charAt(i);
            //
            if (Character.isWhitespace(ch)) {
                if (num.length() > 0) {
                    queue.enQueue(num.toString());
                    num.setLength(0);
                }
                continue;
            }
            // check if token is digit/decimal
            // 1.2 +2 2(exp)
            if (Character.isDigit(ch) || ch == '.') {
                num.append(ch);
                if (i + 1 < exp.length() && exp.charAt(i + 1) == '(') {
                    num.append('*'); // 3(exp)→ 3*(exp)
                }
                continue;
            }

            // handle negative numbers (UNARY MINUS)
            if (ch == '-') {
                // 1-2 -1*2 -1*(exp) 1 -2
                int prevIdx = i - 1;
                while (prevIdx >= 0 && Character.isWhitespace(exp.charAt(prevIdx))) {
                    prevIdx--;
                }

                char prev;
                if (prevIdx < 0) {
                    prev = 0;
                } else {
                    prev = exp.charAt(prevIdx);
                }
                //
                boolean isUnary = (prevIdx < 0 || prev == '(' || isOperator(String.valueOf(prev))); // 1*-3

                if (isUnary) {

                    // case 1: -.5 , -4 (negative sign)
                    if (i + 1 < exp.length() &&
                            (Character.isDigit(exp.charAt(i + 1)) || exp.charAt(i + 1) == '.')) {

                        num.append('-');
                        continue;
                    }

                    // case 2: -(expr) (miltiply by -1 ) (operation) -> -1*(exp)
                    if (i + 1 < exp.length() && exp.charAt(i + 1) == '(') {

                        queue.enQueue("-1");
                        queue.enQueue("*");
                        continue;
                    }
                }
            }

            if (num.length() > 0) {
                queue.enQueue(num.toString()); // enqueue the number as a string
                num.setLength(0);
            }

            String op = String.valueOf(ch);
            queue.enQueue(op); // enqueue the operator or parenthesis as a string
        }

        if (num.length() > 0) {
            queue.enQueue(num.toString()); // enqueue any remaining number at the end of the expression
        }

        return queue;
    }

    // -----------------------------------------------------------------------------------------------------------------------------------

    // || Step 2 : Infix -> Postfix ||
    public static Queue<String> infixToPostfix(Queue<String> input) {

        Stack<String> stack = new Stack<>();
        Queue<String> output = new Queue<>();

        while (!input.isEmpty()) {

            String token = input.deQueue();

            if (isNumber(token)) { // if it's a number, we add it to the output queue
                output.enQueue(token);
                continue;
            }

            if (token.equals("(")) { // if it's an opening parenthesis, we push it onto the stack
                stack.push(token);
                continue;
            }

            if (token.equals(")")) { // if it's a closing parenthesis, we pop from the stack to the output queue
                                     // until we find the corresponding opening parenthesis

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.enQueue(stack.pop()); // pop to output until '(' is found
                }

                if (!stack.isEmpty()) { // pop the opening parenthesis from the stack
                    stack.pop();
                }

                continue;
            }

            if (!isOperator(token)) {
                throw new IllegalArgumentException(
                        "Syntax error: unexpected token '" + token + "'");
            }

            while (!stack.isEmpty()
                    && !stack.peek().equals("(")
                    && precedence(stack.peek()) >= precedence(token)) {

                output.enQueue(stack.pop());
            }

            stack.push(token);
        }

        while (!stack.isEmpty()) {
            output.enQueue(stack.pop());
        }

        return output;
    }

    // -----------------------------------------------------------------------------------------------------------------------------------

    // || Step 3 : Evaluate Postfix ||
    public static double evaluatePostfix(Queue<String> postfix) {

        if (postfix.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        Stack<Double> stack = new Stack<>();

        while (!postfix.isEmpty()) {

            String token = postfix.deQueue();

            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
                continue;
            }

            if (stack.size() < 2) {
                throw new IllegalArgumentException("Invalid expression");
            }

            double b = stack.pop();
            double a = stack.pop();

            double result = 0;

            switch (token) {

                case "+":
                    result = a + b;
                    break;

                case "-":
                    result = a - b;
                    break;

                case "*":
                    result = a * b;
                    break;

                case "/":
                    if (Math.abs(b) < 1e-9) { // we check for division by zero (considering floating-point precision)
                        throw new ArithmeticException("Division by zero");
                    }
                    result = a / b;
                    break;

                case "%":
                    result = a % b;
                    break;

                case "^":
                    result = Math.pow(a, b);
                    break;
            }

            stack.push(result);
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return stack.pop();
    }
}