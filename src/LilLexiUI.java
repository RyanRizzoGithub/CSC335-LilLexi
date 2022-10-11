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
 * 
 */
public class LilLexiUI {
	private LilLexiDoc currentDoc;
	private LilLexiControl lexiControl;
	private Display display;
	private Shell shell;
	private Label statusLabel;
	private Canvas canvas;	
	private int columnWidth;
	private int rowWidth;
	private int rowHeight;
	private int sideMargins;
	private int edgeMargins;
	
	/*
	 * Constructor
	 */
	public LilLexiUI() {
		//---- create the window and the shell
		Display.setAppName("Lil Lexi");
		display = new Display();  
		shell = new Shell(display);
	    shell.setText("Lil Lexi");
		shell.setSize(900,910);
		shell.setLayout( new GridLayout());	
		columnWidth = -1;
		rowHeight = -1;
		rowWidth = -1;	
	}
		
	/**
	 * start the editor
	 * @throws InterruptedException 
	 * @throws FileNotFoundException 
	 */
	public void start() throws InterruptedException, FileNotFoundException
	{	
		//---- create widgets for the interface
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    Composite lowerComp = new Composite(shell, SWT.NO_FOCUS);
	    LilLexiSpellCheck spellCheck = new LilLexiSpellCheck(currentDoc);
	    
	    //---- canvas for the document
		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(800,800);

		canvas.addPaintListener(e -> {
			System.out.println("PaintListener");
			Rectangle rect = shell.getClientArea();
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); 
            e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
            e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 

    		Font font = new Font(display, currentDoc.getCurrFont(), currentDoc.getCurrSize(), SWT.NONE);
    		System.out.println(currentDoc.getCurrSize());
    		e.gc.setFont(font);
    		
    		String colorName = lexiControl.getCurrColor();
    		int colorCode = getColorCode(colorName);
    		Color color = display.getSystemColor(colorCode);
    		e.gc.setForeground(color);
    		
    		// Draw images
    		String[][] images = lexiControl.getImages();
    		for (int i=0; i<images.length; i++) {
    			if (images[i][0] != "") {
    				Image image = new Image(display, images[i][0]);
    				e.gc.drawImage(image, Integer.parseInt(images[i][1]), Integer.parseInt(images[i][2]));
    			}
    		}
    		
    		// Draw rectangles
    		String[][] rects = lexiControl.getRects();
    		for (int i=0; i<rects.length; i++) {
    			if (rects[i][0] != "") {
    				int rectX = Integer.parseInt(rects[i][0]);
    				int rectY = Integer.parseInt(rects[i][1]);
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
    				int lineY1 = Integer.parseInt(lines[i][1]);
    				int lineX2 = Integer.parseInt(lines[i][2]);
    				int lineY2 = Integer.parseInt(lines[i][3]);
    				e.gc.drawLine(lineX1, lineY1, lineX2, lineY2);
    			}
    		}
    		
    		// Draw circles
    		String[][] circles = lexiControl.getCircles();
    		for (int i=0; i<circles.length; i++) {
    			if (circles[i][0] != "" ) {
    				int circleX = Integer.parseInt(circles[i][0]);
    				int circleY = Integer.parseInt(circles[i][1]);
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
    				int triangleY1 = Integer.parseInt(triangles[i][1]);
    				int triangleX2 = Integer.parseInt(triangles[i][2]);
    				int triangleY2 = Integer.parseInt(triangles[i][3]);
    				int triangleX3 = Integer.parseInt(triangles[i][4]);
    				int triangleY3 = Integer.parseInt(triangles[i][5]);
    				e.gc.drawLine(triangleX1, triangleY1, triangleX2, triangleY2);
    				e.gc.drawLine(triangleX2, triangleY2, triangleX3, triangleY3);
    				e.gc.drawLine(triangleX3, triangleY3, triangleX1, triangleY1);				
    			}
    		}
    		

    		String sideMarginString = lexiControl.getCurrSideMargin();
    		sideMargins = getMarginCode(sideMarginString);
    		
    		String edgeMarginString = lexiControl.getCurrEdgeMargin();
    		edgeMargins = getMarginCode(edgeMarginString);
    		
    		int fontSize = lexiControl.getCurrSize();
    		columnWidth = fontSize - 4;
    		rowWidth = (800 - (2 * sideMargins)) / (fontSize - 4);
    		rowHeight = fontSize;
    		
    		List<Glyph> glyphs = currentDoc.getGlyphs();
    		int column = 0; int row = 0; int currRowWidth = 0;
    		for (Glyph g: glyphs) {
    			if (g.getChar() == 13) {
    				column = 0;
    			}
    			else {
    				 
    				
    				
    				if (column == 0) {
    					row += rowHeight;	
    					currentDoc.setRowWidth((row / rowHeight) - 1, currRowWidth);
    					System.out.println("row: " + row/rowHeight + " width: " + currRowWidth);
    					currRowWidth = 0;
    				}
    				e.gc.drawString("" + g.getChar(), column + sideMargins, row + edgeMargins); 
    				column = (column + columnWidth) % (rowWidth * columnWidth);
    				currRowWidth++;
    			}
    			System.out.println(g.getChar());
    		}
    		System.out.println();
		});	
		
		
		
