<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css",
"${springMacroRequestContext.contextPath}/static/public/ringdatatable.css",
"//res.layui.com/layui/dist/css/layui.css"]
></@head>

<body class="pos-r">
<div class="pos-a" style="width:200px;left:0;top:0; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5; overflow:auto;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<#include "/public/datatable.ftl" />
		<@datatable titles=["登录名","所属机构","姓名","备注","是否可用","操作"]>
		<div class="text-c"> 用户名称：
			<input type="hidden" id="parentId" name="officeId" style="width:250px" class="input-text">
			<input type="hidden" id="parentName" name="officeName" style="width:250px" class="input-text">
			<input type="text" name="loginnName" placeholder=" 用户名称" style="width:250px" class="input-text">
			<button class="btn btn-success" onClick="fnSearch()"><i class="Hui-iconfont">&#xe665;</i> 搜索用户</button>
			<button class="btn btn-primary radius" onClick="oper_add('添加用户','${springMacroRequestContext.contextPath}/user/add')"><i class="Hui-iconfont">&#xe600;</i> 添加用户</button>
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
<@ztree id="treeDemo" url="${springMacroRequestContext.contextPath}/office/trees">
</@ztree>
<#include "/public/datatableAjax.ftl" />
<@datatableAjax 
controller="user"
sAjaxSource="${springMacroRequestContext.contextPath}/user/finduser"
aoDataPush=["officeId","name"]
aoColumns=["{'mDataProp':'loginName'}","{'mDataProp':'officeName'}","{'mDataProp':'name'}","{'mDataProp':'remarks'}","{'mDataProp':'enabled'}","{'mDataProp':'oper'}"]
refreshTreeId="treeDemo"
enabled="true"
></@datatableAjax>
<script>
function onClick_treeDemo(event,treeId,treeNodes){
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);
	fnSearch();
}
function oper_tree(obj,id){
	var ly = layer.open({
		type:2,
		title:'授权',
		area:['300px','500px'],
		fix:false,
		maxmin:true,
		shade:0.4,
		content:"${springMacroRequestContext.contextPath}/userrole?id="+id,
		icon: 2,
		btn:['授权','取消'],
		yes:function(index,layero){
			layer.confirm('确认要授权吗？',function(index){
				//子窗口
				var child = $("iframe")[0].contentWindow;
				var treeobj = child.$.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeobj.getCheckedNodes(true);
				var dataStr = "id="+id;
				for(var i=0;i<nodes.length;i++){
					dataStr += "&roleId="+nodes[i].id;
				}
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":"${springMacroRequestContext.contextPath}/userrole/accredit/",
					"data":dataStr,
					"success":function(rd){
						if(rd.code&&rd.code==200){
							layer.msg("授权成功!",{icon:1,time:1000},function(){						
								layer.close(ly);
							});
						}else{
							layer.msg("授权失败："+rd.message+"!",{icon:2,time:5000});
						}
					}
				});
			});
		}
	});
}
</script>
</body>
</html>