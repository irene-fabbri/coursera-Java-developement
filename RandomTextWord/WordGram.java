/**
 * Class for keys in a MarkovModel of order n using words:
 *  each key consist in a collection of myOrder words
 *  after randomly generating a word, the new key consists in the
 *  old key words but the first, plus the word just generated
 * @author Irene Fabbri
 */

public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
        myHash = hashCode();
    }
    
    public int hashCode(){
        String text = "";
        for(int i=0; i<myWords.length; i++) {
            text += myWords[i]+" ";             
        }
        return text.hashCode();
    }
    
    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for(int i=0; i<myWords.length; i++) {
            ret += myWords[i]+" ";             
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if(length() != other.length()){
            return false;
        }
        for(int k = 0; k < myWords.length; k++) {
            if(! myWords[k].equals(other.wordAt(k))){
                return false;
            }
        }
        return true;
    }

    public WordGram shiftAdd(String word) {    
        WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end.
        // you lose the first word
        for(int k=1; k < myWords.length; k++){
            out.myWords[k-1] = myWords[k];            
        }
        out.myWords[myWords.length-1]= word;
        return out;
    }
}