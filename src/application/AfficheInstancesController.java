package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;

public class AfficheInstancesController {

    @FXML
    private TextArea InstancesArea;
	public void initialize() {}
	public void AfficheInst(String inst)
	{
		
		InstancesArea.setText(inst);
	}

}
