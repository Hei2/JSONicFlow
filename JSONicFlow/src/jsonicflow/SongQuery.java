package jsonicflow;
//package beans;

import com.json.parsers.JsonParserFactory;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import com.json.parsers.JSONParser;
import java.util.HashMap;

/**
 *
 * @author Keenan
 */
public class SongQuery
{
    final static String apiKey = "ef4ce04f5581e3cb6736e4ada4077c15";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //Remember to pass queries surrounded by quotes.
        String query = args[0];
        HashMap<String, String> songInfo = getSongInfo(query);
        buildWebPage(songInfo);
    }

    public static HashMap<String, String> getSongInfo(String query)
    {
        query = query.replaceAll("[^\\x00-\\x7F]", "");
        query = query.trim();
        query = query.replaceAll("\\s+", "+"); //Replace strings of spaces with a single '+'
        query = "http://tinysong.com/b/" + query + "?format=json&key=" + apiKey;
        
        String json = getJson(query);
        
        HashMap<String, String> songInfo = new HashMap<String, String>();
        
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map jsonData;
        
        if (!json.equals("[]\n"))
        {
            jsonData = parser.parseJson(json);
            songInfo.put("URL", (String)jsonData.get("Url"));
            songInfo.put("SongID", (String)jsonData.get("SongID"));
            songInfo.put("SongName", (String)jsonData.get("SongName"));
            songInfo.put("ArtistID", (String)jsonData.get("ArtistID"));
            songInfo.put("ArtistName", (String)jsonData.get("ArtistName"));
            songInfo.put("AlbumID", (String)jsonData.get("AlbumID"));
            songInfo.put("AlbumName", (String)jsonData.get("AlbumName"));
        }
        else
        {
            songInfo.put("URL", "N/A");
            songInfo.put("SongID", "N/A");
            songInfo.put("SongName", "N/A");
            songInfo.put("ArtistID", "N/A");
            songInfo.put("ArtistName", "N/A");
            songInfo.put("AlbumID", "N/A");
            songInfo.put("AlbumName", "N/A");
        }
        
        return songInfo;
    }
    
    public static String getJson(String u)
    {
        try
        {
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();
            int status = connection.getResponseCode();
            
            switch (status)
            {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
                default: System.out.println("Default case for status reached.");
            }
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Caught MalformedURLException");
        }
        catch (IOException ex)
        {
            System.out.print("Caught IOException");
        }
        return null;
    }
    
    private static void buildWebPage(HashMap<String, String> songInfo)
    {
        System.out.print("<HTML>" +
        "<BODY bgcolor=\"Black\">" +
        "<table border=\"1\" bgcolor=\"White\">" +
        "	<tr>" +
        "		<td>Song:</td>" +
        "		<td>" + songInfo.get("SongName") + "</td>" +
        "	</tr>" +
        "	<tr>" +
        "		<td>Artist:</td>" +
        "		<td>" + songInfo.get("ArtistName") + "</td>" +
        "	</tr>" +
        "	<tr>" +
        "		<td>Album:</td>" +
        "		<td>" + songInfo.get("AlbumName") + "</td>" +
        "	</tr>" +
        "</table><BR>" +

        "<object width=\"250\" height=\"40\">" +
        "	<param name=\"movie\" value=\"http://grooveshark.com/songWidget.swf\">" +
        "	<param name=\"wmode\" value=\"window\">" +
        "	<param name=\"allowScriptAccess\" value=\"always\">" +
        "	<param name=\"flashvars\" value=\"hostname=cowbell.grooveshark.com&amp;songIDs=" + songInfo.get("SongID") + "&amp;style=metal&amp;p=1\">" +
        "	<embed src=\"http://grooveshark.com/songWidget.swf\" type=\"application/x-shockwave-flash\" width=\"250\" height=\"40\" flashvars=\"hostname=cowbell.grooveshark.com&amp;songIDs=" + songInfo.get("SongID") + "&amp;style=metal&amp;p=1\" allowscriptaccess=\"always\" wmode=\"window\">" +
        "</object><BR>" +

        "<a href=\"FirstPage.html\"><button type=\"button\">Back</button></a></center>" +
        "</BODY>" +
        "</HTML>");
    }
}