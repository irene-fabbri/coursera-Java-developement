/**
 * Abstract class AbstractMarkovModel
 * generates random text by picking a random char from the 
 * most common followers of a key in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText; // training text
    protected Random myRandom;
    
    public AbstractMarkovModel() {
        myRandom = new Random();
    }
    
    public void setTraining(String s) {
        myText = s.trim();
    }
 
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    // generates a random text of numChars charachters
    abstract public String getRandomText(int numChars);
    
    protected ArrayList<String> getFollows(String key){
        // return the set of all char following a key in text
        ArrayList <String> follows = new ArrayList();
        int pos = 0;
        int maxIndex = myText.length()-key.length();
        // while there is text to read
        while(myText.indexOf(key,pos) != -1 && myText.indexOf(key,pos) < maxIndex){
            // look for the String key in myText
            int index = myText.indexOf(key,pos);
            // add the letter following key to the follows set
            follows.add(String.valueOf(myText.charAt(index+key.length())));
            // next time, start analyzing text starting from the added letter (included)
            pos = index+key.length();
        }
        return follows;
    }
}