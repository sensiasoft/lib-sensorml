/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.process;


/**
 * <p>
 * Basic process info used for discovering available process implementations
 * on the classpath
 * </p>
 *
 * @author Alex Robin
 * @since May 5, 2017
 */
public class ProcessInfo
{
    protected String uri;
    protected String name;
    protected String description;    
    protected Class<? extends IProcessExec> clazz;
    
    
    public ProcessInfo(String uri, String name, String desc, Class<? extends IProcessExec> clazz)
    {
        this.uri = uri;
        this.name = name;
        this.description = desc;
        this.clazz = clazz;
    }


    public String getUri()
    {
        return uri;
    }


    public String getName()
    {
        return name;
    }


    public String getDescription()
    {
        return description;
    }


    public Class<? extends IProcessExec> getImplementationClass()
    {
        return clazz;
    }
}