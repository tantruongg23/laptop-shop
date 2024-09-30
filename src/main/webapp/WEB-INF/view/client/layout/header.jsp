<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid fixed-top">

  <div class="container px-0">
    <nav class="navbar navbar-light bg-white navbar-expand-xl">
      <a href="/" class="navbar-brand">
        <h1 class="text-primary display-6">Laptopshop</h1>
      </a>
      <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="fa fa-bars text-primary"></span>
      </button>
      <div class="collapse navbar-collapse bg-white justify-content-between mx-5 " id="navbarCollapse">
        <div class="navbar-nav ">
          <a href="/" class="nav-item nav-link active">Trang chủ</a>
          <a href="/products" class="nav-item nav-link">Sản phẩm</a>
          <a href="#footer" class="nav-item nav-link">Liên hệ</a>
        </div>

        <!-- User -->
        <div class="d-flex align-items-center m-3 me-0">

          <c:choose>
            <c:when test="${not empty pageContext.request.userPrincipal}">
              <a href="/cart" class="position-relative me-4 my-auto">
                <i class="fa fa-shopping-bag fa-2x"></i>
                <span
                  class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                  style="top: -5px; left: 15px; height: 20px; min-width: 20px;" id="sumCart">
                  ${sessionScope.sum}
                </span>
              </a>
              <div class="dropdown my-auto">
                <a id="dropdownMenuAccount" href="#" class="dropdown" role="button" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  <i class="fas fa-user fa-2x"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-end p-4 " aria-labelledby="dropdownMenuAccount">
                  <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
                    <img width="150" height="150px" style="border-radius: 50%; overflow: hidden;" class="bordered"
                      src="/images/avatar/${sessionScope.avatar}" alt="1711078092373-asus-01.png">
                    <div class="text-center my-3">
                      <c:out value="${sessionScope.fullName}" />
                    </div>
                  </li>
                  <li><a class="dropdown-item" href="#">Quản lí tài khoản</a></li>
                  <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>
                  <hr>
                  <li>
                    <form method="post" action="/logout">
                      <div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                      </div>
                      <button class="dropdown-item" type="submit">Đăng xuất</button>
                    </form>

                  </li>
                </ul>
              </div>
            </c:when>
            <c:otherwise>
              <a href="/login" class="a-login position-relative me-4 my-auto">
                Đăng nhập
              </a>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </nav>
  </div>
</div>