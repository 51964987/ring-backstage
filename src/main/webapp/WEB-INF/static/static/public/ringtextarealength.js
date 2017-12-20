!function($){
	$.ringtextarealength = function(obj,maxlength){
		var v = $(obj).val();
		var l = v.length;
		if(l > maxlength){
			v = v.substring(0,maxlength);
			$(obj).val(v);
		}
		$(obj).parent().find(".textarea-length").text(v.length);
	}
}(window.jQuery)