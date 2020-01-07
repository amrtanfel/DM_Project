package application;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import weka.core.Instance;
import weka.core.Instances;

public class DBScan {
Hashtable<Instance,Integer> Dataset=new Hashtable<Instance,Integer>();// o unvisated 1 visated
ArrayList <Cluster> clusters=new ArrayList <Cluster>();
float eps;
int minPts;
int nbUnvisated;

//-------------------Constructor--------------------------------------------//

public DBScan(Instances inst)
{
	
	nbUnvisated=inst.numInstances();
	for(int i=0;i<inst.numInstances();i++)
	{
		Dataset.put(inst.instance(i),0);// initialement on a visité aucune instance
	}
	
}
//-------------------Methods--------------------------------------------//	
public void setEps(float eps)
{
	this.eps=eps;
}

public void setMinPts(int minpts)
{
	this.minPts=minpts;
}
public void DBScanAction()
{
	Instance p;
	Neighborhood  Neigh;
	while(this.nbUnvisated!=0)// tq il existe toujours des intances qu'on a pas encore visitées.
	{
		// selecting of a random unvisited instance// change it to visited
		p=RandomUnvisated();
		nbUnvisated--;
		Neigh=new Neighborhood(this.Dataset,p,this.eps);
		if(Neigh.EpsNeighb.size()>=minPts-1)//p is a core point
		{
			Cluster C=new Cluster(p);// creation of cluster
			for(int i=0;i<Neigh.EpsNeighb.size();i++)// parcour de la liste des voisines
			{
				Instance p2=Neigh.EpsNeighb.get(i);
			    int type=0;// initialement le type est border
				if(Visited(p2)==false)// si p2 n'est pas encore visitée
				{   
					VisitedSet(p2);// mark it as visited
					this.nbUnvisated-- ;

					Neighborhood Neigh2=new Neighborhood(this.Dataset,p2,this.eps);
					if(Neigh2.EpsNeighb.size()>=minPts-1)
					{ 

						Neigh.AddNeighbrs(Neigh2);
					    type=1;
					}
				}
				if(NotClustersMember(p2))
					{ if(type==1)
					   C.AddInstance(p2,"Core point");
					 else
						 C.AddInstance(p2,"Border");
					}
			}
			C.CalculCG();
			System.out.println(C.members.size());
			clusters.add(C);// l'ajout du cluster apres le traitement.
		}		
	}
}

public Instance RandomUnvisated()
{ 
	   Random r = new Random();
	   int indice;
	 if(this.nbUnvisated==1)
	 {  indice=1;}
	 else
	 {indice= r.nextInt(nbUnvisated-1)+1;}
	
	   int i=0;
	    Set<Instance> keys=Dataset.keySet();
	    Iterator<Instance> itr = keys.iterator();
	    Instance key=null;
	   while(i<indice)
	   {
	   key=itr.next();
	   if(Visited(key)==false)
		   i++;
	   }
	  
	   VisitedSet(key);
	   return key;
}
//----------------------------------
public void VisitedSet(Instance p)
{ 
	Dataset.put(p,1);
}
//----------------------------------------
public boolean Visited(Instance p)
{
	if(Dataset.get(p)==0) return false; else return true;
}
//-------------------------------------------
public boolean NotClustersMember(Instance p)
{ 
	for(int i=0;i<this.clusters.size();i++)
	{
		if(clusters.get(i).ContainInstance(p)) return false;
	}
	return true;
}
//---------------------------------------------
public double InertieIntraclasse()
{
	double W=0;
	for(int i=0;i<this.clusters.size();i++)
	{
		W=W+clusters.get(i).CalculIl();
	}
	return W;
}
public double InertieInterclasse(String[] CGG)
{
	double W=0;
	 Set<Instance> keys=Dataset.keySet();
	    Iterator<Instance> itr = keys.iterator();
	    Instance key=itr.next();
	   
	if(this.clusters.size()!=0&&this.clusters.get(0).members.size()==Dataset.size())
		return 0;
	for(int i=0;i<this.clusters.size();i++)
	{
		W=W+clusters.get(i).CalculDistanceGC(CGG,key);
	}
	return W;
	
}
}

