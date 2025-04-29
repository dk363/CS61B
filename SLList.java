/** An SLList is a list of integers, which  hides the terrible truth
 *  of the nakedness within. */
public class SLList {
	/** Note: DO NOT MESS WITH first. 
	 *  但是如果改成private，除了main，谁都不可以访问，这样有什么用处？*/
	private IntNode first;

	public class IntNode {
		public int item;
		public IntNode next;

		public IntNode(int i, IntNode n) {
			item = i;
			next = n;
		}
	}

	public SLList(int x) {
		first = new IntNode(x, null);
	}

	/** Adds x to the front of the list. */
	public void addFirst(int x) {
		first = new IntNode(x, first);
	} 

	/** Return the first item in the list. */
	public int getFirst() {
		return first.item;
	}
}
