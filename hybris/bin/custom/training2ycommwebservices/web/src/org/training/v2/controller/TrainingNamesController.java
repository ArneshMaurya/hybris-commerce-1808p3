package org.training.v2.controller;

import de.hybris.platform.webservicescommons.swagger.ApiBaseSiteIdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/{baseSiteId}")
@Api(tags = "Training names")
public class TrainingNamesController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(com.training.v2.contoller.TrainingNamesController.class);

    @Secured("ROLE_TRUSTED_CLIENT")
    @RequestMapping(value = "/{name}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(nickname = "getNames",value = "Get The Names",notes = "Name Wale notes",authorizations =
            {@Authorization(value = "oauth2_client_credentials")})
    @ApiBaseSiteIdParam
    public String getNameDetails(
            @ApiParam(value = "Response config",allowableValues = "BASIC,DEFAULT,FULL")
            @RequestParam(defaultValue = DEFAULT_FIELD_SET)
            final String fields)
    {
        return "I Am Test CAll";
    }
}
