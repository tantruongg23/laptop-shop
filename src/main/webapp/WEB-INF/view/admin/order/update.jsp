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
  <title>Create A Order - SB Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
  <link href="/css/styles.css" rel="stylesheet" />
  <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    $(document).ready(() => {
      const avatarFile = $("#avatarFile");
      const orgImage = "${newProduct.image}";
      if (orgImage) {
        const urlImage = "/images/product/" + orgImage;
        $("#avatarPreview").attr("src", urlImage);
        $("#avatarPreview").css({
          "display": "block"
        });


      }
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
              <li class="breadcrumb-item " aria-current="page">Orders</li>
              <li class="breadcrumb-item active" aria-current="page">Update</li>
            </ol>
          </nav>
          <div class="mt-5">
            <a href="/admin/product" class="btn btn-primary my-2">Back</a>
            <div class="row">
              <div class="mx-auto col-md-6 col-12">
                <h2 class=" text-center">Update A Order</h2>
                <hr class="border-2">

                <form:form class="row g-3" method="POST" action="/admin/order/update" modelAttribute="order">
                  <div class="mb-3" style="display: none;">
                    <label class="form-label">Id:</label>
                    <form:input type="text" class="form-control" path="id" />
                  </div>
                  <div class="col-md-6">
                    <label class="form-label">Order Id</label>
                    <p class="form-control">
                      ${order.id}
                    </p>
                  </div>

                  <div class="col-md-6">
                    <label for="price" class="form-label">Price</label>
                    <p class="form-control">
                      <fmt:formatNumber type="number" value=" ${order.totalPrice}" />
                    </p>
                  </div>

                  <div class="col-md-6">
                    <label for="form-label" class="form-label">Customer Name</label>
                    <p class="form-control" disabled="true">
                      ${order.receiverName}
                    </p>
                  </div>

                  <div class="col-md-6">
                    <label for="target" class="form-label">Status</label>
                    <form:select id="target" class="form-select" path="status">
                      <option value="PENDING">PENDING</option>
                      <option value="SHIPPING">SHIPPING</option>
                      <option value="COMPLETED">COMPLETED</option>
                      <option value="CANCEL">CANCEL</option>
                    </form:select>
                  </div>

                  <div class="col-12">
                    <button type="submit" class="btn btn-warning w-100">Update</button>
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