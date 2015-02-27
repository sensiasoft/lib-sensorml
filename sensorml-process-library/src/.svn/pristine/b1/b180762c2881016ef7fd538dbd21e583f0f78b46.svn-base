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
    Christopher Dillard <csdillard@gmail.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorML.process;

import org.vast.cdm.common.DataBlock;
import org.vast.cdm.common.DataComponent;
import org.vast.process.DataProcess;
import org.vast.process.ProcessException;

/**
 * <p>
 * This class is a collection of static utility methods that can be used by
 * process implementations to find inputs, outputs, and parameters.  These
 * methods are designed to throw {@link ProcessException}s when the requested
 * data is not available, and the error messages are intended to be verbose
 * enough to help diagnose the problem (as opposed to just having the
 * {@link DataProcess#init() init()} method throw a {@code NullPointerException}
 * with an empty error message).
 * </p>
 *
 * @author Christopher Dillard
 */
public class DataUtils {
	/**
	 * Attempts to locate a named input for a process.  Throws an exception if
	 * the input cannot be located.  Suitable for use in a process's
	 * {@link DataProcess#init()} and {@link DataProcess#execute()} methods.
	 * Provides detailed error messages in the exception to help diagnose
	 * problems if the given input can't be found.
	 *
	 * @param process A non-null {@link DataProcess process} instance whose
	 *   inputs have already been initialized.
	 * @param inputName The name of an input that will be located in the
	 *   process's {@linkplain DataProcess#getInputList() input list}.
	 *
	 * @return Guaranteed to return a non-null {@link DataComponent} for the
	 *   given input (or throw an exception if it can't be found).
	 *
	 * @throws NullPointerException Thrown if the input {@link DataProcess} is
	 *   null.
	 * @throws ProcessException Thrown if an input with the given name cannot
	 *   be found.  The error message will contain extra information to help
	 *   determine the problem.
	 */
	public static DataComponent findInput(DataProcess process, String inputName) throws ProcessException {
		// Check for null DataProcess.
		if (process == null) {
			throw new NullPointerException("Failed to find input \"" +
					inputName + "\": process was null.");
		}

		// Check for null input list.
		DataComponent inputList = process.getInputList();
		if (inputList == null) {
			throw new ProcessException("Failed to find input \"" +
					inputName + "\" from process \"" +
					process.getName() + "\": The process's input list is null.");
		}

		// Okay, basic checks came back okay.  Try to get the input.
		DataComponent result = inputList.getComponent(inputName);

		if (result == null) {
			// Okay... so didn't find it.

			// If it's because the input list is completely empty, throw an
			// exception that says so.
			int numInputs = inputList.getComponentCount();
			if (numInputs == 0) {
				throw new ProcessException("Failed to find input \"" +
						inputName + "\" from process \"" +
						process.getName() + "\": The process's input list is empty.");
			}

			// Otherwise, make a nice error message that includes the ACTUAL
			// list of input names so the user can compare.
			StringBuilder childNames = new StringBuilder();
			for (int i=0; i<numInputs; ++i) {
				if (i > 0)
					childNames.append(", ");
				childNames.append('"').append(inputList.getComponent(i).getName()).append('"');
			}
			throw new ProcessException("Failed to find input named \"" +
					inputName + "\" from process \"" +
					process.getName() + "\".  Available inputs: [" +
					childNames.toString() + "].");
		}

		// If we get through to here, then we actually got a non-null result
		// to return to the caller.
		return result;
	}

