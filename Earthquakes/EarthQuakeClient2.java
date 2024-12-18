/**
 * Some tests
 * 
 * @author Irene Fabbri
 */
import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        Location tokyo = new Location (35.42,139.43);
        Filter f1 = new DistanceFilter(10000000, tokyo);
        ArrayList<QuakeEntry> f1List  = filter(list, f1);
        
        Filter f2 = new PhraseFilter("end","Japan");
        ArrayList<QuakeEntry> f1f2List = filter(f1List, f2);
        for (QuakeEntry qe: f1f2List) { 
            System.out.println(qe);
        }
    }
    
    public void testMatchAllFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0,3.0));
        Location tulsa = new Location (36.1314, -95.9372);
        maf.addFilter(new DistanceFilter(10000000.,tulsa));
        maf.addFilter(new PhraseFilter("any", "Ca"));
        String used = maf.getName();
        System.out.println(used);
        ArrayList<QuakeEntry> fList  = filter(list, maf);
        
        for (QuakeEntry qe: fList) { 
            System.out.println(qe);
        }
    }

    public void testQuiz() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes\n");
        
        System.out.println("In Japan and less than 10000000 m from Tokyo:");
        Location tokyo = new Location (35.42,139.43);
        Filter f1 = new DistanceFilter(10000000, tokyo);
        ArrayList<QuakeEntry> f1List  = filter(list, f1);
        
        Filter f2 = new PhraseFilter("end","Japan");
        ArrayList<QuakeEntry> f1f2List = filter(f1List, f2);
        for (QuakeEntry qe: f1f2List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+f1f2List.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 4.0 and 5.0 inclusive and depth between -35,000.0 and -12,000.0 inclusive:");
        
        Filter f3 = new MagnitudeFilter(4.0, 5.0);
        ArrayList<QuakeEntry> f3List  = filter(list, f3);
        
        Filter f4 = new DepthFilter(-35000.,-12000);
        ArrayList<QuakeEntry> f3f4List = filter(f3List, f4);
        for (QuakeEntry qe: f3f4List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+f3f4List.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 0.0 and 2.0, depth between -100,000.0 and -10,000.0, and the letter “a” in the title");
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0,2.0));
        maf.addFilter(new DepthFilter(-100000.,-10000.));
        maf.addFilter(new PhraseFilter("any", "a"));
        String used = maf.getName();
        System.out.println(used);
        ArrayList<QuakeEntry> mfList  = filter(list, maf);
        
        for (QuakeEntry qe: mfList) { 
            System.out.println(qe);
        }
        System.out.println("Found "+mfList.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 0.0 and 3.0, distance from Tulsa, Oklahoma less than 10,000,000 meters, and “Ca” in the title");
        MatchAllFilter maf2 = new MatchAllFilter();
        maf2.addFilter(new MagnitudeFilter(0.0,3.0));
        Location tulsa = new Location (36.1314, -95.9372);
        maf2.addFilter(new DistanceFilter(10000000.,tulsa));
        maf2.addFilter(new PhraseFilter("any", "Ca"));
        String used2 = maf2.getName();
        System.out.println(used2);
        ArrayList<QuakeEntry> mf2List  = filter(list, maf2);

        for (QuakeEntry qe: mf2List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+mf2List.size()+" thet match these criteria");
        
    }

        public void testFinalQuiz() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes\n");
        
        System.out.println("Less than 1000000 from Denver and end with with a in title:");
        Location denver = new Location (39.7392, -104.9903);
        Filter f1 = new DistanceFilter(1000000, denver);
        ArrayList<QuakeEntry> f1List  = filter(list, f1);
        
        Filter f2 = new PhraseFilter("end","a");
        ArrayList<QuakeEntry> f1f2List = filter(f1List, f2);
        for (QuakeEntry qe: f1f2List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+f1f2List.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 3.5 and 4.5 inclusive and depth between -55,000.0 and -20,000.0 inclusive:");
        
        Filter f3 = new MagnitudeFilter(3.5, 4.5);
        ArrayList<QuakeEntry> f3List  = filter(list, f3);
        
        Filter f4 = new DepthFilter(-55000.,-20000);
        ArrayList<QuakeEntry> f3f4List = filter(f3List, f4);
        for (QuakeEntry qe: f3f4List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+f3f4List.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 1.0 and 4.0, depth between -180,000.0 and -30,000.0, and the letter “o” in the title");
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(1.0,4.0));
        maf.addFilter(new DepthFilter(-180000.,-30000.));
        maf.addFilter(new PhraseFilter("any", "o"));
        String used = maf.getName();
        System.out.println(used);
        ArrayList<QuakeEntry> mfList  = filter(list, maf);
        
        for (QuakeEntry qe: mfList) { 
            System.out.println(qe);
        }
        System.out.println("Found "+mfList.size()+" thet match these criteria");
        
        System.out.println("\n With magnitude between 0.0 and 5.0, distance from Billund less than 3,000,000 meters, and “e” in the title");
        MatchAllFilter maf2 = new MatchAllFilter();
        maf2.addFilter(new MagnitudeFilter(0.0,5.0));
        Location billund = new Location (55.7308, 9.1153);
        maf2.addFilter(new DistanceFilter(3000000.,billund));
        maf2.addFilter(new PhraseFilter("any", "e"));
        String used2 = maf2.getName();
        System.out.println(used2);
        ArrayList<QuakeEntry> mf2List  = filter(list, maf2);

        for (QuakeEntry qe: mf2List) { 
            System.out.println(qe);
        }
        System.out.println("Found "+mf2List.size()+" thet match these criteria");
        
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
