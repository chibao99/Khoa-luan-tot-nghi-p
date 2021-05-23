
-- tìm phiếu đặt phòng khong co trong khoảng ngày
select distinct p.*,pdp.* from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong
where (pdp.ngayDen >= '2020-12-26' or pdp.ngayDi  <='2020-12-11')--di-den

-- tìm phiếu đặt phòng trong khoảng ngày neesu null thi cho thue
select p.*, tinhtrang= 1 from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong
where (pdp.ngayDen <= '2020-12-26' and pdp.ngayDi  >='2020-12-11') and p.maPhong = 8 --di-den
--tìm phong có tình trạng trống trong khoảng ngày
select * from Phong
except
select p.* from  PhieuDatPhong as pdp join Phong as p on pdp.maPhong = p.maPhong
where (pdp.ngayDen  <= '2020-12-26' and pdp.ngayDi  >='2020-12-11')--đi - đến
-- list pdp cua 1 phong tu ngay den - ngay di -> dsPDP
select pdp.*, kh.hoTen,kh.CMND,
	case
		when pdp.ngayDi = '2020-10-23' then 4 -- sap check out
		when pdp.ngayDen = '2020-10-23' then 2 -- dang check in
		when pdp.ngayDen < '2020-10-23' then 3 -- dang su dung
		else 1 -- da dat
	end as tinhtrang
from  PhieuDatPhong as pdp 
join Phong as p on pdp.maPhong = p.maPhong
join KhachHang as kh on kh.maKH = pdp.maKH
where (
	( pdp.ngayDi  >='2020-10-23') -- ngay hien tai
	and p.maPhong = 2
)

-- list phong voi tinh trang ngay hien tai ->Hiejen button
select p.*,
case 
	when ctp.tinhtrang is null then 0
	else ctp.tinhtrang
end as tinhtrang
 from Phong as p left join
(
select distinct pdp.maPhong,
	case
		when pdp.maPhieuDatPhong is null then 0 -- trong
		when pdp.ngayDen = '2020-11-20' then 2 -- den han check in
		when pdp.ngayDi = '2020-11-20' then 4 -- den han check out
		else 3 -- dang su dung
	end as tinhtrang
from  PhieuDatPhong as pdp right join Phong as p on pdp.maPhong = p.maPhong
where ( (pdp.ngayDen <= '2020-11-20' and pdp.ngayDi  >='2020-11-20')) --di-den
) as ctp on p.maPhong = ctp.maPhong
order by p.giaPhong ASC



--Câu lệnh đếm số phòng hiện tại
select COUNT(*) as tongSoPhong from Phong
--Sắp xếp phòng theo giá tiền tăng/giảm
select * from Phong Order by giaPhong DESC -- giảm
select * from Phong Order by giaPhong ASC -- tăng

--QUẢN LÝ Khách Hàng
CREATE PROCEDURE QuanLyKhachHang(  @ma int,  @ten nvarchar(30),  @cmnd nvarchar(15),@ngaySinh date, @gioiTinh bit, @sDt nvarchar(15),  @Type nvarchar(20) = ''  )  
AS  
BEGIN  
IF @Type = 'Insert'  
BEGIN  
insert into KhachHang(hoTen,CMND, ngaySinh, gioiTinh,sDT) values( @ten, @cmnd, @ngaySinh, @gioiTinh, @sDt)  
END  
IF @Type = 'Select'  
BEGIN  
select * from KhachHang  
END  
IF @Type = 'Update'  
BEGIN  
UPDATE KhachHang 
SET  hoTen = @ten, CMND = @cmnd,  gioiTinh = @gioiTinh, sDT = @sDt
WHERE KhachHang.maKH = @ma 
END
IF @Type = 'Delete'  
BEGIN  
DELETE From KhachHang 
WHERE KhachHang.maKH = @ma 
END
end  
--QUẢN LÝ DỊCH VỤ 
CREATE PROCEDURE QuanLyDichVu  (  @ma int,  @ten nvarchar(30),@donVi nvarchar(30), @loai nvarchar(30), @soLuongCo int, @giaDV int, @Type nvarchar(20) = '')  
AS  
BEGIN  
IF @Type = 'Insert'  
BEGIN  
insert into DichVu(tenDV,donVi, loai, soLuongCo,giaDV) values( @ten, @donVi, @loai, @soLuongCo, @giaDV)  
END  
IF @Type = 'Select'  
BEGIN  
select * from DichVu  
END  
IF @Type = 'Update'  
BEGIN  
UPDATE DichVu 
SET  tenDV = @ten, donVi = @donVi,  loai = @loai, soLuongCo = @soLuongCo, giaDV = @giaDV
WHERE DichVu.maDV = @ma 
END  
IF @Type = 'Delete'  
BEGIN  
DELETE From DichVu 
WHERE DichVu.maDV = @ma 
END    
end  
select * from NhanVien

-- THỐNG KÊ DOANH THU TỪNG PHÒNG TRONG TỪNG THÁNG
select p.maPhong, p.loaiPhong, tt = SUM(hd.tongTien) from Phong as p
join PhieuDatPhong as pdp on pdp.maPhong = p.maPhong
join HoaDon as hd on hd.maHD = pdp.maPhieuDatPhong
group by p.maPhong, p.loaiPhong,pdp.ngayDen
having DATEPART(mm,pdp.ngayDen) = 11 and  DATEPART(yyyy,pdp.ngayDen) = 2020
-- THỐNG KÊ SỐ LƯỢNG PDP TỪNG PHÒNG
select p.maPhong, tt = Count(p.maPhong) from Phong as p
join PhieuDatPhong as pdp on pdp.maPhong = p.maPhong
where DATEPART(MM,pdp.ngayDen) = 11 and  DATEPART(yyyy,pdp.ngayDen) = 2020
group by p.maPhong
-- THỐNG KÊ DOANH THU trong ngày
select * from HoaDon
select hd.ngayLapHD,tt = SUM(hd.tongTien) from HoaDon as hd
where DATEPART(MM,hd.ngayLapHD) = 12 and  DATEPART(yyyy,hd.ngayLapHD) = 2019
group by hd.ngayLapHD
--Thống kê khách hàng nào đặt nhiều phòng nhất
select top 10 maKH,num=COUNT(maPhieuDatPhong) from PhieuDatPhong
where  DATEPART(yyyy,ngayLapPhieu) = 2020 
group by maKH
order by num desc

