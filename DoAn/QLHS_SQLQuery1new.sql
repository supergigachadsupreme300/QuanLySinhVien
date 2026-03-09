CREATE DATABASE QuanLyHocSinh;
GO
USE QuanLyHocSinh;
GO
DROP DATABASE IF EXISTS QuanLyHocSinh;
-- ==================== TẠO CÁC BẢNG ====================
CREATE TABLE NAM (
    maNam VARCHAR(50) PRIMARY KEY,
    tenNam NVARCHAR(50) NOT NULL,
    trangThai BIT DEFAULT 1
);

CREATE TABLE HOCKY (
    maHK VARCHAR(50) PRIMARY KEY,
    tenHK NVARCHAR(50) NOT NULL,
    maNam VARCHAR(50) NOT NULL,
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE
);

CREATE TABLE GIAOVIEN (
    maGV VARCHAR(50) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    diaChi NVARCHAR(200),
    trangThai BIT DEFAULT 1
);

CREATE TABLE LOP (
    maLop VARCHAR(50) PRIMARY KEY,
    tenLop NVARCHAR(50) NOT NULL,
    siSo INT DEFAULT 0,
    maNam VARCHAR(50) NOT NULL,
    maGVCN VARCHAR(50),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE,
    FOREIGN KEY (maGVCN) REFERENCES GIAOVIEN(maGV) ON DELETE SET NULL
);

CREATE TABLE HOCSINH (
    maHS VARCHAR(50) PRIMARY KEY,
    hoTen NVARCHAR(50) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh NVARCHAR(10),
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    maLop VARCHAR(50),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE SET NULL
);

CREATE TABLE PHUHUYNH (
    maPH VARCHAR(50) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    ngheNghiep NVARCHAR(100),
    trangThai BIT DEFAULT 1
);

CREATE TABLE HOCSINH_PHUHUYNH (
    maHS VARCHAR(50),
    maPH VARCHAR(50),
    quanHe NVARCHAR(50),
    trangThai BIT DEFAULT 1,
    PRIMARY KEY (maHS, maPH),
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maPH) REFERENCES PHUHUYNH(maPH) ON DELETE CASCADE
);

CREATE TABLE MON (
    maMon VARCHAR(50) PRIMARY KEY,
    tenMon NVARCHAR(100) NOT NULL,
    trangThai BIT DEFAULT 1
);

CREATE TABLE PHANCONG (
    maPC VARCHAR(50) PRIMARY KEY,
    maGV VARCHAR(50) NOT NULL,
    maMon VARCHAR(50) NOT NULL,
    maLop VARCHAR(50) NOT NULL,
    maNam VARCHAR(50) NOT NULL,
    ghiChu NVARCHAR(200),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maGV) REFERENCES GIAOVIEN(maGV) ON DELETE NO ACTION,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE NO ACTION,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE NO ACTION,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE,
	CONSTRAINT UQ_PhanCong UNIQUE (maGV, maMon, maLop, maNam)
);

CREATE TABLE CHITIETMON (
    maChiTiet VARCHAR(50) PRIMARY KEY,
    maMon VARCHAR(50) NOT NULL,
    tenChiTiet NVARCHAR(100) NOT NULL,
    heSo INT NOT NULL,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE CASCADE
);

