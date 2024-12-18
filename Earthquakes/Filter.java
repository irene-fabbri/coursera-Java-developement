/**
 * Filter interface: filters the Quake Entries satisfying a certain condition
 * 
 * @author Irene Fabbri
 */
public interface Filter
{
    public  boolean satisfies(QuakeEntry qe);
    
    public String getName();
}
