var TableManaged = function () {
	  var dicTable;
	  var anSelected;
	  
	

    return {

        //main function to initiate the module
    	
        init: function () {
            
            if (!jQuery().dataTable) {
                return;
            }
            // begin first table
       
         
         
         
            dicTable =  $('#sample_1').dataTable({
            	"aoColumns": [   
            	                 {
            	                    "mData": "id",	 
            	                    "mRender": function (data, type, full) {
					                    sReturn = '<input type="checkbox" name ="sdic" value ="1" class ="checkboxes" />';
					                    return sReturn;
                                        }  
                                 },
            	                 {
                            	    "mData":"dicKey",
                                   
                                 },
            	                 {
                            	    "mData":"dicName",
                            	    "bSortable": false	 
            	                 },
            	                 {
        	                	    "mData":"dicTypeKey",
        	                	    "bSortable": false	
            	                 },
            	                 {
        	                	    "mData":"dicTypeName",
        	                	    "bSortable": false	
            	                 },
            	                 {
            	                	 "mData":"description",
            	                	 "bSortable": false	
            	                 },
            	                 {   
            	                	 "mData": "id",
                                     "mRender": function(data, type, full) { // 返回自定义内容
                                    	 return '<a href="javascript:void(0)" id ="edit" class="btn default btn-xs purple"><i class="fa fa-edit"></i>修改</a>&nbsp;&nbsp;<a href="javascript:void(0)" id="del" class="btn default btn-xs black" onclick ="ModalsManager.dicDel(\'' + data + '\');"><i class="fa fa-trash-o"></i>删除</a>';                                    	
                                     }
                                 }
            	                 ],
            	                 "aoColumnDefs": [{
            	                        'bSortable': false,
            	                        'aTargets': [0]
            	                    }
            	                ],
            	   
                 "fnDrawCallback": function () {
                     App.initUniform();
                 }, 
               // "aLengthMenu": [
                //    [5, 10, 15],
               //     [5, 10, 15] // 每页显示几条数据
              //  ],
                // 设置页面初始属性
               // "bDestroy": true,
                "bLengthChange": false, 
                "bProcessing": true,
                "bServerSide": true,
                "bSort": false,
                "bFilter": false, 
                "sAjaxSource": "findDic.html",
                "sServerMethod": "GET", 
                "iDisplayLength": 10,
                "sPaginationType": "bootstrap",
                "oLanguage": {
			                	"sProcessing": '<i class="fa fa-coffee"></i>&nbsp;加载中，请耐心等待...',
			                	//"sLengthMenu": "每页显示 _MENU_条",
			                    "sZeroRecords": "没有找到符合条件的数据",
			                    "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
			                    "sInfoEmpty": "没有记录",
			                    "oPaginate": 
			                        {
			                    	"sFirst": "首页",
			                        "sPrevious": "前一页",
			                        "sNext": "后一页",
			                        "sLast": "尾页"
			                        }
                			}
                
            });
            
            $('#sample_1 .group-checkable').change(function () {
                var set = $(this).attr("data-set");
                var checked = $(this).is(":checked");
                $(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                        $(this).parents('tr').addClass("active");
                    } else {
                        $(this).attr("checked", false);
                        $(this).parents('tr').removeClass("active");
                    }                    
                });
                $.uniform.update(set);
            });
            
            /*jQuery('#sample_1 tbody tr .checkboxes').change(function(){
                $(this).parents('tr').toggleClass("active");
           });*/
           
           $("#edit").live('click',function(){
        	 
        	    var checked =$('#sample_1 tbody tr').find("input[type=checkbox]").is(":checked");
           	   if(checked)
           		{
           		ModalsManager.updateDicModal(); 
           		     $('#sample_1 tbody tr').find("input[type=checkbox]").attr("checked", false);
          		     $('#sample_1 tbody tr').find('span').removeClass('checked');
          		     $('#sample_1 tbody tr').removeClass('active'); 
           		}  
        	  
           });
          
           
           $('#sample_1 tbody').on( 'click', 'tr', function () {
            	var checked = $(this).find("input[type=checkbox]").is(":checked");
            	if(checked)
            		{
            		 $(this).find("input[type=checkbox]").attr("checked", false);
            		 $(this).find('span').removeClass('checked');
          		     $(this).removeClass('active');             	           	
            		}
            	else            		
            		{
            		 $(this).find("input[type=checkbox]").attr("checked", true);
            		 $(this).find('span').toggleClass('checked');
            		 $(this).toggleClass('active');
            		         		
            		}  
            } );
            $('#sample_1_wrapper .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
            $('#sample_1_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown
            //jQuery('#sample_1_wrapper .dataTables_length select').select2(); // initialize select2 dropdown
        },
	 
   checkboxCheck: function() {
		   function fnGetSelected(object){
			   var aReturn = new Array(); 
			    var aTrs = object.fnGetNodes(); 
			    for(var i =0 ; i < aTrs.length ;i++){ 
			        if($(aTrs[i]).hasClass('active')){ 
			            aReturn.push(object.fnGetData(aTrs[i])); 
			        } 
			    } 
			    return aReturn; 
			   
		   }
		    anSelected = fnGetSelected(dicTable);
		    if(anSelected.length!=0)
		    	{
		    	  if(anSelected.length==1)
		    		  {
		    		      return 1;
		    		  }
		    	  else
		    		  {
		    		  
		    		     return 2;
		    		  }
		    	          
		    	}
		    else
		    	{
		    	   return false;
		    	}
		},
        
       getSelected: function() {
              return anSelected;
       },
       delSelected: function(){
    	   dicTable.fnDeleteRow(anSelected);
       },
       refreshTable: function(){
    	   dicTable.fnReloadAjax();

       }
  

    };

}();