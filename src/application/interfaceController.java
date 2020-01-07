package application;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Log;
import org.jfree.util.LogContext;

public class interfaceController {
	 public Instances Inst;
	 String[] CentreGravité;
	 public AfficheInstancesController cont=new AfficheInstancesController();
	 public KnnWindowController cont2=new KnnWindowController();
/*---------------------------------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------------------------------*/
	//les composant de mon interface
	@FXML
	private TextArea AffichageTT;
	@FXML
    private Button DBScanButton;
	@FXML
	private Button lancer;
	@FXML
    private Button KNNButton;
	@FXML
	private ComboBox Fichiers;
    @FXML
    private Button ApriButton;
	@FXML
	private Button normalisationButton;
	@FXML
	private Button boxPlotButton;
	@FXML
	private TextField Nombre_Att;
	@FXML
	private TextField NomInst;
	@FXML
	private TextArea Type_Att;
	@FXML
	private TextArea Classes;
	 @FXML
	private TableColumn<MeasurementCT,String> Q1Att;
     @FXML
	private TableColumn<MeasurementCT,String> Q3Att;
     @FXML
    private TableColumn<MeasurementCT,String> MidRAtt;
    @FXML
    private TableColumn<MeasurementCT,String> meanAtt;
    @FXML
    private TableColumn<MeasurementCT,String> Attribut;
    @FXML
    private TableColumn<MeasurementCT,String> typeAtt;
    @FXML
    private TableColumn<MeasurementCT,String> MedianAtt;
    @FXML
    private TableColumn<MeasurementCT,String> ModeAtt;
    @FXML
    private TableView<MeasurementCT> attributTab;
    @FXML
    private TableColumn<MeasurementCT,String> MaxAtt;

