var ModalsManager = function () {
	var dicId;
	
	$("#csaveModal").on("click",function(){
        	var dicTypeKey = $("#udicTypeKey").val();
        	var dicTypeName = $("#udicTypeName").val();
        	var description = $("#udescription").val();
        	var id = dicId;
        	$.ajax({
				type : "POST",
				url : "update.html",
				data : {
					id : id,
					dicTypeKey : dicTypeKey,
					dicTypeName : dicTypeName,
					description : description
				},
				dataType: "json",
				async : false,
				cache : false,
				success : function(data) {
					if (data.flag == "true") {
		            	$('#cclose').click();	
		            	$('#uclose').click();	
		            	$("#success").modal();
		            	TableManaged.refreshTable();
					} else {
						$("#error").modal();
						return false;
						//$("#loginCheck", $('.login-form')).show();
					}
				}
			});	
	});
	
	
	 var delDicType = function(id){
			$("#confirm").modal();
        	
        	$("#csaveModal").live("click",function(){
			  		$.ajax({
							type : "POST",
							url : "deleteById.html",
							data : {
								ids : id	
							},
							dataType: "json",
							async : false,
							success : function(data) 
							{
								if (data.flag == "true") {
									//if(confirm("需要显示的内容")){}。 
									$('#cclose').click();
									$("#success").modal();
									TableManaged.delSelected();
									
								} else {
									$("#error").modal();
									return false;
									//$("#loginCheck", $('.login-form')).show();
								}
							}
						   });	
			     	 
						$('#sample_1 tbody tr').find("input[type=checkbox]").attr("checked", false);
					    $('#sample_1 tbody tr').find('span').removeClass('checked');
						$('#sample_1 tbody tr').removeClass('active');   
						$('#sample_1 .group-checkable').find("input[type=checkbox]").attr("checked", false);
					    $('#sample_1 .group-checkable').offsetParent().find('span').removeClass('checked');
        	});
				
	 };

			//初始化添加模式化窗口中的输入部分
		var addDicTypeInit = function(url) {
				
				$("#addNew").validate({
			            errorElement: 'span', //default input error message container
			            errorClass: 'help-block', // default input error message class
			            focusInvalid: false, // do not focus the last invalid input
			            rules: {
			            	dicTypeKey: {
			                    required: true,
			                    remote:{ //异步验证是否存在
			    					type:"POST",
			    					url:  'isExist.html',
			    					data:{
			    						name:function(){return $("#dicTypeKey").val();}
			    					 }
			    					}
			                },
			                dicTypeName: {
			                    required: true
			                },
			                description: {
			                    required: true
			                }
			                
			            },
		
			            messages: {
			            	dicTypeKey: {
			                    required: "请输入Key！",
			                    remote:"该key已经存在"
			                },
			                dicTypeName: {
			                    required: "请输入Key名称！"
			                },
			                description: {
			                    required: "请输入描述！"
			                }
			            },
		
			            invalidHandler: function (event, validator) { //display error alert on form submit   
			            	//$("#inputCheck", $('.login-form')).show();
			            },
		
			            highlight: function (element) { // hightlight error inputs
			                $(element)
			                    .closest('.form-group').addClass('has-error'); // set error class to the control group
			            },
		
			            success: function (label) {
			                label.closest('.form-group').removeClass('has-error');
			                label.remove();
			            },
		
			            errorPlacement: function (error, element) {
			                error.insertAfter(element.closest('.input-icon'));
			            },
		
			            submitHandler: function (form) {
			            	var dicTypeKey = $("#dicTypeKey").val();
			            	var dicTypeName = $("#dicTypeName").val();
			            	var description = $("#description").val();
			            	$.ajax({
			    				type : "POST",
			    				url : url,
			    				data : {
			    					dicTypeKey : dicTypeKey,
			    					dicTypeName : dicTypeName,
			    					description : description
			    				},
			    				dataType: "json",
			    				async : false,
			    				success : function(data) {
			    					if (data.flag == "true") {
			    						
						            	$('#close').click();
						            	$("#success").modal();
						            	TableManaged.refreshTable();					            
						            	//dicTable.fnClearTable();  
			    					} else {
			    						$("#error").modal();
			    						return false;
			    						//$("#loginCheck", $('.login-form')).show();
			    					}
			    				}
			    			});	
			            	
			                 // form validation success, call ajax form submit
			            }
			        });
		
			       /* $('.form-horizontal input').keypress(function (e) {
			            if (e.which == 13) {
			                if ($('.form-horizontal').validate().form()) {
			                    $('.form-horizontal').submit(); //form validation success, call ajax form submit
			                }
			                return false;
			            }
			        });*/
    };
			
	//初始化更新模式化窗口中的输入部分
	var updateDicTypeInit = function(url) {
			
			$("#updateDic").validate({
		            errorElement: 'span', //default input error message container
		            errorClass: 'help-block', // default input error message class
		            focusInvalid: false, // do not focus the last invalid input
		            rules: {
		            	udicTypeKey: {
		                    required: true,
		                    remote:{ //异步验证是否存在
		    					type:"POST",
		    					url:  'isExist.html',
		    					data:{
		    						dicTypeKey:function(){return $("#udicTypeKey").val();}
		    					 }
		    					}
		                },
		                udicTypeName: {
		                    required: true
		                },
		                udescription: {
		                    required: true
		                }
		                
		            },
	
		            messages: {
		            	udicTypeKey: {
		                    required: "请输入Key！",
		                    remote:"该key已经存在"
		                },
		                udicTypeName: {
		                    required: "请输入Key名称！"
		                },
		                udescription: {
		                    required: "请输入描述！"
		                }
		            },
	
		            invalidHandler: function (event, validator) { //display error alert on form submit   
		            	//$("#inputCheck", $('.login-form')).show();
		            },
	
		            highlight: function (element) { // hightlight error inputs
		                $(element)
		                    .closest('.form-group').addClass('has-error'); // set error class to the control group
		            },
	
		            success: function (label) {
		                label.closest('.form-group').removeClass('has-error');
		                label.remove();
		            },
	
		            errorPlacement: function (error, element) {
		                error.insertAfter(element.closest('.input-icon'));
		            },
	
		            submitHandler: function (form) {
		            	$("#confirm").modal();		           	            	
		                 // form validation success, call ajax form submit
		            }
		        });
	

		       /* $('.form-horizontal input').keypress(function (e) {
		            if (e.which == 13) {
		                if ($('.form-horizontal').validate().form()) {
		                    $('.form-horizontal').submit(); //form validation success, call ajax form submit
		                }
		                return false;
		            }
		        });*/
      };
      

    return {
        //main function to initiate the module
        init: function () {
        	
        	
        },
        
        dicTypeDel :function(id)
        {
              delDicType(id);	
        },
        batchDicTypeDel : function()
        { 
        	  if("1"==TableManaged.checkboxCheck() || "2"==TableManaged.checkboxCheck())
        		  {
        		    var dicArray=new Array();
        		    var getDic = TableManaged.getSelected();
        		    var i = TableManaged.getSelected().length;
        		     for (var j=0;j<i;j++)
        		     {
        		    	 dicArray.push(getDic[j]['id']);
        		     }
        		     delDicType(dicArray.join(","));
        		  }
        	  else
        		  {
        		  
        		     $("#alert").modal();
        		  }
        
        },
        //打开添加dic的模式化窗口
        addDicTypeModal: function(){
	        	
	        	  var url="add.html";  //传送后台的地址
	        	  $("#add-new").modal();//模式化窗口开启
	        	  addDicTypeInit(url);//初始化模式化窗口数据
        },
        //打开更新dic的模式化窗口
        updateDicTypeModal: function(){
          if("1" == TableManaged.checkboxCheck())//监测行数据有无激活状态
        	  {
	        	   var getDic = TableManaged.getSelected();//获取行数据数组
	        	   $("#udicTypeKey").val(getDic[0]['dicTypeKey']);//把第一行数组中的dickey属性值读取并赋值给input udicKey
	        	   $("#udicTypeName").val(getDic[0]['dicTypeName']);//根据传过来的dicName找到select中的dicName
	        	   $("#udescription").val(getDic[0]['description']);
	        	   dicId = getDic[0]['id'];
	               $("#update-Dic").modal();//模式化窗口开启
	               updateDicTypeInit();//初始化模式化窗口数据
        	  
        	  }
          else
        	  {
        	  
        	    $("#alert").modal();
        	  }
         }

    };

}();