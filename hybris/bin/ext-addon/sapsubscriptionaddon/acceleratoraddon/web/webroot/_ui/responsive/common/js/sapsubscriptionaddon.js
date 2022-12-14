if ($("#quickOrder").length > 0) {
ACC.quickorder.getAndDisplayProductInfo = function (event, parentLi, productCode) {
    var url = ACC.config.encodedContextPath + '/quickOrder/productInfo?code=' + productCode;
    $.getJSON(url, function (result) {
        if (result.errorMsg != null && result.errorMsg.length > 0) {
            $(event.target).addClass(ACC.quickorder.$classHasError);
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text(result.errorMsg);
        }
        else {
            $(event.target).removeClass(ACC.quickorder.$classHasError);
            ACC.quickorder.findElement(parentLi, ACC.quickorder.$skuValidationContainer).text('');
            $('#quickOrderRowTemplate').tmpl(result.productData).insertAfter(ACC.quickorder.findElement(parentLi, '.js-sku-container'));
            var qtyInputField = ACC.quickorder.findElement(parentLi, ACC.quickorder.$qtyInputField);
            qtyInputField.focusout(ACC.quickorder.handleFocusOutOnQtyInput).keydown(ACC.quickorder.handleFocusOutOnQtyInput);
            var stockLevelStatus = result.productData.stock.stockLevelStatus.code;
            if(result.productData.subscriptionTerm != null)
            {
            	qtyInputField.prop('disabled', true);
            }
            if (stockLevelStatus === "outOfStock") {
                qtyInputField.val(0);
                qtyInputField.prop('disabled', true);
            }
            else {
            	qtyInputField.focus().select();
            }
            ACC.quickorder.enableDisableAddToCartBtn();
        }
    });
} }


ACC.autocomplete.bindSearchAutocomplete = function ()
{
	// extend the default autocomplete widget, to solve issue on multiple instances of the searchbox component
	$.widget( "custom.yautocomplete", $.ui.autocomplete, {
		_create:function(){
			
			// get instance specific options form the html data attr
			var option = this.element.data("options");
			// set the options to the widget
			this._setOptions({
				minLength: option.minCharactersBeforeRequest,
				displayProductImages: option.displayProductImages,
				delay: option.waitTimeBeforeRequest,
				autocompleteUrl: option.autocompleteUrl,
				source: this.source
			});
			
			// call the _super()
			$.ui.autocomplete.prototype._create.call(this);
			
		},
		options:{
			cache:{}, // init cache per instance
			focus: function (){return false;}, // prevent textfield value replacement on item focus
			select: function (event, ui){
				ui.item.value = ACC.sanitizer.sanitize(ui.item.value, false);
                window.location.href = ui.item.url;
            }
		},
		_renderItem : function (ul, item){
			
			if (item.type === "autoSuggestion"){
				var renderHtml = "<a href='"+ item.url + "' ><div class='name'>" + item.value + "</div></a>";
				return $("<li>")
						.data("item.autocomplete", item)
						.append(renderHtml)
						.appendTo(ul);
			}
			else if (item.type ==="productResult"){

				var renderHtml = "<a href='" + item.url + "' >";

				if (item.image != null){
					renderHtml += "<div class='thumb'><img src='" + item.image + "'  /></div>";
				}

				renderHtml += 	"<div class='name'>" + item.value +"</div>";
				renderHtml += 	"<div class='price'>" + item.price +"</div>";
				renderHtml += 	"</a>";

				return $("<li>").data("item.autocomplete", item).append(renderHtml).appendTo(ul);
			}
		},
		source: function (request, response)
		{
			var self=this;
			var term = request.term.toLowerCase();
			if (term in self.options.cache)
			{
				return response(self.options.cache[term]);
			}

			$.getJSON(self.options.autocompleteUrl, {term: request.term}, function (data)
			{
				var autoSearchData = [];
				if(data.suggestions != null){
					$.each(data.suggestions, function (i, obj)
					{
						autoSearchData.push({
							value: obj.term,
							url: ACC.config.encodedContextPath + "/search?text=" + obj.term,
							type: "autoSuggestion"
						});
					});
				}
				if(data.products != null){
					$.each(data.products, function (i, obj)
					{
						autoSearchData.push({
							value: ACC.sanitizer.sanitize(obj.name),
							code: obj.code,
							desc: ACC.sanitizer.sanitize(obj.description),
							manufacturer: ACC.sanitizer.sanitize(obj.manufacturer),
							url:  ACC.config.encodedContextPath + obj.url,
							price: obj.price.oneTimeChargeEntries[0].price.formattedValue,
							type: "productResult",
							image: (obj.images!=null && self.options.displayProductImages) ? obj.images[0].url : null // prevent errors if obj.images = null
						});
					});
				}
				self.options.cache[term] = autoSearchData;
				return response(autoSearchData);
			});
		}

	});


	$search = $(".js-site-search-input");
	if($search.length>0){
		$search.yautocomplete()
	}

}

$(function(){

    $("#toDate").datepicker({
    	changeMonth: true,
		changeYear: true,
		yearRange: '2015:2025',
    	dateFormat: 'yy-mm-dd' 
    });
    
    $("#fromDate").datepicker({ 
    	changeMonth: true,
		changeYear: true,
		yearRange: '2015:2025',
    	dateFormat: 'yy-mm-dd' 
    		}).bind("change",function(){
        var minValue = $(this).val();
        minValue = $.datepicker.parseDate("yy-mm-dd", minValue);
        minValue.setDate(minValue.getDate()+1);
        $("#toDate").datepicker( "option", "minDate", minValue );
    })
 

});

$(document).ready(function() {
	
	
	$("#rcSubUnlimited").click(function() {
		if ($(this).prop("checked") === true) {
			$('#extensionPeriod').attr("disabled", "disabled");
		} else if ($(this).prop("checked") === false) {
			$('#extensionPeriod').removeAttr("disabled");
		}
	});
	

    $(document).on("click","#cancelRCSubscription", function(e){
        e.preventDefault();
        var url = $(this).data("cancelSubUrl");
      
        ACC.colorbox.open('Cancel Subscription',{
            href: url,
            maxWidth:"100%",
            width:"380px",
            initialWidth :"380px"
        });
    });

    $(document).on("click",".js-mini-cart-close-button", function(e){
        e.preventDefault();
        ACC.colorbox.close();
    });
    
        $('#fromDate').datepicker({
            dateFormat: 'dd-mm-yy',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd'
        });
        
        $('#toDate').datepicker({
            dateFormat: 'dd-mm-yy',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd'
        });

    
});


