package jsonicflow;
//package beans;

/**
 *
 * @author Keenan
 */
public class SongSearch
{
    String query;
    
    public SongSearch()
    {
        //empty constructor
    }
    
    public void setQuery(String query) { this.query = query.replaceAll("[^\\x00-\\x7F]", ""); }

    public String getQuery() { return query; }
}
