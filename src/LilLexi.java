/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexi.java
 * DATE:		10/13/22
 */

import java.io.IOException;

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
	public static void main(String args[]) throws InterruptedException, IOException {
		

		// Setup user interface
		LilLexiUI lexiUI = new LilLexiUI();
		LilLexiLoader lexiLoader = new LilLexiLoader(lexiUI.getDisplay());
		lexiLoader.start();
		
		
		LilLexiDoc doc = lexiLoader.getDoc();
		
		currentDoc = doc;
		lexiUI.setCurrentDoc(currentDoc);
		currentDoc.setUI(lexiUI);
		
		// Setup control
		LilLexiControl lexiControl = new LilLexiControl( currentDoc );
		lexiUI.setController( lexiControl );
		
		// Start the loop
		lexiUI.start();
	} 
}


