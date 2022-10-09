import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LilLexiSpellCheck {
	List<String> words;
	List<String> errors;
	List<Integer> errorsIndex;
	LilLexiDoc currentDoc;
	
	
	public LilLexiSpellCheck(LilLexiDoc currentDoc) throws FileNotFoundException {
		this.currentDoc = currentDoc;
		words = new LinkedList<String>();
		errors = new LinkedList<String>();
		errorsIndex = new LinkedList<Integer>();
		Scanner scanner = new Scanner(new File("src/dictionary.txt"));
		while (scanner.hasNextLine()) {
		   String line = scanner.nextLine();
		   words.add(line);
		}
	}
	
	public void setDocument(LilLexiDoc currentDoc) {
		this.currentDoc = currentDoc;
	}
	
	public void checkWords() {
		System.out.println("Spell check...");
		List<Glyph> glyphs = currentDoc.getGlyphs();
		String currWord = "";
		int i = 0;
		for (Glyph g: glyphs) {
			if (g.getChar() == ' ' || g.getChar() == '\b' || i == glyphs.size()-1) {
				if (!words.contains(currWord)) {
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
		toString();
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	
	public String toString() {
		String str = "The following words were not found in our dictionary...\n";
		str += "LINE:   COLUMN:	WORD: \n";
		for (int i=0; i < errors.size(); i++) {
			String word = errors.get(i);
			int line = errorsIndex.get(i) % (currentDoc.getUI().getRowWidth());
			int column = errorsIndex.get(i) / (currentDoc.getUI().getRowWidth());
			str += line + "		" + column + "		" + word + "\n";
		}
		return str;
	}
}
