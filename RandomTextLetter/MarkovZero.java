/**
 * MarkovModel of order 0: generates random text by picking a random char in
 * a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.Random;

public class MarkovZero extends AbstractMarkovModel{
    
    public MarkovZero() {
        myRandom = new Random();
    }
    
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int k=0; k < numChars; k++){
            // pick char at random in myText
            int index = myRandom.nextInt(myText.length());
            sb.append(myText.charAt(index));
        }
        
        return sb.toString();
    }
    
    public String toString(){
        return "MarkovModel of order 0";
    }
}
