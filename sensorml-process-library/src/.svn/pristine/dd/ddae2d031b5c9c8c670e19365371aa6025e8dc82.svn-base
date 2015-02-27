
//change to your package location
package org.sensorML.process;

import org.vast.data.*;
import org.vast.process.*;
import org.vast.math.*;


/**
* <p><b>Title:  </b><br/></p>
*
* <p><b>Description:</b><br/>
* </p>
* @author 
* @date  
* @version 
*/



public class CSM_FrameCamera_Colinear_Process extends DataProcess {


   // declare input components
   DataValue ground_Ya;
   DataValue ground_Xa;
   DataValue ground_Za;
   DataValue lens_Yc;
   DataValue lens_Xc;
   DataValue lens_Zc;
   DataValue attitude_pitch;
   DataValue attitude_roll;
   DataValue attitude_heading;

   // declare output components
   DataValue pixel_r;
   DataValue pixel_c;

   // declare parameters components
   DataValue focalLength;
   DataValue pixelGrid_pixelRows;
   DataValue pixelGrid_pixelColumns;
   DataValue pixelGrid_rowSpacing;
   DataValue pixelGrid_columnSpacing;
   DataValue principalPoint_x0;
   DataValue principalPoint_y0;
   DataValue affine_a1;
   DataValue affine_b1;
   DataValue affine_c1;
   DataValue affine_a2;
   DataValue affine_b2;
   DataValue affine_c2;
   DataValue radial_k1;
   DataValue radial_k2;
   DataValue radial_k3;
   DataValue decentering_p1;
   DataValue decentering_p2;


   // declare any other class variables needed


   public CSM_FrameCamera_Colinear_Process() {
   }


