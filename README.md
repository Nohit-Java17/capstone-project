# DỰ ÁN WEB THƯƠNG MẠI ĐIỆN TỬ

`ecommerce_project` là website thương mại điện tử xây dựng bằng Spring Boot, Thymeleaf, Spring Security và MySQL. Ứng dụng hỗ trợ luồng mua sắm cơ bản: xem sản phẩm, phân loại, chi tiết sản phẩm, giỏ hàng, thanh toán, lịch sử đơn hàng, hồ sơ khách hàng, đăng ký, đăng nhập và đặt lại mật khẩu qua email.

## TÍNH NĂNG CHÍNH

- Hiển thị danh sách sản phẩm, sản phẩm mới, sản phẩm bán chạy và sản phẩm theo phân loại.
- Quản lý giỏ hàng, mã giảm giá, phí vận chuyển và thông tin người nhận.
- Tạo đơn hàng với các phương thức thanh toán tiền mặt, chuyển khoản hoặc thẻ.
- Xác thực người dùng bằng Spring Security và phát JWT cho API refresh token.
- Gửi email đặt lại mật khẩu bằng cấu hình SMTP qua biến môi trường.

## CÔNG NGHỆ

- Java 17
- Spring Boot 2.7.x
- Spring MVC, Spring Data JPA, Spring Security, Thymeleaf
- MySQL
- Maven Wrapper
- Docker, Jenkins, GitHub Actions

## CẤU HÌNH MÔI TRƯỜNG

Ứng dụng không còn lưu credential trực tiếp trong source. Khi chạy local hoặc deploy, cấu hình các biến môi trường sau nếu giá trị mặc định chưa phù hợp:

```bash
PORT=8080
DB_URL=jdbc:mysql://localhost:3306/ecommerce?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh
DB_USERNAME=root
DB_PASSWORD=your_database_password
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
JWT_SECRET=your_jwt_secret
JPA_SHOW_SQL=false
JPA_FORMAT_SQL=false
JPA_DDL_AUTO=update
```

## CHẠY LOCAL

```bash
./mvnw spring-boot:run
```

Trên Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## BUILD

```bash
./mvnw clean package
```

File WAR sau khi build nằm tại:

```text
target/ecommerce_project-0.0.1-SNAPSHOT.war
```

## HÌNH ẢNH DEMO

<p align='center'>
<img src='pic/0.jpg'></img>
</p>

## VIDEO DEMO

<div align='center'>

[![Video demo dự án thương mại điện tử](https://img.youtube.com/vi/YXG24rEs8Q4/0.jpg)](https://youtu.be/YXG24rEs8Q4)

</div>

## EER DIAGRAM

<p align='center'>
<img src='design/EER%20Diagram.jpg'></img>
</p>

## THÀNH VIÊN

Nhóm NOHIT gồm các thành viên:

<img src='pic/1.jpg' align='right' width='21%' height='21%'></img>
<div style='display:flex;'>

- Nguyễn Đặng Trường An (team lead)
- Trần Gia Bảo (đã rời nhóm)
- Cao Đức Mạnh
- Đặng Bá Quí (đã rời nhóm)
- Nguyễn Tiến Đạt

</div>
