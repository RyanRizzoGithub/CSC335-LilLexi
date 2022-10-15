/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiSpellCheck.java
 * DATE:		10/13/22
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/* PURPOSE:		
 * This class adds a spell check feature to the document. Spell check is
 * handled by sending a pop-up message to the user, which contains information
 * about all the potentially incorrectly spelled words
 * 
 * METHODS:
 * + LilLexiSpellCheck(LilLexiDoc): constructor
 * + setDocument(LilLexiDoc): void
 * + checkWords(): void
 * + getErrors(): List<String>
 * + toString(): String
 */
public class LilLexiSpellCheck {
	List<String> words;
	List<String> errors;
	List<Integer> errorsIndex;
	LilLexiDoc currentDoc;
	
	/* - - - - - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function creates a new instance of LilLexiSpellCheck
	 * 
	 * @param currentDoc, the LilLexiDoc for which we are spell checking
	 */
	public LilLexiSpellCheck(LilLexiDoc currentDoc) throws FileNotFoundException {
		this.currentDoc = currentDoc;
		
		// Initialize Linked Lists
		words = new LinkedList<String>();
		errors = new LinkedList<String>();
		errorsIndex = new LinkedList<Integer>();
		
		// Add all words in dictionary to words
		Scanner scanner = new Scanner(new File("src/dictionary.txt"));
		while (scanner.hasNextLine()) {
		   String line = scanner.nextLine();
		   words.add(line);
		}
	}
	
	/* - - - - - - SET DOCUMENT - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * The purpose of this function is to set the current document. This 
	 * function may be used if the user switches documents
	 * 
	 * @param currentDoc, the new LilLexiDoc for which we are spell checking
	 */
	public void setDocument(LilLexiDoc currentDoc) {
		this.currentDoc = currentDoc;
	}
	
	/* - - - - - - CHECK WORDS - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * The purpose of this function is to iterate over all words on the current
	 * document, and determine if any of them are not found in out dictionary
	 */
	public void checkWords() {
		errors.clear();
		System.out.println("Spell check...");
		
		// Get all Glyphs from the document
		List<Glyph> glyphs = currentDoc.getGlyphs();
		String currWord = "";
		int i = 0;
		
		// Iterate over all Glyphs
		for (Glyph g: glyphs) {
			String character = "" + g.getChar();
			boolean numeric = false;
			try {
				Integer.parseInt(character);
				numeric = true;
			} catch (NumberFormatException e){
			} 
			
			if (numeric) {
				
			}
			// Check if start of new word
			else if (g.getChar() == ' ' || g.getChar() == '\b' || i == glyphs.size()-1) {
				// Check if word is in the dictionary
				if (!words.contains(currWord) && currWord != "") {
					currWord = currWord.toLowerCase();
					errors.add(currWord);
					errorsIndex.add(i);
					System.out.println("Error found: " + currWord);
				}
				currWord = "";
		    	
		    } else {
				currWord = currWord + g.getChar();
			}
			i++;
		}
	}
	
	/* - - - - - - GET ERRORS - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function returns a List containing all words potentially spelled wrong
	 * 
	 * @return errors, List<String> of potential errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	
	
	/* - - - - - - TO STRING - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function turns the list of errors into a string that can be delivered
	 * to the user
	 * 
	 * @return str, the String representation of all potential errors
	 */
	public String toString() {
		// Create string header
		String str = "The following words were not found in our dictionary...\n";
		str += "LINE:   COLUMN:	WORD: \n";
		
		// Iterate over all errors
		for (int i=0; i < errors.size(); i++) {
			// Add error to the string
			String word = errors.get(i);
			int line = errorsIndex.get(i) % (currentDoc.getRowsWidth());
			int column = errorsIndex.get(i) / (currentDoc.getRowsWidth());
			str += line + "		" + column + "		" + word + "\n";
		}
		return str;
	}
}
