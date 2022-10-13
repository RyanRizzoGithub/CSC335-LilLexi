/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiUI.java
 * DATE:		10/13/22
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.graphics.Font;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * PURPOSE:
 * This class is responsible for handling all of the user interface
 * for LilLexi
 * 
 * METHODS:
 * + LilLexiUI():						Constructor
 * + start(): 							void
 * + updateUI():						void
 * + setCurrentDoc(LilLexiDoc):			void
 * + setController(LilLexiController):	void
 * + getColorCode(String):				int
 * + getMarginCode(String):				int
 */
public class LilLexiUI {
	private LilLexiDoc currentDoc;
	private LilLexiControl lexiControl;
	private Display display;
	private Shell shell;
	private Label statusLabel;
	private Canvas canvas;	
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * This function creates a new instance of LilLexiUI
	 */
	public LilLexiUI() {
		// Create the window and shell
		Display.setAppName("Lil Lexi");
		display = new Display();  
		shell = new Shell(display);
	    shell.setText("Lil Lexi");
		shell.setSize(900,910);
		shell.setLayout( new GridLayout());	
	}
		
	/* - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the editor, and setting up all
	 * necessary user interface elements
	 * 
	 * @throws InterruptedException 
	 * @throws FileNotFoundException 
	 */
	public void start() throws InterruptedException, FileNotFoundException
	{	
		// Create widgets for the interface
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    Composite lowerComp = new Composite(shell, SWT.NO_FOCUS);
	    LilLexiSpellCheck spellCheck = new LilLexiSpellCheck(currentDoc);
	    
	    // Canvas for the document
		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(800,800);
		
		// Add a paint listener to update the document each time a change is made
		canvas.addPaintListener(e -> {
			System.out.println("PaintListener");
			
			// Find the bounds of the shell
			Rectangle rect = shell.getClientArea();
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); 
            e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
            e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
            
            // Get the font information from document
    		Font font = new Font(display, currentDoc.getCurrFont(), currentDoc.getCurrSize(), SWT.NONE);
    		e.gc.setFont(font);
    		
    		// Get the color information from the document
    		String colorName = lexiControl.getCurrColor();
    		int colorCode = getColorCode(colorName);
    		Color color = display.getSystemColor(colorCode);
    		if (colorCode == 12) {
    			color = new Color(display, 255, 127, 0);
    		} 
    		e.gc.setForeground(color);
    		
    		// Draw images
    		String[][] images = lexiControl.getImages();
    		for (int i=0; i<images.length; i++) {
    			if (images[i][0] != "") {
    				Image image = new Image(display, images[i][0]);
    				int imageX = Integer.parseInt(images[i][1]);
    				int imageY = Integer.parseInt(images[i][2]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				e.gc.drawImage(image, imageX, imageY);
    			}
    		}
    		
    		// Draw rectangles
    		String[][] rects = lexiControl.getRects();
    		for (int i=0; i<rects.length; i++) {
    			if (rects[i][0] != "") {
    				int rectX = Integer.parseInt(rects[i][0]);
    				int rectY = Integer.parseInt(rects[i][1]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				int rectWidth = Integer.parseInt(rects[i][2]);
    				int rectHeight = Integer.parseInt(rects[i][3]);
    				Rectangle rectangle = new Rectangle(rectX, rectY, rectWidth, rectHeight); 	
    				e.gc.drawRectangle(rectangle);
    			}
    		}
    		// Draw lines
    		String[][] lines = lexiControl.getLines();
    		for (int i=0; i<lines.length; i++) {
    			if (lines[i][0] != "") {
    				int lineX1 = Integer.parseInt(lines[i][0]);
    				int lineY1 = Integer.parseInt(lines[i][1]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				int lineX2 = Integer.parseInt(lines[i][2]);
    				int lineY2 = Integer.parseInt(lines[i][3]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				e.gc.drawLine(lineX1, lineY1, lineX2, lineY2);
    			}
    		}
    		for (int i=0; i<currentDoc.getPages(); i++) {
    			e.gc.drawLine(0, (800 * (i+1)) - (currentDoc.getDepth() * currentDoc.getRowHeight()), 800, (800* (i+1)) - (currentDoc.getDepth() * currentDoc.getRowHeight()));
    		}
    			
    		// Draw circles
    		String[][] circles = lexiControl.getCircles();
    		for (int i=0; i<circles.length; i++) {
    			if (circles[i][0] != "" ) {
    				int circleX = Integer.parseInt(circles[i][0]);
    				int circleY = Integer.parseInt(circles[i][1]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				int circleWidth = Integer.parseInt(circles[i][2]);
    				int circleHeight = Integer.parseInt(circles[i][3]);
    				e.gc.drawArc(circleX, circleY, circleWidth, circleHeight, 0, 360);
    			}
    		}
    		
    		// Draw triangles
    		String[][] triangles = lexiControl.getTriangles();
    		for (int i=0; i<triangles.length; i++) {
    			if (triangles[i][0] != "") {
    				int triangleX1 = Integer.parseInt(triangles[i][0]);
    				int triangleY1 = Integer.parseInt(triangles[i][1]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				int triangleX2 = Integer.parseInt(triangles[i][2]);
    				int triangleY2 = Integer.parseInt(triangles[i][3]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				int triangleX3 = Integer.parseInt(triangles[i][4]);
    				int triangleY3 = Integer.parseInt(triangles[i][5]) - (currentDoc.getDepth() * currentDoc.getRowHeight());
    				e.gc.drawLine(triangleX1, triangleY1, triangleX2, triangleY2);
    				e.gc.drawLine(triangleX2, triangleY2, triangleX3, triangleY3);
    				e.gc.drawLine(triangleX3, triangleY3, triangleX1, triangleY1);				
    			}
    		}
    		
    		// Convert side margin string to int
    		String sideMarginString = lexiControl.getCurrSideMargin();
    		int sideMargins = getMarginCode(sideMarginString);
    		
    		// Convert edge margin string to int
    		String edgeMarginString = lexiControl.getCurrEdgeMargin();
    		int edgeMargins = getMarginCode(edgeMarginString);
    		
    		// Setup variables for Glyph loop
    		int fontSize = lexiControl.getCurrSize();
    		currentDoc.setColumnWidth(fontSize - 4);
    		currentDoc.setRowsWidth((800 - (2 * sideMargins)) / (fontSize - 4));
    		currentDoc.setRowHeight(fontSize);
    		
    		List<Glyph> glyphs = currentDoc.getGlyphs();
    		int column = 0; int row = 0; int currRowWidth = 0;
    		
    		// Add all Glyphs to the document
    		
    		for (int i = 0; i < glyphs.size(); i++) {
    			// Check if enter key was pressed
    			if (glyphs.get(i).getChar() == 13) {
    				column = 0;
    			}
    			else {
    				// Check if we need to move down a line
    				if (column == 0) {
    					row += currentDoc.getRowHeight();	
    					currentDoc.setRowWidth((row / currentDoc.getRowHeight()) - 1, currRowWidth);
    					currRowWidth = 0;
    				}
    				// Add the glyph to the document
    				e.gc.drawString("" + glyphs.get(i).getChar(), column + sideMargins, (row + edgeMargins) - (currentDoc.getDepth() * currentDoc.getRowHeight())); 
    				
    				// Update values for loop
    				column = (column + currentDoc.getColumnWidth()) % (currentDoc.getRowsWidth() * currentDoc.getColumnWidth());
    				currRowWidth++;
    			}
    		}
    		currentDoc.setRows(row / currentDoc.getRowHeight());
		});	
		
		
		// Add listener for user mouse click interaction
        canvas.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
            	System.out.println("mouseDown in canvas");
            	System.out.println("x: " + e.x + " y: " + e.y);
            	
            	// Determine how many characters are on document
            	int i = 0;
            	int total = 1;
            	int edgeMargin = getMarginCode(currentDoc.getCurrEdgeMargin());
            	int sideMargin = getMarginCode(currentDoc.getCurrSideMargin());
            	while (i < ((e.y - edgeMargin) / currentDoc.getRowHeight())) {
            		total += currentDoc.getRowWidth()[i];
            		System.out.println("row: " + i + " width: " + currentDoc.getRowWidth()[i]);
            		i++;
            	}
            
            	// Calculate index in glyphs from x & y coordinate
            	int index = ((e.x - sideMargin) / currentDoc.getColumnWidth()) + (total) + (currentDoc.getDepth() * currentDoc.getRowsWidth());
            	
            	// Check if user clicked out of range
            	if (index >= currentDoc.getGlyphs().size()) {
            		index = currentDoc.getGlyphs().size() - 1;
            	}
            	if ((e.y - edgeMargin) / currentDoc.getRowHeight() > currentDoc.getRows()) {
        			index = currentDoc.getGlyphs().size() - 1;
        		}
            	if (index < 0) {
            		index = 0;
            	}

            	// Remove the previous cursor
            	List<Glyph> glyphs = currentDoc.getGlyphs();
            	glyphs.remove(currentDoc.getCurrIndex());
      
            	System.out.println("index: " + index + " prevIndex: " + currentDoc.getCurrIndex());
            	currentDoc.setCurrIndex(index);
            	
            	// Add the new cursor
            	glyphs.add(index, new Glyph('|', currentDoc.getCurrFont(), currentDoc.getCurrSize(), currentDoc.getCurrColor()));

            	// Update the canvas
            	canvas.redraw();
            	updateUI();
            } 
            public void mouseUp(MouseEvent e) {} 
            public void mouseDoubleClick(MouseEvent e) {} 
        });
        
        // Add listener for user key typing interaction
        canvas.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		// Get the glyphs from document
        		List<Glyph> glyphs = currentDoc.getGlyphs();
        		int index = currentDoc.getCurrIndex();
        		System.out.println("key " + e.character);
        		
        		// Check if deleting
        		if (e.character == '\b') {
        			if (index > 0) {
        				lexiControl.remove();
        				if (glyphs.get(index - 1).getChar() == 13) {
        					lexiControl.removeNewline();
        				}
        			}
        		// Add the character
        		} else {
        			lexiControl.add(e.character);
        			if (e.character == 13) {
        				lexiControl.addNewline();
        			}
        		}
        	}
        	public void keyReleased(KeyEvent e) {}
        });
        
        // Add a slider to the interface
		Slider slider = new Slider (canvas, SWT.VERTICAL);
		Rectangle clientArea = canvas.getClientArea ();
		slider.setBounds (clientArea.width - 40, clientArea.y + 10, 32, clientArea.height);
		
		// Add listener for the slider
		slider.addListener (SWT.Selection, event -> {
			String string = "SWT.NONE";
			switch (event.detail) {
				case SWT.DRAG: string = "SWT.DRAG"; break;
				case SWT.HOME: string = "SWT.HOME"; break;
				case SWT.END: string = "SWT.END"; break;
				case SWT.ARROW_DOWN: string = "SWT.ARROW_DOWN"; break;
				case SWT.ARROW_UP: string = "SWT.ARROW_UP"; break;
				case SWT.PAGE_DOWN: string = "SWT.PAGE_DOWN"; break;
				case SWT.PAGE_UP: string = "SWT.PAGE_UP"; break;
			}
			System.out.println ("Scroll detail -> " + string + "  " + slider.getSelection());
			
			// Update the document
			if (string.equals("SWT.DRAG")) {
				
				currentDoc.setDepth(slider.getSelection());
				updateUI();
			}
		});
		        
        // Status label
        lowerComp.setLayout(new RowLayout());
        statusLabel = new Label(lowerComp, SWT.NONE);		

		FontData[] fD = statusLabel.getFont().getFontData();
		fD[0].setHeight(24);
		statusLabel.setFont( new Font(display,fD[0]));
		statusLabel.setText("Ready to edit!");
	    
		// Setup menus
        Menu systemMenu = Display.getDefault().getSystemMenu();
        MenuItem[] mi = systemMenu.getItems();
        mi[0].addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event){
            	System.out.println("About");
            }
        });
	    
        // Setup the user interfaces side bar
        LilLexiSidebarUI sidebar = new LilLexiSidebarUI(display, shell, upperComp, lexiControl, canvas, spellCheck);
        sidebar.start();
        
        // Setup the user interface for the menu options
        LilLexiMenuUI menu = new LilLexiMenuUI(display, shell, upperComp, lexiControl, canvas, spellCheck);
        menu.start();
        
        // Set the menu bar
        Menu menuBar = menu.getMenuBar();
	    shell.setMenuBar(menuBar);
	      
		// Event loop
		shell.open();
		while( !shell.isDisposed())
			if(!display.readAndDispatch()) {		
			}
		display.dispose();
	} 

	/* - - - - - - UPDATE UI - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This functions purpose is to update the user interface any time changes are
	 * made to the document
	 */
	public void updateUI() {
		System.out.println("updateUI");
		canvas.redraw();
	}
	
	/* - - - - - - SET CURRENT DOC - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This method sets the current doc for the user interface
	 * 
	 * @param cd, the current LilLexiDoc
	 */
	public void setCurrentDoc(LilLexiDoc cd) {
		currentDoc = cd;
		}
	
	/* - - - - - - SET CONTROLLER - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This method sets the current control object for the user interface
	 * 
	 * @param lc, the current LilLexiControl
	 */
	public void setController(LilLexiControl lc) {
		lexiControl = lc;
		}
	
	/* - - - - - - GET COLOR CODE - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This methods purpose is to convert the string representation of the color 
	 * selected, to the appropriate SWT color constant
	 * 
	 * @param color, the String representation of the current text color
	 * @return colorAttribute, the SWT color constant
	 */
	public int getColorCode(String color) {
		int colorAttribute = -1;
		// Black
	    if (color.equals("Black")) {
	    	colorAttribute = SWT.COLOR_BLACK;
	    }
	    
	    // White
	    else if (color.equals("White")) {
	    	colorAttribute = SWT.COLOR_WHITE;
	    }
	    
	    // Red
	    else if (color.equals("Red")) {
	    	colorAttribute = SWT.COLOR_RED;
	    }
	    
	    // Orange
	    else if (color.equals("Orange")) {
	    	colorAttribute = 12;
	    }
	    
	    // Yellow
	    else if (color.equals("Yellow")) {
	    	colorAttribute = SWT.COLOR_YELLOW;
	    }
	    
	    // Green
	    else if (color.equals("Green")) {
	    	colorAttribute = SWT.COLOR_GREEN;
	    }
	    
	    // Blue
	    else if (color.equals("Blue")) {
	    	colorAttribute = SWT.COLOR_BLUE;
	    }
	    
	    // Purple
	    else if (color.equals("Purple")) {
	    	colorAttribute = SWT.COLOR_MAGENTA;
	    }
	    return colorAttribute;
	}
	
	/* - - - - - - GET MARGIN CODE - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This methods purpose is to convert the string representation of the margin size 
	 * selected, to the appropriate amount of pixels
	 * 
	 * @param color, the String representation of the margin size
	 * @return int, the number of pixels in margin
	 */
	public int getMarginCode(String margin) {
		// 0 inch
		if (margin.equals("0''")) {
			return 0;
		}
		
		// Quarter inch
		else if (margin.equals("1/4''")) {
			return 24;
		}
		
		// Half inch
		else if (margin.equals("1/2''")) {
			return 48;
		}
		
		// Three quarter inch
		else if (margin.equals("3/4''")) {
			return 72;
		}
		
		// One inch
		else if (margin.equals("1''")) {
			return 96;
		}
		
		// One an a half inch
		else if (margin.equals("1 1/2''")) {
			return 144;
		}
		
		// In case of error
		else {
			return 0;
		}
	}
}

