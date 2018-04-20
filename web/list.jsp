<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻列表</title>
<%
	request.setAttribute("APP_PATH", request.getContextPath());
%>

<script type="text/javascript"
	src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body background="${APP_PATH }/static/image/bg.jpg"  style="background-size:100% 100%;background-repeat: no-repeat;overflow-x: hidden;">

<!-- 保存 -->
<div class="modal fade" id="newsAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新闻添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
        
		  <div class="form-group">
		    <label class="col-sm-2 control-label">标题</label>
		    <div class="col-sm-10">
		      <input type="text" name="title" class="form-control" id="newsName_add_input" placeholder="标题">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label class="col-sm-2 control-label">作者</label>
		    <div class="col-sm-10">
		      <input type="text" name="author" class="form-control" id="author_add_input" placeholder="作者">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  
		   <div class="form-group">
		    <label class="col-sm-2 control-label">来源</label>
		    <div class="col-sm-10">
		      <input type="text" name="source" class="form-control" id="source_add_input" placeholder="来源">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  
		   <div class="form-group">
		    <label class="col-sm-2 control-label">网址</label>
		    <div class="col-sm-10">
		      <input type="text" name="url" class="form-control" id="source_add_input" placeholder="网址">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  
		</form>
      </div>
     
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="news_save_btn">保存</button>
      </div>
    </div>
  </div>
