/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.promotionengineatddtests.keywords;

import static com.google.common.base.Preconditions.checkState;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import de.hybris.platform.atddengine.keywords.AbstractKeywordLibrary;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.enums.GroupType;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.core.order.EntryGroup;
import de.hybris.platform.couponservices.services.CouponService;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.order.CalculationService;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.daos.DeliveryModeDao;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.promotionengineatddtests.constants.PromotionEngineAtddTestsConstants;
import de.hybris.platform.promotionengineservices.dao.PromotionDao;
import de.hybris.platform.promotionengineservices.model.AbstractRuleBasedPromotionActionModel;
import de.hybris.platform.promotionengineservices.promotionengine.PromotionEngineService;
import de.hybris.platform.promotionengineservices.promotionengine.impl.DefaultPromotionEngineService;
import de.hybris.platform.promotions.model.AbstractPromotionActionModel;
import de.hybris.platform.promotions.model.PromotionGroupModel;
import de.hybris.platform.promotions.model.PromotionResultModel;
import de.hybris.platform.ruleengine.MessageLevel;
import de.hybris.platform.ruleengine.ResultItem;
import de.hybris.platform.ruleengine.RuleEngineActionResult;
import de.hybris.platform.ruleengine.RuleEngineService;
import de.hybris.platform.ruleengine.RuleEvaluationResult;
import de.hybris.platform.ruleengine.dao.DroolsKIEBaseDao;
import de.hybris.platform.ruleengine.dao.EngineRuleDao;
import de.hybris.platform.ruleengine.dao.RuleEngineContextDao;
import de.hybris.platform.ruleengine.dao.RulesModuleDao;
import de.hybris.platform.ruleengine.enums.RuleType;
import de.hybris.platform.ruleengine.init.InitializationFuture;
import de.hybris.platform.ruleengine.model.AbstractRuleEngineContextModel;
import de.hybris.platform.ruleengine.model.AbstractRulesModuleModel;
import de.hybris.platform.ruleengine.model.DroolsKIEBaseModel;
import de.hybris.platform.ruleengine.model.DroolsKIEModuleModel;
import de.hybris.platform.ruleengine.model.DroolsRuleEngineContextModel;
import de.hybris.platform.ruleengine.model.DroolsRuleModel;
import de.hybris.platform.ruleengineservices.action.RuleActionService;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerService;
import de.hybris.platform.ruleengineservices.model.AbstractRuleModel;
import de.hybris.platform.ruleengineservices.order.dao.ExtendedOrderDao;
import de.hybris.platform.ruleengineservices.rao.AbstractRuleActionRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.services.RuleService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.TaxValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;



public class PromotionEngineServicesKeywordLibrary extends AbstractKeywordLibrary
{

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(PromotionEngineServicesKeywordLibrary.class);

	@Autowired
	@Qualifier("commerceRuleEngineService")
	private RuleEngineService commerceRuleEngineService;

	@Autowired
	@Qualifier("modelService")
	private ModelService modelService;

	@Autowired
	@Qualifier("promotionEngineService")
	private PromotionEngineService promotionEngineService;

