package com.hybris.training.facades.CustomerNameFacade.Impl;

import com.hybris.training.core.service.CustomerNameService;
import com.hybris.training.facades.CustomerNameFacade.CustomerNameFacade;
import com.hybris.training.facades.user.data.NameData;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerNameFacadeImpl implements CustomerNameFacade {
    @Autowired
    CustomerNameService customerNameService;

    @Override
    public List<NameData> getCustomerNameData() {
        List<CustomerModel> customerModel = customerNameService.getCustomerName();
        List<NameData> nameList = null;
        for (CustomerModel cs : customerModel){
            NameData nameData = new NameData();
            nameData.setCode(cs.getCustomerID());
            nameData.setName(cs.getName());
            nameList.add(nameData);
        }
    return nameList;
    }
}