</div>
	<!-- 搭建显示页面 -->
	<div class="container"">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>新闻后台管理系统</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-10 col-md-offset-11">
			
				<button class="btn btn-success " id="news_add_modal_btn"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
				
			   <a href="logout">Logout</a>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="news_table">
					<thead>
						<tr>
							<th>标题</th>
							<th>作者</th>
							<th>来源</th>
							<th>时间</th>
							<th>网址</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
		</div>

		<!-- 显示分页信息 -->
		<div class="row">
			<!--分页文字信息  -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
				
			</div>
		</div>
		
	</div>
	<script type="text/javascript">
	
		var totalRecord,currentPage;
		//1、页面加载完成以后，直接去发送ajax请求,要到分页数据
		$(function(){
			//去首页
			to_page(1);
		});
		
		function to_page(pn){
			$.ajax({
				url:"${APP_PATH}/news",
				data:"pn="+pn,
				type:"GET",
				success:function(result){
					console.log(result);
					//1、解析并显示员工数据
					build_news_table(result);
					//2、解析并显示分页信息
			    	build_page_info(result);
					//3、解析显示分页条数据
			    	build_page_nav(result);
				}
			});
		}
		
		function build_news_table(result){
			//清空table表格
			$("#news_table tbody").empty();
			var news = result.extend.pageInfo.list;
			$.each(news,function(index,item){
			//	var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
				
				var title =item.title;
				
				if(title.length>24){
					title=title.substring(0,24)+"....";
				}
				
				var title = $("<td></td>").append(title);
				
				var author = item.author;
		
				if(author.length>14){
					author=author.substring(0,13)+"....";
				}
				var author = $("<td></td>").append(author);
				
				var source = $("<td></td>").append(item.source);

				var time = $("<td></td>").append(item.time);
				
				var url = $("<td></td>").append($("<a></a>").append("来源页面").attr("href",item.url));
				
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
				.append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				
				var delBtn =  $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
								.append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
					
				delBtn.attr("del-title",item.title);
				delBtn.attr("del-author",item.author);
				delBtn.attr("del-source",item.source);
			
				var btnTd = $("<td></td>").append(delBtn);
				//var delBtn = 
				//append方法执行完成以后还是返回原来的元素
		
				$("<tr></tr>")
					.append(title)
					.append(author)
					.append(source)
					.append(time)
					.append(url)
					.append(btnTd)
					.appendTo("#news_table tbody");
			});
		}
		//解析显示分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页,总"+
					result.extend.pageInfo.pages+"页,总"+
					result.extend.pageInfo.total+"条记录");
			totalRecord = result.extend.pageInfo.total;
			currentPage = result.extend.pageInfo.pageNum;
		}
		//解析显示分页
		function build_page_nav(result){
			//page_nav_area
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			
			//构建元素
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			if(result.extend.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			}else{
				//为元素添加点击翻页的事件
				firstPageLi.click(function(){
					to_page(1);
				});
				prePageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum -1);
				});
			}
			
			var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(result.extend.pageInfo.hasNextPage == false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			}else{
				nextPageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum +1);
				});
				lastPageLi.click(function(){
					to_page(result.extend.pageInfo.pages);
				});
			}
			
			//添加首页和前一页 的提示
			ul.append(firstPageLi).append(prePageLi);
			//1,2，3遍历给ul中添加页码提示
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageInfo.pageNum == item){
					numLi.addClass("active");
				}
				numLi.click(function(){
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页 的提示
			ul.append(nextPageLi).append(lastPageLi);
			
			//把ul加入到nav
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
		
		//清空表单样式及内容
	
		function reset_form(ele){
			$(ele)[0].reset();
			//清空表单样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		
		//点击新增按钮弹出模态框。
		$("#news_add_modal_btn").click(function(){
			//清除表单数据（表单完整重置（表单的数据，表单的样式））
			reset_form("#newsAddModal form");
			//弹出模态框
			$("#newsAddModal").modal({
				backdrop:"static"
			});
		});
		
		
		//校验表单数据
		function validate_add_form(){	
			
			var title = $("#newsName_add_input").val();
			if(title==""){
				show_validate_msg("#newsName_add_input", "error", "不能为空");
				return false;
			}else{
				show_validate_msg("#newsName_add_input", "success", "");
			}
			
			var source = $("#author_add_input").val();
			if(source==""){
				show_validate_msg("#author_add_input", "error", "不能为空");
				return false;
			}else{
				show_validate_msg("#author_add_input", "success", "");
			}
			
			
			var url = $("#url_add_input").val();
			if(url==""){
				show_validate_msg("#url_add_input", "error", "不能为空");
				return false;
			}else{
				show_validate_msg("#url_add_input", "success", "");
			}
	
			return true;
			
		}
		
		//显示校验结果的提示信息
		function show_validate_msg(ele,status,msg){
			//清除当前元素的校验状态
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if("error" == status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		
		//校验用户名是否可用
		$("#newsName_add_input").change(function(){
			//发送ajax请求校验新闻名是否可用
			var title = this.value;
			$.ajax({
				url:"${APP_PATH}/checktitle",
				data:"title="+title,
				type:"POST",
				success:function(result){
					if(result.code==100){
						show_validate_msg("#newsName_add_input","success","新闻名可用");
						$("#news_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#newsName_add_input","error",result.extend.va_msg);
						$("#news_save_btn").attr("ajax-va","error");
					}
				}
			});
		});
		
		
		$("#news_save_btn").click(function(){
			//1、模态框中填写的表单数据提交给服务器进行保存
			//1、先对要提交给服务器的数据进行校验

			if(!validate_add_form()){
				return false;
			};
			if($(this).attr("ajax-va")=="error"){
				return false;
			}
			
			
			$.ajax({
				url:"${APP_PATH}/news",
				type:"POST",
				data:$("#newsAddModal form").serialize(),
				success:function(result){
					if(result.code == 100){
					
						//1、关闭模态框
						$("#newsAddModal").modal('hide');	
						//2、来到最后一页，显示刚才保存的数据
						//发送ajax请求显示最后一页数据即可
						to_page(totalRecord);
					}else{
						//显示失败信息
						console.log(result);
						//有哪个字段的错误信息就显示哪个字段的；
						if(undefined != result.extend.errorFields.title){
						
							show_validate_msg("#email_add_input", "error", result.extend.errorFields.title);
						}
						if(undefined != result.extend.errorFields.url){
							
							show_validate_msg("#empName_add_input", "error", result.extend.errorFields.url);
						}
					}
				}
			});
		});
			
		
		//单个删除
		$(document).on("click",".delete_btn",function(){
			//1、弹出是否确认删除对话框
		//	var  = $(this).parents("tr").find("td:eq(1)").text();
			var title = $(this).attr("del-title");
			var author = $(this).attr("del-author");
			var source = $(this).attr("del-source");
			//alert($(this).parents("tr").find("td:eq(1)").text());
			if(confirm("确认删除【"+title+"】吗？")){
				//确认，发送ajax请求删除即可
				$.ajax({
					url:"${APP_PATH}/news/"+title+"/"+author+"/"+source,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						//回到本页
						to_page(currentPage);
					}
				});
			}
		});
	</script>
</body>
</html>