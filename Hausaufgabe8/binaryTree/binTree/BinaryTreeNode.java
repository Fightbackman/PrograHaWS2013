package binTree;


public class BinaryTreeNode {
	private int value;
	private BinaryTreeNode left, right;

	public BinaryTreeNode(int v) {
		value = v;
		left = right = null;
	}

	public boolean contains(int v) {
		if (v < value) {
			return left != null && left.contains(v);
		}
		if (v > value) {
			return right != null && right.contains(v);
		}
		return true;

	}

	public void insert(int v) {
		if (v < value) {
			if (left == null) {
				left = new BinaryTreeNode(v);
			} else {
				left.insert(v);
			}
		} else if (v > value) {
			if (right == null) {
				right = new BinaryTreeNode(v);
			} else {
				right.insert(v);
			}
		}
	}

	public void remove(int x) {
		BinaryTreeNode parent = this;
		if (this.value != x) {
			if (x < this.value) {
				this.left.remove(x, parent, 0);
			} else if (x > this.value) {
				this.right.remove(x, parent, 1);
			}
		} else {

			if (this.right == null && this.left == null) {
				System.out
						.println("Zu löschendes Objekt ist das letzte Objekt im Tree. Programm wird terminieren.\n");
				System.exit(0);
			}
			if (this.right != null && this.left == null) {
				if (this.value == x) {
					int mini = this.min().value;
					this.right.remove(mini);
					this.value = mini;

				}
			}
			if (this.left != null) {
				if (this.value == x) {
					int maxi = this.max().value;
					this.left.remove(maxi);
					this.value = maxi;
				}
			}
		}
	}

	public void remove(int x, BinaryTreeNode parent_, int direction_) {
		System.out.println(x + parent_.toString() +  direction_ );
		BinaryTreeNode parent = parent_;
		int direction = direction_;
		if (this.value != x) {
			if (x < this.value) {
				parent=this;
				this.left.remove(x, parent, 0);
			} else if (x > this.value && this.right != null) {
				parent=this;
				this.right.remove(x, parent , 1);
			}
		} else {
			if (this.right == null) {
				if (direction == 0) {
					parent.left = null;
				} else if (direction == 1) {
					parent.right = null;
				}

			}
			if (this.right != null && this.left == null) {
				int mini = this.right.min().value;
				this.right.remove(mini, parent, direction);

				this.value = mini;
			}
			if (this.left != null) {
				int maxi = this.max().value;
				this.left.remove(maxi, parent, direction);
				this.value = maxi;
			}
		}
	}

	public BinaryTreeNode max() {
		if(this.right == null){
			return this;
		}
		return this.left.max();		
	}

	public BinaryTreeNode min() {
		if (this.left == null) {
			return this;
		}
		return this.left.min();
		
	}

}