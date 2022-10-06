/**
 * Lil Lexi Document Model
 * 
 */
import java.util.List;
import java.util.ArrayList;

/**
 * LilLexiDoc
 */
public class LilLexiDoc 
{
	private LilLexiUI ui;
	private List<Glyph> glyphs;
	private int index;
	
	/**
	 * Constructor
	 */
	public LilLexiDoc() {
		glyphs = new ArrayList<Glyph>();
		index = 0;
	}
	
	/**
	 * setUI
	 */
	public void setUI(LilLexiUI ui) {this.ui = ui;}

	/**
	 * add a char
	 */
	public void add(char c) {
		glyphs.add(index, new Glyph(c));
		ui.updateUI();
		index++;
	}
	
	public void remove() {
		if (index >= 1) {
			glyphs.remove(index-1);
			ui.updateUI();
			index--;
		}
	}
	
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





