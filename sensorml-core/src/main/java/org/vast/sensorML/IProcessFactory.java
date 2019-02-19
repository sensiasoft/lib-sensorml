/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML;

import org.vast.process.IProcessExec;
import org.vast.process.ProcessException;


/**
 * <p>
 * Interface for factories used to lookup and instantiate specific process
 * implementation for use in executable processing chains
 * </p>
 *
 * @author Alex Robin
 * @since May 16, 2017
 */
public interface IProcessFactory
{

    /**
     * Loads the executable process implementation corresponding to the given URI.
     * @param uri URI of the method (i.e. the specific process to instantiate)
     * @return the newly created process object
     * @throws ProcessException if the process cannot be instantiated (e.g. unknown URI)
     */
    public IProcessExec loadProcess(String uri) throws ProcessException;
    
}
