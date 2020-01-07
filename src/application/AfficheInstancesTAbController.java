package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
import weka.core.Instances;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import weka.core.Instance;
import weka.core.Instances;
public class AfficheInstancesTAbController {
	@FXML
	private AnchorPane InstanceView;
	@FXML
	private TableView InstancesTable;
		public void initialize() {}
		public void fillTableView(Instances data){
	        ArrayList<TableColumn<Instance, String>> atrributes = new ArrayList<>();
	        ArrayList <Instance> instances = new ArrayList<>();
	        int i=0;
	        for ( i =0;i<data.numInstances();i++)
	        {
	            instances.add(data.instance(i));

	        }
	        ObservableList<Instance> tableContent = FXCollections.observableArrayList(instances);


	        for ( i = 0; i < data.numAttributes(); i++) {
	            TableColumn<Instance, String> column = new TableColumn<Instance,String>(data.attribute(i).name());
	            final int attIndex = i ;
	            column.setCellValueFactory(cellData ->
	                    new SimpleStringProperty(cellData.getValue().toString(attIndex)));
	            atrributes.add(column);

	        }
	        InstancesTable.getColumns().clear();
	        InstancesTable.getColumns().addAll(atrributes);
	        InstancesTable.setItems(tableContent);
	        InstancesTable.setVisible(true);

	    }
}
