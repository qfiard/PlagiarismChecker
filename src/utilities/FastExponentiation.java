package utilities;

public class FastExponentiation {

	public static long pow(long a, int exp)
	{
		if(exp<=0)
		{
			throw new IllegalArgumentException("Exposant négatif ou nul");
		}
		
		if(exp==1)
		{
			return a;
		}
		
		long tmp = pow(a,exp/2);
		
		if(exp%2==0)
		{
			
			return tmp*tmp;
		}
		else
		{
			return a*tmp*tmp;
		}
	}
}
