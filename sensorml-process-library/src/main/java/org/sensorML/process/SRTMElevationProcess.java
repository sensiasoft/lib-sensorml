
//change to your package location
package org.sensorML.process;

import java.io.IOException;
import net.opengis.swe.v20.DataRecord;
import org.vast.data.DataValue;
import org.vast.process.ProcessException;
import org.vast.sensorML.AbstractProcessImpl;


public class SRTMElevationProcess extends AbstractProcessImpl {


	// declare input components
	DataValue CoverageIn_lon;
	DataValue CoverageIn_lat;

	// declare output components
	DataValue CoverageOut_lon;
	DataValue CoverageOut_lat;
	DataValue CoverageOut_alt;

	// declare parameters components

	// declare any other class variables needed


	public SRTMElevationProcess() {
	}


	/**
	 * Initializes the process
	 * Get handles to input/output components
	 */
	public void init() throws ProcessException {

		try {

			// initialize input components
			DataRecord CoverageIn = (DataRecord) inputData.getComponent("CoverageIn");
			CoverageIn_lon = (DataValue) CoverageIn.getComponent("lon");
			CoverageIn_lat = (DataValue) CoverageIn.getComponent("lat");

			// initialize output components
			DataRecord CoverageOut = (DataRecord) outputData.getComponent("CoverageOut");
			CoverageOut_lon = (DataValue) CoverageOut.getComponent("lon");
			CoverageOut_lat = (DataValue) CoverageOut.getComponent("lat");
			CoverageOut_alt = (DataValue) CoverageOut.getComponent("alt");

			// initialize parameter components

		}
		catch (ClassCastException e) {
			throw new ProcessException("Invalid I/O data", e);
		}

		// initialize any class variables needed

	}

	/**
	 * Executes the process
	 * Get current values for all components and then executes
	 */
	public void execute() throws ProcessException {

		// get values for input components
		double lon_in = CoverageIn_lon.getData().getDoubleValue();
		double lat_in = CoverageIn_lat.getData().getDoubleValue();

		// re-initialize values for output components to zero or default
		// note: you can rename these variable names to match your code
		//  don't need to do this for primitives, do we?
//		double CoverageOut_lon_value = CoverageOut_lon.getData().getDoubleValue();
//		CoverageOut_lon_value = 0;
//		double CoverageOut_lat_value = CoverageOut_lat.getData().getDoubleValue();
//		CoverageOut_lat_value = 0;
//		double CoverageOut_alt_value = CoverageOut_alt.getData().getDoubleValue();
//		CoverageOut_alt_value = 0;


		/*****************************************
		 *    SRTM lookup and interpolation      *
		 *****************************************/
		SRTMUtil util = new SRTMUtil();
		double elevOut;
		try {
			elevOut = util.getInterpolatedElevation(lat_in, lon_in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProcessException(e.getMessage(), e);
		}

		// set values for output components
		CoverageOut_lon.getData().setDoubleValue(lon_in);
		CoverageOut_lat.getData().setDoubleValue(lat_in);
		CoverageOut_alt.getData().setDoubleValue(elevOut);
	}
}
