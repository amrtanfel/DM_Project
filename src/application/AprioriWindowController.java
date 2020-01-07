package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;

public class AprioriWindowController {
	@FXML
    private TextArea associationArea;
	@FXML
    private TextArea FrequentArea;
	@FXML
	private TextField SuppTxt;
	@FXML
	private TextField Conftxt;
	@FXML
	private Button LancerButton;
	public void initialize() {
	}
	public void PressButton() throws IOException
	{
		associationArea.clear();
		int suppMin=Integer.parseInt(SuppTxt.getText());
		int confMin=Integer.parseInt(Conftxt.getText());
		TransactionTable tb=new TransactionTable("//home//anfel//eclipse-workspace//DM_TP//Instances.txt");
	     // la creation des Li
	      
	      ArrayList <Hashtable> al=tb.FrequentItems(suppMin);
	      AfficheFrequentItems(al);
	      //generation of associations rules from frequent itmset
	     ArrayList<AssRule>Al= tb.AssoRule(al,confMin);
	   
	     for(int i=0;i<Al.size();i++)
	     {
	    	 associationArea.appendText(Al.get(i).LImplec+" => "+Al.get(i).RImplec+"      "+Al.get(i).conf+"%\n\n");
	     }
	}
	public void afficheHashtable(Hashtable ht)
	{
		Set keys=ht.keySet();
		Iterator it=keys.iterator();
		String key;
		while(it.hasNext())
		{
			key=(String)it.next();
			FrequentArea.appendText(key+" :    "+ht.get(key)+"\n");
		}
	}
	public void AfficheFrequentItems(ArrayList <Hashtable>al)
	{
		
		for(int i=0;i<al.size();i++)
		{
			FrequentArea.appendText("\nL"+(i+1)+"\n");
			this.afficheHashtable(al.get(i));
		}
	}

}
