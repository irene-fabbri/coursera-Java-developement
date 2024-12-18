/**
 * Filters Quakes with depth whithin min and max
 * 
 * @author Irene Fabbri
 */
public class DepthFilter implements Filter
{
    private double depthMin;
    private double depthMax;
    private String name;
    public DepthFilter(double min, double max) { 
        depthMin = min;
        depthMax = max;
        name = getName();
    } 

    public boolean satisfies(QuakeEntry qe) {
        double depth = qe.getDepth();
        return (depth >= depthMin && depth <= depthMax); 
    }
    
        public String getName(){
        return "Depth";
    }

}