    @FXML
    private TableColumn<MeasurementCT,String> MinAtt;
    @FXML
    private Button details;
    @FXML
    private CategoryAxis X= new CategoryAxis();
    @FXML
    private NumberAxis Y= new NumberAxis();
    @FXML
    public ObservableList<MeasurementCT> liste;
    @FXML
    private BarChart barChart;
    @FXML
    private Button AffichInstbutton;
    @FXML
    private TextArea CondVArea;

/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
	public void initialize() {
		// l'initialisation de la liste roulante
		Fichiers.getItems().addAll("breast-cancer.arff.gz", "contact-lenses.arff", "cpu.arff.gz","cpu.with.vendor.arff.gz","credit-g.arff.gz","diabetes.arff.gz","glass.arff.gz","ionosphere.arff.gz","iris.2D.arff","iris.arff.gz","labor.arff.gz","ReutersCorn-test.arff.gz","ReutersCorn-train.arff.gz");
		Fichiers.getSelectionModel().select("---");
		
		//initialisation du tableau
		MaxAtt.setCellValueFactory(new PropertyValueFactory<>("max")); 
		MinAtt.setCellValueFactory(new PropertyValueFactory<>("min")); 
		Q1Att.setCellValueFactory(new PropertyValueFactory<>("q1")); 
		Q3Att.setCellValueFactory(new PropertyValueFactory<>("q3"));
		MidRAtt.setCellValueFactory(new PropertyValueFactory<>("midR")); 
	    typeAtt.setCellValueFactory(new PropertyValueFactory<>("type")); 
	   // System.out.println(meanAtt);
		MedianAtt.setCellValueFactory(new PropertyValueFactory<>("median")); 
		meanAtt.setCellValueFactory(new PropertyValueFactory<>("mean")); 
		ModeAtt.setCellValueFactory(new PropertyValueFactory<>("mode"));
		Attribut.setCellValueFactory(new PropertyValueFactory<>("name"));
		//l'action de cliquer sur une cellule
	}
/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
	//l'action du button
	public void AfficheResulteButtonPushed(ActionEvent event) throws Exception {
		attributTab.getItems().clear();
		if((Fichiers.getSelectionModel().getSelectedItem().equals("---"))==false)
		{   String type;
		    // lecture du fichier choisi 
			DataSource p= new DataSource("//usr//share//doc//weka//examples//"+Fichiers.getSelectionModel().getSelectedItem());
            Inst = p.getDataSet();
            String S=String.valueOf(Inst);
            
            //l'affichage du banshmark
            AffichageTT.setText(S);
            
            //l'affichage du nombre d'attributs
            Nombre_Att.setText(String.valueOf(Inst.numAttributes()));
            
            //l'affichage du nombre d'instances
            NomInst.setText(String.valueOf(Inst.numInstances()));
            
            //l'affichage de la table
        	MeasurementCT meas=null;
        	CentreGravité=new String[Inst.numAttributes()];
        
            for(int j=0;j<Inst.numAttributes();j++)
            {
            	 meas= new MeasurementCT(j,Inst);
            	 meas.CalculMeasure(Inst);
            	if(Inst.attribute(j).type()==0)
            	{    
                    type="numerique";
                    CentreGravité[j]=meas.mean;// centre de gravité global
            	}
                else 
                {
                type="nominal";
                CentreGravité[j]=meas.mode;// centre de gravité global
                }
            	 
            	
            	meas.type=type;
            	
                attributTab.getItems().add(meas);
         
            
            }
          
            attributTab.setOnMouseClicked(e -> {
            	 AfficheHistogramme();	 
            
              });
             //---------
            
            //classification
            if(Inst.attribute(Inst.numAttributes()-1).isNominal())
            {  
               
               String classification="Il y a "+Inst.attribute(Inst.numAttributes()-1).numValues()+" classes:\n";
               Hashtable ht=meas.calculFrequence(Inst);
               Set keys=ht.keySet();
               //obtenir un iterator des clés
           	   Iterator itr = keys.iterator();
           	 while (itr.hasNext())
        	 {
           		 String key;
           		 key=(String) itr.next();
           		 classification=classification+key+" : "+ht.get(key)+" instances\n";
        	 }
              /* for(int j=0;j<i.attribute(i.numAttributes()-1).numValues();j++)
               {
                classification=classification+i.attribute(i.numAttributes()-1).value(j)+"\n";
               }*/
               Classes.setText(classification);
            }
             else
             {
            	 Classes.setText("Dans ce cas il n'y a pas une classification. \nC'est une Estimation"); 
             }
            
           String resultat= conditionV();
           this.CondVArea.setText(resultat);
		}//fin du if

	}// fin de la methode AfficheResulteButtonPushed
/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
public void AfficheHistogramme()
{
	
	    if(attributTab.getSelectionModel().getSelectedItem()!=null)// pour tester si on a pas cliqué hors table
	    {	
	     ArrayList AL= new ArrayList();
	     String text=attributTab.getSelectionModel().getSelectedItem().name;// le nom de l'attribut
		 int indice=attributTab.getSelectionModel().getSelectedIndex();// l'indice de l'attribut
          Label secondLabel = new Label(text);
        // MeasurementCT meas= new MeasurementCT(indice,this.Inst);
          MeasurementCT meas=attributTab.getSelectionModel().getSelectedItem();
          
          Hashtable ht= meas.calculFrequence(Inst);// table des frequences
         // Hashtable ht= meas.trierHashtable(result,AL);
          StackPane secondaryLayout = new StackPane();
          Scene secondScene = new Scene(secondaryLayout, 1000,500);
         // New window (Stage)
          Stage newWindow = new Stage();
          Stage primaryStage=new Stage();
         /**********Remplissage du dataset**************/
         XYChart.Series<String, Integer>series=new XYChart.Series<>();
         Set keys=ht.keySet();
     	//obtenir un iterator des clés
     	 Iterator itr = keys.iterator();
     	 if(Inst.attribute(attributTab.getSelectionModel().getSelectedItem().attributIndice).isNumeric())
     	 {
     		 histoDonnees hd;
     		ht= meas.trierHashtable(ht,AL);
     		hd=meas.Discretisation(Inst,ht,AL);
     	    ht=hd.ht;
     	    AL=hd.AL;
     		 for(int j=0;j<AL.size();j++)
     		 {
     	
     	 series.getData().add(new XYChart.Data<String,Integer>(String.valueOf(AL.get(j)),(Integer)ht.get(String.valueOf(AL.get(j)))));
     		 }
     		
     	 }
     	 else
     	 {
     		while (itr.hasNext())
        	 {
        	String key=(String) itr.next();
        	
        	 series.getData().add(new XYChart.Data<String,Integer>(key,(Integer)ht.get(key)));
        	 }
     	 }
     	 /*********************************************/
     	barChart=new BarChart(X,Y);
         barChart.getData().add(series);
         newWindow.setTitle(text);
         newWindow.setScene(secondScene);
         secondaryLayout.getChildren().add(barChart);
         // Set position of second window, related to primary window.
         newWindow.centerOnScreen();
         newWindow.show();
	    }
}
/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
public void normalisation() throws IOException
{ MeasurementCT meas;
	for(int i=0;i<attributTab.getItems().size();i++)
	{
		meas=attributTab.getItems().get(i);
		if(meas.type.equals("numerique"))
		{
			meas.normalization(Inst);
			attributTab.getItems().set(i, meas);
			CentreGravité[i]=meas.mean;
		}
	}

}
	    
/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
public Hashtable calculatData()
{
	 String text=attributTab.getSelectionModel().getSelectedItem().name;
	 int indice=attributTab.getSelectionModel().getSelectedIndex();
     DefaultCategoryDataset dcd=new DefaultCategoryDataset();
     MeasurementCT meas= new MeasurementCT(indice,this.Inst);
     Hashtable ht= meas.calculFrequence(Inst);
     return ht;
}
/*-----------------------------------------------------------------------------------------------------//
-------------------------------------------------------------------------------------------------------*/
public ArrayList  calculatTableTrie(int indice)
{
MeasurementCT meas= new MeasurementCT(indice,this.Inst);
return meas.valeurTrie(this.Inst);
	
}
/*-----------------------------------------------------------------------------------------------------//
-------------------------------------------------------------------------------------------------------*/
public String conditionV()
{  MeasurementCT meas;
   String resultat="";
double mode, mean,median;
	for(int i=0;i<attributTab.getItems().size();i++)
	{
		meas=attributTab.getItems().get(i);
	
		if(meas.type.equals("numerique"))
			{
			mean=Double.parseDouble(meas.mean);
			mode=Double.parseDouble(meas.mode);
			median=Double.parseDouble(meas.median);
			     if(Math.abs((mean-mode)-3*(mode-median))<0.2)
					{
			          resultat=resultat+meas.name+"\n";
				    }
			     
			}
	}
	return resultat;
}
/*------------------------------------------------------------------------------------------------------//
//-----------------------------------------------------------------------------------------------------*/
/*affiche les instances apres le remplissage de les valeurs manquantes */
void affiche()
{
	for (int i=0;i<Inst.numInstances();i++)
	{
		System.out.println(Inst.instance(i));
	}
}
/*------------------------------------------------------------------------------------------------------//
 -------------------------------------------------------------------------------------------------------*/
/* la methode qui affiches les boxplot */
 public void boxPlot()
 {
	boxPlot box=new boxPlot();
	box.afficheboxPlot(this);

 }
 /*------------------------------------------------------------------------------------------------------//
 -------------------------------------------------------------------------------------------------------*/
 public void FileInstances(Instances Inst,int o) throws IOException
 {
	Instance inst;
	File f; 
	if(o==0)
	f = new File("WithoutMissingValues.txt");
	else
		f = new File("NormalisedInstances.txt");
	if(f.exists()==false)
	{
	f.createNewFile();
	}
	FileWriter writer = new FileWriter(f);
	 for(int i=0;i<Inst.numInstances();i++)
	 {
		 inst=Inst.instance(i);
		 writer.write(inst.toString()+"\n");
	 }
	 writer.close();
	
 }
 public void LancerAffiche() throws IOException
 {
	 FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("AfficheInstancesTAb.fxml"));
	 Parent root1=(Parent) fxmlLoader.load();
	 AfficheInstancesTAbController cont= fxmlLoader.getController();
	 cont.fillTableView(this.Inst);
	 Stage stage=new Stage();
	 stage.setTitle("Instances");
	 stage.setScene(new Scene(root1));
	 stage.show();
 }
 public void InstancesToFichier() throws IOException
 {
	 Instance inst;
	 File f;
	 f=new File("Instances.txt");
	 if(f.exists()==false)
		{
		f.createNewFile();
		
		}
	FileWriter writer = new FileWriter(f);
	for(int i=0;i<Inst.numInstances();i++)
	 {
		 inst=Inst.instance(i);
		 String s="";
		 for(int j=0;j<Inst.numAttributes();j++)
		 {
			 if(Inst.attribute(j).isNumeric())
			 {
			 s=s+Inst.attribute(j).name()+"::"+String.valueOf(inst.value(j))+" ";
			 }
			 else
			 {
				 
				 s=s+Inst.attribute(j).name()+"::"+inst.stringValue(j)+" "; 
			 }
		 }
		 writer.write(s+"\n");
	 }
	 writer.close();
	 
 }
 public void AprioriAction() throws IOException
 {

	 InstancesToFichier();
	 FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("AprioriWindow.fxml"));
	 Parent root1=(Parent) fxmlLoader.load();
	 AprioriWindowController cont= fxmlLoader.getController();
	 Stage stage=new Stage();
	 stage.setTitle("Apriori");
	 stage.setScene(new Scene(root1));
	 stage.show();
	
 }
 public void KNNAction() throws IOException
 {
	 FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("KnnWindow.fxml"));
	 Parent root1=(Parent) fxmlLoader.load();
	 KnnWindowController cont= fxmlLoader.getController();
	 cont.Affectinst(this.Inst);
	 Stage stage=new Stage();
	 stage.setTitle("Knn");
	 stage.setScene(new Scene(root1));
	 stage.show();
 }
 //------------------------------------------------------------------------------------------------//
 public void DBScanAction() throws IOException
 {
	/* DBScan db=new DBScan(this.Inst);
	 db.setEps((float)1);
	 db.setMinPts(3);
      db.DBScanAction();
	 //System.out.println("Nb clusers"+db.clusters.size());
	 for(int i=0;i<db.clusters.size();i++)
	 {
		 System.out.println("Cluster "+(i+1)+" :"+db.clusters.get(i).members.size());
		 
		
	 }
		*/ 
	 
	 FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("DbScanWindow.fxml"));
	 Parent root1=(Parent) fxmlLoader.load();
	 DbScanWindowController cont= fxmlLoader.getController();
	 cont.affectDonnees(this.CentreGravité,this.Inst);
	 Stage stage=new Stage();
	 stage.setTitle("DBScan");
	 stage.setScene(new Scene(root1));
	 stage.show();
 }
}
