package application;

public class KnnTab {
	String instance;
	String Classif;
	String KnnClassif;
public KnnTab(String S1,String S2,String S3)
{
	this.instance=S1;
	this.Classif=S2;
	this.KnnClassif=S3;
}
public String getInstance()
{
	return instance;
	
}
public String getClassif()
{
	return Classif;
	
}
public String getKnnClassif()
{
	return KnnClassif;
	
}

}
