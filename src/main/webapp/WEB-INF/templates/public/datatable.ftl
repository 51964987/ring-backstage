<#macro datatable titles >
<#nested>
<div class="mt-20">
	<table class="table table-border table-bordered table-bg table-hover table-sort">
		<thead>
			<tr class="text-c">
				<#list titles as j>
				<th>${j}</th>
				</#list>
			</tr>
		</thead>
	</table>
</div>
</#macro>