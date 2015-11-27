package tarcio.accurateride.model.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAdapter {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @ToJson
    public String toJson(Date date) {
        return DATE_FORMAT.format(date);
    }

    @FromJson
    public Date fromJson(String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }
}
