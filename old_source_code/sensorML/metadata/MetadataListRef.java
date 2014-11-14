/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.sensorML.metadata;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.vast.ogc.xlink.CachedReference;
import org.vast.ogc.xlink.IReferenceResolver;


/**
 * <p>
 * Implementation of xlink cached reference to a metadata list
 * </p>
 *
 * <p>Copyright (c) 2014 Sensia Software LLC</p>
 * @author Alexandre Robin <alex.robin@sensiasoftware.com>
 * @since Mar 13, 2014
 */
public class MetadataListRef<ItemType> extends CachedReference<MetadataList<ItemType>> implements IMetadataList<ItemType>
{
    
    public MetadataListRef(IReferenceResolver<MetadataList<ItemType>> resolver)
    {
        this.resolver = resolver;
    }
    

    public void add(int index, ItemType element)
    {
        getTarget().add(index, element);
    }


    public boolean add(ItemType e)
    {
        return getTarget().add(e);
    }


    public boolean addAll(Collection<? extends ItemType> c)
    {
        return getTarget().addAll(c);
    }


    public boolean addAll(int index, Collection<? extends ItemType> c)
    {
        return getTarget().addAll(index, c);
    }


    public void clear()
    {
        getTarget().clear();
    }


    public Object clone()
    {
        return getTarget().clone();
    }


    public boolean contains(Object o)
    {
        return getTarget().contains(o);
    }


    public boolean containsAll(Collection<?> c)
    {
        return getTarget().containsAll(c);
    }


    public void ensureCapacity(int minCapacity)
    {
        getTarget().ensureCapacity(minCapacity);
    }


    public boolean equals(Object o)
    {
        return getTarget().equals(o);
    }


    public ItemType get(int index)
    {
        return getTarget().get(index);
    }


    public String getLocalId()
    {
        return getTarget().getLocalId();
    }


    public String getIdentifier()
    {
        return getTarget().getIdentifier();
    }


    public String getLabel()
    {
        return getTarget().getLabel();
    }


    public String getDescription()
    {
        return getTarget().getDescription();
    }
    
    
    public String getCodespace()
    {
        return getTarget().getCodespace();
    }


    public int hashCode()
    {
        return getTarget().hashCode();
    }


    public int indexOf(Object o)
    {
        return getTarget().indexOf(o);
    }


    public boolean isEmpty()
    {
        return getTarget().isEmpty();
    }


    public Iterator<ItemType> iterator()
    {
        return getTarget().iterator();
    }


    public int lastIndexOf(Object o)
    {
        return getTarget().lastIndexOf(o);
    }


    public ListIterator<ItemType> listIterator()
    {
        return getTarget().listIterator();
    }


    public ListIterator<ItemType> listIterator(int index)
    {
        return getTarget().listIterator(index);
    }


    public ItemType remove(int index)
    {
        return getTarget().remove(index);
    }


    public boolean remove(Object o)
    {
        return getTarget().remove(o);
    }


    public boolean removeAll(Collection<?> c)
    {
        return getTarget().removeAll(c);
    }


    public boolean retainAll(Collection<?> c)
    {
        return getTarget().retainAll(c);
    }


    public ItemType set(int index, ItemType element)
    {
        return getTarget().set(index, element);
    }


    public void setLocalId(String localId)
    {
        getTarget().setLocalId(localId);
    }


    public void setIdentifier(String identifier)
    {
        getTarget().setIdentifier(identifier);
    }


    public void setLabel(String label)
    {
        getTarget().setLabel(label);
    }


    public void setDescription(String description)
    {
        getTarget().setDescription(description);
    }
    
    
    public void setCodespace(String codespace)
    {
        getTarget().setCodespace(codespace);
    }


    public int size()
    {
        return getTarget().size();
    }


    public List<ItemType> subList(int fromIndex, int toIndex)
    {
        return getTarget().subList(fromIndex, toIndex);
    }


    public Object[] toArray()
    {
        return getTarget().toArray();
    }


    public Object[] toArray(Object[] a)
    {
        return getTarget().toArray(a);
    }


    public String toString()
    {
        return getTarget().toString();
    }


    public void trimToSize()
    {
        getTarget().trimToSize();
    }
    
}
