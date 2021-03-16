package JSONReader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Array of names, class for data we're trying to parse
 */
public class NamesData {

    private String[] data;

//    public String getRandomName(){
//        Random rand = new Random();
//        return data.get(rand.nextInt(data.size()));
//    }

    public NamesData() {
        data = new String[152];
    }

    public String[] getName() {
        return data;
    }
}
