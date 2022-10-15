/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiDoc.java
 * DATE:		10/13/22
 */

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * PURPOSE:
 * This class is responsible for housing all information about that current
 * state of the document
 * 
 * METHODS:
 * + LilLexiDoc():						Constructor
 * + setUI(LilLexiUI):					void
 * + add(char):							void
 * + remove(char):						void
 * + undo():							void
 * + redo():							void
 * + getUI():							LilLexiUI
 * + getCurrFont():						String
 * + getCurrcolor():					String
 * + getCurrSize():						String
 * + getCurrSizeMargin():				String
 * + getCurrEdgeMargin():				String
 * + getCurrIndex():					int
 * + getDepth():						int
 * + getRowHeight():					int
 * + getRows():							int
 * + getGlyphs():						List<Glyph>
 * + getImages():						String[][]
 * + getRects():						String[][]
 * + getLines():						String[][]
 * + getCircles():						String[][]
 * + getTriangles():					String[][]
 * + getNumNewline():					int
 * + getRowWidth():						int
 * + getColumnWidth():					int
 * + setCurrFont(String):				void
 * + setCurrColor(String):				void
 * + setCurrSize(String):				void
 * + setCurrSizeMargin(String):			void
 * + setCurrEdgeMargin(String):			void
 * + setCurrIndex(int):					void
 * + setColumnWidth(int):				void
 * + setRowsWidth(int):					void
 * + setRowHeight(int):					void
 * + setDepth(int):						void
 * + setRowWidth(int):					void
 * + setRows(int):						void
 * + setPages(int):						void
 * + addImage(String[]):				void
 * + addRect(String[]):					void
 * + addLine(String[]):					void
 * + addCircle(String[]):				void
 * + addTriangle(String[]):				void
 * + addNewline():						void
 * + removeNewline():					void
 */
public class LilLexiDoc {
	private LilLexiUI ui;
	private List<Glyph> glyphs;
	private HashMap<Integer, Character> undoMemo;
	private HashMap<Integer, Character> redoMemo;
	private Stack<Integer> undo;
	private Stack<String> undoShape;
	private Stack<String[]> redoShape;
	private String currFont;
	private String currColor;
	private String currSideMargin;
	private String currEdgeMargin;
	private String backgroundColor;
	private int currSize;
	private int index;
	private int depth;
	private int columnWidth;
	private int rowHeight;
	private int rows;
	private int pages;
	private int rowsWidth;
	private int numNewline;
	private int[] rowWidth;
	private String[][] images;
	private String[][] rects;
	private String[][] lines;
	private String[][] circles;
	private String[][] triangles;
	
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function creates a new instance of LilLexiDoc
	 */
	public LilLexiDoc() {
		glyphs = new ArrayList<Glyph>();
		glyphs.add(new Glyph('|'));

		undoMemo = new HashMap<Integer, Character>();
		redoMemo = new HashMap<Integer, Character>();
		undo = new Stack<Integer>();
		undoShape = new Stack<String>();
		index = 0;
		currSideMargin = "1/2''";
		currEdgeMargin = "1/2''";
		currFont = "Courier";
		currColor = "Black";
		backgroundColor = "White";
		currSize = 12;
		numNewline = 0;
		depth = 0;		
		columnWidth = -1;
		rowHeight = -1;
		rows = 0;
		rowsWidth = 0;
		pages = 2;
		
		redoShape = new Stack<String[]>();
		
		rowWidth = new int[1000];
		for (int i=0; i<1000; i++) {
			rowWidth[i] = -1;
		}
		
		images = new String[100][5];
		rects = new String[100][5];
		lines = new String[100][4];
		circles = new String[100][4];
		triangles = new String[100][6];
		for (int i=0; i<100; i++) {
			String[] tempImage = new String[3];
			tempImage[0] = "";
			tempImage[1] = "";
			tempImage[2] = "";
			String[] tempRect = new String[5];
			tempRect[0] = "";
			tempRect[1] = "";
			tempRect[2] = "";
			tempRect[3] = "";
			tempRect[4] = "";
			String[] tempLineCircle = new String[4];
			tempLineCircle[0] = "";
			tempLineCircle[1] = "";
			tempLineCircle[2] = "";
			tempLineCircle[3] = "";
			String[]  tempTriangle = new String[6];
			tempTriangle[0] = "";
			tempTriangle[1] = "";
			tempTriangle[2] = "";
			tempTriangle[3] = "";
			tempTriangle[4] = "";
			tempTriangle[5] = "";
			images[i] = tempImage;
			rects[i] = tempRect;
			lines[i] = tempLineCircle;
			circles[i] = tempLineCircle;
			triangles[i] = tempTriangle;
		}
	}
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function creates a saved instance of LilLexiDoc
	 */
	public LilLexiDoc(List<Glyph >glyphs, int index, String sideMargin, String edgeMargin, String font,
			String color, String bgColor, int size, int newlines,int depth, int columnWidth,
			int rowHeight, int rows, int rowsWidth, int pages, int[] rowWidth, String[][] images,
			String[][] rects, String[][] lines, String[][] circles, String[][] triangles) {
		this.undoMemo = new HashMap<Integer, Character>();
		this.redoMemo = new HashMap<Integer, Character>();
		this.undo = new Stack<Integer>();
		this.undoShape = new Stack<String>();
		this.glyphs = glyphs;
		this.index = index;
		this.currSideMargin = sideMargin;
		this.currEdgeMargin = edgeMargin;
		this.currFont = font;
		this.currColor = color;
		this.backgroundColor = bgColor;
		this.currSize = size;
		this.numNewline = newlines;
		this.depth = depth;
		this.columnWidth = columnWidth;
		this.rowHeight = rowHeight;
		this.rows = rows;
		this.rowsWidth = rowsWidth;
		this.pages = pages;
		this.rowWidth = rowWidth;
		this.images = images;
		this.rects = rects;
		this.lines = lines;
		this.circles = circles;
		this.triangles = triangles;
		this.redoShape = new Stack<String[]>();
		
	}
	
