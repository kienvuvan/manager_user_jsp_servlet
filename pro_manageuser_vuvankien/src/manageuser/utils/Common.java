/**
 * Copyright (C) 2018 Luvina Academy
 * Common.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.HttpSession;

/**
 * Class chứa các phương thức dùng chung trong projectF
 * 
 * @author kien vu
 *
 */
public class Common {

	/**
	 * Phương thức mã hóa các ký tự đặc biệt trong HTML
	 * 
	 * @param input
	 *            Chuỗi nhập vào
	 * @return Trả về chuỗi sau khi mã hóa
	 */
	public static String enCodeHtml(String input) {
		return input.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("\'", "&#x27;")
				.replaceAll("/", "&#x2F;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * Phương thức tạo thêm 1 chuỗi ngẫu nhiên để mã hóa kèm thêm với chuỗi cần
	 * mã hóa
	 * 
	 * @param length
	 *            Độ dài chuỗi sinh ngẫu nhiên ra
	 * @return Chuỗi sinh ngẫu nhiên
	 */
	public static String creatSalt(int length) {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder(length);
		String stringRandom = Constant.STRING_RANDOM;
		for (int i = 0; i < length; i++) {
			stringBuilder.append(stringRandom.charAt(secureRandom.nextInt(stringRandom.length())));
		}
		return stringBuilder.toString();
	}

	/**
	 * Phương thức mã hóa chuỗi đầu vào theo chuẩn SHA-1
	 * 
	 * @param input
	 *            Chuỗi cần mã hóa
	 * @param salt
	 *            Chuỗi thêm vào để mã hóa
	 * @return Chuỗi sau khi mã hóa
	 * @throws NoSuchAlgorithmException
	 *             Lỗi khi không thực hiện mã hóa theo chuẩn SHA-1
	 */
	public static String encrypt(String input, String salt) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
			throw e;
		}
		return Base64.getEncoder().encodeToString(messageDigest.digest((salt + input).getBytes()));
	}

	/**
	 * Phương thức kiểm tra xem đã có tài khoản admin đăng nhập hay chưa?
	 * 
	 * @param session
	 *            HttpSession
	 * @return true nếu admin đã đăng nhập và ngược lại
	 */
	public static boolean checkLogin(HttpSession session) {
		if (session != null && (String) session.getAttribute("usernameLogin") != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public static ArrayList<Integer> getListPaning(int totalRecords, int limit, int currentPage) {
		ArrayList<Integer> listPaning = new ArrayList<>();
		int sizePaning = Integer.valueOf(ConfigProperties.getData("SIZE_PANNING"));
		int numberPaningStart = currentPage % sizePaning == 0 ? currentPage - sizePaning + 1
				: currentPage - currentPage % sizePaning + 1;
		int totalPaning = (totalRecords % limit == 0) ? totalRecords / limit : totalRecords / limit + 1;
		int numberPaningEnd = (numberPaningStart + sizePaning - 1) * limit > totalRecords ? totalPaning
				: numberPaningStart + sizePaning - 1;
		for (int i = numberPaningStart; i <= numberPaningEnd; i++) {
			listPaning.add(i);
		}
		return listPaning;
	}

	public static String changeSort(String typeSort) {
		return Constant.SORTASC.equals(typeSort) ? Constant.SORTDESC : Constant.SORTASC;
	}

	public static void main(String[] args) {
		System.out.println(getListPaning(5, 1, 5));
	}

}
