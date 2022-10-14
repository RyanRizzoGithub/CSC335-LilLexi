/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiMenuUI.java
 * DATE:		10/13/22
 */

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * PURPOSE:
 * This class is responsible for handling all of the user interface
 * for LilLexi menu bar
 * 
 * METHODS:
 * + LilLexiMenuUI():					Constructor
 * + start(): 							void
 * + getMenuBar():						Menu
 */
public class LilLexiMenuUI {
	Display display;
	Shell shell;
	Composite upperComp;
	LilLexiControl lexiControl;
	Canvas canvas;
	LilLexiSpellCheck spellCheck;
	Menu menuBar;
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * This function creates a new instance of LilLexiMenuUI
	 */
	public LilLexiMenuUI(Display display, Shell shell, Composite upperComp,
			LilLexiControl lexiControl, Canvas canvas, LilLexiSpellCheck spellCheck) {
		this.display = display;
		this.shell = shell;
		this.upperComp = upperComp;
		this.lexiControl = lexiControl;
		this.canvas = canvas;
		this.spellCheck = spellCheck;
	}
	
	/* - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting setting up all necessary user 
	 * interface elements for all options in the menu bar
	 */
	public void start() {
		// Setup menus and menu items
		Menu menuBar, fileMenu, insertMenu, helpMenu;
		MenuItem fileMenuHeader, insertMenuHeader, helpMenuHeader, fileExitItem, fileSaveItem, helpGetHelpItem;
		MenuItem insertImageItem, insertRectItem, insertLineItem, insertCircleItem, insertTriangleItem;
		menuBar = new Menu(shell, SWT.BAR);
		this.menuBar = menuBar;
		
		// File Menu header
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		// Menu item to save file
	    fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileSaveItem.setText("Save");
	    // Menu item to exit file
	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("Exit");
	    
	    // Insert Menu header
		insertMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		insertMenuHeader.setText("Insert");
		insertMenu = new Menu(shell, SWT.DROP_DOWN);
		insertMenuHeader.setMenu(insertMenu);
		
		// Menu item to add image
	    insertImageItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertImageItem.setText("Image");
	    // Menu item to add rectangle
	    insertRectItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertRectItem.setText("Rectangle");
	    // Menu item to add line
	    insertLineItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertLineItem.setText("Line");
	    // Menu item to add circle
	    insertCircleItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertCircleItem.setText("Circle");
	    // Menu item to add triangle
	    insertTriangleItem = new MenuItem(insertMenu, SWT.PUSH);
	    insertTriangleItem.setText("Triangle");
	    
	    // Help Menu header
	    helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    helpMenuHeader.setText("Help");
	    helpMenu = new Menu(shell, SWT.DROP_DOWN);
	    helpMenuHeader.setMenu(helpMenu);

	    helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
	    helpGetHelpItem.setText("Get Help");
	    
	    
	    
	    // - - - - - Interface for adding a image - - - - - - - - - - - - - - //
	    
	    // Create shell for adding image
	    Shell addImageShell = new Shell(display, SWT.NO_TRIM);
		addImageShell.setSize(325, 235);
		
		Composite imageUpperComp = new Composite(addImageShell, SWT.NONE);
		Composite imageLowerComp = new Composite(addImageShell, SWT.NONE);
		
		// Setup the layout
		GridLayout gridLayout = new GridLayout();
        gridLayout.marginLeft = 10;
        gridLayout.marginTop = 10;
        gridLayout.numColumns = 2;
        gridLayout.makeColumnsEqualWidth = true;
        addImageShell.setLayout(gridLayout);
        
        imageUpperComp.setLayout(gridLayout);
        imageLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        
        imageUpperComp.setBounds(0, 0, 400, 250);
        imageLowerComp.setBounds(0, 250, 400, 150);

        addImageShell.setLayout(new RowLayout(SWT.VERTICAL));
        
        // Get all images in in the images folder
        File file = new File("images");
        File folder = new File(file.getAbsolutePath());
       
        File[] listOfFiles = folder.listFiles();
        String[] imagesPath = new String[listOfFiles.length];
        String[] imageNames = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
        	imagesPath[i] = listOfFiles[i].toString();
        	imageNames[i] = imagesPath[i].substring(60);
        }
        
