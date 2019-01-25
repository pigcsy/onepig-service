package com.one.pig;

import java.util.ArrayList;
import java.util.Stack;

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {
    //利用两个栈的辅助空间分别存储奇数偶数层的节点，然后打印输出。或使用链表的辅助空间来实现，利用链表的反向迭实现逆序输出。
    public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(pRoot == null) {
			return res;
		}
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(pRoot);
        int level = 1;
        while (!s1.empty()||!s2.empty()) {
			if (level %2 != 0) {
				ArrayList<Integer> list = new ArrayList<>();
				while (!s1.empty()) {
					TreeNode node = s1.pop();
					if (node!= null) {
						list.add(node.val);
						s2.push(node.left);//因为偶数层，先右后左，所以要先放左子树，栈
						s2.push(node.right);
						
					}
				}
				  if (!list.isEmpty()) {
		                res.add(list);
		                level++;
		            }
			}
			else {
				ArrayList<Integer> list = new ArrayList<>();
				while (!s2.empty()) {
					TreeNode node = s2.pop();
					if (node!= null) {
						list.add(node.val);
						s1.push(node.right);
						s1.push(node.left);
						
					}
				}
				  if (!list.isEmpty()) {
		                res.add(list);
		                level++;
		            }
			}
		}
        return res;
    }
 
}
