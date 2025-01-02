/**
 * MarkovModel of order myOrder: generates random text by picking a random word from the 
 * most common followers of a set of n words in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> myMap;
    
    public EfficientMarkovWord(int n) {
        myRandom = new Random();
        myOrder = n;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
    }
    
    private void buildMap(){
        // map all sized myOrder substring of myText to their following word
        myMap = new HashMap< WordGram, ArrayList<String> >();
        
        for(int i = 0; i < myText.length-myOrder; i++){
            // get current key
            WordGram currKey = new WordGram(myText,i, myOrder);
            // get current follower
            String currNext = myText[i+myOrder];
            // add to map
            ArrayList<String> currValue;
            if(!myMap.containsKey(currKey)){
                currValue = new ArrayList<String>();
            } else {
                currValue = myMap.get(currKey);
            }
            currValue.add(currNext);
            myMap.put(currKey, currValue);
        }
        // last key
        WordGram lastKey = new WordGram(myText,myText.length-myOrder, myOrder);
        if(!myMap.containsKey(lastKey)){
            myMap.put(lastKey, new ArrayList<String>());
        }
        // print info on the map just built
        printHashMapInfo();
    }
    
    public void printHashMapInfo(){
        int max=0;
        for(WordGram key : myMap.keySet()){
            // print only if the map is small
            if(myMap.size() < 50){
                System.out.println("Key "+key+" "+myMap.get(key));
            }
            // get the max number of followers a key has
            if(myMap.get(key).size() > max){
                max = myMap.get(key).size();
            }
        }
        
        System.out.println("# of keys "+myMap.size());
        System.out.println("largest key have size "+max);
        
        for(WordGram key : myMap.keySet()){
            if(myMap.get(key).size() == max){
                System.out.println("Key "+key+" "+myMap.get(key));            
            }
        }
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
            //System.out.println("Keys "+kGram "+follows);
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
        if(myMap.containsKey(kGram)){
            return myMap.get(kGram);
        } else {
            return new ArrayList <String>();
        }
    }

}