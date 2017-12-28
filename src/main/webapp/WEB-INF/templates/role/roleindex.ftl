<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css","${springMacroRequestContext.contextPath}/static/public/ringdatatable.css"]
></@head>
<body >
<div >
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 角色管理 <span class="c-gray en">&gt;</span> 角色列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<#include "/public/datatable.ftl" />
		<@datatable titles=["角色名称","排序","备注","是否可用","操作"]>
		<div class="text-c"> 角色名称：
			<input type="text" name="name" placeholder=" 角色名称" style="width:250px" class="input-text">
			<button class="btn btn-success" onClick="fnSearch()"><i class="Hui-iconfont">&#xe665;</i> 搜索角色</button>
			<button class="btn btn-primary radius" onClick="oper_add('添加角色','${springMacroRequestContext.contextPath}/role/add')"><i class="Hui-iconfont">&#xe600;</i> 添加角色</button>
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
controller="role"
sAjaxSource="${springMacroRequestContext.contextPath}/role/findrole"
aoDataPush=["parentId","name"]
aoColumns=["{'mDataProp':'name'}","{'mDataProp':'sort'}","{'mDataProp':'remarks'}","{'mDataProp':'enabled'}","{'mDataProp':'oper'}"]
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
		title:'分配资源',
		area:['300px','500px'],
		fix:false,
		maxmin:true,
		shade:0.4,
		content:"${springMacroRequestContext.contextPath}/roleresource?id="+id,
		icon: 2,
		btn:['分配资源','取消'],
		yes:function(index,layero){
			layer.confirm('确认要保存吗？',function(index){
				//子窗口
				var child = $("iframe")[0].contentWindow;
				var treeobj = child.$.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeobj.getCheckedNodes(true);
				var dataStr = "id="+id;
				for(var i=0;i<nodes.length;i++){
					dataStr += "&resourceId="+nodes[i].id;
				}
				$.ajax({
					"dataType":"json",
					"type":"post",
					"url":"${springMacroRequestContext.contextPath}/roleresource/accredit/",
					"data":dataStr,
					"success":function(rd){
						if(rd.code&&rd.code==200){
							layer.msg("保存成功!",{icon:1,time:1000},function(){						
								layer.close(ly);
							});
						}else{
							layer.msg("保存失败："+rd.message+"!",{icon:2,time:5000});
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