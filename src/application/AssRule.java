package application;

public class AssRule {
	Transaction LImplec;
	Transaction RImplec;
	float conf;
public AssRule(Transaction l,Transaction R,float conf)
{
	LImplec=l;
	RImplec=R;
	this.conf=conf;
}
public void affiche()
{
	System.out.println(LImplec.toString()+" => "+RImplec.toString()+": "+conf);
}
}
