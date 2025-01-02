
/**
 * Some test for Markov Model of order n using words
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        long start = System.nanoTime();
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        }
        long stop = System.nanoTime();
        long elapsed = stop-start;
        System.out.println("Elapsed time using "+markov+" "+ elapsed);
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        long start = System.nanoTime();
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        }
        long stop = System.nanoTime();
        long elapsed = stop-start;
        System.out.println("Elapsed time using "+markov+" "+ elapsed);
    } 

    public void runMarkov() { 
        //FileResource fr = new FileResource(); 
        //String st = fr.asString(); 
        //st = st.replace('\n', ' '); 
        //MarkovWord markovWord = new MarkovWord(3); 
        //runModel(markovWord, st, 200, 643);
        
        String st = "this is a test yes this is really a test yes a test this is wow"; 
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2); 
        runModel(markovWord, st, 50, 42);
    }
    
    public void runQuiz() { 
        FileResource fr = new FileResource("data/confucius.txt"); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord mW3 = new MarkovWord(3); 
        runModel(mW3, st, 50, 621);
        
        MarkovWord mW5 = new MarkovWord(5);
        runModel(mW5, st, 50, 844);
        
        EfficientMarkovWord emW3 = new EfficientMarkovWord(3);  
        runModel(emW3, st, 50, 371);
        
        EfficientMarkovWord emW2 = new EfficientMarkovWord(2);  
        runModel(emW2, st, 50, 65);
    }
       
    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');        
        int seed = 42;
        int size = 100;
        
        EfficientMarkovWord emTwo = new EfficientMarkovWord(2);
        runModel(emTwo, st, size, seed);
        
        MarkovWord mTwo = new MarkovWord(2);
        runModel(mTwo, st, size, seed);
    }

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
