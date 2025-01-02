
/**
 * IMarkovModel: Interface for a Markov Model random text generator.
 * generate random text using a training text
 * 
 * @author Irene Fabbri 
 */

public interface IMarkovModel {
    public void setTraining(String text);

    public void setRandom(int seed);

    public String getRandomText(int numChars);
    
    public String toString();
    
}