        // Setup options for item sizes
        String[] sizeOptions = new String[8];
        sizeOptions[0] = "4";
        sizeOptions[1] = "8";
        sizeOptions[2] = "16";
        sizeOptions[3] = "32";
        sizeOptions[4] = "64";
        sizeOptions[5] = "128";
        sizeOptions[6] = "256";
        sizeOptions[7] = "512";
        
        // Setup options for item locations
        String[] locationOptions = new String[15];
        locationOptions[0] = "50";
        locationOptions[1] = "100";
        locationOptions[2] = "150";
        locationOptions[3] = "200";
        locationOptions[4] = "250";
        locationOptions[5] = "300";
        locationOptions[6] = "350";
        locationOptions[7] = "400";
        locationOptions[8] = "450";
        locationOptions[9] = "500";
        locationOptions[10] = "550";
        locationOptions[11] = "600";
        locationOptions[12] = "650";
        locationOptions[13] = "700";
        locationOptions[14] = "750";
        
        // Setup image name combo
        Label imageName = new Label(imageUpperComp, SWT.NONE);
        imageName.setText("Image name: \n");
        imageName.setBounds(20, 0, 40, 10);
        Combo imagesCombo = new Combo(imageUpperComp, SWT.PUSH);
        imagesCombo.setItems(imageNames);
        imageName.setBounds(40, 0, 40, 10);
        
        // Setup image x coordinate combo
        Label imageX = new Label(imageUpperComp, SWT.NONE);
        imageX.setText("x: ");         
        Combo imageXcombo = new Combo(imageUpperComp, SWT.PUSH);
        imageXcombo.setItems(locationOptions);  
        
        // Setup image y coordinate combo
        Label imageY = new Label(imageUpperComp, SWT.NONE);
        imageY.setText("y: ");      
        Combo imageYcombo = new Combo(imageUpperComp, SWT.PUSH);
        imageYcombo.setItems(locationOptions);   
        
        // Setup label for warning message
        Label imageWarning = new Label(imageLowerComp, SWT.NONE);
        imageWarning.setText(" NOTE: Please make sure any image you wish to add\n is inside './CSC252-A2-LilLexi/images'");
        imageWarning.setLocation(0, 350);
        
        // Setup button for submitting the image
        Button submitImage = new Button(imageLowerComp, SWT.PUSH);
        submitImage.setText("Submit");
        
        // Setup button for closing the image shell
        Button closeImage = new Button(imageLowerComp, SWT.PUSH);
        closeImage.setText("Close");
	    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - Interface for adding a rectangle - - - - - - - - - - - //
        
        // Create shell for adding a rectangle
        Shell addRectShell = new Shell(display, SWT.NO_TRIM);
		addRectShell.setSize(200, 230);
		
		Composite rectUpperComp = new Composite(addRectShell, SWT.NONE);
		Composite rectLowerComp = new Composite(addRectShell, SWT.NONE);
        
		// Set the layout for this interface
        rectUpperComp.setLayout(gridLayout);
        rectLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        addRectShell.setLayout(new RowLayout(SWT.VERTICAL));
        
        rectUpperComp.setBounds(0, 0, 400, 200);
        rectLowerComp.setBounds(0, 300, 400, 100);
        
        // Setup the rectangle width combo
        Label rectWidth = new Label(rectUpperComp, SWT.NONE);
        rectWidth.setText("Width: ");
        Combo rectWidthCombo = new Combo(rectUpperComp, SWT.PUSH);
        rectWidthCombo.setItems(sizeOptions);
        
        // Setup the rectangle height combo
        Label rectHeight = new Label(rectUpperComp, SWT.NONE);
        rectHeight.setText("Height: \n");       
        Combo rectHeightCombo = new Combo(rectUpperComp, SWT.PUSH);
        rectHeightCombo.setItems(sizeOptions);
        
