package vn.hoidanit.laptopshop.utils;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {
  // Phương thức xử lý phân trang chung
  public static Pageable createPageable(Optional<String> pageOptional, int defaultPageSize) {
    int page = pageOptional.map(p -> {
      try {
        int parsedPage = Integer.parseInt(p);
        return (parsedPage > 0) ? parsedPage : 1; // Nếu page <= 0, trả về 1
      } catch (NumberFormatException e) {
        return 1; // Trả về trang mặc định là 1 nếu có lỗi parse
      }
    }).orElse(1); // Trả về 1 nếu pageOptional rỗng

    return PageRequest.of(page - 1, defaultPageSize);
  }

  // Kiểm tra xem có cần chuyển hướng về trang 1 không
  public static boolean shouldRedirectToFirstPage(Optional<String> pageOptional) {
    return pageOptional.isPresent() && pageOptional.get().equals("0");
  }
}
