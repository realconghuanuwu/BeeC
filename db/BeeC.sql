/*CREATE DATABASE BeeCGear*/

USE [BeeCGear]
GO
/****** Object:  Table [dbo].[ChiTietHoaDon]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHoaDon](
	[MaHoaDon] [int] NOT NULL,
	[MaSanPham] [int] NOT NULL,
	[SoLuong] [int] NULL,
 CONSTRAINT [PK__ChiTietH__4CF2A579FE879F54] PRIMARY KEY CLUSTERED 
(
	[MaHoaDon] ASC,
	[MaSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietPhieuNhap]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietPhieuNhap](
	[MaPhieuNhap] [varchar](20) NULL,
	[TenSanPham] [nvarchar](50) NULL,
	[DonViTinh] [nvarchar](10) NULL,
	[SoLuong] [int] NULL,
	[DonGia] [money] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMucSanPham]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucSanPham](
	[MaDanhMuc] [varchar](10) NOT NULL,
	[TenDanhMuc] [nvarchar](50) NULL,
	[GhiChu] [nvarchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDanhMuc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDonThanhToan]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDonThanhToan](
	[MaHoaDon] [int] IDENTITY(1,1) NOT NULL,
	[MaNguoiTao] [varchar](10) NULL,
	[MaKhachHang] [varchar](30) NOT NULL,
	[NgayTao] [date] NULL,
	[TrangThaiThanhToan] [nvarchar](20) NULL,
	[GhiChu] [nvarchar](150) NULL,
 CONSTRAINT [PK__HoaDonTh__835ED13BBA938CBB] PRIMARY KEY CLUSTERED 
(
	[MaHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[MaKhachHang] [varchar](30) NOT NULL,
	[HovaTen] [nvarchar](50) NULL,
	[DiaChi] [nvarchar](150) NULL,
	[GhiChu] [nvarchar](150) NULL,
 CONSTRAINT [PK__KhachHan__88D2F0E5F6D62CAE] PRIMARY KEY CLUSTERED 
(
	[MaKhachHang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhaCungCap]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaCungCap](
	[MaNhaCungCap] [varchar](10) NOT NULL,
	[TenNhaCungCap] [nvarchar](50) NULL,
	[DiaChi] [nvarchar](150) NULL,
	[SoDienThoai] [varchar](15) NULL,
	[MaSoThue] [varchar](15) NULL,
	[GhiChu] [nvarchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaNhaCungCap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNhanVien] [varchar](10) NOT NULL,
	[HovaTen] [nvarchar](50) NULL,
	[Email] [varchar](50) NULL,
	[MatKhau] [varchar](150) NULL,
	[VaiTro] [int] NULL,
	[GioiTinh] [bit] NULL,
	[DiaChi] [nvarchar](150) NULL,
	[SoDienThoai] [varchar](15) NULL,
	[LichLamViec] [nvarchar](50) NULL,
	[Anh] [varchar](20) NULL,
	[Luong] [money] NULL,
	[NgayVaoCuaHang] [date] NULL,
 CONSTRAINT [PK__NhanVien__77B2CA4794302C14] PRIMARY KEY CLUSTERED 
(
	[MaNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuNhapHang]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuNhapHang](
	[MaPhieuNhap] [varchar](20) NOT NULL,
	[MaNhaCungCap] [varchar](10) NULL,
	[NgayTao] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaPhieuNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[MaSanPham] [int] IDENTITY(1,1) NOT NULL,
	[MaPhieuNhap] [varchar](20) NULL,
	[TenSanPham] [nvarchar](50) NULL,
	[MaModel] [varchar](20) NULL,
	[MaThuongHieu] [varchar](10) NULL,
	[MaDanhMuc] [varchar](10) NULL,
	[GiaBan] [money] NULL,
	[MoTa] [nvarchar](150) NULL,
	[MaNguoiNhap] [varchar](10) NULL,
	[NgayNhap] [date] NULL,
	[Anh] [varchar](20) NULL,
	[ThoiGianBaoHanh] [int] NULL,
	[SoLuong] [int] NULL,
 CONSTRAINT [PK__SanPham__FAC7442D194B946D] PRIMARY KEY CLUSTERED 
(
	[MaSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ThuongHieu]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ThuongHieu](
	[MaThuongHieu] [varchar](10) NOT NULL,
	[Anh] [varchar](20) NULL,
	[TenThuongHieu] [nvarchar](50) NULL,
	[GhiChu] [nvarchar](150) NULL,
 CONSTRAINT [PK__ThuongHi__A3733E2CD62FBE7B] PRIMARY KEY CLUSTERED 
(
	[MaThuongHieu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HoaDonThanhToan] ADD  CONSTRAINT [DF__HoaDonTha__NgayT__4F7CD00D]  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[HoaDonThanhToan] ADD  CONSTRAINT [DF__HoaDonTha__Trang__5070F446]  DEFAULT (N'Chưa Thanh Toán') FOR [TrangThaiThanhToan]
GO
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [DF__NhanVien__VaiTro__37A5467C]  DEFAULT ((1)) FOR [VaiTro]
GO
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [DF__NhanVien__GioiTi__38996AB5]  DEFAULT ((0)) FOR [GioiTinh]
GO
ALTER TABLE [dbo].[NhanVien] ADD  DEFAULT (getdate()) FOR [NgayVaoCuaHang]
GO
ALTER TABLE [dbo].[PhieuNhapHang] ADD  DEFAULT (getdate()) FOR [NgayTao]
GO
ALTER TABLE [dbo].[SanPham] ADD  CONSTRAINT [DF__SanPham__NgayNha__48CFD27E]  DEFAULT (getdate()) FOR [NgayNhap]
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK__ChiTietHo__MaSan__5629CD9C] FOREIGN KEY([MaSanPham])
REFERENCES [dbo].[SanPham] ([MaSanPham])
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK__ChiTietHo__MaSan__5629CD9C]
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_CTHD_HD] FOREIGN KEY([MaHoaDon])
REFERENCES [dbo].[HoaDonThanhToan] ([MaHoaDon])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK_CTHD_HD]
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhapHang_ChiTietPhieuNhap] FOREIGN KEY([MaPhieuNhap])
REFERENCES [dbo].[PhieuNhapHang] ([MaPhieuNhap])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap] CHECK CONSTRAINT [FK_PhieuNhapHang_ChiTietPhieuNhap]
GO
ALTER TABLE [dbo].[HoaDonThanhToan]  WITH CHECK ADD  CONSTRAINT [FK__HoaDonTha__MaKha__52593CB8] FOREIGN KEY([MaKhachHang])
REFERENCES [dbo].[KhachHang] ([MaKhachHang])
GO
ALTER TABLE [dbo].[HoaDonThanhToan] CHECK CONSTRAINT [FK__HoaDonTha__MaKha__52593CB8]
GO
ALTER TABLE [dbo].[HoaDonThanhToan]  WITH CHECK ADD  CONSTRAINT [FK__HoaDonTha__MaNgu__5165187F] FOREIGN KEY([MaNguoiTao])
REFERENCES [dbo].[NhanVien] ([MaNhanVien])
GO
ALTER TABLE [dbo].[HoaDonThanhToan] CHECK CONSTRAINT [FK__HoaDonTha__MaNgu__5165187F]
GO
ALTER TABLE [dbo].[PhieuNhapHang]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhapHang_NhaCungCap] FOREIGN KEY([MaNhaCungCap])
REFERENCES [dbo].[NhaCungCap] ([MaNhaCungCap])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PhieuNhapHang] CHECK CONSTRAINT [FK_PhieuNhapHang_NhaCungCap]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK__SanPham__MaNguoi__4CA06362] FOREIGN KEY([MaNguoiNhap])
REFERENCES [dbo].[NhanVien] ([MaNhanVien])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK__SanPham__MaNguoi__4CA06362]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhapHang_SanPham] FOREIGN KEY([MaPhieuNhap])
REFERENCES [dbo].[PhieuNhapHang] ([MaPhieuNhap])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_PhieuNhapHang_SanPham]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_DanhMuc] FOREIGN KEY([MaDanhMuc])
REFERENCES [dbo].[DanhMucSanPham] ([MaDanhMuc])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_SanPham_DanhMuc]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_ThuongHieu] FOREIGN KEY([MaThuongHieu])
REFERENCES [dbo].[ThuongHieu] ([MaThuongHieu])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_SanPham_ThuongHieu]
GO
/****** Object:  StoredProcedure [dbo].[getChiTietHoaDonByMaHoaDon]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[getChiTietHoaDonByMaHoaDon]
    @MaHD int
AS
BEGIN
    select sp.MaSanPham, sp.TenSanPham, sp.MaModel, ct.SoLuong, sp.GiaBan  from ChiTietHoaDon ct  join SanPham sp
	 on ct.MaSanPham = sp.MaSanPham where ct.MaHoaDon = @MaHD
END
GO
/****** Object:  StoredProcedure [dbo].[getChiTietPhieuNhap]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROC [dbo].[getChiTietPhieuNhap] (@MaPhieuNhap Nvarchar(50))
as
BEGIN
	SELECT * From ChiTietPhieuNhap where MaPhieuNhap like '%'+@MaPhieuNhap+'%'
END
GO
/****** Object:  StoredProcedure [dbo].[getDonHangDaThanhToan]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[getDonHangDaThanhToan]
    @NgayThangNam date, @TrangThaiThanhToan nvarchar(20), @LocTheo varchar(20)
AS
BEGIN
	IF @LocTheo = 'ngay'
	BEGIN
		 select count(hd.MaHoaDon) as TongDonHang, sum(cthd.SoLuong*sp.GiaBan) as TongTien from ChiTietHoaDon cthd 
		 right join HoaDonThanhToan hd  on hd.MaHoaDon = cthd.MaHoaDon
		 left join SanPham sp on sp.MaSanPham = cthd.MaSanPham
		 where NgayTao = @NgayThangNam and hd.TrangThaiThanhToan = @TrangThaiThanhToan
	END
	ELSE IF @LocTheo = 'thang'
	BEGIN
		select count(hd.MaHoaDon) as TongDonHang, sum(cthd.SoLuong*sp.GiaBan) as TongTien from ChiTietHoaDon cthd 
		 right join HoaDonThanhToan hd  on hd.MaHoaDon = cthd.MaHoaDon
		 left join SanPham sp on sp.MaSanPham = cthd.MaSanPham
		 where month(NgayTao) = month(@NgayThangNam) and hd.TrangThaiThanhToan = @TrangThaiThanhToan
	END
	ELSE IF @LocTheo = 'nam'
	BEGIN
		select count(hd.MaHoaDon) as TongDonHang, sum(cthd.SoLuong*sp.GiaBan) as TongTien from ChiTietHoaDon cthd 
		 right join HoaDonThanhToan hd  on hd.MaHoaDon = cthd.MaHoaDon
		 left join SanPham sp on sp.MaSanPham = cthd.MaSanPham
		 where year(NgayTao) = year(@NgayThangNam) and hd.TrangThaiThanhToan = @TrangThaiThanhToan
	END
	ELSE
	BEGIN
		PRINT N'không đọc đc Dữ liệu đầu vào!';
	END 
END
GO
/****** Object:  StoredProcedure [dbo].[getPhieuNhap]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[getPhieuNhap]
    @keyword NVARCHAR(50)
AS
BEGIN
    SELECT MaPhieuNhap, NhaCungCap.TenNhaCungCap, NgayTao
    FROM PhieuNhapHang
    INNER JOIN NhaCungCap ON PhieuNhapHang.MaNhaCungCap = NhaCungCap.MaNhaCungCap
    WHERE MaPhieuNhap LIKE '%' + @keyword + '%' OR TenNhaCungCap LIKE '%' + @keyword + '%'
END
GO
/****** Object:  StoredProcedure [dbo].[getThongKeNhanVienTheoThoiGian]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[getThongKeNhanVienTheoThoiGian]
    @NgayThangNam date, @LocTheo varchar(20)
AS
BEGIN
	IF @LocTheo = 'ngay'
	BEGIN
		select nv.MaNhanVien, nv.HovaTen,count(sp.MaSanPham) as TongDonHang, sum(ct.SoLuong) as TongSoLuong, sum(ct.SoLuong*sp.GiaBan) as DonSo from ChiTietHoaDon ct 
		join SanPham sp on ct.MaSanPham = sp.MaSanPham
		join HoaDonThanhToan hd on ct.MaHoaDon = hd.MaHoaDon
		right join NhanVien nv on nv.MaNhanVien = hd.MaNguoiTao
		where hd.NgayTao = @NgayThangNam
		group by  nv.MaNhanVien, nv.HovaTen 
	END
	ELSE IF @LocTheo = 'thang'
	BEGIN
		select nv.MaNhanVien, nv.HovaTen,count(sp.MaSanPham) as TongDonHang, sum(ct.SoLuong) as TongSoLuong, sum(ct.SoLuong*sp.GiaBan) as DonSo from ChiTietHoaDon ct 
		join SanPham sp on ct.MaSanPham = sp.MaSanPham
		join HoaDonThanhToan hd on ct.MaHoaDon = hd.MaHoaDon
		right join NhanVien nv on nv.MaNhanVien = hd.MaNguoiTao
		where MONTH(hd.NgayTao) = MONTH(@NgayThangNam)
		group by  nv.MaNhanVien, nv.HovaTen 
	END
	ELSE IF @LocTheo = 'nam'
	BEGIN
		select nv.MaNhanVien, nv.HovaTen,count(sp.MaSanPham) as TongDonHang, sum(ct.SoLuong) as TongSoLuong, sum(ct.SoLuong*sp.GiaBan) as DonSo from ChiTietHoaDon ct 
		join SanPham sp on ct.MaSanPham = sp.MaSanPham
		join HoaDonThanhToan hd on ct.MaHoaDon = hd.MaHoaDon
		right join NhanVien nv on nv.MaNhanVien = hd.MaNguoiTao
		where YEAR(hd.NgayTao) = YEAR(@NgayThangNam)
		group by  nv.MaNhanVien, nv.HovaTen 
	END
	ELSE
	BEGIN
		PRINT N'không đọc đc Dữ liệu đầu vào!';
	END
	
END
GO
/****** Object:  StoredProcedure [dbo].[getVonNhapHang]    Script Date: 02/11/2023 6:49:42 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[getVonNhapHang]
    @NgayThangNam date, @LocTheo varchar(20)
AS
BEGIN
	IF @LocTheo = 'ngay'
	BEGIN
		select sum(ct.SoLuong) as SoLuongHangHoa, sum(SoLuong*DonGia) as VonNhapHang from ChiTietPhieuNhap ct 
		inner join PhieuNhapHang pn on ct.MaPhieuNhap = pn.MaPhieuNhap
		where NgayTao = @NgayThangNam
		group by NgayTao
	END
	ELSE IF @LocTheo = 'thang'
	BEGIN
		select sum(ct.SoLuong) as SoLuongHangHoa, sum(SoLuong*DonGia) as VonNhapHang from ChiTietPhieuNhap ct 
		inner join PhieuNhapHang pn on ct.MaPhieuNhap = pn.MaPhieuNhap
		where month(NgayTao) = month(@NgayThangNam)
		group by NgayTao
	END
	ELSE IF @LocTheo = 'nam'
	BEGIN	
		select sum(ct.SoLuong) as SoLuongHangHoa, sum(SoLuong*DonGia) as VonNhapHang from ChiTietPhieuNhap ct 
		inner join PhieuNhapHang pn on ct.MaPhieuNhap = pn.MaPhieuNhap
		where year(NgayTao) = year(@NgayThangNam)
		group by NgayTao
	END
	ELSE
	BEGIN
		PRINT N'không đọc đc Dữ liệu đầu vào!';
	END 
END
GO
