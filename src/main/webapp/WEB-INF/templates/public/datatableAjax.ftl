<#macro datatableAjax
sAjaxSource=""
controller=""
add="true"
delete="true"
update="true"
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

<#if update="true">
/*修改*/
function oper_edit(title,url,w,h){
	layer_show(title,url+"?oper=edit",w,h);
}
</#if>

/*搜索*/
function fnSearch(){
	$('.table-sort').dataTable().fnDraw(true);
}
var otable;
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
	         }  
		}));  
	});
});
</script>
</#macro>