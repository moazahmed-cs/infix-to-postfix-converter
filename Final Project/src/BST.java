class TreeNode {
    String data;
    TreeNode left, right;

    public TreeNode(String x) {
        this.data = x;
        left = right = null;
    }
}

public class BST {
    TreeNode root;

    public TreeNode insert(TreeNode root, String data) {

        if (data == null || data.isEmpty()) {
            return root;
        }

        if (root == null) {
            return new TreeNode(data);
        }

        int cmp = data.compareTo(root.data);

        if (cmp < 0) {
            root.left = insert(root.left, data);
        } else if (cmp > 0) {
            root.right = insert(root.right, data);
        } 
        // else {
        //     return root;
        // }

        return root;
    }

    public void inorder(TreeNode root) {

        if (root == null) {
            return;
        }

        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public void preorder(TreeNode root) {

        if (root == null) {
            return;
        }

        System.out.print(root.data + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public void postorder(TreeNode root) {

        if (root == null) {
            return;
        }

        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data + " ");
    }

}
