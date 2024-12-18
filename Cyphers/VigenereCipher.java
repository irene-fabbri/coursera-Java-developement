/**
 * A Vigenere Cipher encrypts using a word key. The text is encrypted using one
 * Caesar Cipher per key letter
 * i.e if the key is 'day' the word 'hello' would be encrypted
 *  using CaesarCiher('d') for h
 *  using CaesarCiher('a') for e
 *  using CaesarCiher('y') for l
 *  using CaesarCiher('d') for l
 *  using CaesarCiher('a') for o
 *  
 * @author Irene Fabbri 
 */

import edu.duke.*;
import java.util.*;

public class VigenereCipher {
    CaesarCipher[] ciphers;
    
    public VigenereCipher(int[] key) {
        // each letter(int from 0-25) in key[] corresponds to a Caesar Cipher
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new CaesarCipher(key[i]);
        }
    }
    
    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        // examine the input char by char
        for (char c : input.toCharArray()) {
            // pick the right cipher to use
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            // append the encrypted char
            answer.append(thisCipher.encryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        // examine the input char by char
        for (char c : input.toCharArray()) {
            // pick the right cipher to use
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            // append the decrypted char
            answer.append(thisCipher.decryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    public String toString() {
        return Arrays.toString(ciphers);
    }
    
}