package application;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import weka.core.Instance;

public class Neighborhood {
Instance ins; 
ArrayList<Instance> EpsNeighb=new ArrayList<Instance>();
//-------------------Constructor--------------------------------//
public Neighborhood(Hashtable<Instance,Integer> Dataset,Instance Ins,float eps)
{ 
	this.ins=Ins;
	    Set<Instance> keys=Dataset.keySet();
	    Iterator<Instance> itr = keys.iterator();
	    Instance key;
	    InstanceDistance D= new InstanceDistance(ins);
	    String keyS;
	   String insS=ins.toString();
	   while(itr.hasNext())
	   {
		   key=itr.next();
		   keyS=key.toString();
		   if(insS.equals(keyS)==false)
		   {
		   D.CalculDistance(key);
		   if (D.distance<=eps)// la condition de Eps est verifier
		   {
			   EpsNeighb.add(key);
		   } 
		   }
	   }
}
public void AddNeighbrs(Neighborhood n)
{
	for(int i=0;i<n.EpsNeighb.size();i++)
	{
		if(ins.toString().equals(n.EpsNeighb.get(i).toString())==false)
		this.EpsNeighb.add(n.EpsNeighb.get(i));
	}
}


}
