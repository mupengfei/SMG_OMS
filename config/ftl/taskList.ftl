    <!DOCTYPE html>
    <html>
    <head>
    
 
	    <meta charset="utf-8">
	    <title>metro-bootstrap: Twitter Bootstrap with Metro style</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <!-- Le styles -->
	    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/metro-bootstrap.css">
	    <link rel="stylesheet" href="${request.contextPath}/docs/font-awesome.css">
	
	    <style>
	        body
	        {
	            padding-top: 10px; /* 60px to make the container go all the way to the bottom of the topbar */
	        }
	    </style>
    
    </head>
    <body>
    
    	<div class="container">
    	
	    	

			<div class="row">
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
									持续时间
								</th>
								<th>
									执行
								</th>
							</tr>
						</thead>
						<tbody  id="tableBody">
						</tbody>
					</table>
			<div>	
		 	 
  
 		</div>
 
 
 
 
 
 
 
 
 
 
 
     	<script type="text/javascript" src="${request.contextPath}/docs/jquery-1.8.0.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-tooltip.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-alert.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-button.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-collapse.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-dropdown.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-modal.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-popover.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-scrollspy.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-tab.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-transition.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/bootstrap-typeahead.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/jquery.validate.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/jquery.validate.unobtrusive.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/jquery.unobtrusive-ajax.js"></script>
		<script type="text/javascript" src="${request.contextPath}/docs/metro-bootstrap/metro-docs.js"></script>
		<script type="text/javascript"> 

			  var _gaq = _gaq || [];
			  _gaq.push(['_setAccount', 'UA-36060270-1']);
			  _gaq.push(['_trackPageview']);
			
			  (function() {
			    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			  })();

		</script>
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
					 	var html ="<tr  ><td>"+(i+1)+"</td><td>"+task.code+" </td><td>"+task.startTime+"</td><td>"+task.conTime+"</td><td>" 
						+"	<button class='btn   btn-primary btn-mini ' type='button' onclick=\"showInfo('"+i+"')\">--详情--</button>"
						+"	<button class='btn btn-danger btn-mini   ' type='button' onclick=\"closeTask('"+task.code+"')\">--中断--</button>"
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




    </body>
    </html>
    
    
    
    

 

