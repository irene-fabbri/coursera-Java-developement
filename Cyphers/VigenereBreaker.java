/**
 * Attempts at cracking a Vigenere Cypher encrypted message by inferring that the most
 * common letter appearing is the most common letter in the language the message is
 * supposed to be written in
 * @author Irene Fabbri 
 */

import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    private HashMap<String, Character> mostCommon;
    private HashMap<String, HashSet<String>> languages;
    
    public VigenereBreaker (){
        mostCommon = new HashMap<String, Character>();
        languages = new HashMap<String, HashSet<String>>();
        buildDictMap();
        findMostCommonChar();
    }
    
    private void buildDictMap(){
        // import all the dictionaries
        String path = "dictionaries/";
        String[] myDict = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};
        for(String lang : myDict){
            String currPath = path+lang;
            FileResource fr = new FileResource(currPath);
            System.out.println("Importing the "+lang+" dictionary");
            HashSet<String> thisDict = readDictionary(fr);
            languages.put(lang, thisDict);
        }
    }
    
    private void findMostCommonChar(){
        // find the most common letter in every language by reading the dictionary
        for (String lang : languages.keySet()){
            char ch = mostCommonCharIn(languages.get(lang));
            mostCommon.put(lang, ch);
        }
    }
      
    public String sliceString(String message, int whichSlice, int totalSlices) {
        // select all the chars encrypted with the same Caesar Cipher  by slicing
        // the message
        StringBuilder slice = new StringBuilder();
        for(int i = whichSlice; i < message.length(); i += totalSlices){
            char currChar = message.charAt(i);
            slice.append(currChar);
        }
        return slice.toString();
    }

    private String [] buildSlices(String message, int klength){
        // build an array with in each position has the letter encrypted using the same 
        // Caesar Cipher by assuming a certain key length
        String [] slices = new String[klength];
        for(int i = 0 ; i < klength; i++){
            slices[i] = sliceString(message, i, klength);
        }
        return slices;
    }
    
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        // find the most likely keys assuming a certain key length
        int[] key = new int[klength];
        String[] slices = buildSlices(encrypted, klength);
        CaesarCracker cc = new CaesarCracker();
        
        for(int i=0; i < klength; i++) {
            key[i] = cc.getKey(slices[i]);
            //System.out.println(i+"th slice analyzed: "+ slices[i]);
            //System.out.println(i+"th key found: "+ key[i]);
        }
        return key;
    }
    
    private HashSet <String> readDictionary( FileResource fr){
        // import all the words in a dictionary and lower, when needed
        HashSet <String> dictionary = new HashSet <String>();
        for(String line : fr.lines()){
            line = line.toLowerCase();
            dictionary.add(line);
        }
        return dictionary;
    }
    
    private int countWords(String message, HashSet<String> dictionary){
        // count how many real words in that dictionary the decrypted message has
        // to make sure the found key is an actual key
        int realWords = 0;
        message = message.toLowerCase();
        for(String word : message.split("\\W+")){
            if (dictionary.contains(word)){
                realWords ++;
            }
        }
        return realWords;
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
    
    public char mostCommonCharIn(HashSet<String> dict){
        // finds the most common char in a given dictionary
        // (all words are lowered when imported)
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        // counts how many occurences of each letter
        for(String word : dict){ 
            for(int k=0; k < word.length(); k++){
                int dex = alph.indexOf(word.charAt(k));
                if (dex != -1){
                    counts[dex] += 1;
                }
            }
        }
        // find the most common one (no draw accounted for but highly unlikely)
        int maxDex = maxIndex(counts);
        return alph.charAt(maxDex);
    }
    
    public VigenereCipher breakForLanguageEnglish(String encrypted, 
        HashSet <String> dictionary){
        // find the most likely keyLength assuming the language is English
        // so most common letter is 'e'
        int max = 0;
        int bestKeyLength = 0;
        // for each keyLength (max 100)
        for(int keyLength = 1; keyLength <= 100; keyLength++ ){
            // find keys
            int [] keys = tryKeyLength(encrypted, keyLength, 'e');
            // decrypt using keys
            VigenereCipher vc = new VigenereCipher(keys);
            String decrypted = vc.decrypt(encrypted);
            // count valid words
            int validWords = countWords(decrypted, dictionary);
            // the best keyLength has the most valid words
            if(validWords > max){
                max = validWords;
                bestKeyLength = keyLength;
            }
        }        
        // select the winning set of keys
        int [] bestKeys = tryKeyLength(encrypted, bestKeyLength, 'e');
        // make a Vigenere Cipher with those keys
        VigenereCipher vc = new VigenereCipher(bestKeys);
        // print info
        System.out.println("Key length Found: "+bestKeyLength);
        System.out.println("Number of valid words found: "+max);
        // return the Cipher so you can decrypt the message
        return vc;
    }

    public int breakForLanguage(String encrypted, String lang){
        // find the most likely keyLength for a given language
        int max = 0;
        int bestKeyLength = 0;
        HashSet<String> dictionary = languages.get(lang);
        char mostCommonInLang = mostCommon.get(lang);
        // for each keyLength (max 100)
        for(int keyLength = 1; keyLength <= 100; keyLength++ ){
            // find keys
            int [] keys = tryKeyLength(encrypted, keyLength, mostCommonInLang);
            // decrypt using keys
            VigenereCipher vc = new VigenereCipher(keys);
            String decrypted = vc.decrypt(encrypted);
            // count valid words
            int validWords = countWords(decrypted, dictionary);
            if(validWords > max){
                max = validWords;
                bestKeyLength = keyLength;
            }
        }
        // select the winning set of keys
        int [] bestKeys = tryKeyLength(encrypted, bestKeyLength, mostCommonInLang);
        // make a Vigenere Cipher with those keys
        VigenereCipher vc = new VigenereCipher(bestKeys);
        System.out.println("\nKey length Found for the "+lang+" Language: "+bestKeyLength);
        System.out.println("Number of valid words found: "+max);
        // decrypt message and print the fist 100 chars
        String decrypted = vc.decrypt(encrypted);
        System.out.println("Total word number in the decrypted message: "+ decrypted.split("\\W+").length);
        System.out.println("Message decripted from "+lang+" (truncated)");
        System.out.println(decrypted.substring(0,100));
        // return the number of valid words
        return max;
    }
    
    public void breakForAllLangs(String encrypted){
        // finds the most likely language i.e. those with most valid words found when 
        // decrypting
        int max = 0;
        String bestLang = "***ERROR***"; 
        for(String lang: languages.keySet()){
            int validWords = breakForLanguage(encrypted, lang);
            if(validWords > max){
                max = validWords;
                bestLang = lang;
            }
        }
        
        System.out.println("Most likely language found is "+ bestLang+" with "+max+" valid words");
    }
    
    public void breakVigenereEnglish () {
        // Decrypts assuming the language is English
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String pathToDict = "dictionaries/English";
        FileResource dict = new FileResource(pathToDict);
        HashSet <String> dictionary = readDictionary(dict);
        
        VigenereCipher vc = breakForLanguageEnglish(encrypted, dictionary);
        System.out.println("Key Found: "+vc.toString());
        String decrypted = vc.decrypt(encrypted);
        System.out.println("Total word number: "+ decrypted.split("\\W+").length);
        System.out.println(decrypted.substring(0,200));
    }
    
    public void breakVigenere() {
        // decrypts not assuming the language
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        breakForAllLangs(encrypted);
    }
}
