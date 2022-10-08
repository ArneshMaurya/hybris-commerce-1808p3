<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<script type="text/javascript">
   
   if (window.SapEcfClient && window.SapEcfClient.destroy) {
	   window.SapEcfClient.destroy();
	 }
 var map = new Map();
 map.set("Anonymous","Anonymous");

 
	 window.SapEcfClient = {
	   "options": {
	     "configuration": {
	       "cctrUrl": "${chatCctrUrl}",
	       "showLogin": true,
	       "alias": "${customerName}",
	       "address": "${customerEmail}",
	       
	       "chatQueues": [
	         {
	           "text": "CEC chat",
	           "key": "${chatQueue}"
	         }
	       ],
	       "enableVideo": "${videoChatEnabled}"
	     },
	     "visitorUri": "${chatEcfModulePath}"
	   }
	 }
	 
	 window.SapEcfClient.loadToDom = function () {
	   var oElem = document.createElement("div");
	   oElem.id = "sap-ecf-client";
	   var oScript = document.createElement("script");
	   oScript.src = window.SapEcfClient.options.visitorUri + "/Intermediate.js";
	   oElem.appendChild(oScript);
	   document.body.appendChild(oElem);
	 };
	 window.onload=function(){
	 	window.SapEcfClient.loadToDom();
	 };
</script>

