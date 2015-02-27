/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * AIRS Preprocessing Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This process preprocesses the airs data and format them into a ADAS input file.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date May 20, 2008
 * @version 1.0
 */
public class AirsPreprocessing_Process extends DataProcess
{
	int  scanLineNumber;
	AbstractDataComponent statusData, scanLineData, AirsData;    
	DataBlock AirsDataBlock;
	double startTime, stopTime;
	double[][] latitude, longitude;
	float[][] landFrac, topog, time, PSurfStd, PBest;
	float[][][] TAirSup, H2OCDSup;
	Calendar cal;
	String filepathLand, filepathWater;
	String dirPrefix = "C:/Data/Airs/ADAS/";
	//String dirPrefix = "/data/publicweb/Smart/";
	
    @Override
    public void init() throws ProcessException
    {
        try
        {
        	String mapUrl4 = AirsPreprocessing_Process.class.getResource("./srtmProps.txt").toString();
        	String part1 = mapUrl4.substring(0, mapUrl4.indexOf("/org/"));
        	String part2 = part1.substring(0, part1.lastIndexOf("/"));
        	String filepath = part2.substring(6) + "/directorySetting.txt";
        	File settingFile = new File(filepath);
        	if(!settingFile.exists())
        	{
        		filepath = part2.substring(0, part2.lastIndexOf("/"));
        		filepath = filepath.substring(6) + "/directorySetting.txt";
        	}
        	BufferedReader reader = new BufferedReader(new FileReader(filepath));
        	String line = reader.readLine();
        	dirPrefix = line.substring(line.indexOf("=")+1);
        	
    		AirsData = inputData.getComponent(0);
        	statusData = outputData.getComponent("airsPreprocessingStatus");        	
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
        
    }
    
    @Override
    public void execute() throws ProcessException
    {
    	
    	cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    	long currentTime = System.currentTimeMillis();
    	cal.setTimeInMillis(currentTime);
    	String yearD = Integer.toString(cal.get(Calendar.YEAR));     	
    	String monthD = Integer.toString(cal.get(Calendar.MONTH)+1);
    	String dayD = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
    	String dayOfYearD = Integer.toString(cal.get(Calendar.DAY_OF_YEAR));
    	
    	if(dayD.length()==1)
    		dayD = "0" + dayD;
    	if(monthD.length()==1)
    		monthD = "0" + monthD;
    	if(dayOfYearD.length()==2)
    		dayOfYearD = "0" + dayOfYearD;

    	String destinationDirectoryDayPath =  dirPrefix + yearD + "/" + monthD + "/" + dayD + "/";
		File dirDay = new File(destinationDirectoryDayPath);
		
    	if(!dirDay.exists()){
    		dirDay.mkdirs();
		}
    	
    	int m = 3;

    	DataBlock AirsDataBlock = AirsData.getData(); 
    	startTime = AirsDataBlock.getDoubleValue(0);
    	stopTime = AirsDataBlock.getDoubleValue(1);
    	scanLineNumber = (int)AirsDataBlock.getDoubleValue(2);
    	
    	double[][] latitude = new double[30][scanLineNumber], longitude = new double[30][scanLineNumber];
    	float[][] landFrac = new float[30][scanLineNumber], topog = new float[30][scanLineNumber];
    	float[][] time = new float[30][scanLineNumber], PSurfStd = new float[30][scanLineNumber], PBest = new float[30][scanLineNumber];
    	float[][][] TAirSup = new float[100][30][scanLineNumber], H2OCDSup = new float[100][30][scanLineNumber];
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			latitude[i][j] = AirsDataBlock.getDoubleValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			longitude[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			time[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			topog[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			landFrac[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			PSurfStd[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			PBest[i][j] = AirsDataBlock.getFloatValue(m);
    			m++;
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			for(int k=0; k<100; k++){
    				TAirSup[k][i][j] = AirsDataBlock.getFloatValue(m);
    				m++;
    			}
    		}
    	}
    	
    	for(int i=0; i<30; i++){
    		for(int j=0; j<scanLineNumber; j++){
    			for(int k=0; k<100; k++){
    				H2OCDSup[k][i][j] = AirsDataBlock.getFloatValue(m);
    				m++;
    			}
    		}
    	}
    
    	cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
    	cal.setTimeInMillis((long)((stopTime+startTime)*500.00));
    	
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);

		if(minute<31){
			minute = 0;
		}
		else if(minute>30){
			minute = 0;
			hour = hour + 1;
		}
		
		String prefix = "0", monthS, dayS, hourS, minuteS;
		
		if(month<10){
			monthS = prefix + Integer.toString(month);
		}
		else monthS = Integer.toString(month);
		
		if(day<10){
			dayS = prefix + Integer.toString(day);
		}
		else dayS = Integer.toString(day);

		if(hour<10){
			hourS = prefix + Integer.toString(hour);
		}
		else hourS = Integer.toString(hour);
		
		if(minute<10){
			minuteS = prefix + Integer.toString(minute);
		}
		else minuteS = Integer.toString(minute);
		
		if(scanLineNumber!=0)
			processAndWriteData(latitude, longitude, landFrac, PSurfStd, topog, PBest, TAirSup, H2OCDSup, year, monthS, dayS,
    							hourS, minuteS);
    	
    	/*try {
			Runtime.getRuntime().exec("chmod 664 " + filepathWater); 
			Runtime.getRuntime().exec("chmod 664 " + filepathLand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
    	if(scanLineNumber==0)
    		statusData.getData().setStringValue("failed");
    	else
    		statusData.getData().setStringValue("completed");
    	
    	//airsDataBlockNull = new DataBlock();
    	AirsData.clearData();
     } 	 


	public void processAndWriteData(double[][] latitude, double[][] longitude, float[][] landfrac,
			float[][] psurf, float[][] elev, float[][] plevmax, float[][][] tprofK, float[][][] wcd, int year, String monthS, String dayS,
			String hourS, String minuteS){
		
		 		
		PrintWriter printLand = null;
		String filename = Integer.toString(year) + monthS + dayS + "_" + stopTime + "_" + startTime;
		File dir = new File(dirPrefix + Integer.toString(year) + "/" + monthS + "/" + dayS + "/");
		if(!dir.exists())
			dir.mkdirs();
		filepathWater = dirPrefix + Integer.toString(year) + "/" + monthS + "/" + dayS + "/" + filename + ".arw";
		File land = new File(filepathWater);
		
		try 
		{
			land.createNewFile();
			printLand = new PrintWriter(land);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		PrintWriter printWater = null;		
		filepathLand = dirPrefix + Integer.toString(year) + "/" + monthS + "/" + dayS + "/" + filename + ".arl";
		File water = new File(filepathLand);
		
		try 
		{
			water.createNewFile();
			printWater = new PrintWriter(water);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
/////////////////  inputs  originally read from files  //////////////////////

//   double[][] latitude = new double[30][45];
//   double[][] longitude = new double[30][45];
//   float[][] landfrac = new float[30][45];
//   float[][] psurf = new float[30][45];
//   float[][] elev = new float[30][45];
//   int[][] plevmax = new int[30][45];
//   float[][] tprofK = new float[100][30][45];
//   float[][][] wcd = new float[100][30][45];
//   int hour = 0;
//   int minute = 0;

/////////////////////////////////////////////////////////////////////////////

int numberOfScanLines = latitude[1].length;		
		System.out.println("numberOfScanLines:  " + numberOfScanLines);
int[][] counter = new int[30][numberOfScanLines];
double[][][] tprof = new double[100][30][numberOfScanLines];
double[][][] LCDd = new double[100][30][numberOfScanLines];
double[][][] LCDt = new double[100][30][numberOfScanLines];
double[][][] mixr = new double[100][30][numberOfScanLines];
double[][][] mixr_kgkg = new double[100][30][numberOfScanLines];
double[][][] vap_pres = new double[100][30][numberOfScanLines];
double[][][] tdprof = new double[100][30][numberOfScanLines];
double[][][] hght = new double[100][30][numberOfScanLines];

double nobs_w=0;
double nobs_l=0;

double [] pobs = {0.01610000059, 0.03840000182, 0.07689999789, 0.136999995, 0.224399999, 
		0.345400006, 0.506399989, 0.713999987, 0.975300014, 1.29719996, 1.68719995, 
		2.1526000, 2.70090008, 3.33979988, 4.07700014, 4.92040014, 5.87760019, 6.95669985, 
		8.16549969, 9.51189995, 11.0038004, 12.6492004, 14.4559002, 16.4318008, 18.5846996, 
		20.9223995, 23.4526005, 26.1828995, 29.1210003, 32.2743988, 35.6505013, 39.2565994, 
		43.1001015, 47.1882019, 51.5278015, 56.1259995, 60.9894981, 66.1252975, 71.5398026, 
		77.2396011, 83.2310028, 89.520401, 96.1138, 103.017197, 110.236603, 117.777496, 
		125.645599, 133.846207, 142.384796, 151.266403, 160.495895, 170.0784, 180.018295, 
		190.320297, 200.988693, 212.027695, 223.441498, 235.233795, 247.408493, 259.969086, 
		272.919098, 286.261688, 300., 314.136902, 328.675293, 343.617615, 358.966492, 
		374.724091, 390.892609, 407.473785, 424.469788, 441.881897, 459.711792, 477.960693, 
		496.629791, 515.719971, 535.232178, 555.16687, 575.52478, 596.306213, 617.51123, 
		639.139771, 661.192017, 683.667297, 706.56543, 729.885681, 753.627502, 777.789673, 
		802.371399, 827.371277, 852.788025, 878.620117, 904.865906, 931.523621, 958.591125, 
		986.066589, 1013.94757, 1042.2319, 1070.91699, 1100};


    // printf,90,hour,minute

double avogandro = 6.02214199E+23;
double go = 980.665;
double mmd = 28.9644;
double mmw = 18.0151;
int l = 0;
double mean_tv = 0, thkns = 0, sum_hght = 0;
 // Now we want to write out the HDF files into one-dimensional arrays

//	         printf,20,hour,minute

for(int i=0; i<30; i++){
   for(int j=0; j<numberOfScanLines; j++){

//             printf,70,latitude(i,j),longitude(i,j),plevmax(i,j)

       for(int k=1; k<100; k++){ 
    	   
    	   l=k-1;
    	   if(tprofK[k][i][j]!=-9999){
    		   tprof[k][i][j] = tprofK[k][i][j] - 273.15;
    	   }
    	   if(tprofK[k][i][j]==-9999){
    		   tprof[k][i][j] = -999;
    	   }   

    	   if(wcd[k][i][j]!=-9999){

//		First calculate the total air layer column density (LCD)
    		   LCDt[k][i][j] = 1000.0 * (((pobs[k] - pobs[l]) * avogandro) / (double)(mmd * go));

//		Subtract the LCD of water (from the L2 profiles) to get dry air LCD
    		   LCDd[k][i][j] = LCDt[k][i][j] - wcd[k][i][j];

//		Indirectly use Avogandro's number and molar mass (mm) of the water and dry air consituents to find mixing ratio (g/kg)
    		   mixr[k][i][j] = (mmw * wcd[k][i][j]) / (0.001 * mmd * LCDd[k][i][j]);
    		   mixr_kgkg[k][i][j] = mixr[k][i][j] * 0.001;
    		   vap_pres[k][i][j] = (mixr_kgkg[k][i][j] * (pobs[k] + pobs[l]) * 0.5) / (mixr_kgkg[k][i][j] + (mmw / mmd)); 
    		   tdprof[k][i][j]=(243.5 * (Math.log(vap_pres[k][i][j])) - 440.8) / (19.48 - (Math.log(vap_pres[k][i][j])));

    	   }
    	   
    	   if(wcd[k][i][j]==-9999){
    		   tdprof[k][i][j]=-999;
    	   }
       }
   }
}

for(int i=0; i<30; i++){
 for(int j=0; j<numberOfScanLines; j++){
	 
   sum_hght = elev[i][j];
  // System.out.println(sum_hght);
   for(int k=98; k>41; k--){
	   
      l=k-1;
      
      if((psurf[i][j] < pobs[k]) && (psurf[i][j] < pobs[l])){           	  
//		indicates that observation level is below ground
         hght[k][i][j]=-999.0;   
      }
      
      if((psurf[i][j] < pobs[k]) && (psurf[i][j] > pobs[l])){ 
    	  hght[k][i][j] = -999.0; 
          mean_tv = calculateVirtualTemperature(tprof[l][i][j],tdprof[l][i][j],pobs[l]);
          thkns = calculateThickness(pobs[l],psurf[i][j],mean_tv);
          
//              print,'surf',i,j,k,l,pobs(l),psurf(i,j),thkns
         hght[l][i][j]=sum_hght + thkns;
         sum_hght = hght[l][i][j];
      }
      
      
      if ((psurf[i][j] > pobs[k]) && (psurf[i][j] > pobs[l])){
    	  
         mean_tv = (calculateVirtualTemperature(tprof[k][i][j],tdprof[k][i][j],pobs[k])+
        		 calculateVirtualTemperature(tprof[l][i][j],tdprof[k][i][j],pobs[l]))*0.5;
         
         thkns = calculateThickness(pobs[l],pobs[k],mean_tv);
//              print,'non-surf',i,j,k,l,pobs(l),psurf(i,j),thkns
         
         hght[l][i][j] = sum_hght + thkns;
         if(hght[l][i][j]==0 && (sum_hght>0 || thkns>0)){
        	 if(sum_hght>0)
        		 hght[l][i][j]= sum_hght;
        	 if(sum_hght>0)
        		 hght[l][i][j]= thkns;
         }
         sum_hght = hght[l][i][j];
      }

       if (hght[k][i][j] != -999.){
          counter[i][j] = counter[i][j] + 1;
       }
   }
 }
}

String codeLand, codeWater;
int n = 0, m = 0;

for(int i=0; i<30; i++){
 for(int j=0; j<numberOfScanLines; j++){
       if(plevmax[i][j] >= 100.0){
    	   if(landfrac[i][j] == 0.0){
    		   
    		   nobs_w = nobs_w + 1;
    		   
    		   codeLand = "A000"+ Integer.toString(n);
    		   if(n>9){
    			   codeLand = "A00"+ Integer.toString(n);
    		   }
    		   if(n>99){
    			   codeLand = "A0"+ Integer.toString(n);
    		   }
    		   if(n>999){
    			   codeLand = "A"+ Integer.toString(n);
    		   }
    		      		   
    		   StringBuilder sbL1 = new StringBuilder();
     		   // Send all output to the Appendable object sb    write(15,'(6f15.4)')
     		    Formatter formatterL1 = new Formatter(sbL1, Locale.US);
     		    formatterL1.format(Locale.US, "%12d", n);
     		    formatterL1.format(Locale.US, "%12d", 54); 
     		  //  in the file it is actually lat then lon but it is inverse in the SOS
     		  // so lon holds the lat and inversely
     		  // to be changed after the demo
     		    formatterL1.format(Locale.US, "%11.4f", longitude[i][j]);
     		    formatterL1.format(Locale.US, "%15.4f", latitude[i][j]);    		    
     		    formatterL1.format(Locale.US, "%15.0f", elev[i][j]);
     		    String elementL1 = formatterL1.toString();
    		   printLand.println(elementL1 + "          " + codeLand);
    		   n++;
    		   
       		   for(int k=96; k>42; k--){
       		   	   if (pobs[k] >= plevmax[i][j]){
       		   	   		hght[k][i][j]=-999;
       		   	   		tprof[k][i][j]=-999;
       		   	   		tdprof[k][i][j]=-999;
       		       }
       		   	   
       		   	StringBuilder sbL2 = new StringBuilder();
     		   // Send all output to the Appendable object sb    write(15,'(6f15.4)')
     		    Formatter formatterL2 = new Formatter(sbL2, Locale.US);
     		  //  System.out.println(hght[k][i][j]);
     		    formatterL2.format(Locale.US, "%15.4f", hght[k][i][j]);
     		    formatterL2.format(Locale.US, "%15.4f", pobs[k]);
     		    formatterL2.format(Locale.US, "%15.4f", tprof[k][i][j]);
     		    formatterL2.format(Locale.US, "%15.4f", tdprof[k][i][j]);
     		    String elementL2 = formatterL2.toString();
     		   
       		   	printLand.println(elementL2 + "      -999.0000      -999.0000");
   		   
   //    		   	   		printf,20,pobs(k),hght(k,i,j),tprof(k,i,j),tdprof(k,i,j)
       		   }

    	   }
    	   if(landfrac[i][j] != 0.0){
    		   nobs_l = nobs_l + 1;

    		   codeWater = "A000"+ Integer.toString(m);
    		   if(m>9){
    			   codeWater = "A00"+ Integer.toString(m);
    		   }
    		   if(m>99){
    			   codeWater = "A0"+ Integer.toString(m);
    		   }
    		   if(m>999){
    			   codeWater = "A"+ Integer.toString(m);
    		   }
    		   
    		   StringBuilder sbW1 = new StringBuilder();
     		   // Send all output to the Appendable object sb    write(15,'(6f15.4)')
     		    Formatter formatterW1 = new Formatter(sbW1, Locale.US);
     		    formatterW1.format(Locale.US, "%12d", m);
     		    formatterW1.format(Locale.US, "%12d", 54);
     		    formatterW1.format(Locale.US, "%11.4f", longitude[i][j]);
    		    formatterW1.format(Locale.US, "%15.4f", latitude[i][j]);
     		    formatterW1.format(Locale.US, "%15.0f", elev[i][j]);
     		    String elementW1 = formatterW1.toString();
    		   printWater.println(elementW1 + "          " + codeWater);
    		   m++;
    		   
//              printf,25,'54',latitude(i,j),longitude(i,j),elev(i,j)

//              for k=43,loop_end do begin
    		   for(int k=96; k>42; k--){
    			   if (pobs[k] >= plevmax[i][j]){
    				   hght[k][i][j]=-999;
    				   tprof[k][i][j]=-999;
    				   tdprof[k][i][j]=-999;
    			   }
    			   
    			   StringBuilder sbW2 = new StringBuilder();
         		   // Send all output to the Appendable object sb    write(15,'(6f15.4)')
         		    Formatter formatterW2 = new Formatter(sbW2, Locale.US);
         		    formatterW2.format(Locale.US, "%15.4f", hght[k][i][j]);
         		    formatterW2.format(Locale.US, "%15.4f", pobs[k]);
         		    formatterW2.format(Locale.US, "%15.4f", tprof[k][i][j]);
         		    formatterW2.format(Locale.US, "%15.4f", tdprof[k][i][j]);
         		    String elementW2 = formatterW2.toString();
         		   
           		   	printWater.println(elementW2 + "      -999.0000      -999.0000");
    			   
//          printf,25,pobs(k),hght(k,i,j),tprof(k,i,j),tdprof(k,i,j)
    		   }
    	   }
       }
 }


}

printWater.flush();
printLand.flush();

		}
	
    protected static double calculateThickness(double pt, double pressure, double virtualTemperature) {  	
    	
  /** this function to calculate thickness for converting layer-average liquid water content to 
   *  dew point */ 	
    	
    	double thickness; 	
    	if(pressure<pt){
    		thickness = -1;
    	} else{
            thickness=29.3*(virtualTemperature+273.15)*Math.log(pressure/pt);
    	}
    	return thickness;
    }
    
    
    protected static double calculateSaturationVaporPressure(double temperature) {  
    	
  /** this function returns the saturation vapor pressure saturationVaporPressure 
   *  (millibars) over liquid water given the temperature t (celsius). the polynomial
   *  approximation below is due to herman wobus, a mathematician who
   *  worked at the navy weather research facility, norfolk, virginia,
   *  but who is now retired. the coefficients of the polynomial were
   *  chosen to fit the values in table 94 on pp. 351-353 of the smith-
   *  sonian meteorological tables by roland list (6th edition). the
   *  approximation is valid for -50 < t < 100c. */

   //   es0 = saturation vapor pressure over liquid water at 0c
    	double es0 = 6.1078;
    	double  pol = 0.99999683 + temperature*(-0.90826951e-02 +
    			temperature*(0.78736169e-04   + temperature*(-0.61117958e-06 +
    					temperature*(0.43884187e-08   + temperature*(-0.29883885e-10 +
    							temperature*(0.21874425e-12   + temperature*(-0.17892321e-14 +
    									temperature*(0.11112018e-16   + temperature*(-0.30994571e-19)))))))));
    	double saturationVaporPressure = es0/(Math.pow(pol,8));
    	return saturationVaporPressure;
    }
    
    
    protected static double calculateVirtualTemperature(double temperature, double dewPoint, double pressure) {  	
    
  /** this function returns the virtual temperature (celsius) of
   *  a parcel of air at temperature(celsius), dew point td
   *  (celsius), and pressure (millibars). the equation appears
   *  in most standard meteorological texts */
    	
   //   cta = difference between kelvin and celsius temperatures.
   //   eps = ratio of the mean molecular weight of water (18.016 g/mole) to that of dry air (28.966 g/mole) 	
    	double cta = 273.15, eps = 0.62197;
        double tk = temperature + cta;

   //	calculate the dimensionless mixing ratio.

        double w = .001*calculateMixingRatio(pressure,dewPoint);
        double virtualTemperature = tk*(1.+w/eps)/(1.+w)-cta;
        return virtualTemperature;
    }
    
    
    protected static double calculateMixingRatio(double pressure, double temperature) {  	
    	
   /** this function approximates the mixing ratio (grams of water
    *  vapor per kilogram of dry air) given the pressure (mb) and the
    *  temperature (celsius). the formula used is given on p. 302 of the
    *  smithsonian meteorological tables by roland list (6th edition) */
    			
   //	eps = ratio of the mean molecular weight of water (18.016 g/mole) to that of dry air (28.966 g/mole)
    	double eps = 0.62197;

   //   the next two lines contain a formula by herman wobus for the
   //   correction factor wfw for the departure of the mixture of air
   //   and water vapor from the ideal gas law. the formula fits values
   //   in table 89, p. 340 of the smithsonian meteorological tables,
   //   but only for temperatures and pressures normally encountered in
   //   in the atmosphere.
    	double x = 0.02 * (temperature - 12.5 + 7500 / pressure);
    	double wfw = 1 + 4.5e-06 * pressure + 1.4e-03 * Math.pow(x,2);
    	double fwesw = wfw * calculateSaturationVaporPressure(temperature);
    	double r = eps * fwesw /(pressure - fwesw);

   //   convert r from a dimensionless ratio to grams/kilogram.
    	double mixingRatio = 1000 * r;
    	return mixingRatio;
    }
    
    protected static double calculateDewPoint(double temperature, double relativeHumidity) {  	
    	
  /** this function returns the dew point (celsius) given the temperature
   *  (celsius) and relative humidity (%). the formula is used in the
   *  processing of u.s. rawinsonde data and is referenced in parry, h.
   *  dean, 1969: "the semiautomatic computation of rawinsondes,"
   *  technical memorandum wbtm edl 10, u.s. department of commerce,
   *  environmental science services administration, weather bureau,
   *  office of systems development, equipment development laboratory,
   *  silver spring, md (october), page 9 and page ii-4, line 460.  */
    	
    	double x = 1 - 0.01 * relativeHumidity;
    	
   //	compute dew point depression
        double dewPoint = temperature - ((14.55+(0.114*temperature))*x) + ((2.5+(0.007*temperature))
        		          *(Math.pow(x,3))) + ((15.9+(0.117*temperature))*(Math.pow(x,14)));
    	return dewPoint;
    }
    
}
