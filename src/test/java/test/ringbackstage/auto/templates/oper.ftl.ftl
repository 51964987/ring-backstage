
<!DOCTYPE HTML>
<html>
<${r'#include'} "/public/head.ftl" />
<${r'@head '}
links=["${r'${springMacroRequestContext.contextPath}'}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"]
></${r'@head '}>
<body>
<#assign id="" />
<#assign parentId="" />
<${r'#if'} ${model}?exists>
<#assign id="${r'${'}${model}${r'.id}'}" />
</${r'#if'}>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-${model}">
		<input type="hidden" class="input-text" <${r'#if'} ${model}?exists>value="${r'${'}${model}${r'.id}'}"</${r'#if'}> placeholder="" name="id">
		<#list cols as col>
		<#if (col_index+1) % 2 == 1>
		<#if col_index &gt; 0>
		</div>
		</#if>
		<div class="row cl">
		</#if>
			<label class="form-label col-xs-2 col-sm-2"><#if col.IS_NULLABLE == "NO"><span class="c-red">*</span></#if>${col.REMARKS}：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" <${r'#if'} ${model}?exists>value="${r'${'}${model}.${col.FIELD_NAME}}"</${r'#if'}> placeholder="" name="${col.FIELD_NAME}">
			</div>
		<#if cols?size-1 == col_index>
		</div>
		</#if>
		</#list>
		
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


<${r'#include'} "/public/footer.ftl" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${r'${springMacroRequestContext.contextPath}'}/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript">
$(function(){
	
	<#if RequestParameters['oper']?exists>
		<#if "add" == RequestParameters['oper']>
		$("#parentId").val(parent.$("#parentId").val());
		$("#parentName").val(parent.$("#parentName").val());
		</#if>
	</#if>
	
	$("#form-${model}").validate({
		rules:{<#assign requireds = "0" />
			<#list cols as col>
			<#if col.IS_NULLABLE == "NO"><#if requireds == "1">,</#if>
			${col.FIELD_NAME}:{
				required:true
			}
			<#assign requireds = "1" />
			</#if>
			</#list>
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$.ajax({
				"dataType":"json",
				"type":"post",
				"url":"${r'${springMacroRequestContext.contextPath}'}/${model}/${r'${url}'}/",
				"data":$("#form-${model}").serialize(),
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
<${r'#include'} "/public/ztree.ftl" />
<${r'@ztree'} id="treeDemo" 
url="${r'${springMacroRequestContext.contextPath}'}/${model}/selecttree"
select="true"
selectId="parentName"
checkEnable="true"
checkRadio="true"
>
</${r'@ztree'}>
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
	$("#parentId").val(treeNodes.id);
	$("#parentName").val(treeNodes.name);	
}
</script>
</body>
</html>