import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class ExpressionSimuator {
    // ---------------- MAIN ---------------------------------------------//
    // Steps : Input → Expression → Postfix → Result → BST → Traversals → Hash Tables
    

    public static void main(String[] args) {

        try {

            // ---------------- Read Input ----------------
            String exp;
            int size;

            File file = new File("input.txt");

            if (!file.exists()) {
                System.out.println("Error: input.txt file not found.");
                return;
            }
            //3+2 32+ 

            try (Scanner in = new Scanner(file)) {

                if (!in.hasNextLine()) {
                    System.out.println("Error: expression is missing.");
                    return;
                }

                exp = in.nextLine();

                if (!in.hasNextLine()) {
                    System.out.println("Error: Hash table size is missing.");
                    return;
                }

                size = Integer.parseInt(in.nextLine().trim());

                if (size <= 0) {
                    System.out.println("Error: Hash table size must be greater than 0.");
                    return;
                }
            }

            // ---------------- Output File ----------------
            try (PrintStream out = new PrintStream(new File("output.txt"))) {
                System.setOut(out); // redirect standard output to output.txt
                System.out.println("Input: " + exp);
                System.out.println("Hash Table Size: " + size);

                // ---------------- Validation ----------------
                try {
                    ExpressionUtils.validateParentheses(exp);
                } catch (IllegalArgumentException e) {
                    System.out.println("Syntax Error: " + e.getMessage());
                    return;
                }
                
                Queue<String> tokens = ExpressionUtils.tokenize(exp);  // tokenizing the expression into a queue of tokens (numbers and operators)
                System.out.println("\nQueue: " + formatQueue(tokens));  // formating display the tokenized queue

                Queue<String> postfix = ExpressionUtils.infixToPostfix(tokens); // converting the infix expression (tokenized queue) to postfix notation using the Shunting Yard algorithm
                System.out.println("Postfix: " + formatQueue(postfix)); // formating display the postfix expression

                double result = ExpressionUtils.evaluatePostfix(  // evaluating the postfix expression to get the final result of the expression
                        ExpressionUtils.copyQueue(postfix)); // we create a copy of the postfix queue to preserve the original queue for later use (BST construction)

                System.out.println("Result: " + result); // displaying the final result of the expression

                // ---------------- BST ----------------
                Stack<TreeNode> stack = new Stack<>(); // stack to help construct the BST from the postfix expression
                Queue<String> postfixCopy = ExpressionUtils.copyQueue(postfix);  // we create another copy of the postfix queue for BST construction to preserve the original queue for later use (hash table insertion)

                while (!postfixCopy.isEmpty()) {   

                    String token = postfixCopy.deQueue();   // we process each token in the postfix expression to construct the BST. If the token is a number, we create a new TreeNode and push it onto the stack. If the token is an operator, we pop the top two nodes from the stack, make them the left and right children of a new TreeNode created with the operator, and then push this new node back onto the stack. This way, we build the BST according to the structure of the expression.
                    TreeNode node = new TreeNode(token); // we create a new TreeNode for the current token

                    if (ExpressionUtils.isNumber(token)) { // if the token is a number, we simply push it onto the stack as a leaf node
                        stack.push(node);
                    } else { // if the token is an operator 

                        if (stack.size() < 2) { // if there are not enough operands on the stack for the operator, it means the expression is invalid for BST construction
                            throw new IllegalArgumentException("Invalid expression for BST construction");
                        }
                        // if the token is an operator, we pop the top two nodes from the stack to be the right and left children of the operator node, and then push this new node back onto the stack
                        node.right = stack.pop(); 
                        node.left = stack.pop();
                        stack.push(node); // we push the new operator node back onto the stack, which now represents a subtree of the BST.
                    }
                }

                TreeNode root1 = stack.pop(); 

                BST bst = new BST(); 

                System.out.println("\nBST Traversals:");
                System.out.print("Preorder: ");
                bst.preorder(root1);
                System.out.println();

                System.out.print("Inorder: ");
                bst.inorder(root1);
                System.out.println();

                System.out.print("Postorder: ");
                bst.postorder(root1);
                System.out.println();

                // ---------------- Hash Tables ----------------
                LinearProbing linear = new LinearProbing(size);
                QuadraticProbing quadratic = new QuadraticProbing(size);
                DoubleHashing doubleHash = new DoubleHashing(size);
                ChainingHashTable chaining = new ChainingHashTable(size);

                Queue<String> tempHash = ExpressionUtils.tokenize(exp);

                while (!tempHash.isEmpty()) {

                    String token = tempHash.deQueue();

                    if (ExpressionUtils.isNumber(token)) {
                        int value = Integer.parseInt(token);
                        linear.insert(value);
                        quadratic.insert(value);
                        doubleHash.insert(value);
                        chaining.insert(value);
                    }
                }

                System.out.println("\n--- Hash Tables ---");

                System.out.println("\nLinear Probing:");
                linear.display();

                System.out.println("\nQuadratic Probing:");
                quadratic.display();

                System.out.println("\nDouble Hashing:");
                doubleHash.display();

                System.out.println("\nSeparate Chaining:");
                chaining.display();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (ArithmeticException e) {
            System.out.println("Math Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected Error occurred: " + e.getMessage());
        }
    }

    // ---------------- FORMAT HELPER ----------------

    public static String formatQueue(Queue<String> q) {

        Queue<String> temp = new Queue<>();
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        while (!q.isEmpty()) {
            String x = q.deQueue();

            sb.append(x);
            temp.enQueue(x);

            if (!q.isEmpty()) {
                sb.append(", ");
            }
        }

        sb.append("]");

        // restore queue
        while (!temp.isEmpty()) {
            q.enQueue(temp.deQueue());
        }

        return sb.toString();
    }
}