        // Setup the rectangle x coordinate combo
        Label rectX = new Label(rectUpperComp, SWT.NONE);
        rectX.setText("x: ");         
        Combo rectXcombo = new Combo(rectUpperComp, SWT.PUSH);
        rectXcombo.setItems(locationOptions);  
        
        // Setup the rectangle y coordinate combo
        Label rectY = new Label(rectUpperComp, SWT.NONE);
        rectY.setText("y: ");        
        Combo rectYcombo = new Combo(rectUpperComp, SWT.PUSH);
        rectYcombo.setItems(locationOptions);   
        
        // Setup the rectangle submit button
        Button submitRect = new Button(rectLowerComp, SWT.PUSH);
        submitRect.setText("Submit");
        
        // Setup button for closing the rectangle shell
        Button closeRect = new Button(rectLowerComp, SWT.PUSH);
        closeRect.setText("Close");
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - - Interface for adding a line - - - - - - - - - - - - //
        
        // Create a new shell for adding a line
        Shell addLineShell = new Shell(display, SWT.NO_TRIM);
        addLineShell.setSize(200, 230);
        
        Composite lineUpperComp = new Composite(addLineShell, SWT.NONE);
        Composite lineLowerComp = new Composite(addLineShell, SWT.NONE);
        
        // Setup the layout for this interface
        lineUpperComp.setLayout(gridLayout);
        lineLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        addLineShell.setLayout(new RowLayout(SWT.VERTICAL));
        
        rectUpperComp.setBounds(0, 0, 400, 200);
        rectLowerComp.setBounds(0, 150, 400, 100);
        
        // Setup the line x1 coordinate combo
        Label lineX1 = new Label(lineUpperComp, SWT.NONE);
        lineX1.setText("X1: ");
        Combo lineX1combo = new Combo(lineUpperComp, SWT.PUSH);
        lineX1combo.setItems(locationOptions);
        
        // Setup the line y1 coordinate combo
        Label lineY1 = new Label(lineUpperComp, SWT.NONE);
        lineY1.setText("Y1: ");
        Combo lineY1combo = new Combo(lineUpperComp, SWT.PUSH);
        lineY1combo.setItems(locationOptions);
        
        // Setup the line x2 coordinate combo
        Label lineX2 = new Label(lineUpperComp, SWT.NONE);
        lineX2.setText("X2: ");
        Combo lineX2combo = new Combo(lineUpperComp, SWT.PUSH);
        lineX2combo.setItems(locationOptions);
        
        // Setup the line y2 coordinate combo
        Label lineY2 = new Label(lineUpperComp, SWT.NONE);
        lineY2.setText("Y2: ");
        Combo lineY2combo = new Combo(lineUpperComp, SWT.PUSH);
        lineY2combo.setItems(locationOptions);
        
        // Setup the line submit button
        Button submitLine = new Button(lineLowerComp, SWT.PUSH);
        submitLine.setText("Submit");
        
        // Setup button for closing line shell
        Button closeLine = new Button(lineLowerComp, SWT.PUSH);
        closeLine.setText("Close");
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - - Interface for adding a circle - - - - - - - - - - - //
        
        // Create shell for adding a circle
        Shell addCircleShell = new Shell(display, SWT.NO_TRIM);
        addCircleShell.setSize(200, 230);
        
        Composite circleUpperComp = new Composite(addCircleShell, SWT.NONE);
        Composite circleLowerComp = new Composite(addCircleShell, SWT.NONE);
        
        // Setup the layout for this interface
        circleUpperComp.setLayout(gridLayout);
        circleLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        addCircleShell.setLayout(new RowLayout(SWT.VERTICAL));
        
        circleUpperComp.setBounds(0, 0, 400, 200);
        circleLowerComp.setBounds(0, 150, 400, 100);
        
