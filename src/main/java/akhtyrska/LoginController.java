package akhtyrska;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import beans.User;
import dao.UserDAO;

public class LoginController {

	@FXML
	private TextField loginField;
	@FXML
	private TextField passwordField;	

	public void onOKClick() throws SQLException, IOException {
		Stage stage = (Stage)passwordField.getScene().getWindow();
		System.out.println(loginField.getText());
		System.out.println(passwordField.getText());
		UserDAO userDAO = new UserDAO();

		User u = userDAO.authenticateUser(loginField.getText(),
				passwordField.getText());
		if (u != null) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/fxml/HelpPro.fxml"));
			BorderPane pane = (BorderPane) loader.load();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();

		}
	}

}
