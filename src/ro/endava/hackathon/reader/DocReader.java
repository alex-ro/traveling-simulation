package ro.endava.hackathon.reader;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.util.FileUtil;

import java.io.FileInputStream;
import java.util.Map;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocReader {
	public static void addMoreDataFromDoc(Map<String, Person> personMap, Map<String, Activity> activityMap, String folderPath) {
		for (String filepath : FileUtil.getFilePaths(folderPath, ".docx")) {
			try {
				XWPFDocument docx = new XWPFDocument(new FileInputStream(filepath));
				XWPFWordExtractor wordxExtractor = new XWPFWordExtractor(docx);
				String text = wordxExtractor.getText();
				wordxExtractor.close();
				FileUtil.updatedEntities(text.toString(), activityMap, personMap);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
