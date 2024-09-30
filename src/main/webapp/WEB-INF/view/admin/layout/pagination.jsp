<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item">
      <a class="page-link ${currentPage == 1 ? 'disabled' : ''}" href="/admin/product?page=${currentPage - 1}"
        aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
      <li class="page-item"><a class="page-link ${currentPage - 1 == loop.index ? 'active' : ''}"
          href="/admin/product?page=${loop.index + 1}">${loop.index
          +
          1}</a>
      </li>
    </c:forEach>

    <li class="page-item">
      <a class="page-link ${currentPage == totalPages  ? 'disabled' : ''}" href="/admin/product?page=${currentPage + 1}"
        aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>