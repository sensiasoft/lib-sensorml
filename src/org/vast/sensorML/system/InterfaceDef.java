/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
 Alexandre Robin <robin@nsstc.uah.edu>
 
 ******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.system;

/**
 * <p><b>Title:</b><br/>
 * Interface Definition
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SensorML Communication Interface Definition
 * Defines a protocol for each layer of the OSI stack of communication
 * protocols, and adds a mechanical layer for describing the connector
 * as well as a service layer for describing web service parameters.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 17, 2006
 * @version 1.0
 */
public class InterfaceDef
{
    protected String name;
    protected ProtocolDef serviceLayerProtocol;         // SOS, SPS, WFS
    protected ProtocolDef applicationLayerProtocol;     // HTTP, SMTP, FTP, TELNET
    protected ProtocolDef presentationLayerProtocol;    // ASCII, ZIP, SSH, MPEG
    protected ProtocolDef sessionLayerProtocol;         // Session for TCP
    protected ProtocolDef transportLayerProtocol;       // TCP, UDP, RTP
    protected ProtocolDef networkLayerProtocol;         // IP, ICMP, ARP
    protected ProtocolDef dataLinkLayerProtocol;        // Ethernet, IEEE802.11
    protected ProtocolDef physicalLayerProtocol;        // RS-232, 10BaseT, 100BaseT, DSL
    protected ProtocolDef mechanicalLayerProtocol;      // DB9, RJ45


    public ProtocolDef getApplicationLayerProtocol()
    {
        return applicationLayerProtocol;
    }


    public void setApplicationLayerProtocol(ProtocolDef applicationLayerProtocol)
    {
        this.applicationLayerProtocol = applicationLayerProtocol;
    }


    public ProtocolDef getDataLinkLayerProtocol()
    {
        return dataLinkLayerProtocol;
    }


    public void setDataLinkLayerProtocol(ProtocolDef dataLinkLayerProtocol)
    {
        this.dataLinkLayerProtocol = dataLinkLayerProtocol;
    }


    public ProtocolDef getMechanicalLayerProtocol()
    {
        return mechanicalLayerProtocol;
    }


    public void setMechanicalLayerProtocol(ProtocolDef mechanicalLayerProtocol)
    {
        this.mechanicalLayerProtocol = mechanicalLayerProtocol;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public ProtocolDef getNetworkLayerProtocol()
    {
        return networkLayerProtocol;
    }


    public void setNetworkLayerProtocol(ProtocolDef networkLayerProtocol)
    {
        this.networkLayerProtocol = networkLayerProtocol;
    }


    public ProtocolDef getPhysicalLayerProtocol()
    {
        return physicalLayerProtocol;
    }


    public void setPhysicalLayerProtocol(ProtocolDef physicalLayerProtocol)
    {
        this.physicalLayerProtocol = physicalLayerProtocol;
    }


    public ProtocolDef getPresentationLayerProtocol()
    {
        return presentationLayerProtocol;
    }


    public void setPresentationLayerProtocol(ProtocolDef presentationLayerProtocol)
    {
        this.presentationLayerProtocol = presentationLayerProtocol;
    }


    public ProtocolDef getServiceLayerProtocol()
    {
        return serviceLayerProtocol;
    }


    public void setServiceLayerProtocol(ProtocolDef serviceLayerProtocol)
    {
        this.serviceLayerProtocol = serviceLayerProtocol;
    }


    public ProtocolDef getSessionLayerProtocol()
    {
        return sessionLayerProtocol;
    }


    public void setSessionLayerProtocol(ProtocolDef sessionLayerProtocol)
    {
        this.sessionLayerProtocol = sessionLayerProtocol;
    }


    public ProtocolDef getTransportLayerProtocol()
    {
        return transportLayerProtocol;
    }


    public void setTransportLayerProtocol(ProtocolDef transportLayerProtocol)
    {
        this.transportLayerProtocol = transportLayerProtocol;
    }
}
