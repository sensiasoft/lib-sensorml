
//change to your package location
package org.sensorML.process;

import org.vast.data.*;
import org.vast.process.*;
import org.vast.sensorML.ExecutableProcessImpl;


public class GreaterThanInBinary_Process extends ExecutableProcessImpl {


   // declare input components
   DataValue inputValue;

   // declare output components
   DataValue trueOrFalseInBinary;

   DataValue threshold;


   public GreaterThanInBinary_Process() {
   }


   /**
   * Initializes the process
   * Get handles to input/output components
   */
   public void init() throws SMLProcessException {

      try {

         // initialize input components
         inputValue= (DataValue) inputData.getComponent("inputValue");

         // initialize output components
         trueOrFalseInBinary = (DataValue) outputData.getComponent("trueOrFalseInBinary");

	   threshold = (DataValue) paramData.getComponent("threshold");


      }
      catch (ClassCastException e) {
         throw new SMLProcessException("Invalid I/O data", e);
      }

   }

   /**
   * Executes the process
   * Get current values for all components and then executes
   */
   public void execute() throws SMLProcessException {


         // get values for input components
         // note: you can rename these variable names to match your code
         double inputValueData = inputValue.getData().getDoubleValue();
         double thresholdData = threshold.getData().getDoubleValue();
         
         // set values for output components
         if(inputValueData > thresholdData){
        	 trueOrFalseInBinary.getData().setDoubleValue(1);
         }
         if(inputValueData <= thresholdData){
        	 trueOrFalseInBinary.getData().setDoubleValue(0);
         }
         
   }
}