CREATE TABLE DIEM (
    maDiem VARCHAR(50) PRIMARY KEY,
    maHS VARCHAR(50) NOT NULL,
    maMon VARCHAR(50) NOT NULL,
    maHocKy VARCHAR(50) NOT NULL,
    diemThuongXuyen FLOAT,
    diemGiuaKy FLOAT,
    diemCuoiKy FLOAT,
    diemTBMonHocKy FLOAT,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE NO ACTION,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE HANHKIEM (
    maHanhKiem VARCHAR(50) PRIMARY KEY,
    maHS VARCHAR(50) NOT NULL,
    maHocKy VARCHAR(50) NOT NULL,
    xepLoai NVARCHAR(100),
    soLanViPham INT DEFAULT 0,
    nhanXet NVARCHAR(500),
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE VIPHAM (
    maViPham VARCHAR(50) PRIMARY KEY,
    maHS VARCHAR(50) NOT NULL,
    maHocKy VARCHAR(50) NOT NULL,
    ngayViPham DATE NOT NULL,
    noiDung NVARCHAR(500),
    mucDo NVARCHAR(50),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE XEPLOAI (
    maXepLoai VARCHAR(50) PRIMARY KEY,
    maHS VARCHAR(50) NOT NULL,
    maHocKy VARCHAR(50) NOT NULL,
    xepLoaiHocLuc NVARCHAR(20),
    xepLoaiHanhKiem NVARCHAR(20),
    diemTBChung FLOAT,
    nhanXet NVARCHAR(500),
    duocLenLop BIT DEFAULT 1,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE THOIKHOABIEU (
    maTKB VARCHAR(50) PRIMARY KEY,
    maLop VARCHAR(50) NOT NULL,
    maHocKy VARCHAR(50) NOT NULL,
    ngayBatDau DATE,
    ngayKetThuc DATE,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE NO ACTION,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE CHITIETTIET (
    maChiTiet VARCHAR(50) PRIMARY KEY,
    maTKB VARCHAR(50) NOT NULL,
    maMon VARCHAR(50) NOT NULL,
    thu NVARCHAR(20) NOT NULL,
    tiet INT NOT NULL,
    phongHoc VARCHAR(20),
    gioBatDau TIME,
    gioKetThuc TIME,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maTKB) REFERENCES THOIKHOABIEU(maTKB) ON DELETE CASCADE,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE NO ACTION
);

-- ==================== THÊM DỮ LIỆU MẪU ====================

-- 1. NĂM HỌC
INSERT INTO NAM (maNam, tenNam, trangThai) VALUES
('NH2122', N'Năm học 2021-2022', 0),
('NH2223', N'Năm học 2022-2023', 0),
('NH2324', N'Năm học 2023-2024', 0),
('NH2425', N'Năm học 2024-2025', 0),
('NH2526', N'Năm học 2025-2026', 1);

-- 2. HỌC KỲ
INSERT INTO HOCKY (maHK, tenHK, maNam, ngayBatDau, ngayKetThuc, trangThai) VALUES
('HK1_2122', N'Học kỳ 1', 'NH2122', '2021-09-05', '2022-01-10', 0),
('HK2_2122', N'Học kỳ 2', 'NH2122', '2022-01-13', '2022-05-25', 0),
('HK1_2223', N'Học kỳ 1', 'NH2223', '2022-09-05', '2023-01-10', 0),
('HK2_2223', N'Học kỳ 2', 'NH2223', '2023-01-13', '2023-05-25', 0),
('HK1_2324', N'Học kỳ 1', 'NH2324', '2023-09-05', '2024-01-10', 0),
('HK2_2324', N'Học kỳ 2', 'NH2324', '2024-01-13', '2024-05-25', 0),
('HK1_2425', N'Học kỳ 1', 'NH2425', '2024-09-05', '2025-01-10', 0),
('HK2_2425', N'Học kỳ 2', 'NH2425', '2025-01-13', '2025-05-25', 0),
('HK1_2526', N'Học kỳ 1', 'NH2526', '2025-09-05', '2025-01-10', 1),
('HK2_2526', N'Học kỳ 2', 'NH2526', '2026-01-13', '2026-05-25', 1);

-- 3. GIÁO VIÊN
INSERT INTO GIAOVIEN (maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai) VALUES
('GV001', N'Nguyễn Thị Mai', '1985-03-15', N'Nữ', '0901000001', 'gv001@thcs.edu.vn', N'Hà Nội', 1),
('GV002', N'Trần Văn Hùng', '1983-07-20', N'Nam', '0901000002', 'gv002@thcs.edu.vn', N'Hưng Yên', 1),
('GV003', N'Lê Thị Lan', '1987-11-10', N'Nữ', '0901000003', 'gv003@thcs.edu.vn', N'Cam Ranh', 1),
('GV004', N'Phạm Văn Nam', '1984-05-25', N'Nam', '0901000004', 'gv004@thcs.edu.vn', N'Vinh', 1),
('GV005', N'Hoàng Thị Hoa', '1986-02-14', N'Nữ', '0901000005', 'gv005@thcs.edu.vn', N'Hải Phòng', 1),
('GV006', N'Đỗ Văn Tùng', '1982-09-08', N'Nam', '0901000006', 'gv006@thcs.edu.vn', N'Vũng Tàu', 1),
('GV007', N'Vũ Thị Thu', '1988-12-30', N'Nữ', '0901000007', 'gv007@thcs.edu.vn', N'Huế', 1),
('GV008', N'Bùi Văn Đức', '1985-06-18', N'Nam', '0901000008', 'gv008@thcs.edu.vn', N'TP.HCM', 1),
('GV009', N'Đặng Thị Ngọc', '1989-04-22', N'Nữ', '0901000009', 'gv009@thcs.edu.vn', N'Quy Nhơn', 1),
('GV010', N'Ngô Văn Sơn', '1984-08-16', N'Nam', '0901000010', 'gv010@thcs.edu.vn', N'Đà Lạt', 1),
('GV011', N'Dương Thị Hằng', '1987-01-05', N'Nữ', '0901000011', 'gv011@thcs.edu.vn', N'Thái Bình', 1),
('GV012', N'Lý Văn Kiên', '1986-10-12', N'Nam', '0901000012', 'gv012@thcs.edu.vn', N'Cà Mau', 1),
('GV013', N'Nguyễn Văn Toàn', '1985-04-18', N'Nam', '0901000013', 'gv013@thcs.edu.vn', N'Hà Nội', 1),
('GV014', N'Lê Hoàn Long', '1987-01-12', N'Nam', '0901000014', 'gv014@thcs.edu.vn', N'Đà Nẵng', 1),
('GV015', N'Nguyễn Thiên Kiên', '1986-11-12', N'Nam', '0901000015', 'gv015@thcs.edu.vn', N'Quảng Ngãi', 1),
('GV016', N'Lý Văn Kiệt', '1986-10-12', N'Nam', '0901000016', 'gv016@thcs.edu.vn', N'Cà Mau', 1),
('GV017', N'Hùng Kim Sang', '1986-10-12', N'Nam', '0901000017', 'gv017@thcs.edu.vn', N'Bến Tre', 1),
('GV018', N'Thiên Tuấn Lộc', '1986-10-12', N'Nam', '0901000018', 'gv018@thcs.edu.vn', N'Đồng Tháp', 1),
('GV019', N'Phan Hoàng Kha', '1986-10-12', N'Nam', '0901000019', 'gv019@thcs.edu.vn', N'Kiên Giang', 1),
('GV020', N'Long Trần Dương', '1986-10-12', N'Nam', '0901000020', 'gv020@thcs.edu.vn', N'Bình Thuận', 1),
('GV021', N'Toàn Lê Đạt', '1986-10-12', N'Nam', '0901000021', 'gv021@thcs.edu.vn', N'Đắk Lắk', 1),
('GV022', N'Nguyễn Thị Hồng', '1986-10-12', N'Nữ', '0901000022', 'gv022@thcs.edu.vn', N'Bình Định', 1),
('GV023', N'Phạm Văn Khánh', '1986-10-12', N'Nam', '0901000023', 'gv023@thcs.edu.vn', N'Nam Định', 1),
('GV024', N'Phan Thị Tâm', '1988-03-28', N'Nữ', '0901000024', 'gv024@thcs.edu.vn', N'Bình Định', 1),
('GV025', N'Phan Long Tuấn', '1988-03-28', N'Nam', '0901000025', 'gv025@thcs.edu.vn', N'Nam Định', 1),
('GV026', N'Hà Xuân Cập', '1988-05-29', N'Nữ', '0901000026', 'gv026@thcs.edu.vn', N'Hưng Yên', 1),
('GV027', N'Nguyễn Văn Phúc', '1988-03-28', N'Nam', '0901000027', 'gv027@thcs.edu.vn', N'Hà Nội', 1),
('GV028', N'Trần Thị Mỹ', '1988-03-28', N'Nữ', '0901000028', 'gv028@thcs.edu.vn', N'Hải Dương', 1),
('GV029', N'Nguyễn Văn Lộc', '1988-03-28', N'Nam', '0901000029', 'gv029@thcs.edu.vn', N'Quảng Nam', 1),
('GV030', N'Phạm Thị Hòa', '1988-03-28', N'Nữ', '0901000030', 'gv030@thcs.edu.vn', N'Bắc Ninh', 1),
('GV031', N'Nguyễn Văn Hậu', '1988-03-28', N'Nam', '0901000031', 'gv031@thcs.edu.vn', N'Hà Tĩnh', 1),
('GV032', N'Lê Thị Hương', '1988-03-28', N'Nữ', '0901000032', 'gv032@thcs.edu.vn', N'Quảng Trị', 1),
('GV033', N'Nguyễn Văn Tài', '1988-03-28', N'Nam', '0901000033', 'gv033@thcs.edu.vn', N'Đà Nẵng', 1),
('GV034', N'Phan Thị Thu', '1988-03-28', N'Nữ', '0901000034', 'gv034@thcs.edu.vn', N'Bình Định', 1),
('GV035', N'Nguyễn Văn Khôi', '1988-03-28', N'Nam', '0901000035', 'gv035@thcs.edu.vn', N'Phú Yên', 1),
('GV036', N'Nguyễn Thị Yến', '1988-03-28', N'Nữ', '0901000036', 'gv036@thcs.edu.vn', N'Hà Nội', 1),
('GV037', N'Nguyễn Văn Hùng', '1988-03-28', N'Nam', '0901000037', 'gv037@thcs.edu.vn', N'Hải Phòng', 1),
('GV038', N'Nguyễn Thị Mai Anh', '1988-03-28', N'Nữ', '0901000038', 'gv038@thcs.edu.vn', N'Hà Nội', 1),
('GV039', N'Nguyễn Văn Quang', '1988-03-28', N'Nam', '0901000039', 'gv039@thcs.edu.vn', N'Bắc Giang', 1),
('GV040', N'Nguyễn Thị Thu Hà', '1988-03-28', N'Nữ', '0901000040', 'gv040@thcs.edu.vn', N'Kiên Giang',1);

-- 4. LỚP HỌC
INSERT INTO LOP (maLop, tenLop, siSo, maNam, maGVCN, trangThai) VALUES
('6A1', N'Lớp 6A1', 40, 'NH2526', 'GV001', 1),
('6A2', N'Lớp 6A2', 40, 'NH2526', 'GV002', 1),
('6A3', N'Lớp 6A3', 40, 'NH2526', 'GV003', 1),
('6A4', N'Lớp 6A4', 40, 'NH2526', 'GV004', 1),
('6A5', N'Lớp 6A5', 40, 'NH2526', 'GV005', 1),
('6A6', N'Lớp 6A6', 40, 'NH2526', 'GV006', 1),
('6A7', N'Lớp 6A7', 40, 'NH2526', 'GV007', 1),
('6A8', N'Lớp 6A8', 40, 'NH2526', 'GV008', 1),
('6A9', N'Lớp 6A9', 40, 'NH2526', 'GV009', 1),
('6A10', N'Lớp 6A10', 40, 'NH2526', 'GV010', 1),

('7A1', N'Lớp 7A1', 40, 'NH2526', 'GV011', 1),
('7A2', N'Lớp 7A2', 40, 'NH2526', 'GV012', 1),
('7A3', N'Lớp 7A3', 40, 'NH2526', 'GV013', 1),
('7A4', N'Lớp 7A4', 40, 'NH2526', 'GV014', 1),
('7A5', N'Lớp 7A5', 40, 'NH2526', 'GV015', 1),
('7A6', N'Lớp 7A6', 40, 'NH2526', 'GV016', 1),
('7A7', N'Lớp 7A7', 40, 'NH2526', 'GV017', 1),
('7A8', N'Lớp 7A8', 40, 'NH2526', 'GV018', 1),
('7A9', N'Lớp 7A9', 40, 'NH2526', 'GV019', 1),
('7A10', N'Lớp 7A10', 40, 'NH2526', 'GV020', 1),

('8A1', N'Lớp 8A1', 40, 'NH2526', 'GV021', 1),
('8A2', N'Lớp 8A2', 40, 'NH2526', 'GV022', 1),
('8A3', N'Lớp 8A3', 40, 'NH2526', 'GV023', 1),
('8A4', N'Lớp 8A4', 40, 'NH2526', 'GV024', 1),
('8A5', N'Lớp 8A5', 40, 'NH2526', 'GV025', 1),
('8A6', N'Lớp 8A6', 40, 'NH2526', 'GV026', 1),
('8A7', N'Lớp 8A7', 40, 'NH2526', 'GV027', 1),
('8A8', N'Lớp 8A8', 40, 'NH2526', 'GV028', 1),
('8A9', N'Lớp 8A9', 40, 'NH2526', 'GV029', 1),
('8A10', N'Lớp 8A10', 40, 'NH2526', 'GV030', 1),

('9A1', N'Lớp 9A1', 40, 'NH2526', 'GV031', 1),
('9A2', N'Lớp 9A2', 40, 'NH2526', 'GV032', 1),
('9A3', N'Lớp 9A3', 40, 'NH2526', 'GV033', 1),
('9A4', N'Lớp 9A4', 40, 'NH2526', 'GV034', 1),
('9A5', N'Lớp 9A5', 40, 'NH2526', 'GV035', 1),
('9A6', N'Lớp 9A6', 40, 'NH2526', 'GV036', 1),
('9A7', N'Lớp 9A7', 40, 'NH2526', 'GV037', 1),
('9A8', N'Lớp 9A8', 40, 'NH2526', 'GV038', 1),
('9A9', N'Lớp 9A9', 40, 'NH2526', 'GV039', 1),
('9A10', N'Lớp 9A10', 40, 'NH2526', 'GV040', 1);

-- 5. HỌC SINH (Lớp 6A - 40 HS)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('6A1252601', N'Trần Thị An', '2012-01-15', N'Nữ', N'Hà Nội', '0900000001', 'tran.an@student.edu.vn', '6A1', 1),
('6A1252602', N'Nguyễn Văn Bình', '2012-02-20', N'Nam', N'Hà Nội', '0900000002', 'nguyen.binh@student.edu.vn', '6A1', 1),
('6A1252603', N'Lê Thị Cẩm', '2012-03-10', N'Nữ', N'Hà Nội', '0900000003', 'le.cam@student.edu.vn', '6A1', 1),
('6A1252604', N'Phạm Văn Dũng', '2012-04-05', N'Nam', N'Hà Nội', '0900000004', 'pham.dung@student.edu.vn', '6A1', 1),
('6A1252605', N'Hoàng Thị Linh', '2012-05-12', N'Nữ', N'Hà Nội', '0900000005', 'hoang.linh@student.edu.vn', '6A1', 1),
('6A1252606', N'Vũ Văn Nam', '2012-06-18', N'Nam', N'Hà Nội', '0900000006', 'vu.nam@student.edu.vn', '6A1', 1),
('6A1252607', N'Đỗ Thị Oanh', '2012-07-22', N'Nữ', N'Hà Nội', '0900000007', 'do.oanh@student.edu.vn', '6A1', 1),
('6A1252608', N'Bùi Văn Phong', '2012-08-14', N'Nam', N'Hà Nội', '0900000008', 'bui.phong@student.edu.vn', '6A1', 1),
('6A1252609', N'Đinh Thị Quỳnh', '2012-09-09', N'Nữ', N'Hà Nội', '0900000009', 'dinh.quynh@student.edu.vn', '6A1', 1),
('6A1252610', N'Trương Văn Sơn', '2012-10-25', N'Nam', N'Hà Nội', '0900000010', 'truong.son@student.edu.vn', '6A1', 1),
('6A1252611', N'Ngô Thị Trang', '2012-11-30', N'Nữ', N'Hà Nội', '0900000011', 'ngo.trang@student.edu.vn', '6A1', 1),
('6A1252612', N'Phan Văn Tùng', '2012-12-05', N'Nam', N'Hà Nội', '0900000012', 'phan.tung@student.edu.vn', '6A1', 1),
('6A1252613', N'Lý Thị Uyên', '2012-01-08', N'Nữ', N'Hà Nội', '0900000013', 'ly.uyen@student.edu.vn', '6A1', 1),
('6A1252614', N'Dương Văn Việt', '2012-02-14', N'Nam', N'Hà Nội', '0900000014', 'duong.viet@student.edu.vn', '6A1', 1),
('6A1252615', N'Mai Thị Xuân', '2012-03-20', N'Nữ', N'Hà Nội', '0900000015', 'mai.xuan@student.edu.vn', '6A1', 1),
('6A1252616', N'Hà Văn Yên', '2012-04-16', N'Nam', N'Hà Nội', '0900000016', 'ha.yen@student.edu.vn', '6A1', 1),
('6A1252617', N'Trần Thị Lan', '2012-05-22', N'Nữ', N'Hà Nội', '0900000017', 'tran.lan@student.edu.vn', '6A1', 1),
('6A1252618', N'Nguyễn Văn Minh', '2012-06-28', N'Nam', N'Hà Nội', '0900000018', 'nguyen.minh@student.edu.vn', '6A1', 1),
('6A1252619', N'Lê Thị Nga', '2012-07-11', N'Nữ', N'Hà Nội', '0900000019', 'le.nga@student.edu.vn', '6A1', 1),
('6A1252620', N'Phạm Văn Quân', '2012-08-17', N'Nam', N'Hà Nội', '0900000020', 'pham.quan@student.edu.vn', '6A1', 1),
('6A1252621', N'Hoàng Thị Hoa', '2012-09-23', N'Nữ', N'Hà Nội', '0900000021', 'hoang.hoa@student.edu.vn', '6A1', 1),
('6A1252622', N'Vũ Văn Đức', '2012-10-19', N'Nam', N'Hà Nội', '0900000022', 'vu.duc@student.edu.vn', '6A1', 1),
('6A1252623', N'Đỗ Thị Thanh', '2012-11-25', N'Nữ', N'Hà Nội', '0900000023', 'do.thanh@student.edu.vn', '6A1', 1),
('6A1252624', N'Bùi Văn Hùng', '2012-12-30', N'Nam', N'Hà Nội', '0900000024', 'bui.hung@student.edu.vn', '6A1', 1),
('6A1252625', N'Đinh Thị Mai', '2012-01-12', N'Nữ', N'Hà Nội', '0900000025', 'dinh.mai@student.edu.vn', '6A1', 1),
('6A1252626', N'Trương Văn Khánh', '2012-02-18', N'Nam', N'Hà Nội', '0900000026', 'truong.khanh@student.edu.vn', '6A1', 1),
('6A1252627', N'Ngô Thị Linh', '2012-03-24', N'Nữ', N'Hà Nội', '0900000027', 'ngo.linh@student.edu.vn', '6A1', 1),
('6A1252628', N'Phan Văn Long', '2012-04-29', N'Nam', N'Hà Nội', '0900000028', 'phan.long@student.edu.vn', '6A1', 1),
('6A1252629', N'Lý Thị Nhung', '2012-05-05', N'Nữ', N'Hà Nội', '0900000029', 'ly.nhung@student.edu.vn', '6A1', 1),
('6A1252630', N'Dương Văn Phúc', '2012-06-10', N'Nam', N'Hà Nội', '0900000030', 'duong.phuc@student.edu.vn', '6A1', 1),
('6A1252631', N'Mai Thị Hương', '2012-07-15', N'Nữ', N'Hà Nội', '0900000031', 'mai.huong@student.edu.vn', '6A1', 1),
('6A1252632', N'Hà Văn Tâm', '2012-08-21', N'Nam', N'Hà Nội', '0900000032', 'ha.tam@student.edu.vn', '6A1', 1),
('6A1252633', N'Trần Thị Thảo', '2012-09-26', N'Nữ', N'Hà Nội', '0900000033', 'tran.thao@student.edu.vn', '6A1', 1),
('6A1252634', N'Nguyễn Văn Tuấn', '2012-10-02', N'Nam', N'Hà Nội', '0900000034', 'nguyen.tuan@student.edu.vn', '6A1', 1),
('6A1252635', N'Lê Thị Vân', '2012-11-07', N'Nữ', N'Hà Nội', '0900000035', 'le.van@student.edu.vn', '6A1', 1),
('6A1252636', N'Phạm Văn Thắng', '2012-12-13', N'Nam', N'Hà Nội', '0900000036', 'pham.thang@student.edu.vn', '6A1', 1),
('6A1252637', N'Hoàng Thị Ngọc', '2012-01-18', N'Nữ', N'Hà Nội', '0900000037', 'hoang.ngoc@student.edu.vn', '6A1', 1),
('6A1252638', N'Vũ Văn Hải', '2012-02-23', N'Nam', N'Hà Nội', '0900000038', 'vu.hai@student.edu.vn', '6A1', 1),
('6A1252639', N'Đỗ Thị Phương', '2012-03-28', N'Nữ', N'Hà Nội', '0900000039', 'do.phuong@student.edu.vn', '6A1', 1),
('6A1252640', N'Bùi Văn Kiên', '2012-04-03', N'Nam', N'Hà Nội', '0900000040', 'bui.kien@student.edu.vn', '6A1', 1);

-- Lớp 7A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('7A1252601', N'Lê Thị Anh', '2011-01-10', N'Nữ', N'Hà Nội', '0900000041', 'le.anh@student.edu.vn', '7A1', 1),
('7A1252602', N'Phạm Văn Bảo', '2011-02-15', N'Nam', N'Hà Nội', '0900000042', 'pham.bao@student.edu.vn', '7A1', 1),
('7A1252603', N'Hoàng Thị Chi', '2011-03-20', N'Nữ', N'Hà Nội', '0900000043', 'hoang.chi@student.edu.vn', '7A1', 1),
('7A1252604', N'Vũ Văn Duy', '2011-04-25', N'Nam', N'Hà Nội', '0900000044', 'vu.duy@student.edu.vn', '7A1', 1),
('7A1252605', N'Đỗ Thị Hà', '2011-05-30', N'Nữ', N'Hà Nội', '0900000045', 'do.ha@student.edu.vn', '7A1', 1),
('7A1252606', N'Bùi Văn Khoa', '2011-06-12', N'Nam', N'Hà Nội', '0900000046', 'bui.khoa@student.edu.vn', '7A1', 1),
('7A1252607', N'Đinh Thị Ly', '2011-07-18', N'Nữ', N'Hà Nội', '0900000047', 'dinh.ly@student.edu.vn', '7A1', 1),
('7A1252608', N'Trương Văn Mạnh', '2011-08-24', N'Nam', N'Hà Nội', '0900000048', 'truong.manh@student.edu.vn', '7A1', 1),
('7A1252609', N'Ngô Thị Phượng', '2011-09-29', N'Nữ', N'Hà Nội', '0900000049', 'ngo.phuong@student.edu.vn', '7A1', 1),
('7A1252610', N'Phan Văn Quốc', '2011-10-05', N'Nam', N'Hà Nội', '0900000050', 'phan.quoc@student.edu.vn', '7A1', 1),
('7A1252611', N'Nguyễn Thị Mai', '2011-11-10', N'Nữ', N'Hà Nội', '0900000051', 'nguyen.mai@student.edu.vn', '7A1', 1),
('7A1252612', N'Trần Văn Hùng', '2011-12-20', N'Nam', N'Hà Nội', '0900000052', 'tran.hung@student.edu.vn', '7A1', 1);


-- Lớp 8A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('8A1252601', N'Hoàng Thị Bình', '2010-01-08', N'Nữ', N'Hà Nội', '0900000081', 'hoang.binh@student.edu.vn', '8A1', 1),
('8A1252602', N'Vũ Văn Cường', '2010-02-13', N'Nam', N'Hà Nội', '0900000082', 'vu.cuong@student.edu.vn', '8A1', 1),
('8A1252603', N'Đỗ Thị Diệu', '2010-03-18', N'Nữ', N'Hà Nội', '0900000083', 'do.dieu@student.edu.vn', '8A1', 1),
('8A1252604', N'Bùi Văn Hiếu', '2010-04-23', N'Nam', N'Hà Nội', '0900000084', 'bui.hieu@student.edu.vn', '8A1', 1),
('8A1252605', N'Đinh Thị Giang', '2010-05-28', N'Nữ', N'Hà Nội', '0900000085', 'dinh.giang@student.edu.vn', '8A1', 1),
('8A1252606', N'Trương Văn Kiên', '2010-06-10', N'Nam', N'Hà Nội', '0900000086', 'truong.kien@student.edu.vn', '8A1', 1),
('8A1252607', N'Ngô Thị Hương', '2010-07-15', N'Nữ', N'Hà Nội', '0900000087', 'ngo.huong@student.edu.vn', '8A1', 1),
('8A1252608', N'Phan Văn Lộc', '2010-08-20', N'Nam', N'Hà Nội', '0900000088', 'phan.loc@student.edu.vn', '8A1', 1),
('8A1252609', N'Lý Thị My', '2010-09-25', N'Nữ', N'Hà Nội', '0900000089', 'ly.my@student.edu.vn', '8A1', 1),
('8A1252610', N'Dương Văn Nhật', '2010-10-30', N'Nam', N'Hà Nội', '0900000090', 'duong.nhat@student.edu.vn', '8A1', 1);

-- Lớp 9A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('9A1252601', N'Đỗ Thị An', '2009-01-05', N'Nữ', N'Hà Nội', '0900000121', 'do.an@student.edu.vn', '9A1', 1),
('9A1252602', N'Bùi Văn Bách', '2009-02-10', N'Nam', N'Hà Nội', '0900000122', 'bui.bach@student.edu.vn', '9A1', 1),
('9A1252603', N'Đinh Thị Châu', '2009-03-15', N'Nữ', N'Hà Nội', '0900000123', 'dinh.chau@student.edu.vn', '9A1', 1),
('9A1252604', N'Trương Văn Đạt', '2009-04-20', N'Nam', N'Hà Nội', '0900000124', 'truong.dat@student.edu.vn', '9A1', 1),
('9A1252605', N'Ngô Thị Hằng', '2009-05-25', N'Nữ', N'Hà Nội', '0900000125', 'ngo.hang@student.edu.vn', '9A1', 1),
('9A1252606', N'Phan Văn Hoàng', '2009-06-08', N'Nam', N'Hà Nội', '0900000126', 'phan.hoang@student.edu.vn', '9A1', 1),
('9A1252607', N'Lý Thị Khánh', '2009-07-13', N'Nữ', N'Hà Nội', '0900000127', 'ly.khanh@student.edu.vn', '9A1', 1),
('9A1252608', N'Dương Văn Lâm', '2009-08-18', N'Nam', N'Hà Nội', '0900000128', 'duong.lam@student.edu.vn', '9A1', 1),
('9A1252609', N'Mai Thị Ngân', '2009-09-23', N'Nữ', N'Hà Nội', '0900000129', 'mai.ngan@student.edu.vn', '9A1', 1),
('9A1252610', N'Hà Văn Phú', '2009-10-28', N'Nam', N'Hà Nội', '0900000130', 'ha.phu@student.edu.vn', '9A1', 1);

-- 6. MÔN HỌC
INSERT INTO MON (maMon, tenMon, trangThai) VALUES
('TOAN', N'Toán', 1),
('VAN', N'Ngữ Văn', 1),
('ANH', N'Tiếng Anh', 1),
('LY', N'Vật Lý', 1),
('HOA', N'Hóa Học', 1),
('SINH', N'Sinh Học', 1),
('SU', N'Lịch Sử', 1),
('DIA', N'Địa Lý', 1),
('GDCD', N'Giáo Dục Công Dân', 1),
('TD', N'Thể Dục', 1),
('TIN', N'Tin học', 1),
('GDQP', N'Giáo dục quốc phòng', 1);

-- 7. CHI TIẾT MÔN
INSERT INTO CHITIETMON (maChiTiet, maMon, tenChiTiet, heSo, trangThai) VALUES
('TOAN_TX', 'TOAN', N'Thường xuyên', 1, 1),
('TOAN_GK', 'TOAN', N'Giữa kỳ', 2, 1),
('TOAN_CK', 'TOAN', N'Cuối kỳ', 3, 1),
('VAN_TX', 'VAN', N'Thường xuyên', 1, 1),
('VAN_GK', 'VAN', N'Giữa kỳ', 2, 1),
('VAN_CK', 'VAN', N'Cuối kỳ', 3, 1),
('ANH_TX', 'ANH', N'Thường xuyên', 1, 1),
('ANH_GK', 'ANH', N'Giữa kỳ', 2, 1),
('ANH_CK', 'ANH', N'Cuối kỳ', 3, 1),
('LY_TX', 'LY', N'Thường xuyên', 1, 1),
('LY_GK', 'LY', N'Giữa kỳ', 2, 1),
('LY_CK', 'LY', N'Cuối kỳ', 3, 1),
('HOA_TX', 'HOA', N'Thường xuyên', 1, 1),
('HOA_GK', 'HOA', N'Giữa kỳ', 2, 1),
('HOA_CK', 'HOA', N'Cuối kỳ', 3, 1),
('SINH_TX', 'SINH', N'Thường xuyên', 1, 1),
('SINH_GK', 'SINH', N'Giữa kỳ', 2, 1),
('SINH_CK', 'SINH', N'Cuối kỳ', 3, 1),
('SU_TX', 'SU', N'Thường xuyên', 1, 1),
('SU_GK', 'SU', N'Giữa kỳ', 2, 1),
('SU_CK', 'SU', N'Cuối kỳ', 3, 1),
('DIA_TX', 'DIA', N'Thường xuyên', 1, 1),
('DIA_GK', 'DIA', N'Giữa kỳ', 2, 1),
('DIA_CK', 'DIA', N'Cuối kỳ', 3, 1),
('GDCD_TX', 'GDCD', N'Thường xuyên', 1, 1),
('GDCD_GK', 'GDCD', N'Giữa kỳ', 2, 1),
('GDCD_CK', 'GDCD', N'Cuối kỳ', 3, 1),
('TD_TX', 'TD', N'Thường xuyên', 1, 1),
('TD_GK', 'TD', N'Giữa kỳ', 2, 1),
('TD_CK', 'TD', N'Cuối kỳ', 3, 1),
('TIN_TX', 'TIN', N'Thường xuyên', 1, 1),
('TIN_GK', 'TIN', N'Giữa kỳ', 2, 1),
('TIN_CK', 'TIN', N'Cuối kỳ', 3, 1),
('GDQP_TX', 'GDQP', N'Thường xuyên', 1, 1),
('GDQP_GK', 'GDQP', N'Giữa kỳ', 2, 1),
('GDQP_CK', 'GDQP', N'Cuối kỳ', 3, 1);

-- 8. PHÂN CÔNG GIẢNG DẠY
INSERT INTO PHANCONG (maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai) VALUES
-- Toán
('PC001', 'GV001', 'TOAN', '6A1', 'NH2526', N'Giảng dạy chính', 1),
('PC002', 'GV002', 'TOAN', '7A2', 'NH2526', N'Giảng dạy chính', 1),
('PC003', 'GV003', 'TOAN', '8A3', 'NH2526', N'Giảng dạy chính', 1),
('PC004', 'GV004', 'TOAN', '9A4', 'NH2526', N'Giảng dạy chính', 1),
-- Văn
('PC005', 'GV005', 'VAN', '6A2', 'NH2526', N'Giảng dạy chính', 1),
('PC006', 'GV006', 'VAN', '7A3', 'NH2526', N'Giảng dạy chính', 1),
('PC007', 'GV007', 'VAN', '8A4', 'NH2526', N'Giảng dạy chính', 1),
('PC008', 'GV008', 'VAN', '9A5', 'NH2526', N'Giảng dạy chính', 1),
-- Anh
('PC009', 'GV008', 'ANH', '6A3', 'NH2526', N'Giảng dạy chính', 1),
('PC010', 'GV010', 'ANH', '7A4', 'NH2526', N'Giảng dạy chính', 1),
('PC011', 'GV011', 'ANH', '8A5', 'NH2526', N'Giảng dạy chính', 1),
('PC012', 'GV012', 'ANH', '9A6', 'NH2526', N'Giảng dạy chính', 1),
-- Lý
('PC013', 'GV013', 'LY', '6A4', 'NH2526', N'Giảng dạy chính', 1),
('PC014', 'GV014', 'LY', '7A5', 'NH2526', N'Giảng dạy chính', 1),
('PC015', 'GV015', 'LY', '8A6', 'NH2526', N'Giảng dạy chính', 1),
('PC016', 'GV016', 'LY', '9A7', 'NH2526', N'Giảng dạy chính', 1),
-- Hóa
('PC017', 'GV017', 'HOA', '6A5', 'NH2526', N'Giảng dạy chính', 1),
('PC018', 'GV018', 'HOA', '7A6', 'NH2526', N'Giảng dạy chính', 1),
('PC019', 'GV019', 'HOA', '8A7', 'NH2526', N'Giảng dạy chính', 1),
('PC020', 'GV020', 'HOA', '9A8', 'NH2526', N'Giảng dạy chính', 1),
-- Sinh
('PC021', 'GV021', 'SINH', '6A6', 'NH2526', N'Giảng dạy chính', 1),
('PC022', 'GV022', 'SINH', '7A7', 'NH2526', N'Giảng dạy chính', 1),
('PC023', 'GV023', 'SINH', '8A8', 'NH2526', N'Giảng dạy chính', 1),
('PC024', 'GV024', 'SINH', '9A9', 'NH2526', N'Giảng dạy chính', 1),
-- Sử
('PC025', 'GV025', 'SU', '6A7', 'NH2526', N'Giảng dạy chính', 1),
('PC026', 'GV026', 'SU', '7A8', 'NH2526', N'Giảng dạy chính', 1),
('PC027', 'GV027', 'SU', '8A9', 'NH2526', N'Giảng dạy chính', 1),
('PC028', 'GV028', 'SU', '9A10', 'NH2526', N'Giảng dạy chính', 1),
-- Địa
('PC029', 'GV029', 'DIA', '6A8', 'NH2526', N'Giảng dạy chính', 1),
('PC030', 'GV030', 'DIA', '7A9', 'NH2526', N'Giảng dạy chính', 1),
('PC031', 'GV031', 'DIA', '8A10', 'NH2526', N'Giảng dạy chính', 1),
('PC032', 'GV032', 'DIA', '9A10', 'NH2526', N'Giảng dạy chính', 1),
-- GDCD
('PC033', 'GV033', 'GDCD', '6A9', 'NH2526', N'Giảng dạy chính', 1),
('PC034', 'GV034', 'GDCD', '7A10', 'NH2526', N'Giảng dạy chính', 1),
('PC035', 'GV035', 'GDCD', '8A10', 'NH2526', N'Giảng dạy chính', 1),
('PC036', 'GV036', 'GDCD', '9A10', 'NH2526', N'Giảng dạy chính', 1),

-- TIN
('PC037', 'GV001', 'TIN', '6A10', 'NH2526', N'Giảng dạy chính', 1),
('PC038', 'GV002', 'TIN', '7A10', 'NH2526', N'Giảng dạy chính', 1),
('PC039', 'GV003', 'TIN', '8A10', 'NH2526', N'Giảng dạy chính', 1),
('PC040', 'GV004', 'TIN', '9A10', 'NH2526', N'Giảng dạy chính', 1),

-- TD
('PC041', 'GV033', 'TD', '6A1', 'NH2526', N'Giảng dạy chính', 1),
('PC042', 'GV034', 'TD', '7A2', 'NH2526', N'Giảng dạy chính', 1),
('PC043', 'GV035', 'TD', '8A3', 'NH2526', N'Giảng dạy chính', 1),
('PC044', 'GV036', 'TD', '9A4', 'NH2526', N'Giảng dạy chính', 1),

-- GDQP
('PC045', 'GV037', 'GDQP', '6A9', 'NH2526', N'Giảng dạy chính', 1),
('PC046', 'GV038', 'GDQP', '7A10', 'NH2526', N'Giảng dạy chính', 1),
('PC047', 'GV039', 'GDQP', '8A10', 'NH2526', N'Giảng dạy chính', 1),
('PC048', 'GV040', 'GDQP', '9A10', 'NH2526', N'Giảng dạy chính', 1);

-- Phân công Văn cho lớp 6A1
INSERT INTO PHANCONG (maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai)
VALUES ('PC049', 'GV005', 'VAN', '6A1', 'NH2526', N'Giảng dạy chính', 1);

-- Phân công Anh cho lớp 6A1
INSERT INTO PHANCONG (maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai)
VALUES ('PC050', 'GV010', 'ANH', '6A1', 'NH2526', N'Giảng dạy chính', 1);

-- 9. THỜI KHÓA BIỂU
INSERT INTO THOIKHOABIEU (maTKB, maLop, maHocKy, ngayBatDau, ngayKetThuc, trangThai) VALUES
('TKB_6A1_HK1', '6A1', 'HK1_2526', '2025-09-05', '2025-01-10', 1),
('TKB_7A1_HK1', '7A1', 'HK1_2526', '2025-09-05', '2025-01-10', 1),
('TKB_8A1_HK1', '8A1', 'HK1_2526', '2025-09-05', '2025-01-10', 1),
('TKB_9A1_HK1', '9A1', 'HK1_2526', '2025-09-05', '2025-01-10', 1);

-- 10. CHI TIẾT TIẾT HỌC (mẫu lớp 6A1 - Thứ 2)
INSERT INTO CHITIETTIET (maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai) VALUES
('TKB6A1_T2_1', 'TKB_6A1_HK1', 'TOAN', N'Thứ 2', 1, 'P101', '07:00', '07:45', 1),
('TKB6A1_T2_2', 'TKB_6A1_HK1', 'TOAN', N'Thứ 2', 2, 'P101', '07:50', '08:35', 1),
('TKB6A1_T2_3', 'TKB_6A1_HK1', 'VAN', N'Thứ 2', 3, 'P101', '08:40', '09:25', 1),
('TKB6A1_T2_4', 'TKB_6A1_HK1', 'VAN', N'Thứ 2', 4, 'P101', '09:45', '10:30', 1),
('TKB6A1_T2_5', 'TKB_6A1_HK1', 'ANH', N'Thứ 2', 5, 'P101', '10:35', '11:20', 1);

-- 11. ĐIỂM MẪU (5 HS đầu lớp 6A)
INSERT INTO DIEM (maDiem, maHS, maMon, maHocKy, diemThuongXuyen, diemGiuaKy, diemCuoiKy, diemTBMonHocKy)
VALUES
-- HS 6A1252601
('D_6A1252601_TOAN', '6A1252601', 'TOAN', 'HK1_2526', 7.5, 8.0, 8.5, 8.08),
('D_6A1252601_VAN',  '6A1252601', 'VAN',  'HK1_2526', 7.0, 7.5, 8.0, 7.58),
('D_6A1252601_ANH',  '6A1252601', 'ANH',  'HK1_2526', 8.0, 8.0, 8.5, 8.17),

-- HS 6A1252602
('D_6A1252602_TOAN', '6A1252602', 'TOAN', 'HK1_2526', 8.0, 8.5, 9.0, 8.58),
('D_6A1252602_VAN',  '6A1252602', 'VAN',  'HK1_2526', 7.5, 8.0, 8.5, 8.08),
('D_6A1252602_ANH',  '6A1252602', 'ANH',  'HK1_2526', 8.5, 9.0, 9.0, 8.83),

-- HS 6A1252603
('D_6A1252603_TOAN', '6A1252603', 'TOAN', 'HK1_2526', 6.5, 7.0, 7.5, 7.08),
('D_6A1252603_VAN',  '6A1252603', 'VAN',  'HK1_2526', 6.0, 6.5, 7.0, 6.58),
('D_6A1252603_ANH',  '6A1252603', 'ANH',  'HK1_2526', 7.0, 7.0, 7.5, 7.17),

-- HS 6A1252604
('D_6A1252604_TOAN', '6A1252604', 'TOAN', 'HK1_2526', 9.0, 9.5, 10.0, 9.58),
('D_6A1252604_VAN',  '6A1252604', 'VAN',  'HK1_2526', 8.5, 9.0, 9.5, 9.08),
('D_6A1252604_ANH',  '6A1252604', 'ANH',  'HK1_2526', 9.0, 9.5, 10.0, 9.58),

-- HS 6A1252605
('D_6A1252605_TOAN', '6A1252605', 'TOAN', 'HK1_2526', 7.0, 7.5, 8.0, 7.58),
('D_6A1252605_VAN',  '6A1252605', 'VAN',  'HK1_2526', 7.5, 8.0, 8.5, 8.08),
('D_6A1252605_ANH',  '6A1252605', 'ANH',  'HK1_2526', 7.0, 7.5, 8.0, 7.58);

-- 12. HẠNH KIỂM
INSERT INTO HANHKIEM (maHanhKiem, maHS, maHocKy, xepLoai, soLanViPham, nhanXet) VALUES
('HK_6A01_HK1', '6A01', 'HK1_2425', N'Tốt', 0, N'Học sinh có ý thức tốt'),
('HK_6A02_HK1', '6A02', 'HK1_2425', N'Tốt', 0, N'Học sinh chăm chỉ'),
('HK_6A03_HK1', '6A03', 'HK1_2425', N'Khá', 1, N'Cần chú ý hơn'),
('HK_6A04_HK1', '6A04', 'HK1_2425', N'Tốt', 0, N'Học sinh giỏi và có ý thức cao'),
('HK_6A05_HK1', '6A05', 'HK1_2425', N'Tốt', 0, N'Học sinh tích cực');

-- 13. VI PHẠM
INSERT INTO VIPHAM (maViPham, maHS, maHocKy, ngayViPham, noiDung, mucDo, trangThai) VALUES
('VP001', '6A03', 'HK1_2425', '2024-10-15', N'Đi học muộn', N'Nhẹ', 1),
('VP002', '6A05', 'HK1_2425', '2024-11-20', N'Quên làm bài tập', N'Nhẹ', 1);

-- 14. XẾP LOẠI
INSERT INTO XEPLOAI (maXepLoai, maHS, maHocKy, xepLoaiHocLuc, xepLoaiHanhKiem, diemTBChung, nhanXet, duocLenLop) VALUES
('XL_6A01_HK1', '6A01', 'HK1_2425', N'Khá', N'Tốt', 7.8, N'Học sinh có học lực khá', 1),
('XL_6A02_HK1', '6A02', 'HK1_2425', N'Khá', N'Tốt', 8.3, N'Học sinh tiến bộ tốt', 1),
('XL_6A03_HK1', '6A03', 'HK1_2425', N'Trung bình', N'Khá', 6.8, N'Cần cố gắng hơn', 1),
('XL_6A04_HK1', '6A04', 'HK1_2425', N'Giỏi', N'Tốt', 9.3, N'Học sinh xuất sắc', 1),
('XL_6A05_HK1', '6A05', 'HK1_2425', N'Khá', N'Tốt', 7.8, N'Học sinh ổn định', 1);

INSERT INTO PHUHUYNH (maPH, hoTen, soDienThoai, email, ngheNghiep, trangThai) VALUES
('PH001', N'Nguyễn Văn An', '0901000001', 'nguyenvanan.ph001@example.com', N'Giáo viên', 1),
('PH002', N'Trần Thị Bình', '0901000002', 'tranthibinh.ph002@example.com', N'Kỹ sư', 1),
('PH003', N'Lê Văn Cường', '0901000003', 'levancuong.ph003@example.com', N'Bác sĩ', 1),
('PH004', N'Phạm Thị Dung', '0901000004', 'phamthidung.ph004@example.com', N'Nông dân', 1),
('PH005', N'Hoàng Văn Hà', '0901000005', 'hoangvanha.ph005@example.com', N'Công nhân', 1),
('PH006', N'Vũ Thị Hải', '0901000006', 'vuthihai.ph006@example.com', N'Kinh doanh', 1),
('PH007', N'Đặng Văn Hạnh', '0901000007', 'dangvanhanh.ph007@example.com', N'Giáo viên', 1),
('PH008', N'Bùi Thị Hiếu', '0901000008', 'buithihieu.ph008@example.com', N'Kỹ sư', 1),
('PH009', N'Đỗ Văn Hoa', '0901000009', 'dovanhoa.ph009@example.com', N'Bác sĩ', 1),
('PH010', N'Hồ Thị Hương', '0901000010', 'hothihuong.ph010@example.com', N'Nông dân', 1),
('PH011', N'Ngô Văn Khánh', '0901000011', 'ngovankhanh.ph011@example.com', N'Công nhân', 1),
('PH012', N'Dương Thị Lan', '0901000012', 'duongthilan.ph012@example.com', N'Kinh doanh', 1),
('PH013', N'Lý Văn Linh', '0901000013', 'lyvanlinh.ph013@example.com', N'Giáo viên', 1),
('PH014', N'Vương Thị Long', '0901000014', 'vuongthilong.ph014@example.com', N'Kỹ sư', 1),
('PH015', N'Đinh Văn Mạnh', '0901000015', 'dinhvanmanh.ph015@example.com', N'Bác sĩ', 1),
('PH016', N'Nguyễn Thị Minh', '0901000016', 'nguyenthiminh.ph016@example.com', N'Nông dân', 1),
('PH017', N'Trần Văn Nam', '0901000017', 'tranvannam.ph017@example.com', N'Công nhân', 1),
('PH018', N'Lê Thị Ngọc', '0901000018', 'lethingoc.ph018@example.com', N'Kinh doanh', 1),
('PH019', N'Phạm Văn Phong', '0901000019', 'phamvanphong.ph019@example.com', N'Giáo viên', 1),
('PH020', N'Hoàng Thị Phương', '0901000020', 'hoangthiphuong.ph020@example.com', N'Kỹ sư', 1),
('PH021', N'Vũ Văn Quân', '0901000021', 'vuvanquan.ph021@example.com', N'Bác sĩ', 1),
('PH022', N'Đặng Thị Quỳnh', '0901000022', 'dangthiquynh.ph022@example.com', N'Nông dân', 1),
('PH023', N'Bùi Văn Sơn', '0901000023', 'buivanson.ph023@example.com', N'Công nhân', 1),
('PH024', N'Đỗ Thị Thảo', '0901000024', 'dothithao.ph024@example.com', N'Kinh doanh', 1),
('PH025', N'Hồ Văn Thắng', '0901000025', 'hovanthang.ph025@example.com', N'Giáo viên', 1),
('PH026', N'Ngô Thị Thi', '0901000026', 'ngothithi.ph026@example.com', N'Kỹ sư', 1),
('PH027', N'Dương Văn Thủy', '0901000027', 'duongvanthuy.ph027@example.com', N'Bác sĩ', 1),
('PH028', N'Lý Thị Trang', '0901000028', 'lythitrang.ph028@example.com', N'Nông dân', 1),
('PH029', N'Vương Văn Tùng', '0901000029', 'vuongvantung.ph029@example.com', N'Công nhân', 1),
('PH030', N'Đinh Thị Tuyết', '0901000030', 'dinhthituyet.ph030@example.com', N'Kinh doanh', 1),
('PH031', N'Nguyễn Văn Vân', '0901000031', 'nguyenvanvan.ph031@example.com', N'Giáo viên', 1),
('PH032', N'Trần Thị Yến', '0901000032', 'tranthiyen.ph032@example.com', N'Kỹ sư', 1),
('PH033', N'Lê Văn An', '0901000033', 'levanan.ph033@example.com', N'Bác sĩ', 1),
('PH034', N'Phạm Thị Bình', '0901000034', 'phamthibinh.ph034@example.com', N'Nông dân', 1),
('PH035', N'Hoàng Văn Cường', '0901000035', 'hoangvancuong.ph035@example.com', N'Công nhân', 1),
('PH036', N'Vũ Thị Dung', '0901000036', 'vuthidung.ph036@example.com', N'Kinh doanh', 1),
('PH037', N'Đặng Văn Hà', '0901000037', 'dangvanha.ph037@example.com', N'Giáo viên', 1),
('PH038', N'Bùi Thị Hải', '0901000038', 'buithihai.ph038@example.com', N'Kỹ sư', 1),
('PH039', N'Đỗ Văn Hạnh', '0901000039', 'dovanhanh.ph039@example.com', N'Bác sĩ', 1),
('PH040', N'Hồ Thị Hiếu', '0901000040', 'hothihieu.ph040@example.com', N'Nông dân', 1),
('PH041', N'Ngô Văn Hoa', '0901000041', 'ngovanhoa.ph041@example.com', N'Công nhân', 1),
('PH042', N'Dương Thị Hương', '0901000042', 'duongthihuong.ph042@example.com', N'Kinh doanh', 1),
('PH043', N'Lý Văn Khánh', '0901000043', 'lyvankhanh.ph043@example.com', N'Giáo viên', 1),
('PH044', N'Vương Thị Lan', '0901000044', 'vuongthilan.ph044@example.com', N'Kỹ sư', 1),
('PH045', N'Đinh Văn Linh', '0901000045', 'dinhvanlinh.ph045@example.com', N'Bác sĩ', 1),
('PH046', N'Nguyễn Thị Long', '0901000046', 'nguyenthilong.ph046@example.com', N'Nông dân', 1),
('PH047', N'Trần Văn Mạnh', '0901000047', 'tranvanmanh.ph047@example.com', N'Công nhân', 1),
('PH048', N'Lê Thị Minh', '0901000048', 'lethiminh.ph048@example.com', N'Kinh doanh', 1),
('PH049', N'Phạm Văn Nam', '0901000049', 'phamvannam.ph049@example.com', N'Giáo viên', 1),
('PH050', N'Hoàng Thị Ngọc', '0901000050', 'hoangthingoc.ph050@example.com', N'Kỹ sư', 1),
('PH051', N'Vũ Văn Phong', '0901000051', 'vuvanphong.ph051@example.com', N'Bác sĩ', 1),
('PH052', N'Đặng Thị Phương', '0901000052', 'dangthiphuong.ph052@example.com', N'Nông dân', 1),
('PH053', N'Bùi Văn Quân', '0901000053', 'buivanquan.ph053@example.com', N'Công nhân', 1),
('PH054', N'Đỗ Thị Quỳnh', '0901000054', 'dothiquynh.ph054@example.com', N'Kinh doanh', 1),
('PH055', N'Hồ Văn Sơn', '0901000055', 'hovanson.ph055@example.com', N'Giáo viên', 1),
('PH056', N'Ngô Thị Thảo', '0901000056', 'ngothithao.ph056@example.com', N'Kỹ sư', 1),
('PH057', N'Dương Văn Thắng', '0901000057', 'duongvanthang.ph057@example.com', N'Bác sĩ', 1),
('PH058', N'Lý Thị Thi', '0901000058', 'lythithi.ph058@example.com', N'Nông dân', 1),
('PH059', N'Vương Văn Thủy', '0901000059', 'vuongvanthuy.ph059@example.com', N'Công nhân', 1),
('PH060', N'Đinh Thị Trang', '0901000060', 'dinhthitrang.ph060@example.com', N'Kinh doanh', 1),
('PH061', N'Nguyễn Văn Tùng', '0901000061', 'nguyenvantung.ph061@example.com', N'Giáo viên', 1),
('PH062', N'Trần Thị Tuyết', '0901000062', 'tranthituyet.ph062@example.com', N'Kỹ sư', 1),
('PH063', N'Lê Văn Vân', '0901000063', 'levanvan.ph063@example.com', N'Bác sĩ', 1),
('PH064', N'Phạm Thị Yến', '0901000064', 'phamthiyen.ph064@example.com', N'Nông dân', 1),
('PH065', N'Hoàng Văn An', '0901000065', 'hoangvanan.ph065@example.com', N'Công nhân', 1),
('PH066', N'Vũ Thị Bình', '0901000066', 'vuthibinh.ph066@example.com', N'Kinh doanh', 1),
('PH067', N'Đặng Văn Cường', '0901000067', 'dangvancuong.ph067@example.com', N'Giáo viên', 1),
('PH068', N'Bùi Thị Dung', '0901000068', 'buithidung.ph068@example.com', N'Kỹ sư', 1),
('PH069', N'Đỗ Văn Hà', '0901000069', 'dovanha.ph069@example.com', N'Bác sĩ', 1),
('PH070', N'Hồ Thị Hải', '0901000070', 'hothihai.ph070@example.com', N'Nông dân', 1),
('PH071', N'Ngô Văn Hạnh', '0901000071', 'ngovanhanh.ph071@example.com', N'Công nhân', 1),
('PH072', N'Dương Thị Hiếu', '0901000072', 'duongthihieu.ph072@example.com', N'Kinh doanh', 1),
('PH073', N'Lý Văn Hoa', '0901000073', 'lyvanhoa.ph073@example.com', N'Giáo viên', 1),
('PH074', N'Vương Thị Hương', '0901000074', 'vuongthihuong.ph074@example.com', N'Kỹ sư', 1),
('PH075', N'Đinh Văn Khánh', '0901000075', 'dinhvankhanh.ph075@example.com', N'Bác sĩ', 1),
('PH076', N'Nguyễn Thị Lan', '0901000076', 'nguyenthilan.ph076@example.com', N'Nông dân', 1),
('PH077', N'Trần Văn Linh', '0901000077', 'tranvanlinh.ph077@example.com', N'Công nhân', 1),
('PH078', N'Lê Thị Long', '0901000078', 'lethilong.ph078@example.com', N'Kinh doanh', 1),
('PH079', N'Phạm Văn Mạnh', '0901000079', 'phamvanmanh.ph079@example.com', N'Giáo viên', 1),
('PH080', N'Hoàng Thị Minh', '0901000080', 'hoangthiminh.ph080@example.com', N'Kỹ sư', 1),
('PH081', N'Vũ Văn Nam', '0901000081', 'vuvannam.ph081@example.com', N'Bác sĩ', 1),
('PH082', N'Đặng Thị Ngọc', '0901000082', 'dangthingoc.ph082@example.com', N'Nông dân', 1),
('PH083', N'Bùi Văn Phong', '0901000083', 'buivanphong.ph083@example.com', N'Công nhân', 1),
('PH084', N'Đỗ Thị Phương', '0901000084', 'dothiphuong.ph084@example.com', N'Kinh doanh', 1),
('PH085', N'Hồ Văn Quân', '0901000085', 'hovanquan.ph085@example.com', N'Giáo viên', 1),
('PH086', N'Ngô Thị Quỳnh', '0901000086', 'ngothiquynh.ph086@example.com', N'Kỹ sư', 1),
('PH087', N'Dương Văn Sơn', '0901000087', 'duongvanson.ph087@example.com', N'Bác sĩ', 1),
('PH088', N'Lý Thị Thảo', '0901000088', 'lythithao.ph088@example.com', N'Nông dân', 1),
('PH089', N'Vương Văn Thắng', '0901000089', 'vuongvanthang.ph089@example.com', N'Công nhân', 1),
('PH090', N'Đinh Thị Thi', '0901000090', 'dinhthithi.ph090@example.com', N'Kinh doanh', 1),
('PH091', N'Nguyễn Văn Thủy', '0901000091', 'nguyenvanthuy.ph091@example.com', N'Giáo viên', 1),
('PH092', N'Trần Thị Trang', '0901000092', 'tranthitrang.ph092@example.com', N'Kỹ sư', 1),
('PH093', N'Lê Văn Tùng', '0901000093', 'levantung.ph093@example.com', N'Bác sĩ', 1),
('PH094', N'Phạm Thị Tuyết', '0901000094', 'phamthituyet.ph094@example.com', N'Nông dân', 1),
('PH095', N'Hoàng Văn Vân', '0901000095', 'hoangvanvan.ph095@example.com', N'Công nhân', 1),
('PH096', N'Vũ Thị Yến', '0901000096', 'vuthiyen.ph096@example.com', N'Kinh doanh', 1),
('PH097', N'Đặng Văn An', '0901000097', 'dangvanan.ph097@example.com', N'Giáo viên', 1),
('PH098', N'Bùi Thị Bình', '0901000098', 'buithibinh.ph098@example.com', N'Kỹ sư', 1),
('PH099', N'Đỗ Văn Cường', '0901000099', 'dovancuong.ph099@example.com', N'Bác sĩ', 1),
('PH100', N'Hồ Thị Dung', '0901000100', 'hothidung.ph100@example.com', N'Nông dân', 1);

--lệnh giúp sửa lại cái table diem (đang có machitiet như sau)
SELECT name 
FROM sys.foreign_keys 
WHERE parent_object_id = OBJECT_ID('DIEM');
--tìm xem các khóa ngoại
--xóa cái fk machitiet đã tìm được vd như bên dưới
ALTER TABLE DIEM DROP CONSTRAINT FK__DIEM__maChiTiet__74AE54BC;
--bỏ cột machitiet 
ALTER TABLE DIEM DROP COLUMN maChiTiet;
--thêm vào cột mã môn
ALTER TABLE DIEM ADD maMon VARCHAR(50);
--thêm kháo ngoại vào 
ALTER TABLE DIEM ADD CONSTRAINT FK_DIEM_MON FOREIGN KEY (maMon) REFERENCES MON(maMon) on delete no action;
--chỉnh lại phần insert dữ liêu ở trên trên là maMon thay vì machitiet để đảm bảo nhất quán

UPDATE LOP
SET trangThai = 1
WHERE maLop = '6A1';  -- hoặc điều kiện phù hợp

UPDATE PHANCONG
SET trangThai = 1
WHERE maPC = 'PC001';  -- hoặc điều kiện phù hợp

UPDATE PHUHUYNH
SET trangThai = 1
WHERE maPH = 'PH001';  -- hoặc điều kiện phù hợp

UPDATE HOCSINH
SET trangThai = 1
WHERE maHS = '6A1252601';  -- hoặc điều kiện phù hợp

UPDATE GIAOVIEN
SET trangThai = 1
WHERE maGV = 'GV001';  -- hoặc điều kiện phù hợp

SELECT * from XEPLOAI;
SELECT * from NAM;
select * from HOCKY;
select * from LOP;
delete from lop where malop in ('6A11');
select * from HOCSINH;
select * from GIAOVIEN;
select * from THOIKHOABIEU;
select * from CHITIETTIET;
delete from PHUHUYNH;
DELETE FROM ChiTietTiet WHERE MaChiTiet IN ('TKB_6A1_HK1_Th2_T1', 'TKB_6A1_HK1_Th2_T6');
select * from MON;
select * from CHITIETMON;
select * from PHUHUYNH;
select * from HOCSINH_PHUHUYNH;
select * from PHANCONG;
-- Lấy thời khóa biểu lớp 6A1 học kỳ 1
SELECT * FROM THOIKHOABIEU WHERE maLop = '6A1' AND maHocKy = 'HK1_2526';

-- Lấy chi tiết tiết học lớp 6A1
SELECT CT.maChiTiet, CT.thu, CT.tiet, CT.phongHoc, CT.gioBatDau, CT.gioKetThuc,
       MON.tenMon, GV.hoTen AS giaoVien
FROM CHITIETTIET CT
JOIN THOIKHOABIEU TKB ON CT.maTKB = TKB.maTKB
JOIN MON ON CT.maMon = MON.maMon
JOIN PHANCONG PC ON PC.maMon = CT.maMon AND PC.maLop = TKB.maLop
JOIN GIAOVIEN GV ON PC.maGV = GV.maGV
WHERE TKB.maLop = '6A1' AND TKB.maHocKy = 'HK1_2526';


-- Năm học
CREATE PROCEDURE sp_getAllActiveNamHoc
AS
BEGIN
    SELECT maNam, tenNam, trangThai
    FROM NAM
    WHERE trangThai = 1;
END;
GO
-- Học kỳ
CREATE PROCEDURE sp_getAllActiveHocKy
AS
BEGIN
    SELECT maHK, tenHK, maNam, ngayBatDau, ngayKetThuc, trangThai
    FROM HOCKY
    WHERE trangThai = 1;
END;
GO
-- Lớp
CREATE PROCEDURE sp_getAllActiveLop
AS
BEGIN
    SELECT maLop, tenLop, siSo, maNam, maGVCN, trangThai
    FROM LOP
    WHERE trangThai = 1;
END;
GO
-- Giáo viên
CREATE PROCEDURE sp_getAllActiveGiaoVien
AS
BEGIN
    SELECT maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai
    FROM GIAOVIEN
    WHERE trangThai = 1;
END;
GO
-- Môn học
CREATE PROCEDURE sp_getAllActiveMon
AS
BEGIN
    SELECT maMon, tenMon, trangThai
    FROM MON
    WHERE trangThai = 1;
END;
GO

--Chitiettiet
CREATE PROCEDURE sp_getAllActiveChiTietTiet
AS
BEGIN
    SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai
    FROM CHITIETTIET
    WHERE trangThai = 1;
END;
GO

CREATE PROCEDURE sp_getChiTietTietByMaTKB
    @maTKB NVARCHAR(50)
AS
BEGIN
    SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai
    FROM CHITIETTIET
    WHERE maTKB = @maTKB AND trangThai = 1;
END;
GO

-- Lấy danh sách học sinh kèm tên lớp
CREATE PROCEDURE sp_getAllActiveHocSinh
AS
BEGIN
    SELECT hs.*, l.tenLop 
    FROM HOCSINH hs
    LEFT JOIN LOP l ON hs.maLop = l.maLop
    WHERE hs.trangThai = 1;
END;
GO

-- Combo lớp (dùng khi thêm/sửa học sinh)
CREATE PROCEDURE sp_getLopCombo
AS
BEGIN
    SELECT maLop, tenLop FROM LOP WHERE trangThai = 1;
END;
GO

-- Lấy danh sách chi tiết môn kèm tên môn
CREATE PROCEDURE sp_getAllActiveChiTietMon
AS
BEGIN
    SELECT ct.*, m.tenMon 
    FROM CHITIETMON ct
    JOIN MON m ON ct.maMon = m.maMon
    WHERE ct.trangThai = 1;
END;
GO

CREATE TRIGGER trg_UpdateSiSo
ON HOCSINH
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Cập nhật sĩ số cho các lớp bị ảnh hưởng bởi inserted (thêm/sửa)
    UPDATE LOP
    SET siSo = (
        SELECT COUNT(*)
        FROM HOCSINH
        WHERE maLop = LOP.maLop AND trangThai = 1
    )
    WHERE maLop IN (
        SELECT DISTINCT maLop FROM inserted WHERE maLop IS NOT NULL
    );
    
    -- Cập nhật sĩ số cho các lớp bị ảnh hưởng bởi deleted (xóa/sửa)
    UPDATE LOP
    SET siSo = (
        SELECT COUNT(*)
        FROM HOCSINH
        WHERE maLop = LOP.maLop AND trangThai = 1
    )
    WHERE maLop IN (
        SELECT DISTINCT maLop FROM deleted WHERE maLop IS NOT NULL
    );
END;


UPDATE LOP
SET siSo = (SELECT COUNT(*) FROM HOCSINH WHERE maLop = LOP.maLop AND trangThai = 1);

	
-- Combo môn (có thể dùng sp_getAllActiveMon)

PRINT N'✅ Đã tạo xong database với dữ liệu mẫu!';
PRINT N'';
PRINT N'📊 THỐNG KÊ:';
PRINT N'- 4 năm học (2021-2025)';
PRINT N'- 8 học kỳ';
PRINT N'- 4 lớp (6A, 7A, 8A, 9A)';
PRINT N'- 13 giáo viên';
PRINT N'- 9 môn học';
PRINT N'- 40 học sinh lớp 6A (đầy đủ)';
PRINT N'- 10 học sinh mẫu cho các lớp 7A, 8A, 9A';
PRINT N'';
PRINT N'📝 GHI CHÚ:';
PRINT N'- Các lớp 7A, 8A, 9A chỉ có 10 HS mẫu (còn 30 HS nữa cần thêm)';
PRINT N'- Dữ liệu điểm, hạnh kiểm, xếp loại cho 5 HS đầu lớp 6A';
PRINT N'- Thời khóa biểu mẫu cho lớp 6A (Thứ 2)';

PRINT N'- Nhóm có thể dễ dàng thêm dữ liệu bằng INSERT';



