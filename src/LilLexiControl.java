

/**
 * Controller
 */
public class LilLexiControl 
{
	private LilLexiDoc currentDoc;

	/**
	 * LilLexiControl
	 */
	public LilLexiControl( LilLexiDoc doc ) {
		this.currentDoc = doc;
	}
	
	/**
	 * selectCard  user selects a card
	 */
	void add( char c ) {	
		currentDoc.add(c);
	}	
	
	void remove() {
		currentDoc.remove();
	}
	
	Glyph getChar() {
		return currentDoc.getGlyphs().get(0);
	}
	
	void setIndex(int i) {
		currentDoc.setIndex(i);
	}

	/**
	 * quitEditor  user quits
	 */
	void  quitEditor() { 
		System.exit(0); 
	}	
}






