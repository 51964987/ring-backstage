<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css","${springMacroRequestContext.contextPath}/static/public/ringdatatable.css"]
></@head>
<body >
<div >
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 操作日志管理 <span class="c-gray en">&gt;</span> 操作日志列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<#include "/public/datatable.ftl" />
		<@datatable titles=["请求地址","请求时间","请求参数","日志状态","日志状态信息","异常信息","操作"]>
		<div class="text-c"> 操作日志名称：
			<input type="hidden" id="parentId" name="parentId" style="width:250px" class="input-text">
			<input type="hidden" id="parentName" name="parentName" style="width:250px" class="input-text">
			<input type="text" name="name" placeholder=" 操作日志名称" style="width:250px" class="input-text">
			<button class="btn btn-success" onClick="fnSearch()"><i class="Hui-iconfont">&#xe665;</i> 搜索操作日志</button>
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
<#include "/public/datatableAjax.ftl" />
<@datatableAjax 
controller="log"
sAjaxSource="${springMacroRequestContext.contextPath}/log/findlog"
aoDataPush=["parentId","name"]
aoColumns=["{'mDataProp':'uri'}","{'mDataProp':'createDate'}","{'mDataProp':'reqParams','width':'250px'}","{'mDataProp':'code'}","{'mDataProp':'message'}","{'mDataProp':'errorMsg','width':'250px'}","{'mDataProp':'oper'}"]
refreshTreeId="treeDemo"
></@datatableAjax>
</body>
</html>