 drop database QLKS
 create database QLKS
use QLKS

create table KhachHang(
	maKH int IDENTITY(1,1)  primary key,
	hoTen nvarchar(30),
	CMND nvarchar(15),
	ngaySinh date,
	gioiTinh bit, --1 la nu, 0 la nam
	sDT nvarchar(15)
)

create table NhanVien(
	maNV int IDENTITY(1,1) primary key,
	hoTen nvarchar(30),
	CMND nvarchar(15),
	ngaySinh date,
	gioiTinh bit,--1 la nu, 0 la nam
	sDT nvarchar(15),
	diaChi nvarchar(100)
)
create table TaiKhoan(
	password nvarchar(30),
	primary key(maNV, password),
	maNV int unique FOREIGN KEY REFERENCES NhanVien (maNV) on delete cascade,
)
create table Phong(
	maPhong int IDENTITY(1,1) primary key,
	loaiPhong nvarchar(30),
	ghiChu nvarchar(30),
	giaPhong int
)
create table PhieuDatPhong(
	maPhieuDatPhong int IDENTITY(1,1) primary key,
	maPhong int FOREIGN KEY REFERENCES Phong (maPhong) on delete cascade,
	maKH int FOREIGN KEY REFERENCES KhachHang (maKH) on delete cascade,
	maNV int FOREIGN KEY REFERENCES NhanVien (maNV) on delete cascade,
	ngayDen date,
	ngayDi date,
	ngayLapPhieu date
	--tinh trang phieu java 0: Trống. 1: Đã đặt. 2: đến hạn check in 3: Đang sử dụng 4: đến hạn check out
)

create table HoaDon(
	maHD INT primary key FOREIGN KEY REFERENCES PhieuDatPhong(maPhieuDatPhong),
	maNV int FOREIGN KEY REFERENCES NhanVien (maNV) on delete cascade,
	tongTien int,
	ngayLapHD date, --Thường trùng ngày trả phòng (If(tinhTrang == 0) ngayLapHD = null)
	tinhTrangHoaDon int
)


create table DichVu(
	maDV int IDENTITY(1,1) primary key,
	tenDV nvarchar(30),
	donVi nvarchar(30),
	loai nvarchar(30),
	soLuongCo int,
	giaDV int
)

create table CTDichVu(
	maCTDV int IDENTITY(1,1) primary key,
	maDV int FOREIGN KEY REFERENCES DichVu (maDV) on delete set default,
	maHD int FOREIGN KEY REFERENCES HoaDon (maHD) on delete set default,
	soLuongSD int,
	thoiGianSD nvarchar(30),
)

INSERT INTO KhachHang(hoTen,CMND, ngaySinh, gioiTinh,sDT) VALUES
(N'Vũ Quang Ngọc', N'585150968', '1985-3-18', 0, N'0168839245'),
(N'Nguyên Nhất Phượng', N'409211422', '1977-3-28', 0, N'0966397869'),
(N'Vũ Quang Duy', N'60570304', '1992-8-21', 1, N'0167196978'),
(N'Đinh Khánh Hoàng', N'523428896', '2001-12-11', 0, N'0167430224'),
(N'Hoàng Văn Nam', N'116364406', '1972-9-18', 0, N'0123461772'),
(N'Dương Văn Phượng', N'629402774', '1987-12-17', 1, N'0961187280'),
(N'Trần Đình Bảo', N'589395193', '1991-9-6', 1, N'0934148587'),
(N'Hồ Đức Mai', N'043640998', '1993-2-17', 1, N'0124225737'),
(N'Trần Kim Hiếu', N'399522920', '1973-4-13', 1, N'0972614816'),
(N'Nguyên Khánh Mai', N'896341499', '1982-3-21', 0, N'0168529289'),
(N'Dương Kim Bảo', N'872767560', '1988-2-9', 1, N'0864094868'),
(N'Dương Văn Mai', N'162744440', '1985-11-10', 1, N'0164679434'),
(N'Hồ Gia Ninh', N'708281745', '1975-9-25', 0, N'0936117617'),
(N'Ngô Quang Duy', N'004724711', '1993-3-7', 1, N'0894125613'),
(N'Dương Mộng Hào', N'195911238', '1976-7-8', 1, N'0123936593'),
(N'Đặng Như Trung', N'577086447', '1975-9-23', 0, N'0164002074'),
(N'Dương Đức Xinh', N'885223072', '1997-10-7', 0, N'0900888151'),
(N'Đỗ Mộng Ngọc', N'699026714', '1982-10-5', 1, N'0163718525'),
(N'Dương Kiều Hà', N'959493267', '1973-9-1', 1, N'0121284011'),
(N'Đinh Như Ngọc', N'673856368', '1988-2-28', 0, N'0121412014'),
(N'Hồ Thành Hào', N'331228006', '2002-6-20', 1, N'0163469307')

