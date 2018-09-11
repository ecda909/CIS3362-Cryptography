/*
Name: Eder Mazariegos
Class: CIS3362
Semester: Fall 2017
*/

#include <iostream>



using namespace std;
char grid[8][8];
char plaintext[100];
char cipher[200];
bool prime[199999];
int primeLen=0;
int n,p;

void SieveOfEratosthenes()
{
    int n=199999;
    
    for(int i=0;i<n;i++)
        prime[i]=true;
    
    for (int p=2; p*p <=n; p++)
    {
        if (prime[p]==true)
        {
            for (int i=p*2; i<=n; i += p)
                prime[i] = false;
        }
    }
}

char toRadix64(int dec)
{
    if(dec>=0 && dec<=25)
        return (char)(dec+65) ;
    if(dec>=26 && dec<=51)
        return (char)(dec-26+97) ;
    if(dec>=52 && dec<=61)
        return (char)(dec-52+48) ;
    if(dec==62)
        return '+';
    return '/';
}

void rotateBox(int k)
{
    if(k%2==0)
    {
        int x=(k%16)/2;
        int temp=grid[x][7];
        for(int i=7;i>=1;i--)
            grid[x][i]=grid[x][i-1];
        grid[x][0]=temp;
    }
    else
    {
        int x=(k%16)/2;
        int temp=grid[7][x];
        for(int i=7;i>=1;i--)
            grid[i][x]=grid[i-1][x];
        grid[0][x]=temp;
    }
}
int main()
{
    SieveOfEratosthenes();
    
    cin>>n;
    while(n--)
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
                cin>>grid[i][j];
        }
        
        cin>>p;
        cin>>plaintext;
        
        // size is the value L
        int size=0;
        for(int i=0;i<101;i++)
        {
            if(plaintext[i]=='\0')
                break;
            size++;
        }
        
        int k=0;
        for(int i=0;i<size;i++)
        {
            // finding cipher text for each base64 character
            char c=plaintext[i];
            bool flag=false;
            for(int j=0;j<8;j++)
            {
                for(int l=0;l<8;l++)
                {
                    if(grid[j][l]==c)
                    {
                        cipher[k++]=j;
                        cipher[k++]=l;
                        flag=true;
                        break;
                    }
                }
                if(flag) break;
            }
            
            // Rotation of grid
            rotateBox(i);
        }
        
        k=0;
        // to store 2L+1 primes after p
        int after_prime[2*size+1];
        for (int i=p+1; i<=199999; i++)
        {
            if (prime[i])
            {
                after_prime[k++]=i;
                if(k == 2*size+1)	
                    break;
            }
        } 
        
        for(int i=0;i<2*size;i++)
        {
            int s=( after_prime[i+1]-after_prime[i] )/2;
            cipher[i]=(cipher[i]+s)%8 ; 
        }
        
        for(int i=0;i<2*size;i+=2)
        {
            int dec=8*cipher[i]+cipher[i+1];
            char x=toRadix64(dec);
            cout<<x;
        }
    } cout<<endl;
}
