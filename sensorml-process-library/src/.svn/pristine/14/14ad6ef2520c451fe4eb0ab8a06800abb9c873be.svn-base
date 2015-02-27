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
 Kevin Carter <kcarter@nsstc.uah.edu> 
 Alexandre Robin <robin@nsstc.uah.edu>

 ******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.data.*;
import org.vast.process.*;


/**
 * <p><b>Title:</b><br/>
 * ECIVeltoECEF_Process
 * </p>
 * 
 * <p><b>Description:</b><br/>
 * Converts an ECI velocity vector to ECEF coordinates
 * </p>
 * 
 * <p>Copywrite (c) 2007</p>
 * @author Kevin Carter
 * @date March 2, 2006
 * @version 1.0
 */
public class ECIVeltoECEF_Process extends DataProcess
{
    DataValue eciX, eciY, eciZ, julianTime;
    DataValue ecfX, ecfY, ecfZ;


    public ECIVeltoECEF_Process()
    {
    }


    /**
     * Initializes the process
     * Gets handles to input/output components
     */
    public void init() throws ProcessException
    {
        try
        {
            // Inputs Mappings
            julianTime = (DataValue) inputData.getComponent("julianTime");
            DataGroup eciVector = (DataGroup)inputData.getComponent("ECI_velocity");
            eciX = (DataValue) eciVector.getComponent("x");
            eciY = (DataValue) eciVector.getComponent("y");
            eciZ = (DataValue) eciVector.getComponent("z");        
            
            // Outputs Mappings
            DataGroup ecefVector = (DataGroup)outputData.getComponent("ECEF_velocity");
            ecfX = (DataValue) ecefVector.getComponent("x");
            ecfY = (DataValue) ecefVector.getComponent("y");
            ecfZ = (DataValue) ecefVector.getComponent("z");
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }


    /**
     * Executes process algorithm on inputs and set output data
     */
    public void execute() throws ProcessException
    {
        double Xi = eciX.getData().getDoubleValue();
        double Yi = eciY.getData().getDoubleValue();
        double Zi = eciZ.getData().getDoubleValue();
        
        double c, s;
        double GHA;

        //Compute GHAD
        /* System generated locals */
        double d__1, d__2, d__3;

        /* Local variables */
        double tsec, tday, gmst, t, omega, tfrac, pi, tu, dat, rtd;

        /*     INPUT IS TIME "secondsSince1970" IN SECONDS AND "TDAY" */
        /*     WHICH IS WHOLE DAYS FROM 1970 JAN 1 0H */
        /*     THE OUTPUT IS GREENWICH HOUR ANGLE IN DEGREES */
        /*     XOMEGA IS ROTATION RATE IN DEGREES/SEC */
        pi = 3.141592653589793238;
        rtd = 180. / pi;

        /*     FOR COMPATABILITY */
        tday = (double) ((int) (julianTime.getData().getDoubleValue() / 86400.));
        tsec = (int) (julianTime.getData().getDoubleValue()) % 86400;

        /*     THE NUMBER OF DAYS FROM THE J2000 EPOCH */
        /*     TO 1970 JAN 1 0H UT1 IS -10957.5 */
        t = tday - (float) 10957.5;
        tfrac = tsec / 86400.;
        dat = t;
        tu = dat / 36525.;

        /* Computing 2nd power */
        d__1 = tu;

        /* Computing 3rd power */
        d__2 = tu;
        d__3 = d__2;
        gmst = tu * 8640184.812866 + 24110.54841 + d__1 * d__1 * .093104 - d__3 * (d__2 * d__2) * 6.2e-6;

        /*     COMPUTE THE EARTH'S ROTATION RATE */
        /* Computing 2nd power */
        d__1 = tu;
        omega = tu * 5.098097e-6 + 86636.55536790872 - d__1 * d__1 * 5.09e-10;

        /*     COMPUTE THE GMST AND GHA */
        //  da is earth nutation - currently unused
        double da = 0.0;
        gmst = gmst + omega * tfrac + da * rtd * 86400. / 360.;
        gmst = gmst % 86400;
        if (gmst < 0.)
            gmst += 86400.;
        gmst = gmst / 86400. * 360.;
        //ghan = gmst;
        //  returns gha in radians
        gmst = gmst * Math.PI / 180.0;
        GHA = gmst;

        //RotateZ
        c = Math.cos(GHA);
        s = Math.sin(GHA);
        double X = c * Xi + s * Yi;
        double Y = -s * Xi + c * Yi;
        double Z = Zi;
        
        // also account for angular velocity of the earth
        // compute velocity cross earthOmega [0,0,0,7.292e-5]
        double dX = Y * 7.2921158553e-5; // - Z * 0.0;
        double dY = - X * 7.2921158553e-5; // + Z * 0.0; 
        double dZ = 0.0; // X * 0.0 - Y * 0.0; 
        X -= dX; Y -= dY; Z -= dZ;

        //Set outputs
        ecfX.getData().setDoubleValue(X);
        ecfY.getData().setDoubleValue(Y);
        ecfZ.getData().setDoubleValue(Z);
    }
}