	/* - - - - - - SET UI - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function lets us set the user interface for this document
	 * 
	 * @param ui, the LilLexiUI object being used
	 */
	public void setUI(LilLexiUI ui) {
		this.ui = ui;
	}

	/* - - - - - - ADD - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for adding a single Glyph
	 * to the document.
	 * 
	 * @param c, the character being added to the document
	 */
	public void add(char c) {
		// Add the character to the list
		if (glyphs.size() > 0) {
			glyphs.remove(index);
		}
		glyphs.add(index, new Glyph(c));
		glyphs.add(index + 1, new Glyph('|'));
		
		// Add the location & character to the undo tracker
		undoMemo.put(index, c);
		undo.add(index);
		
		// Update
		index++;
		ui.updateUI();
	}
	
	/* - - - - - - ADD UNDO SHAPE - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function adds a shape to the undo shape tracker
	 */
	public void addUndoShape(String action) {
		undoShape.add(action);
	}
	
	/* - - - - - - REMOVE - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function is responsible for removing a single Glyph
	 * to the document.
	 */
	public void remove() {
		// Check that there is at least one character
		if (index >= 1) {
			// Remove the character from the list
			glyphs.remove(index - 1);
		
			// Remove action from the undo tracker
			if (undo.size() > 0) {
				undoMemo.remove(index - 1);
				undo.pop();
			}
		
			// Update
			ui.updateUI();
			index--;
		}
	}
	
	/* - - - - - - UNDO - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for triggering the internal function
	 * for the undo button. Here we "remove" whatever most previous action
	 * the user made.
	 */
	public void undo() {
		// Remove the character from the document
		if (undo.size() > 0) {
			int toRemove = undo.pop();
			glyphs.remove(toRemove);
		
			// Change undo & redo trackers
			char c = undoMemo.get(index - 1);
			undoMemo.remove(index - 1);
			redoMemo.put(index - 1, c);
		
			// Update
			ui.updateUI();
			index--;
		}
	}
	
