/**
 * 
 */
package ro.endava.hackathon.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gnaftanaila
 *
 */
public class FileUtil {
	
	public static List<String> getFilePaths(String folderPath, String extension) {
		List<String> filePaths = new ArrayList<>();
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	String fileName = listOfFiles[i].getName();
		    	if (listOfFiles[i].isFile() && fileName.contains(extension)) {
		    		filePaths.add(folderPath+"\\" + fileName);
			      } 
		    }
		return filePaths;
	}

}