	@Autowired
	@Qualifier("flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Autowired
	@Qualifier("priceService")
	private PriceService priceService;

	@Autowired
	@Qualifier("cartService")
	private CartService cartService;

	@Autowired
	@Qualifier("calculationService")
	private CalculationService calculationService;

	@Autowired
	@Qualifier("ruleActionService")
	private RuleActionService ruleActionService;

	@Autowired
	@Qualifier("ruleEngineContextDao")
	private RuleEngineContextDao ruleEngineContextDao;

	@Autowired
	@Qualifier("commonI18NService")
	private CommonI18NService commonI18NService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("extendedOrderDao")
	private ExtendedOrderDao orderDao;

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;

	@Autowired
	@Qualifier("mediaService")
	private MediaService mediaService;

	@Autowired
	@Qualifier("ruleService")
	private RuleService ruleService;

	@Autowired
	@Qualifier("ruleCompilerService")
	private RuleCompilerService ruleCompilerService;

	@Autowired
	@Qualifier("droolsKIEBaseDao")
	private DroolsKIEBaseDao droolsKIEBaseDao;

	@Autowired
	@Qualifier("rulesModuleDao")
	private RulesModuleDao rulesModuleDao;

	@Autowired
	@Qualifier("promotionDao")
	private PromotionDao promotionsDao;

	@Autowired
	@Qualifier("deliveryModeDao")
	private DeliveryModeDao deliveryModeDao;

	@Autowired
	@Qualifier("couponService")
	private CouponService couponService;

	@Autowired
	@Qualifier("baseSiteService")
	private BaseSiteService baseSiteService;

	@Autowired
	@Qualifier("sessionService")
	private SessionService sessionService;

	@Autowired
	@Qualifier("engineRuleDao")
	private EngineRuleDao engineRuleDao;

	public RuleEngineActionResult initializeRuleEngineWithRuleFromAndMaxAllowedRuns(final String rulesFileName,
			final int maxAllowedRuns) throws IOException
	{
		final String ruleContent = readRuleFile(rulesFileName);

		final Optional<DroolsKIEBaseModel> baseModel = getDroolsKieBase("promotions-base-junit");
		if (baseModel.isPresent())
		{
			final DroolsRuleModel rule = modelService.create(DroolsRuleModel.class);
			rule.setActive(Boolean.TRUE);
			rule.setRuleContent(ruleContent);
			rule.setCode(rulesFileName);
			rule.setMaxAllowedRuns(Integer.valueOf(maxAllowedRuns));
			rule.setRuleType(RuleType.PROMOTION);
			if (rulesFileName.contains(".drl"))
			{
				rule.setUuid(rulesFileName.substring(0, rulesFileName.length() - 4));
			}
			else
			{
				rule.setUuid(rulesFileName);
			}
			setGlobals(rule);
			rule.setKieBase(baseModel.get());
			getModelService().save(rule);
			final List<RuleEngineActionResult> results = getCommerceRuleEngineService()
					.initialize(Collections.singletonList(getTestRulesModule(singleton(rule))), false, false)
					.waitForInitializationToFinish().getResults();
			if (CollectionUtils.isNotEmpty(results))
			{
				final RuleEngineActionResult result = results.get(0);
				if (result.isActionFailed())
				{
					throw new IllegalStateException("error during rule initialization. Check your rules. \nDetailed error(s): "
							+ result.getMessagesAsString(MessageLevel.ERROR));
				}
				return result;
			}
		}
		throw new IllegalStateException("KieBaseModel was not found, check the test setup");
	}

	public RuleEngineActionResult initializeRuleEngineWithRulesFrom(final String rulesFileNames) throws IOException
	{
		final String[] fileNames = rulesFileNames.split("\\s+");
		final List<String> ruleNameList = Arrays.stream(fileNames).collect(Collectors.toList());//NOSONAR
		final Map<String, String> ruleName2RuleContent = new HashMap<>();
		for (final String rulesFileName : ruleNameList)
		{
			ruleName2RuleContent.put(rulesFileName, readRuleFile(rulesFileName));
		}
		final RuleEngineActionResult result = initializeRuleEngineWithRules(ruleName2RuleContent);
		if (result.isActionFailed())
		{
			throw new IllegalStateException("error during rule initialization. Check your rules. \nDetailed error(s): "
					+ result.getMessagesAsString(MessageLevel.ERROR));
		}
		return result;
	}

	public CartModel createCart(final String cartId, final String currency)
	{
		final CartModel result = modelService.create(CartModel.class);

		result.setCode(cartId);
		result.setCurrency(commonI18NService.getCurrency(currency != null ? currency : "USD"));
		result.setDate(new Date());
		result.setUser(userService.getAnonymousUser());
		result.setNet(Boolean.TRUE);
		modelService.save(result);

		return result;
	}

	public CartModel createCart(final String cartId)
	{
		return createCart(cartId, null);
	}


	public void addProductToCart(final ProductModel product, final String cartId)
	{

		final CartModel cart = getCartByCode(cartId);
		final CartEntryModel entry = cartService.addNewEntry(cart, product, 1, product.getUnit());
		entry.setTaxValues(new ArrayList<TaxValue>());

		for (final PriceRowModel pr : product.getEurope1Prices())
		{
			if (pr.getCurrency().equals(cart.getCurrency()))
			{
				entry.setBasePrice(pr.getPrice());
				entry.setTotalPrice(pr.getPrice());
			}
		}

		modelService.save(entry);
		modelService.save(cart);
	}

	public void removeProduct(final ProductModel product, final String cartId) throws CalculationException
	{
		final CartModel cart = getCartByCode(cartId);
		final Map<Integer, Long> quantityMap = new HashMap<Integer, Long>();
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getProduct().equals(product))
			{
				quantityMap.put(entry.getEntryNumber(), Long.valueOf(0L));
			}
		}
		cartService.updateQuantities(cart, quantityMap);

