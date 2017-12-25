<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css","${springMacroRequestContext.contextPath}/static/public/ringdatatable.css"]
></@head>

<body class="pos-r">
<div class="pos-a" style="width:200px;left:0;top:0; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5; overflow:auto;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 资源管理 <span class="c-gray en">&gt;</span> 资源列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<#include "/public/datatable.ftl" />
		<@datatable titles=["资源名称","上级编号","排序","备注","是否后台应用","操作"]>
		<div class="text-c"> 资源名称：
			<input type="hidden" id="parentId" name="parentId" style="width:250px" class="input-text">
			<input type="hidden" id="parentName" name="parentName" style="width:250px" class="input-text">
			<input type="text" name="name" placeholder=" 资源名称" style="width:250px" class="input-text">
			<button class="btn btn-success" onClick="fnSearch()"><i class="Hui-iconfont">&#xe665;</i> 搜索资源</button>
			<button class="btn btn-primary radius" onClick="oper_add('添加资源','${springMacroRequestContext.contextPath}/resource/add')"><i class="Hui-iconfont">&#xe600;</i> 添加资源</button>
		</div>
		</@datatable>
	</div>
</div>
<#include "/public/footer.ftl" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/laypage/1.2/laypage.js"></script>

<#include "/public/ztree.ftl" />
<@ztree id="treeDemo" url="${springMacroRequestContext.contextPath}/resource/trees">
</@ztree>
<#include "/public/datatableAjax.ftl" />
<@datatableAjax 
controller="resource"
sAjaxSource="${springMacroRequestContext.contextPath}/resource/findresource"
aoDataPush=["parentId","name"]
aoColumns=["{'mDataProp':'name'}","{'mDataProp':'parentName'}","{'mDataProp':'sort'}","{'mDataProp':'remarks'}","{'mDataProp':'backFlag'}","{'mDataProp':'oper'}"]
refreshTreeId="treeDemo"
></@datatableAjax>
<script>
function onClick_treeDemo(event,treeId,treeNodes){
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);
	fnSearch();
}
</script>
</body>
</html>