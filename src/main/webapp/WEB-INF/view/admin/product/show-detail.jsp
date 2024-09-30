<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title> Product ${id} - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
  <jsp:include page="../layout/header.jsp" />
  <div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
      <main>
        <div class="container-fluid px-4">
          <h1 class="mt-4">Manage Users</h1>
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
              <li class="breadcrumb-item active" aria-current="page">Products</li>
              <li class="breadcrumb-item active" aria-current="page">${id}</li>
            </ol>
          </nav>

          <div class="mt-5">
            <a href="/admin/product" class="btn btn-primary my-2">Back</a>
            <div class="row">
              <div class="col-12 mx-auto">
                <div class="d-flex justify-content-between">
                  <h2>Product ${product.id}</h2>
                  <a class="btn btn-primary" href="/admin/product/update/${id}">Edit User</a>
                </div>

                <hr>
                <div class="card mx-auto" style="width: 60%; ">
                  <img src="/images/product/${product.image}" alt="${product.image}" class="card-img-top">
                  <div class="card-header">
                    <h4>Product Information</h4>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">ID: ${id}</li>
                    <li class="list-group-item">Name: ${product.name}</li>
                    <li class="list-group-item">Price:
                      <fmt:formatNumber type="number" value="${product.price}" /> đ

                    </li>
                    <li class="list-group-item">Description: ${product.detailDesc}</li>
                    <li class="list-group-item">Short Description: ${product.shortDesc}</li>
                    <li class="list-group-item">Quantity: ${product.quantity}</li>
                    <li class="list-group-item">Factory: ${product.factory}</li>
                    <li class="list-group-item">Target: ${product.target}</li>
                  </ul>

                </div>

              </div>
            </div>
          </div>

        </div>
      </main>
      <jsp:include page="../layout/footer.jsp" />
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous">
  </script>
  <script src="/js/scripts.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
  <script src="/js/chart-area-demo.js"></script>
  <script src="/js/chart-bar-demo.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
    crossorigin="anonymous"></script>
  <script src="/js/datatables-simple-demo.js"></script>
</body>

</html>