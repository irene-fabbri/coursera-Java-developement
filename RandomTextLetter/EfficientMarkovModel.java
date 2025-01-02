
/**
 * MarkovModel of order n: generates random text by picking a random char from the 
 * most common followers of a set of n chars in a given training text (myText)
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class EfficientMarkovModel extends AbstractMarkovModel{
    private int N;
    private HashMap< String , ArrayList<String> > myMap;
    
    public EfficientMarkovModel(int order) {
        myRandom = new Random();
        N = order;
        myMap = new HashMap< String , ArrayList<String> >();
    }
    
    public void setTraining(String s) {
        myText = s.trim();
        buildMap();
    }
    
    private void buildMap(){
        // map all sized N substring of myText to their following char
        myMap = new HashMap< String , ArrayList<String> >();
        
        for(int i = 0; i < myText.length()-N; i++){
            // get current key
            String currKey = myText.substring(i, i+N);
            // get current follower
            String currNext = String.valueOf(myText.charAt(i+N));
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
        String lastKey = myText.substring(myText.length()-N);
        if(!myMap.containsKey(lastKey)){
            myMap.put(lastKey, new ArrayList<String>());
        }
        // print info on the map just built
        printHashMapInfo();
    }
    
    // return the follower set of a given key when present or an empty set
    public ArrayList<String> getFollows(String key){
        if(myMap.containsKey(key)){
            return myMap.get(key);
        } else {
            return new ArrayList <String>();
        }
    }
    
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // pick starting chars at random in myText
        int index = myRandom.nextInt(myText.length()-N);
        String key = myText.substring(index, index +N);
        sb.append(key);
        
        for(int k=0; k < numChars-N; k++){
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

    public void printHashMapInfo(){
        int max=0;
        for(String key : myMap.keySet()){
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
        
        for(String key : myMap.keySet()){
            if(myMap.get(key).size() == max){
                System.out.println("Key "+key+" "+myMap.get(key));            
            }
        }
    }
    
    public String toString(){
        return "EfficientMarkovModel of order "+N; 
    }
}
