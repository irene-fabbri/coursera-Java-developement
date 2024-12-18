
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 

public class MarkovRunnerWithInterface {
    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        long start = System.nanoTime();
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
        long stop = System.nanoTime();
        long elapsed = stop-start;
        System.out.println("Elapsed time using "+markov+" "+ elapsed);
    }
    
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;
        int seed = 15;
        
        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size, seed);
    
        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size, seed);
        
        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size, seed);
        
        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size, seed);

    }
    
    public void testHashMap(){
        String st = "yes-this-is-a-thin-pretty-pink-thistle";
        int seed = 42;
        int size = 50;
        
        EfficientMarkovModel mTwo = new EfficientMarkovModel(2);
        runModel(mTwo, st, size, seed);
    }
    
    public void testQuiz(){
        FileResource fr = new FileResource("data/Romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');        
        int seed = 615;
        int size = 50;
        
        EfficientMarkovModel emFive = new EfficientMarkovModel(5);
        runModel(emFive, st, size, seed);
    }
    
    public void testFinalQuiz(){
        FileResource fr = new FileResource("data/confucius.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');        
        int seed = 792;
        int size = 50;
        
        EfficientMarkovModel em6 = new EfficientMarkovModel(6);
        runModel(em6, st, size, seed);
        
        seed = 531;       
        EfficientMarkovModel em5 = new EfficientMarkovModel(5);
        runModel(em5, st, size, seed);
    }

    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');        
        int seed = 42;
        int size = 1000;
        
        EfficientMarkovModel emTwo = new EfficientMarkovModel(2);
        runModel(emTwo, st, size, seed);
        
        MarkovModel mTwo = new MarkovModel(2);
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
