/**
 * A Ceasar Cypher encrypts using a 1 letter key by shifting the alphabet
 * the shifted alphabet goes goes from 'key' to 'z', then from 'a' to 'key'
 * @author Irene Fabbri 
 */

import edu.duke.*;

public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int theKey; // the index of the key letter (0-25)
    
    public CaesarCipher(int key) {
        theKey = key;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // find the shifted alphabet for key: key to z, then a to key
        shiftedAlphabet = alphabet.substring(key) +
                            alphabet.substring(0,key);
        // account for lowercase letters
        alphabet = alphabet + alphabet.toLowerCase();
        shiftedAlphabet = shiftedAlphabet + shiftedAlphabet.toLowerCase();
    }
    
    private char transformLetter(char c, String from, String to) {
        // transfroms a char from an alphabet (from) to another (to)
        
        // find char in from alphabet
        int idx = from.indexOf(c);
        if (idx != -1) {
            // if found return the correspondant in the to alphabet
            return to.charAt(idx);
        }
        // else, return the char unchanged (like space, punctuation and so on) 
        return c;
    }
    
    public char encryptLetter(char c) {
        // encrypt: transforms a char from alphabet to shifted alphabet
        return transformLetter(c, alphabet, shiftedAlphabet);
    }
    
    public char decryptLetter(char c) {
        // decrypt: transforms a char from shifted alphabet to alphabet
        return transformLetter(c, shiftedAlphabet, alphabet);
    }
    
    private String transform(String input, String from, String to){
        // transfroms a String from an alphabet (from) to another (to)

        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            c = transformLetter(c, from, to);
            sb.setCharAt(i, c);
        }
        return sb.toString();
    }
    
    public String encrypt(String input) {
        // encrypt: transforms a String from alphabet to shifted alphabet
        return transform(input, alphabet, shiftedAlphabet);
    }
    
    public String decrypt(String input) {
        // decrypt: transforms a String from shifted alphabet to alphabet
        return transform(input, shiftedAlphabet, alphabet);
    }
    
    public String toString() {
        return "" + theKey;
    }
    
}
