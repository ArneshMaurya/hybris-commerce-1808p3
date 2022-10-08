package com.hybris.training.core.service;

import com.hybris.training.core.dao.CustomerName;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerNameService {

    @Autowired
    CustomerName customerName;

    public List<CustomerModel> getCustomerName(){
        return customerName.getCustomerName();
    }
}
