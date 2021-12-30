import java.util.ArrayList;

public class NodeData {
	private String name; // key attribute for the data stored in a node
	private ArrayList<MultimediaItem> media; // list of multimedia objects
	
	// Constructor
	// creates a new NodeData object with the given key attribute and
	// an empty media list
	NodeData(String newName) {
		this.name = newName;
		media = new ArrayList<MultimediaItem>(); // WHAT IS AN EMPTY MEDIA LIST?????
	}
	
	// adds newItem to the media list of this object
	public void add(MultimediaItem newItem) {
		media.add(newItem);
	}
	
	// returns the name attribute of this object
	public String getName() {
		return this.name;
	}
	
	public ArrayList<MultimediaItem> getMedia() {
		return media;
	}
	
}
