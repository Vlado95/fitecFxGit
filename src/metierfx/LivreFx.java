package metierfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import metier.Livre;

public class LivreFx extends Livre{

	private	 BooleanProperty  selected = new SimpleBooleanProperty();

	public BooleanProperty getSelected() {
		return selected;
	}

	public void setSelected(BooleanProperty selected) {
		this.selected = selected;
	}

}
