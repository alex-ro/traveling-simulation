/**
 * 
 */
package ro.endava.hackathon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLStreamException;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.service.TravelingParserService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author gnaftanaila
 * 
 */
public class Main2 {

	/**
	 * @param args
	 * @throws XMLStreamException
	 * @throws IOException 
	 * @throws WriterException 
	 * @throws NotFoundException 
	 */
	public static void main(String[] args) throws XMLStreamException, WriterException, IOException, NotFoundException {
		/*TravelingParserService travelingParserService = new TravelingParserService();

		Integer journey = travelingParserService
				.getJouneyDuration("D:\\dataset.xml");

		List<Activity> activityList = travelingParserService
				.getActivities("D:\\dataset.xml");

		List<Person> personList = travelingParserService.getPersons(
				"D:\\dataset.xml", activityList);*/

		String qrCodeData = "Hello World!";
		String filePath = "D:\\0006.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		/*createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		System.out.println("QR Code image created successfully!");*/

		System.out.println("Data read from QR Code: "
				+ readQRCode(filePath, charset, hintMap));

	}

	public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
		return qrCodeResult.getText();
	}

}
