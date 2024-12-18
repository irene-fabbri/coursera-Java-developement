/**
 * Filters Quakes with a certain String at the start, 
 * end or anywhere in their description
 * @author Irene Fabbri
 */

public class PhraseFilter implements Filter
{
    private String where;
    private String phrase;
    private String name;
    
    public PhraseFilter(String w, String p) { 
        where = w;
        if((where.equals("start")||where.equals("end")||where.equals("any")) == false){
            System.out.println("Invalid input for PhraseFilter: where is "+where+" instead of start/end/any");
        }
        phrase = p;
        name = getName();
    }

    public boolean satisfies(QuakeEntry qe) {
        String title = qe.getInfo();
        return (where.equals("start") && title.startsWith(phrase)) || 
               (where.equals("end") && title.endsWith(phrase)) ||
               (where.equals("any") && (title.indexOf(phrase) != -1));
    }
    
        public String getName(){
        return "Phrase";
    }
}
