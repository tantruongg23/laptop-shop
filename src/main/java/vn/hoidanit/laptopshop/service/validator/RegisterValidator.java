package vn.hoidanit.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Name validator
        if (user.getFirstName().length() < 1) {
            context.buildConstraintViolationWithTemplate("First Name phải có tối thiểu 1 kí tự")
                    .addPropertyNode("firstName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        if (user.getLastName().length() < 2) {
            context.buildConstraintViolationWithTemplate("Last Name phải có tối thiểu 2 kí tự")
                    .addPropertyNode("lastName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Email validator
        boolean isEmailValid = user.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        if (!isEmailValid) {
            context.buildConstraintViolationWithTemplate("Email không hợp lệ")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        boolean isEmailExist = this.userService.checkEmailExist(user.getEmail());
        if (isEmailExist) {
            context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Password validator
        boolean isPasswordValid = user.getPassword()
                .matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$");
        if (!isPasswordValid) {
            context.buildConstraintViolationWithTemplate(
                    "Mật khẩu phải dài ít nhất 8 kí tự. Bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt")
                    .addPropertyNode("password")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        // if (user.getPassword().length() < 2) {
        // context.buildConstraintViolationWithTemplate("Password phải có tối thiểu 2 kí
        // tự")
        // .addPropertyNode("password")
        // .addConstraintViolation()
        // .disableDefaultConstraintViolation();
        // valid = false;
        // }
        // Check if password fields match
        boolean isPasswordMatching = user.getPassword().equals(user.getConfirmPassword());
        if (!isPasswordMatching) {
            context.buildConstraintViolationWithTemplate("Passwords không trùng khớp")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
