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
    Gregoire Berthiau <berthiau@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.cdm.common.DataBlock;
import org.vast.data.*;
import org.vast.process.*;
import org.vast.math.*;

/**
 * <p><b>Title:</b><br/>
 * Matrix by Matrix Multiplication Process
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Multiplication of a 3D Matrix by another 3D matrix and a scalar,
 * such that M3 = k.M1.M2, where M1, M2, and M3 are matrices and k a scalar.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Jan 16, 2008
 * @version 1.0
 */
public class MatrixByMatrixMultiplication_Process extends DataProcess
{
	AbstractDataComponent Mat1, Mat2, resultMatrix, coeffData;
    double coeff;
	
    public void init() throws ProcessException
    {
    	try
        {
            //I/O mappings
            Mat1 = inputData.getComponent("firstMatrix");
            Mat2 = inputData.getComponent("secondMatrix");
            coeffData = inputData.getComponent("scalarCoefficient");
            resultMatrix = inputData.getComponent("resultMatrix");
        }
        catch (Exception e)
        {
            throw new ProcessException(ioError, e);
        }
    }
    

    public void execute() throws ProcessException
    {

        DataBlock MatrixData1 = Mat1.getData();
        DataBlock MatrixData2 = Mat2.getData();
        
        Matrix3d Matrix1 = new Matrix3d();
        Matrix3d Matrix2 = new Matrix3d();
        Matrix3d Matrix3 = new Matrix3d();
        
        for (int i=0; i<9; i++){
        	Matrix1.setElement(i/3, i%3, MatrixData1.getDoubleValue(i));
        	Matrix2.setElement(i/3, i%3, MatrixData2.getDoubleValue(i));
        }

        coeff = coeffData.getData().getDoubleValue();
        Matrix3.mul(Matrix1, Matrix2);
        Matrix3.mul(coeff);

        DataBlock data = resultMatrix.getData();
        for (int i=0; i<9; i++){
            data.setDoubleValue(i, Matrix3.getElement(i/3, i%3));
        }
    } 
}