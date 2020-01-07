package application;

import weka.core.Instance;

public class InstanceDistance {
Instance inst;
float distance;
/*****Constructeur*************************/
public InstanceDistance(Instance inst )
{
	this.inst=inst;
}
public InstanceDistance(Instance inst,float dis)
{
	this.inst=inst;
	this.distance=dis;
}
/**********Methodes***********************/
public void CalculDistance(Instance inst)//en exluant la classe
{
	float somme=0;
	float diff;
	for(int i=0;i<this.inst.numAttributes()-1;i++)// on exclue la classe
	{
		if(this.inst.attribute(i).isNumeric())// attribut numerique
		{
			diff=(float) (this.inst.value(i)-inst.value(i));
			diff=diff*diff;
			somme=somme+diff;
		}
		else// attribut nominal
		{
			if (this.inst.stringValue(i).equals(inst.stringValue(i))==false)// si les valeurs sont differentes: 1
			{
				somme++;
			}
		}
	}
	this.distance=(float) Math.sqrt(somme);
}
public void CalculDistanceWithClass(Instance inst)// en encluant la classe
{
	float somme=0;
	float diff;
	for(int i=0;i<this.inst.numAttributes();i++)
	{
		if(this.inst.attribute(i).isNumeric())// attribut numerique
		{
			diff=(float) (this.inst.value(i)-inst.value(i));
			diff=diff*diff;
			somme=somme+diff;
		}
		else// attribut nominal
		{
			if (this.inst.stringValue(i).equals(inst.stringValue(i))==false)// si les valeurs sont differentes: 1
			{
				somme++;
			}
		}
	}
	this.distance=(float) Math.sqrt(somme);
}
public void Permute(InstanceDistance inst)
{
	InstanceDistance ID=new InstanceDistance(this.inst,this.distance);
	this.inst=inst.inst;
	this.distance=inst.distance;
	inst.distance=ID.distance;
	inst.inst=ID.inst;
}
public boolean greaterThen(InstanceDistance inst)
{
	if (this.distance>inst.distance) return true;
	else return false;
}

}
