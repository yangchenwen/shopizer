package com.salesmanager.shop.init.data;

import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.reference.init.InitializationDatabase;
import com.salesmanager.core.business.services.system.MerchantConfigurationService;
import com.salesmanager.core.business.services.system.SystemConfigurationService;
import com.salesmanager.core.business.services.user.GroupService;
import com.salesmanager.core.business.services.user.PermissionService;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantConfig;
import com.salesmanager.shop.admin.security.WebUserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class InitializationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationLoader.class);
    @Inject
    protected PermissionService permissionService;
    @Inject
    protected GroupService groupService;
    @Inject
    protected MerchantStoreService merchantService;

    //@Inject
    //private InitData initData;
    @Value("${db.init.data:true}")
    private boolean initDefaultData;
    @Inject
    private MerchantConfigurationService merchantConfigurationService;
    @Inject
    private InitializationDatabase initializationDatabase;
    @Inject
    private SystemConfigurationService systemConfigurationService;
    @Inject
    private WebUserServices userDetailsService;
    @Inject
    private CoreConfiguration configuration;

    @PostConstruct
    public void init() {

        try {

            //Check flag to populate or not the database
            if (!this.initDefaultData) {
                return;
            }

            if (initializationDatabase.isEmpty()) {

                //All default data to be created

                LOGGER.info(String.format("%s : Shopizer database is empty, populate it....", "sm-shop"));

                initializationDatabase.populate("sm-shop");

                MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);

                userDetailsService.createDefaultAdmin();
                MerchantConfig config = new MerchantConfig();
                config.setAllowPurchaseItems(true);
                config.setDisplayAddToCartOnFeaturedItems(true);

                merchantConfigurationService.saveMerchantConfig(config, store);

            }

        } catch (Exception e) {
            LOGGER.error("Error in the init method", e);
        }

    }

}
