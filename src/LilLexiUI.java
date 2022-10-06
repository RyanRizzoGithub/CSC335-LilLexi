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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import java.io.File;
import java.util.List;

/**
 * PURPOSE:
 * This class is responsible for handling all of the user interface
 * for LilLexi
 * 
 * METHODS:
 * 
 */
public class LilLexiUI
{
	private LilLexiDoc currentDoc;
	private LilLexiControl lexiControl;
	private Display display;
	private Shell shell;
	private Label statusLabel;
	private Canvas canvas;	
	private int glyphsLength;
	
	/*
	 * Constructor
	 */
	public LilLexiUI() 
	{
		//---- create the window and the shell
		Display.setAppName("Lil Lexi");
		display = new Display();  
		shell = new Shell(display);
	    shell.setText("Lil Lexi");
		shell.setSize(900,900);
		shell.setLayout( new GridLayout());	
		glyphsLength = 0;
	}
		
	/**
	 * start the editor
	 */
	public void start()
	{	
		//---- create widgets for the interface
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    Composite lowerComp = new Composite(shell, SWT.NO_FOCUS);
	    
	    //---- canvas for the document
		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(800,800);

		canvas.addPaintListener(e -> {
			System.out.println("PaintListener");
			Rectangle rect = shell.getClientArea();
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); 
            e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
            e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
    		Font font = new Font(display, currentDoc.getCurrFont(), currentDoc.getCurrSize(), SWT.BOLD );
    		e.gc.setFont(font);
    		//TODO
    		
    		String colorName = currentDoc.getCurrColor();
    		int colorCode = getColorCode(colorName);
    		Color color = display.getSystemColor(colorCode);
    		System.out.println(colorName + ", " + colorCode + ", " + color);
    		e.gc.setForeground(color);
    		
    		List<Glyph> glyphs = currentDoc.getGlyphs();
    		int column = 0; int row = 0;
    		for (Glyph g: glyphs)
    		{
    			e.gc.drawString("" + g.getChar(), column, row + 10);    
    			column = (column + 18) % (40*18);
    			if (column == 0) row += 32;
    			System.out.println(g.getChar());
    		}
    		glyphsLength = glyphs.size();
		});	
		
