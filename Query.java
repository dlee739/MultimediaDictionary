import java.io.*;
import java.util.ArrayList;

public class Query {

	public static void main(String[] args) {
		BSTOrderedDictionary bst = new BSTOrderedDictionary(); // bst goes here

		/****************************************************************************
		 * 
		 * Reading an input file & Storing the contents into BSTOrdered Dictionary
		 * 
		 **************************************************************************/
		try {
			BufferedReader in = new BufferedReader(new FileReader(args[0])); // buffered reader
			String line1, line2; // hold all first lines and second lines, respectively 
			int type = 0; // type var
			while ((line1 = in.readLine()) != null) { 
				line1 = line1.toLowerCase(); 
				line2 = in.readLine();
				
				int i = line2.indexOf('.'); 
				String extension = line2.substring(i + 1); // separate the extension out
				
				// type assignment
				if (extension.equals("wav") || extension.equals("mid")) {
					type = 2;
				} else if (extension.equals("jpg") || extension.equals("gif")) {
					type = 3;
				} else if (extension.equals("html")) {
					type = 4;
				} else {
					type = 1;
				}
				
				bst.put(bst.getRoot(), line1, line2, type);
			}
			in.close(); // close buffered reader
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		/****************************************************************************
		 * 
		 * Commands are read here
		 * 
		 **************************************************************************/
		StringReader keyboard = new StringReader(); // keyboard for reading strings
		String line; // string variable for each lines read

		while (true) { // infinite loop until terminates
			line = keyboard.read("Enter next command:  "); // read in the command
			String[] parts = line.split(" "); // array of strings that stores bits of line
			String command = parts[0]; // separate the command and rest of parameters

			String k; // key
			String c; // content
			ArrayList<MultimediaItem> items; // multi-media item
			int t; // type
			int d; // number of nodes

			NodeData predNode; // NodeData for predecessor node
			NodeData succNode; // NodeData for successor node
			NodeData smallNode; // NodeData for smallest node
			NodeData largeNode; // NodeData for largest node

			if (command.equals("get") && parts.length == 2) { // get k

				k = parts[1]; // extract key				
				items = bst.get(bst.getRoot(), k);
				
				if (items == null) { // k not found in bst
					bst.put(bst.getRoot(), k, "", 0); // temporarily 
					
					predNode = bst.predecessor(bst.getRoot(), k);
					succNode = bst.successor(bst.getRoot(), k);

					System.out.println("The word " + k + " is not in the ordered dictionary.");
					// print preceding key
					System.out.print("Preceding word:  ");
					if (predNode != null) {
						System.out.print(predNode.getName());
					}
					System.out.println();
					// print following key
					System.out.print("Following word:  ");
					if (succNode != null) {
						System.out.print(succNode.getName());
					}
					System.out.println();
					
					try { // try catch clause for DictionaryException
						bst.remove(bst.getRoot(), k);
					} catch (DictionaryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else { // k is found in bst
					int len = items.size(); // length of items

					for (int i = 0; i < len; i++) {
						MultimediaItem curItem = items.get(i); // current item of items
						t = curItem.getType();
						try {
							switch (t) { // switch the behaviour depending on type
							case 1:
								System.out.println(curItem.getContent());
								break;
							case 2: // Play Sound
								SoundPlayer player = new SoundPlayer();
								player.play(curItem.getContent());
								break;
							case 3: // Display Image
								PictureViewer viewer = new PictureViewer();
							    viewer.show(curItem.getContent());
								break;
							case 4: // Display webpage
								ShowHTML displayer = new ShowHTML();
								displayer.show(curItem.getContent());
								break;
							}
						} catch (MultimediaException e) {
							System.out.println(e.getMessage());
						}
					}
				}

			} else if (command.equals("remove") && parts.length == 2) { // remove k

				k = parts[1]; // extract key
				items = bst.get(bst.getRoot(), k);

				if (items == null) { // k not found in bst
					System.out.println("No record in the ordered dictionary has key " + k + ".");
				} else {
					try {
						bst.remove(bst.getRoot(), k);
					} catch (DictionaryException e) {
						e.printStackTrace();
					}
				}

			} else if (command.equals("delete") && parts.length == 3) { // delete k t

				k = parts[1]; // extract key
				t = Integer.parseInt(parts[2]); // extract type
				items = bst.get(bst.getRoot(), k);		

				if (items == null) { // k not found in bst
					System.out.println("No record in the ordered dictionary has key " + k + ".");
				} else {
					try {
						bst.remove(bst.getRoot(), k, t);
					} catch (DictionaryException e) {
						e.printStackTrace();
					}
				}

			} else if (command.equals("add") && parts.length >= 4) { // add k c t
				
				k = parts[1]; // extract key
				String sentence = parts[2];// extract content
				for (int i = 3; i < parts.length - 1; i++) { // loop to add everything
															// in between in case of a sentence
					sentence = sentence + " " + parts[i];
				}
				t = Integer.parseInt(parts[parts.length - 1]); // extract type
				bst.put(bst.getRoot(), k, sentence, t);

			} else if (command.equals("next") && parts.length == 3) { // next k d

				k = parts[1]; // extract key
				d = Integer.parseInt(parts[2]); // extract d
				items = bst.get(bst.getRoot(), k);
				
				if (items == null) { // k not found in bst
					bst.put(bst.getRoot(), k, "", 0); // temporarily insert k	
				}
				succNode = bst.successor(bst.getRoot(), k); // get first successor of k
				
				if (items == null && succNode == null) {
					System.out.print("There are no keys smaller than or equal to " + k);
				} else {
					if (items != null) { // k found
						System.out.print(k + " ");
					}
					for (int i = 0; i < d; i++) { // iterate through successors of k
						if (succNode == null) {
							break;
						}
						k = succNode.getName(); // update k
						System.out.print(k + " ");
						succNode = bst.successor(bst.getRoot(), k);
					}
				}
				System.out.println();
				
				if (items == null) {
					try { // try catch clause for DictionaryException
						bst.remove(bst.getRoot(), k);
					} catch (DictionaryException e) {
						e.printStackTrace();
					}
				}

			} else if (command.equals("prev") && parts.length == 3) { // prev k d

				k = parts[1]; // extract key
				d = Integer.parseInt(parts[2]); // extract d
				items = bst.get(bst.getRoot(), k);
				
				if (items == null) { // k not found in bst
					bst.put(bst.getRoot(), k, "", 0); // temporarily insert k	
				}
				predNode = bst.predecessor(bst.getRoot(), k); // get first successor of k
				
				if (items == null && predNode == null) {
					System.out.print("There are no keys smaller than or equal to " + k);
				} else {
					if (items != null) { // k found
						System.out.print(k + " ");
					}
					for (int i = 0; i < d; i++) { // iterate through predecessors of k
						if (predNode == null) {
							break;
						}
						k = predNode.getName(); // update k
						System.out.print(k + " ");
						predNode = bst.predecessor(bst.getRoot(), k);
					}
				}
				System.out.println();
				
				if (items == null) {
					try { // try catch clause for DictionaryException
						bst.remove(bst.getRoot(), k);
					} catch (DictionaryException e) {
						e.printStackTrace();
					}
				}

			} else if (command.equals("first") && parts.length == 1) { // first

				smallNode = bst.smallest(bst.getRoot());
				if (smallNode == null) {
					System.out.println("The ordered dictionary is empty");
				} else {
					System.out.println(smallNode.getName());
				}

			} else if (command.equals("last") && parts.length == 1) { // last

				largeNode = bst.largest(bst.getRoot());
				if (largeNode == null) {
					System.out.println("The ordered dictionary is empty");
				} else {
					System.out.println(largeNode.getName());
				}

			} else if (command.equals("size") && parts.length == 1) { // size

				System.out.println("There are " + bst.getNumInternalNodes() + " keys in the ordered dictionary");

			} else if (command.equals("end") && parts.length == 1) { 

				break; // terminate the infinite while loop

			} else { // All other commands

				System.out.println("Invalid command");

			}
		}

	}

}
