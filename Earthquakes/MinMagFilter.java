/**
 * Filters Quakes with magnitude bigger than magMin
 * 
 * @author Irene Fabbri
 */
public class MinMagFilter implements Filter
{
    private double magMin; 
    private String name;
    public MinMagFilter(double min) { 
        magMin = min;
        name = getName();
    } 

    public boolean satisfies(QuakeEntry qe) { 
        return qe.getMagnitude() >= magMin; 
    } 

    public String getName(){
        return "MinMag";
    }
}
