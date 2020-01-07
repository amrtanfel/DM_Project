package application;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import weka.core.Instance;
public class Cluster {
Hashtable<Instance,String> members=new Hashtable<Instance,String>();
String[] CG;
//------------------------------Constructor------------------------//
public Cluster(Instance p)
{
	members.put(p,"Core point");
	CG=new String[p.numAttributes()-1];// eliminant la classe
}
//------------------------------Methods------------------------//
public void AddInstance(Instance p, String type)// ajouter un element au cluster
{
	members.put(p,type);
}
//----------------
public boolean ContainInstance(Instance p)// verifier si un element existe dans ce cluster
{
	if (members.containsKey(p)) return true;
	else return false;
}
//-----------------
public void AffichCluster()
{
Set<Instance> keys=members.keySet();
Iterator <Instance>itr=keys.iterator();
Instance key;
while(itr.hasNext())
{
	key=itr.next();
	System.out.println(this.InstanceCluster(key));
}
System.out.println(" CG:"+String.join(",", this.CG));
}
//-----------------------
String meanAttribut(int indice)// attribut numérique
{
	double somme=0;
	Set<Instance> keys=members.keySet();
	Iterator <Instance>itr=keys.iterator();
	Instance key;
	while(itr.hasNext())
	{
		key=itr.next();
		somme=somme+key.value(indice);
		
	}
	return String.valueOf(somme/members.size());
	
}

//-------------------------
String ModeAttribut(int indice)
{
	Hashtable<String,Integer> freq=new Hashtable<String,Integer>();
	Set<Instance> keys=members.keySet();
	int max=0;
	String mode="";
	Iterator <Instance>itr=keys.iterator();
	Instance key;
	while(itr.hasNext())
	{key=itr.next();
	String value=key.stringValue(indice);
	if (freq.contains(value)==false)
	{
		freq.put(value,0);
	}
	 freq.put(value,freq.get(value)+1);
	if( max<freq.get(value))
	{
		max=freq.get(value);
		mode=value;
	}
	}
	
	return mode;
}
//-------------------------------------
public void CalculCG()
{
	Set<Instance> keys=members.keySet();
	Iterator <Instance>itr=keys.iterator();
	Instance key=itr.next();
	for(int i=0;i<CG.length;i++)
	{
		if(key.attribute(i).isNumeric())
		{
			CG[i]=meanAttribut(i);
		}
		else 
			CG[i]=ModeAttribut(i);
	}
}
//----------------------------
public double CalculDistanceGC(Instance inst)
{
	double dist=0;
	for(int i=0;i<CG.length;i++)
	{
		if(inst.attribute(i).isNumeric())// numerique
		{
			dist=dist+(inst.value(i)-Double.parseDouble(CG[i]))*(inst.value(i)-Double.parseDouble(CG[i]));
		}
		else // nominal
		{
			if(inst.stringValue(i).equals(CG[i])==false)
				dist++;
				
		}
	}
	return dist;// car dans calculIL elle est au carré
}
//----------------------------
public double CalculIl()
{
	double somme=0;
	Set<Instance> keys=members.keySet();
	Iterator <Instance>itr=keys.iterator();
	Instance key;
	while(itr.hasNext())
	{key=itr.next();
	 somme=somme+CalculDistanceGC(key);
	}
	return somme;
}
//----------------------------
public double CalculDistanceGC(String[] CGG,Instance inst)
{
	double dist=0;
	for(int i=0;i<CG.length;i++)
	{
		if(inst.attribute(i).isNumeric())// numerique
		{
		dist=dist+(Double.parseDouble(CGG[i])-Double.parseDouble(CG[i]))*(Double.parseDouble(CGG[i])-Double.parseDouble(CG[i]));
	
		}
		else // nominal
		{
			if(CGG[i].equals(CG[i])==false)
				dist++;
				
		}
	}
	return dist;
}
//-------------------------------------
public String InstanceCluster(Instance inst)
{
	String[] element=inst.toString().split(",");
	String[] element2=new String[inst.numAttributes()-1];
for(int i=0;i <element2.length;i++)
{
	element2[i]=element[i];
}
return String.join(",", element2);
}
} 

