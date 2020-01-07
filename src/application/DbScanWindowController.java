package application;

import java.util.Iterator;
import java.util.Set;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import weka.core.Instance;
import weka.core.Instances;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class DbScanWindowController {
	@FXML
	private TableView<ClusterTab>ClustersTab;
	@FXML
	private TableColumn <ClusterTab,String>ClusteColumn;
	@FXML
	private TableColumn <ClusterTab,Integer>tailleColumn;
	@FXML
	private TableView<InstanceClusTab>InstancesTab;
	@FXML
	private TableColumn<InstanceClusTab,String> InstanceColumn;
	@FXML
	private TextField EpsTxt;
	@FXML
	private TextField MinPtsTxt;
	@FXML
	private TextField  TTxt;
	@FXML
	private TextField WTxt;
	@FXML
	private Button LanerButton;
	String[] CGG;
	Instances inst;
	public void initialize() {
		ClusteColumn.setCellValueFactory(new PropertyValueFactory<>("numClust")); 
		tailleColumn.setCellValueFactory(new PropertyValueFactory<>("taille")); 
		InstanceColumn.setCellValueFactory(new PropertyValueFactory<>("instC"));
	}
	public void affectDonnees(String[]G,Instances ins)
	{
		CGG=G;
		inst=ins;
	}
	public void LancerAction()
	{
		ClustersTab.getItems().clear();
		DBScan D=new DBScan(inst);
		D.setEps((float)(Double.parseDouble(EpsTxt.getText())));
		D.setMinPts(Integer.parseInt(MinPtsTxt.getText()));
		D.DBScanAction();
		TTxt.setText(String.valueOf(D.InertieInterclasse(CGG)));
		WTxt.setText(String.valueOf(D.InertieIntraclasse()));
		for(int i=0;i<D.clusters.size();i++)
		{
			ClusterTab cl=new ClusterTab(String.valueOf((i+1)),D.clusters.get(i).members.size());
			ClustersTab.getItems().add(cl);
			
		}
		ClustersTab.setOnMouseClicked(e -> {
			afficheClusterContent(D);	 
       
         });
		
	}
	public void afficheClusterContent(DBScan D)
	{
		if(ClustersTab.getSelectionModel().getSelectedItem()!=null)// pour tester si on a pas cliqué hors table
	    {	InstancesTab.getItems().clear();
			Integer indice=Integer.parseInt(ClustersTab.getSelectionModel().getSelectedItem().numClust)-1;
			Cluster c=D.clusters.get(indice);
			Set<Instance> keys=c.members.keySet();
			Iterator <Instance>itr=keys.iterator();
			Instance key;
			while(itr.hasNext())
			{
				key=itr.next();
				InstanceClusTab ct=new InstanceClusTab(c.InstanceCluster(key));
				InstancesTab.getItems().add(ct);
				
			}
			
	    }
	}

}
