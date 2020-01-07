package application;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Transaction {
String[] itemset;
/*---------------Constructeur------------------------*/
Transaction(String s)
{
itemset=s.split(" ");// pour avoir les items
}
Transaction(String[] s)
{
itemset=s;
}
/*----------------------------------------------------*/
/*---------------Methods-----------------------------*/
// pour tester si un item existe dans une transaction
public boolean exist(String s)
{
	for(int i=0;i<this.itemset.length;i++ )
	{
		if(itemset[i].equals(s)) return true;
	}
	return false;
}
//pour tester si un itemset existe dans une transaction
public boolean exist(Transaction st)
{
	for(int i=0;i<st.length();i++)
	{
		if(this.exist(st.getValue(i))==false)
         return false;
	}
	return true;
}
public String toString()
{
	
  
	return String.join(" ", itemset);
}
public int length()
{
	return this.itemset.length;
}
//--------------------------//
public String getValue(int i)
{
	return this.itemset[i];
}
//-------------------------//
public boolean itemIdentique(Transaction itm)
{
	for(int i=0;i<this.length();i++)
	{
		if(this.exist(itm.getValue(i))==false)
		{
			return false;
		}
	}
	return true;
}
//--------------------//
// la methode qui donne toutes les itemset de tailles k-1 a partir d'un itemset de tailles k
public ArrayList toutItemSetk_1()
{ int ind;
	ArrayList <String[]> al=new ArrayList <String[]>();
	for(int i=0;i<this.length();i++)
	{String[] itm=new String[this.length()-1];
	ind=0;
		for(int j=0;j<this.length();j++)
		{
			if (j!=i)
			{
				itm[ind]=this.getValue(j);
				ind++;
			}
		}
			
	}
	
	return al;
	
}
//-------------------//

public Transaction UnionItem(Transaction tr)
{
	Transaction Tr=new Transaction(String.join(" ", this.itemset)+" "+String.join(" ", tr.itemset));
	return Tr;
}
//------------------//
public ArrayList<AssRule> AssRules(	ArrayList<AssRule> al,ArrayList <Hashtable>al2,int minConf)// pour chaque transaction
{
	int indice1,indice2;
	int entre,supUnion,supleft=1;
	Set Keys;
	Iterator itr;
	String key;
	Transaction tr; 
	
	float confidence;
	for(int i=0;i<this.length()-1;i++)// la taille du LeftImplecation
	{
		for(int j=0;j<this.length();j++)
		{
			
			String[]left=new String[i+1];
			String[] right=new String[this.length()-(i+1)];
			indice1=0;
			indice2=0;
			entre=i+j-this.length()+1;
			for(int k=0;k<this.length();k++)
			{
				
			if(entre<0)
			{
				if(k>=j&&k<=j+i)
				{
				left[indice1]=this.getValue(k);
				indice1++;
				}
				else
				{
					right[indice2]=this.getValue(k);
					indice2++;
				}
			}
			else
			{
				if(k>=j||k<entre)
				{left[indice1]=this.getValue(k);
				indice1++;
				}
				else
				{
					right[indice2]=this.getValue(k);
					indice2++;
				}
			}
			}
			// le calcul de la valeur conf
			supUnion=(int) al2.get(this.length()-1).get(this.toString());
			// pour le supp du A:
			Keys=al2.get(i).keySet();
			itr=Keys.iterator();
			tr=new Transaction(left);
			while(itr.hasNext())
			{
				key=(String)itr.next();
				if(tr.exist(new Transaction(key)))
				{
					supleft=(int)al2.get(i).get(key);
					break;
				}
			}
			confidence=supUnion*100/supleft;
	       if (confidence>=minConf)
			al.add(new AssRule(new Transaction(left),new Transaction(right),confidence));
			
			
		}
	}
	return al;
}
//-----------------------------------------------//
// elle verfie si les k-1 elements des 2 transactions sont identiques afin de pouvoir faire le join par la suite
public String joinTransaction(Transaction tr)
{
	Transaction result;
	String s="";
	if(this.length()==1)
		return tr.getValue(0)+" "+this.getValue(0);
	
	for(int i=0;i<this.length()-1;i++)
	{
		if(this.getValue(i).equals(tr.getValue(i))==false)
			return null;
		else
		{
			
			s=s+this.getValue(i)+" ";
		}
	}
	s=s+tr.getValue(this.length()-1)+" "+this.getValue(this.length()-1); 
	return s;
}
}