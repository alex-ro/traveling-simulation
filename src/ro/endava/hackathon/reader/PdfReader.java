package ro.endava.hackathon.reader;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.util.FileUtil;

import java.util.List;
import java.util.Map;

import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * @author gnaftanaila
 *
 */
public class PdfReader {
	public static void addMoreDataFromPdf(Map<String, Person> personMap,
			Map<String, Activity> activityMap, String folderPath) {
		List<String> filePaths = FileUtil.getFilePaths(folderPath, ".pdf");
		
		try {
			for (String pdf : filePaths) {
				System.out.println("path: " + pdf);
				StringBuilder text = new StringBuilder();
				com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(pdf);
				PdfReaderContentParser parser = new PdfReaderContentParser(reader);
				TextExtractionStrategy strategy;
		        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
		            text.append(strategy.getResultantText());
		        }
		        reader.close();
		        // TODO Do something with text!
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
