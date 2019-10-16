import java.util.*;

public class Huffman {

	    private Node root;
		
		class Node{
			private String data;
			private int freq;
			private Node l;
			private Node r;
			
			public Node(String data, int freq) {
				this.data = data;
				this.freq = freq;
			}
			
			public Node findMin(ArrayList<Node> array) {
				Node min = array.get(0); int minFreq = array.get(0).freq;
				for(int ix = 1; ix < array.size(); ix++) {
					if (array.get(ix).freq < minFreq) {
						min = array.get(ix);
						minFreq = min.freq;
					}
				}
				array.remove(min);
				return min;
			}
		}
		
		public String alphabet = "";
		public String encoded = "";
		
		
		public Huffman(String input) {
			String replace = input;
			ArrayList<Node> array = new ArrayList<Node>();
			while (replace.length() > 0) {
				String fChar = replace.substring(0, 1);
				int freq = 1;
				for (int dx = 1; dx < replace.length(); dx++) {
					if (replace.substring(dx, dx+1).equals(fChar)) {
						freq++;
					}
				}
				replace = replace.replaceAll(fChar, "");
				Node nn = new Node(fChar, freq);
				array.add(nn);
			}
			Node n1 = new Node("", -1), n2 = new Node("", -1); 
			for (; array.size() > 1; ) {
				n1 = n1.findMin(array); n2 = n2.findMin(array);
				Node parent = new Node("", n1.freq+n2.freq); 
				parent.l = n1; 
				parent.r = n2;
				array.add(parent);
			}
			if (array.get(0).data != "") {
				this.root = new Node("", array.get(0).freq);
				this.root.l = array.get(0);
			}
			else {
				this.root = array.get(0);
			}
			checkPosition(root, 1);
			for (int ix = 0; ix < input.length(); ix++) {
				encode(root, input.substring(ix, ix+1), "");
			}
		}
		
		public void checkPosition(Node root, int pos) {
			if (root == null) {
				return;
			}
			if (root.l == null && root.r == null) {
				this.alphabet += (pos + " " + root.data + " ");
			}
			else {
				checkPosition(root.l, pos*2);
				checkPosition(root.r, (pos*2)+1);
			}
		}
		
		public void encode(Node node, String str, String text) {
			if (node == null) {
				return;
			}

			if (node.r == null && node.l == null && node.data.equals(str)) {
				this.encoded = this.encoded + text;
			}
			
			encode(node.l, str, text+"0");
			encode(node.r, str, text+"1");
		}
}	

