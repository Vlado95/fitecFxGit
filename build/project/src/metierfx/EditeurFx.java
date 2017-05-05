package metierfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import metier.Editeur;

public class EditeurFx extends Editeur{
	
	private	 BooleanProperty  selected = new SimpleBooleanProperty();

	public BooleanProperty getSelected() {
		return selected;
	}

	public void setSelected(BooleanProperty selected) {
		this.selected = selected;
	}



}
