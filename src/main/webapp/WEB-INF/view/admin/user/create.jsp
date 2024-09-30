<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Create A User - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    $(document).ready(() => {
      const avatarFile = $("#avatarFile");
      avatarFile.change(function (e) {
        const imgURL = URL.createObjectURL(e.target.files[0]);
        $("#avatarPreview").attr("src", imgURL);
        $("#avatarPreview").css({
          "display": "block"
        });
      });
    });
  </script>

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
              <li class="breadcrumb-item active" aria-current="page">Users</li>
            </ol>
          </nav>
          <div class="mt-5">
            <a href="/admin/user" class="btn btn-primary my-2">Back</a>
            <div class="row">
              <div class="mx-auto col-md-6 col-12">
                <h2 class=" text-center">Create A User</h2>
                <hr class="border-2">

                <form:form class="row g-3" method="POST" enctype="multipart/form-data" action="/admin/user/create"
                  modelAttribute="newUser">
                  <div class="col-md-6">
                    <c:set var="errorEmail">
                      <form:errors path="email" />
                    </c:set>
                    <label for="form-label" class="form-label">Email</label>
                    <form:input type="email" class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                      path="email" />
                    <span class="${not empty errorEmail ? 'invalid-feedback' : ''}">${errorEmail}</span>
                  </div>
                  <div class="col-md-6">
                    <c:set var="errorPassword">
                      <form:errors path="password" />
                    </c:set>
                    <label for="password" class="form-label">Password</label>
                    <form:input type="password" class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                      path="password" />
                    <span class="${not empty errorPassword ? 'invalid-feedback' : ''}"> ${errorPassword}</span>
                  </div>
                  <div class="col-md-6">
                    <c:set var="errorFullName">
                      <form:errors path="fullName" />
                    </c:set>
                    <label for="fullName" class="form-label">Full Name</label>
                    <form:input type="text" class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"
                      path="fullName" />
                    <span class="${not empty errorFullName ? 'invalid-feedback' : ''}"> ${errorFullName}</span>

                  </div>
                  <div class="col-md-6">
                    <label for="phone" class="form-label">Phone</label>
                    <form:input type="text" class="form-control" path="phone" />
                  </div>
                  <div class="col-12">
                    <label for="address" class="form-label">Address</label>
                    <form:input type="text" class="form-control" path="address" />
                  </div>

                  <div class="col-md-6">
                    <label for="role" class="form-label">Role</label>
                    <form:select id="role" class="form-select" path="role">
                      <c:forEach var="role" items="${roles}">
                        <form:option value="${role.id}">${role.name}</form:option>
                      </c:forEach>
                    </form:select>

                  </div>
                  <div class="col-md-6">
                    <label for="avatarFile" class="form-label">Avatar</label>
                    <input name="tantrFile" class="form-control" type="file" id="avatarFile"
                      accept="image/png, image/jpeg, image/jpg" />
                  </div>

                  <div class="col-12 d-flex justify-content-center">
                    <img style="max-height: 250px; display: none; max-width: 100%; " src="" alt="avatar preview"
                      id="avatarPreview">
                  </div>
                  <div class="col-12">
                    <button type="submit" class="btn btn-primary w-100">Create</button>
                  </div>
                </form:form>

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