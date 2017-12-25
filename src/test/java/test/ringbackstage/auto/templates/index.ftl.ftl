<!DOCTYPE HTML>
<html>
<${r'#include'} "/public/head.ftl" />
${r'<@'}head 
links=["${r'${springMacroRequestContext.contextPath}'}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css","${r'${springMacroRequestContext.contextPath}'}/static/public/ringdatatable.css"]
>${r'</@'}head>

<body class="pos-r">
<div class="pos-a" style="width:200px;left:0;top:0; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5; overflow:auto;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> ${modelName}管理 <span class="c-gray en">&gt;</span> ${modelName}列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<${r'#include'} "/public/datatable.ftl" />
		${r'<@'}datatable titles=[<#list cols as col><#if col_index &gt; 0>,</#if>"${col.REMARKS}"</#list>]>
		<div class="text-c"> ${modelName}名称：
			<input type="hidden" id="parentId" name="parentId" style="width:250px" class="input-text">
			<input type="hidden" id="parentName" name="parentName" style="width:250px" class="input-text">
			<input type="text" name="name" placeholder=" ${modelName}名称" style="width:250px" class="input-text">
			<button class="btn btn-success" onClick="fnSearch()"><i class="Hui-iconfont">&#xe665;</i> 搜索${modelName}</button>
			<button class="btn btn-primary radius" onClick="oper_add('添加${modelName}','${r'${springMacroRequestContext.contextPath}'}/${model}/add')"><i class="Hui-iconfont">&#xe600;</i> 添加${modelName}</button>
		</div>
		${r'</@'}datatable>
	</div>
</div>
<${r'#include'} "/public/footer.ftl" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/laypage/1.2/laypage.js"></script>

<${r'#include'} "/public/ztree.ftl" />
${r'<@'}ztree id="treeDemo" url="${r'${springMacroRequestContext.contextPath}'}/${model}/trees">
${r'</@'}ztree>
<${r'#include'} "/public/datatableAjax.ftl" />
${r'<@'}datatableAjax 
controller="${model}"
sAjaxSource="${r'${springMacroRequestContext.contextPath}'}/${model}/find${model}"
aoDataPush=["parentId","name"]
aoColumns=[<#list cols as col><#if col_index &gt; 0>,</#if>"{'mDataProp':'${col.FIELD_NAME}'}"</#list>]
refreshTreeId="treeDemo"
>${r'</@'}datatableAjax>
<script>
function onClick_treeDemo(event,treeId,treeNodes){
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);
	fnSearch();
}
</script>
</body>
</html>