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

//   1.�α���
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

//   2.ȸ������
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

//   3.ȸ������ ����
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

//   4.ȸ������ ����
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
//   5.sha2 ��ȣȭ
	public String convertToSha(String password) {
//      ��ȣȭ�� ���� ���¿��� �����Ͱ� ������ ��ŷ���� �����ϴ�.
		String converted = null;
		StringBuilder builder = null;

		try {
			// ��ȣȭ�� ��� Ŭ����
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// password�� utf-8���ڵ� �н����带 ��ȣȭ�� byte �迭�� ����
			byte[] hash = md.digest(password.getBytes("UTF-8"));

			builder = new StringBuilder();

			for (int i = 0; i < hash.length; i++) {
//            10�������� �����Ͱ� ����Ǿ� �ִ°� 16�������� �����Ϸ��� ���̴�.
//            �̷� �������� ���ؼ� 32�� ���̰� 64���� ���̷� �ȴ�.
				// sha2�� ��ȣȭ ����� 16�������� �ٲٱ� ������ for������ ���ڸ� �޾ƿ°��� 16�������� �ٲٴ� ����
				// %2x 2�ڸ�16���� 0���� ä���� +�θ� ���� �� �ְ�
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