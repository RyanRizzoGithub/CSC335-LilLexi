/**
 * AUTHOR(S):	Ryan Rizzo
 * CLASS:		CSC 335
 * FILE:		LilLexiLoader.java
 * DATE:		10/13/22
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class LilLexiLoader {
	private Display display;
	private LilLexiDoc doc;

	/* - - - - - - CONSRUCTOR - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Creates a new instance of LilLexiLoader
	 */
	public LilLexiLoader(Display dis) {
		this.display = dis;
	}
	
	/* - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Starts the Lil Lexi loader. Gives users the ability to chose from files
	 * they have created, as well as creating a new one
	 */
	public void start() {
		// Create a new shell
		Shell docSelectorShell = new Shell(display);
		docSelectorShell.setSize(180, 230);
		docSelectorShell.setLayout(new GridLayout());
		
		// Find all files in docs folder
		File file = new File("docs");
	    File folder = new File(file.getAbsolutePath());
	       
	    File[] listOfDocs = folder.listFiles();
	    String[] docPath = new String[listOfDocs.length];
	    String[] docName = new String[listOfDocs.length];
	    for (int i = 0; i < listOfDocs.length; i++) {
	        docPath[i] = listOfDocs[i].toString();
	        docName[i] = docPath[i].substring(58);
	    }
		
	    // Add button to load a specific document
		Button loadDoc = new Button(docSelectorShell, SWT.PUSH);
		loadDoc.setText("Load");
		
		// Add a combo to select the document you wish to choose
		Combo docCombo = new Combo(docSelectorShell, SWT.PUSH);
		docCombo.setText("Doc");
		docCombo.setItems(docName);
		
		// Add a button to create a new document
		Button newDoc = new Button(docSelectorShell, SWT.PUSH);
		newDoc.setText("New");
		
		// Add a button to close the program
		Button cancelDoc = new Button(docSelectorShell, SWT.PUSH);
		cancelDoc.setText("Cancel");
		
		// Information 
		Label warningLabel = new Label(docSelectorShell, SWT.PUSH);
		warningLabel.setText("Warning: Loading documents\nstill in experimental\nphase");
		
		// Add selection listener for loading the document
		loadDoc.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}

			public void widgetSelected(SelectionEvent event) {
				// Check if fields are empty
				if (!docCombo.getText().equals("")) {
					// Get info
					String docName = docCombo.getText().substring(0, docCombo.getText().length() - 4);
					try {
						// Create the doc
						LilLexiDoc doc = loadDoc(docName);
						setDoc(doc);
						docSelectorShell.setVisible(false);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		
		});
		
		// Add selection listener for loading a new document
		newDoc.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			
			public void widgetSelected(SelectionEvent event) {
				LilLexiDoc doc = new LilLexiDoc();
				setDoc(doc);
				docSelectorShell.setVisible(false);
			}
		});
		
		// Add selection listener for quitting the program
		cancelDoc.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			
			public void widgetSelected(SelectionEvent event) {
				display.dispose();
			}
		});

		// Event loop
		docSelectorShell.open();
		while(docSelectorShell.isVisible()) 
			if(!display.readAndDispatch()) {		
			}
	}
	
	/* - - - - - - SET DOC - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for setting the current document for this loader
	 * 
	 * @param doc, the LilLexiDoc
	 */
	public void setDoc(LilLexiDoc doc) {
		this.doc = doc;
	}
	
	/* - - - - - - GET DOC - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for returning the current document for this loader
	 * 
	 * @return doc, the LilLexiDoc
	 */
	public LilLexiDoc getDoc() {
		return this.doc;
	}
	
	/* - - - - - - LOAD DOC - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * Author: Ryan Rizzo
	 * Given the name of a file in the docs folder, this function converts the information
	 * stored in that .txt file, into a working document in LilLexi
	 * 
	 * @throws IOException
	 * @param name, the String name of the file being loaded
	 * @return lexiDoc, the created LilLexiDoc
	 */
	public static LilLexiDoc loadDoc(String name) throws IOException {
		// Get the file and setup reader
		File myDoc = new File("docs/" + name + ".txt");
		FileReader fileReader = new FileReader(myDoc);
		BufferedReader myReader = new BufferedReader(fileReader);
		String fileLine = myReader.readLine();
		
		
		LilLexiDoc lexiDoc = new LilLexiDoc();
		List<Glyph> glyphs = new LinkedList<Glyph>();
		// Add Glyphs
		while (!fileLine.equals("END GLYPHS")) {
			for (int i=0; i<fileLine.length(); i++) {
				glyphs.add(new Glyph(fileLine.charAt(i)));
			}
			glyphs.add(new Glyph('`'));
			fileLine = myReader.readLine();
		} 
		fileLine = myReader.readLine();
		
		// Get the variables
		String currSideMargin = myReader.readLine();
		String currEdgeMargin = myReader.readLine();
		String currFont = myReader.readLine();
		String currColor = myReader.readLine();
		String currBackgroundColor = myReader.readLine();
		int currSize = Integer.parseInt(myReader.readLine());
		int numNewline = Integer.parseInt(myReader.readLine());
		int currDepth = Integer.parseInt(myReader.readLine());
		int columnWidth = Integer.parseInt(myReader.readLine());
		int rowHeight = Integer.parseInt(myReader.readLine());
		int rows = Integer.parseInt(myReader.readLine());
		int rowsWidth = Integer.parseInt(myReader.readLine());
		int pages = Integer.parseInt(myReader.readLine());
		
		// Get each rows width
		fileLine = myReader.readLine();
		int[] rowWidth = new int[1000];
		int i = 0;
		while (!fileLine.equals("IMAGES")) {
			System.out.println(fileLine);
			rowWidth[i] = Integer.parseInt(fileLine);
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 1000) {
			rowWidth[i] = -1;
			i++;
		}
		// Get the images
		String[][] images = new String[100][3];
		String[] image = new String[3];
		i = 0;
		while (!fileLine.equals("RECTANGLES")) {		
			image[0] = myReader.readLine();
			image[1] = myReader.readLine();
			image[2] = myReader.readLine();
			if (!image[0].equals("")) {
				images[i] = image;
			}
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 100) {
			images[i][0] = "";
			images[i][1] = "";
			images[i][2] = "";
			i++;
		}
		
		// Get the rectangles
		String[][] rects = new String[100][4];
		String[] rect = new String[4];
		i = 0;
		System.out.println("fileLine: " + fileLine);
		while (!fileLine.equals("LINES")) {
			rect[0] = myReader.readLine();
			rect[1] = myReader.readLine();
			rect[2] = myReader.readLine();
			rect[3] = myReader.readLine();
			if (!rects[0].equals("")) {
				rects[i] = rect;
			}
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 100) {
			rects[i][0] = "";
			rects[i][1] = "";
			rects[i][2] = "";
			rects[i][3] = "";
			i++;
		}
		
		// Get the lines
		String[][] lines = new String[100][4];
		String[] line = new String[4];
		i = 0;
		
		while (!fileLine.equals("CIRCLES")) {
			line[0] = myReader.readLine();
			line[1] = myReader.readLine();
			line[2] = myReader.readLine();
			line[3] = myReader.readLine();
			if (!line[0].equals("")) {
				lines[i] = line;
			}
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 100) {
			lines[i][0] = "";
			lines[i][1] = "";
			lines[i][2] = "";
			lines[i][3] = "";
			i++;
		}
		
		// Get the circles
		String[][] circles = new String[100][4];
		String[] circle = new String[4];
		i = 0;
		
		while (!fileLine.equals("TRIANGLES")) {
			circle[0] = myReader.readLine();
			circle[1] = myReader.readLine();
			circle[2] = myReader.readLine();
			circle[3] = myReader.readLine();
			if (!circle[0].equals("")) {
				circles[i] = circle;
			}
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 100) {
			circles[i][0] = "";
			circles[i][1] = "";
			circles[i][2] = "";
			circles[i][3] = "";
			i++;
		}
		
		// Get the triangles
		String[][] triangles = new String[100][6];
		String[] triangle = new String[6];
		i = 0;
		
		while (!fileLine.equals("EOF")) {
			triangle[0] = myReader.readLine();
			triangle[1] = myReader.readLine();
			triangle[2] = myReader.readLine();
			triangle[3] = myReader.readLine();
			triangle[4] = myReader.readLine();
			triangle[5] = myReader.readLine();
			if (!triangle[0].equals("")) {
				triangles[i] = triangle;
			}
			fileLine = myReader.readLine();
			i++;
		}
		while (i < 100) {
			triangles[i][0] = "";
			triangles[i][1] = "";
			triangles[i][2] = "";
			triangles[i][3] = "";
			triangles[i][4] = "";
			triangles[i][5] = "";
			i++;
		}	
		
		// Create the document
		lexiDoc = new LilLexiDoc(glyphs, 0, currSideMargin, currEdgeMargin,
				currFont, currColor, currBackgroundColor, currSize, numNewline, 
				currDepth, columnWidth, rowHeight, rows, rowsWidth, pages, rowWidth,
				images,rects, lines, circles, triangles);	
		myReader.close();
		return lexiDoc;
	}
	
	/* - - - - - - SAVE DOC - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Ryan Rizzo
	 * This function is responsible for taking in a LilLexiDoc, and a user chosen
	 * name, and converting it into a save-able .txt file
	 * 
	 * @throws IOException
	 * @param doc, the LilLexiDoc
	 * @param name, the String
	 */
	public void saveDoc(LilLexiDoc doc, String name) throws IOException {
		// Create the file
		FileWriter myWriter = new FileWriter("docs/" + name + ".txt");
		List<Glyph> glyphs = doc.getGlyphs();
		String str = "";
		
		
		// Get all the text characters
		for (int i=0; i<glyphs.size(); i++) {
			if (glyphs.get(i).getChar() != '|') {
				str += glyphs.get(i).getChar();
			}
		}
		
		// Add all variables
		str += "\n";
		str += "END GLYPHS\n";
		str += glyphs.size() + "\n";
		str += doc.getCurrSideMargin() + "\n";
		str += doc.getCurrEdgeMargin() + "\n";
		str += doc.getCurrFont() + "\n";
		str += doc.getCurrColor() + "\n";
		str += doc.getCurrBackgroundColor() + "\n";
		str += doc.getCurrSize() + "\n";
		str += doc.getNumNewline() + "\n";
		str += doc.getDepth() + "\n";
		str += doc.getColumnWidth() + "\n";
		str += doc.getRowHeight() + "\n";
		str += doc.getRows() + "\n";
		str += doc.getRowsWidth() + "\n";
		str += doc.getPages() + "\n";
		
		int[] rowWidth = doc.getRowWidth();
		for (int i=0; i<doc.getRows(); i++) {
			str += rowWidth[i] + "\n";
		}
	
		// Add images
		str += "IMAGES\n";
		int i = 0;
		String[][] images = doc.getImages();
		while (images[i][0] != "") {
			str += images[i][0] + "\n";
			str += images[i][1] + "\n";
			str += images[i][2] + "\n";
			i++;
		}
		if (images[0][0] == "") {
			str += "\n";
			str += "\n";
			str += "\n";
		}
		
		// Add rectangles
		str += "RECTANGLES\n";
		i = 0;
		String[][] rects = doc.getRects();
		while (rects[i][0] != "") {
			str += rects[i][0] + "\n";
			str += rects[i][1] + "\n";
			str += rects[i][2] + "\n";
			str += rects[i][3] + "\n";
			i++;
		}
		if (rects[0][0] == "") {
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
		}
		
		// Add lines
		str += "LINES\n";
		i = 0;
		String[][] lines = doc.getLines();
		while (lines[i][0] != "") {
			str += lines[i][0] + "\n";
			str += lines[i][1] + "\n";
			str += lines[i][2] + "\n";
			str += lines[i][3] + "\n";
			i++;
		}
		if (lines[0][0] == "") {
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
		}
		
		// Add circles
		str += "CIRCLES\n";
		i = 0;
		String[][] circles = doc.getCircles();
		while (circles[i][0] != "") {
			str += circles[i][0] + "\n";
			str += circles[i][1] + "\n";
			str += circles[i][2] + "\n";
			str += circles[i][3] + "\n";
			i++;
		}
		if (circles[0][0] == "") {
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
		}
		
		// Add triangles
		str += "TRIANGLES\n";
		i = 0;
		String[][] triangles = doc.getTriangles();
		while (triangles[i][0] != "") {
			str += triangles[i][0] + "\n";
			str += triangles[i][1] + "\n";
			str += triangles[i][2] + "\n";
			str += triangles[i][3] + "\n";
			str += triangles[i][4] + "\n";
			str += triangles[i][5] + "\n";
			i++;
		}
		if (triangles[0][0] == "") {
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
			str += "\n";
		}
		str += "EOF";
		
		myWriter.write(str);
		myWriter.close();	
		System.out.println("Successfully wrote to the file.");
		
     } 
}