	/**
	 * Attempts to locate a named output for a process.  Throws an exception if
	 * the output cannot be located.  Suitable for use in a process's
	 * {@link DataProcess#init()} and {@link DataProcess#execute()} methods.
	 * Provides detailed error messages in the exception to help diagnose
	 * problems if the given output can't be found.
	 *
	 * @param process A non-null {@link DataProcess process} instance whose
	 *   outputs have already been initialized.
	 * @param inputName The name of an output that will be located in the
	 *   process's {@linkplain DataProcess#getOutputList() output list}.
	 *
	 * @return Guaranteed to return a non-null {@link DataComponent} for the
	 *   given output (or throw an exception if it can't be found).
	 *
	 * @throws NullPointerException Thrown if the input {@link DataProcess} is
	 *   null.
	 * @throws ProcessException Thrown if an output with the given name cannot
	 *   be found.  The error message will contain extra information to help
	 *   determine the problem.
	 */
	public static DataComponent findOutput(DataProcess process, String outputName) throws ProcessException {
		// Check for null DataProcess.
		if (process == null) {
			throw new NullPointerException("Failed to find output \"" +
					outputName + "\": process was null.");
		}

		// Check for null output list.
		DataComponent outputList = process.getOutputList();
		if (outputList == null) {
			throw new ProcessException("Failed to find output \"" +
					outputName + "\" from process \"" +
					process.getName() + "\": The process's output list is null.");
		}

		// Okay, basic checks came back okay.  Try to get the output.
		DataComponent result = outputList.getComponent(outputName);

		if (result == null) {
			// Okay... so didn't find it.

			// If it's because the output list is completely empty, throw an
			// exception that says so.
			int numOutputs = outputList.getComponentCount();
			if (numOutputs == 0) {
				throw new ProcessException("Failed to find output \"" +
						outputName + "\" from process \"" +
						process.getName() + "\": The process's output list is empty.");
			}

			// Otherwise, make a nice error message that includes the ACTUAL
			// list of output names so the user can compare.
			StringBuilder childNames = new StringBuilder();
			for (int i=0; i<numOutputs; ++i) {
				if (i > 0)
					childNames.append(", ");
				childNames.append('"').append(outputList.getComponent(i).getName()).append('"');
			}
			throw new ProcessException("Failed to find output named \"" +
					outputName + "\" from process \"" +
					process.getName() + "\".  Available outputs: [" +
					childNames.toString() + "].");
		}

		// If we get through to here, then we actually got a non-null result
		// to return to the caller.
		return result;
	}

	/**
	 * Attempts to locate a named parameter for a process.  Throws an exception
	 * if the parameter cannot be located.  Suitable for use in a process's
	 * {@link DataProcess#init()} and {@link DataProcess#execute()} methods.
	 * Provides detailed error messages in the exception to help diagnose
	 * problems if the given parameter can't be found.
	 *
	 * @param process A non-null {@link DataProcess process} instance whose
	 *   parameters have already been initialized.
	 * @param inputName The name of a parameter that will be located in the
	 *   process's {@linkplain DataProcess#getParameterList() parameter list}.
	 *
	 * @return Guaranteed to return a non-null {@link DataComponent} for the
	 *   given parameter (or throw an exception if it can't be found).
	 *
	 * @throws NullPointerException Thrown if the input {@link DataProcess} is
	 *   null.
	 * @throws ProcessException Thrown if a parameter with the given name cannot
	 *   be found.  The error message will contain extra information to help
	 *   determine the problem.
	 */
	public static DataComponent findParameter(DataProcess process, String parameterName) throws ProcessException {
		// Check for null DataProcess.
		if (process == null) {
			throw new NullPointerException("Failed to find parameter \"" +
					parameterName + "\": process was null.");
		}

		// Check for null output list.
		DataComponent parameterList = process.getParameterList();
		if (parameterList == null) {
			throw new ProcessException("Failed to find parameter \"" +
					parameterName + "\" from process \"" +
					process.getName() + "\": The process's parameter list is null.");
		}

		// Okay, basic checks came back okay.  Try to get the parameter.
		DataComponent result = parameterList.getComponent(parameterName);

		if (result == null) {
			// Okay... so didn't find it.

			// If it's because the parameter list is completely empty, throw an
			// exception that says so.
			int numParameters = parameterList.getComponentCount();
			if (numParameters == 0) {
				throw new ProcessException("Failed to find parameter \"" +
						parameterName + "\" from process \"" +
						process.getName() + "\": The process's parameter list is empty.");
			}

			// Otherwise, make a nice error message that includes the ACTUAL
			// list of output names so the user can compare.
			StringBuilder childNames = new StringBuilder();
			for (int i=0; i<numParameters; ++i) {
				if (i > 0)
					childNames.append(", ");
				childNames.append('"').append(parameterList.getComponent(i).getName()).append('"');
			}
			throw new ProcessException("Failed to find parameter \"" +
					parameterName + "\" from process \"" +
					process.getName() + "\".  Available parameters: [" +
					childNames.toString() + "].");
		}

		// If we get through to here, then we actually got a non-null result
		// to return to the caller.
		return result;
	}

