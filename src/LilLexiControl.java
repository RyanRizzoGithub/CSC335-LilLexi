

/**
 * Controller
 */
public class LilLexiControl {	
	private String currFont;
	private String currColor;
	private String currSideMargin;
	private String currEdgeMargin;
	private int currSize;
	private LilLexiDoc currentDoc;

	/**
	 * LilLexiControl
	 */
	public LilLexiControl( LilLexiDoc doc ) {
		this.currentDoc = doc;
		currFont = currentDoc.getCurrFont();
		currColor = currentDoc.getCurrColor();
		currSize = currentDoc.getCurrSize();
		currSideMargin = currentDoc.getCurrSideMargin();
		currEdgeMargin = currentDoc.getCurrEdgeMargin();
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
	void undo() {
		currentDoc.undo();
	}
	void redo() {
		currentDoc.redo();
	}
	
	Glyph getChar() {
		return currentDoc.getGlyphs().get(0);
	}
	
	void setIndex(int i) {
		currentDoc.setIndex(i);
	}
	void setCurrSize(int size) {
		currSize = size;
	}
	void setCurrFont(String font) {
		currFont = font;
	}
	void setCurrColor(String color) {
		currColor = color;
	}
	void setCurrSideMargin(String margin) {
		currSideMargin = margin;
	}
	void setCurrEdgeMargin(String margin) {
		currEdgeMargin = margin;
	}
	int getCurrSize() {
		return currSize;
	}
	String getCurrFont() {
		return currFont;
	}
	String getCurrColor() {
		return currColor;
	}
	String getCurrSideMargin() {
		return currSideMargin;
	}
	String getCurrEdgeMargin() {
		return currEdgeMargin;
	}
	int getCurrIndex() {
		return currentDoc.getCurrIndex();
	}

	/**
	 * quitEditor  user quits
	 */
	void  quitEditor() { 
		System.exit(0); 
	}	
}