		calculationService.calculate(cart);
	}

	public void removeOneItemOfProduct(final ProductModel product, final String cartId) throws CalculationException
	{
		final CartModel cart = getCartByCode(cartId);
		final Map<Integer, Long> quantityMap = new HashMap<Integer, Long>();
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getProduct().equals(product))
			{
				quantityMap.put(entry.getEntryNumber(), Long.valueOf(entry.getQuantity().longValue() - 1));
			}
		}
		cartService.updateQuantities(cart, quantityMap);

		calculationService.calculate(cart);
	}

	public void updateCartQuantity(final int entryNo, final long quantity, final String cartId) throws CalculationException
	{
		final CartModel cart = getCartByCode(cartId);



		final Map<Integer, Long> quantityMap = new HashMap<Integer, Long>();
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getEntryNumber().intValue() == (entryNo))
			{
				quantityMap.put(entry.getEntryNumber(), Long.valueOf(quantity));
				break;
			}
		}
		cartService.updateQuantities(cart, quantityMap);

		calculationService.calculate(cart);
	}

	public void updateCartEntryWithGroupNumber(final int entryNumber, final int entryGroupNumber, final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);

		final CartEntryModel entry = cartService.getEntryForNumber(cart, entryNumber);
		entry.setEntryGroupNumbers(singleton(entryGroupNumber));

		getModelService().save(entry);
	}

	public void createEntryGroupForCart(final int groupNumber, final String groupType, final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);

		final EntryGroup entryGroup = new EntryGroup();

		entryGroup.setGroupNumber(groupNumber);
		entryGroup.setGroupType(GroupType.valueOf(groupType));

		final List<EntryGroup> entryGroups = Lists.newArrayList(cart.getEntryGroups());
		entryGroups.add(entryGroup);
		cart.setEntryGroups(entryGroups);

		getModelService().save(cart);
	}

	protected CartModel getCartByCode(final String code)
	{
		return (CartModel) orderDao.findOrderByCode(code);

	}

	public BigDecimal getTotalOfCart(final String cartId) throws CalculationException
	{

		final CartModel cart = getCartByCode(cartId);
		calculationService.calculate(cart);

		return BigDecimal.valueOf(cart.getTotalPrice().doubleValue());

	}

	protected String readRuleFile(final String fileName) throws IOException
	{
		Path rulePath;
		try
		{
			rulePath = Paths.get(Registry.getApplicationContext().getResource("classpath:/rules/" + fileName).getURI());
		}
		catch (final FileNotFoundException fnf)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(fnf.getMessage(), fnf);
			}
			rulePath = Paths.get(Registry.getApplicationContext()
					.getResource("classpath:../../promotionenginesamplesaddon/resources/rules/" + fileName + ".drl").getURI());
		}
		final InputStream is = Files.newInputStream(rulePath);
		final StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, Charset.forName("UTF-8"));
		return writer.toString();
	}


	protected RuleEngineActionResult initializeRuleEngineWithRules(final Map<String, String> ruleName2media)
	{
		final Set<DroolsRuleModel> rules = new LinkedHashSet<>();
		final Optional<DroolsKIEBaseModel> baseModel = getDroolsKieBase("promotions-base-junit");
		if (baseModel.isPresent())
		{
			for (final Entry<String, String> entry : ruleName2media.entrySet())
			{
				final DroolsRuleModel rule = modelService.create(DroolsRuleModel.class);
				rule.setActive(Boolean.TRUE);
				rule.setRuleContent(entry.getValue());
				rule.setCode(entry.getKey());
				rule.setRuleType(RuleType.PROMOTION);
				if (entry.getKey().contains(".drl"))
				{
					rule.setUuid(entry.getKey().substring(0, entry.getKey().length() - 4));
				}
				else
				{
					rule.setUuid(entry.getKey());
				}
				rule.setKieBase(baseModel.get());
				setGlobals(rule);
				rules.add(rule);
				modelService.save(rule);
			}
		}
		final List<RuleEngineActionResult> results = getCommerceRuleEngineService()
				.initialize(singletonList(getTestRulesModule(rules)), false, false).waitForInitializationToFinish().getResults();
		return CollectionUtils.isNotEmpty(results) ? results.get(0) : null;
	}

	protected List<PromotionGroupModel> getTestPromoGroups()
	{
		final List<PromotionGroupModel> list = Lists.newArrayList();
		final PromotionGroupModel testPromo = promotionsDao.findPromotionGroupByCode("testPromoGrp");
		final PromotionGroupModel electronicsPromoGrp = promotionsDao.findPromotionGroupByCode("electronicsPromoGrp");
		list.add(testPromo);
		list.add(electronicsPromoGrp);
		return list;
	}

	// extracts the global definitions out of the drl and sets them at the rule
	protected void setGlobals(final DroolsRuleModel rule)
	{
		final String drl = rule.getRuleContent();
		final BufferedReader br = new BufferedReader(new StringReader(drl));
		final Map<String, String> globals = new HashMap<>();
		br.lines().filter(s -> s.startsWith("global")).forEach(s -> {
			final String[] split = s.split("\\s+");
			// 3rd string is the global bean
				if (split.length == 3)
				{
					final String string = split[2].substring(0, split[2].length() - 1);
					globals.put(string, string);
				}
			});
		rule.setGlobals(globals);
	}

	protected DroolsKIEModuleModel getTestRulesModule(final Set<DroolsRuleModel> rules)
	{
		final AbstractRuleEngineContextModel abstractContext = getRuleEngineContextDao().findRuleEngineContextByName(
				"promotions-junit-context");
		checkState(abstractContext instanceof DroolsRuleEngineContextModel,
				"ruleengine context must be of type DroolsRuleEngineContextModel");

		final DroolsRuleEngineContextModel context = (DroolsRuleEngineContextModel) abstractContext;

		final DroolsKIEBaseModel kieBase = context.getKieSession().getKieBase();
		kieBase.setRules(rules);
		getModelService().saveAll();
		return context.getKieSession().getKieBase().getKieModule();
	}


	public BigDecimal evaluatePromotionForCart(final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);

		final CurrencyModel currency = cart.getCurrency();
		Integer scale = null;
		if (nonNull(currency))
		{
			scale = currency.getDigits();
		}

		((DefaultPromotionEngineService) getPromotionEngineService()).updatePromotions(getTestPromoGroups(), cart);
		BigDecimal promotionPrice = new BigDecimal(String.valueOf(cart.getTotalPrice().doubleValue()));
		logCartDetails(cart);

		if (nonNull(scale))
		{
			promotionPrice = promotionPrice.setScale(scale.intValue(), RoundingMode.HALF_UP);
		}
		return promotionPrice;
	}

	protected void logCartDetails(final CartModel cart)
	{
		LOG.info("Cart details:");
		LOG.info("Totals: {}", cart.getTotalPrice());
		LOG.info("Products:");
		cart.getEntries().forEach(
				e -> LOG.info(" - code : {} , quantity: {}, base price: {}, adjusted total price: {}", e.getProduct().getCode(),
						e.getQuantity(), e.getBasePrice(), e.getTotalPrice()));
		if (isNotEmpty(cart.getGlobalDiscountValues()))
		{
			LOG.info("Global Discounts");
			cart.getGlobalDiscountValues().forEach(
					d -> LOG.info(" - discount value: {}, applied value: {}, absolute: {}", d.getValue(), d.getAppliedValue(),
							d.isAbsolute()));
		}
		else
		{
			LOG.info("Global Discounts - no global discounts");
		}
		logAppliedPromotions(cart);
	}

	protected void logAppliedPromotions(final CartModel cart)
	{
		if (isNotEmpty(cart.getAllPromotionResults()))
		{
			LOG.info("Calculation with applied promotions: ");
			for (final PromotionResultModel promotion : cart.getAllPromotionResults())
			{
				LOG.info(" - code: " + promotion.getPromotion().getCode());
			}
		}
		else
		{
			LOG.info("Calculation - none of the promotions has been applied ");
		}
	}

	public String getDeliveryModeForCart(final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);
		return cart.getDeliveryMode() != null ? cart.getDeliveryMode().getCode() : null;
	}

	public void setDeliveryModeForCart(final String cartId, final String deliveryModeCode)
	{
		final List<DeliveryModeModel> deliveryModesByCode = deliveryModeDao.findDeliveryModesByCode(deliveryModeCode);
		Assert.assertEquals(1, deliveryModesByCode.size());
		final CartModel cart = getCartByCode(cartId);
		cart.setDeliveryMode(deliveryModesByCode.get(0));
	}

	public AbstractRuleActionRAO evaluatePromotionForProduct(final ProductModel product)
	{
		final RuleEvaluationResult evaluatingResult = promotionEngineService.evaluate(product, getTestPromoGroups());
		final RuleEngineResultRAO result = evaluatingResult.getResult();
		final LinkedHashSet<AbstractRuleActionRAO> actions = result.getActions();
		for (final AbstractRuleActionRAO action : actions)
		{
			if (action.getAppliedToObject() instanceof ProductRAO
					&& product.getCode().equals(((ProductRAO) action.getAppliedToObject()).getCode()))
			{
				return action;
			}
		}
		return null;
	}

	/**
	 * Provides product model for with given product code
	 *
	 * @param productCode
	 *           product code
	 * @return product model
	 */
	public ProductModel getProduct(final String productCode)
	{
		return productService.getProductForCode(productCode);
	}


	public CategoryModel getCategory(final String categoryCode)
	{
		return categoryService.getCategoryForCode(categoryCode);
	}

	public BigDecimal getProductPrice(final ProductModel productModel)
	{
		return getProductPriceWithPromotion(productModel, null);
	}

	public BigDecimal getProductPriceWithPromotion(final ProductModel productModel, final DiscountRAO discount)
	{
		double discountAmount = 0.0;
		if (discount != null && discount.getCurrencyIsoCode() == null)
		{
			discountAmount = discount.getValue().doubleValue();
		}

		return BigDecimal.valueOf(getWebPriceForProduct(productModel).getPriceValue().getValue() * (100.0D - discountAmount)
				/ 100.0D);

	}

	public void checkProductQuantity(final ProductModel product, final String cartId, final int quantity)
	{
		final CartModel cart = getCartByCode(cartId);
		long cartQuantity = 0;
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getProduct().equals(product))
			{
				cartQuantity += entry.getQuantity().longValue();
			}
		}

		Assert.assertEquals(quantity, cartQuantity);

	}

	public void checkThatPriceLessThanFor(final BigDecimal beforePromoPrice, final BigDecimal afterPromoPrice, final String expectedDifferenceValue)
	{
		if (expectedDifferenceValue.contains("\""))
		{
			throw new RuntimeException(String.format("Get rid of invalid quote symbols in %s", expectedDifferenceValue));
		}
		if (expectedDifferenceValue.contains("%"))
		{
			final int percentageDifference = Integer.parseInt(expectedDifferenceValue.replace("%", ""));
			Assert.assertTrue(
					String.format("Before promotion price was %f, after promotions is applied price is %f but expected discount percentage value %s %", beforePromoPrice, afterPromoPrice,
							expectedDifferenceValue),
					beforePromoPrice
							.multiply(
									BigDecimal.valueOf(percentageDifference).divide(BigDecimal.valueOf(100.0), 2,
											BigDecimal.ROUND_UNNECESSARY)).subtract(afterPromoPrice).compareTo(BigDecimal.ZERO) == 0);
		}
		else
		{
			Assert.assertTrue(String.format("Before promotion price was %f, after promotions is applied price is %f but expected discount value %s", beforePromoPrice, afterPromoPrice,
					expectedDifferenceValue),
					beforePromoPrice.subtract(afterPromoPrice).subtract(new BigDecimal(expectedDifferenceValue)).compareTo(BigDecimal.ZERO) == 0);
		}
	}

	public void checkThatDeliveryModeNotDefined(final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);
		Assert.assertTrue("Delivery mode of cart with id '" + cartId + "' but was " + cart.getDeliveryMode(),
				cart.getDeliveryMode() == null);
	}

	public void setCartToUser(final String userUID, final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);
		final UserModel user = getUserService().getUserForUID(userUID);
		cart.setUser(user);
		getModelService().save(cart);
		// avoids that the cart is calculated for the wrong user
		getModelService().refresh(cart);
		JaloSession.getCurrentSession().getSessionContext().setUser((User) getModelService().getSource(user));
	}

	protected PriceInformation getWebPriceForProduct(final ProductModel product)
	{
		validateParameterNotNull(product, "Product model cannot be null");
		final List<PriceInformation> prices = priceService.getPriceInformationsForProduct(product);
		if (CollectionUtils.isNotEmpty(prices))
		{
			PriceInformation minPriceForLowestQuantity = null;
			for (final PriceInformation price : prices)
			{
				if (minPriceForLowestQuantity == null
						|| (((Long) minPriceForLowestQuantity.getQualifierValue("minqtd")).longValue() > ((Long) price
								.getQualifierValue("minqtd")).longValue()))
				{
					minPriceForLowestQuantity = price;
				}
			}
			return minPriceForLowestQuantity;
		}
		return null;
	}

	public void compileRuleIncrementally(final String ruleCode, final String moduleName)
	{
		compileAndDeployRule(ruleCode, moduleName, true);
	}

	public void compileSourceRule(final String ruleCode, final String moduleName)
	{
		compileAndDeployRule(ruleCode, moduleName, false);
	}

	protected void compileAndDeployRule(final String ruleCode, final String moduleName, final boolean enableIncrementalUpdate)
	{
		final AbstractRuleModel rule = getRuleService().getRuleForCode(ruleCode);
		getRuleCompilerService().compile(rule, moduleName);

		final DroolsRuleModel droolsRule = (DroolsRuleModel) getEngineRuleDao().getRuleByCode(ruleCode, moduleName);

		setGlobals(droolsRule);
		final InitializationFuture initializationFuture = getCommerceRuleEngineService().initialize(
				singletonList(getTestRulesModule(singleton(droolsRule))), false, enableIncrementalUpdate)
				.waitForInitializationToFinish();
		logModuleInitialization(initializationFuture);
	}

	public void compileSourceRules(final String ruleCodes, final String moduleName)
	{
		final String[] ruleCodeArray = ruleCodes.split("\\s+", 100);

		if (ruleCodeArray.length > 0)
		{
			final Set<DroolsRuleModel> droolsRules = Sets.newHashSet();
			for (final String ruleCode : ruleCodeArray)
			{
				final AbstractRuleModel rule = getRuleService().getRuleForCode(ruleCode);
				getRuleCompilerService().compile(rule, moduleName);

				final DroolsRuleModel droolsRule = (DroolsRuleModel) getEngineRuleDao().getRuleByCode(ruleCode, moduleName);
				setGlobals(droolsRule);
				droolsRules.add(droolsRule);
			}

			final InitializationFuture initializationFuture = getCommerceRuleEngineService().initialize(
					singletonList(getTestRulesModule(droolsRules)), false, false).waitForInitializationToFinish();
			logModuleInitialization(initializationFuture);
		}
	}

	protected void logModuleInitialization(final InitializationFuture initializationFuture)
	{
		if (isNotEmpty(initializationFuture.getResults()))
		{
			LOG.info("Rule engine initialized with following results: ");
			initializationFuture.getResults().forEach(
					r -> LOG.info(" - result, module name: {}, deployed maven version: {}, is failed: {}, features results: {} ",
							r.getModuleName(), r.getDeployedVersion(), r.isActionFailed(), isNotEmpty(r.getResults()) ? r.getResults()
									.stream().map(ResultItem::getMessage).collect(Collectors.toList()) : " - "));
		}
	}

	public void redeemCouponWithCodeForCart(final String couponCode, final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);
		getCouponService().redeemCoupon(couponCode, cart);
	}

	public void releaseCouponWithCodeForCart(final String couponCode, final String cartId)
	{
		final CartModel cart = getCartByCode(cartId);
		getCouponService().releaseCouponCode(couponCode, cart);
	}

	public void checkOrderUsesCoupon(final String cartId, final String couponCode)
	{
		if (!orderUsesCoupon(cartId, couponCode))
		{
			Assert.fail("Coupon code " + couponCode + " is not used for cart " + cartId);
		}
	}

	public void checkOrderNotUsesCoupon(final String cartId, final String couponCode)
	{
		if (orderUsesCoupon(cartId, couponCode))
		{
			Assert.fail("Coupon code " + couponCode + " is used for cart " + cartId);
		}
	}

	protected boolean orderUsesCoupon(final String cartId, final String couponCode)
	{
		final CartModel cart = getCartByCode(cartId);
		for (final AbstractPromotionActionModel action : getPromotionActionsForCart(cart))
		{
			if (action instanceof AbstractRuleBasedPromotionActionModel)
			{
				final AbstractRuleBasedPromotionActionModel ruleBasedPromotionAction = (AbstractRuleBasedPromotionActionModel) action;
				return CollectionUtils.isNotEmpty(ruleBasedPromotionAction.getUsedCouponCodes())
						&& ruleBasedPromotionAction.getUsedCouponCodes().contains(couponCode);
			}
		}
		return false;
	}

	public BigDecimal getProductTotalFromCart(final String productId, final String cartId)
	{
		BigDecimal productTotal = BigDecimal.valueOf(0);
		final CartModel cart = getCartByCode(cartId);
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getProduct().getCode().equals(productId))
			{
				productTotal = productTotal.add(BigDecimal.valueOf(entry.getTotalPrice().doubleValue()));
			}
		}

		return productTotal;
	}

	public void verifyPromotionActionByType(final String cartId, final String promotionActionType)
	{
		if (verifyCartHasNotPromotionActionType(cartId, promotionActionType))
		{
			Assert.fail("No '" + promotionActionType + "' found in the promotion result for the cart id " + cartId);
		}
	}

	public boolean verifyCartHasNotPromotionActionType(final String cartId, final String promotionActionType)
	{
		return getPromotionActionsForCart(getCartByCode(cartId)).stream().noneMatch(
				action -> getModelService().getModelType(action).equals(promotionActionType));
	}

	public List<AbstractPromotionActionModel> getPromotionActionsForCart(final CartModel cartModel)
	{

		final List<AbstractPromotionActionModel> promotionActions = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(cartModel.getAllPromotionResults()))
		{
			cartModel.getAllPromotionResults().forEach(p -> promotionActions.addAll(p.getAllPromotionActions()));
		}
		return promotionActions;
	}

	public Optional<DroolsKIEBaseModel> getDroolsKieBase(final String baseName)
	{
		final List<DroolsKIEBaseModel> allKIEBases = getDroolsKIEBaseDao().findAllKIEBases();
		return allKIEBases.stream().filter(b -> b.getName().equals(baseName)).findFirst();
	}

	public AbstractRulesModuleModel getRulesModule(final String moduleName)
	{
		return getRulesModuleDao().findByName(moduleName);
	}

	public void setCurrentSite()
	{
		final BaseSiteModel baseSite = baseSiteService.getBaseSiteForUID(PromotionEngineAtddTestsConstants.BASESITE);
		sessionService.setAttribute("currentSite", baseSite);
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	protected RuleEngineService getCommerceRuleEngineService()
	{
		return commerceRuleEngineService;
	}

	protected PromotionEngineService getPromotionEngineService()
	{
		return promotionEngineService;
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	protected PriceService getPriceService()
	{
		return priceService;
	}

	protected RuleEngineContextDao getRuleEngineContextDao()
	{
		return ruleEngineContextDao;
	}

	protected RuleActionService getRuleActionService()
	{
		return ruleActionService;
	}

	protected void setRuleActionService(final RuleActionService ruleActionService)
	{
		this.ruleActionService = ruleActionService;
	}

	protected MediaService getMediaService()
	{
		return mediaService;
	}

	protected UserService getUserService()
	{
		return userService;
	}

	protected RuleService getRuleService()
	{
		return ruleService;
	}

	protected RuleCompilerService getRuleCompilerService()
	{
		return ruleCompilerService;
	}

	protected EngineRuleDao getEngineRuleDao()
	{
		return engineRuleDao;
	}

	protected CouponService getCouponService()
	{
		return couponService;
	}

	protected void setCouponService(final CouponService couponService)
	{
		this.couponService = couponService;
	}

	protected DroolsKIEBaseDao getDroolsKIEBaseDao()
	{
		return droolsKIEBaseDao;
	}

	protected RulesModuleDao getRulesModuleDao()
	{
		return rulesModuleDao;
	}
}
