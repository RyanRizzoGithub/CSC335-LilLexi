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
	private String[][] images;
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
		shell.setSize(900,900);
		shell.setLayout( new GridLayout());	
		columnWidth = -1;
		rowHeight = -1;
		rowWidth = -1;	
		
		images = new String[100][3];
		for (int i=0; i<100; i++) {
			String[] temp = new String[3];
			temp[0] = "";
			temp[1] = "";
			temp[2] = "";
			images[i] = temp;
		}
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
            // // // //
    		Font font = new Font(display, currentDoc.getCurrFont(), currentDoc.getCurrSize(), SWT.NONE);
    		System.out.println(currentDoc.getCurrSize());
    		e.gc.setFont(font);
    		
    		String colorName = lexiControl.getCurrColor();
    		int colorCode = getColorCode(colorName);
    		Color color = display.getSystemColor(colorCode);
    		System.out.println(colorName + ", " + colorCode + ", " + color);
    		e.gc.setForeground(color);
    		
    		for (int i=0; i<images.length; i++) {
    			if (images[i][0] != "") {
    				Image image = new Image(display, images[i][0]);
    				e.gc.drawImage(image, Integer.parseInt(images[i][1]), Integer.parseInt(images[i][2]));
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
    		int column = 0; int row = 0;
    		for (Glyph g: glyphs) {
    			e.gc.drawString("" + g.getChar(), column + sideMargins, row + edgeMargins);  
    			column = (column + columnWidth) % (rowWidth * columnWidth);
    			if (column == 0) row += rowHeight;
    			System.out.println(g.getChar());
    		}
    		System.out.println();
		});	
		
		
		
        canvas.addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
            	System.out.println("mouseDown in canvas");
            	System.out.println("x: " + e.x + " y: " + e.y);
            	
            	
            	int index = ((e.x - sideMargins) / columnWidth) + (rowWidth * ((e.y - edgeMargins) / rowHeight));
            	List<Glyph> glyphs = currentDoc.getGlyphs();
            	glyphs.remove(currentDoc.getCurrIndex());

            	lexiControl.setIndex(index);
            	index = lexiControl.getCurrIndex();
            	glyphs.add(index, new Glyph('|', currentDoc.getCurrFont(), currentDoc.getCurrSize(), currentDoc.getCurrColor()));
            	canvas.redraw();
            	updateUI();
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
	    
	    
	    // - - - - - Sidebar Interface - - - - - - - - - - - - - - - - - - //
	    
	    // Undo button
	    Button undo = new Button(upperComp, SWT.PUSH);
	    undo.setText("Undo");
	    undo.setBounds(810, 10, 75, 30);
	    
	    // Redo button
	    Button redo = new Button(upperComp, SWT.PUSH);
	    redo.setText("Redo");
	    redo.setBounds(810, 40, 75, 30);
	    
	    // Size combo
	    Combo sizeCombo = new Combo(upperComp, SWT.DROP_DOWN);
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
	    Combo fontCombo = new Combo(upperComp, SWT.DROP_DOWN);
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
	    Combo colorsCombo = new Combo(upperComp, SWT.DROP_DOWN);
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
	    
	    // Margin combos
	    Combo sideMarginCombo = new Combo(upperComp, SWT.DROP_DOWN);
	    sideMarginCombo.setText("Margin");
	    String[] margins = new String[6];
	    margins[0] = "0''";
	    margins[1] = "1/4''";
	    margins[2] = "1/2''";
	    margins[3] = "3/4''";
	    margins[4] = "1''";
	    margins[5] = "1 1/2''";
	    sideMarginCombo.setItems(margins);
	    sideMarginCombo.setBounds(810, 160, 75, 40);
	    sideMarginCombo.select(2);
	    
	    Combo edgeMarginCombo = new Combo(upperComp, SWT.DROP_DOWN);
	    edgeMarginCombo.setText("Margin");
	    edgeMarginCombo.setItems(margins);
	    edgeMarginCombo.setBounds(810, 190, 75, 40);
	    edgeMarginCombo.select(2);
	    
	    Button activateSpellCheck = new Button(upperComp, SWT.PUSH);
	    activateSpellCheck.setText("Spell Check");
	    activateSpellCheck.setBounds(810, 220, 75, 40);
	    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	    
	    
	    
	    // - - - - - Interface for adding a image - - - - - - - - - - - - - - //
	    Shell addImageShell = new Shell(display);
		addImageShell.setSize(325, 310);
		
		Composite imageUpperComp = new Composite(addImageShell, SWT.NONE);
		Composite imageLowerComp = new Composite(addImageShell, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
        gridLayout.marginLeft = 10;
        gridLayout.marginTop = 10;
        gridLayout.numColumns = 2;
        gridLayout.makeColumnsEqualWidth = true;
        addImageShell.setLayout(gridLayout);
        
        imageUpperComp.setLayout(gridLayout);
        imageLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        
        imageUpperComp.setBounds(0, 0, 400, 400);
        imageLowerComp.setBounds(0, 300, 400, 100);

        addImageShell.setLayout(new RowLayout(SWT.VERTICAL));
        
       
        File folder = new File("/Users/ryanrizzo/eclipse-workspace/CSC335-A2-LilLexi/images");
        File[] listOfFiles = folder.listFiles();
        String[] imagesPath = new String[listOfFiles.length];
        String[] imageNames = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
        	imagesPath[i] = listOfFiles[i].toString();
        	imageNames[i] = imagesPath[i].substring(60);
        }
        
        String[] imageSizeOptions = new String[8];
        imageSizeOptions[0] = "4";
        imageSizeOptions[1] = "8";
        imageSizeOptions[2] = "16";
        imageSizeOptions[3] = "32";
        imageSizeOptions[4] = "64";
        imageSizeOptions[5] = "128";
        imageSizeOptions[6] = "256";
        imageSizeOptions[7] = "512";
        
        String[] imageLocationOptions = new String[15];
        imageLocationOptions[0] = "50";
        imageLocationOptions[1] = "100";
        imageLocationOptions[2] = "150";
        imageLocationOptions[3] = "200";
        imageLocationOptions[4] = "250";
        imageLocationOptions[5] = "300";
        imageLocationOptions[6] = "350";
        imageLocationOptions[7] = "400";
        imageLocationOptions[8] = "450";
        imageLocationOptions[9] = "500";
        imageLocationOptions[10] = "550";
        imageLocationOptions[11] = "600";
        imageLocationOptions[12] = "650";
        imageLocationOptions[13] = "700";
        imageLocationOptions[14] = "750";
        
        Label imageName = new Label(imageUpperComp, SWT.NONE);
        imageName.setText("Image name: \n");
        imageName.setBounds(20, 0, 40, 10);
        
        Combo imagesCombo = new Combo(imageUpperComp, SWT.DROP_DOWN);
        imagesCombo.setItems(imageNames);
        imageName.setBounds(40, 0, 40, 10);
        
        Label imageWidth = new Label(imageUpperComp, SWT.NONE);
        imageWidth.setText("Width: ");
        
        Combo imageWidthCombo = new Combo(imageUpperComp, SWT.DROP_DOWN);
        imageWidthCombo.setItems(imageSizeOptions);
        
        Label imageHeight = new Label(imageUpperComp, SWT.NONE);
        imageHeight.setText("Height: \n");
        imageName.setBounds(20, 60, 40, 10);
        
        Combo imageHeightCombo = new Combo(imageUpperComp, SWT.DROP_DOWN);
        imageHeightCombo.setItems(imageSizeOptions);
        
        Label imageX = new Label(imageUpperComp, SWT.NONE);
        imageX.setText("x: "); 
        
        Combo imageXcombo = new Combo(imageUpperComp, SWT.NONE);
        imageXcombo.setItems(imageLocationOptions);  
        
        Label imageY = new Label(imageUpperComp, SWT.NONE);
        imageY.setText("y: ");
        
        Combo imageYcombo = new Combo(imageUpperComp, SWT.NONE);
        imageYcombo.setItems(imageLocationOptions);   
        
        Label imageWarning = new Label(imageLowerComp, SWT.NONE);
        imageWarning.setText("NOTE: Please make sure any image you wish to add\n is inside './CSC252-A2-LilLexi/images'");
        imageWarning.setLocation(0, 350);
        
        Button submitImage = new Button(imageLowerComp, SWT.PUSH);
        submitImage.setText("Submit");
	    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - Interface for adding a rectangle - - - - - - - - - - - //
        Shell addRectangleShell = new Shell(display);
        addRectangleShell.setSize(350, 10);
        addRectangleShell.setLayout(gridLayout);
        
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
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - - Interface for spell check - - - - - - - - - - - - - //
        RowLayout rowLayout = new RowLayout(SWT.NONE);
        Shell spellCheckShell = new Shell(display);
        spellCheckShell.setLayout(rowLayout);
        spellCheckShell.setSize(400, 100 + (10 * spellCheck.getErrors().size()));
        
        Label spellCheckInfo = new Label(spellCheckShell, SWT.NONE);
        String spellCheckInfoText = spellCheck.toString();
        spellCheckInfo.setText(spellCheckInfoText);
        
        
        
        // - - - - - - Selection Listeners - - - - - - - - - - - - - - - - //
        
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
        		imageToAdd = "/Users/ryanrizzo/eclipse-workspace/CSC335-A2-LilLexi/images/" + imageToAdd; 
        		
        		
        		int imageWidth = Integer.parseInt(imageWidthCombo.getText());
        		int imageHeight = Integer.parseInt(imageHeightCombo.getText());
        		String imageX = imageXcombo.getText();
        		String imageY = imageYcombo.getText();
        		
        		int index = 0;
        		while (images[index][0] != "") {
        			index++;
        		}
        		String[] imageInfo = new String[3];
        		imageInfo[0] = imageToAdd;
        		imageInfo[1] = imageX;
        		imageInfo[2] = imageY;
        		images[index] = imageInfo;
        		addImageShell.close();
        		canvas.redraw();
        		updateUI();
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
	    		System.out.println("Size: " + size);
	    		lexiControl.setCurrSize(size);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new font
	    fontCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String font = fontCombo.getText();
	    		lexiControl.setCurrFont(font);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new color
	    colorsCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String color = colorsCombo.getText();
	    		lexiControl.setCurrColor(color);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new side margin size
	    sideMarginCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String sideMarginSize = sideMarginCombo.getText();
	    		lexiControl.setCurrSideMargin(sideMarginSize);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new edge margin size
	    edgeMarginCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String edgeMarginSize = edgeMarginCombo.getText();
	    		lexiControl.setCurrEdgeMargin(edgeMarginSize);
	    		canvas.redraw();
	    		updateUI();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using spell check
	    activateSpellCheck.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		spellCheck.checkWords();
	    		String spellCheckInfoText = spellCheck.toString();
	    		spellCheckInfo.setText(spellCheckInfoText);
	    		spellCheckShell.setSize(400, 100 + (14 * spellCheck.getErrors().size()));
	    		spellCheckShell.open();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	   
	    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	    
	    
	    
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

