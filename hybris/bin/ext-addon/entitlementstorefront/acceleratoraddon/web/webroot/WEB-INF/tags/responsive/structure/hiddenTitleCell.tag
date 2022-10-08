<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="titleCode" required="true" type="java.lang.String" %>
<%@ attribute name="titleDefaultText" required="true" type="java.lang.String" %>

<spring:htmlEscape defaultHtmlEscape="true"/>

<td class="hidden-cell">
	<spring:theme code="${titleCode}" text="${titleDefaultText}"/>
</td>
<td>
	<jsp:doBody/>
</td>

