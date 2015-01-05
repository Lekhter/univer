package akhtyrska;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;

public class HelpProController extends BorderPane implements Initializable {

	@FXML
	private HTMLEditor htmlEditor;
	@FXML
	private TreeView<String> treeView;

	public void generatePDF(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save PDF File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"PDF files (*.pdf)", "*.pdf");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog((Stage) htmlEditor.getScene()
				.getWindow());
		System.out.println(file.getAbsolutePath());

		final Document document = new Document(PageSize.A4);

		try {
			runConverter(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}

	public void saveAsFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save HTML File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"HTML files (*.html)", "*.html");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog((Stage) htmlEditor.getScene()
				.getWindow());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(htmlEditor.getHtmlText());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void newFile(ActionEvent event) {
		htmlEditor.setHtmlText("");
	}

	public void openFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open HTML File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"HTML files (*.html)", "*.html");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog((Stage) htmlEditor.getScene()
				.getWindow());

		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			String s = null;
			StringBuilder content = new StringBuilder(1024);
			while ((s = br.readLine()) != null) {
				content.append(s);
			}
			htmlEditor.setHtmlText(content.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue,
					rightValue));
		} else {
			pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue,
					rightValue));
		}

		pd4ml.setHtmlWidth(userSpaceWidth);

		pd4ml.render(new StringReader(htmlEditor.getHtmlText()), fos);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItem<String> root1 = new TreeItem<String>("Introduction");
		TreeItem<String> overview = new TreeItem<String>("Overview");
		TreeItem<String> systemReq = new TreeItem<String>("System Requirements");
		TreeItem<String> legalInfo = new TreeItem<String>("Legal Information");
		systemReq.getChildren().addAll(
				new TreeItem<String>("System Requirements"),
				new TreeItem<String>("System Requirements"));
		legalInfo.getChildren().addAll(
				new TreeItem<String>("Legal Information"),
				new TreeItem<String>("Legal Information"));
		root1.getChildren().addAll(overview, systemReq, legalInfo);
		root1.setExpanded(true);
		treeView.setRoot(root1);

	}
}
