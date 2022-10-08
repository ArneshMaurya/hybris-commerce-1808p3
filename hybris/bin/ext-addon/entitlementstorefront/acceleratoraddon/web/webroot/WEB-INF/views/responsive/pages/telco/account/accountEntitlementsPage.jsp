<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="structure" tagdir="/WEB-INF/tags/addons/entitlementstorefront/responsive/structure" %>

<%--@elvariable id="grants" type="java.util.Collection<de.hybris.platform.entitlementfacades.data.EntitlementData>"--%>

<spring:htmlEscape defaultHtmlEscape="true"/>

<div class="account-section-header"><spring:theme code="text.account.entitlements" text="Access & Entitlements"/></div>

<c:choose>
	<c:when test="${empty grants}">
		<div class="account-section-content content-empty">
			<spring:theme code="text.account.entitlements.noEntitlements" text="You have no entitlements"/>
		</div>
	</c:when>
	<c:otherwise>
		<div class="account-section-content">
			<div class="account-orderhistory">
				<div class="account-overview-table">
					<table class="subscriptions-table">
						<tr class="responsive-table-head">
							<th><spring:theme code="text.account.entitlements.entitlementName" text="Entitlement Name"/></th>
							<th><spring:theme code="text.account.entitlements.date.start" text="Start Date"/></th>
							<th><spring:theme code="text.account.entitlements.date.end" text="End Date"/></th>
							<th><spring:theme code="text.account.entitlements.status" text="Status"/></th>
						</tr>

						<c:forEach items="${grants}" var="grant">
							<tr class="entitlements-item">
								<structure:hiddenTitleCell titleCode="text.account.entitlements.entitlementName"
																	titleDefaultText="Entitlement Name">
									${ycommerce:encodeHTML(grant.name)}
								</structure:hiddenTitleCell>

								<structure:hiddenTitleCell titleCode="text.account.entitlements.date.start" titleDefaultText="Start Date">
									<fmt:formatDate value="${grant.startTime}" dateStyle="long" timeStyle="short" type="date"/>
								</structure:hiddenTitleCell>

								<structure:hiddenTitleCell titleCode="text.account.entitlements.date.end" titleDefaultText="End Date">
									<c:choose>
										<c:when test="${empty grant.endTime}">
											<spring:theme code="text.account.entitlements.date.end.unlimited" text="Unlimited"/>
										</c:when>
										<c:otherwise>
											<fmt:formatDate value="${grant.endTime}" dateStyle="long" timeStyle="short" type="date"/>
										</c:otherwise>
									</c:choose>
								</structure:hiddenTitleCell>

								<structure:hiddenTitleCell titleCode="text.account.entitlements.status" titleDefaultText="Status">
									${ycommerce:encodeHTML(grant.status)}
								</structure:hiddenTitleCell>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
