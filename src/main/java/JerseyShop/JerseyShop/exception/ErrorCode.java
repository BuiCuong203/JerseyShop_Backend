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
    NOT_FOUND_CLUB(1006, "Club không tồn tại", HttpStatus.NOT_FOUND),
    TYPE_EXISTED(1007, "Type đã tồn tại", HttpStatus.BAD_REQUEST),
    NOT_FOUND_TYPE(1008, "Type không tồn tại", HttpStatus.NOT_FOUND),
    JERSEY_EXISTED(1009, "Jersey đã tồn tại", HttpStatus.BAD_REQUEST),
    NOT_FOUND_JERSEY(1010, "Jersey không tồn tại", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER(1011, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    NOT_FOUND_SIZE(1011, "Size không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_FILE(1012, "Không có ảnh nào được tải lên", HttpStatus.BAD_REQUEST),
    WRONG_QUANTITY(1013, "Chưa nhập đủ số lượng", HttpStatus.BAD_REQUEST),
    OVER_QUANTITY(1014, "Số lượng vượt quá số lượng có sẵn", HttpStatus.BAD_REQUEST),
    NOT_FOUND_CARTITEM(1015, "Không tìm thấy CartItem", HttpStatus.NOT_FOUND),
    NOT_FOUND_CART(1016, "Không tìm thấy Cart", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDER(1017, "Không tìm thấy Order", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDERITEM(1018, "Không tìm thấy OrderItem", HttpStatus.NOT_FOUND),
    WRONG_ORDER_STATUS(1019, "Không thể thay đổi được trạng thái đơn hàng", HttpStatus.BAD_REQUEST),
    FAILED_PAYMENT(1020, "Thanh toán thất bại", HttpStatus.BAD_REQUEST);



    int code;
    String message;
    HttpStatus statusCode;
}
