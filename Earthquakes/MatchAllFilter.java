/**
 * Filters Quakes satisfying a set of Filters
 * 
 * @author Irene Fabbri
 */

import java.util.*;

public class MatchAllFilter implements Filter
{
    private ArrayList<Filter> filters;
    private String name;
    
    public MatchAllFilter() { 
        filters = new ArrayList<Filter>();
        name = getName();
    }
    
    public void addFilter(Filter f){
        filters.add(f);
    }

    public boolean satisfies(QuakeEntry qe) {
        for(Filter f : filters){
            if(!f.satisfies(qe)){
                return false;
            }
        }
        return true;
    }
    
    public String getName(){
        StringBuilder names = new StringBuilder("Filters used are:");
        for(Filter f : filters){
            names.append(" ");
            names.append(f.getName());
        }
        return names.toString();
    }
}