# PetShop Management System

Hệ thống quản lý PetShop – RESTful API cho quản lý thú cưng, khách hàng, hóa đơn, nhân viên, và báo cáo doanh thu.

---

## Công nghệ sử dụng
- **Backend:** Java 17, Spring Boot, Spring Security, JWT, JPA/Hibernate
- **Database:** MySQL
- **Validation & Security:** Bean Validation, BCrypt, Role-based access control (Admin/Employee)
- **Others:** Lombok, Dotenv, JavaTimeModule (JSON date handling)

---

## Tính năng chính
- **Quản lý người dùng**: đăng nhập, đăng ký, refresh token, phân quyền Admin / Employee
- **Quản lý khách hàng**: CRUD thông tin khách hàng, validate dữ liệu
- **Quản lý thú cưng**: CRUD pets, species, suppliers, stock quantity
- **Quản lý bán hàng & hóa đơn**: tạo giao dịch bán, chi tiết hóa đơn, tự động trừ stock
- **Báo cáo**: doanh thu, số lượng thú cưng bán theo thời gian
- **Bảo mật**: JWT authentication, encode password, reset password chỉ admin
- **Xử lý lỗi**: global exception handler, validation errors, trả JSON chuẩn (401, 403, 400)

---

## Cấu trúc API

### Auth
- `POST /api/auth/register` – Đăng ký
- `POST /api/auth/login` – Đăng nhập, trả access + refresh token
- `POST /api/auth/refresh` – Lấy access token mới từ refresh token
- `POST /api/auth/reset-password` – Admin reset password cho user

### Customer
- `POST /api/customers` – Tạo khách hàng
- `GET /api/customers/{id}` – Xem chi tiết khách hàng
- `GET /api/customers` – Danh sách khách hàng
- `PUT /api/customers/{id}` – Cập nhật
- `DELETE /api/customers/{id}` – Xóa

### Pets
- `POST /api/pets` – Tạo thú cưng
- `GET /api/pets` – Danh sách pets
- `GET /api/pets/{id}` – Chi tiết pet
- `PUT /api/pets/{id}` – Cập nhật pet
- `DELETE /api/pets/{id}` – Xóa pet

### Sales / Invoice
- `POST /api/sales` – Tạo giao dịch bán (trừ stock tự động)
- `GET /api/sales` – Danh sách giao dịch
- `GET /api/sales/{id}` – Chi tiết giao dịch
- `GET /api/sales/user/{userId}` – Giao dịch của 1 user
- `GET /api/sales/report` – Báo cáo doanh thu & số lượng bán
- `GET /api/invoices/{id}` – Chi tiết hóa đơn
- `GET /api/invoices` – Danh sách hóa đơn

### Admin / Employee
- `GET /api/admin/**` – Chỉ Admin truy cập
- `GET /api/employee/**` – Admin và Employee truy cập

---

## Ví dụ request/response

**Login thành công**

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
Response
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Login thất bại
{
  "error": "Bad credentials",
  "message": "Bad credentials"
}
