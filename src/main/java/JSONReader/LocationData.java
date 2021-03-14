package JSONReader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Array of locations, class for data we're trying to parse
 */
public class LocationData {
    ArrayList<Location> data;

    public Location getRandomLocation(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }
}