	/* - - - - - - REDO - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
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
			redoMemo.remove(index);
			undoMemo.put(index, c);
			undo.add(index);
		
			// Update
			ui.updateUI();
			index++;
		}
	}
	
	/* - - - - - - UNDO SHAPE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for triggering the internal function for the 
	 * undo shape button. Here we "remove" whatever shape was most previously
	 * created
	 */
	public void undoShape() {
		// check that there are shapes to remove
		if (undoShape.size() > 0) {
			String action = undoShape.pop();
			// Check if image
			if (action == "Image") {
				int i = 0;
				while (images[i][0] != "") {
					i++;
				}
				i--;
				
				// Get details
				String[] redoInfo = new String[4];
				redoInfo[0] = "Image";
				redoInfo[1] = images[i][0];
				redoInfo[2] = images[i][1];
				redoInfo[3] = images[i][2];
				redoShape.add(redoInfo);
				images[i][0] = "";
				images[i][1] = "";
				images[i][2] = "";
			}
			// Check if rectangle
			if (action == "Rectangle") {
				int i = 0;
				while (rects[i][0] != "") {
					i++;
				}
				i--;
				
				// Get details
				String[] redoInfo = new String[5];
				redoInfo[0] = "Rectangle";
				redoInfo[1] = rects[i][0];
				redoInfo[2] = rects[i][1];
				redoInfo[3] = rects[i][2];
				redoInfo[4] = rects[i][3];
				redoShape.add(redoInfo);
				rects[i][0] = "";
				rects[i][1] = "";
				rects[i][2] = "";
				rects[i][3] = "";
			}
			// Check if line
			if (action == "Line") {
				int i = 0;
				while (lines[i][0] != "") {
					i++;
				}
				i--;
				
				// Get details
				String[] redoInfo = new String[5];
				redoInfo[0] = "Line";
				redoInfo[1] = lines[i][0];
				redoInfo[2] = lines[i][1];
				redoInfo[3] = lines[i][2];
				redoInfo[4] = lines[i][3];
				redoShape.add(redoInfo);
				lines[i][0] = "";
				lines[i][1] = "";
				lines[i][2] = "";
				lines[i][3] = "";
			}
			// Check if circle
			if (action == "Circle") {
				int i = 0;
				while (circles[i][0] != "") {
					i++;
				}
				i--;
				
				// Get details
				String[] redoInfo = new String[5];
				redoInfo[0] = "Circle";
				redoInfo[1] = circles[i][0];
				redoInfo[2] = circles[i][1];
				redoInfo[3] = circles[i][2];
				redoInfo[4] = circles[i][3];
				redoShape.add(redoInfo);
				circles[i][0] = "";
				circles[i][1] = "";
				circles[i][2] = "";
				circles[i][3] = "";
			}
			// Check if triangle
			if (action == "Triangle") {
				int i = 0;
				while (triangles[i][0] != "") {
					i++;
				}
				i--;
				
				// Get details
				String[] redoInfo = new String[7];
				redoInfo[0] = "Triangle";
				redoInfo[1] = triangles[i][0];
				redoInfo[2] = triangles[i][1];
				redoInfo[3] = triangles[i][2];
				redoInfo[4] = triangles[i][3];
				redoInfo[5] = triangles[i][4];
				redoInfo[6] = triangles[i][5];
				redoShape.add(redoInfo);
				triangles[i][0] = "";
				triangles[i][1] = "";
				triangles[i][2] = "";
				triangles[i][3] = "";
				triangles[i][4] = "";
				triangles[i][5] = "";
			}
		}
	}
	
