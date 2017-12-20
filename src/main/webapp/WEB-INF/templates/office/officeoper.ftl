
<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"]
></@head>
<body>
<#assign id="" />
<#assign parentId="" />
<#if office?exists>
<#assign id="${office.id}" />
<#assign parentId="${office.parentId}" />
</#if>

<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-office">
		<input type="hidden" class="input-text" <#if office?exists>value="${office.id}"</#if> placeholder="" name="id">
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>机构名称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" <#if office?exists>value="${office.name}"</#if> placeholder="" name="name">
			</div>
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>排序：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="number" min="1" class="input-text" style="width:100%" <#if office?exists>value="${(office.sort)?c}"</#if> placeholder="" name="sort">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2">上级机构：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" readonly="readonly" <#if office?exists><#if (office.parentName)?exists>value="${office.parentName}"</#if></#if> placeholder="" name="parentName" id="parentName">
				<input type="text" class="input-text" readonly="readonly" <#if office?exists>value="${office.parentId}"</#if> placeholder="" name="parentId" id="parentId">
			</div>
			<label class="form-label col-xs-2 col-sm-2">&nbsp;</label>
			<div class="formControls col-xs-4 col-sm-4">
				&nbsp;
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2">备注：</label>
			<div class="formControls col-xs-10 col-sm-10">
				<textarea name="remarks" cols="" rows="" class="textarea"  placeholder="说点什么..." onKeyUp="$.ringtextarealength(this,255)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/255</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-7 col-sm-8 col-xs-offset-3 col-sm-offset-2">
				<button type="submit" class="btn btn-success radius"><i class="icon-ok"></i> 保存</button>
			</div>
		</div>
	</form>
</article>


<#include "/public/footer.ftl" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript">
$(function(){
	<#if RequestParameters['oper']?exists>
		<#if "add" == RequestParameters['oper']>
		$("#parentId").val(parent.$("#parentId").val());
		$("#parentName").val(parent.$("#parentName").val());
		</#if>
	</#if>
	
	$("#form-office").validate({
		rules:{
			name:{
				required:true
			},
			sort:{
				required:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$.ajax({
				"dataType":"json",
				"type":"post",
				"url":"${springMacroRequestContext.contextPath}/office/${url}/",
				"data":$("#form-office").serialize(),
				"success":function(rd){
					if(rd.code&&rd.code==200){
						layer.msg("保存成功!",{icon:1,time:1000},function(){						
							parent.fnSearch();
							if(parent.fnRefreshTree_treeDemo != null){
								parent.fnRefreshTree_treeDemo();
							}
							layer_close();
						});
					}else{
						layer.msg("失败："+rd.message+"!",{icon:2,time:5000});
					}
				}
			});
		}
	});
});
</script>
<#include "/public/ztree.ftl" />
<@ztree id="treeDemo" 
url="${springMacroRequestContext.contextPath}/office/selecttree"
select="true"
selectId="parentName"
checkEnable="true"
checkRadio="true"
autoParam=[]
otherParam=["\"id\"","\"${id}\"","\"parentId\"","\"${parentId}\""]
>
</@ztree>
<script>
/*单击选中*/
function onClick_treeDemo(event,treeId,treeNodes){
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);
	var treeobj = $.fn.zTree.getZTreeObj("treeDemo");
	var checkNodes = treeobj.getCheckedNodes(true);
	if(checkNodes.length>0){
		for(var i=0;i<checkNodes.length;i++){
			treeobj.checkNode(checkNodes[i],false,false);//不选
		}
	}
	treeobj.checkNode(treeNodes,true,false);//不选
}
/*radio勾选选中*/
function beforeCheckCallback_treeDemo(treeId,treeNodes){
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);	
}
</script>
</body>
</html>