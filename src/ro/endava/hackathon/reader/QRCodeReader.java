/**
 * 
 */
package ro.endava.hackathon.reader;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.util.FileUtil;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author gnaftanaila
 * 
 */
public class QRCodeReader {
	public static void addMoreDataFromPng(Map<String, Person> personMap,
			Map<String, Activity> activityMap, String folderPath) {

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		List<String> files = FileUtil.getFilePaths(folderPath, ".png");

		for (String filePath : files) {
			String update = readQRCode(filePath, charset, hintMap);
			FileUtil.updatedEntities(update, activityMap, personMap);
		}
	}

	private static String readQRCode(String filePath, String charset, Map hintMap) {
		String result = "";
		try {
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
					new BufferedImageLuminanceSource(
							ImageIO.read(new FileInputStream(filePath)))));
			Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
			result = qrCodeResult.getText();
		}
		catch (Exception e) {
			
		}
		return result;
	}

}
