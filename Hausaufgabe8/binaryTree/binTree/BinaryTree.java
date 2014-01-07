package binTree;

public class BinaryTree {
	private BinaryTreeNode root;

	public BinaryTree() {
		root = null;
	}

	public boolean contains(int v) {
		return root != null && root.contains(v);
	}

	public void insert(int v) {
		if (root == null) {
			root = new BinaryTreeNode(v);
		} else {
			root.insert(v);
		}
	}
	
	public void remove(int x){
		if(contains(x)){
			if(root==null){
				return;
			}else{
				root.remove(x);
			}		
			
		}else{
			return;
		}
			
	}
	
	public static void main(String[] args){
	 BinaryTree test = new BinaryTree();
	 test.insert(5);
	 test.insert(7);
	 test.insert(19);
	 test.insert(10);
	 test.insert(3);
	 test.insert(4);
	 test.insert(16);
	 
	 test.remove(10);
	 
	}
}

