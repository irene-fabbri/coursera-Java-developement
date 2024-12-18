/**
 * Some of the test for ensuring the Vigenere Breaker works as expected
 * @author Irene Fabbri 
 */

import java.util.*;
import edu.duke.*;

public class testVigenere {

    public void testSliceString(){
        VigenereBreaker vb = new VigenereBreaker();
        String message = "abcdefghijklm";
        System.out.println("Testing sliceString on " + message);
        for(int j = 3; j < 6 ; j++){
            for(int i = 0; i < j; i++){
                System.out.println("slice: "+i+" keylength: "+j+"\n"+vb.sliceString(message, i,j));                
            }
        }
    }
    
    public void testTryKeyLength(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        //System.out.println("Message analyzed:\n"+encrypted);
        int [] keys = vb.tryKeyLength(encrypted, 5, 'e');
        System.out.println(Arrays.toString(keys));
    }
}
