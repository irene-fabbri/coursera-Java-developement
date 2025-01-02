/**
 * MarkovModel of order myOrder: generates random text by picking a random word from the 
 * most common followers of a set of n words in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int n) {
        myRandom = new Random();
        myOrder = n;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram kGram = new WordGram(myText,index, myOrder);
        for(int i = 0; i < myOrder; i++) {
            sb.append(kGram.wordAt(i));
            sb.append(" ");
        }
        
        for(int k=0; k < numWords-1; k++){
            // get followers list
            ArrayList<String> follows = getFollows(kGram);
            if (follows.size() == 0) {
                break;
            }
            // pick one of them randomly
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            // get new key
            kGram = kGram.shiftAdd(next);
        }
        
        return sb.toString().trim();
    }
    
    private int indexOf(String[] words, WordGram target, int start){
        int size = target.length();
        for(int i = start; i <= words.length-myOrder; i++){
            WordGram thisBit = new WordGram(words, i, size);
            if(thisBit.equals(target)){
                return i;
            }
        }
        return -1;
    }
    
    // return the follower set of a given key when present or an empty set
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        int maxIndex = myText.length - myOrder;
        while(pos < maxIndex){
            int index = indexOf(myText,kGram,pos);
            if(index == -1 || index == maxIndex){
                break;
            }
            String next = myText[index+myOrder];
            follows.add(next);
            pos = index+myOrder;
        }
        return follows;
    }

}