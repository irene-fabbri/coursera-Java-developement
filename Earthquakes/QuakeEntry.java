/**
 * Quake Entry class. Each Quake Entry has latitude and longitude, a title, 
 * a depth and a magnitude.
 * Quakes can be compared using their latitude and longitude
 * 
 * @author Irene Fabbri
 */

public class QuakeEntry implements Comparable<QuakeEntry>{
    
    private Location myLocation;
    private String title;
    private double depth;
    private double magnitude;

    public QuakeEntry(double lat, double lon, double mag, 
                      String t, double d) {
        myLocation = new Location(lat,lon);
        
        magnitude = mag;
        title = t;
        depth = d;
    }
    
    public Location getLocation(){
        return myLocation;
    }
    
    public double getMagnitude(){
        return magnitude;
    }
    
    public String getInfo(){
        return title;
    }
    
    public double getDepth(){
        return depth;
    }

    @Override
    public int compareTo(QuakeEntry loc) {
        // compare the latitudes
        double difflat = myLocation.getLatitude() - loc.myLocation.getLatitude();
        //if they are almost the same
        if (Math.abs(difflat) < 0.001) {
            // compare the longitudes
            double diff = myLocation.getLongitude() - loc.myLocation.getLongitude();
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
        }
        if (difflat < 0) return -1;
        if (difflat > 0) return 1;
        
        // never reached
        return 0;
    }
    
    public String toString(){
        return String.format("(%3.2f, %3.2f), mag = %3.2f, depth = %3.2f, title = %s", myLocation.getLatitude(),myLocation.getLongitude(),magnitude,depth,title);
    }
    
}