        canvas.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
            	System.out.println("mouseDown in canvas");
            	System.out.println("x: " + e.x + " y: " + e.y);
            	
            	int i = 0;
            	int total = 1;
            	while (i < ((e.y - edgeMargins) / rowHeight)) {
            		total += currentDoc.getRowWidth()[i];
            		System.out.println("row: " + i + " width: " + currentDoc.getRowWidth()[i]);
            		i++;
            	}
            	
            	
            	
            	int index = ((e.x - sideMargins) / columnWidth) + (total);
            	if (index >= currentDoc.getGlyphs().size()) {
            		index = currentDoc.getGlyphs().size() - 1;
            	}
            	if (index < 0) {
            		index = 0;
            	}
            	System.out.println("total: " + total);
            	System.out.println(lexiControl.getNumNewline());
            	System.out.println("index: " + index);
            	List<Glyph> glyphs = currentDoc.getGlyphs();
            	glyphs.remove(currentDoc.getCurrIndex());
            	lexiControl.setIndex(index);
            	
            	
            		glyphs.add(index, new Glyph('|', currentDoc.getCurrFont(), currentDoc.getCurrSize(), currentDoc.getCurrColor()));

            	
            	canvas.redraw();
            	updateUI();
            } 
            public void mouseUp(MouseEvent e) {} 
            public void mouseDoubleClick(MouseEvent e) {} 
        });
        
        canvas.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		List<Glyph> glyphs = currentDoc.getGlyphs();
        		int index = currentDoc.getCurrIndex();
        		System.out.println("key " + e.character);
        		if (e.character == '\b') {
        			lexiControl.remove();
        			if (glyphs.get(index).getChar() == 13) {
        				lexiControl.removeNewline();
        			}
        		} else {
        			lexiControl.add(e.character);
        			if (e.character == 13) {
        				lexiControl.addNewline();
        			}
        		}
        	}
        	public void keyReleased(KeyEvent e) {}
        });
        
		Slider slider = new Slider (canvas, SWT.VERTICAL);
		Rectangle clientArea = canvas.getClientArea ();
		slider.setBounds (clientArea.width - 40, clientArea.y + 10, 32, clientArea.height);
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
			System.out.println ("Scroll detail -> " + string);
		});
		        
        //---- status label
        lowerComp.setLayout(new RowLayout());
        statusLabel = new Label(lowerComp, SWT.NONE);		

		FontData[] fD = statusLabel.getFont().getFontData();
		fD[0].setHeight(24);
		statusLabel.setFont( new Font(display,fD[0]));
		statusLabel.setText("Ready to edit!");
	    
	    
	    
        Menu systemMenu = Display.getDefault().getSystemMenu();
        MenuItem[] mi = systemMenu.getItems();
        mi[0].addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event){
            	System.out.println("About");
            }
        });
	    
        LilLexiSidebarUI sidebar = new LilLexiSidebarUI(display, shell, upperComp, lexiControl, canvas, spellCheck);
        sidebar.start();
        
        
        LilLexiMenuUI menu = new LilLexiMenuUI(display, shell, upperComp, lexiControl, canvas, spellCheck);
        menu.start();
        
        Menu menuBar = menu.getMenuBar();
	    shell.setMenuBar(menuBar);
	      
		//---- event loop
		shell.open();
		while( !shell.isDisposed())
			if(!display.readAndDispatch()) {		
			}
		display.dispose();
	} 

	/**
	 * updateUI
	 */
	public void updateUI()
	{
		System.out.println("updateUI");
		canvas.redraw();
	}
	
	/**
	 * setCurrentDoc
	 */
	public void setCurrentDoc(LilLexiDoc cd) { currentDoc = cd; }
	/**
	 * setController
	 */
	public void setController(LilLexiControl lc) { lexiControl = lc; }
	
	public int getColorCode(String color) {
		int colorAttribute = -1;
	    if (color.equals("Black")) {
	    	colorAttribute = SWT.COLOR_BLACK;
	    }
	    else if (color.equals("White")) {
	    	colorAttribute = SWT.COLOR_WHITE;
	    }
	    else if (color.equals("Red")) {
	    	colorAttribute = SWT.COLOR_RED;
	    }
	    else if (color.equals("Orange")) {
	    	colorAttribute = SWT.COLOR_TITLE_BACKGROUND;
	    }
	    else if (color.equals("Yellow")) {
	    	colorAttribute = SWT.COLOR_YELLOW;
	    }
	    else if (color.equals("Green")) {
	    	colorAttribute = SWT.COLOR_GREEN;
	    }
	    else if (color.equals("Blue")) {
	    	colorAttribute = SWT.COLOR_BLUE;
	    }
	    else if (color.equals("Purple")) {
	    	colorAttribute = SWT.COLOR_MAGENTA;
	    }
	    return colorAttribute;
	}
	
	public int getMarginCode(String margin) {
		if (margin.equals("0''")) {
			return 0;
		}
		else if (margin.equals("1/4''")) {
			return 24;
		}
		else if (margin.equals("1/2''")) {
			return 48;
		}
		else if (margin.equals("3/4''")) {
			return 72;
		}
		else if (margin.equals("1''")) {
			return 96;
		}
		else if (margin.equals("1 1/2''")) {
			return 144;
		}
		else {
			return 0;
		}
	}
	
	public int getRowWidth() {
		return rowWidth;
	}
}

