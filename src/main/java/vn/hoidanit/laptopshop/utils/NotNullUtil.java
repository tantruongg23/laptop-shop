package vn.hoidanit.laptopshop.utils;

import java.util.Optional;

public class NotNullUtil {
  public static <T> boolean checkOptionNotNull(Optional<T> x) {
    return x != null && x.isPresent() && x.get() != null;
  }
}
