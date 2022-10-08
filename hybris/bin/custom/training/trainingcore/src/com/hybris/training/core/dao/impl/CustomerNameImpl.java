package com.hybris.training.core.dao.impl;

import com.hybris.training.core.dao.CustomerName;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerNameImpl implements CustomerName {
    String queryString = "select {pk} from {customer} where {uid}='admin'";
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Override
    public List<CustomerModel> getCustomerName() {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString.toString());
        SearchResult<CustomerModel> result = flexibleSearchService.search(query);
        return result.getResult();
    }
}
