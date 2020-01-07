package application;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import weka.core.Instances;

public class MeasurementCT {
int  attributIndice;
     String type="--";
     String name="--";
     String mode="--";
     String q1="--";
	 String q3="--";
	 String max="--";
	 String min="--";
	 String midR="--";
	 String median="--";
	 String mean="--";
	 float missingVal=0;// le pourcentage de missing values
//----------------------------------------------Constructeur-------------------------------------------------//
 public MeasurementCT(int indice,Instances inst) {
	 attributIndice=indice;
	 name=inst.attribute(indice).name();
	 
 }
 //---------------------------------------------------------------------------------------------------------//
 public String getName()
	{
		return this.name;
	}
	public String getMode()
	{
		return this.mode;
	}
	public String getMax()
	{
		return this.max;
	}
	public String getMin()
	{
		return this.min;
	}
	public String getQ1()
	{
		return this.q1;
	}
	public String getQ3()
	{
		return this.q3;
	}
	public String getMidR()
	{
		return this.midR;
	}
	public String getMedian()
	{
		return this.median;
	}
	public String getType()
	{
		return this.type;
	}
	public String getMean()
	{
		return this.mean;
	}
 //-----------------------------------------Methodes----------------------------------------------------------//
 // cette methode cherche la valeur qui a la plus grande frequence et la met dans l'attribut mode
 public void CalculMeasure(Instances Inst)
 {  
	 Hashtable ht= this.calculFrequence(Inst);
	 this.trouvMode(ht);// la recherche du mode
	 
	 this.MisVal(Inst);//calcul du pourcentage du missing values
	 if(Inst.attribute(attributIndice).isNumeric())
	  {
		 ArrayList<Double> D=valeurTrie(Inst);
		 trouvMean(D);
		
	  }
	 
		// on remplace 
		 
			//remplacing missing values; si dans la methode qu'on teste le type d'attribut
			this.ReplaceMissVal(Inst);
		 
		 if(Inst.attribute(attributIndice).isNumeric())
		  {
			 ArrayList<Double> D=valeurTrie(Inst);
			 trouvMAxMin(D);
			 trouvQ1(D);
			 trouvQ3(D);
			 trouvMedian(D);
			 trouvMidR(D);
			 trouvMean(D);
			
		  }
		 
 }
 //---------------------------------------------------------------------------------------//
 // trier valeur attributs numérique;
 ArrayList<Double> valeurTrie(Instances Inst)
 {
	 ArrayList<Double> val=new  ArrayList<Double>();
	 for(int i=0;i<Inst.numInstances();i++)
	 {
		 if(String.valueOf(Inst.instance(i).value(this.attributIndice)).equals("NaN")==false)
		 val.add(Inst.instance(i).value(this.attributIndice));
	 }
	 System.out.println("val"+val.size());
	 // le trie a bulle:
	 int indice;
	 double perm;
	 for(int i=0;i<val.size()-1;i++)
	 {  indice=i;
	 System.out.println("valBoucle"+val.size());
		 for(int j=i+1;j<val.size();j++)
		 {
			 if(val.get(indice)>val.get(j))
				 indice=j;
		 }
		 if(indice!=i)
		 {//permutation
			 perm=val.get(i);
			 val.set(i,val.get(indice));
			 val.set(indice, perm);
		 }
	 }
	
	return val;
 }
 //------------------------------------------------------------------------------------------------------------
 void trouvMAxMin( ArrayList<Double> D)
 {
	 this.min=String.valueOf(D.get(0));
	 this.max=String.valueOf(D.get(D.size()-1));
	 
 }
 //------------------------------------------------------------------------------------------------------------
 void trouvMedian(ArrayList<Double> D)
 {
	 if(D.size()%2==1)
	 {
		 this.median=String.valueOf(D.get(D.size()/2));
	 }
	 else
	 {
		 double moyenne=(D.get(D.size()/2-1)+D.get(D.size()/2))/2;
		 this.median=String.valueOf(moyenne);
	 }
 }
 //------------------------------------------------------------------------------------------------------------
 void trouvQ1(ArrayList<Double> D)
 {
	if((D.size()/2)%2==1)
	{
		this.q1=String.valueOf(D.get(D.size()/4));	
	}
	else
	{
		 double moyenne=(D.get(D.size()/4-1)+D.get(D.size()/4))/2;
		 this.q1=String.valueOf(moyenne);
     }
 }
 //-----------------------------------------------------------------------------------------------------------
 void trouvQ3(ArrayList<Double> D)
 {
	 if((D.size()/2)%2==1)
		{
			this.q3=String.valueOf(D.get(D.size()/2+D.size()/4));	
		}
		else
		{
			 double moyenne=(D.get(D.size()/2+D.size()/4-1)+D.get(D.size()/2+D.size()/4))/2;
			 this.q3=String.valueOf(moyenne);
	     }
 }
 //------------------------------------------------------------------------------------------------------------
 void trouvMidR(ArrayList<Double> D)
 {
	 double moyenne=(D.get(0)+D.get(D.size()-1))/2;
	 this.midR=String.valueOf(moyenne);
 }
 //-------------------------------------------------------------------------------------------------------------
 void trouvMean(ArrayList<Double> D)
 {  double somme=0;
	 for (int i=0;i<D.size();i++)
	 {
		 somme=somme+D.get(i);
	 }
	 this.mean=String.valueOf(somme/D.size());
 }
 //--------------------------------------------------------------------------------------------------------------
 //calcule le pourcentage de missing values
 void MisVal(Instances inst)
 {
	 int misV=0;
	 if(inst.attribute(this.attributIndice).isNumeric())
	 {
		 for(int i=0;i<inst.numInstances();i++)
		 {
			 if(String.valueOf(inst.instance(i).value(this.attributIndice)).equals("NaN"))
			 {
				 misV++;
			 }
	     }
	 }

	 else
		 {
		   for(int i=0;i<inst.numInstances();i++)
		   {
			 if(inst.instance(i).stringValue(this.attributIndice).equals("?"))
			 {
				 misV++;
			 }
			 
		   }
	     }
	 this.missingVal=misV/inst.numInstances();
	 
 }
 //----------------------------------------------------------------------------------------------------------------
 void ReplaceMissVal(Instances Inst)
 {
	 if(Inst.attribute(this.attributIndice).isNumeric())
	 {
		 for(int i=0;i<Inst.numInstances();i++)
		 {
			 if(String.valueOf(Inst.instance(i).value(this.attributIndice)).equals("NaN"))
				 Inst.instance(i).setValue(this.attributIndice, Double.parseDouble(this.mean));// on remplace les missing
		 }           // values par le mean
	 }
	 else
	 {
		 for(int i=0;i<Inst.numInstances();i++)
		 {
			 if(Inst.instance(i).stringValue(this.attributIndice).equals("?"))
				 Inst.instance(i).setValue(this.attributIndice,this.mode);// on remplace les missing values par le mode
		 }
	 }
 }
 //--------------------------------------------------------------------------------------------------------//
 // elle calcule la frequence de chaque valeur et elle retourne le resultat dans un Hashtable
 Hashtable calculFrequence(Instances Inst)
 {Hashtable ht= new Hashtable();
 String key;
 int nV;
 //la creation du Hashtable + le calcul de frequence pour chaque valeur
 for(int i=0;i<Inst.numInstances();i++)
 {
	if(Inst.attribute(attributIndice).isNumeric())
      {
           key=String.valueOf(Inst.instance(i).value(this.attributIndice));
      }
    else
            key=Inst.instance(i).stringValue(this.attributIndice);
    if(ht.containsKey(key))
      {
	    ht.put(key,(int)ht.get(key)+1);//incrementation
      }
    else
       {
	     if(!key.equals("NaN")&&!key.equals("?") )
	 //creation
	     ht.put(key,(1));
       }    
      
 }
 return ht; 
 }
 //--------------------------------------------------------------------//
 // la recherche du mode
 void trouvMode(Hashtable ht)
 {
String key;
	 
	 //la recherche de la valeur avec la plus grande frequence le mode
	 String maxV="";
	 int max=0;
	 Set keys=ht.keySet();
	//obtenir un iterator des clés
	 Iterator itr = keys.iterator();
	 while (itr.hasNext())
	 {
	 key=(String) itr.next();
	
	 if (max<(int)ht.get(key))
		 {maxV=key;
		 max=(int)ht.get(key);
		 }
	 }
	 //changement de l'attribut mode:
	 this.mode=maxV; 
 }
 //---------------------------------------------------------------------------------//
 void normalization(Instances Inst)
 { double NvValeur;
	 for(int i=0;i<Inst.numInstances();i++)
	 {
		 NvValeur=(Inst.instance(i).value(this.attributIndice)-Double.parseDouble(this.min))/(Double.parseDouble(this.max)-Double.parseDouble(this.min));//normalisation entre 0 et 1
	     Inst.instance(i).setValue(this.attributIndice, NvValeur);
	 }
	 //calcul des nouvelles caractéristiques.
	 ArrayList<Double> D=valeurTrie(Inst);
	 System.out.println(D.size());
	 trouvMAxMin(D);
	 trouvQ1(D);
	 trouvQ3(D);
	 trouvMedian(D);
	 trouvMidR(D);
	 trouvMean(D);
	 Hashtable ht= this.calculFrequence(Inst);
	 this.trouvMode(ht);// la recherche du mode
	 
 }
Hashtable trierHashtable(Hashtable ht,ArrayList <Double> AL)
 {
	Hashtable resultat=new Hashtable();
	Set keys=ht.keySet();
	String key;
	String min;
	//obtenir un iterator des clés
	Iterator itr = keys.iterator();
	 while (itr.hasNext())
	 {
		
	     min=(String) itr.next();
	     while (itr.hasNext())
	      {
	       key=(String) itr.next();
	       if (Double.parseDouble(key)<Double.parseDouble(min))
	       {
	    	   min=key;
		  
	       }
	      }
	  resultat.put(min, ht.get(min));
	  ht.remove(min);
	//  System.out.println(min);
	  itr = keys.iterator();
	  AL.add(Double.parseDouble(min));
	 }
	 
	return resultat;
 }
 public histoDonnees Discretisation(Instances inst,Hashtable ht,ArrayList AL)
 {
	histoDonnees hd=new histoDonnees();
	
	 if(ht.size()<=10)
		{hd.ht=ht;hd.AL=AL; return hd;}
	  Double borneinf=(double)Math.round(Double.parseDouble(this.min)*100)/100,bornesup;
	  String key;
	  Integer nombre;
	  Double interval=(Double.parseDouble(this.max)-Double.parseDouble(this.min))/10;
	  //creation de la nouvelle hashtable
	  for(int i=0;i<9;i++)
	  {
		bornesup=(double)Math.round((borneinf+interval)*100)/100;
		key= "{"+String.valueOf(borneinf)+","+String.valueOf(bornesup)+"}";
		//cacul le nombre de valeur inclues dans l'intervalle;
		nombre=0;
		Set keys=ht.keySet();
		String key2;
		Iterator itr = keys.iterator();
		while (itr.hasNext())
		 {
			key2=(String) itr.next();
			if(Double.parseDouble(key2)>=borneinf && Double.parseDouble(key2)<bornesup)
			{
				nombre=nombre+(Integer)ht.get(key2);
			}
		 }
		hd.ht.put(key, nombre);
		borneinf=bornesup;
		System.out.println(key+"   "+nombre);
		hd.AL.add(key);
		
	  }
	  bornesup=Double.valueOf(this.max);
		key= "{"+String.valueOf(borneinf)+","+String.valueOf(bornesup)+"}";
		//cacul le nombre de valeur inclues dans l'intervalle;
		nombre=0;
		Set keys=ht.keySet();
		String key2;
		Iterator itr = keys.iterator();
		while (itr.hasNext())
		 {
			key2=(String) itr.next();
			if(Double.parseDouble(key2)>=borneinf && Double.parseDouble(key2)<=bornesup)
			{
				nombre=nombre+(Integer)ht.get(key2);
			}
		 }
		hd.ht.put(key, nombre);
		hd.AL.add(key);
		
	  
	  return hd;
	 
 }

}
