<#macro javascript src>
	<script type="text/javascript" src="${request.contextPath}/${src}"></script>
</#macro>

<#macro link href>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/${href}" />
</#macro>


<#macro plugin plugin>
	<#if plugin=="validate">
	<!-- validate 验证框架   -->
	<@link href="plugin/bootstrap/css/bootstrap.min.css" />
	<@javascript src="plugin/jquery.js" />
	<@javascript src="plugin/hashmap.js" />
	<@javascript src="plugin/bootstrap/js/bootstrap.js" />
	<@javascript src="plugin/validate/validate.js" />
	<!-- validate 验证框架   -->
	</#if>
</#macro>
