package com.lucifiere.bef.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lucifiere.bef.annotation.Product;
import com.lucifiere.bef.template.TemplateRealization;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * The type Product spec.
 *
 * @author 沾清
 */
public class ProductSpec extends BaseSpec {

    /**
     * The Product abilities.
     */
    private Set<AbilitySpec> productAbilities = Sets.newHashSet();

    /**
     * The Realizations.
     */
    private List<TemplateRealization> realizations = Lists.newArrayList();

    /**
     * create a ProductSpec.
     *
     * @param productCode        the product unique code.
     * @param productName        the name of product.
     * @param productDescription the product description.
     * @return the created ProductSpec.
     */
    public static ProductSpec of(String productCode, String productName, String productDescription) {
        ProductSpec productSpec = new ProductSpec();
        productSpec.setCode(productCode);
        productSpec.setName(productName);
        productSpec.setDescription(productDescription);
        return productSpec;
    }

    /**
     * create a ProductSpec from annotation.
     *
     * @param product the Product annotation.
     * @return the created ProductSpec.
     */
    public static ProductSpec of(Product product) {
        ProductSpec productSpec = new ProductSpec();
        productSpec.setCode(product.code());
        productSpec.setName(product.name());
        productSpec.setDescription(product.desc());
        return productSpec;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return getName() + " - " + getCode();
    }

    /**
     * All extension points set.
     *
     * @return all the extension point specification under current domain's abilities.
     */
    public Set<ExtensionPointSpec> allExtensionPoints() {
        Set<ExtensionPointSpec> extensionPointSpecList = Sets.newHashSet();
        for (AbilitySpec ability : productAbilities) {
            for (AbilityInstanceSpec p : ability.getAbilityInstances()) {
                extensionPointSpecList.addAll(p.allExtensionPoints());
            }
        }
        return extensionPointSpecList;
    }

    /**
     * Find extension point desc extension point spec.
     *
     * @param extensionCode the extension code
     * @return the extension point spec
     */
    public ExtensionPointSpec findExtensionPointDesc(String extensionCode) {
        for (ExtensionPointSpec ext : allExtensionPoints()) {
            if (StringUtils.equals(extensionCode, ext.getCode())) { return ext; }
        }
        return null;
    }

    /**
     * Gets productAbilities
     *
     * @return the productAbilities
     */
    public Set<AbilitySpec> getProductAbilities() {
        return productAbilities;
    }

    /**
     * Sets productAbilities
     *
     * @param productAbilities the productAbilities
     */
    public void setProductAbilities(Set<AbilitySpec> productAbilities) {
        this.productAbilities = productAbilities;
    }

    /**
     * Gets realizations
     *
     * @return the realizations
     */
    public List<TemplateRealization> getRealizations() {
        return realizations;
    }
}
