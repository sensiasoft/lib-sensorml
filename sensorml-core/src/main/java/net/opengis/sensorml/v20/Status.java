package net.opengis.sensorml.v20;



/**
 * POJO class for XML type StatusType(@http://www.opengis.net/sensorml/2.0).
 *
 */
public enum Status
{
    ENABLED("enabled"),
    DISABLED("disabled");
    
    private final String text;
    
    
    
    /**
     * Private constructor for storing string representation
     */
    private Status(String s)
    {
        this.text = s;
    }
    
    
    
    /**
     * To convert an enum constant to its String representation
     */
    public String toString()
    {
        return text;
    }
    
    
    
    /**
     * To get the enum constant corresponding to the given String representation
     */
    public static Status fromString(String s)
    {
        if (s.equals("enabled"))
            return ENABLED;
        else if (s.equals("disabled"))
            return DISABLED;
        
        throw new IllegalArgumentException("Invalid token " + s + " for enum Status");
    }
}
