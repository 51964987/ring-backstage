<!DOCTYPE HTML>
<html>
<#include "/public/head.ftl" />
<@head 
links=["${springMacroRequestContext.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"]
></@head>
<body>
<div class="pos-a" style="width:298px;left:0;right:0;top:0; bottom:0; height:100%; background-color:#f5f5f5; overflow:auto;">
	<ul id="treeDemo" class="ztree"></ul>
</div>

<#include "/public/footer.ftl" />

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${springMacroRequestContext.contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<#include "/public/ztree.ftl" />
<@ztree id="treeDemo" 
url="${springMacroRequestContext.contextPath}/roleresource/trees"
checkEnable="true"
checkRadio="false"
otherParam=["'id'","\"${RequestParameters['id']}\""]
>
</@ztree>
</body>
</html>