        // Setup the circle x coordinate combo
        Label circleX = new Label(circleUpperComp, SWT.NONE);
        circleX.setText("X: ");
        Combo circleXcombo = new Combo(circleUpperComp, SWT.PUSH);
        circleXcombo.setItems(locationOptions);
        
        // Setup the circle y coordinate combo
        Label circleY = new Label(circleUpperComp, SWT.NONE);
        circleY.setText("Y: ");
        Combo circleYcombo = new Combo(circleUpperComp, SWT.PUSH);
        circleYcombo.setItems(locationOptions);
        
        // Setup the circle width combo
        Label circleWidth = new Label(circleUpperComp, SWT.NONE);
        circleWidth.setText("Width: ");
        Combo circleWidthCombo = new Combo(circleUpperComp, SWT.PUSH);
        circleWidthCombo.setItems(sizeOptions);
        
        // Setup the circle height combo
        Label circleHeight = new Label(circleUpperComp, SWT.NONE);
        circleHeight.setText("Height: ");
        Combo circleHeightCombo = new Combo(circleUpperComp, SWT.PUSH);
        circleHeightCombo.setItems(sizeOptions);
        
        // Setup the circle submit button
        Button submitCircle = new Button(circleLowerComp, SWT.PUSH);
        submitCircle.setText("Submit");
        
        // Setup button to close the circle shell
        Button closeCircle = new Button(circleLowerComp, SWT.PUSH);
        closeCircle.setText("Close");
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - - Interface for adding a triangle - - - - - - - - - - //
        
        // Create interface for adding a triangle
        Shell addTriangleShell = new Shell(display, SWT.NO_TRIM);
        addTriangleShell.setSize(200, 280);
        
        Composite triangleUpperComp = new Composite(addTriangleShell, SWT.NONE);
        Composite triangleLowerComp = new Composite(addTriangleShell, SWT.NONE);
        
        // Setup the layout for this interface
        triangleUpperComp.setLayout(gridLayout);
        triangleLowerComp.setLayout(new RowLayout(SWT.VERTICAL));
        addTriangleShell.setLayout(new RowLayout(SWT.VERTICAL));
        
        circleUpperComp.setBounds(0, 0, 400, 150);
        circleLowerComp.setBounds(0, 150, 400, 50);
        
        // Setup the triangle X1 coordinate combo
        Label triangleX1 = new Label(triangleUpperComp, SWT.NONE);
        triangleX1.setText("X1: ");
        Combo triangleX1combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleX1combo.setItems(locationOptions);
        
        // Setup the triangle Y1 coordinate combo
        Label triangleY1 = new Label(triangleUpperComp, SWT.NONE);
        triangleY1.setText("Y1: ");
        Combo triangleY1combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleY1combo.setItems(locationOptions);
        
        // Setup the triangle X2 coordinate combo
        Label triangleX2 = new Label(triangleUpperComp, SWT.NONE);
        triangleX2.setText("X2: ");
        Combo triangleX2combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleX2combo.setItems(locationOptions);
        
        // Setup the triangle Y2 coordinate combo
        Label triangleY2 = new Label(triangleUpperComp, SWT.NONE);
        triangleY2.setText("Y2: ");
        Combo triangleY2combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleY2combo.setItems(locationOptions);
        
        // Setup the triangle X3 coordinate combo
        Label triangleX3 = new Label(triangleUpperComp, SWT.NONE);
        triangleX3.setText("X3: ");
        Combo triangleX3combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleX3combo.setItems(locationOptions);
        
        // Setup the triangle Y3 coordinate combo
        Label triangleY3 = new Label(triangleUpperComp, SWT.NONE);
        triangleY3.setText("Y3: ");
        Combo triangleY3combo = new Combo(triangleUpperComp, SWT.PUSH);
        triangleY3combo.setItems(locationOptions);
        
        // Setup the triangle submit button
        Button submitTriangle = new Button(triangleLowerComp, SWT.PUSH);
        submitTriangle.setText("Submit");
        
