/**
 * Filters Quakes with magnitude whithin min and max
 * 
 * @author Irene Fabbri
 */
public class MagnitudeFilter implements Filter
{
    private double magMin;
    private double magMax;
    private String name;
    public MagnitudeFilter(double min, double max) { 
        magMin = min;
        magMax = max;
        name = getName();
    } 

    public boolean satisfies(QuakeEntry qe) {
        double mag = qe.getMagnitude();
        return (mag >= magMin && mag <= magMax); 
    }
    
    public String getName(){
        return "Magnitude";
    }

}
