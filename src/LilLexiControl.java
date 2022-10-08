

/**
 * Controller
 */
public class LilLexiControl {	
	/*
	private String currFont;
	private String currColor;
	private String currSideMargin;
	private String currEdgeMargin;
	private int currSize;
	*/
	private LilLexiDoc currentDoc;

	/**
	 * LilLexiControl
	 */
	public LilLexiControl( LilLexiDoc doc ) {
		this.currentDoc = doc;
		/*
		currFont = currentDoc.getCurrFont();
		currColor = currentDoc.getCurrColor();
		currSize = currentDoc.getCurrSize();
		currSideMargin = currentDoc.getCurrSideMargin();
		currEdgeMargin = currentDoc.getCurrEdgeMargin();
		*/
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
		currentDoc.setCurrSize(size);
	}
	void setCurrFont(String font) {
		currentDoc.setCurrFont(font);
	}
	void setCurrColor(String color) {
		currentDoc.setCurrColor(color);
	}
	void setCurrSideMargin(String margin) {
		currentDoc.setCurrSideMargin(margin);
	}
	void setCurrEdgeMargin(String margin) {
		currentDoc.setCurrEdgeMargin(margin);
	}
	int getCurrSize() {
		return currentDoc.getCurrSize();
	}
	String getCurrFont() {
		return currentDoc.getCurrFont();
	}
	String getCurrColor() {
		return currentDoc.getCurrColor();
	}
	String getCurrSideMargin() {
		return currentDoc.getCurrSideMargin();
	}
	String getCurrEdgeMargin() {
		return currentDoc.getCurrEdgeMargin();
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






