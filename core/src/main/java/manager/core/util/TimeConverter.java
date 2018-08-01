package manager.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TimeConverter
{
    
    default DateTime stringToTimestamp(String timestamp) throws InvalidParameterException
    {
        String date;
        String time;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        
        Pattern datePattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}).*");
        Matcher dateMatcher = datePattern.matcher(timestamp);
        Pattern timePattern = Pattern.compile("^.*(\\d{2}:\\d{2}:\\d{2}).*");
        Matcher timeMatcher = timePattern.matcher(timestamp);
    
        if (dateMatcher.find() && timeMatcher.find())
        {
            date = dateMatcher.group(1);
            time = timeMatcher.group(1);
        }
        else
        {
            throw new InvalidParameterException();
        }
    
        timestamp = date + " " + time;
        
        return formatter.parseDateTime(timestamp);
    }
    
}
