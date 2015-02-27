/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html

 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.

 The Original Code is the "SensorML DataProcessing Engine".

 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.

 Contributor(s):
 Alexandre Robin <robin@nsstc.uah.edu>

 ******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * TLEParser_Process
 * </p>
 * 
 * <p><b>Description:</b><br/>
 * Loads TLE data from a TLE data file (gzip or not) and outputs
 * the TLE corresponding to the requested time.
 * </p>
 * 
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date February 27, 2006
 * @version 1.0
 */
public class TLEParser_Process extends DataProcess
{
    // input time
    protected DataValue time;
    
    // output TLE elements
    protected DataValue paramEpochYear;
    protected DataValue paramEpochDay;
    protected DataValue paramBstar;
    protected DataValue paramInclination;
    protected DataValue paramRightAscension;
    protected DataValue paramEccentricity;
    protected DataValue paramArgOfPerigee;
    protected DataValue paramMeanAnomaly;
    protected DataValue paramMeanMotion;
    protected DataValue paramRevNumber;
    
    // internal variables
    protected String tlePath;
    protected BufferedReader tleReader;
    protected int lineNumber = 0;
    protected String currentLine1, previousLine1, nextLine1;
    protected String currentLine2, previousLine2, nextLine2;
    protected double currentTime, nextTime;
    