        // Setup button to close triangle shell
        Button closeTriangle = new Button(triangleLowerComp, SWT.PUSH);
        closeTriangle.setText("Close");
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
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
	    		addImageShell.setVisible(true);
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for adding a rectangle to the document
	    insertRectItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addRectShell.open();
	    		addRectShell.setVisible(true);
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for adding a line to the document
	    insertLineItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addLineShell.open();
	    		addLineShell.setVisible(true);
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for adding a circle to the document
	    insertCircleItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addCircleShell.open();
	    		addCircleShell.setVisible(true);
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for adding a triangle to the document
	    insertTriangleItem.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		addTriangleShell.open();
	    		addTriangleShell.setVisible(true);
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for submitting an image
	    submitImage.addSelectionListener(new SelectionListener() {
        	public void widgetSelected(SelectionEvent event) {
        		// Check if all fields are filled
        		if (!imagesCombo.getText().equals("") && !imageXcombo.getText().equals("") 
        				&& !imageYcombo.getText().equals("")) {
        			
        			// Get the variables
        			String imageToAdd = imagesCombo.getText();
        			imageToAdd = "/Users/ryanrizzo/eclipse-workspace/CSC335-A2-LilLexi/images/" + imageToAdd; 
        			String imageX = imageXcombo.getText();
        			String imageY = imageYcombo.getText();
        		
        			// Move to end of array
	        		String[][] images = lexiControl.getImages();
	        		int index = 0;
	        		while (images[index][0] != "") {
	        			index++;
	        		}
	        		
	        		// Set the variables
	        		String[] imageInfo = new String[3];
	        		imageInfo[0] = imageToAdd;
	        		imageInfo[1] = imageX;
	        		imageInfo[2] = imageY;
	        		images[index] = imageInfo;
	        		addImageShell.setVisible(false);
	        		lexiControl.addUndoShape("Image");
	        		canvas.redraw();
        		}
        	}
        	public void widgetDefaultSelected(SelectionEvent event) {}
        });
	    
