<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/assets/css/style.css"%></style>
<script type="text/javascript" src="../js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
		<%@include file="/views/header/header.jsp" %>
	<!-- End vung header -->	

<!-- Begin vung dieu kien tim kiem -->	
<form action="" method="post" name="mainform">
	<table  class="tbl_input" border="0" width="90%"  cellpadding="0" cellspacing="0" >		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				会員名称で会員を検索します。検索条件無しの場合は全て表示されます。 
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table class="tbl_input" cellpadding="4" cellspacing="0" >
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="name" value=""
							size="20" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" width="80px">
							<select name="group_id">
								<option value="0">全て</option>								
								<option value="1">Nhóm 1</option>
								<option value="2">Nhóm 2</option>
							</select>							
						</td>
						<td align="left">
							<input class="btn" type="submit" value="検索" />
							<input class="btn" type="button" value="新規追加" />							
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	<!-- End vung dieu kien tim kiem -->
</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0" width="80%">
		
		<tr class="tr2">
			<th align="center" width="20px">
				ID
			</th>
			<th align="left">
				氏名 <a href = "">▲▽</a>
			</th>
			<th align="left">
				生年月日
			</th>
			<th align="left">
				グループ
			</th>
			<th align="left">
				メールアドレス
			</th>
			<th align="left" width="70px">
				電話番号
			</th>
			<th align="left">
				日本語能力  <a href = "">▲▽</a>
			</th>
			<th align="left">
				失効日 <a href = "">△▼</a>
			</th>
			<th align="left">
				点数
			</th>
		</tr>
		
		<tr>
			<td align="right">
				<a href="ADM005">1</a>
			</td>
			<td>
				Nguyễn Thị Mai Hương
			</td>
			<td align="center">
				1983/07/08
			</td>
			<td>
				Phòng QAT
			</td>
			<td>
				ntmhuong@luvina.net
			</td>
			<td>
				0914326386
			</td>
			<td>
				Trình độ tiếng nhật cấp 4
			</td>
			<td align="center">
				2011/07/08
			</td>
			<td align="right">
				290
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href="ADM005">2</a>
			</td>
			<td>
				Lê Thị Xoa
			</td>
			<td align="center">
				1983/07/08
			</td>
			<td>
				Phòng DEV1
			</td>
			<td>
				xoalt@luvina.net
			</td>
			<td>
				1234567894
			</td>
			<td>
				Trình độ tiếng nhật cấp 4
			</td>
			<td align="center">
				2011/07/08
			</td>
			<td align="right">
				290
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href="ADM005">3</a>
			</td>
			<td>
				Đặng Thị Hân
			</td>
			<td align="center">
				1983/07/08
			</td>
			<td>
				Phòng DEV1
			</td>
			<td>
				handt@luvina.net
			</td>
			<td>
				1234567894
			</td>
			<td>
				Trình độ tiếng nhật cấp 4
			</td>
			<td align="center">
				2011/07/08
			</td>
			<td align="right">
				290
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href="ADM005">4</a>
			</td>
			<td>
				Lê Nghiêm Thủy
			</td>			
			<td align="center">
				1983/07/08
			</td>
			<td>
				Phòng DEV1
			</td>
			<td>
				thuyln@luvina.net
			</td>
			<td>
				1234567894
			</td>
			<td>
				Trình độ tiếng nhật cấp 4
			</td>
			<td align="center">
				2011/07/08
			</td>
			<td align="right">
				290
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href="ADM005">5</a>
			</td>
			<td>
				Lê Phương Anh
			</td>
			<td align="center">
				1983/07/08
			</td>
			<td>
				Phòng QAT
			</td>
			<td>
				anhlp@luvina.net
			</td>
			<td>
				1234567894
			</td>
			<td>
				Trình độ tiếng nhật cấp 4
			</td>
			<td align="center">
				2011/07/08
			</td>
			<td align="right">
				290
			</td>
		</tr>
		
	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class = "lbl_paging"><a href = "#">1</a> &nbsp;<a href = "#">2</a> &nbsp;<a href = "#">3</a>&nbsp;<a href = "#">>></a></td>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
		<%@include file="/views/footer/footer.jsp" %>
	<!-- End vung footer -->

</body>

</html>