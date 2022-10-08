package com.hybris.training.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/test")
public class MyTestLandingController  extends AbstractPageController{

    private static final String TEST_CMS_PAGE_CON = "testCMSPage";


    private static final String LOGOUT = "logout";
    private static final String ACCOUNT_CONFIRMATION_SIGNOUT_TITLE = "account.confirmation.signout.title";
    private static final String ACCOUNT_CONFIRMATION_CLOSE_TITLE = "account.confirmation.close.title";

    @RequestMapping(method = RequestMethod.GET)
    public String home(@RequestParam(value = WebConstants.CLOSE_ACCOUNT, defaultValue = "false") final boolean closeAcc,
                       @RequestParam(value = LOGOUT, defaultValue = "false") final boolean logout, final Model model,
                       final RedirectAttributes redirectModel) throws CMSItemNotFoundException
    {
        if (logout)
        {
            String message = ACCOUNT_CONFIRMATION_SIGNOUT_TITLE;
            if (closeAcc)
            {
                message = ACCOUNT_CONFIRMATION_CLOSE_TITLE;
            }
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, message);
            return REDIRECT_PREFIX + ROOT;
        }

        storeCmsPageInModel(model, getContentPageForLabelOrId(TEST_CMS_PAGE_CON));
        setUpMetaDataForContentPage(model, getContentPageForLabelOrId(TEST_CMS_PAGE_CON));
        updatePageTitle(model, getContentPageForLabelOrId(TEST_CMS_PAGE_CON));
        System.out.println("######              We are in test Page #########################");
        return getViewForPage(model);
    }

    protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
    {
        storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
    }

}