	/* - - - - - - REDO SHAPE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for triggering the internal function for the 
	 * redo shape button. Here we "replace" whatever shape was most previously
	 * undone.
	 */
	public void redoShape() {
		// Check that something has been undone
		if (redoShape.size() > 0) {
			String[] info = redoShape.pop();
			String action = info[0];
			// Check if image
			if (action == "Image") {
				addImage(Arrays.copyOfRange(info, 1, 4));
			}
			// Check if rectangle
			if (action == "Rectangle") {
				addRect(Arrays.copyOfRange(info, 1, 5));
			}
			// Check if line
			if (action == "Line") {
				addLine(Arrays.copyOfRange(info, 1, 5));
			}
			// Check if circle
			if (action == "Circle") {
				addCircle(Arrays.copyOfRange(info, 1, 5));
			}
			// Check if triangle
			if (action == "Triangle") {
				addTriangle(Arrays.copyOfRange(info, 1, 7));
			}
		}
	}
	
	/* - - - - - - GET UI - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current user interface
	 * object
	 * 
	 * @return ui, the LilLexiUI object
	 */
	public LilLexiUI getUI() {
		return this.ui;
	}
	
	/* - - - - - - GET CURR FONT - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current font being used
	 * 
	 * @return currFont, the String which represents the font being used
	 */
	public String getCurrFont() {
		return this.currFont;
	}
	
	/* - - - - - - GET CURR COLOR - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current color being used
	 * 
	 * @return currColor, the String which represents the color being used
	 */
	public String getCurrColor() {
		return this.currColor;
	}
	
	/* - - - - - - GET CURR SIZE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current font size being used
	 * 
	 * @return currColor, the int which represents the font size being used
	 */
	public int getCurrSize() {
		return this.currSize;
	}
	
	/* - - - - - - GET CURR SIDE MARGIN - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current size of the side margins
	 * 
	 * @return currSideMargin, the int which represents the size of the side margins
	 */
	public String getCurrSideMargin() {
		return this.currSideMargin;
	}
	
	/* - - - - - - GET CURR EDGE MARGIN - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current size of the edge margins
	 * 
	 * @return currEdgeMargin, the int which represents the size of the edge margins
	 */
	public String getCurrEdgeMargin() {
		return this.currEdgeMargin;
	}
	
	/* - - - - - - GET CURR INDEX - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the index of the cursor
	 * 
	 * @return index, the int which represents the location of the cursor
	 */
	public int getCurrIndex() {
		return this.index;
	}
	
	/* - - - - - - GET DEPTH - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the depth of the document
	 * 
	 * @return depth, the int which represents the depth
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/* - - - - - - GET ROW HEIGHT - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the row height of the document
	 * 
	 * @return rowHeight, the int which represents the height of each row
	 */
	public int getRowHeight() {
		return this.rowHeight;
	}
	
	/* - - - - - - GET ROWS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the number of rows on the document
	 * 
	 * @return rows, the int which represents the number of rows
	 */
	public int getRows() {
		return this.rows;
	}
	
	
	/* - - - - - - GET GLYPHS - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the list of Glyphs on the document
	 * 
	 * @return glyphs, the list of Glyph objects
	 */
	public List<Glyph> getGlyphs(){
		return glyphs;
	}
	
	/* - - - - - - GET IMAGES - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the array of images
	 * 
	 * @return images, String[][] containing data for each image
	 */
	public String[][] getImages(){
		return images;
	}
	
	/* - - - - - - GET RECTANGLES - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns a 2d array containing all images on the document
	 * 
	 * @return String[][], all rectangles
	 */
	public String[][] getRects(){
		return rects;
	}
	
	/* - - - - - - GET LINES - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns a 2d array containing all lines on the document
	 * 
	 * @return String[][], all lines
	 */
	public String[][] getLines(){
		return lines;
	}
	
	/* - - - - - - GET CIRCLES - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns a 2d array containing all circles on the document
	 * 
	 * @return String[][], all circles
	 */
	public String[][] getCircles(){
		return circles;
	}
	
	/* - - - - - - GET TRIANGLES - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * Returns a 2d array containing all triangles on the document
	 * 
	 * @return String[][], all triangles
	 */
	public String[][] getTriangles(){
		return triangles;
	}
	