    // inner structure for TLE params
    public class TLEInfo
    {
        public String epochYear;
        public String epochDay;
        public String bstar;
        public String inclination;
        public String rightAscension;
        public String eccentricity;
        public String argOfPerigee;
        public String meanAnomaly;
        public String meanMotion;
    }
    
    
    public TLEParser_Process()
    {
        reset();
    }
    
    
    public TLEParser_Process(String tlePath)
    {
        this();
        this.tlePath = tlePath;

        try
        {
            tleReader = new BufferedReader(new FileReader(tlePath));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     * Preloads TLE Data File
     */
    public void init() throws ProcessException
    {
        try
        {
            // Input mappings
            time = (DataValue) inputData.getComponent("julianTime");
            
            // Output mappings
            DataGroup paramElements = (DataGroup) outputData.getComponent("elements");
            paramEpochYear = (DataValue) paramElements.getComponent("epochYear");
            paramEpochDay = (DataValue) paramElements.getComponent("epochDay");
            paramBstar = (DataValue) paramElements.getComponent("bstar");
            paramInclination = (DataValue) paramElements.getComponent("inclination");
            paramRightAscension = (DataValue) paramElements.getComponent("rightAscension");
            paramEccentricity = (DataValue) paramElements.getComponent("eccentricity");
            paramArgOfPerigee = (DataValue) paramElements.getComponent("argOfPerigee");
            paramMeanAnomaly = (DataValue) paramElements.getComponent("meanAnomaly");
            paramMeanMotion = (DataValue) paramElements.getComponent("meanMotion");
            paramRevNumber = (DataValue) paramElements.getComponent("revNumber");
            
            // Get TLE file name
            tlePath = paramData.getComponent("tleDataUrl").getData().getStringValue();
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
        
        reset();
    }
    
    
    public void reset()
    {
        closeFile();
        currentTime = Double.NEGATIVE_INFINITY;
        nextTime = Double.NEGATIVE_INFINITY;
        currentLine1 = "";
        previousLine1 = "";
        nextLine1 = "";
        currentLine2 = "";
        previousLine2 = "";
        nextLine2 = "";
        lineNumber = 0;
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    public void execute() throws ProcessException
    {
        double t = time.getData().getDoubleValue();
        
        try
        {
            TLEInfo tle = null;
            
            if (tleReader != null && t >= currentTime)
            {
                tle = readTLE(t);
            }
            else
            {
                reset();
                tleReader = new BufferedReader(new FileReader(tlePath));
                tle = readTLE(t);
            }
            
            paramEpochYear.getData().setStringValue(tle.epochYear);
            paramEpochDay.getData().setStringValue(tle.epochDay);
            paramBstar.getData().setStringValue(tle.bstar);
            paramInclination.getData().setStringValue(tle.inclination);
            paramRightAscension.getData().setStringValue(tle.rightAscension);
            paramEccentricity.getData().setStringValue(tle.eccentricity);
            paramArgOfPerigee.getData().setStringValue(tle.argOfPerigee);
            paramMeanAnomaly.getData().setStringValue(tle.meanAnomaly);
            paramMeanMotion.getData().setStringValue(tle.meanMotion);
            
            //System.err.println(t + ": " + tle.inclination);
        }
        catch (IOException e)
        {
            reset();
            throw new ProcessException("Cannot read from TLE file", e);            
        }
    }
    
    
    /**
     * Reads all TLEs for the given time range
     * @param timeRange
     * @return list of TLE objects
     */
    public List<TLEInfo> readTLEList(double startTime, double stopTime) throws IOException
    {
        List<TLEInfo> tleList = new ArrayList<TLEInfo>();
        
        TLEInfo tle = readTLE(startTime);
        tleList.add(tle);
        while ((tle = readNextTLE(stopTime)) != null)
            tleList.add(tle);
        
        return tleList;
    }


    /**
     * Reads the next TLE
     * @param stopTime
     * @return
     * @throws IOException
     */
    public TLEInfo readNextTLE(double stopTime) throws IOException
    {
        boolean isLastEntry = readNextEntry();
        if (isLastEntry)
            return null;
        
        double tleTime = getJulian(currentLine1);
        if (tleTime > stopTime)
            return null;
        
        return parseTLE(currentLine1, currentLine2);
    }
    
    
    /**
     * Reads the closest TLE to the given time 
     * @param desiredTime
     * @return
     * @throws IOException
     */
    public TLEInfo readTLE(double desiredTime) throws IOException
    {
        boolean isLastEntry = false;
        
        // loop until we find the good TLE for the requested time
        while (desiredTime > nextTime)
        {
            // read next 2 lines
            isLastEntry = readNextEntry();
            if (isLastEntry)
                break;
            
            // check to see if this is the right time
            currentTime = nextTime;
            nextTime = getJulian(nextLine1);
        }        
        
        // if no more lines, use last one
        if (!isLastEntry)
        {
            // now we have the tle greater than the input time,
            // which currently lies between next and current positions,
            // determine which TLE is closer
            double currentDelta = Math.abs(currentTime - desiredTime);
            double nextDelta = Math.abs(nextTime - desiredTime);

            if (currentDelta > nextDelta)
                return parseTLE(nextLine1, nextLine2);
            else
                return parseTLE(currentLine1, currentLine2);
        }
        
        // parse TLE
        return parseTLE(currentLine1, currentLine2);
    }
    
    
    /**
     * Try to read next TLE entry
     * @return true if EOF
     */
    protected boolean readNextEntry()
    {
        try
        {
            tleReader.mark(140);
            previousLine1 = currentLine1;
            previousLine2 = currentLine2;
            currentLine1 = nextLine1;
            currentLine2 = nextLine2;

            // skip blank lines and get line 1
            do
            {
                nextLine1 = tleReader.readLine();
            }
            while ((nextLine1 != null) && (nextLine1.length() == 0));

            // return if EOF
            if (nextLine1 == null)
            {
                nextLine1 = currentLine1;
                nextLine2 = currentLine2;
                return true;
            }

            // skip blank lines and get line 2
            do
            {
                nextLine2 = tleReader.readLine();
            }
            while (nextLine2.length() == 0);
            lineNumber++;
            
            return false;
        }
        catch (IOException e)
        {
            return true;
        }
    }
    
    
    /**
     * Parse one TLE entry and create a TLEInfo object 
     * @param lineBuffer1
     * @param lineBuffer2
     * @return
     */
    protected TLEInfo parseTLE(String lineBuffer1, String lineBuffer2)
    {
        TLEInfo tle = new TLEInfo();

        tle.epochYear = lineBuffer1.substring(18,20).trim();
        if (Integer.parseInt(tle.epochYear) < 50)
            tle.epochYear = "20" + tle.epochYear;
        else
            tle.epochYear = "19" + tle.epochYear;
        
        tle.epochDay = lineBuffer1.substring(20,32).trim();
        
        tle.bstar = lineBuffer1.charAt(53) + "0." + lineBuffer1.substring(54,59).trim() + "e" + lineBuffer1.substring(59,61);
        
        tle.inclination = lineBuffer2.substring(8,16).trim();
        tle.rightAscension = lineBuffer2.substring(17,25).trim();
        tle.eccentricity = "0." + lineBuffer2.substring(26,33).trim();
        tle.argOfPerigee = lineBuffer2.substring(34,42).trim();
        tle.meanAnomaly = lineBuffer2.substring(43,51).trim();
        tle.meanMotion = lineBuffer2.substring(52,63).trim();
        
        return tle;
    }
    
    
    /**
     * Get Julian time for Line1 of TLE entry
     * @param lineBuffer
     * @return
     */
    protected double getJulian(String lineBuffer)
    {
        int year = Integer.valueOf(lineBuffer.substring(18, 20).trim()).intValue();
        double doyFrac = Double.valueOf(lineBuffer.substring(20, 32).trim()).doubleValue();

        // change to 4 digit year with Y2K check (good til 2050)
        year = (year < 50) ? 2000 + year : 1900 + year;

        // convert to julian time
        double julianTime = (doyFrac - 1.0) * 3600 * 24;
        for (int i = 1970; i < year; i++)
            julianTime += ((i % 4) == 0) ? 31622400.0 : 31536000.0;

        return julianTime;
    }
    
    
    /**
     * Call to close the TLE data file when done
     */
    public void closeFile()
    {
        try
        {
            if (tleReader != null)
            {
                tleReader.close();
                tleReader = null;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
    protected void finalize()
    {
        closeFile();
    }
}