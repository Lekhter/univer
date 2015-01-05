package akhtyrska;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

public class TestController {
	@FXML
	private HTMLEditor htmlEditor;

	
	public void generatePDF(ActionEvent event) {
		final Document document = new Document(PageSize.A4);

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("C:/Work/loremipsum.pdf"));
			document.open();
			HTMLWorker hw = new HTMLWorker(document);
			hw.parse(new StringReader(htmlEditor.getHtmlText()));
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}
	
	public void generatePDF2(ActionEvent event) {
		final Document document = new Document(PageSize.A4);

		try {
			runConverter(new File("C:/Work/loremipsum111.pdf"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}

	private void runConverter(File output) throws IOException {

		Dimension format = PD4Constants.A4;
		boolean landscapeValue = false;
		int topValue = 10;
		int leftValue = 10;
		int rightValue = 10;
		int bottomValue = 10;
		String unitsValue = "mm";
		String proxyHost = "";
		int proxyPort = 0;

		int userSpaceWidth = 780;

		
			java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

			if (proxyHost != null && proxyHost.length() != 0 && proxyPort != 0) {
				System.getProperties().setProperty("proxySet", "true");
				System.getProperties().setProperty("proxyHost", proxyHost);
				System.getProperties().setProperty("proxyPort", "" + proxyPort);
			}

			PD4ML pd4ml = new PD4ML();

			try {
				pd4ml.setPageSize(landscapeValue ? pd4ml
						.changePageOrientation(format) : format);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (unitsValue.equals("mm")) {
				pd4ml.setPageInsetsMM(new Insets(topValue, leftValue,
						bottomValue, rightValue));
			} else {
				pd4ml.setPageInsets(new Insets(topValue, leftValue,
						bottomValue, rightValue));
			}

			pd4ml.setHtmlWidth(userSpaceWidth);

			pd4ml.render(new StringReader(htmlEditor.getHtmlText()), fos);
		
	}
}
