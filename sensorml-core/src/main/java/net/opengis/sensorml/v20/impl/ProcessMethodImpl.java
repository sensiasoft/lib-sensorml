/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20.impl;

import java.util.ArrayList;
import java.util.List;
import org.vast.data.AbstractSWEIdentifiableImpl;
import net.opengis.sensorml.v20.AbstractAlgorithm;
import net.opengis.sensorml.v20.ProcessMethod;


/**
 * POJO class for XML type ProcessMethodType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
public class ProcessMethodImpl extends AbstractSWEIdentifiableImpl implements ProcessMethod
{
    private static final long serialVersionUID = 7677068874778573592L;
    protected ArrayList<AbstractAlgorithm> algorithmList = new ArrayList<AbstractAlgorithm>();
    
    
    public ProcessMethodImpl()
    {
    }
    
    
    /**
     * Gets the list of algorithm properties
     */
    @Override
    public List<AbstractAlgorithm> getAlgorithmList()
    {
        return algorithmList;
    }
    
    
    /**
     * Returns number of algorithm properties
     */
    @Override
    public int getNumAlgorithms()
    {
        if (algorithmList == null)
            return 0;
        return algorithmList.size();
    }
    
    
    /**
     * Adds a new algorithm property
     */
    @Override
    public void addAlgorithm(AbstractAlgorithm algorithm)
    {
        this.algorithmList.add(algorithm);
    }
}
