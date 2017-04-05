package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder.Case;

import org.omg.CORBA.Environment;

import dao.DaoAuteur;
import dao.DaoEditeur;
import dao.IDao;
import factory.FactoryDao;
import factory.FactoryDao.typeDao;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Auteur;
import metier.Editeur;
import metier.Livre;
import metierfx.AuteurFx;
import metierfx.EditeurFx;
import metierfx.LivreFx;

public class FXMLController implements Initializable {

	/*
	 * Table Editeur
	 */
	@FXML
	private TableView<EditeurFx> gIEdieurs;
	@FXML
	private TableColumn<EditeurFx, Integer> idEditeur;
	@FXML
	private TableColumn<EditeurFx, String> nomEditeur;
	@FXML
	private TableColumn<EditeurFx, Boolean> selectedEditeur = new TableColumn<>("Check");
	@FXML
	private TextField motCleEditeur;
	@FXML
	private TextField newNomEditeur;

	/*
	 * Table Auteur
	 */
	@FXML
	private TableView<AuteurFx> gIAuteurs;
	@FXML
	private TableColumn<AuteurFx, Integer> idAuteur;
	@FXML
	private TableColumn<AuteurFx, String> nomAuteur;
	@FXML
	private TableColumn<AuteurFx, String> prenomAuteur;
	@FXML
	private TableColumn<AuteurFx, Boolean> selectedAuteur = new TableColumn<>("Check");
	@FXML
	private TextField motCleAuteur;
	@FXML
	private TextField newNomAuteur;

	/*
	 * Table Livre
	 */
	@FXML
	private TableView<LivreFx> gILivre;
	@FXML
	private TableColumn<LivreFx, Integer> idLivre;
	@FXML
	private TableColumn<LivreFx, String> titreLivre;
	@FXML
	private TableColumn<LivreFx, String> auteur;
	@FXML
	private TableColumn<LivreFx, String> editeur;
	@FXML
	private TableColumn<LivreFx, Integer> pages;
	@FXML
	private TableColumn<LivreFx, Float> prix;
	// @FXML
	// private TableColumn<Livre, Boolean> selectedLivre = new
	// TableColumn<>("Check");
	@FXML
	private TextField motLivre;
	@FXML
	private TextField newNomLivre;

	/** les bouttons */
	@FXML
	private Button reEditeur;
	@FXML
	private Button deEditeur;
	@FXML
	private Button creEditeur;
	@FXML
	private Button upEditeur;
	@FXML
	private Button create;
	@FXML
	private Button close;
	public Stage stage;

	/* definir les dao */
	public IDao<Editeur> daoEditeur;
	public IDao<Auteur> daoAuteur;
	public IDao<Livre> daoLivre;
	private Auteur aut;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* initialise les dao */
		daoEditeur = (IDao<Editeur>) FactoryDao.getDAO("Editeur", typeDao.JPA);
		daoAuteur = (IDao<Auteur>) FactoryDao.getDAO("Auteur", typeDao.JPA);
		daoLivre = (IDao<Livre>) FactoryDao.getDAO("Livre", typeDao.JPA);

