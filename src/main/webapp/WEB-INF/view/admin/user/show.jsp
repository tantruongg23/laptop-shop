<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>List Users</title>
  <!-- Latest compiled and minified CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Latest compiled JavaScript -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js">
  </script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js">
  </script>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Order - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body>
  <jsp:include page="../layout/header.jsp" />
  <div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
      <main>
        <div class="container-fluid px-4">
          <div class="mb-5">
            <h1 class="mt-4">Manage Users</h1>
            <nav aria-label="breadcrumb">
              <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                <li class="breadcrumb-item active" aria-current="page">Users</li>
              </ol>
            </nav>
          </div>
          <div class="row">
            <div class="col-12 mx-auto">
              <div class="d-flex justify-content-between">
                <h3>Table Users</h3>
                <a class="btn btn-primary" href="/admin/user/create">Create A User</a>
              </div>

              <hr>

              <table class="table table-hover table-bordered ">
                <thead>
                  <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Email</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Role</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="user" items="${users}">

                    <tr>
                      <td>${user.id}</td>
                      <td>${user.email}</td>
                      <td>${user.fullName}</td>
                      <td>${user.role.name}</td>
                      <td>
                        <a href="/admin/user/${user.id}" class="btn btn-success">View</a>
                        <a href="/admin/user/update/${user.id}" class="btn btn-warning">Update</a>
                        <a href="/admin/user/delete/${user.id}" class="btn btn-danger">Delete</a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>

              <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                  <li class="page-item">
                    <a class="page-link ${currentPage == 1 ? 'disabled' : ''}"
                      href="/admin/user?page=${currentPage - 1}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                    <li class="page-item"><a class="page-link ${currentPage - 1 == loop.index ? 'active' : ''}"
                        href="/admin/user?page=${loop.index + 1}">${loop.index
                        +
                        1}</a>
                    </li>
                  </c:forEach>

                  <li class="page-item">
                    <a class="page-link ${currentPage == totalPages  ? 'disabled' : ''}"
                      href="/admin/user?page=${currentPage + 1}" aria-label="Next">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </main>
      <jsp:include page="../layout/footer.jsp" />
    </div>
  </div>

  <jsp:include page="../layout/footer.jsp" />

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