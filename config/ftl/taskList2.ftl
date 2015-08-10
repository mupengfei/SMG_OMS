<!DOCTYPE html>
<html>
 
  
    
    <head>
        <meta charset='utf-8' />
        <title>任务列表</title>
        
        <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"  >
        <link href="${request.contextPath}/css/bootstrap-theme.min.css" rel="stylesheet"  >
        <link href="${request.contextPath}/css/theme.css" rel="stylesheet">
        
        <script type="text/javascript" src="${request.contextPath}/js/jquery.js"></script>
        <script src="${request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript">
        
        	
        	var taskList = null;
        	$(document).ready(function(){
		  		restTable();
			});
			
			
			function restTable(){
				var tb=	$("#tableBody");
				tb.html("");
				$.post("${request.contextPath}/plugin/taskJson.htm", null, function(data){
					taskList = data;
					$.each(data, function(i,task){
					 	var trclass = "success";
					 	if( i%2 == 0 ){
					 		trclass="info";
					 	}
					 	var html ="<tr  ><td>"+(i+1)+"</td><td>"+task.code+" </td><td>"+task.startTime+"</td><td>" 
						+"	<button class='btn btn-large btn-primary  ' type='button' onclick=\"showInfo('"+i+"')\">详情</button>"
						+"	<button class='btn btn-danger btn-large   ' type='button' onclick=\"closeTask('"+task.code+"')\">中断</button>"
					  	+"</td>";
						tb.append(html);
					});
				}, "json");
				
				
				
			}
			
			
			function showInfo(id){
				alert(taskList[id].message);
				
			}
			
			function closeTask(id){
			 	if (confirm("确认中断:"+id)) {
            		$.post("${request.contextPath}/plugin/taskClose.htm?hashCode="+id, null, function(data){
						restTable();
					}, "json");
        		}
        
        
				
			}
			
		</script>
    </head>
    
    <body>
    
    
    
    
   <div class="container theme-showcase">
	 
	 <div class="row"> 
	  <div class="span4"> 
	  
		<div class="panel panel-primary">
            <div class="panel-heading">
              <h3 class="panel-title">任务执行列表</h3>
            </div>
            <div class="panel-body">
              <table class="table table-striped table-bordered" >
				<thead>
					<tr>
						<th>
							序号
						</th>
						<th>
							HashCode
						</th>
						<th>
							启动时间
						</th>
						<th>
							执行
						</th>
					</tr>
				</thead>
				<tbody  id="tableBody">
					
					
				</tbody>
			</table> 
          </div>
          </div>
          </div>

 <div class="span8">123213 </div>

      </div></div>
          


	 
		
 


		 
			
			
		 

 


    </body>
 

</html>