--100 NV

INSERT INTO NhanVien(hoTen,CMND,ngaySinh,gioiTinh,sDT,diaChi) VALUES
(N'Vũ Hoàng Trang', N'427113122', '1976-6-7', 1, N'0169175398', N'375 Đường Lương Khải Siêu Tổ 10 Khu 2 Phường Đông Hưng Thuận Quận 11 TP.Hồ Chí Minh'),
(N'Nguyễn Như Ninh', N'684095622', '1983-1-28', 0, N'0127615388', N'504 Đường An Hội Tổ 10 Khu 5 Phường Tân Thới Nhất Quận Tân Phú TP.Hồ Chí Minh'),
(N'Nguyễn Tuấn Bảo', N'875123784', '1975-5-28', 1, N'0120154268', N'612 Đường Tam Châu Tổ 9 Khu 6 Phường An Phú Đông Quận Tân Bình TP.Hồ Chí Minh'),
(N'Đặng Nhất Việt', N'637802714', '1997-2-23', 1, N'0128075002', N'950 Đường Nguyễn Bỉnh Khiêm Tổ 12 Khu 6 Phường Phú Hữu Quận Tân Phú TP.Hồ Chí Minh'),
(N'Huỳnh Thiên Ninh', N'933350612', '1994-2-20', 1, N'0169570986', N'775 Đường Lê Văn Chí Tổ 19 Khu 5 Phường Tân Chánh Hiệp Quận 5 TP.Hồ Chí Minh'),
(N'Đinh Nhất Bé', N'386877775', '1971-12-19', 1, N'0163236829', N'978 Đường An Nhơn Tổ 12 Khu 10 Phường Tam Phú Quận 11 TP.Hồ Chí Minh'),
(N'Võ Kim Bé', N'932841918', '1973-1-24', 0, N'0985024265', N'338 Đường Phạm Văn Chiêu Tổ 5 Khu 9 Phường Cát Lái Quận 5 TP.Hồ Chí Minh'),
(N'Ngô Mộng Hiếu', N'860502982', '1973-1-5', 1, N'0124686392', N'257 Đường Phạm Văn Chiêu Tổ 20 Khu 10 Phường Tam Phú Quận Phú Nhuận TP.Hồ Chí Minh'),
(N'Dương Kim Phượng', N'163493175', '1977-8-11', 1, N'0169025106', N'505 Đường Quang Trung Tổ 16 Khu 7 Phường Đa Kao Quận Bình Thạnh TP.Hồ Chí Minh'),
(N'Phạm Đức Nguyên', N'351511943', '1997-10-24', 1, N'0126687758', N'432 Đường Phạm Văn Đồng Tổ 14 Khu 3 Phường Tân Chánh Hiệp Quận 12 TP.Hồ Chí Minh'),
(N'Nguyễn Đức Bảo', N'985055445', '1997-9-7', 1, N'0126935230', N'405 Đường Tam Hà Tổ 15 Khu 10 Phường Tân Định Quận 2 TP.Hồ Chí Minh'),
(N'Trần Thu Huy', N'728987639', '1975-8-12', 0, N'0128000506', N'646 Đường Dương Văn Cam Tổ 7 Khu 6 Phường Trung Mỹ Tây Quận 6 TP.Hồ Chí Minh'),
(N'Hoàng Kiều Đạt', N'983077617', '1987-9-11', 0, N'0165785171', N'621 Đường Đoàn Công Hớn Tổ 16 Khu 7 Phường Thủ Thiêm Quận Phú Nhuận TP.Hồ Chí Minh'),
(N'Đặng Minh Ninh', N'522379986', '1999-3-9', 1, N'0901353583', N'961 Đường Trần Bá Giao Tổ 3 Khu 3 Phường Hiệp Phú Quận 6 TP.Hồ Chí Minh'),
(N'Đặng Văn Xinh', N'375463514', '2002-11-14', 1, N'0123183384', N'190 Đường Nguyễn Duy Cung Tổ 18 Khu 8 Phường Cát Lái Quận Gò Vấp TP.Hồ Chí Minh'),
(N'Võ Hoàng Trang', N'619666274', '1976-6-10', 1, N'0169623601', N'995 Đường Việt Thắng Tổ 9 Khu 10 Phường An Khánh Quận Tân Bình TP.Hồ Chí Minh'),
(N'Đinh Khánh Đạt', N'673833411', '1993-12-11', 0, N'0165430326', N'813 Đường Phú Châu Tổ 14 Khu 7 Phường Phước Bình Quận 5 TP.Hồ Chí Minh'),
(N'Hoàng Quang Việt', N'458744329', '1973-6-18', 1, N'0125409284', N'704 Đường Nguyễn Oanh Tổ 19 Khu 2 Phường Phước Long A Quận 6 TP.Hồ Chí Minh'),
(N'Đặng Văn Hiếu', N'329132627', '1984-5-5', 1, N'0864480620', N'109 Đường Nguyễn Oanh Tổ 16 Khu 2 Phường Phước Bình Quận 2 TP.Hồ Chí Minh'),
(N'Nguyên Thị Duy', N'492647104', '2002-2-10', 0, N'0124936315', N'588 Đường Einstein Tổ 8 Khu 4 Phường An Phú Quận 11 TP.Hồ Chí Minh'),
(N'Hoàng Thị Hiếu', N'733679739', '1994-2-7', 0, N'0120493248', N'814 Đường Ngô Chí Quốc Tổ 3 Khu 3 Phường Nguyễn Cư Trinh Quận 3 TP.Hồ Chí Minh'),
(N'Dương Minh Hoàng', N'077718236', '2001-5-4', 1, N'0982190403', N'668 Đường Lê Thị Hồng Tổ 11 Khu 1 Phường Tân Thành Quận 7 TP.Hồ Chí Minh'),
(N'Nguyên Thị Hoàng', N'370640472', '1977-7-8', 0, N'0167422488', N'413 Đường Bùi Quang Là Tổ 7 Khu 1 Phường Tân Phú Quận 7 TP.Hồ Chí Minh'),
(N'Hoàng Mộng Huy', N'783264151', '1983-1-23', 0, N'0123989589', N'413 Đường Nguyễn Khuyến Tổ 19 Khu 8 Phường Linh Đông Quận 9 TP.Hồ Chí Minh'),
(N'Vũ Quang Ngọc', N'585509268', '1985-3-18', 0, N'0168839245', N'791 Đường Huỳnh Khương An Tổ 14 Khu 9 Phường Long Phước Quận 3 TP.Hồ Chí Minh'),
(N'Nguyên Nhất Phượng', N'409114262', '1977-3-28', 0, N'0966397869', N'159 Đường Lê Thị Hồng Tổ 3 Khu 6 Phường Phú Thọ Hoà Quận 7 TP.Hồ Chí Minh'),
(N'Vũ Quang Duy', N'605760304', '1992-8-21', 1, N'0167196978', N'862 Đường Nguyễn Duy Cung Tổ 18 Khu 9 Phường Bình Thọ Quận 5 TP.Hồ Chí Minh'),
(N'Đinh Khánh Hoàng', N'523328896', '2001-12-11', 0, N'0167430224', N'220 Đường An Hội Tổ 5 Khu 7 Phường Phước Bình Quận 1 TP.Hồ Chí Minh'),
(N'Hoàng Văn Nam', N'116369406', '1972-9-18', 0, N'0123461772', N'763 Đường Gò Dưa Tổ 13 Khu 7 Phường Tân Qúy Quận 2 TP.Hồ Chí Minh'),
(N'Dương Văn Phượng', N'622402774', '1987-12-17', 1, N'0961187280', N'507 Đường Đặng Văn Bi Tổ 19 Khu 6 Phường An Phú Đông Quận 2 TP.Hồ Chí Minh'),
(N'Trần Đình Bảo', N'589399513', '1991-9-6', 1, N'0934148587', N'621 Đường Công Lý Tổ 12 Khu 7 Phường Tam Phú Quận Tân Bình TP.Hồ Chí Minh'),
(N'Hồ Đức Mai', N'043640498', '1993-2-17', 1, N'0124225737', N'811 Đường Einstein Tổ 9 Khu 9 Phường Tân Thới Nhất Quận Thủ Đức TP.Hồ Chí Minh'),
(N'Trần Kim Hiếu', N'399526920', '1973-4-13', 1, N'0972614816', N'292 Đường Tô Vĩnh Diện Tổ 6 Khu 1 Phường Tân Thành Quận Thủ Đức TP.Hồ Chí Minh'),
(N'Nguyên Khánh Mai', N'896346499', '1982-3-21', 0, N'0168529289', N'383 Đường Nguyễn Kiệm Tổ 15 Khu 5 Phường Thạnh Xuân Quận Thủ Đức TP.Hồ Chí Minh'),
(N'Lê Văn Sơn', N'972794460', '1983-4-26', 1, N'0166555024', N'409 Đường Lê Lai Tổ 2 Khu 7 Phường Bình An Quận 8 TP.Hồ Chí Minh'),
(N'Đặng Thành Nguyên', N'678792504', '1986-10-8', 0, N'0120377362', N'219 Đường Hoàng Minh Giám Tổ 19 Khu 10 Phường Thạnh Mỹ Lợi Quận 9 TP.Hồ Chí Minh'),
(N'Võ Khánh Nguyên', N'575404201', '1994-4-27', 0, N'0890083583', N'268 Đường Huỳnh Khương An Tổ 11 Khu 6 Phường Trường Thạnh Quận 6 TP.Hồ Chí Minh'),
(N'Huỳnh Nhất Hiệp', N'214254581', '1986-2-1', 1, N'0164122727', N'769 Đường Xa Lộ Hà Nội Tổ 20 Khu 3 Phường Tân Phú Quận 10 TP.Hồ Chí Minh'),
(N'Võ Khánh Mai', N'115031065', '1974-9-8', 0, N'0162890734', N'383 Đường Trần Bá Giao Tổ 13 Khu 6 Phường Phạm Ngũ Lão Quận 7 TP.Hồ Chí Minh'),
(N'Nguyên Thị Mai', N'403350292', '1977-5-27', 1, N'0163040317', N'724 Đường An Nhơn Tổ 14 Khu 3 Phường Hiệp Bình Chánh Quận Tân Phú TP.Hồ Chí Minh'),
(N'Trần Kiều Bé', N'141225156', '1982-10-12', 1, N'0128922453', N'36 Đường An Nhơn Tổ 18 Khu 8 Phường Nguyễn Cư Trinh Quận 2 TP.Hồ Chí Minh'),
(N'Đỗ Nhất Phượng', N'218887973', '1984-6-20', 1, N'0167124430', N'510 Đường Nguyễn Văn Lượng Tổ 19 Khu 9 Phường Bình Thọ Quận 5 TP.Hồ Chí Minh'),
(N'Võ Gia Việt', N'964585864', '1984-3-19', 0, N'0128085117', N'188 Đường Nguyễn Tuân Tổ 12 Khu 9 Phường Linh Đông Quận Gò Vấp TP.Hồ Chí Minh'),
(N'Trần Thu Nam', N'252750418', '1991-9-1', 1, N'0162921677', N'382 Đường Tam Bình Tổ 10 Khu 1 Phường Bình Trưng Tây Quận 10 TP.Hồ Chí Minh'),
(N'Lê Đình Hào', N'385898446', '1976-4-27', 1, N'0127602432', N'572 Đường Trần Phú Cường Tổ 2 Khu 4 Phường Tây Thạnh Quận 6 TP.Hồ Chí Minh'),
(N'Nguyên Minh Ninh', N'247374861', '2002-2-20', 1, N'0893853783', N'997 Đường Hồng Đức Tổ 16 Khu 4 Phường An Phú Đông Quận 6 TP.Hồ Chí Minh'),
(N'Nguyên Văn Sơn', N'351318978', '1983-1-2', 1, N'0123234854', N'447 Đường Công Lý Tổ 1 Khu 9 Phường Thảo Điền Quận Gò Vấp TP.Hồ Chí Minh'),
(N'Bùi Văn Hiếu', N'041660847', '1980-1-10', 0, N'0931030991', N'827 Đường Hồ Văn Tư Tổ 7 Khu 2 Phường Tăng Nhơn Phú A Quận 11 TP.Hồ Chí Minh'),
(N'Dương Đình Duy', N'792151244', '1998-10-13', 1, N'0931661362', N'294 Đường Dân Chủ Tổ 19 Khu 7 Phường Bình An Quận 9 TP.Hồ Chí Minh'),
(N'Bùi Thành Hoàng', N'416129395', '1972-10-14', 1, N'0124577358', N'7 Đường Huỳnh Khương An Tổ 19 Khu 10 Phường Hiệp Thành Quận Tân Phú TP.Hồ Chí Minh'),

