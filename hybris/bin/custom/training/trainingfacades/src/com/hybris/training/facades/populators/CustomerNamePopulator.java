package com.hybris.training.facades.populators;

import com.hybris.training.facades.user.data.NameData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class CustomerNamePopulator implements Populator<CustomerModel, NameData> {
    @Override
    public void populate(CustomerModel source, NameData target) throws ConversionException {
        target.setCode(source.getCustomerID());
        target.setName(source.getName());
    }
}
