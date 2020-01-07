package application;

import java.util.ArrayList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import weka.core.Instance;
import weka.core.Instances;
import javafx.scene.control.TextArea;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class KnnWindowController {
	@FXML
	private TableView<KnnTab> resultTab;
	@FXML
	private TableColumn<KnnTab,String> instanceColumn;
	@FXML
	private TableColumn<KnnTab,String> ClassifColumn;
	@FXML
	private TableColumn<KnnTab,String> KnnColumn;
	@FXML
	private TextField KValue;
	@FXML
	private TextField PourValue;
	@FXML
	private TextArea DetailText;
	@FXML
	private Button LancerButton;
	Instances inst;
	public void initialize() {
		instanceColumn.setCellValueFactory(new PropertyValueFactory<>("instance")); 
		ClassifColumn.setCellValueFactory(new PropertyValueFactory<>("Classif")); 
		KnnColumn.setCellValueFactory(new PropertyValueFactory<>("KnnClassif"));
	}
	public void Affectinst(Instances inst)
	{
		this.inst=inst;
	}
	public void LancerAction()
	{
		int k,pourc,nbTraining,nbTest;
		resultTab.getItems().clear();
		pourc=Integer.parseInt(PourValue.getText());
		k=Integer.parseInt(KValue.getText());
		 KNN knn=new KNN(this.inst,pourc);
		 nbTraining=knn.Training.size();
		 nbTest=knn.Test.size();
		 knn.Kset(k);
		 ArrayList<String> classif=knn.ClassificationTest();
		 for(int i=0;i<classif.size();i++)
		 {
			 Instance instc=knn.Test.get(i);
			 String S="";
			 for(int j=0;j<instc.numAttributes()-1;j++)
			 {
				 if(instc.attribute(j).isNominal())
					 S=S+instc.stringValue(j)+" ";
				 else
					 S=S+String.valueOf(instc.value(j))+" ";
			 }
			 System.out.println(S);
			 
			 KnnTab kn=new KnnTab(S,instc.stringValue(instc.numAttributes()-1),classif.get(i));
			 resultTab.getItems().add(kn);
		 }
		 float tEreur=knn.TautErreur(classif);
		 String str="Training: "+nbTraining+" instances\nTest: "+nbTest+" instances\nTaut d'erreur: "+tEreur;
		 DetailText.setText(str);;
	}

}
