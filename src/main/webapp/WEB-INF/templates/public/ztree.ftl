<#macro ztree
id=""
url=""
nameIsHTML="true"
showLine="true"
showIcon="true"
showTile="true"
selectedMulti="true"
autoParam=["'id=parentId'"]
checkEnable="false"
checkRadio="false"
select="false"
selectId=""
otherParam=[]
>
<#if select == "true">
<div id="menuContent_${id}" class="menuContent" style="display:none; position: absolute;background-color:white;border:1px solid #ccc;">
	<ul id="${id}" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>
</#if>
<script type="text/javascript">
$(document).ready(function(){
	var setting = {
		async:{
			enable:true,
			type:"post",
			url:"${url}",
			autoParam:[<#list autoParam as o><#if o_index &gt; 0>,</#if>${o}</#list>],
			otherParam:[<#list otherParam as o><#if o_index &gt; 0>,</#if>${o}</#list>]
		},
		<#if checkEnable == "true">
		check:{
			enable:true<#if checkRadio == 'true'>,
			chkStyle:"radio"
			</#if>
		},
		</#if>
		view:{
			dblClickExpand: false,
			nameIsHTML:${nameIsHTML},
			showLine: ${showLine},
			showIcon:${showIcon},
			showTile:${showTile},
			selectedMulti: ${selectedMulti}
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			onAsyncSuccess:onAsyncSuccess_${id},
			beforeClick:beforeClick_${id},
			onClick:onClick_${id},
			beforeExpand:beforeExpand_${id},
			beforeCheck:beforeCheck_${id}
		}
	};	
	$.fn.zTree.init($("#${id}"), setting);
});
function onAsyncSuccess_${id}(event,treeId,treeNodes){
	<#if checkEnable=="true">
	var treeobj = $.fn.zTree.getZTreeObj("${id}");
	treeobj.setting.async.enable=false;
	treeobj.expandAll(true);
	</#if>
}
function beforeClick_${id}(treeId,treeNode){	
}
function onClick_${id}(event,treeId,treeNodes){
}
/*展开节点*/
function beforeExpand_${id}(treeId,treeNode){
	if(!treeNode.isAjaxing){
		var treeobj = $.fn.zTree.getZTreeObj("${id}");
		treeobj.updateNode(treeNode);
		treeobj.reAsyncChildNodes(treeNode,"refresh",true);
		return true;
	}else{
		layer.msg("正在下载数据中，请稍后展开节点...",{icon:2,time:5000});
		return false;
	}	
}
function beforeCheck_${id}(treeId,treeNode){
	<#if checkRadio == "true">
	/*radio单选*/
	var treeobj = $.fn.zTree.getZTreeObj("${id}");
	var checkNodes = treeobj.getCheckedNodes(true);
	if(checkNodes.length>0){
		for(var i=0;i<checkNodes.length;i++){
			treeobj.checkNode(checkNodes[i],false,false);//不选
		}
	}
	beforeCheckCallback_${id}(treeId,treeNode);
	</#if>
}
/*radio单选回调*/
function beforeCheckCallback_${id}(treeId,treeNode){
	
}
/*刷新当前节点*/
function fnRefreshTree_${id}(){
	var treeobj = $.fn.zTree.getZTreeObj("${id}");
	var selNodes = treeobj.getSelectedNodes();
	var selNodeTree = selNodes[0];
	if(selNodeTree!=null&&selNodeTree.id=="-1"){//根节点
		selNodeTree = null;
	}
	treeobj.reAsyncChildNodes(selNodeTree,"refresh",false);
	//展开
	treeobj.expandAll(true);
}
<#if select == "true">
$(function(){
	$("#${selectId}").on("click",function(){
		showMenu_${id}();
	});
	$("#menuContent_${id}").css("width",$("#${selectId}").css("width"));
});
function showMenu_${id}() {
	var cityObj = $("#${selectId}");
	var cityOffset = $("#${selectId}").offset();
	$("#menuContent_${id}").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown_${id});
}
function hideMenu_${id}() {
	$("#menuContent_${id}").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown_${id});
}
function onBodyDown_${id}(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_${id}" || $(event.target).parents("#menuContent_${id}").length>0)) {
		hideMenu_${id}();
	}
}
</#if>
</script>
</#macro>