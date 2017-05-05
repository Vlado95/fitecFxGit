package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.DaoUser;
import dao.IDao;
import factory.FactoryDao;
import factory.FactoryDao.typeDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import metier.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	@FXML
	private Label fxError;

	@FXML
	private Button btnOK;

	public Stage stage;

	public void initlogin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();

	}

	public void mainFrame() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("fitecMain.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Bibliothéque");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			this.stage = primaryStage;
			initlogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleLoginBtnOK(ActionEvent event) {
		boolean status = authentification();
		if (event.getSource() == btnOK) {
			stage = (Stage) btnOK.getScene().getWindow();
			if (password.getText().isEmpty() || email.getText().isEmpty()) {
				fxError.setText("Login and password fields must be filled");
			} else if (status == true) {
				mainFrame();

			}
		}

	}

	protected boolean authentification() {
		boolean status = false;

		//DaoUser daou = (DaoUser) FactoryDao.getDAO("User", FactoryDao.typeDao.JPA);
	//	IDao<User> daou = (IDao<User>) FactoryDao.getDAO("User", FactoryDao.typeDao.JPA);
		// DaoUser daou = new DaoUser();
		User user = new User();
		user.setEmail(email.getText());
		user.setPassword(password.getText());
		// user = dao.selectLogin(user);
		//user = daou.selectLogin(user);
		user = FactoryDao.authenticate(user, typeDao.JPA);
		if (user.getId() == 0) {
			fxError.setText("login ou mot de passe incorrecté");

		} else {
			status = true;
			fxError.setText("vous etes connectés");
		}
		return status;

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		launch(args);
	}
}
