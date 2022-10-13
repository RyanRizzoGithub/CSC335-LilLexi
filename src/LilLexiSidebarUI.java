/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiDoc.java
 * DATE:		10/13/22
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * PURPOSE:
 * This class is responsible for handling all of the user interface in the
 * side bar
 * 
 * METHODS:
 * + LilLexiSidebarUI():				Constructor
 * + start(): 							void
 */
public class LilLexiSidebarUI {
	Display display;
	Shell shell;
	Composite upperComp;
	LilLexiControl lexiControl;
	Canvas canvas;
	LilLexiSpellCheck spellCheck;
	

	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - -
	 * This function creates a new instance of LilLexiSidebarUI
	 */
	public LilLexiSidebarUI(Display display, Shell shell, Composite upperComp, 
			LilLexiControl lexiControl, Canvas canvas, LilLexiSpellCheck spellCheck) {
		this.display = display;
		this.shell = shell;
		this.upperComp = upperComp;
		this.lexiControl = lexiControl;
		this.canvas = canvas;
		this.spellCheck = spellCheck;
	}
	
	/* - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for  setting up all necessary user interface 
	 * elements for the document side bar
	 */
	public void start() {	
		// Label for text control
		Label textControl = new Label(upperComp, SWT.PUSH);
		textControl.setText("Text Controls");
		textControl.setBounds(810, 10, 100, 30);
		
	    // Undo button
	    Button undoText = new Button(upperComp, SWT.PUSH);
	    undoText.setText("Undo");
	    undoText.setBounds(810, 40, 75, 30);
	    
	    // Redo button
	    Button redoText = new Button(upperComp, SWT.PUSH);
	    redoText.setText("Redo");
	    redoText.setBounds(810, 70, 75, 30);
	    
	    // Label for object control
	    Label objectControl = new Label(upperComp, SWT.PUSH);
	    objectControl.setText("Shape Controls");
	    objectControl.setBounds(810, 130, 100, 30);
	    
	    // Undo button
	    Button undoShape = new Button(upperComp, SWT.PUSH);
	    undoShape.setText("Undo");
	    undoShape.setBounds(810, 160, 75, 30);
	    
	    // Redo button
	    Button redoShape = new Button(upperComp, SWT.PUSH);
	    redoShape.setText("Redo");
	    redoShape.setBounds(810, 190, 75, 30);
	    
	    // Label for text size
	    Label textSizeLabel = new Label(upperComp, SWT.NONE);
	    textSizeLabel.setText("Text Size");
	    textSizeLabel.setBounds(810, 250, 100, 15);
	    
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
	    sizeCombo.setBounds(810, 280, 75, 20);
	    sizeCombo.select(3);
	    
	    // Label for font type
	    Label fontLabel = new Label(upperComp, SWT.NONE);
	    fontLabel.setText("Text Font");
	    fontLabel.setBounds(810, 310, 100, 20);
	    
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
	    fontCombo.setBounds(810, 340, 75, 20);
	    fontCombo.select(0);
	    
	    // Label for text color
	    Label colorLabel = new Label(upperComp, SWT.NONE);
	    colorLabel.setText("Text Color");
	    colorLabel.setBounds(810, 370, 100, 20);
	    
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
	    colorsCombo.setBounds(810, 400, 75, 20);
	    colorsCombo.select(0);
	    
	    // Label for side margin size
	    Label sideMarginLabel = new Label(upperComp, SWT.NONE);
	    sideMarginLabel.setText("L/R Margin");
	    sideMarginLabel.setBounds(810, 430, 100, 20);
	    
	    // Side margin combo
	    Combo sideMarginCombo = new Combo(upperComp, SWT.PUSH);
	    sideMarginCombo.setText("Margin");
	    String[] margins = new String[6];
	    margins[0] = "0''";
	    margins[1] = "1/4''";
	    margins[2] = "1/2''";
	    margins[3] = "3/4''";
	    margins[4] = "1''";
	    margins[5] = "1 1/2''";
	    sideMarginCombo.setItems(margins);
	    sideMarginCombo.setBounds(810, 460, 75, 20);
	    sideMarginCombo.select(2);
	    
	    // Label for edge margin size
	    Label edgeMarginLabel = new Label(upperComp, SWT.NONE);
	    edgeMarginLabel.setText("T/B Margin");
	    edgeMarginLabel.setBounds(810, 490, 100, 20);
	    
	    // Edge margin combo
	    Combo edgeMarginCombo = new Combo(upperComp, SWT.PUSH);
	    edgeMarginCombo.setText("Margin");
	    edgeMarginCombo.setItems(margins);
	    edgeMarginCombo.setBounds(810, 520, 75, 20);
	    edgeMarginCombo.select(2);
	    
	    // Background color label
	    Label backgroundColorLabel = new Label(upperComp, SWT.NONE);
	    backgroundColorLabel.setText("Page Color");
	    backgroundColorLabel.setBounds(810, 550, 100, 20);
	    
	    // Background color combo
	    Combo backgroundColorCombo = new Combo(upperComp, SWT.PUSH);
	    backgroundColorCombo.setText("Color");
	    backgroundColorCombo.setItems(colors);
	    backgroundColorCombo.setBounds(810, 580, 75, 20);
	    backgroundColorCombo.select(1);
	    
	    // Button to use spell check
	    Button activateSpellCheck = new Button(upperComp, SWT.PUSH);
	    activateSpellCheck.setText("Spell Check");
	    activateSpellCheck.setBounds(805, 625, 100, 40);
	    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
       
	    
	    
        // - - - - - - Interface for spell check - - - - - - - - - - - - - - //
        RowLayout rowLayout = new RowLayout(SWT.NONE);
        Shell spellCheckShell = new Shell(display);
        spellCheckShell.setLayout(rowLayout);
        spellCheckShell.setSize(400, 100 + (10 * spellCheck.getErrors().size()));
        
        Label spellCheckInfo = new Label(spellCheckShell, SWT.NONE);
        String spellCheckInfoText = spellCheck.toString();
        spellCheckInfo.setText(spellCheckInfoText);
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
        
        
        
        // - - - - - - Selection Listeners - - - - - - - - - - - - - - - - - - //
        
        // Selection listener for use of the undo button
	    undoText.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.undo();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for use of the redo button
	    redoText.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.redo();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    undoShape.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.undoShape();
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    redoShape.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		lexiControl.redoShape();
	    		canvas.redraw();
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
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new font
	    fontCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String font = fontCombo.getText();
	    		lexiControl.setCurrFont(font);
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new color
	    colorsCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String color = colorsCombo.getText();
	    		lexiControl.setCurrColor(color);
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new side margin size
	    sideMarginCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String sideMarginSize = sideMarginCombo.getText();
	    		lexiControl.setCurrSideMargin(sideMarginSize);
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    // Selection listener for using a new edge margin size
	    edgeMarginCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String edgeMarginSize = edgeMarginCombo.getText();
	    		lexiControl.setCurrEdgeMargin(edgeMarginSize);
	    		canvas.redraw();
	    	}
	    	public void widgetDefaultSelected(SelectionEvent event) {}
	    });
	    
	    backgroundColorCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		String backgroundColor = backgroundColorCombo.getText();
	    		lexiControl.setCurrBackgroundColor(backgroundColor);
	    		canvas.redraw();
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
	}
}
