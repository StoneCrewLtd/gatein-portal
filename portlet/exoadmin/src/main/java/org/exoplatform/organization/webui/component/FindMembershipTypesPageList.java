/*
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.organization.webui.component;

import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.commons.utils.ListAccessImpl;
import org.exoplatform.commons.utils.StatelessPageList;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.organization.MembershipType;
import org.exoplatform.services.organization.OrganizationService;
import org.gatein.common.text.EntityEncoder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class FindMembershipTypesPageList extends StatelessPageList<FindMembershipTypesPageList.UIMembershipType>
{

   public FindMembershipTypesPageList(int pageSize)
   {
      super(pageSize);
   }

   @Override
   protected ListAccess<UIMembershipType> connect() throws Exception
   {
      ExoContainer container = PortalContainer.getInstance();
      OrganizationService service = (OrganizationService)container.getComponentInstance(OrganizationService.class);
      List<MembershipType> memberships = (List<MembershipType>)service.getMembershipTypeHandler().findMembershipTypes();
      
      return new ListAccessImpl<UIMembershipType>(UIMembershipType.class, convertMembershipTypes(memberships));
   }
   
   private List<UIMembershipType> convertMembershipTypes(List<MembershipType> memberships)
   {
      List<UIMembershipType> types = new ArrayList<UIMembershipType>(memberships.size());
      for (MembershipType type: memberships)
      {
         types.add(new UIMembershipType(type));
      }
      return types;
   }
   
   public class UIMembershipType implements Serializable
   {
      private MembershipType mType;
      
      public UIMembershipType(MembershipType mType)
      {
         this.mType = mType;
      }
      
      public String getName()
      {
         return mType.getName();
      }
      
      public Date getCreatedDate()
      {
         return mType.getCreatedDate();
      }

      public Date getModifiedDate()
      {
         return mType.getModifiedDate();
      }

      public String getDescription()
      {
         return mType.getDescription();
      }
      
      public String getEncodedDescription()
      {
         if (getDescription() != null)
         {
            EntityEncoder encoder = EntityEncoder.FULL;
            return encoder.encode(getDescription());
         }
         else
         {
            return null;
         }
      }
   }
}
