CREATE DATABASE QuanLyHocSinh;
GO
USE QuanLyHocSinh;
GO

-- ==================== TẠO CÁC BẢNG ====================
CREATE TABLE NAM (
    maNam VARCHAR(20) PRIMARY KEY,
    tenNam NVARCHAR(50) NOT NULL,
    trangThai BIT DEFAULT 1
);

CREATE TABLE HOCKY (
    maHK VARCHAR(20) PRIMARY KEY,
    tenHK NVARCHAR(50) NOT NULL,
    maNam VARCHAR(20) NOT NULL,
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE
);

CREATE TABLE GIAOVIEN (
    maGV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    diaChi NVARCHAR(200),
    trangThai BIT DEFAULT 1
);

CREATE TABLE LOP (
    maLop VARCHAR(20) PRIMARY KEY,
    tenLop NVARCHAR(50) NOT NULL,
    siSo INT DEFAULT 0,
    maNam VARCHAR(20) NOT NULL,
    maGVCN VARCHAR(20),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE,
    FOREIGN KEY (maGVCN) REFERENCES GIAOVIEN(maGV) ON DELETE SET NULL
);

CREATE TABLE HOCSINH (
    maHS VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh NVARCHAR(10),
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    maLop VARCHAR(20),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE SET NULL
);

CREATE TABLE PHUHUYNH (
    maPH VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    ngheNghiep NVARCHAR(100),
    trangThai BIT DEFAULT 1
);

CREATE TABLE HOCSINH_PHUHUYNH (
    maHS VARCHAR(20),
    maPH VARCHAR(20),
    quanHe NVARCHAR(50),
    trangThai BIT DEFAULT 1,
    PRIMARY KEY (maHS, maPH),
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maPH) REFERENCES PHUHUYNH(maPH) ON DELETE CASCADE
);

CREATE TABLE MON (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon NVARCHAR(100) NOT NULL,
    trangThai BIT DEFAULT 1
);

CREATE TABLE PHANCONG (
    maPC VARCHAR(20) PRIMARY KEY,
    maGV VARCHAR(20) NOT NULL,
    maMon VARCHAR(20) NOT NULL,
    maLop VARCHAR(20) NOT NULL,
    maNam VARCHAR(20) NOT NULL,
    ghiChu NVARCHAR(200),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maGV) REFERENCES GIAOVIEN(maGV) ON DELETE NO ACTION,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE NO ACTION,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE NO ACTION,
    FOREIGN KEY (maNam) REFERENCES NAM(maNam) ON DELETE CASCADE
);

CREATE TABLE CHITIETMON (
    maChiTiet VARCHAR(20) PRIMARY KEY,
    maMon VARCHAR(20) NOT NULL,
    tenChiTiet NVARCHAR(100) NOT NULL,
    heSo INT NOT NULL,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maMon) REFERENCES MON(maMon) ON DELETE CASCADE
);

