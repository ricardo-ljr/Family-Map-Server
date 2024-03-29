package JSONReader;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Deserializer {

    public Deserializer() { }

    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }

    public static NamesData deserializeNameList(File filename) throws IOException {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            NamesData nameList = gson.fromJson(bufferedReader, NamesData.class);
            return nameList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static LocationData deserializeLocations(File filename) throws IOException {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            LocationData locationList = gson.fromJson(bufferedReader, LocationData.class);
            return locationList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}