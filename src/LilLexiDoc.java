/**
 * Lil Lexi Document Model
 * 
 */
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * LilLexiDoc
 */
public class LilLexiDoc 
{
	private LilLexiUI ui;
	private List<Glyph> glyphs;
	private HashMap<Integer, Character> undoMemo;
	private HashMap<Integer, Character> redoMemo;
	private int index;
	
	/**
	 * Constructor
	 */
	public LilLexiDoc() {
		glyphs = new ArrayList<Glyph>();
		undoMemo = new HashMap<Integer, Character>();
		redoMemo = new HashMap<Integer, Character>();
		index = 0;
	}
	
	/**
	 * setUI
	 */
	public void setUI(LilLexiUI ui) {this.ui = ui;}

	/*
	 * - - - - - - ADD - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible for adding a single Glyph
	 * to the document.
	 */
	public void add(char c) {
		// Add the character to the list
		glyphs.add(index, new Glyph(c));
		
		// Add the location & character to the undo tracker
		undoMemo.put(index, c);
		
		// Update
		ui.updateUI();
		index++;
	}
	/*
	 * - - - - - - REMOVE - - - - - - - - - - - - - - - - - - - - - - - 
	 * This function is responsible for removing a single Glyph
	 * to the document.
	 */
	public void remove() {
		// Check that there is at least one character
		if (index >= 1) {
			// Remove the character from the list
			glyphs.remove(index - 1);
			
			// Remove action from the undo tracker
			undoMemo.remove(index - 1);
			
			// Update
			ui.updateUI();
			index--;
		}
	}
	
	/*
	 * - - - - - - UNDO - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible for triggering the internal function
	 * for the undo button. Here we "remove" whatever most previous action
	 * the user made.
	 */
	public void undo() {
		// Check if there are undo actions to take
		if (undoMemo.get(index - 1) != null) {
			// Remove the character from the document
			glyphs.remove(index - 1);
		
			// Change undo & redo trackers
			char c = undoMemo.get(index - 1);
			undoMemo.remove(index - 1);
			redoMemo.put(index - 1, c);
			
			// Update
			ui.updateUI();
			index--;
		}
	}
	
	/*
	 * - - - - - - REDO - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible for triggering the internal function
	 * for the redo button. Here we "replace" whatever action was most
	 * previously undone.
	 */
	public void redo() {
		// Check if there are redo actions to take
		if (redoMemo.get(index) != null) {
			// Add the character back to the document
			char c = redoMemo.get(index);
			glyphs.add(index, new Glyph(c));
		
			// Change undo & redo trackers
			redoMemo.remove(redoMemo.size() - 1);
				undoMemo.put(index, c);
		
			// Update
			ui.updateUI();
			index++;
		}
	}
	
	/* - - - - - - SET INDEX - - - - - - - - - - - - - - - - - - - - - - - 
	 * This function is responsible for setting the index (or the location
	 * of the cursor), for use in the document
	 */
	public void setIndex(int i) {
		index = i;
	}
	
	/**
	 * gets
	 */
	public List<Glyph> getGlyphs(){return glyphs;}
}




/**
 * Glyph
 */
class Glyph 
{
	private char c;
	
	public Glyph(char c) {
		this.c = c;
	}

	public char getChar() {return c;}
	public void setChar(char c) {this.c = c;}
}





