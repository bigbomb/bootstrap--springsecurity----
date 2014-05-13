var UIExtendedModals = function () {
	var dicId;
	//获取dic类型
	var  getDicType = function(byId){
		//异步加载所有菜单列表
		$("#"+byId).empty();
			$.ajax({
			    type: "get", //使用get方法访问后台
			    dataType: "json", //json格式的数据
			    async: true, //同步   不写的情况下 默认为true
			    url: "../dicType/findDicType.html", //要访问的后台地址
			    //data:{"dicTypeKey":type},传送的参数
			    success : function(data){
			    	$("#"+byId).append("<option value=''>--请选择类型--</option>");
			    	for(var i = 0; i < data.length;i++)
			    	$("#"+byId).append("<option value='"+data[i].id+"'>"+data[i].dicTypeName+"</option>");//select节点增加
				}
		});
	};
	//
 	
	$("#csaveModal").on("click",function(){
        	var dicKey = $("#udicKey").val();
        	var dicName = $("#udicName").val();
        	var dicTypeId = $("#udicType").val();
        	var description = $("#udescription").val();
        	var id = dicId;
        	$.ajax({
				type : "POST",
				url : "update.html",
				data : {
					id : id,
					dicKey : dicKey,
					dicName : dicName,
					dicTypeId : dicTypeId,
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
	
	
	 var delDic = function(id){
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
	var getSelectDic = function(byId,value)
	{
		$("#"+byId).empty();
		//异步加载所有菜单列表
		$.ajax({
		    type: "get", //使用get方法访问后台
		    dataType: "json", //json格式的数据
		    async: true, //同步   不写的情况下 默认为true
		    url:'../dicType/findDicType.html', //要访问的后台地址
		    //data:{"dicTypeKey":type},
		    success : function(data){
		    	$("#"+byId).append("<option value=''>--请选择类型--</option>");
		    	for(var i = 0; i < data.length;i++)
			     {$("#"+byId).append("<option value='"+data[i].id+"'>"+data[i].dicTypeName+"</option>");
			    }	  		
		    	
		    	obj=document.getElementById(byId).options;
		    	for(i=0,k=obj.length;i<k;i++){
		    	   if(obj[i].value==value){
		    	    obj[i].selected=true;
		    	    break;
		    	   }
		    	}
		    	
			}
	});
		
	};
	//获取dic名称
	var getDicName = function(id,value)
	{
	    //获得下拉列表的id
	    var select = document.getElementById(id);
	    //获得下拉列表的所有option
	    var options = select.options;
	    //循环获得对应的节点
	    for(var i=0;i<options.length;i++)
	    {
	     //获得节点的值和后台传来的值进行比较
	      if (options[i].value == value)
	      {
	      //如果当前节点与后台传来的值一致，则将当前节点设置为选中状态，并跳出循环
	       options[i].selected = true;
	       break;
	      }
	    }
	 };

		//初始化添加模式化窗口中的输入部分
		var addDicInit = function(url) {
				
				$("#addNew").validate({
			            errorElement: 'span', //default input error message container
			            errorClass: 'help-block', // default input error message class
			            focusInvalid: false, // do not focus the last invalid input
			            rules: {
			            	dicKey: {
			                    required: true,
			                    remote:{ //异步验证是否存在
			    					type:"POST",
			    					url:  'isExist.html',
			    					data:{
			    						name:function(){return $("#dicKey").val();}
			    					 }
			    					}
			                },
			                dicName: {
			                    required: true
			                },
			                dicType: {
			                    required: true
			                },
			                description: {
			                    required: true
			                }
			                
			            },
		
			            messages: {
			            	dicKey: {
			                    required: "请输入Key！",
			                    remote:"该key已经存在"
			                },
			                dicName: {
			                    required: "请输入Key名称！"
			                },
			                dicType: {
			                    required: "请选择类型！"
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
			            	var dicKey = $("#dicKey").val();
			            	var dicName = $("#dicName").val();
			            	var dicTypeId = $("#dicType").val();
			            	var description = $("#description").val();
			            	$.ajax({
			    				type : "POST",
			    				url : url,
			    				data : {
			    					dicKey : dicKey,
			    					dicName : dicName,
			    					dicTypeId : dicTypeId,
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
	var updateDicInit = function(url) {
			
			$("#updateDic").validate({
		            errorElement: 'span', //default input error message container
		            errorClass: 'help-block', // default input error message class
		            focusInvalid: false, // do not focus the last invalid input
		            rules: {
		            	udicKey: {
		                    required: true,
		                    remote:{ //异步验证是否存在
		    					type:"POST",
		    					url:  'isExist.html',
		    					data:{
		    						dicKey:function(){return $("#udicKey").val();}
		    					 }
		    					}
		                },
		                udicName: {
		                    required: true
		                },
		                udicType: {
		                    required: true
		                },
		                udescription: {
		                    required: true
		                }
		                
		            },
	
		            messages: {
		            	udicKey: {
		                    required: "请输入Key！",
		                    remote:"该key已经存在"
		                },
		                udicName: {
		                    required: "请输入Key名称！"
		                },
		                udicType: {
		                    required: "请选择类型！"
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
        
        dicDel :function(id)
        {
              delDic(id);	
        },
        batchDicDel : function()
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
        		     delDic(dicArray.join(","));
        		  }
        
        },
        //打开添加dic的模式化窗口
        addDicModal: function(){
	        	
	        	  var url="add.html";  //传送后台的地址
	        	  $("#add-new").modal();//模式化窗口开启
	        	  getDicType("dicType");//获取dic的select
	        	  addDicInit(url);//初始化模式化窗口数据
        },
        //打开更新dic的模式化窗口
        updateDicModal: function(){
          if("1" == TableManaged.checkboxCheck())//监测行数据有无激活状态
        	  {
	        	   var getDic = TableManaged.getSelected();//获取行数据数组
	        	   $("#udicKey").val(getDic[0]['dicKey']);//把第一行数组中的dickey属性值读取并赋值给input udicKey
	        	   getDicName("udicName",getDic[0]['dicName']);//根据传过来的dicName找到select中的dicName
	        	   getSelectDic("udicType",getDic[0]['dicTypeId']);//根据传过来的dicTypeId找到select中的dicTypeId
	        	   $("#udescription").val(getDic[0]['description']);
	        	   dicId = getDic[0]['id'];
	               $("#update-Dic").modal();//模式化窗口开启
	               updateDicInit();//初始化模式化窗口数据
        	  
        	  }
          else
        	  {
        	  
        	    $("#alert").modal();
        	  }
         }

    };

}();