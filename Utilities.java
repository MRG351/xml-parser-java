
// package ret.utils
import java.lang.Math;

public class Utilities {

    /* find the smallest square x larger than n */
    public static int nextSquare(int n) {
        while (!isSquare(n)) {
            n++;
        }
        return n;
    }

    /* return true if n is square */
    public static boolean isSquare(int n) {
        return ( Math.sqrt(n) == (int)Math.sqrt(n) ) ? true : false;
    }
    
    public static int fitInSquare(int n) {
        return (int)Math.sqrt(nextSquare(n));
    }
    
}    
           
    
    /*  For testing
    public static void main(String[] args) {
        
            
            if (isSquare(7))
                System.out.println("7 is square");
            else
                System.out.println("7 is not square");
            System.out.println("sqrt(7) = " + Math.sqrt(7) + " =? " + "(int)sqrt(7) = " + (int)Math.sqrt(7));
            
            int x = 19;
            if (isSquare(x))
                System.out.println(x + " is square");
            else
                System.out.println(x + " is not square");
            System.out.println("sqrt(" + x + ") = " + Math.sqrt(x));
            System.out.println("sqrt(" + x + ") = " + Math.sqrt(x) + " =? " + "(int)sqrt(" + x + ") = " + (int)Math.sqrt(x));
            
             int y = 25;
            if (isSquare(y))
                System.out.println(y + " is square");
            else
                System.out.println(y + " is not square");
            System.out.println("sqrt(" + y + ") = " + Math.sqrt(y));
            System.out.println("sqrt(" + y + ") = " + Math.sqrt(y) + " =? " + "(int)sqrt(" + y + ") = " + (int)Math.sqrt(y));
            
            System.out.println("next square of 7: " + nextSquare(7));
            System.out.println("next square of " + x + " :" + nextSquare(x));
            System.out.println("next square of " + y + " " + nextSquare(y));
    }
*/ 