		if (location.getFile().contains("fitecMain.fxml") == true) {
			nomEditeur.setCellFactory(TextFieldTableCell.forTableColumn());
			nomEditeur.setOnEditCommit(new EventHandler<CellEditEvent<EditeurFx, String>>() {
				@Override
				public void handle(CellEditEvent<EditeurFx, String> event) {
					((EditeurFx) event.getTableView().getItems().get(event.getTablePosition().getRow()))
							.setNom(event.getNewValue());
				}
			});
			nomAuteur.setCellFactory(TextFieldTableCell.forTableColumn());
			nomAuteur.setOnEditCommit(new EventHandler<CellEditEvent<AuteurFx, String>>() {
				@Override
				public void handle(CellEditEvent<AuteurFx, String> event) {
					((AuteurFx) event.getTableView().getItems().get(event.getTablePosition().getRow()))
							.setNom(event.getNewValue());
				}
			});
			prenomAuteur.setCellFactory(TextFieldTableCell.forTableColumn());
			prenomAuteur.setOnEditCommit(new EventHandler<CellEditEvent<AuteurFx, String>>() {
				@Override
				public void handle(CellEditEvent<AuteurFx, String> event) {
					((AuteurFx) event.getTableView().getItems().get(event.getTablePosition().getRow()))
							.setPrenom(event.getNewValue());
				}
			});
		}

	}

	public void afficheAllEditeur() {
		CheckBox checkBox = new CheckBox();
		List<Editeur> listEdit = new ArrayList<>();
		listEdit = daoEditeur.selectAll();
		List<EditeurFx> listFx = new ArrayList<>();
		System.out.println("size" + listFx.size());
		for (Editeur ed : listEdit) {
			EditeurFx ediFX = new EditeurFx();
			ediFX.setId(ed.getId());
			System.out.println("idED " + ed.getId());
			ediFX.setNom(ed.getNom());
			ediFX.getSelected();
			listFx.add(ediFX);
		}
		System.out.println("size2" + listFx.size());
		// if (!gIEdieurs.getItems().isEmpty()) {
		gIEdieurs.getItems().clear();
		// }

		gIEdieurs.getSelectionModel().setCellSelectionEnabled(true);
		gIEdieurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedEditeur.setCellFactory(column -> new CheckBoxTableCell());
		selectedEditeur.setCellValueFactory(c -> c.getValue().getSelected());
		idEditeur.setCellValueFactory(new PropertyValueFactory<EditeurFx, Integer>("id"));
		nomEditeur.setCellValueFactory(new PropertyValueFactory<EditeurFx, String>("nom"));
		gIEdieurs.getItems().addAll(listFx);
	}

	public void afficheAllAuteur() {
		List<Auteur> listAuteur = daoAuteur.selectAll();
		List<AuteurFx> listFx = new ArrayList<>();
		for (Auteur auteur : listAuteur) {
			AuteurFx auteurFX = new AuteurFx();
			auteurFX.setId(auteur.getId());
			auteurFX.setNom(auteur.getNom());
			auteurFX.setPrenom(auteur.getPrenom());
			// auteurFX.getSelected();
			listFx.add(auteurFX);
		}
		//if (!gIAuteurs.getItems().isEmpty()) {
			gIAuteurs.getItems().clear();
		//}
		gIAuteurs.getSelectionModel().setCellSelectionEnabled(true);
		gIAuteurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedAuteur.setCellFactory(column -> new CheckBoxTableCell());
		selectedAuteur.setCellValueFactory(c -> c.getValue().getSelected());
		idAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, Integer>("id"));
		nomAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, String>("nom"));
		prenomAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, String>("prenom"));
		gIAuteurs.getItems().addAll(listFx);
	}

	public void createEditeur() {
		Editeur editor = new Editeur();
		editor.setNom(newNomEditeur.getText());
		if (!editor.getNom().isEmpty()) {
			daoEditeur.insert(editor);
		}

	}

	public void searchByKeyWord(ActionEvent event, String keyWord) {

		String action = ((Button) event.getSource()).getId();
		System.out.println(reEditeur.getId());
		System.out.println("Bouton pressé: " + ((Button) event.getSource()).getId());

		switch (action) {
		case "reEditeur":
			keyWord = motCleEditeur.getText();
			System.out.println(keyWord);
			if (!keyWord.isEmpty()) {
				/// searchByKeyWordEditeur(keyWord);

				CheckBox checkBox = new CheckBox();
				// DaoEditeur duo = new DaoEditeur();
				List<Editeur> listEdit = new ArrayList<>();
				listEdit = daoEditeur.searchLike(keyWord);
				List<EditeurFx> listFx = new ArrayList<>();
				for (Editeur ed : listEdit) {
					EditeurFx ediFX = new EditeurFx();
					ediFX.setId(ed.getId());
					System.out.println("idED " + ed.getId());
					ediFX.setNom(ed.getNom());
					ediFX.getSelected();
					listFx.add(ediFX);
				}
				if (!gIEdieurs.getItems().isEmpty()) {
					gIEdieurs.getItems().clear();
				}

				gIEdieurs.getSelectionModel().setCellSelectionEnabled(true);
				gIEdieurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				selectedEditeur.setCellFactory(column -> new CheckBoxTableCell());
				selectedEditeur.setCellValueFactory(c -> c.getValue().getSelected());
				idEditeur.setCellValueFactory(new PropertyValueFactory<EditeurFx, Integer>("id"));
				nomEditeur.setCellValueFactory(new PropertyValueFactory<EditeurFx, String>("nom"));
				gIEdieurs.getItems().addAll(listFx);

			} else {
				afficheAllEditeur();
			}
			System.out.println("testestestes case");
			break;
		case "reAuteur":
			keyWord = motCleAuteur.getText();
			System.out.println(keyWord);
			if (!keyWord.isEmpty()) {
				// searchByKeyWordAuteur(keyWordAut);

				CheckBox checkBox = new CheckBox();
				// DaoEditeur duo = new DaoEditeur();
				List<Auteur> listAuteur = new ArrayList<>();
				listAuteur = daoAuteur.searchLike(keyWord);
				List<AuteurFx> listFx = new ArrayList<>();
				for (Auteur auteur : listAuteur) {
					AuteurFx auteurFX = new AuteurFx();
					auteurFX.setId(auteur.getId());
					System.out.println("idED " + auteur.getId());
					auteurFX.setNom(auteur.getNom());
					auteurFX.setPrenom(auteur.getPrenom());
					auteurFX.getSelected();
					listFx.add(auteurFX);
				}
				if (!gIAuteurs.getItems().isEmpty()) {
					gIAuteurs.getItems().clear();
				}

				gIAuteurs.getSelectionModel().setCellSelectionEnabled(true);
				gIAuteurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				selectedAuteur.setCellFactory(column -> new CheckBoxTableCell());
				selectedAuteur.setCellValueFactory(c -> c.getValue().getSelected());
				idAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, Integer>("id"));
				nomAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, String>("nom"));
				prenomAuteur.setCellValueFactory(new PropertyValueFactory<AuteurFx, String>("prenom"));
				gIAuteurs.getItems().addAll(listFx);
			} else {
				afficheAllAuteur();
			}
			System.out.println("testestestes case");

			break;

		}

	}

	public void update(ActionEvent event) {
		String action = ((Button) event.getSource()).getId();
		System.out.println(reEditeur.getId());
		System.out.println("Bouton pressé: " + ((Button) event.getSource()).getId());

		switch (action) {
		case "upEditeur":
			if (gIEdieurs.getSelectionModel().isEmpty()) {
				System.err.println("not selected cell");

			} else {
				try {
					Editeur editor = new Editeur();
					editor.setNom(gIEdieurs.getSelectionModel().getSelectedItem().getNom());
					editor.setId(gIEdieurs.getSelectionModel().getSelectedItem().getId());
					daoEditeur.update(editor);
					afficheAllEditeur();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "upAuteur":
			if (gIAuteurs.getSelectionModel().isEmpty()) {
				System.err.println("not selected cell");

			} else {
				System.out.println("update auteur: "+gIAuteurs.getSelectionModel().getSelectedItem().getNom() +" ; "+gIAuteurs.getSelectionModel().getSelectedItem().getPrenom());
				try {
					Auteur auteur = new Auteur();
					auteur.setId(gIAuteurs.getSelectionModel().getSelectedItem().getId());
					auteur.setNom(gIAuteurs.getSelectionModel().getSelectedItem().getNom());
					auteur.setPrenom(gIAuteurs.getSelectionModel().getSelectedItem().getPrenom());
					System.out.println("auteur a modifier; "+auteur.getNom()+" ; "+auteur.getPrenom()+" ; "+auteur.getId());
					daoAuteur.update(auteur);
					afficheAllAuteur();
					System.out.println("nommmmm: "+daoAuteur.selectById(auteur).getNom()+ " ; " +daoAuteur.selectAll().get(0).getNom());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;
		}

	}

	public void supprime(ActionEvent event) {
		String action = ((Button) event.getSource()).getId();
		System.out.println("Bouton pressé: " + ((Button) event.getSource()).getId());
		int i;
		switch (action) {
		case "deEditeur":
			try {
				for (i = 0; i < gIEdieurs.getItems().size(); i++) {
					if (gIEdieurs.getItems().get(i).getSelected().getValue() == true) {
						Editeur editor = new Editeur();
						editor.setNom(gIEdieurs.getItems().get(i).getNom());
						editor.setId(gIEdieurs.getItems().get(i).getId());
						daoEditeur.delete(editor);
					}
				}
				afficheAllEditeur();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "deAuteur":
			try {
				for (i = 0; i < gIAuteurs.getItems().size(); i++) {
					if (gIAuteurs.getItems().get(i).getSelected().getValue() == true) {
						Auteur auteur = new Auteur();
						// auteur.setNom(gIAuteurs.getItems().get(i).getNom());
						auteur.setId(gIAuteurs.getItems().get(i).getId());
						daoAuteur.delete(auteur);
					}
				}
				afficheAllAuteur();
			} catch (Exception e) {
				System.err.println("message delete auteur: " + e.getLocalizedMessage());
				e.printStackTrace();
			}
			break;
		}

	}

	@FXML
	protected void handleSearch(ActionEvent event) {
		String keyWord = null;
		searchByKeyWord(event, keyWord);
	}

	@FXML
	protected void handleDelete(ActionEvent event) {
		supprime(event);
	}

	@FXML
	protected void handleCreate(ActionEvent event) {
		if (event.getSource() == creEditeur) {
			stage = (Stage) creEditeur.getScene().getWindow();
			showEditeurForm(stage);
		}
		if (event.getSource() == create) {
			createEditeur();
			((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
			// afficheAll();
		}
		// afficheAll();
		if (event.getSource() == close) {
			stage = (Stage) close.getScene().getWindow();
			stage.close();
			// ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		}

	}

	@FXML
	protected void handleUpdate(ActionEvent event) {
		// if (event.getSource() == upEditeur) {
		// System.out.println("je suis dans l'update");
		// if (gIEdieurs.getSelectionModel().isEmpty()) {
		// System.err.println("not selected cell");
		//
		// } else {
		// System.out.println(gIEdieurs.getSelectionModel().getSelectedItem().getId()
		// + " , "
		// + gIEdieurs.getSelectionModel().getSelectedItem().getNom());
		update(event);
		// }

	}

	public void showEditeurForm(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("editeurForm.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// initialize the confirmation dialog
			final Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			dialog.setScene(scene);
			// stage.setScene(scene);
			dialog.setTitle("Creer Editeur");
			dialog.show();
		} catch (IOException e) {
			System.out.println("Message Erreur: " + e.getLocalizedMessage());
		}

	}

	// class Delta {
	// double x, y;
	// }

}
