

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
	public String[][] getImages(){
		return currentDoc.getImages();
	}
	
	public String[][] getRects(){
		return currentDoc.getRects();
	}
	
	public String[][] getLines(){
		return currentDoc.getLines();
	}
	
	public String[][] getCircles(){
		return currentDoc.getCircles();
	}
	
	public String[][] getTriangles(){
		return currentDoc.getTriangles();
	}
	public int getNumNewline() {
		return currentDoc.getNumNewline();
	}
	
	void setIndex(int i) {
		currentDoc.setCurrIndex(i);
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
	public void addImage(String[] imageInfo) {
		currentDoc.addImage(imageInfo);
	}
	public void addRect(String[] rectInfo) {
		currentDoc.addRect(rectInfo);
	}
	public void addLine(String[] lineInfo) {
		currentDoc.addLine(lineInfo);
	}
	public void addCircle(String[] circleInfo) {
		currentDoc.addCircle(circleInfo);
	}
	public void addTriangle(String[] triangleInfo) {
		currentDoc.addTriangle(triangleInfo);
	}
	public void addNewline() {
		currentDoc.addNewline();
	}
	public void removeNewline() {
		currentDoc.removeNewline();
	}

	/**
	 * quitEditor  user quits
	 */
	void  quitEditor() { 
		System.exit(0); 
	}	
}






