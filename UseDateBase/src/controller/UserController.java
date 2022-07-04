package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Connecter.DBConnector;
import model.UserDTO;

public class UserController {
	private Connection conn;

	public UserController(DBConnector conn) {
		this.conn = conn.makeConnection();
	}

//   1.로그인
	public UserDTO login(String username, String password) {
		String query = "SELECT *FROM `user` WHERE `username` = ? AND `userpassword` = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, convertToSha(password));

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				UserDTO u = new UserDTO();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("userpassword"));
				u.setNickname(rs.getString("nickname"));

				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

//   2.회원가임
	public boolean register(UserDTO u) {
		String query = "INSERT INTO `user`(`username`,`userpassword`,`nickname`) VALUES(?,?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, convertToSha(u.getPassword()));
			pstmt.setString(3, u.getNickname());

			pstmt.executeUpdate();

		} catch (SQLException e) {
//			e.printStackTrace();
			return false;
		}

		return true;
	}

//   3.회원정보 수정
	public void update(UserDTO u) {
		String query = "UPDATE `user` SET `userpassword` = ?, `nickname` = ? WHERE `id` = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, convertToSha(u.getPassword()));
			pstmt.setString(2, u.getNickname());
			pstmt.setInt(3, u.getId());

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//   4.회원정보 삭제
	public void delete(int id) {
		String query = "DELETE FROM `user` WHERE `id` = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
//   5.sha2 함호화
	public String convertToSha(String password) {
//      암호화가 끝난 상태에서 데이터가 오고가야 해킹에서 안전하다.
		String converted = null;
		StringBuilder builder = null;

		try {
			// 암호화의 담당 클래스
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// password를 utf-8인코딩 패스워드를 암호화된 byte 배열로 저장
			byte[] hash = md.digest(password.getBytes("UTF-8"));

			builder = new StringBuilder();

			for (int i = 0; i < hash.length; i++) {
//            10진법으로 데이터가 저장되어 있는걸 16진법으로 변경하려는 것이다.
//            이런 변경으로 인해서 32의 길이가 64개의 길이로 된다.
				// sha2의 암호화 기법은 16진법으로 바꾸기 때문에 for문으로 문자를 받아온것을 16진법으로 바꾸는 문장
				// %2x 2자리16진법 0으로 채워라 +로만 나올 수 있게
				builder.append(String.format("%02x", 255 & hash[i])); 
			}
			converted = builder.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return converted;
	}
	public UserDTO selectOne(int id) {
        UserDTO u = null;
        String query = "SELECT * FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                u = new UserDTO();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setNickname(rs.getString("nickname"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }
}