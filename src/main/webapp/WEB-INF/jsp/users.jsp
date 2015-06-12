<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<script type="text/javascript">
	$(function(){
		$('.triggerRemove').click(function(e){
			e.preventDefault();
			$('#removeBtn').prop("href", $(this).prop("href"));
			$("#removeModal").modal();
		});
	});

</script>

<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>User name</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a
					href='<spring:url value="user/${user.id }.html"></spring:url>'>
						${user.name } </a></td>				
				<td>
					<a href="/user/remove/${user.id}.html" class="btn btn-danger triggerRemove">Remove</a>
				</td>
			</tr>	
		</c:forEach>
	</tbody>
</table>

<!-- Modal -->
	<div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Remove user</h4>
	      </div>
	      <div class="modal-body">
	        Really remove?
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <a href="" class="btn btn-danger" id="removeBtn">Remove</a>
	      </div>
	    </div>
	  </div>
	</div>