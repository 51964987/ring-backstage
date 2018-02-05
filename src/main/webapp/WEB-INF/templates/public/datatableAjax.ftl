<#macro datatableAjax
sAjaxSource=""
controller=""
add="true"
delete="true"
update="true"
enabled="false"
aoColumns=[]
aoDataPush=[]
refreshTreeId=""
>
<script type="text/javascript">

<#if add="true">
/*添加*/
function oper_add(title,url,w,h){
	layer_show(title,url+"?oper=add",w,h);
}
</#if>

<#if delete="true">
/*删除*/
function oper_delete(obj,id){
	layer.confirm("确认要删除吗？",function(index){
		$.ajax({
			"dataType":"json",
			"type":"POST",
			"url":"${springMacroRequestContext.contextPath}/${controller}/delete/"+id,
			"success":function(rd){
				if(rd.code&&rd.code==200){
					layer.msg("已删除!",{icon:1,time:1000},function(){						
						fnSearch();
						<#if refreshTreeId != "">
						if(document.fnRefreshTree_${refreshTreeId} != null){
							fnRefreshTree_${refreshTreeId}();
						}
						</#if>
					});
				}else{
					layer.msg("失败："+rd.message+"!",{icon:2,time:5000});
				}
			}
		});
	});
}
</#if>

<#if enabled="true">
/*启用/停用*/
function oper_enabled(obj,url){
	var title = $(obj).attr("title");
	layer.confirm("确认要"+title+"吗？",function(index){
		$.ajax({
			"dataType":"json",
			"type":"POST",
			"url":url,
			"success":function(rd){
				if(rd.code&&rd.code==200){
					layer.msg("已"+title+"!",{icon:1,time:1000},function(){						
						fnSearch();
						<#if refreshTreeId != "">
						if(document.fnRefreshTree_${refreshTreeId} != null){
							fnRefreshTree_${refreshTreeId}();
						}
						</#if>
					});
				}else{
					layer.msg("失败："+rd.message+"!",{icon:2,time:5000});
				}
			}
		});
	});
}
</#if>

<#if update="true">
/*修改*/
function oper_edit(title,url,w,h){
	layer_show(title,url+"?oper=edit",w,h);
}
</#if>

<#if update="true">
/*查看*/
function oper_detail(obj,id){
	var shallowEncoded = $.param(curPageData[id], true);
	var shallowDecoded = decodeURIComponent(shallowEncoded);
	//alert(shallowEncoded);
	//alert(shallowDecoded);
}
</#if>

/*搜索*/
function fnSearch(){
	$('.table-sort').dataTable().fnDraw(true);
}
var otable;
var curPageData={};
$(document).ready(function(){
	$.getJSON("${springMacroRequestContext.contextPath}/static/public/ajax-datatable.json",function(json){
			otable = $('.table-sort').dataTable($.extend(json,{
			 "sAjaxSource" : "${sAjaxSource}",
			 "sDom":"<\"H\">t<\"F\"ifl>p",
			 "aoColumns" : [<#list aoColumns as j><#if j_index &gt; 0>,</#if>${j}</#list>],
	         fnServerData:function(sSource,aoData,fnCallback){
				<#list aoDataPush as j>
				aoData.push({"name":"${j}","value":$("[name='${j}']").val()});
				</#list>
				curPageData={};
				$.ajax({
					"dataType":"json",
					"type":"POST",
					"url":sSource,
					"data":aoData,
					"success":fnCallback
				});
	         },
	         fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
	         	$(nRow).removeClass("odd");
				$(nRow).removeClass("even");
				$(nRow).addClass("text-c");
				curPageData[aData.id]=aData;
	         }  
		}));  
	});
});
</script>
</#macro>