<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Create A Product - SB Admin</title>
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
          <h1 class="mt-4">Manage Products</h1>
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
              <li class="breadcrumb-item " aria-current="page">Products</li>
              <li class="breadcrumb-item active" aria-current="page">Delete</li>
            </ol>
          </nav>
          <div class="mt-5">
            <a href="/admin/product" class="btn btn-primary my-2">Back</a>
            <div class="row">
              <div class="mx-auto col-md-6 col-12">
                <h2 class=" text-center">Create A Product</h2>
                <hr class="border-2">

                <form:form class="row g-3" method="POST" enctype="multipart/form-data" action="/admin/product/create"
                  modelAttribute="newProduct">
                  <div class="col-md-6">
                    <c:set var="errorName">
                      <form:errors path="name" />
                    </c:set>
                    <label for="form-label" class="form-label">Name</label>
                    <form:input type="name" class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                      path="name" />
                    <span class="${not empty errorName ? 'invalid-feedback' : ''}"> ${errorName}</span>

                  </div>
                  <div class="col-md-6">
                    <c:set var="errorPrice">
                      <form:errors path="price" />
                    </c:set>
                    <label for="price" class="form-label">Price</label>
                    <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                      path="price" />
                    <span class="${not empty errorPrice ? 'invalid-feedback' : ''}"> ${errorPrice}</span>

                  </div>
                  <div class="col-12">
                    <c:set var="errorDetailDesc">
                      <form:errors path="detailDesc" />
                    </c:set>
                    <label for="" class="form-label">Detail description</label>
                    <form:textarea class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}"
                      path="detailDesc" />
                    <span class="${not empty errorDetailDesc ? 'invalid-feedback' : ''}"> ${errorDetailDesc}</span>

                  </div>
                  <div class="col-md-6">
                    <c:set var="errorShortDesc">
                      <form:errors path="shortDesc" />
                    </c:set>
                    <label for="shortDesc" class="form-label">Short description</label>
                    <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}"
                      path="shortDesc" />
                    <span class="${not empty errorShortDesc ? 'invalid-feedback' : ''}"> ${errorShortDesc}</span>

                  </div>
                  <div class="col-md-6">
                    <c:set var="errorQuantity">
                      <form:errors path="quantity" />
                    </c:set>
                    <label for="quantity" class="form-label">Quantity</label>
                    <form:input type="number" class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}"
                      path="quantity" />
                    <span class="${not empty errorQuantity ? 'invalid-feedback' : ''}"> ${errorQuantity}</span>

                  </div>

                  <div class="col-md-6">
                    <label for="target" class="form-label">Factory</label>
                    <form:select id="target" class="form-select" path="factory">
                      <!-- <c:forEach var="target" items="${roles}">
                        <form:option value="${target.id}">${target.name}</form:option>
                      </c:forEach> -->
                      <option selected="true" value="APPLE">APPLE</option>
                      <option value="SAMSUNG">SAMSUNG</option>
                      <option value="ASUS">ASUS</option>
                      <option value="LENOVO">LENOVO</option>
                      <option value="DELETE">DELETE</option>
                      <option value="LG">LG</option>
                      <option value="ACER">ACER</option>

                    </form:select>
                  </div>

                  <div class="col-md-6">
                    <label for="target" class="form-label">Target</label>
                    <form:select id="target" class="form-select" path="target">
                      <option selected="true" value="Gaming">Gaming</option>
                      <option value="Sinh viên - Văn phòng">Sinh viên - Văn phòng</option>
                      <option value="Thiết kế đồ họa">Thiết kế đồ họa</option>
                      <option value="Mỏng nhẹ">Mỏng nhẹ</option>
                      <option value="Doanh nhân">Doanh nhân</option>
                    </form:select>
                  </div>
                  <div class="col-md-6">
                    <label for="avatarFile" class="form-label">Image</label>
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