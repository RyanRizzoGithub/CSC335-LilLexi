/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexi.java
 * DATE:		10/13/22
 */

import java.io.FileNotFoundException;

/**
 * PURPOSE:
 * This class contains the main function for the editor. In this function we setup all the 
 * objects required for the document.
 * 
 * METHODS:
 * + main(String[]):		void
 */

public class LilLexi
{
	static private LilLexiDoc currentDoc = null;

	/* - - - - - - MAIN - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Sets up all objects for the program and starts the UI loop
	 */
	public static void main(String args[]) throws InterruptedException, FileNotFoundException {
		// Check if there is already a document
		if (currentDoc == null)
			currentDoc = new LilLexiDoc();
		
		// Setup user interface
		LilLexiUI lexiUI = new LilLexiUI();
		lexiUI.setCurrentDoc( currentDoc );
		currentDoc.setUI(lexiUI);
		
		// Setup control
		LilLexiControl lexiControl = new LilLexiControl( currentDoc );
		lexiUI.setController( lexiControl );
		
		// Start the loop
		lexiUI.start();
	} 
}


