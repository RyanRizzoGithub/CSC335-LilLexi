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
	 * Creates a new instance of LilLexiLoader
	 */
	public LilLexiLoader(Display dis) {
		display = dis;
	}
	
	/* - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Starts the Lil Lexi loader
	 */
	public void start() {
		Shell docSelectorShell = new Shell(display);
		docSelectorShell.setSize(180, 230);
		docSelectorShell.setLayout(new GridLayout());
		
		File file = new File("docs");
	    File folder = new File(file.getAbsolutePath());
	       
	    File[] listOfDocs = folder.listFiles();
	    String[] docPath = new String[listOfDocs.length];
	    String[] docName = new String[listOfDocs.length];
	    for (int i = 0; i < listOfDocs.length; i++) {
	        docPath[i] = listOfDocs[i].toString();
	        docName[i] = docPath[i].substring(58);
	    }
		
		Button loadDoc = new Button(docSelectorShell, SWT.PUSH);
		loadDoc.setText("Load");
		
		Combo docCombo = new Combo(docSelectorShell, SWT.PUSH);
		docCombo.setText("Doc");
		docCombo.setItems(docName);
		
		Button newDoc = new Button(docSelectorShell, SWT.PUSH);
		newDoc.setText("New");
		
		Button cancelDoc = new Button(docSelectorShell, SWT.PUSH);
		cancelDoc.setText("Cancel");
		
		Label warningLabel = new Label(docSelectorShell, SWT.PUSH);
		warningLabel.setText("Warning: Loading documents\nstill in experimental\nphase");
		
		loadDoc.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}

			public void widgetSelected(SelectionEvent event) {
				if (!docCombo.getText().equals("")) {
					String docName = docCombo.getText().substring(0, docCombo.getText().length() - 4);
					try {
						LilLexiDoc doc = loadDoc(docName);
						setDoc(doc);
						docSelectorShell.setVisible(false);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		
		});
		
		newDoc.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {}
			
			public void widgetSelected(SelectionEvent event) {
				LilLexiDoc doc = new LilLexiDoc();
				setDoc(doc);
				docSelectorShell.setVisible(false);
			}
		});
		
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
	
	public void setDoc(LilLexiDoc doc) {
		this.doc = doc;
	}
	public LilLexiDoc getDoc() {
		return this.doc;
	}
	
	public static LilLexiDoc loadDoc(String name) throws IOException {
		File myDoc = new File("docs/" + name + ".txt");
		FileReader fileReader = new FileReader(myDoc);
		BufferedReader myReader = new BufferedReader(fileReader);
		String fileLine = myReader.readLine();
		System.out.println(fileLine);
		LilLexiDoc lexiDoc = new LilLexiDoc();
		List<Glyph> glyphs = new LinkedList<Glyph>();
			// Add Glyphs
			while (!fileLine.equals("END GLYPHS")) {
				for (int i=0; i<fileLine.length(); i++) {
					glyphs.add(new Glyph(fileLine.charAt(i)));
				}
				fileLine = myReader.readLine();
			} 
			
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
			
			String[][] images = new String[100][3];
			i = 0;
			
			while (!fileLine.equals("RECTANGLES")) {
				
				images[i][0] = myReader.readLine();
				System.out.println(images[i][0]);
				images[i][1] = myReader.readLine();
				images[i][2] = myReader.readLine();
				fileLine = myReader.readLine();
				i++;
			}
			while (i < 100) {
				images[i][0] = "";
				images[i][1] = "";
				images[i][2] = "";
				i++;
			}
			
			String[][] rects = new String[100][4];
			i = 0;
			fileLine = myReader.readLine();
			
			while (!fileLine.equals("LINES")) {
				rects[i][0] = myReader.readLine();
				rects[i][1] = myReader.readLine();
				rects[i][2] = myReader.readLine();
				rects[i][3] = myReader.readLine();
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
			
			String[][] lines = new String[100][4];
			i = 0;
			fileLine = myReader.readLine();
			
			while (!fileLine.equals("CIRCLES")) {
				lines[i][0] = myReader.readLine();
				lines[i][1] = myReader.readLine();
				lines[i][2] = myReader.readLine();
				lines[i][3] = myReader.readLine();
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
			
			
			String[][] circles = new String[100][4];
			i = 0;
			fileLine = myReader.readLine();
			
			while (!fileLine.equals("TRIANGLES")) {
				circles[i][0] = myReader.readLine();
				circles[i][1] = myReader.readLine();
				circles[i][2] = myReader.readLine();
				circles[i][3] = myReader.readLine();
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
			
			String[][] triangles = new String[100][6];
			i = 0;
			fileLine = myReader.readLine();
			
			while (!fileLine.equals("EOF")) {
				triangles[i][0] = myReader.readLine();
				triangles[i][1] = myReader.readLine();
				triangles[i][2] = myReader.readLine();
				triangles[i][3] = myReader.readLine();
				triangles[i][4] = myReader.readLine();
				triangles[i][5] = myReader.readLine();
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
				
		lexiDoc = new LilLexiDoc(glyphs, 0, currSideMargin, currEdgeMargin,
				currFont, currColor, currBackgroundColor, currSize, numNewline, 
				currDepth, columnWidth, rowHeight, rows, rowsWidth, pages, rowWidth,
				images,rects, lines, circles, triangles);	
		myReader.close();
		return lexiDoc;
	}
	
	public void saveDoc(LilLexiDoc doc, String name) throws IOException {
		FileWriter myWriter = new FileWriter("docs/" + name + ".txt");
		List<Glyph> glyphs = doc.getGlyphs();
		String str = "";
		
		for (int i=0; i<glyphs.size(); i++) {
			str += glyphs.get(i).getChar();
		}
		str += "\n";
		str += "END GLYPHS\n";
		str += doc.getCurrIndex() + "\n";
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
	
		
		str += "IMAGES\n";
		int i = 0;
		String[][] images = doc.getImages();
		while (images[i][0] != "") {
			str += images[i][0] + "\n";
			str += images[i][1] + "\n";
			str += images[i][2] + "\n";
			i++;
		}

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
		str += "EOF";
		
		myWriter.write(str);
		myWriter.close();
		
		System.out.println("Successfully wrote to the file.");
		
     } 
}
