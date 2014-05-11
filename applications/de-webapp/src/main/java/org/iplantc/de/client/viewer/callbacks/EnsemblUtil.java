package org.iplantc.de.client.viewer.callbacks;

import org.iplantc.de.client.gin.ServicesInjector;
import org.iplantc.de.client.models.HasPaths;
import org.iplantc.de.client.models.IsMaskable;
import org.iplantc.de.client.models.diskResources.DiskResourceStatMap;
import org.iplantc.de.client.models.diskResources.File;
import org.iplantc.de.client.models.viewer.InfoType;
import org.iplantc.de.client.services.DiskResourceServiceFacade;
import org.iplantc.de.client.util.DiskResourceUtil;
import org.iplantc.de.commons.client.views.gxt3.dialogs.IplantInfoBox;
import org.iplantc.de.resources.client.messages.I18N;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EnsemblUtil {

    private final IsMaskable container;
    private final File file;
    private final String infoType;

    public EnsemblUtil(File file, String infoType, IsMaskable container) {
        this.file = file;
        this.container = container;
        this.infoType = infoType;
    }

    public void sendToEnsembl() {
        final DiskResourceServiceFacade diskResourceServiceFacade = ServicesInjector.INSTANCE.getDiskResourceServiceFacade();
        final HasPaths diskResourcePaths = diskResourceServiceFacade.getDiskResourceFactory().pathsList().as();
        final String path = file.getPath();
        String filename = DiskResourceUtil.parseNameFromPath(path);
        String parent = DiskResourceUtil.parseParent(path);
        String indexFile = null;
        if (infoType.equals(InfoType.BAM.toString())) {
            indexFile = filename + ".bai";
        } else if (infoType.equals(InfoType.VCF.toString())) {
            indexFile = filename + ".tbi";
        } else if (infoType.equals(InfoType.GFF.toString())) {
            indexFile = null;
        }
        if (indexFile != null) {
            final String indexFilePath = parent + "/" + indexFile;
            diskResourcePaths.setPaths(Lists.newArrayList(path, indexFilePath));
        }

        diskResourceServiceFacade.getStat(diskResourcePaths, new AsyncCallback<DiskResourceStatMap>() {

            @Override
            public void onSuccess(DiskResourceStatMap result) {
                diskResourceServiceFacade.shareWithAnonymous(diskResourcePaths, new ShareAnonymousCallback(file, container));
            }

            @Override
            public void onFailure(Throwable caught) {
                IplantInfoBox info = new IplantInfoBox(I18N.DISPLAY.indexFileMissing(), I18N.ERROR.indexFileMissing());
                info.show();
                if (container != null) {
                    container.unmask();
                }
            }
        });
    }

}
