//=============================================================================
//===	Copyright (C) 2001-2007 Food and Agriculture Organization of the
//===	United Nations (FAO-UN), United Nations World Food Programme (WFP)
//===	and United Nations Environment Programme (UNEP)
//===
//===	This program is free software; you can redistribute it and/or modify
//===	it under the terms of the GNU General Public License as published by
//===	the Free Software Foundation; either version 2 of the License, or (at
//===	your option) any later version.
//===
//===	This program is distributed in the hope that it will be useful, but
//===	WITHOUT ANY WARRANTY; without even the implied warranty of
//===	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//===	General Public License for more details.
//===
//===	You should have received a copy of the GNU General Public License
//===	along with this program; if not, write to the Free Software
//===	Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
//===
//===	Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
//===	Rome - Italy. email: geonetwork@osgeo.org
//==============================================================================

package org.fao.geonet;

import com.google.common.annotations.VisibleForTesting;
import org.fao.geonet.kernel.metadata.StatusActions;
import org.fao.geonet.util.ThreadPool;
import org.springframework.context.ApplicationContext;

public class GeonetContext {
    private final ApplicationContext _springAppContext;
    private final Class<StatusActions> _statusActionsClass;
    private final ThreadPool _threadPool;
    private final NodeInfo nodeInfo;

    // ---------------------------------------------------------------------------
    @VisibleForTesting
    public GeonetContext(ApplicationContext springAppContext, boolean readOnly,
                               Class<StatusActions> statusActionsClass) {
        this._springAppContext = springAppContext;
        this.nodeInfo = springAppContext.getBean(NodeInfo.class);
        nodeInfo.setReadOnly(readOnly);
        this._statusActionsClass = statusActionsClass;
        this._threadPool = springAppContext.getBean(ThreadPool.class);
    }


    // ---------------------------------------------------------------------------

    public ApplicationContext getApplicationContext() {
        return _springAppContext;
    }

    public ThreadPool getThreadPool() {
        return _threadPool;
    }

    public <T> T getBean(Class<T> beanClass) {
        return _springAppContext.getBean(beanClass);
    }

    // ---------------------------------------------------------------------------

    public Class<StatusActions> getStatusActionsClass() {
        return _statusActionsClass;
    }

    public boolean isReadOnly() {
        return nodeInfo.isReadOnly();
    }

    public void setReadOnly(boolean readOnly) {
        nodeInfo.setReadOnly(readOnly);
    }
}