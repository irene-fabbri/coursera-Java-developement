/**
 * MarkovModel of order 1: generates random text by picking a random char from the 
 * most common followers of a char in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class MarkovOne extends AbstractMarkovModel{
    
    public MarkovOne() {
        myRandom = new Random();
    }
       
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        // pick starting char at random in myText
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-1);
        String key = myText.substring(index, index +1);
        sb.append(key);
        for(int k=0; k < numChars-1; k++){
            // get all the letter following key in myText
            ArrayList <String> follows = getFollows(key);
            if(follows.size() == 0){
                break;
            }
            // pick one of them randomly
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            // the new key is the found char
            key = next;
        }
        
        return sb.toString();
    }
    
    public String toString(){
        return "MarkovModel of order 1"; 
    }
}
