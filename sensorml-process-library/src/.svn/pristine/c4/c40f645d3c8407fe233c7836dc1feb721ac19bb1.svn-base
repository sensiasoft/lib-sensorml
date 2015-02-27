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
    Christopher Dillard <csdillard@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vast.cdm.common.DataComponent;
import org.vast.physics.Datum;
import org.vast.process.DataProcess;
import org.vast.process.ProcessException;

/**
 * <p>
 * <b>Title:</b><br/>
 * Ray Intersect Ellipsoid Process
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Computes intersection of a 3D ray with an ellipsoid which axes are aligned
 * with the axes of the referential of the ray. This process outputs coordinates
 * of the intersection point expressed in the same frame.
 * </p>
 * <p>
 * This version allows Height Above Ellipsoid adjustment
 * </p>
 * <p>
 * This version is nearly identical to the RayIntersectEllipsoid2_Process,
 * except that the heightAboveEllipsoid value is an <em>input</em> rather than
 * a <em>parameter</em>, which means that it can change between calls to
 * {@link #execute()}.
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @author Alexandre Robin
 * @date Apr 8, 2010
 * @version 1.0
 */
public class RayIntersectEllipsoid3_Process extends DataProcess {
	private Log log;

	/**
	 * Input DataComponents.  The DataComponent objects are acquired only once
	 * during init(), but the values are re-read each time execute() is invoked.
	 */
	protected DataComponent xInput, yInput, zInput, dxInput, dyInput, dzInput,
			heightAboveEllipsoidInput;

	/**
	 * Output DataComponents.  The DataComponent objects are acquired only once
	 * during init(), but the values are re-written each time execute() is
	 * invoked.
	 */
	protected DataComponent xOutput, yOutput, zOutput;

	/**
	 * X, Y, and Z radii of the datum that is used for the ellipsoidal earth
	 * model.  These values are read only once during init().  If omitted from
	 * the process parameters, they default to the values for the WGS84 datum.
	 */
	protected double datumXRadius, datumYRadius, datumZRadius;

	public RayIntersectEllipsoid3_Process() {
		log = LogFactory.getLog(getClass());
	}

	public void init() throws ProcessException {
		if (log.isTraceEnabled()) { log.trace("init()"); }

		// Get inputs.
		DataComponent rayOriginData = DataUtils.findInput(this, "rayOrigin");
		xInput = DataUtils.getChild(rayOriginData, "x");
		yInput = DataUtils.getChild(rayOriginData, "y");
		zInput = DataUtils.getChild(rayOriginData, "z");

		DataComponent rayDirectionData = DataUtils.findInput(this, "rayDirection");
		dxInput = DataUtils.getChild(rayDirectionData, "x");
		dyInput = DataUtils.getChild(rayDirectionData, "y");
		dzInput = DataUtils.getChild(rayDirectionData, "z");

		heightAboveEllipsoidInput = DataUtils.findInput(this, "heightAboveEllipsoid");

		// Get outputs.
		DataComponent intersectionData = DataUtils.findOutput(this, "intersection");
		xOutput = DataUtils.getChild(intersectionData, "x");
		yOutput = DataUtils.getChild(intersectionData, "y");
		zOutput = DataUtils.getChild(intersectionData, "z");

		// Get parameters.  (In this case, it's datum info.)
		DataComponent paramList = getParameterList();
		boolean foundDatumValues = false;
		if (paramList != null) {
			DataComponent datumComponent = paramList.getComponent("datum");
			if (datumComponent != null) {
				datumXRadius = DataUtils.getDoubleValue(datumComponent, "xRadius");
				datumYRadius = DataUtils.getDoubleValue(datumComponent, "yRadius");
				datumZRadius = DataUtils.getDoubleValue(datumComponent, "zRadius");
				foundDatumValues = true;
			}
		}
		if (! foundDatumValues) {
			if (log.isTraceEnabled()) { log.trace("Using WGS84 default values for datum"); }
			Datum datum = new Datum();
			datumXRadius = datum.equatorRadius;
			datumYRadius = datum.equatorRadius;
			datumZRadius = datum.polarRadius;
		}
	}

	public void execute() throws ProcessException {
		// First, extract input values.
		double rayOriginX = DataUtils.getDoubleValue(xInput);
		double rayOriginY = DataUtils.getDoubleValue(yInput);
		double rayOriginZ = DataUtils.getDoubleValue(zInput);

		double rayDirectionX = DataUtils.getDoubleValue(dxInput);
		double rayDirectionY = DataUtils.getDoubleValue(dyInput);
		double rayDirectionZ = DataUtils.getDoubleValue(dzInput);

		double heightAboveEllipsoid = DataUtils.getDoubleValue(heightAboveEllipsoidInput);

		// Now start doing calculations...

		double adjustedXRadius = datumXRadius + heightAboveEllipsoid;
		double adjustedYRadius = datumYRadius + heightAboveEllipsoid;
		double adjustedZRadius = datumZRadius + heightAboveEllipsoid;

		// scale vectors using ellipsoid radius
		double scaledOriginX = rayOriginX / adjustedXRadius;
		double scaledOriginY = rayOriginY / adjustedYRadius;
		double scaledOriginZ = rayOriginZ / adjustedZRadius;
		double scaledDirectionX = rayDirectionX / adjustedXRadius;
		double scaledDirectionY = rayDirectionY / adjustedYRadius;
		double scaledDirectionZ = rayDirectionZ / adjustedZRadius;

		// computes polynomial coefficients (at^2 + bt + c = 0)
		double a = scaledDirectionX*scaledDirectionX +
				scaledDirectionY*scaledDirectionY +
				scaledDirectionZ*scaledDirectionZ;
		double b = 2*(scaledDirectionX*scaledOriginX +
				scaledDirectionY*scaledOriginY +
				scaledDirectionZ*scaledOriginZ);
		double c = scaledOriginX * scaledOriginX +
				scaledOriginY * scaledOriginY +
				scaledOriginZ * scaledOriginZ - 1.0;

		double dscrm = b*b - 4*a*c;
		double scalar;

		if (dscrm < 0.0) {
			// If dscrm < 0, then the ray doesn't hit the ellipsoid.
			if (log.isTraceEnabled()) {
				log.trace("Ray does not intersect the ellipsoid.  Setting solution to nearest point to ellipsoid.");
			}
			scalar = -b / (2*a);
		}
		else {
			// There are (usually) two solutions, so we need to pick the nearer
			// of the two.
			double sqrt = Math.sqrt(dscrm);
			double s1 = (-b + sqrt)/(2*a);
			double s2 = (-b - sqrt)/(2*a);
			if (Math.abs(s1) < Math.abs(s2)) {
				if (s1 < 0) {
					if (log.isTraceEnabled()) {
						log.trace("Ray intersection is behind the ray's origin point.");
					}
				}
				scalar = s1;
			}
			else {
				if (s2 < 0) {
					if (log.isTraceEnabled()) {
						log.trace("Ray intersection is behind the ray's origin point.");
					}
				}
				scalar = s2;
			}
		}

		// Assign new values to intersection point output.
		// Use the utility methods so that meaningful exception messages are
		// produced if there are problems.
		DataUtils.setDoubleValue(xOutput, rayOriginX + rayDirectionX*scalar);
		DataUtils.setDoubleValue(yOutput, rayOriginY + rayDirectionY*scalar);
		DataUtils.setDoubleValue(zOutput, rayOriginZ + rayDirectionZ*scalar);
	}
}
