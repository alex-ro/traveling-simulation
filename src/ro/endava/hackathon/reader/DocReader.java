package ro.endava.hackathon.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.util.FileUtil;

public class DocReader {
	public static void addMoreDataFromDoc(Map<String, Person> personMap, List<Activity> activities, String folderPath) throws FileNotFoundException, IOException {
		for (String filepath : FileUtil.getFilePaths("D:\\additional-files", "docx")) {
			System.out.println("path" + filepath);
			XWPFDocument docx = new XWPFDocument(new FileInputStream(filepath));
			XWPFWordExtractor wordxExtractor = new XWPFWordExtractor(docx);
			String text = wordxExtractor.getText();
			wordxExtractor.close();
			// TODO Do something with text!
		}
	}
}