   /**
   * Initializes the process
   * Get handles to input/output components
   */
   public void init() throws ProcessException {

      try {

         // initialize input components
         DataGroup ground = (DataGroup) inputData.getComponent("ground");
         ground_Ya = (DataValue) ground.getComponent("Ya");
         ground_Xa = (DataValue) ground.getComponent("Xa");
         ground_Za = (DataValue) ground.getComponent("Za");
         DataGroup lens = (DataGroup) inputData.getComponent("lens");
         lens_Yc = (DataValue) lens.getComponent("Yc");
         lens_Xc = (DataValue) lens.getComponent("Xc");
         lens_Zc = (DataValue) lens.getComponent("Zc");
         DataGroup attitude = (DataGroup) inputData.getComponent("attitude");
         attitude_pitch = (DataValue) attitude.getComponent("pitch");
         attitude_roll = (DataValue) attitude.getComponent("roll");
         attitude_heading = (DataValue) attitude.getComponent("heading");

         // initialize output components
         DataGroup pixel = (DataGroup) outputData.getComponent("pixel");
         pixel_r = (DataValue) pixel.getComponent("r");
         pixel_c = (DataValue) pixel.getComponent("c");

         // initialize parameter components
         focalLength = (DataValue) paramData.getComponent("focalLength");
         DataGroup pixelGrid = (DataGroup) paramData.getComponent("pixelGrid");
         pixelGrid_pixelRows = (DataValue) pixelGrid.getComponent("pixelRows");
         pixelGrid_pixelColumns = (DataValue) pixelGrid.getComponent("pixelColumns");
         pixelGrid_rowSpacing = (DataValue) pixelGrid.getComponent("rowSpacing");
         pixelGrid_columnSpacing = (DataValue) pixelGrid.getComponent("columnSpacing");
         DataGroup principalPoint = (DataGroup) paramData.getComponent("principalPoint");
         principalPoint_x0 = (DataValue) principalPoint.getComponent("x0");
         principalPoint_y0 = (DataValue) principalPoint.getComponent("y0");
         DataGroup affine = (DataGroup) paramData.getComponent("affine");
         affine_a1 = (DataValue) affine.getComponent("a1");
         affine_b1 = (DataValue) affine.getComponent("b1");
         affine_c1 = (DataValue) affine.getComponent("c1");
         affine_a2 = (DataValue) affine.getComponent("a2");
         affine_b2 = (DataValue) affine.getComponent("b2");
         affine_c2 = (DataValue) affine.getComponent("c2");
         DataGroup radial = (DataGroup) paramData.getComponent("radial");
         radial_k1 = (DataValue) radial.getComponent("k1");
         radial_k2 = (DataValue) radial.getComponent("k2");
         radial_k3 = (DataValue) radial.getComponent("k3");
         DataGroup decentering = (DataGroup) paramData.getComponent("decentering");
         decentering_p1 = (DataValue) decentering.getComponent("p1");
         decentering_p2 = (DataValue) decentering.getComponent("p2");
 
         

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
         // note: you can rename these variable names to match your code
         double ground_Ya_value = ground_Ya.getData().getDoubleValue();
         double ground_Xa_value = ground_Xa.getData().getDoubleValue();
         double ground_Za_value = ground_Za.getData().getDoubleValue();
         double lens_Yc_value = lens_Yc.getData().getDoubleValue();
         double lens_Xc_value = lens_Xc.getData().getDoubleValue();
         double lens_Zc_value = lens_Zc.getData().getDoubleValue();
         double attitude_pitch_value = attitude_pitch.getData().getDoubleValue();
         double attitude_roll_value = attitude_roll.getData().getDoubleValue();
         double attitude_heading_value = attitude_heading.getData().getDoubleValue();

         // get values for parameter components
         // note: you can rename these variable names to match your code
         double focalLength_value = focalLength.getData().getDoubleValue();
         int pixelGrid_pixelRows_value = pixelGrid_pixelRows.getData().getIntValue();
         int pixelGrid_pixelColumns_value = pixelGrid_pixelColumns.getData().getIntValue();
         double pixelGrid_rowSpacing_value = pixelGrid_rowSpacing.getData().getDoubleValue();
         double pixelGrid_columnSpacing_value = pixelGrid_columnSpacing.getData().getDoubleValue();
         double principalPoint_x0_value = principalPoint_x0.getData().getDoubleValue();
         double principalPoint_y0_value = principalPoint_y0.getData().getDoubleValue();
         double affine_a1_value = affine_a1.getData().getDoubleValue();
         double affine_b1_value = affine_b1.getData().getDoubleValue();
         double affine_c1_value = affine_c1.getData().getDoubleValue();
         double affine_a2_value = affine_a2.getData().getDoubleValue();
         double affine_b2_value = affine_b2.getData().getDoubleValue();
         double affine_c2_value = affine_c2.getData().getDoubleValue();
         double radial_k1_value = radial_k1.getData().getDoubleValue();
         double radial_k2_value = radial_k2.getData().getDoubleValue();
         double radial_k3_value = radial_k3.getData().getDoubleValue();
         double decentering_p1_value = decentering_p1.getData().getDoubleValue();
         double decentering_p2_value = decentering_p2.getData().getDoubleValue();

         // re-initialize values for output components to zero or default
         // note: you can rename these variable names to match your code
         double pixel_r_value = pixel_r.getData().getDoubleValue();
         pixel_r_value = 0;
         double pixel_c_value = pixel_c.getData().getDoubleValue();
         pixel_c_value = 0;


         /****************************************
          *    PUT YOUR EXECUTION CODE HERE      *
          ****************************************/
         //Create the vector that includes location of the lens center with respect to a parallel Earth coordinate system
         Vector3d lensCenter = new Vector3d(lens_Xc_value,lens_Yc_value , lens_Zc_value);
         //Create the vector that translates from the location of lens coordinate system to the actual image plane coordinate system (this is where the pixels lie)
         Vector3d focalLeng = new Vector3d(0,0 ,focalLength_value);
         //Create the vector that holds the location of the object on the ground with respect to an Earth coordinate system
         Vector3d objectLoc = new Vector3d(ground_Xa_value,ground_Ya_value , ground_Za_value);
         //Create the first column of the orientation matrix M (accounts for pitch, roll, and true heading)
         Vector3d M1 = new Vector3d(Math.cos(attitude_pitch_value)*Math.cos(attitude_heading_value),
        		 (-1*Math.cos(attitude_pitch_value)*Math.sin(attitude_heading_value)),
        		 Math.sin(attitude_pitch_value));
         //Create the second column of the orientation matrix M (accounts for pitch, roll, and true heading)
         Vector3d M2 = new Vector3d((Math.cos(attitude_roll_value)*Math.sin(attitude_heading_value))+
        		 ((Math.sin(attitude_roll_value)*Math.sin(attitude_pitch_value)*Math.cos(attitude_heading_value))),
        		 ((Math.cos(attitude_roll_value)*Math.cos(attitude_heading_value))-(Math.sin(attitude_roll_value)*Math.sin(attitude_pitch_value)*
        				 Math.sin(attitude_heading_value))), (-1*Math.sin(attitude_roll_value)*Math.cos(attitude_pitch_value)));
         //Create the third column of the orientation matrix M (accounts for pitch, roll, and true heading)
         Vector3d M3 = new Vector3d(((Math.sin(attitude_roll_value)*Math.sin(attitude_heading_value))-
        		 (Math.cos(attitude_roll_value)*Math.sin(attitude_pitch_value)*Math.cos(attitude_heading_value))),
        		 ((Math.sin(attitude_roll_value)*Math.cos(attitude_heading_value))
        		+(Math.cos(attitude_roll_value)*Math.sin(attitude_pitch_value)*Math.sin(attitude_heading_value))),
        		Math.cos(attitude_roll_value)*Math.cos(attitude_pitch_value));
         //Combine M1, M2, and M3 to create the 3x3 orientation matrix
         Matrix3d M = new Matrix3d(M1, M2, M3);

         Vector3d subtraction = Vector3d.subtract(objectLoc, lensCenter);

         
         
         
         


         // set values for output components
         // note: you can rename these variable names to match your code
         pixel_r.getData().setDoubleValue(pixel_r_value);
         pixel_c.getData().setDoubleValue(pixel_c_value);
   }
}