	/* - - - - - - GET NUM NEWLINE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns the number of user entered newlines
	 * 
	 * @return in, number of newlines
	 */
	public int getNumNewline() {
		return numNewline;
	}
	
	/* - - - - - - GET ROW WIDTH - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns information about the number of characters in each row
	 * 
	 * @return rowWidth, an int[] 
	 */
	public int[] getRowWidth() {
		return rowWidth;
	}
	
	/* - - - - - - GET ROWW WIDTH - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns the width of each row
	 * 
	 * @return rowsWidth, int 
	 */
	public int getRowsWidth() {
		return this.rowsWidth;
	}
	
	/* - - - - - - GET COLUMN WIDTH - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns the width of each column
	 * 
	 * @return columnWidth, int
	 */
	public int getColumnWidth() {
		return this.columnWidth;
	}
	
	/* - - - - - - GET PAGES - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns the number of pages in the document
	 * 
	 * @return pages, int
	 */
	public int getPages() {
		return this.pages;
	}
	
	/* - - - - - - GET BACKGROUND COLOR - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Returns the current background color
	 * 
	 * @return backgroundColor, String
	 */
	public String getCurrBackgroundColor() {
		return this.backgroundColor;
	}
	
	
	/* - - - - - - SET CURR FONT - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current font value
	 * 
	 * @param font, the String which represents the font we want to use
	 */
	public void setCurrFont(String font) {
		this.currFont = font;
	}
	
	/* - - - - - - SET CURR COLOR - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function is responsible for setting the current color value
	 * 
	 * @param color, the String which represents the color we want to use
	 */
	public void setCurrColor(String color) {
		this.currColor = color;
	}
	
	/* - - - - - - SET CURR SIZE - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current font size value
	 * 
	 * @param size, the int which represents the font size we want to use
	 * 
	 */
	public void setCurrSize(int size) {
		this.currSize = size;
	}
	
	/* - - - - - - SET CURR SIDE MARGIN - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current size of the side margins
	 * 
	 * @param size, the string which represents the size of the size margins
	 */
	public void setCurrSideMargin(String size) {
		this.currSideMargin = size;
	}
	
	/* - - - - - - SET CURR EDGE MARGIN - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current size of the edge margins
	 * 
	 * @param size, the string which represents the size of the edge margins
	 */
	public void setCurrEdgeMargin(String size) {
		this.currEdgeMargin = size;
	}
	
	/* - - - - - - SET CURR INDEX - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the index (or the location
	 * of the cursor), for use in the document
	 * 
	 * @param i, the integer which represents the current index of the cursor
	 */
	public void setCurrIndex(int i) {
		if (i > glyphs.size()) {
			i = glyphs.size();
		} else {
			index = i;
		}
	}
	
	/* - - - - - - SET COLUMN WIDTH - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current column width
	 * 
	 * @param i, int to set as column width
	 */
	public void setColumnWidth(int i) {
		this.columnWidth = i;
	}
	
	/* - - - - - - SET ROWS WIDTH - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current rows width
	 * 
	 * @param i, int to set as rows width
	 */
	public void setRowsWidth(int i) {
		this.rowsWidth = i;
	}
	
	/* - - - - - - SET ROW HEIGHT - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current row height
	 * 
	 * @param i, int to set as row height
	 */
	public void setRowHeight(int i) {
		this.rowHeight = i;
	}
	
	/* - - - - - - SET DEPTH - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current depth
	 * 
	 * @param i, int to set as depth
	 */
	public void setDepth(int i) {
		this.depth = i * (pages);
	}
	
	/* - - - - - - SET ROW WIDTH - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current row width
	 * 
	 * @param row, the row we want to set the width for
	 * @param i, int to set as row width
	 */
	public void setRowWidth(int row, int i) {
		this.rowWidth[row] = i;
	}
	
	/* - - - - - - SET ROWS - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current number of rows
	 * 
	 * @param i, int the set as number of rows
	 */
	public void setRows(int i) {
		this.rows = i;
	}
	
