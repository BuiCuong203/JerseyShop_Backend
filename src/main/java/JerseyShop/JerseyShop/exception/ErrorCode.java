package JerseyShop.JerseyShop.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi ngoại lệ, chưa xử lý", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1001, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    INVALID_PASSWORD(1002, "Mật khẩu phải ít nhất có 8 ký tự", HttpStatus.BAD_REQUEST),
    WRONG_SIGNIN(1003, "Tên tài khoản hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    EMAIL_EXISTED(1004, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    CLUB_EXISTED(1005, "Club đã tồn tại", HttpStatus.BAD_REQUEST),
    TYPE_EXISTED(1006, "Type đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_CLUB(1007, "Club không tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_TYPE(1008, "Type không tồn tại", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatus statusCode;
}
