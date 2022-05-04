<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<script>
$(function(){
	$("ul.pagination li.disabled a").click(function(){
		return false;
	});
});

</script>


<nav>
  <ul class="pagination">
    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=0${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>

    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=${page.start-page.count}${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&lsaquo;</span>
      </a>
    </li>    

    <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
    
    	    <%-- 如果页数是当前页数, 那么这个页数字不可点击 --%>
		    <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                <%-- 如果点击其他页数字, 将会跳转到那一页的界面, 首先传递start参数给page, 然后触发后台执行list方法, 展示新的分页效果 --%>
		    	<a  
		    	href="?start=${status.index*page.count}${page.param}"
		    	<c:if test="${status.index*page.count==page.start}">class="current"</c:if>
		    	>${status.count}</a>
		    </li>
		
    </c:forEach>

    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.start+page.count}${page.param}" aria-label="Next">
        <span aria-hidden="true">&rsaquo;</span>
      </a>
    </li>
    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.last}${page.param}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
