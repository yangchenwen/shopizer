package com.salesmanager.core.business.services.catalog.product.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

import java.util.List;

public interface ProductAttributeService extends
        SalesManagerEntityService<Long, ProductAttribute> {

    void saveOrUpdate(ProductAttribute productAttribute)
            throws ServiceException;

    List<ProductAttribute> getByOptionId(MerchantStore store,
                                         Long id) throws ServiceException;

    List<ProductAttribute> getByOptionValueId(MerchantStore store,
                                              Long id) throws ServiceException;

    List<ProductAttribute> getByProductId(MerchantStore store, Product product, Language language)
            throws ServiceException;

    List<ProductAttribute> getByProductId(MerchantStore store, Product product)
            throws ServiceException;

    List<ProductAttribute> getByAttributeIds(MerchantStore store, Product product, List<Long> ids)
            throws ServiceException;

    List<ProductAttribute> getProductAttributesByCategoryLineage(MerchantStore store, String lineage, Language language) throws Exception;
}