	    // Selection listener for submitting a rectangle
	    submitRect.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		// Check if all fields are filled
	    		if (!rectWidthCombo.getText().equals("") && !rectHeightCombo.getText().equals("")
	    				&& !rectXcombo.getText().equals("") && !rectYcombo.getText().equals("")) {
		    		// Get the variables
		    		String rectWidth = rectWidthCombo.getText();
	        		String rectHeight = rectHeightCombo.getText();
	        		String rectX = rectXcombo.getText();
	        		String rectY = rectYcombo.getText();
	        		String[][] rects = lexiControl.getRects();
	        		
	        		// Move to end of array
	        		int index = 0;
	        		while (rects[index][0] != "") {
	        			index++;
	        		}
	        		// Set the variables
	        		String[] rectInfo = new String[4];
	        		rectInfo[0] = rectX;
	        		rectInfo[1] = rectY;
	        		rectInfo[2] = rectWidth;
	        		rectInfo[3] = rectHeight;
	        		rects[index] = rectInfo;
	        		
	        		// Update
	        		addRectShell.setVisible(false);
	        		lexiControl.addUndoShape("Rectangle");
	        		canvas.redraw();
	    		}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for submitting a line
	    submitLine.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		// Check if all fields are filled
	    		if (!lineX1combo.getText().equals("") && !lineY1combo.getText().equals("")
	    				&& !lineX2combo.getText().equals("") && !lineY2combo.getText().equals("")) {
		    		// Get the variables
		    		String lineX1 = lineX1combo.getText();
		    		String lineY1 = lineY1combo.getText();
		    		String lineX2 = lineX2combo.getText();
		    		String lineY2 = lineY2combo.getText();
		    		String[][] lines = lexiControl.getLines();
		    		
		    		// Move to end of array
		    		int index = 0;
		    		while (lines[index][0] != "") {
		    			index++;
		    		}
		    		
		    		// Set the variables
		    		String[] lineInfo = new String[4];
		    		lineInfo[0] = lineX1;
		    		lineInfo[1] = lineY1;
		    		lineInfo[2] = lineX2;
		    		lineInfo[3] = lineY2;
		    		lines[index] = lineInfo;
		    		
		    		// Update
		    		addLineShell.setVisible(false);
		    		lexiControl.addUndoShape("Line");
		    		canvas.redraw();
	    		}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for submitting a circle
	    submitCircle.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		// Check if all fields are filled
	    		if (!circleXcombo.getText().equals("") && !circleYcombo.getText().equals("")
	    				&& !circleWidthCombo.getText().equals("") && !circleHeightCombo.getText().equals("")) {
		    		// Get the variables
		    		String circleX = circleXcombo.getText();
		    		String circleY = circleYcombo.getText();
		    		String circleWidth = circleWidthCombo.getText();
		    		String circleHeight = circleHeightCombo.getText();
		    		String[][] circles = lexiControl.getCircles();
		    		
		    		// Move to end of array
		    		int index = 0;
		    		while (circles[index][0] != "") {
		    			index++;
		    		}
		    		
		    		// Set the variables
		    		String[] circleInfo = new String[4];
		    		circleInfo[0] = circleX;
		    		circleInfo[1] = circleY;
		    		circleInfo[2] = circleWidth;
		    		circleInfo[3] = circleHeight;
		    		circles[index] = circleInfo;
		    		
		    		// Update
		    		addCircleShell.setVisible(false);
		    		lexiControl.addUndoShape("Circle");
		    		canvas.redraw();
	    		}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for submitting a triangle
	    submitTriangle.addSelectionListener(new SelectionListener() {
	    	// Check if all fields are filled
	    	public void widgetSelected(SelectionEvent event) {
	    		if (!triangleX1combo.getText().equals("") && !triangleY1.getText().equals("")
	    				&& !triangleX2combo.getText().equals("") && !triangleY2.getText().equals("")
	    				&& !triangleX3combo.getText().equals("") && !triangleY3combo.getText().equals("")) {
		    		// Get the variables
		    		String triangleX1 = triangleX1combo.getText();
		    		String triangleY1 = triangleY1combo.getText();
		    		String triangleX2 = triangleX2combo.getText();
		    		String triangleY2 = triangleY2combo.getText();
		    		String triangleX3 = triangleX3combo.getText();
		    		String triangleY3 = triangleY3combo.getText();
		    		String[][] triangles = lexiControl.getTriangles();
		    		
		    		// Move to end of array
		    		int index = 0;
		    		while (triangles[index][0] != "") {
		    			index++;
		    		}
		    		
		    		// Set the variables
		    		String[] triangleInfo = new String[6];
		    		triangleInfo[0] = triangleX1;
		    		triangleInfo[1] = triangleY1;
		    		triangleInfo[2] = triangleX2;
		    		triangleInfo[3] = triangleY2;
		    		triangleInfo[4] = triangleX3;
		    		triangleInfo[5] = triangleY3;
		    		triangles[index] = triangleInfo;
		    		
		    		// Update
		    		addTriangleShell.setVisible(false);
		    		lexiControl.addUndoShape("Triangle");
		    		canvas.redraw();
	    		}
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener to close the image shell
	    closeImage.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				addImageShell.setVisible(false);
			}    	
	    });
	    
	    // Selection listener to close the rectangle shell
	    closeRect.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				addRectShell.setVisible(false);
			}    	
	    });
	    
	    // Selection listener to close the line shell
	    closeLine.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				addLineShell.setVisible(false);
			}    	
	    });
	    
	    // Selection listener to close the circle shell
	    closeCircle.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				addCircleShell.setVisible(false);
			}    	
	    });
	    
	    // Selection listener to close the triangle shell
	    closeTriangle.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			public void widgetSelected(SelectionEvent event) {
				addTriangleShell.setVisible(false);
			}    	
	    });
	}
	
	/* - - - - - - GET MUNU BAR - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible for returning the menu bar so that it many
	 * be used in other classes.
	 * 
	 * @return menuBar, the Menu object being returned
	 */
	public Menu getMenuBar() {
		return menuBar;
	}
}
