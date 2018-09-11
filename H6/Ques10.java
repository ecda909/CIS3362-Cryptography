import java.io.*;
import java.util.*;

public class Ques10
{
    static long phi(long n)
    {
        long res=n;
        for(long p=2;p*p<=n;p++)
        {
            // check if p is a prime factor of n
            if(n%p==0)
            {
                while(n%p==0)
                    n=n/p;
                
                res = res- (res/p);
            }
        }
        
        if(n>1)
            res = res- (res/n);
        return res;
    }
    
   static int primefactors(long n, long ar[])
   {
       int k=0;
       if(n%2==0)
            ar[k++]=2;
       while(n%2==0)
            n=n/2;
       
       for (long i=3; i<=Math.sqrt(n);i=i+2)
       {
           if(n%i==0)
               ar[k++]=i;
                   
           while (n%i == 0)
               n=n/i;
       }
       
       if(n>2)
            ar[k++]=n;
       
       return k; 
   }
   
   static long power(long x, long y, long n)
   {
        // Initialize result
        long res = 1; 
     
        while (y>0)
        {
            // If y is odd, multiply x with result
            if (y%2!=0)
                res = (res*x) % n;
     
            // y must be even now
            y = y/2;
            x = (x*x) % n;
        }
        return res;
    }
   
   public static void main()throws IOException
    {
        Scanner in=new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter n to check if prime (0 to exit)");
            long n=in.nextLong();
            if(n==0) break;
            
            long phi=phi(n);
            
            // if phi(n)=n-1 it is a prime number
            if(phi==n-1)
            {
                System.out.println(n+" is a PRIME number! \n");
                long ar[]=new long[1000000];
                
                // find all unique prime factors of n-1
                // len denotes the length of ar which stores the unique prime factors of n-1
                int len=primefactors(phi,ar);
                
                while(true)
                {
                    System.out.println("\nCheck primitive root : Enter an integer between 1 and "+phi+" (0 to exit)");
                    long x=in.nextLong();
                    if(x==0) break;
                    if(x>phi) continue;
                    
                    boolean flag=false;
                    for(int i=0;i<len;i++)
                    {
                        if(power(x, phi/ar[i], n) == 1)
                        {
                            flag=true;
                            break;
                        }
                    }
                    // If there was no power with value 1.
                    if (flag == false)
                       System.out.println(x+" is a PRIMITIVE ROOT");
                    
                    else
                       System.out.println("NOT a primitive root");
                }
            }
            
           else System.out.println("NOT a prime number\n");
       }
    }
    
}
