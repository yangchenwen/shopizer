/**
 *
 */
package com.salesmanager.core.business.modules.cms.content;

import com.salesmanager.core.business.exception.ServiceException;

import java.util.Optional;

public interface FolderPut {

    /**
     * Create folder on root or on specific path
     * @param merchantStoreCode
     * @param folderName
     * @param path
     * @throws ServiceException
     */
    void addFolder(final String merchantStoreCode, String folderName, Optional<String> path)
            throws ServiceException;

}
