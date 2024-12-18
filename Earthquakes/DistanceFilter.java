/**
 * Filters Quakes within a certain distance (max) to a location (loc)
 * 
 * @author Irene Fabbri
 */
public class DistanceFilter implements Filter
{
    private double distMax;
    private Location centralCoord;
    private String name;
    
    public DistanceFilter(double max, Location loc) { 
        distMax = max;
        centralCoord = loc;
        name = getName();
    }

    public boolean satisfies(QuakeEntry qe) {
        Location location = qe.getLocation();
        double dist = centralCoord.distanceTo(location);
        return dist < distMax;
    } 

    public String getName(){
        return "Distance";
    }
}
