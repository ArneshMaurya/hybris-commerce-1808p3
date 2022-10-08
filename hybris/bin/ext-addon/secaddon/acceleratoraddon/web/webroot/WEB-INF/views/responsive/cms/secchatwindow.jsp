<script type="text/javascript">
$( document ).ready(function() {
    var chatBtnID = "${param.chatType}-btnStartChat";
    sap.ui.getCore().attachInit(function () {
        if("${param.chatType}"==="video" && !isChrome && !isFirefox) {
            return;
        }

        // Create the Chat Info section to enter Visitor info
        var oChatInfoLayout = new sap.ui.layout.form.SimpleForm({
            editable : false,
            layout : "ResponsiveGridLayout",
            labelSpanL : 3,
            labelSpanM : 3,
            emptySpanL : 3,
            emptySpanM : 3,
            columnsL : 2,
            columnsM : 2,
            content : [new sap.m.Label(), new sap.m.Button(chatBtnID, {
                text : "${param.chatBtnTitle}",
                width : "100px",
                enabled : false
            }) ]
        });
        oChatInfoLayout.placeAt("chatinfo");

        // When the Session is connected enable the form controls
        ecfSession.attachConnected(function(evt) {
            sap.ui.getCore().byId(chatBtnID).setEnabled(true);
        });

        // Wire up button press
        sap.ui.getCore().byId(chatBtnID).attachPress(startChat);

        // Handlers to start the chat
        function startChat(evt) {
        widgetInteraction.placeAt("interactions");

        ecfSession.initiateChat({
            chat_address: CHAT_CONFIG.customerEmail,
            alias : CHAT_CONFIG.customerName,
            queue : CHAT_CONFIG.queue,
            subChannelType : "${param.chatType}"
        });
            $("#chat-wrapper").dialog('open');
        };

        // Handler for when Chat is queued to visitor
        widgetInteraction.attachQueuedInteraction(function(evt) {
            // Disable form elements because we are already in a chat
            sap.ui.getCore().byId(chatBtnID).setEnabled(false);
            var oInteraction = evt.getParameter("oInteraction");
            var oTab = widgetInteraction.getInteractionTabByID(oInteraction.id);
        });

        // Handler for when Chat ends
        widgetInteraction.attachRemovingInteraction(function(evt) {
        // Destroy the Session connection to ECFS
        ecfSession.disconnect();
            $("#chat-wrapper").dialog('close');
        });

        // When the Session is destroyed, create a new Session and connect again
        ecfSession.attachDisconnected(function(evt) {
            ecfSession.connect();
        });

        $("#chat-wrapper").dialog({
            'title': 'Service Engagement Center',
            autoOpen: false,
            height: '500',
            width: '590',
            resizable: false,
            scrollable: "no",
            closeOnEscape: false,
            close: function(event, ui) {
                ecfSession.disconnect();
            }
        });
    });
});
</script>
