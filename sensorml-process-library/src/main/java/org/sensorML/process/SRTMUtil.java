package org.sensorML.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Properties;

import javax.vecmath.Vector3d;

import org.vast.math.BilinearInterpolation;


//Utility class to do some common SRTM lookup stuff- extracted some of this from SRTMReader
//for the WRTM WCS to make it more accesible to SRTM Process
public class SRTMUtil {
	String dataRoot;  // Place in props file
	String filename;
	RandomAccessFile file = null;	
	static final double RES_1ARCSEC = 1.0/3600.0;  // .0027777777
	public static int NUM_ROWS_FILE = 3601, NUM_COLS_FILE = 3601;
	static int BYTES_PER_PIXEL = 2;
	double lat0, lon0;  // origin of current file
	
	public SRTMUtil() {
		loadProps();
	}

	private void loadProps() {
		InputStream is = null;
		try {
			Properties srtmProps = new Properties();
			URL propsUrl = SRTMUtil.class.getResource("srtmProps.txt"); 
			is = propsUrl.openConnection().getInputStream();
			srtmProps.load(is);
			//  go ahead and pull values to explicit variables 
			dataRoot = srtmProps.getProperty("dataRoot");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(dataRoot == null)
				//  default to vastServer location if props load failed
				dataRoot = "/data/srtm/US/1arcsec";
			try {
				if(is!=null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void openFile(String filepath) throws IOException {
		file = new RandomAccessFile(filepath, "r");
	}

	//  Convenience method to do everything in one call.
	//  If optimization is needed, don't use this method, but do the steps individually
	public double getInterpolatedElevation(double lat, double lon) throws IOException{
	    // Open file containing lat/lon point?
        openFile(lat,lon);
        //if (file == null)
        //    return 0.0;
        
        // Compute the 4 corners containing this lat lon
        Vector3d [] corners = getCorners(lat, lon);     
        BilinearInterpolation bi = new BilinearInterpolation();
        bi.setCorners(corners[0], corners[1], corners[2], corners[3]);
        double result = bi.interpolate(lon, lat);
        //System.out.println(result + " m");
        return result;
	}

	private boolean fileContains(double lat, double lon){
		if(lat < lat0 || lat > lat0 + 1.0)
			return false;
		if(lon < lon0 || lon > lon0 + 1.0)
			return false;
		
		return true;
	}
	
	public Vector3d [] getCorners(double lat, double lon) throws IOException {
		Vector3d [] corners = new Vector3d[4];
		
		//  Compute floating pt pixel index of lat-lon
		double deltaLat = lat-lat0;
		double deltaLon = lon-lon0;
		double latIndexD = deltaLat/RES_1ARCSEC;
		double lonIndexD = deltaLon/RES_1ARCSEC;
		//  Compute corners
		int x1 = (int)lonIndexD;
		int x2 = (int)lonIndexD+1;
		int y1 = (int)latIndexD;
		int y2 = (int)latIndexD+1;
		//  Get Elevations for corners
		seek(x1, NUM_ROWS_FILE - y1);
		short z11 = readShort();
	    short z21 = readShort();
	    seek(x1, NUM_ROWS_FILE - y2);
	    short z12 = readShort();
	    short z22 = readShort();
		corners[0] = new Vector3d(lon0 + (double)x1/(NUM_COLS_FILE), lat0 + (double)y1/(NUM_ROWS_FILE), z11);
		corners[1] = new Vector3d(lon0 + (double)x1/(NUM_COLS_FILE), lat0 + (double)y2/(NUM_ROWS_FILE), z12);
		corners[2] = new Vector3d(lon0 + (double)x2/(NUM_COLS_FILE), lat0 + (double)y1/(NUM_ROWS_FILE), z21);
		corners[3] = new Vector3d(lon0 + (double)x2/(NUM_COLS_FILE), lat0 + (double)y2/(NUM_ROWS_FILE), z22);
//		System.err.println(corners[0]);
//		System.err.println(corners[1]);
//		System.err.println(corners[2]);
//		System.err.println(corners[3]);
		
		return corners;
	}

	private void seek(int x, int y) throws IOException {
		long pos = y * NUM_COLS_FILE * BYTES_PER_PIXEL + x*BYTES_PER_PIXEL;
		file.seek(pos);
	}
	
	//  read el at pixel index x,y
	private short readElevation(int x, int y) throws IOException {
		seek(x, y);
		short elev = file.readShort();
		//System.err.println("x, y, pos = " + x + " " + y + " " + pos + " " + elev);

		return elev;
	}
	
	private void readSpeedTest() throws IOException {
		for(int i=0;i<100000;i++){
			long pos = (long)(Math.random()*3600.0*BYTES_PER_PIXEL);
			file.seek(pos);
			short elev = readShort();
		}
	}
	
	private void speedTest(int numPts) throws IOException {
		SRTMUtil util = new SRTMUtil();
		double lat = 35.2321, lon = -115.11111;
		long t1 = System.currentTimeMillis();
		util.openFile(lat, lon);
		BilinearInterpolation bi = new BilinearInterpolation();
		Vector3d[] corners;
		for(int i=0; i<numPts; i++){
			lat = 35.0 + Math.random();
			lon = -116.0 + Math.random();
			corners = util.getCorners(lat, lon);
			bi.setCorners(corners[0], corners[1], corners[2], corners[3]);
			bi.interpolate(lon, lat);
		}
		long t2 = System.currentTimeMillis();
		System.err.println("Took " + (t2 -t1) + " millis");
	}
	
	//This needs to be modded for Eastern/Southern hemis
	public String openFile(double lat, double lon) throws IOException {
		lat0 = (int)lat;
		lon0 = (int)lon - 1.0;
		String filename = "N" + toTwoChar((int)lat0) + "W" + toThreeChar(Math.abs((int)lon0)) + ".hgt";
		//System.out.println("Using file " + filename);
		
		// reuse or open file
		if (this.filename == null || this.filename.equals(filename)) {
		    if (file != null)
		        file.close();
		    try
            {
		        this.filename = filename;
		        openFile(dataRoot + filename);
            }
		    catch (FileNotFoundException e)
	        {            
	            System.err.println(e.getMessage());
	            file = null;
	            return null;
	        }
		}
		
		return dataRoot + filename;
	}

	protected short readShort() throws IOException {
		byte b1 = file.readByte();
		byte b2 = file.readByte();
		//short retVal =  (short)(((b1 << 8)&& f0) | b2);
		int retVal = ((b1<<8) & 0x0000ff00) | (b2 & 0x000000ff);
		//if(retVal < 0)
		//	System.err.println("b1, b2, rv = " + b1 + "  " + b2 + "  " + retVal);
		return (short)retVal;
	}

	protected String toTwoChar(int i){
		if(i < 0 || i > 90)
			throw new IllegalArgumentException("wrong");
		if(i<10)
			return "0" + i;
		else
			return "" + i;
	}

	protected String toThreeChar(int i){
		if(i<10)
			return "00" + i;
		else if(i<100)
			return "0" + i;
		else
			return "" + i;
	}

	public static void main(String [] args) throws IOException {
		
		SRTMUtil util = new SRTMUtil();
//		util.speedTest(10000);
		double lat = 35.0, lon = -115.0;
		long t1 = System.currentTimeMillis();
		double result = util.getInterpolatedElevation(lat, lon);
//		Vector3d[] corners = util.getCorners(lat, lon);
//		BilinearInterpolation bi = new BilinearInterpolation();
//		bi.setCorners(corners[0], corners[1], corners[2], corners[3]);
//		double result = bi.interpolate(lon, lat);
		System.err.println("Rseult = " + result);
		long t2 = System.currentTimeMillis();
		System.err.println("Took " + (t2 -t1) + " millis");
		//double result = util.getInterpolatedElevation(lat, lon);
	}
}

