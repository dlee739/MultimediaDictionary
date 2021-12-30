
public class MultimediaItem {
	private String content; // descriptor of the multimedia content.
							// This could be text, the name of an audio file, 
							// the name of an image file, or the name of an html document
	private int type; // the type of information represented by instance variable content
	/***************************************************
	 * 1: if content is text
	 * 2: if content is the name of an audio file
	 * 3: if content is the name of an image file
	 * 4: if content is the name of an html document.
	 **************************************************/
	
	// Constructor
	MultimediaItem(String newContent, int newType) {
		this.content = newContent;
		this.type = newType;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getType() {
		return this.type;
	}
}
