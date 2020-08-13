import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
*
*                       二叉树的普通面试题
*
* */


public class TestDemo {

    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val){
            this.val = val;
        }
    }




    //1.二叉树的前序遍历
    public List<Integer> preorderTraversal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if (root == null){
            //如果是空树返回一个空的List result （不能返回null，返回null 是连盒子都没有，返回空的List是空的盒子）
            return result;
        }
        //访问根节点，把A加入到result中
        //add只返回元素
        result.add(root.val);

        //每次都会开辟一个新的栈帧
        //递归访问左子树
        //addAll是 返回的一个List集合 里面包含有元素
        result.addAll(preorderTraversal(root.left));
        //递归访问右子树
        result.addAll(preorderTraversal(root.right));
        return result;
    }





    //2.二叉树的中序遍历
    public List<Integer> inorderTraversal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if (root == null){
            return result;
        }

        result.addAll(inorderTraversal(root.left));
        result.add(root.val);
        result.addAll(inorderTraversal(root.right));
        return result;
    }





    //3.二叉树的后序遍历
    public List<Integer> postorderTraversal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if (root == null){
            return result;
        }

        result.addAll(postorderTraversal(root.left));
        result.addAll(postorderTraversal(root.right));
        result.add(root.val);
        return result;
    }





    //4.判断两棵树是否相同
    public boolean isSameTree(TreeNode p, TreeNode q) {
        //如果都是空树，认为是相同
        if (p == null && q == null){
            return true;
        }
        //如果两棵树 一颗为空 一颗非空 肯定不相同
        //已经在第一个if 里面判定了两者为空所以可以省略q！=null和p！=null
        //if((p == null && q != null) || (p != null && q == null)){}
        if (p == null || q == null){
            return false;
        }
        return p.val == q.val && isSameTree(p.left , q.left) && isSameTree(p.right, q.right);
    }





    //5.判断t是否是s 的子树
    /*public boolean isSameTree(TreeNode p, TreeNode q) {
        //如果都是空树，认为是相同
        if (p == null && q == null){
            return true;
        }
        //如果两棵树 一颗为空 一颗非空 肯定不相同
        //已经在第一个if 里面判定了两者为空所以可以省略q！=null和p！=null
        //if((p == null && q != null) || (p != null && q == null)){}
        if (p == null || q == null){
            return false;
        }
        return p.val == q.val && isSameTree(p.left , q.left) && isSameTree(p.right, q.right);
    }*/

    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null){
            return false;
        }
        //这道题得加上isSameTree 的方法先判断s和t 是否相等
        return isSameTree(s,t) || isSubtree(s.left,t) || isSubtree(s.right,t);
    }




    //6.二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        if (root.left == null && root.right == null){
            return 1;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + (leftDepth > rightDepth ? leftDepth : rightDepth);
    }





    //7.判断一颗二叉树是否是平衡二叉树
   /* public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        if (root.left == null && root.right == null){
            return 1;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + (leftDepth > rightDepth ? leftDepth : rightDepth);
    }*/
    public boolean isBalanced(TreeNode root) {
        if (root == null){
            return true;
        }
        if (root.left == null && root.right == null){
            return true;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return (leftDepth - rightDepth <= 1 && leftDepth - rightDepth >= -1)
                && isBalanced(root.left) && isBalanced(root.right);
    }



    //8.判断是否为对称二叉树
    public boolean isSymmetric(TreeNode root) {
        if (root == null){
            return true;
        }
        //判断root是否对称与根节点无关 转而判断 左右子树是否对称
        return isMirror(root.left,root.right);
    }
    //因为要判断左右子树是否对称 所以要单独写两个参数的方法
    public boolean isMirror(TreeNode t1,TreeNode t2){
        if (t1 == null && t2 == null){
            return true;
        }
        if (t1 == null || t2 == null){
            return false;
        }
        return (t1.val == t2.val) && isMirror(t1.left , t2.right) && isMirror(t1.right , t2.left);
    }



    //层序遍历(非递归借助队列遍历二叉树)
    public void levelOrder(TreeNode root){
        if (root == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode cur = queue.poll();
            System.out.print(cur.val + "");
            if (cur.left != null){
                queue.offer(cur.left);
            }
            if (cur.right != null){
                queue.offer(cur.right);
            }
        }
    }



    //判断一棵树是否为完全二叉树
    //思路：第一阶段要求任意节点必须同时具备左右子树，如果遇到只有右子树，则认为不是完全二叉树
    //如果遇到只有左子树或者没有子树的情况则进入第二阶段
    //第二阶段要求任意节点必须没有子树
    //遍历结束都没有找到反例那么这棵树就是完全二叉树
    public boolean isComplete(TreeNode root){
        if (root == null){
            return true;
        }
        //定义一个isFirstStep变量为true 表示处于第一阶段
        boolean isFirstStep = true;
        //针对这棵树 进行层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if (isFirstStep){
                //第一阶段，要求任意节点必须具备两个子树
                if (cur.left != null && cur.right != null){
                    //当前节点有两个子树，直接把子树用offer方法入队列，继续往后遍历
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }else if (cur.left == null && cur.right != null){ //对应笔记中的4号情况
                    //当前节点左子树为空，右子树非空，这个时候绝对不是完全二叉树 直接return false
                    return false;
                }else if (cur.left != null && cur.right == null){ //对应笔记中的2号情况
                    //当前节点左子树非空，右子树为空，此时进入第二阶段
                    //讲isFirstStep置为false，且讲cur.left入队列
                    isFirstStep = false;
                    queue.offer(cur.left);
                }else {
                    //当前节点左右子树都为空，进入第二阶段
                    //对应笔记里面的1号，3号情况
                    isFirstStep = false;
                }
            }else{
                if (cur.left != null || cur.right != null){  //对应笔记中的5号情况
                    //当前节点 左子树为空或者右子树为空 那么直接判定不是完全二叉树
                    return false;
                }
            }
        }
        //树遍历完，没有找到反例，最终认定其为完全二叉树
        return true;
    }

}
