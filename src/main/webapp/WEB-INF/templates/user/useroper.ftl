
<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"]
></@head >
<body>
<#assign id="" />
<#assign officeId="" />
<#if user?exists>
<#assign id="${user.id}" />
<#assign officeId="${user.officeId}" />
</#if>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-user">
		<input type="hidden" class="input-text" <#if user?exists>value="${user.id}"</#if> placeholder="" name="id">
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" <#if user?exists>readonly="readonly" value="${user.loginName}"</#if> placeholder="" name="loginName">
			</div>
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" <#if user?exists>value="${user.name}"</#if> placeholder="" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2">所属机构：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" readonly="readonly" <#if user?exists && (user.officeName)?exists >value="${user.officeName}"</#if> placeholder="" name="officeName" id="officeName">
				<input type="hidden" class="input-text" <#if user?exists>value="${user.officeId}"</#if> placeholder="" name="officeId" id="officeId">
			</div>
			<label class="form-label col-xs-2 col-sm-2">用户类型：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<span class="select-box">
				<select class="select" size="1" name="userType">
					<option value="" selected>请选择</option>
					<option value="2" <#if user?exists && (user.userType)?exists && user.userType == "2">selected</#if>>业务用户</option>
					<option value="1" <#if user?exists && (user.userType)?exists && user.userType == "1">selected</#if>>管理用户</option>
				</select>
				</span>
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
		$("#officeId").val(parent.$("#parentId").val());
		$("#officeName").val(parent.$("#parentName").val());
		</#if>
	</#if>
	$("#form-user").validate({
		rules:{
			name:{
				required:true
			},
			loginName:{
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
				"url":"${springMacroRequestContext.contextPath}/user/${url}/",
				"data":$("#form-user").serialize(),
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
selectId="officeName"
checkEnable="true"
checkRadio="true"
otherParam=["\"parentId\"","\"${officeId}\""]
>
</@ztree>
<script>
/*单击选中*/
function onClick_treeDemo(event,treeId,treeNodes){
	beforeCheckCallback_treeDemo(treeId,treeNodes);
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
	$("#officeId").val(treeNodes.id);
	$("#officeName").val(treeNodes.name);	
}
</script>
</body>
</html>