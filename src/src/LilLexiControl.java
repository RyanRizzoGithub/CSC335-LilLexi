/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiControl.java
 * DATE:		10/13/22
 */

/**
 * PURPOSE:
 * This class is responsible for manipulating the state of the document
 * 
 * METHODS:
 * + LilLexiControl: 			Constructor
 * + add(char): 				void
 * + remove(): 					void
 * + undo(): 					void
 * + redo(): 					void
 * + getChar(): 				Glyph
 * + getImages: 				String[][]
 * + getRects: 					String[][]
 * + getLines: 					String[][]
 * + getCircles: 				String[][]
 * + getTriangles: 				String[][]
 * + getNumNewline: 			int
 * + setIndex(int): 			void
 * + setCurrSize(int): 			void
 * + setCurrFont(String): 		void
 * + setCurrColor(String): 		void
 * + setCurrSideMargin(String): void
 * + setCurrEdgeMargin(String):	void
 * + getCurrIndex():			int
 * + getCurrSize():				int
 * + getCurrFont():				String
 * + getCurrColor():			String
 * + getCurrSideMargin():		String
 * + getCurrEdgeMargin():		String
 * + addImage(String[]):		void
 * + addRect(String[]):			void
 * + addLine(String[]):			void
 * + addCircle(String[]):		void
 * + addTriangle(String[]):		void
 * + addNewline():				void
 * + removeNewline():			void
 * + quitEditor():				void
 */
public class LilLexiControl {	
	private LilLexiDoc currentDoc;

	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * This function creates a new instance of LilLexiControl
	 */
	public LilLexiControl( LilLexiDoc doc ) {
		this.currentDoc = doc;
	}
	
	/* - - - - - - ADD - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Adds a character to the document
	 * 
	 * @param c, the character to add
	 */
	void add( char c ) {	
		currentDoc.add(c);
	}	
	
	/* - - - - - - REMOVE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Removes a character from the document at index
	 */
	void remove() {
		currentDoc.remove();
	}
	
	/* - - - - - - UNDO - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Undoes the users most recent text input
	 */
	void undo() {
		currentDoc.undo();
	}
	
	/* - - - - - - REDO - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Redoes the users most recent undo
	 */
	void redo() {
		currentDoc.redo();
	}
	
	/* - - - - - - GET CHAR - - - - - - - - - - - - - - - - - - - - - - - -
	 * Used to get a character, but not remove it from the document
	 * 
	 * @return Glyph, the character being returned
	 */
	Glyph getChar() {
		return currentDoc.getGlyphs().get(0);
	}
	
	/* - - - - - - GET IMAGES - - - - - - - - - - - - - - - - - - - - - - - 
	 * Returns a 2d array containing all images on the document
	 * 
	 * @return String[][], all images
	 */
	public String[][] getImages(){
		return currentDoc.getImages();
	}
	
	/* - - - - - - GET RECTANGLES - - - - - - - - - - - - - - - - - - - - -
	 * Returns a 2d array containing all images on the document
	 * 
	 * @return String[][], all rectangles
	 */
	public String[][] getRects(){
		return currentDoc.getRects();
	}
	
	/* - - - - - - GET LINES - - - - - - - - - - - - - - - - - - - - - - - 
	 * Returns a 2d array containing all lines on the document
	 * 
	 * @return String[][], all lines
	 */
	public String[][] getLines(){
		return currentDoc.getLines();
	}
	
	/* - - - - - - GET CIRCLES - - - - - - - - - - - - - - - - - - - - - -
	 * Returns a 2d array containing all circles on the document
	 * 
	 * @return String[][], all circles
	 */
	public String[][] getCircles(){
		return currentDoc.getCircles();
	}
	
	/* - - - - - - GET TRIANGLES - - - - - - - - - - - - - - - - - - - - - 
	 * Returns a 2d array containing all triangles on the document
	 * 
	 * @return String[][], all triangles
	 */
	public String[][] getTriangles(){
		return currentDoc.getTriangles();
	}
	
	/* - - - - - - GET NUM NEWLINE - - - - - - - - - - - - - - - - - - - - 
	 * Returns the number of user entered newlines
	 * 
	 * @return in, number of newlines
	 */
	public int getNumNewline() {
		return currentDoc.getNumNewline();
	}
	
	/* - - - - - - SET CURR INDEX - - - - - - - - - - - - - - - - - - - - -
	 * Sets the index for the document
	 * 
	 * @param i, the number index is set to
	 */
	void setCurrIndex(int i) {
		currentDoc.setCurrIndex(i);
	}
	
	/* - - - - - - SET CURR SIZE - - - - - - - - - - - - - - - - - - - - - 
	 * Sets the text size for the document
	 * 
	 * @param i, the int text size
	 */
	void setCurrSize(int size) {
		currentDoc.setCurrSize(size);
	}
	
