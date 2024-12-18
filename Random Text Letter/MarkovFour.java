/**
 * MarkovModel of order 4: generates random text by picking a random char from the 
 * most common followers of a set of 4 chars in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class MarkovFour extends AbstractMarkovModel{
    
    public MarkovFour() {
        myRandom = new Random();
    }
        
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // pick starting char at random in myText
        int index = myRandom.nextInt(myText.length()-4);
        String key = myText.substring(index, index +4);
        sb.append(key);
        
        for(int k=0; k < numChars-4; k++){
            // get all the letter following key in myText
            ArrayList <String> follows = getFollows(key);
            if(follows.size() == 0){
                break;
            }
            // pick one of them randomly
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            // the new key is the old one but first char, plus the char just added
            key = key.substring(1)+next;
        }
        
        return sb.toString();
    }
    
    public String toString(){
        return "MarkovModel of order 4"; 
    }
}