        canvas.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
            	System.out.println("mouseDown in canvas");
            	System.out.println("x: " + e.x + "y: " + e.y);
            	// TODO: add variation for font sizes
            	int index = (e.x/18) + (40 * (e.y/32));
            	System.out.println(index);
            	if (index > glyphsLength) {
            		index = glyphsLength;
            	}
            	lexiControl.setIndex(index);
            } 
            public void mouseUp(MouseEvent e) {} 
            public void mouseDoubleClick(MouseEvent e) {} 
        });
        
        canvas.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		System.out.println("key " + e.character);
        		if (e.character == '\b') {
        			lexiControl.remove();
        		} else {
        			lexiControl.add(e.character);
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
		
		//---- main menu
		Menu menuBar, fileMenu, insertMenu, helpMenu;
		MenuItem fileMenuHeader, insertMenuHeader, helpMenuHeader, fileExitItem, fileSaveItem, helpGetHelpItem;
		MenuItem insertImageItem, insertRectItem;

		menuBar = new Menu(shell, SWT.BAR);
		
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

	    fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileSaveItem.setText("Save");
	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("Exit");

		insertMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		insertMenuHeader.setText("Insert");
		insertMenu = new Menu(shell, SWT.DROP_DOWN);
		insertMenuHeader.setMenu(insertMenu);

	    insertImageItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertImageItem.setText("Image");
	    insertRectItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertRectItem.setText("Rectangle");

	    helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    helpMenuHeader.setText("Help");
	    helpMenu = new Menu(shell, SWT.DROP_DOWN);
	    helpMenuHeader.setMenu(helpMenu);

	    helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
	    helpGetHelpItem.setText("Get Help");
	    
	    // - - - - - Sidebar Interface - - - - - - - - - - //
	    
	    // Undo button
	    Button undo = new Button(upperComp, SWT.PUSH);
	    undo.setText("Undo");
	    undo.setBounds(810, 10, 75, 30);
	    
	    // Redo button
	    Button redo = new Button(upperComp, SWT.PUSH);
	    redo.setText("Redo");
	    redo.setBounds(810, 40, 75, 30);
	    
	    // Size combo
	    Combo sizeCombo = new Combo(upperComp, SWT.PUSH);
	    sizeCombo.setText("Size");
	    String[] sizes = new String[12];
	    sizes[0]  = "6";
	    sizes[1]  = "8";
	    sizes[2]  = "10";
	    sizes[3]  = "12";
	    sizes[4]  = "14";
	    sizes[5]  = "16";
	    sizes[6]  = "18";
	    sizes[7]  = "20";
	    sizes[8]  = "22";
	    sizes[9]  = "24";
	    sizes[10] = "26";
	    sizes[11] = "28";
	    sizeCombo.setItems(sizes);
	    sizeCombo.setBounds(810, 70, 75, 40);
	    sizeCombo.select(3);
	    
	    // Fonts combo
	    Combo fontCombo = new Combo(upperComp, SWT.PUSH);
	    fontCombo.setText("Font");
	    String[] fonts = new String[12];
	    fonts[0]  = "Helvetica";
	    fonts[1]  = "Arial";
	    fonts[2]  = "Verdana";
	    fonts[3]  = "Tahoma";
	    fonts[4]  = "Trebuchet MS";
	    fonts[5]  = "Impact";
	    fonts[6]  = "Gill Sans";
	    fonts[7]  = "Times New Roman";
	    fonts[8]  = "Georgia";
	    fonts[9]  = "Palatino";
	    fonts[10] = "Baskerville";
	    fonts[11] = "Courier";
	    fontCombo.setItems(fonts);  
	    fontCombo.setBounds(810, 100, 75, 40);
	    fontCombo.select(0);
	    
	    // Colors combo
	    Combo colorsCombo = new Combo(upperComp, SWT.PUSH);
	    colorsCombo.setText("Color");
	    String[] colors = new String[8];
	    colors[0] = "Black";
	    colors[1] = "White";
	    colors[2] = "Red";
	    colors[3] = "Orange";
	    colors[4] = "Yellow";
	    colors[5] = "Green";
	    colors[6] = "Blue";
	    colors[7] = "Purple";
	    colorsCombo.setItems(colors);
	    colorsCombo.setBounds(810, 130, 75, 40);
	    colorsCombo.select(0);
	    
	    
	    // - - - - - Interface for adding a image - - - - - //
	    Shell addImageShell = new Shell(display);
		addImageShell.setSize(600, 130);
		
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
        rowLayout.marginLeft = 10;
        rowLayout.marginTop = 10;
        rowLayout.spacing = 15;
        addImageShell.setLayout(rowLayout);
		
        Label imageLabel1 = new Label(addImageShell, SWT.NONE);
        imageLabel1.setText("Image name: ");
        
        Combo imagesCombo = new Combo(addImageShell, SWT.DROP_DOWN);
        File folder = new File("/Users/ryanrizzo/eclipse-workspace/CSC335-A2-LilLexi/images");
        File[] listOfFiles = folder.listFiles();
        String[] images = new String[listOfFiles.length];
        String[] imageNames = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
        	images[i] = listOfFiles[i].toString();
        	imageNames[i] = images[i].substring(60);
        }
        imagesCombo.setItems(imageNames);
               
        Button submitImage = new Button(addImageShell, SWT.PUSH);
        submitImage.setText("Submit");
     
        Label imageLabel2 = new Label(addImageShell, SWT.NONE);
        imageLabel2.setText("NOTE: Please make sure any image you wish to add is inside './CSC252-A2-LilLexi/images'");
	    
        
        
        // - - - - - Interface for adding a rectangle - - - - - //
        Shell addRectangleShell = new Shell(display);
        addRectangleShell.setSize(350, 10);
        addRectangleShell.setLayout(rowLayout);
        
        String[] widths = new String[16];
        String[] heights = new String[16];
        for (int i=1; i <= 16; i++) {
        	widths[i-1] = String.valueOf(i);
        	heights[i-1] = String.valueOf(i);
        }
        
        Label addRectangleWidth = new Label(addRectangleShell, SWT.NONE);
        addRectangleWidth.setText("Width: ");
         
        Combo widthCombo = new Combo(addRectangleShell, SWT.DROP_DOWN);
        widthCombo.setItems(widths);
        
        Label addRectangleHeight = new Label(addRectangleShell, SWT.NONE);
        addRectangleHeight.setText("Height: ");
        
        Combo heightCombo = new Combo(addRectangleShell, SWT.DROP_DOWN);
        heightCombo.setItems(heights);
        
        Button submitRectangle = new Button(addRectangleShell, SWT.PUSH);
        submitRectangle.setText("Submit");
        
        // Selection listener for exiting the program
	    fileExitItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		shell.close();
	    		display.dispose();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    		shell.close();
	    		display.dispose();
	    	}
	    });
	    
	    // Selection listener for saving the file
	    fileSaveItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    	}	    		
	    });
	    
	    // Selection listener for accessing help menu
	    helpGetHelpItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {
	    	}	    		
	    });	
	    
	    // Selection listener for adding an image to the document
	    insertImageItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addImageShell.open();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for adding a rectangle to the document
	    insertRectItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addRectangleShell.open();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for submitting an image
	    submitImage.addSelectionListener(new SelectionListener() {
        	public void widgetSelected(SelectionEvent event) {
        		String imageToAdd = imagesCombo.getText();
        		imageToAdd = "./images/" + imageToAdd; 
        		addImageShell.close();
        	}
        	public void widgetDefaultSelected(SelectionEvent event) {}
        });
	    
	    // Selection listener for submitting a rectangle
	    submitRectangle.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for use of the undo button
	    undo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.undo();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for use of the redo button
	    redo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.redo();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new font size
	    sizeCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		int size = Integer.parseInt(sizeCombo.getText());
	    		currentDoc.setCurrSize(size);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new font
	    fontCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String font = fontCombo.getText();
	    		currentDoc.setCurrFont(font);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new color
	    colorsCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String color = colorsCombo.getText();
	    		currentDoc.setCurrColor(color);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    
	    
	    
        Menu systemMenu = Display.getDefault().getSystemMenu();
        MenuItem[] mi = systemMenu.getItems();
        mi[0].addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event){
            	System.out.println("About");
            }
        });
	    
	    shell.setMenuBar(menuBar);
	      
		//---- event loop
		shell.open();
		while( !shell.isDisposed())
			if(!display.readAndDispatch()){}
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
}

