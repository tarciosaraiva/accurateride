package tarcio.accurateride.io;

import android.location.Location;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tarcio.accurateride.model.AccurateLocation;
import tarcio.accurateride.model.AccurateLocations;
import tarcio.accurateride.model.adapters.DateAdapter;

public class LocationsData {

    public static final String DATA_FILE = "location_data";

    private static final String TAG = "LocationsData";
    private static final Moshi MOSHI = new Moshi.Builder().add(new DateAdapter()).build();

    private List<AccurateLocation> accurateLocationCache;
    private JsonAdapter<AccurateLocations> adapter;
    private File dataFile;
    private float distance;

    public LocationsData(File file) {
        dataFile = file;
        accurateLocationCache = new ArrayList<>();
        adapter = MOSHI.adapter(AccurateLocations.class);
    }

    public void persistAndClearCache() {
        Log.d(TAG, "Loading location file");
        String jsonData = loadFile();
        AccurateLocations accurateLocations = new AccurateLocations();

        if (jsonData != null && !jsonData.equals("")) {
            Log.d(TAG, "JSON data is available. Parsing JSON.");
            try {
                accurateLocations = adapter.fromJson(jsonData);
            } catch (IOException e) {
                Log.e(TAG, "Could not parse JSON: " + e.getMessage());
            }
        }

        Log.d(TAG, "Adding cached locations to be written.");
        accurateLocations.addLocations(accurateLocationCache);

        distance = accurateLocations.calculateDistance();

        Log.d(TAG, "Stringifying object. Locations captured on file: [" + accurateLocations.size() + "]");
        final String accurateLocationJson = adapter.toJson(accurateLocations);

        Log.d(TAG, "Writing back to file");
        writeFile(accurateLocationJson);

        Log.d(TAG, "Clearing cache");
        accurateLocationCache.clear();
    }

    private String loadFile() {
        try {
            int c;
            String data = "";

            FileInputStream fis = new FileInputStream(dataFile);

            while ((c = fis.read()) != -1) {
                data = data + Character.toString((char) c);
            }

            fis.close();

            return data;
        } catch (IOException e) {
            Log.e(TAG, "IO Error: " + e.getMessage());
            return "";
        }
    }

    private void writeFile(String accurateLocationJson) {
        try {
            FileOutputStream fos = new FileOutputStream(dataFile); // openFileOutput(IO_FILE, Context.MODE_PRIVATE);
            fos.write(accurateLocationJson.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "IO Error: " + e.getMessage());
        }
    }

    public void addToCache(Location location) {
        accurateLocationCache.add(new AccurateLocation(location));
        Log.d(TAG, "Location cache has [" + accurateLocationCache.size() + "] entries");
    }


    public float getDistance() {
        return distance;
    }
}