	/* - - - - - - SET CURR FONT - - - - - - - - - - - - - - - - - - - - -
	 * Sets the font for the document
	 * 
	 * @param font, String for font name
	 */
	void setCurrFont(String font) {
		currentDoc.setCurrFont(font);
	}
	
	/* - - - - - - SET CURR COLOR - - - - - - - - - - - - - - - - - - - - -
	 * Sets the color for the document
	 * 
	 * @param color, String for color
	 */
	void setCurrColor(String color) {
		currentDoc.setCurrColor(color);
	}
	
	/* - - - - - - SET CURR SIDE MARGIN - - - - - - - - - - - - - - - - - -
	 * Sets the side margin size for the document
	 * 
	 * @param margin, String for side margin size
	 */
	void setCurrSideMargin(String margin) {
		currentDoc.setCurrSideMargin(margin);
	}
	
	/* - - - - - - SET CURR EDGE MARGIN - - - - - - - - - - - - - - - - - -
	 * Sets the top and bottom margin size for the document
	 * 
	 * @param margin, String for top/bottom margin size
	 */
	void setCurrEdgeMargin(String margin) {
		currentDoc.setCurrEdgeMargin(margin);
	}
	
	/* - - - - - - GET CURR SIZE - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the text size for the document
	 * 
	 * @return int, text size
	 */
	int getCurrSize() {
		return currentDoc.getCurrSize();
	}
	
	/* - - - - - - GET CURR FONT - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the text font for the document
	 * 
	 * @return String, text font
	 */
	String getCurrFont() {
		return currentDoc.getCurrFont();
	}
	
	/* - - - - - - GET CURR COLOR - - - - - - - - - - - - - - - - - - - - - 
	 * Returns the text color for the document
	 * 
	 * @return String, text color
	 */
	String getCurrColor() {
		return currentDoc.getCurrColor();
	}
	
	/* - - - - - - GET CURR SIDE MARGIN - - - - - - - - - - - - - - - - - -
	 * Returns the side margin size
	 * 
	 * @return String, side margin size
	 */
	String getCurrSideMargin() {
		return currentDoc.getCurrSideMargin();
	}
	
	/* - - - - - - GET CURR EDGE MARGIN - - - - - - - - - - - - - - - - - -
	 * Returns the top and bottom margin size
	 * 
	 * @return String, top and bottom margin size
	 */
	String getCurrEdgeMargin() {
		return currentDoc.getCurrEdgeMargin();
	}
	
	/* - - - - - - GET CURR INDEX - - - - - - - - - - - - - - - - - - - - -
	 * Returns the current index from the document
	 * 
	 * @ return int, the index
	 */
	int getCurrIndex() {
		return currentDoc.getCurrIndex();
	}
	
	/* - - - - - - ADD IMAGE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Adds a image to the document
	 * 
	 * @param imageInfo, data required for image
	 */
	public void addImage(String[] imageInfo) {
		currentDoc.addImage(imageInfo);
	}
	
	/* - - - - - - ADD RECTANGLE - - - - - - - - - - - - - - - - - - - - - -
	 * Adds a rectangle to the document
	 * 
	 * @param rectInfo, data required for rectangle
	 */
	public void addRect(String[] rectInfo) {
		currentDoc.addRect(rectInfo);
	}
	
	/* - - - - - - ADD LINE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Adds a line to the document
	 * 
	 * @param lineInfo, data required for line
	 */
	public void addLine(String[] lineInfo) {
		currentDoc.addLine(lineInfo);
	}
	
	/* - - - - - - ADD CIRCLE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Adds a circle to the document
	 * 
	 * @param circleInfo, data required for circle
	 */
	public void addCircle(String[] circleInfo) {
		currentDoc.addCircle(circleInfo);
	}
	
	/* - - - - - - ADD TRIANGLE - - - - - - - - - - - - - - - - - - - - - - -
	 * Adds a triangle to the document
	 * 
	 * @param triangleInfo, data required for triangle
	 */
	public void addTriangle(String[] triangleInfo) {
		currentDoc.addTriangle(triangleInfo);
	}
	
	/* - - - - - - ADD NEWLINE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Increments the newline counter
	 */
	public void addNewline() {
		currentDoc.addNewline();
	}
	
	/* - - - - - - REMOVE NEWLINE - - - - - - - - - - - - - - - - - - - - - - 
	 * Decrements the newline counter
	 */
	public void removeNewline() {
		currentDoc.removeNewline();
	}

	/* - - - - - - QUIT EDITOR - - - - - - - - - - - - - - - - - - - - - - -
	 * Exits the editor
	 */
	void  quitEditor() { 
		System.exit(0); 
	}	
}






