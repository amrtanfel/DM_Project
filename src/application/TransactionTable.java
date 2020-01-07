package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class TransactionTable {
ArrayList<Transaction> TR=new ArrayList<Transaction>(); 
/*-----------------------Constructeur-----------------------*/
public TransactionTable(String fichier) throws IOException
{
BufferedReader br = new BufferedReader(new FileReader(new File(fichier)));
String line;
while ((line = br.readLine()) != null) {
   TR.add(new Transaction(line));
  
}
br.close();
}
/*--------------------------------------------------------*/
/*---------------------Mehthods---------------------------*/
public int length()
{
	return TR.size();
}
/**---------------------------------------------------------**/
/**la methode qui construit L1**/
Hashtable ConstructionC1()
{
	Hashtable ht= new Hashtable();
	Transaction tr;
	int sizeTR=this.length();
	int sizetr;
	String key;
    //la construction de C1
	for(int i=0;i<sizeTR;i++)
	{ 
		tr=TR.get(i);
	    sizetr=tr.length();
	    for(int j=0;j<sizetr;j++)
		{
		 key=tr.itemset[j]; 
		 if(ht.containsKey(key))
	      {
		    ht.put(key,(int)ht.get(key)+1);//incrementation
	      }
	     else
	       {   
		 //creation
		     ht.put(key,(1));
	       }
		}
	}
	
	return ht;
}
/**----------------La methode qui construit Li---------**/
Hashtable constrLi(int minsup,Hashtable ht)
{
	Hashtable Li=new Hashtable();
	 Set keys=ht.keySet();
	    Iterator itr = keys.iterator();
	    String key;
	 	 while (itr.hasNext())
	 	 {
	 	  key=(String) itr.next();
	 	   if((int)ht.get(key)>=minsup)
	 	   {
	 		  Li.put(key,ht.get(key));
	 	   }
	 	 }
	 	 return Li;
}
/**----------------La methode join--------------------**/
Hashtable join(Hashtable ht,int minsup)
{
	Hashtable ht2=(Hashtable) ht.clone();// contient les memes elements que ht(k-1)
	Hashtable result=new Hashtable();// va contenir les elements du join
	Set keys2,keys=ht.keySet();
	String key1,key2,join;
	Transaction t1,t2;
    Iterator itr1 = keys.iterator();
   
    while (itr1.hasNext())
	 {
      key1=(String)itr1.next();
      t1=new Transaction(key1);
      ht2.remove(key1);// on supprime l'element avec comme clé key1 de ht2.
	  keys2=ht2.keySet();
	  Iterator itr2=keys2.iterator();
	  while(itr2.hasNext())
	  {
		  t2=new Transaction((String)itr2.next());
		  join=t1.joinTransaction(t2);
		  if(join!=null)
		  {
			  result.put(join, 0);
		  }
	  }

}
   
    result=this.SupItemIdentique(result);
    result=this.CalculFrequence(result);
   result=constrLi(minsup,result);
   


	return result;
}
/**-------------- La methode qui supprime les itemset identique-----**/
Hashtable SupItemIdentique(Hashtable htk)
{
	Hashtable st=(Hashtable) htk.clone();
	ArrayList <String> lt=new <String>ArrayList();
	Set keys2,keys=htk.keySet();
	String key1,key2;
	Transaction t1,t2;
    Iterator itr1 = keys.iterator();
   
    while (itr1.hasNext())
	 {
      key1=(String)itr1.next();
      t1=new Transaction(key1);
      st.remove(key1);// on supprime l'element avec comme clé key1 de ht2.
	  keys2=st.keySet();
	  Iterator itr2=keys2.iterator();
	  while(itr2.hasNext())
	  {
		  key2=(String)itr2.next();
		  t2=new Transaction(key2);
		  if(t1.itemIdentique(t2))
		  {
			  lt.add(key2);
		  }
	  }
}
    for(int i=0;i<lt.size();i++)
    {
    	htk.remove(lt.get(i));
    }
	
	return htk;
}
/**--------------La methode qui test si tous les itemsets de taille k-1 d'un itemset de taille k sont frequents----**/
public boolean Test_sous_Itemsets(Hashtable ht,Transaction tr)
{
	ArrayList <String[]> al=tr.toutItemSetk_1();
	Transaction tr1;
	Set keys=ht.keySet();
	boolean identique;
	for(int i=0;i<al.size();i++)
	{
		tr1=new Transaction(al.get(i));
		Iterator itr = keys.iterator();
		identique=false;
		while(itr.hasNext())
		{
			if(tr1.itemIdentique(new Transaction((String)itr.next())));
			identique=true;
		}
		if(identique==false) return false;// si on trouve un seul itemset (k-1) qui n'existe pas on sort de la boucle 
		// avec false
	}
	return true;
	
	
}
/**--------------La methode qui calcule la frequence de chaque itemset**/
public Hashtable CalculFrequence(Hashtable ht)
{
    Set keys=ht.keySet();
    Iterator itr=keys.iterator();
    Transaction tr;
    String key;
    while(itr.hasNext())
    { 
    	key=(String)itr.next();
    	tr= new Transaction(key);
    	for(int i=0;i<this.TR.size();i++)
    	{
    		if(TR.get(i).exist(tr))
    		{
    			ht.put(key,(int)ht.get(key)+1);//incrementation
    		}
    		
    	}
    }
	return ht;
}
/**-----------------FrequentItems----------------------------**/
public ArrayList <Hashtable> FrequentItems(int minSup)
{
	ArrayList <Hashtable>al=new ArrayList <Hashtable>();
	Hashtable C1=this.ConstructionC1();
	int i=0;
	al.add(this.constrLi(minSup, C1));// L1;
	while(al.get(i).isEmpty()==false)
	{
		al.add(this.join(al.get(i),minSup));
		i++;
	}
	al.remove(i);
	return al;
}
/** Affiche hashtable**/
public void afficheHashtable(Hashtable ht)
{
	Set keys=ht.keySet();
	Iterator it=keys.iterator();
	String key;
	while(it.hasNext())
	{
		key=(String)it.next();
		System.out.println(key+" :"+ht.get(key));
	}
}
/**Affiche frequentItems**/
/** Association rules **/
public ArrayList<AssRule> AssoRule(ArrayList <Hashtable>al2,int minConf)
{
	String key;
	Transaction tr;
	Set keys;
	Iterator it;
	
	ArrayList<AssRule> al=new ArrayList<AssRule>();
	if(al2.size()>1)
	{

		/*for(int i=1;i<al2.size();i++)
		{*/
			
			keys=al2.get(al2.size()-1).keySet();
		    it=keys.iterator();
			/*while(it.hasNext())
			{*/
				key=(String)it.next();
				tr=new Transaction(key);
				al=tr.AssRules(al,al2,minConf);
			/*}/
					
		/*}*/
}	
	return al;
}
public void AfficheAssRule(ArrayList<AssRule> al)
{
	for(int i=0;i<al.size();i++)
	{   
		al.get(i).affiche();
	}
}
}