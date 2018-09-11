// Arup Guha
// Code for Fall 2017 CIS 3362 Homework #7
// Please use this hash function for the assignment.
// 11/22/2017

import java.util.*;

public class hash {

	// Allows the user to enter a string and outputs its hash value.
    public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		char[] inp1 = stdin.next().toCharArray();
		char[] inp2 = stdin.next().toCharArray();
		System.out.println(hashFunction(inp1)==hashFunction(inp2));
		System.out.println(hashFunction(inp1));
		System.out.println(hashFunction(inp2));
    }
   
   	// This is the hash function you must use for homework #6.
    public static long hashFunction(char[] input) {

        int i = 0, n = input.length;
        long buffer = 0x23456789abcdL;
        while (i < n) {
            long temp = 0L;
            int j;
            for (j=0; j<6 && i+j<n; j++)
                temp |=( ( (long)input[i+j])  << (8*j)  );
            i += 6;
            
            for (j=0; j<20; j++)
            	temp = round(temp, j);

            buffer ^= temp;
        }
        return buffer;
    }
    
    public static long round(long value, int index) {
    	long buffer = 0x6ed54291f3c7L;
    	for (int i=0; i<16; i++) {
    		value = rotate(value, i);
    		value = applyBox(value, index);
    	}
    	return value;
    }

    // value must be 8 bits. Swaps the left and the right halves and runs
    // each through an s-box.
    public static long rotate(long value, int round) {
    	int[] shifts = {0,2,3,1,1,3,1,2,2,2,2,1,4,1,3,2};
        int bits = shifts[round]*3;
        long lsbs = value >> (48-bits);
        long msbs = (value << bits) & ((1L<<48)-1);
        return msbs | lsbs;
    }
    
    public static long applyBox(long value, int numTimes) {
    	long res = 0;
    	for (int i=0; i<16; i++) {
    		long tmp = ((value >> (3*i)) & 7);
    		tmp = repeatBox((int)tmp, numTimes);
    		res = res | (tmp << (3*i));
    	}
    	return res;
    }

    // Random s-box of values. 
    public static int box(int value) {
        int ans[] = {3, 5, 0, 2, 7, 1, 4, 6};
        return ans[value];
    }

	// Composes the function box with itself numTimes times and returns result
	// if input is the input.
	public static int repeatBox(int input, int numTimes) {
		for (int i=0; i<numTimes; i++)
			input = box(input);
		return input;
	}
}