	/* - - - - - - SET CURR BACKGROUND COLOR - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current background coloe
	 * 
	 * @param color, String to set as background color
	 */
	public void setCurrBackgroundColor(String color) {
		this.backgroundColor = color;
	}
	
	/* - - - - - - ADD IMAGE - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function is responsible for adding a image to the document
	 * 
	 * @param imageInfo, an array containing info for image placement
	 */
	public void addImage(String[] imageInfo) {
		int i = 0;
		while (images[i][0] != "") {
			i++;
		}
		images[i] = imageInfo;
		
	}
	
	/* - - - - - - ADD RECTANGLE - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for adding a rectangle to the document
	 * 
	 * @param rectInfo, an array containing info for rectangle placement
	 */
	public void addRect(String[] rectInfo) {
		int i = 0;
		while (rects[i][0] != "") {
			i++;
		}
		rects[i] = rectInfo;
		undoShape.add("Rectangle");
	}
	
	/* - - - - - - ADD LINE - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for adding a line to the document
	 * 
	 * @param lineInfo, an array containing info for line placement
	 */
	public void addLine(String[] lineInfo) {
		int i = 0;
		while (lines[i][0] != "") {
			i++;
		}
		lines[i] = lineInfo;
		undoShape.add("Line");
	}
	
	/* - - - - - - ADD CIRCLE - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for adding a circle to the document
	 * 
	 * @param circleInfo, an array containing info for circle placement
	 */
	public void addCircle(String[] circleInfo) {
		int i = 0;
		while (circles[i][0] != "") {
			i++;
		}
		circles[i] = circleInfo;
		undoShape.add("Circle");
	}
	
	/* - - - - - - ADD TRIANGLE - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for adding a triangle to the document
	 * 
	 * @param triangle Info, and array containing info for triangle placement
	 */
	public void addTriangle(String[] triangleInfo) {
		int i = 0;
		while (triangles[i][0] != "") {
			i++;
		}
		triangles[i] = triangleInfo;
		undoShape.add("Triangle");
	}
	
	/* - - - - - - ADD NEWLINE - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for increasing the newline counter
	 */
	public void addNewline() {
		numNewline++;
	}
	
	/* - - - - - - REMOVE NEWLINE - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for decreasing the newline counter
	 */
	public void removeNewline() {
		numNewline--;
	}
}


/**
 * PURPOSE:
 * This class is responsible for housing all information for each character on the
 * document
 * 
 * METHODS:
 * + Glyph():					Constructor
 * + getChar():					char
 * + getFont():					String
 * + getSize():					int
 * + getColor():				String
 * + setChar(char):				void
 * + setFont(String):			void
 * + setColor(String):			void			
 */
class Glyph 
{
	private char c;
	private String font;
	private String color;
	private int size;
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function creates a new instance of Glyph
	 */
	public Glyph(char c) {
		this.c = c;
	}
	
	/* - - - - - - GET CHARACTER - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function returns the character which represents this glyph
	 * 
	 * @return c, the char 
	 */
	public char getChar() {
		return c;
	}
	
	/* - - - - - - GET FONT - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function returns the current text font
	 * 
	 * @return font, the string
	 */
	public String getFont() {
		return font;
	}
	
	/* - - - - - - GET SIZE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function returns the current text size
	 * 
	 * @return size, the int
	 */
	public int getSize() {
		return size;
	}
	
	/* - - - - - - GET COLOR - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function returns the current text color
	 * 
	 * @return color, the string
	 */
	public String getColor() {
		return color;
	}

	/* - - - - - - SET CHAR - - - - - - - - - - - - - - - - - - - - - - - - 
	 * Ryan Rizzo
	 * This function sets the current character
	 * 
	 * @param c, char to add
	 */
	public void setChar(char c) {
		this.c = c;
	}
	
	/* - - - - - - SET FONT - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function sets this current font
	 * 
	 * @param font, font to add
	 */
	public void setFont(String font) {
		this.font = font;
	}
	
	/* - - - - - - SET COLOR - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function sets the current text color
	 * 
	 * @param color, color to add
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