(N'Bùi Khánh Đạt', N'589077512', '1999-12-4', 0, N'0121060702', N'564 Đường Phạm Văn Đồng Tổ 20 Khu 9 Phường Bình Khánh Quận 8 TP.Hồ Chí Minh')



INSERT INTO Phong(loaiPhong,ghiChu,giaPhong) VALUES
(N'Giường đơn', N'Nui', 2499000),
(N'Giường đôi', N'San Golf', 6206000),
(N'Vip', N'San Golf', 12802000),
(N'Giường đôi', N'San Golf', 8756000),
(N'Giường đơn', N'Thanh Pho', 2173000),
(N'Thường', N'Thanh Pho', 10894000),
(N'Thường', N'Thanh Pho', 16771000),
(N'Thường', N'Bien', 10540000),
(N'Thường', N'San Golf', 10197000),
(N'Giường đơn', N'Bien', 2299000),
(N'VIP', N'San Golf', 14542000),
(N'Giường đôi', N'San Golf', 6396000),
(N'Giường Đôi', N'Thanh Pho', 10099000),
(N'Thường', N'Nui', 16987000),
(N'Giường đơn', N'Thanh Pho', 2541000),
(N'Giường đôi', N'Nui', 8961000),
(N'Giường đôi', N'San Golf', 6396000),
(N'Giường đôi', N'Thanh Pho', 10099000),
(N'Thường', N'Nui', 16987000),
(N'Giường đơn', N'Thanh Pho', 2541000),
(N'Giường đôi', N'Nui', 8961000),
(N'Giường đôi', N'San Golf', 6396000),
(N'Giường đôi', N'Thanh Pho', 10099000),
(N'Thường', N'Nui', 16987000),
(N'Giường đơn', N'Thanh Pho', 2541000),
(N'Giường đôi ', N'Nui', 8961000)


