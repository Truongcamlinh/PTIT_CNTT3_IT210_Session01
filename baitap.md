Ex01: 
    Đoạn code sai:
        public RechargeService() {
            this.gateway = new InternalPaymentGateway();
        }

    Giải thích:
        Đây là việc tự khởi tạo dependency (hard-code) bên trong class RechargeService.
        Class bị phụ thuộc trực tiếp vào InternalPaymentGateway, tạo ra tight coupling.

    Theo nguyên lý IoC (Inversion of Control):
        Đối tượng không nên tự tạo dependency, mà phải được tiêm từ bên ngoài (Dependency Injection).
        Việc tự new làm cho class giữ quyền kiểm soát, đi ngược lại IoC.

    Hậu quả:
        Không thể dễ dàng thay đổi sang cổng thanh toán khác (Momo, ZaloPay, …)
        Mỗi lần thay đổi phải sửa code → giảm tính linh hoạt
        Khó test (không thể mock dependency)

Ex02:
    Bean PlaySession được Spring quản lý mặc định ở scope Singleton nên chỉ tồn tại một instance duy nhất trong toàn bộ ứng dụng.

    Biến playTime là dữ liệu trạng thái (mutable state) nằm trong Bean này nên sẽ bị chia sẻ cho tất cả các máy/request.

    Khi nhiều máy cùng gọi addTime(), tất cả đều cập nhật chung một biến playTime, làm cho thời gian chơi bị cộng dồn lẫn nhau.

    Do đó hệ thống không thể tách biệt thời gian của từng máy, dẫn đến việc tính tiền sai.

Ex03:
    1. Dữ liệu đầu vào (Input)
    username (String): tên người dùng
    foodName (String): tên món ăn (ví dụ: "Mì xào bò")
    quantity (int): số lượng cần đặt
    2. Kết quả mong đợi (Output)
        Thành công:
            "Đặt món thành công"
        Thất bại:
            "Người dùng không tồn tại"
            "Hết món trong kho"
            "Số dư không đủ"
    3. Thiết kế kiến trúc (Dependency Injection)
        OrderFoodService phụ thuộc:
        InventoryRepository (kiểm tra kho, giá)
        UserAccountRepository (kiểm tra và trừ tiền)
        Các dependency được tiêm từ bên ngoài (Constructor Injection) → đảm bảo loose coupling
    4. Logic xử lý (Flow)
        Nhận input: username, foodName, quantity
        Kiểm tra người dùng tồn tại
        Nếu không → trả về "Người dùng không tồn tại"
        Lấy số lượng tồn kho
        Nếu stock == 0 hoặc < quantity → trả về "Hết món trong kho"
        Lấy giá món ăn
        Tính tổng tiền: total = price × quantity
        Lấy số dư tài khoản
        Nếu balance < 0 hoặc < total → trả về "Số dư không đủ"
        Trừ tiền tài khoản
        Giảm số lượng trong kho
        Trả về "Đặt món thành công"
5. Xử lý bẫy dữ liệu
        Nếu kho trả về 0 → chặn ngay, không cho đặt
        Nếu tài khoản âm hoặc không đủ tiền → không thực hiện giao dịch
        Đảm bảo không xảy ra trừ tiền khi đơn hàng không hợp lệ
6. Kết luận
        Áp dụng Dependency Injection giúp hệ thống tách biệt rõ ràng giữa Service và Repository
        Tăng tính linh hoạt, dễ mở rộng và dễ kiểm thử
        Logic xử lý đảm bảo đúng nghiệp vụ và xử lý đầy đủ các trường hợp lỗi

Ex04:

| Tiêu chí | Constructor Injection | Field Injection |
|---|---|---|
| Tính rõ ràng dependency | Rõ ràng, bắt buộc truyền vào | Ẩn, khó nhận biết |
| Khả năng test | Dễ mock, dễ test | Khó test |
| Tính bất biến | Có thể dùng final | Không |
| Phát hiện lỗi | Lỗi ngay khi khởi tạo | Lỗi runtime |
| Tuân thủ SOLID | Tốt (DIP) | Kém hơn |
| Xử lý lỗi (SMS lỗi) | Dễ kiểm soát, fallback rõ ràng | Khó kiểm soát |