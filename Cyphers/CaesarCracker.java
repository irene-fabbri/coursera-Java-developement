/**
 * Attempts at cracking a Caesar Cypher encrypted message by inferring that the most
 * common letter appearing is the most common letter in the language the message is
 * supposed to be written in
 * @author Irene Fabbri 
 */

import edu.duke.*;

public class CaesarCracker {
    char mostCommon;
    
    public CaesarCracker() {
        // english most common letter is e
        mostCommon = 'e';
    }
    
    public CaesarCracker(char c) {
        mostCommon = c;
    }
    
    public int[] countLetters(String message){
        // counts the number each letter occurs case insensitively
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int k=0; k < message.length(); k++){
            int dex = alph.indexOf(Character.toLowerCase(message.charAt(k)));
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] vals){
        // returns the index of the most frequent letter
        // (does not account for multiple max, but since this method works for big texts
        // it is an unlikely alternative)
        int maxDex = 0;
        for(int k=0; k < vals.length; k++){
            if (vals[k] > vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }

    public int getKey(String encrypted){
        // count the frequency of each letter
        int[] freqs = countLetters(encrypted);
        // find the most frequent one
        int maxDex = maxIndex(freqs);
        // find the corrisponding key:
        // 1. find the most common letter index
        int mostCommonPos = mostCommon - 'a';
        // 2. the key is |mostCommonInEncrypted-mostCommonPos|
        int dkey = maxDex - mostCommonPos;
        if (maxDex < mostCommonPos) {
            dkey = 26 - (mostCommonPos-maxDex);
        }
        return dkey;
    }
    
    public String decrypt(String encrypted){
        // find the decryption key
        int key = getKey(encrypted);
        // create a CaesarCipher with that key
        CaesarCipher cc = new CaesarCipher(key);
        // return the derypted message
        return cc.decrypt(encrypted);
    }
   
}
