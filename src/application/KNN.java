package application;

import java.util.ArrayList;
import java.util.Hashtable;

import weka.core.Instance;
import weka.core.Instances;

public class KNN {
ArrayList <Instance> Training=new ArrayList <Instance>();
ArrayList <Instance>Test=new ArrayList <Instance>();
String type;
int k=1;
/**************Constructeur********************************************/
public KNN(Instances inst,int PourceTrain)
{
	int nbTraining=inst.numInstances()*PourceTrain/100;// calcul de nombre d'instances pour le training 
// le remplissage du training
	for(int i=0;i<nbTraining;i++)
	{
		Training.add(inst.instance(i));
	}
// le remplissage du test
	
	for(int i=nbTraining;i<inst.numInstances();i++)
	{
		Test.add(inst.instance(i));
	}
	if(inst.attribute(inst.numAttributes()-1).isNumeric())
		type="numerique";
	else
		type="nominal";

}
/*******************Methodes*********************************************/
// pour changer le k si on veut
public void Kset(int k)
{
	this.k=k;
}
//Calcul distance pour une instance avec toutes les instances du training
public ArrayList<InstanceDistance> distanceWithTraining(Instance inst)
{
	ArrayList<InstanceDistance> InstD=new ArrayList<InstanceDistance>();
	for(int i=0;i<this.Training.size();i++)
	{
		InstD.add(new InstanceDistance(Training.get(i)));
		InstD.get(i).CalculDistance(inst);
	}
	return InstD;
}
//le tri du tableau InstD(tri a  bulle)
public void TriDistances(ArrayList<InstanceDistance> distInst)
{
	boolean trie=false;
	int perm;
	while(trie==false)
	{   perm=0;
		for(int i=0;i<distInst.size()-1;i++)
		{
			if(distInst.get(i).greaterThen(distInst.get(i+1)))
			{
				InstanceDistance i1=distInst.get(i);
				InstanceDistance i2=distInst.get(i+1);
				i1.Permute(i2);
				distInst.set(i, i1);
				distInst.set(i+1, i2);
				perm++;
			}
		}
		if(perm==0)
			trie=true;
	}
	
}
//classification de l'instance si la classe est nominale
public String ClassifNominal(Instance Inst)
{
	ArrayList<InstanceDistance> ID= distanceWithTraining(Inst);
	this.TriDistances(ID);
	
	Instance inst;
	String classe;
	String MaxClasse="";int max=0;
	Hashtable<String,Integer> ht=new Hashtable<String,Integer>();
	for (int i=0;i<k;i++)
	{
		inst=ID.get(i).inst;
		classe=inst.stringValue(inst.numAttributes()-1);
		if(ht.containsKey(classe))
		{
			ht.put(classe, ht.get(classe)+1);
			if(max<ht.get(classe))
			{
				max=ht.get(classe);
				MaxClasse=classe;
			}
		}
		else 
		{
			ht.put(classe, 1);
			if(max==0)
			{
				max++;
				MaxClasse=classe;
			}
		}
	}
	return MaxClasse;
}
//predication pour le cas d'une estimation
public String PredictNumerique(Instance Inst)
{
	ArrayList<InstanceDistance> ID= distanceWithTraining(Inst);
	this.TriDistances(ID);

	double somme=0;
	Instance inst;
	for (int i=0;i<k;i++)
	{
		inst=ID.get(i).inst;
		somme=somme+inst.value(inst.numAttributes()-1);
	}
	return String.valueOf(somme/k);
		
}
public ArrayList<String> ClassificationTest()
{ 
	ArrayList<String> Classif=new ArrayList<String>();
	if(this.type.equals("numerique"))
	{
	for(int i=0;i<this.Test.size();i++)
	{
	Classif.add(this.PredictNumerique((Test.get(i))));	
	}
	}
	else
	{
		for(int i=0;i<this.Test.size();i++)
		{
		Classif.add(this.ClassifNominal(Test.get(i)));	
		}
	}
		
	return Classif;
	
}
public float TautErreur(ArrayList<String> list)
{
	float erreur=0;
	float somme=0;
	for(int i=0;i<list.size();i++)
	{
		Instance test=Test.get(i);
		if(list.get(i).equals(test.stringValue(test.numAttributes()-1))==false)
				somme=somme+1;
	}
	erreur=somme/list.size();
	return erreur;
}
}