CREATE TABLE DIEM (
    maDiem VARCHAR(20) PRIMARY KEY,
    maHS VARCHAR(20) NOT NULL,
    maChiTiet VARCHAR(20) NOT NULL,
    maHocKy VARCHAR(20) NOT NULL,
    diemThuongXuyen FLOAT,
    diemGiuaKy FLOAT,
    diemCuoiKy FLOAT,
    diemTBMonHocKy FLOAT,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maChiTiet) REFERENCES CHITIETMON(maChiTiet) ON DELETE NO ACTION,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE HANHKIEM (
    maHanhKiem VARCHAR(20) PRIMARY KEY,
    maHS VARCHAR(20) NOT NULL,
    maHocKy VARCHAR(20) NOT NULL,
    xepLoai NVARCHAR(20),
    soLanViPham INT DEFAULT 0,
    nhanXet NVARCHAR(500),
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE VIPHAM (
    maViPham VARCHAR(20) PRIMARY KEY,
    maHS VARCHAR(20) NOT NULL,
    maHocKy VARCHAR(20) NOT NULL,
    ngayViPham DATE NOT NULL,
    noiDung NVARCHAR(500),
    mucDo NVARCHAR(50),
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE XEPLOAI (
    maXepLoai VARCHAR(20) PRIMARY KEY,
    maHS VARCHAR(20) NOT NULL,
    maHocKy VARCHAR(20) NOT NULL,
    xepLoaiHocLuc NVARCHAR(20),
    xepLoaiHanhKiem NVARCHAR(20),
    diemTBChung FLOAT,
    nhanXet NVARCHAR(500),
    duocLenLop BIT DEFAULT 1,
    FOREIGN KEY (maHS) REFERENCES HOCSINH(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE THOIKHOABIEU (
    maTKB VARCHAR(20) PRIMARY KEY,
    maLop VARCHAR(20) NOT NULL,
    maHocKy VARCHAR(20) NOT NULL,
    ngayBatDau DATE,
    ngayKetThuc DATE,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maLop) REFERENCES LOP(maLop) ON DELETE NO ACTION,
    FOREIGN KEY (maHocKy) REFERENCES HOCKY(maHK) ON DELETE CASCADE
);

CREATE TABLE CHITIETTIET (
    maChiTiet VARCHAR(20) PRIMARY KEY,
    maTKB VARCHAR(20) NOT NULL,
    maMon VARCHAR(20) NOT NULL,
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
('NH2425', N'Năm học 2024-2025', 1);

-- 2. HỌC KỲ
INSERT INTO HOCKY (maHK, tenHK, maNam, ngayBatDau, ngayKetThuc, trangThai) VALUES
('HK1_2122', N'Học kỳ 1', 'NH2122', '2021-09-05', '2022-01-10', 0),
('HK2_2122', N'Học kỳ 2', 'NH2122', '2022-01-13', '2022-05-25', 0),
('HK1_2223', N'Học kỳ 1', 'NH2223', '2022-09-05', '2023-01-10', 0),
('HK2_2223', N'Học kỳ 2', 'NH2223', '2023-01-13', '2023-05-25', 0),
('HK1_2324', N'Học kỳ 1', 'NH2324', '2023-09-05', '2024-01-10', 0),
('HK2_2324', N'Học kỳ 2', 'NH2324', '2024-01-13', '2024-05-25', 0),
('HK1_2425', N'Học kỳ 1', 'NH2425', '2024-09-05', '2025-01-10', 1),
('HK2_2425', N'Học kỳ 2', 'NH2425', '2025-01-13', '2025-05-25', 1);

-- 3. GIÁO VIÊN
INSERT INTO GIAOVIEN (maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai) VALUES
('GV001', N'Nguyễn Thị Mai', '1985-03-15', N'Nữ', '0901234501', 'ntmai@thcs.edu.vn', N'Hà Nội', 1),
('GV002', N'Trần Văn Hùng', '1983-07-20', N'Nam', '0901234502', 'tvhung@thcs.edu.vn', N'Hưng Yên', 1),
('GV003', N'Lê Thị Lan', '1987-11-10', N'Nữ', '0901234503', 'ltlan@thcs.edu.vn', N'Cam Ranh', 1),
('GV004', N'Phạm Văn Nam', '1984-05-25', N'Nam', '0901234504', 'pvnam@thcs.edu.vn', N'Vinh', 1),
('GV005', N'Hoàng Thị Hoa', '1986-02-14', N'Nữ', '0901234505', 'hthoa@thcs.edu.vn', N'Hải Phòng', 1),
('GV006', N'Đỗ Văn Tùng', '1982-09-08', N'Nam', '0901234506', 'dvtung@thcs.edu.vn', N'Vũng Tàu', 1),
('GV007', N'Vũ Thị Thu', '1988-12-30', N'Nữ', '0901234507', 'vtthu@thcs.edu.vn', N'Huế', 1),
('GV008', N'Bùi Văn Đức', '1985-06-18', N'Nam', '0901234508', 'bvduc@thcs.edu.vn', N'TP.HCM', 1),
('GV009', N'Đặng Thị Ngọc', '1989-04-22', N'Nữ', '0901234509', 'dtngoc@thcs.edu.vn', N'Quy Nhơn', 1),
('GV010', N'Ngô Văn Sơn', '1984-08-16', N'Nam', '0901234510', 'nvson@thcs.edu.vn', N'Đà Lạt', 1),
('GV011', N'Dương Thị Hằng', '1987-01-05', N'Nữ', '0901234511', 'dthang@thcs.edu.vn', N'Thái Bình', 1),
('GV012', N'Lý Văn Kiên', '1986-10-12', N'Nam', '0901234512', 'lvkien@thcs.edu.vn', N'Cà Mau', 1),
('GV013', N'Phan Thị Tâm', '1988-03-28', N'Nữ', '0901234513', 'pttam@thcs.edu.vn', N'Bình Định', 1);

-- 4. LỚP HỌC
INSERT INTO LOP (maLop, tenLop, siSo, maNam, maGVCN, trangThai) VALUES
('6A', N'Lớp 6A', 40, 'NH2425', 'GV001', 1),
('7A', N'Lớp 7A', 40, 'NH2425', 'GV002', 1),
('8A', N'Lớp 8A', 40, 'NH2425', 'GV003', 1),
('9A', N'Lớp 9A', 40, 'NH2425', 'GV004', 1);

-- 5. HỌC SINH (Lớp 6A - 40 HS)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('6A01', N'Trần Thị An', '2012-01-15', N'Nữ', N'Hà Nội', '0900000001', 'tran.an@student.edu.vn', '6A', 1),
('6A02', N'Nguyễn Văn Bình', '2012-02-20', N'Nam', N'Hà Nội', '0900000002', 'nguyen.binh@student.edu.vn', '6A', 1),
('6A03', N'Lê Thị Cẩm', '2012-03-10', N'Nữ', N'Hà Nội', '0900000003', 'le.cam@student.edu.vn', '6A', 1),
('6A04', N'Phạm Văn Dũng', '2012-04-05', N'Nam', N'Hà Nội', '0900000004', 'pham.dung@student.edu.vn', '6A', 1),
('6A05', N'Hoàng Thị Linh', '2012-05-12', N'Nữ', N'Hà Nội', '0900000005', 'hoang.linh@student.edu.vn', '6A', 1),
('6A06', N'Vũ Văn Nam', '2012-06-18', N'Nam', N'Hà Nội', '0900000006', 'vu.nam@student.edu.vn', '6A', 1),
('6A07', N'Đỗ Thị Oanh', '2012-07-22', N'Nữ', N'Hà Nội', '0900000007', 'do.oanh@student.edu.vn', '6A', 1),
('6A08', N'Bùi Văn Phong', '2012-08-14', N'Nam', N'Hà Nội', '0900000008', 'bui.phong@student.edu.vn', '6A', 1),
('6A09', N'Đinh Thị Quỳnh', '2012-09-09', N'Nữ', N'Hà Nội', '0900000009', 'dinh.quynh@student.edu.vn', '6A', 1),
('6A10', N'Trương Văn Sơn', '2012-10-25', N'Nam', N'Hà Nội', '0900000010', 'truong.son@student.edu.vn', '6A', 1),
('6A11', N'Ngô Thị Trang', '2012-11-30', N'Nữ', N'Hà Nội', '0900000011', 'ngo.trang@student.edu.vn', '6A', 1),
('6A12', N'Phan Văn Tùng', '2012-12-05', N'Nam', N'Hà Nội', '0900000012', 'phan.tung@student.edu.vn', '6A', 1),
('6A13', N'Lý Thị Uyên', '2012-01-08', N'Nữ', N'Hà Nội', '0900000013', 'ly.uyen@student.edu.vn', '6A', 1),
('6A14', N'Dương Văn Việt', '2012-02-14', N'Nam', N'Hà Nội', '0900000014', 'duong.viet@student.edu.vn', '6A', 1),
('6A15', N'Mai Thị Xuân', '2012-03-20', N'Nữ', N'Hà Nội', '0900000015', 'mai.xuan@student.edu.vn', '6A', 1),
('6A16', N'Hà Văn Yên', '2012-04-16', N'Nam', N'Hà Nội', '0900000016', 'ha.yen@student.edu.vn', '6A', 1),
('6A17', N'Trần Thị Lan', '2012-05-22', N'Nữ', N'Hà Nội', '0900000017', 'tran.lan@student.edu.vn', '6A', 1),
('6A18', N'Nguyễn Văn Minh', '2012-06-28', N'Nam', N'Hà Nội', '0900000018', 'nguyen.minh@student.edu.vn', '6A', 1),
('6A19', N'Lê Thị Nga', '2012-07-11', N'Nữ', N'Hà Nội', '0900000019', 'le.nga@student.edu.vn', '6A', 1),
('6A20', N'Phạm Văn Quân', '2012-08-17', N'Nam', N'Hà Nội', '0900000020', 'pham.quan@student.edu.vn', '6A', 1),
('6A21', N'Hoàng Thị Hoa', '2012-09-23', N'Nữ', N'Hà Nội', '0900000021', 'hoang.hoa@student.edu.vn', '6A', 1),
('6A22', N'Vũ Văn Đức', '2012-10-19', N'Nam', N'Hà Nội', '0900000022', 'vu.duc@student.edu.vn', '6A', 1),
('6A23', N'Đỗ Thị Thanh', '2012-11-25', N'Nữ', N'Hà Nội', '0900000023', 'do.thanh@student.edu.vn', '6A', 1),
('6A24', N'Bùi Văn Hùng', '2012-12-30', N'Nam', N'Hà Nội', '0900000024', 'bui.hung@student.edu.vn', '6A', 1),
('6A25', N'Đinh Thị Mai', '2012-01-12', N'Nữ', N'Hà Nội', '0900000025', 'dinh.mai@student.edu.vn', '6A', 1),
('6A26', N'Trương Văn Khánh', '2012-02-18', N'Nam', N'Hà Nội', '0900000026', 'truong.khanh@student.edu.vn', '6A', 1),
('6A27', N'Ngô Thị Linh', '2012-03-24', N'Nữ', N'Hà Nội', '0900000027', 'ngo.linh@student.edu.vn', '6A', 1),
('6A28', N'Phan Văn Long', '2012-04-29', N'Nam', N'Hà Nội', '0900000028', 'phan.long@student.edu.vn', '6A', 1),
('6A29', N'Lý Thị Nhung', '2012-05-05', N'Nữ', N'Hà Nội', '0900000029', 'ly.nhung@student.edu.vn', '6A', 1),
('6A30', N'Dương Văn Phúc', '2012-06-10', N'Nam', N'Hà Nội', '0900000030', 'duong.phuc@student.edu.vn', '6A', 1),
('6A31', N'Mai Thị Hương', '2012-07-15', N'Nữ', N'Hà Nội', '0900000031', 'mai.huong@student.edu.vn', '6A', 1),
('6A32', N'Hà Văn Tâm', '2012-08-21', N'Nam', N'Hà Nội', '0900000032', 'ha.tam@student.edu.vn', '6A', 1),
('6A33', N'Trần Thị Thảo', '2012-09-26', N'Nữ', N'Hà Nội', '0900000033', 'tran.thao@student.edu.vn', '6A', 1),
('6A34', N'Nguyễn Văn Tuấn', '2012-10-02', N'Nam', N'Hà Nội', '0900000034', 'nguyen.tuan@student.edu.vn', '6A', 1),
('6A35', N'Lê Thị Vân', '2012-11-07', N'Nữ', N'Hà Nội', '0900000035', 'le.van@student.edu.vn', '6A', 1),
('6A36', N'Phạm Văn Thắng', '2012-12-13', N'Nam', N'Hà Nội', '0900000036', 'pham.thang@student.edu.vn', '6A', 1),
('6A37', N'Hoàng Thị Ngọc', '2012-01-18', N'Nữ', N'Hà Nội', '0900000037', 'hoang.ngoc@student.edu.vn', '6A', 1),
('6A38', N'Vũ Văn Hải', '2012-02-23', N'Nam', N'Hà Nội', '0900000038', 'vu.hai@student.edu.vn', '6A', 1),
('6A39', N'Đỗ Thị Phương', '2012-03-28', N'Nữ', N'Hà Nội', '0900000039', 'do.phuong@student.edu.vn', '6A', 1),
('6A40', N'Bùi Văn Kiên', '2012-04-03', N'Nam', N'Hà Nội', '0900000040', 'bui.kien@student.edu.vn', '6A', 1);

-- Lớp 7A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('7A01', N'Lê Thị Anh', '2011-01-10', N'Nữ', N'Hà Nội', '0900000041', 'le.anh@student.edu.vn', '7A', 1),
('7A02', N'Phạm Văn Bảo', '2011-02-15', N'Nam', N'Hà Nội', '0900000042', 'pham.bao@student.edu.vn', '7A', 1),
('7A03', N'Hoàng Thị Chi', '2011-03-20', N'Nữ', N'Hà Nội', '0900000043', 'hoang.chi@student.edu.vn', '7A', 1),
('7A04', N'Vũ Văn Duy', '2011-04-25', N'Nam', N'Hà Nội', '0900000044', 'vu.duy@student.edu.vn', '7A', 1),
('7A05', N'Đỗ Thị Hà', '2011-05-30', N'Nữ', N'Hà Nội', '0900000045', 'do.ha@student.edu.vn', '7A', 1),
('7A06', N'Bùi Văn Khoa', '2011-06-12', N'Nam', N'Hà Nội', '0900000046', 'bui.khoa@student.edu.vn', '7A', 1),
('7A07', N'Đinh Thị Ly', '2011-07-18', N'Nữ', N'Hà Nội', '0900000047', 'dinh.ly@student.edu.vn', '7A', 1),
('7A08', N'Trương Văn Mạnh', '2011-08-24', N'Nam', N'Hà Nội', '0900000048', 'truong.manh@student.edu.vn', '7A', 1),
('7A09', N'Ngô Thị Phượng', '2011-09-29', N'Nữ', N'Hà Nội', '0900000049', 'ngo.phuong@student.edu.vn', '7A', 1),
('7A10', N'Phan Văn Quốc', '2011-10-05', N'Nam', N'Hà Nội', '0900000050', 'phan.quoc@student.edu.vn', '7A', 1);

-- Lớp 8A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('8A01', N'Hoàng Thị Bình', '2010-01-08', N'Nữ', N'Hà Nội', '0900000081', 'hoang.binh@student.edu.vn', '8A', 1),
('8A02', N'Vũ Văn Cường', '2010-02-13', N'Nam', N'Hà Nội', '0900000082', 'vu.cuong@student.edu.vn', '8A', 1),
('8A03', N'Đỗ Thị Diệu', '2010-03-18', N'Nữ', N'Hà Nội', '0900000083', 'do.dieu@student.edu.vn', '8A', 1),
('8A04', N'Bùi Văn Hiếu', '2010-04-23', N'Nam', N'Hà Nội', '0900000084', 'bui.hieu@student.edu.vn', '8A', 1),
('8A05', N'Đinh Thị Giang', '2010-05-28', N'Nữ', N'Hà Nội', '0900000085', 'dinh.giang@student.edu.vn', '8A', 1),
('8A06', N'Trương Văn Kiên', '2010-06-10', N'Nam', N'Hà Nội', '0900000086', 'truong.kien@student.edu.vn', '8A', 1),
('8A07', N'Ngô Thị Hương', '2010-07-15', N'Nữ', N'Hà Nội', '0900000087', 'ngo.huong@student.edu.vn', '8A', 1),
('8A08', N'Phan Văn Lộc', '2010-08-20', N'Nam', N'Hà Nội', '0900000088', 'phan.loc@student.edu.vn', '8A', 1),
('8A09', N'Lý Thị My', '2010-09-25', N'Nữ', N'Hà Nội', '0900000089', 'ly.my@student.edu.vn', '8A', 1),
('8A10', N'Dương Văn Nhật', '2010-10-30', N'Nam', N'Hà Nội', '0900000090', 'duong.nhat@student.edu.vn', '8A', 1);

-- Lớp 9A (10 HS mẫu)
INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, email, maLop, trangThai) VALUES
('9A01', N'Đỗ Thị An', '2009-01-05', N'Nữ', N'Hà Nội', '0900000121', 'do.an@student.edu.vn', '9A', 1),
('9A02', N'Bùi Văn Bách', '2009-02-10', N'Nam', N'Hà Nội', '0900000122', 'bui.bach@student.edu.vn', '9A', 1),
('9A03', N'Đinh Thị Châu', '2009-03-15', N'Nữ', N'Hà Nội', '0900000123', 'dinh.chau@student.edu.vn', '9A', 1),
('9A04', N'Trương Văn Đạt', '2009-04-20', N'Nam', N'Hà Nội', '0900000124', 'truong.dat@student.edu.vn', '9A', 1),
('9A05', N'Ngô Thị Hằng', '2009-05-25', N'Nữ', N'Hà Nội', '0900000125', 'ngo.hang@student.edu.vn', '9A', 1),
('9A06', N'Phan Văn Hoàng', '2009-06-08', N'Nam', N'Hà Nội', '0900000126', 'phan.hoang@student.edu.vn', '9A', 1),
('9A07', N'Lý Thị Khánh', '2009-07-13', N'Nữ', N'Hà Nội', '0900000127', 'ly.khanh@student.edu.vn', '9A', 1),
('9A08', N'Dương Văn Lâm', '2009-08-18', N'Nam', N'Hà Nội', '0900000128', 'duong.lam@student.edu.vn', '9A', 1),
('9A09', N'Mai Thị Ngân', '2009-09-23', N'Nữ', N'Hà Nội', '0900000129', 'mai.ngan@student.edu.vn', '9A', 1),
('9A10', N'Hà Văn Phú', '2009-10-28', N'Nam', N'Hà Nội', '0900000130', 'ha.phu@student.edu.vn', '9A', 1);

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
('GDCD', N'GDCD', 1);

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
('GDCD_CK', 'GDCD', N'Cuối kỳ', 3, 1);

-- 8. PHÂN CÔNG GIẢNG DẠY
INSERT INTO PHANCONG (maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai) VALUES
-- Toán
('PC001', 'GV005', 'TOAN', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC002', 'GV005', 'TOAN', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC003', 'GV005', 'TOAN', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC004', 'GV005', 'TOAN', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Văn
('PC005', 'GV006', 'VAN', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC006', 'GV006', 'VAN', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC007', 'GV006', 'VAN', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC008', 'GV006', 'VAN', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Anh
('PC009', 'GV007', 'ANH', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC010', 'GV007', 'ANH', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC011', 'GV007', 'ANH', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC012', 'GV007', 'ANH', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Lý
('PC013', 'GV008', 'LY', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC014', 'GV008', 'LY', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC015', 'GV008', 'LY', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC016', 'GV008', 'LY', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Hóa
('PC017', 'GV009', 'HOA', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC018', 'GV009', 'HOA', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC019', 'GV009', 'HOA', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC020', 'GV009', 'HOA', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Sinh
('PC021', 'GV010', 'SINH', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC022', 'GV010', 'SINH', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC023', 'GV010', 'SINH', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC024', 'GV010', 'SINH', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Sử
('PC025', 'GV011', 'SU', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC026', 'GV011', 'SU', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC027', 'GV011', 'SU', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC028', 'GV011', 'SU', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- Địa
('PC029', 'GV012', 'DIA', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC030', 'GV012', 'DIA', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC031', 'GV012', 'DIA', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC032', 'GV012', 'DIA', '9A', 'NH2425', N'Giảng dạy chính', 1),
-- GDCD
('PC033', 'GV013', 'GDCD', '6A', 'NH2425', N'Giảng dạy chính', 1),
('PC034', 'GV013', 'GDCD', '7A', 'NH2425', N'Giảng dạy chính', 1),
('PC035', 'GV013', 'GDCD', '8A', 'NH2425', N'Giảng dạy chính', 1),
('PC036', 'GV013', 'GDCD', '9A', 'NH2425', N'Giảng dạy chính', 1);

-- 9. THỜI KHÓA BIỂU
INSERT INTO THOIKHOABIEU (maTKB, maLop, maHocKy, ngayBatDau, ngayKetThuc, trangThai) VALUES
('TKB_6A_HK1', '6A', 'HK1_2425', '2024-09-05', '2025-01-10', 1),
('TKB_7A_HK1', '7A', 'HK1_2425', '2024-09-05', '2025-01-10', 1),
('TKB_8A_HK1', '8A', 'HK1_2425', '2024-09-05', '2025-01-10', 1),
('TKB_9A_HK1', '9A', 'HK1_2425', '2024-09-05', '2025-01-10', 1);

-- 10. CHI TIẾT TIẾT HỌC (mẫu lớp 6A - Thứ 2)
INSERT INTO CHITIETTIET (maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai) VALUES
('TKB6A_T2_1', 'TKB_6A_HK1', 'TOAN', N'Thứ 2', 1, 'P101', '07:00', '07:45', 1),
('TKB6A_T2_2', 'TKB_6A_HK1', 'TOAN', N'Thứ 2', 2, 'P101', '07:50', '08:35', 1),
('TKB6A_T2_3', 'TKB_6A_HK1', 'VAN', N'Thứ 2', 3, 'P101', '08:40', '09:25', 1),
('TKB6A_T2_4', 'TKB_6A_HK1', 'VAN', N'Thứ 2', 4, 'P101', '09:45', '10:30', 1),
('TKB6A_T2_5', 'TKB_6A_HK1', 'ANH', N'Thứ 2', 5, 'P101', '10:35', '11:20', 1);

-- 11. ĐIỂM MẪU (5 HS đầu lớp 6A)
INSERT INTO DIEM (maDiem, maHS, maChiTiet, maHocKy, diemThuongXuyen, diemGiuaKy, diemCuoiKy, diemTBMonHocKy) VALUES
('D6A01_TOAN_HK1', '6A01', 'TOAN_TX', 'HK1_2425', 7.5, 8.0, 8.5, 8.08),
('D6A01_VAN_HK1', '6A01', 'VAN_TX', 'HK1_2425', 7.0, 7.5, 8.0, 7.58),
('D6A02_TOAN_HK1', '6A02', 'TOAN_TX', 'HK1_2425', 8.0, 8.5, 9.0, 8.58),
('D6A02_VAN_HK1', '6A02', 'VAN_TX', 'HK1_2425', 7.5, 8.0, 8.5, 8.08),
('D6A03_TOAN_HK1', '6A03', 'TOAN_TX', 'HK1_2425', 6.5, 7.0, 7.5, 7.08),
('D6A03_VAN_HK1', '6A03', 'VAN_TX', 'HK1_2425', 6.0, 6.5, 7.0, 6.58),
('D6A04_TOAN_HK1', '6A04', 'TOAN_TX', 'HK1_2425', 9.0, 9.5, 10.0, 9.58),
('D6A04_VAN_HK1', '6A04', 'VAN_TX', 'HK1_2425', 8.5, 9.0, 9.5, 9.08),
('D6A05_TOAN_HK1', '6A05', 'TOAN_TX', 'HK1_2425', 7.0, 7.5, 8.0, 7.58),
('D6A05_VAN_HK1', '6A05', 'VAN_TX', 'HK1_2425', 7.5, 8.0, 8.5, 8.08);

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

SELECT * from XEPLOAI;

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