insert into DichVu(tenDV, donVi, loai, soLuongCo,giaDV) values
(N'Lemon',N'lon',N'Đồ Uống',100, 10000),
 (N'Soda Chanh','lon',N'Đồ Uống',100, 50000),
 (N'Soda Dâu','lon',N'Đồ Uống',100, 50000),
 (N'Soda Chuối','lon',N'Đồ Uống',100, 50000),
 (N'Soda Dừa','lon',N'Đồ Uống',100, 50000),
 (N'Sữa','hộp',N'Đồ Uống',100, 10000),
 (N'Milo','hộp',N'Đồ Uống',100, 30000),

(N'C2','lon',N'Đồ Uống',23, 10000),
(N'Mirinda','lon',N'Đồ Uống',634, 12000),
(N'Monster','lon',N'Đồ Uống',21, 22000),
(N'333','lon',N'Đồ Uống',55, 30000),
(N'Tiger','lon',N'Đồ Uống',77, 32000),
(N'Nước ép','lon',N'Đồ Uống',21, 50000),
(N'Nước cam','lon',N'Đồ Uống',11, 50000),
 (N'CocaCola',N'lon',N'Đồ Uống',100, 50000),
 (N'Pepsi','lon',N'Đồ Uống',100, 50000),
 (N'7Up','lon',N'Đồ Uống',100, 50000),
 (N'MountainDew',N'lon',N'Đồ Uống',100, 100000),
 (N'Bò Húc',N'lon',N'Đồ Uống',100, 90000),
 (N'Thuê Xe',N'chiếc',N'Dịch Vụ',10, 900000),
  (N'Hồ Bơi',N'giờ',N'Dịch Vụ',1, 90000),
 (N'Message',N'giờ',N'Dịch Vụ',100, 900000)
  


 insert into PhieuDatPhong (maPhong, maKH, maNV, ngayDen, ngayDi, ngayLapPhieu) values