	/**
	 * Attempts to find a named sub-component of a {@link DataComponent} (such
	 * as a process's input, output, or parameter list).  Throws an exception if
	 * the input does not have a sub-component with the given name.
	 *
	 * @param parent A {@link DataComponent} instance, such as one returned by
	 *   {@link #findInput(DataProcess, String)},
	 *   {@link #findOutput(DataProcess, String)},
	 *   or {@link #findParameter(DataProcess, String)}.
	 * @param childName The name of a sub-component that this method will
	 *   attempt to locate.
	 *
	 * @return Guaranteed to return a non-null {@link DataComponent} whose name
	 *   matches {@code childName} (or throw an exception if it can't be found).
	 *
	 * @throws ProcessException Thrown if a sub-component of the given name
	 *   cannot be found.
	 * @throws NullPointerException Thrown if the input {@link DataComponent}
	 *   is null.
	 */
	public static DataComponent getChild(DataComponent parent, String childName) throws ProcessException {
		if (parent == null) {
			throw new NullPointerException(
					"Failed to find data sub-component named \"" +
					childName + "\": null DataComponent was passed to getChild.");
		}
		DataComponent child = parent.getComponent(childName);
		if (child == null) {
			String parentName = parent.getName();
			if (parentName != null) parentName = "\"" + parentName + "\"";

			StringBuilder childNames = new StringBuilder();
			int numChildren = parent.getComponentCount();
			for (int i=0; i<numChildren; ++i) {
				if (i > 0)
					childNames.append(", ");
				childNames.append('"').append(parent.getComponent(i).getName()).append('"');
			}
			throw new ProcessException("Failed to find sub-component named \"" +
					childName + "\" in component " + parentName +
					".  Available sub-components: [" +
					childNames.toString() + "].");
		}
		return child;
	}

	public static double getDoubleValue(DataComponent parent, String childName) throws ProcessException {
		if (parent == null) {
			throw new NullPointerException("Failed to find sub-component named \"" +
					childName + "\": null DataComponent was passed to getDoubleValue(...).");
		}
		DataComponent child = getChild(parent, childName);
		DataBlock childDataBlock = child.getData();
		if (childDataBlock == null) {
			throw new ProcessException("Found a sub-component named \"" +
					childName + "\" in component \"" +
					parent.getName() +
					"\", but its DataBlock is null.  (Check that " +
					"a value was provided in the SensorML and/or you have " +
					"called init() on the DataProcess.)");
		}
		return childDataBlock.getDoubleValue();
	}

	public static double getDoubleValue(DataComponent data) throws ProcessException {
		if (data == null) {
			throw new NullPointerException("null DataComponent was passed to getDoubleValue");
		}
		DataBlock dataBlock = data.getData();
		if (dataBlock == null) {
			throw new ProcessException("Attempted to get a double value from " +
					"a component with a null DataBlock.  (Check that a value " +
					"was provided in the SensorML and/or you have called " +
					"init() on the DataProcess and/or you have links to copy " +
					"values to the input.)");
		}
		return dataBlock.getDoubleValue();
	}

	public static void setDoubleValue(DataComponent data, double value) throws ProcessException {
		if (data == null) {
			throw new NullPointerException("null DataComponent was passed to setDoubleValue");
		}
		DataBlock dataBlock = data.getData();
		if (dataBlock == null) {
			throw new ProcessException("Attempted to set a double value into " +
					"a component with a null DataBlock.  (Check that you have " +
					"called init() and/or createNewOutputBlocks() on the " +
					"DataProcess.)");
		}
		dataBlock.setDoubleValue(value);
	}
}
