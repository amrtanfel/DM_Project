package application;

import java.awt.Font;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import weka.core.Instances;

public class boxPlot {
public void afficheboxPlot(interfaceController cont)
{ 
	int i;
	 List al;
	 Stage newWindow = new Stage();
    Stage primaryStage=new Stage();
	 StackPane secondaryLayout = new StackPane();
    Scene secondScene = new Scene(secondaryLayout, 1000,500);
	 DefaultBoxAndWhiskerCategoryDataset dataset  = new DefaultBoxAndWhiskerCategoryDataset();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
    renderer.setFillBox(false);
    renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
	 for(i=0;i<cont.Inst.numAttributes();i++)
	 {
		 if(cont.Inst.attribute(i).isNumeric())
		 {
			 System.out.println(i);
			 al=cont.calculatTableTrie(i);
			 dataset.add(al,0,cont.Inst.attribute(i).name());
			 
			 
		 }
	 }        
	 CategoryPlot plot= new CategoryPlot (dataset, xAxis, yAxis, renderer);       
	JFreeChart chart = new JFreeChart(
	            "",
	            new Font("SansSerif", Font.BOLD, 14),
	            plot,
	            true
	        );
	        ChartPanel chartPanel = new ChartPanel(chart);
	        SwingNode chartSwingNode = new SwingNode();
	        chartSwingNode.setContent(chartPanel);


	        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
	        newWindow.setTitle("BoxPlots");
	        newWindow.setScene(secondScene);
	        secondaryLayout.getChildren().add(chartSwingNode);
	         // Set position of second window, related to primary window.
	         newWindow.centerOnScreen();
	         newWindow.show();
	       
}

}