(4,8,1,'2019-11-15','2019-11-17', '2019-11-15'),
(2,8,1,'2019-10-28','2019-11-1', '2019-10-28'),
(4,8,1,'2019-10-28','2019-10-30', '2019-11-5'),
(3,6,1,'2019-10-28','2019-10-31', '2019-11-5'),
(1,2,1,'2019-10-19','2019-10-22', '2017-11-5'),
(1,3,1,'2019-10-23','2019-10-25', '2017-12-5'),
(2,4,1,'2019-11-2','2019-11-7','2017-11-5'),
(4,1,1,'2018-12-4','2018-12-7','2017-11-5'),
(1,4,1,'2017-10-8','2017-10-20', '2017-11-5'),
(1,5,1,'2019-11-19','2019-11-22', '2017-11-5'),
(3,3,1,'2019-10-8','2019-10-26','2017-11-5'),
(8,3,1,'2017-12-4','2017-12-7', '2017-11-5'),
(5,4,1,'2019-10-9','2019-10-11','2017-11-5'),
(6,5,1,'2019-10-9','2019-10-11', '2017-11-5'),
(7,6,1,'2019-10-9','2019-10-11', '2017-11-5')

 select * from PhieuDatPhong
 select * FROM DichVu 
 SELECT * FROM Phong
 select * from NhanVien
 select * from KhachHang
 select * from HoaDon where maHD = 1025
 select * from CTDichVu
