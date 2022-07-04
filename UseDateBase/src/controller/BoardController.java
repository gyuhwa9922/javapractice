package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connecter.DBConnector;
import model.BoardDTO;

public class BoardController {
	private Connection con;

	public BoardController(DBConnector connector) {
		// TODO Auto-generated constructor stub
		con = connector.makeConnection();
	}

	// 글 입력 메소드
	public void insert(BoardDTO b) {
		String query = "INSERT INTO `board`(`writerid`, `title`, `content`) VALUES(?, ?, ?)";
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			//파라미터 1에는 boardDTO의 writerId값을 넣음
			pstmt.setInt(1, b.getWriterId());
			//파라미터 2에는 boardDTO의 title값을 넣음
			pstmt.setString(2, b.getTitle());
			//파라미터 3에는 boardDTO의 content값을 넣음
			pstmt.setString(3, b.getContent());
			//반영된 레코드의 건수를 반환
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 글 수정 메소드
	public void update(BoardDTO b) {
		// update board테이블의 title의 값과 content의 값 id의 값에 들어있는
		String query = "UPDATE `board` SET `title` = ?, `content` = ? WHERE `id` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			//파라미터 1에 boardDTO의 title값을 넣음
			pstmt.setString(1, b.getTitle());
			//파라미터 2에 boardDTO의 content값을 넣음
			pstmt.setString(2, b.getContent());
			//파라미터 3에 boardDTO의 id값을 넣음
			pstmt.setInt(3, b.getId());
			//반영된 레코드의 건수를 반환
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 글 삭제 메소드
	public void delete(int id) {
		String query = "DELETE FROM `board` WHERE `id` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			//파라미터 1에 받아온 id값을 넣음
			pstmt.setInt(1, id);
			//반영된 레코드의 건수를 반환
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<BoardDTO> selectAll() {
		//boardDTO타입의 ArrayList를 선언
		ArrayList<BoardDTO> list = new ArrayList<>();
		String query = "SELECT * FROM `board`";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO b = new BoardDTO();
				b.setId(rs.getInt("id"));
				b.setWriterId(rs.getInt("writerid"));
				b.setTitle(rs.getString("title"));
				b.setContent(rs.getString("content"));

				list.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 글 개별 조회
	public BoardDTO selectOne(int id) {
		BoardDTO b = null;
		String query = "SELECT * FROM `board` WHERE id = ?";
		try {
            PreparedStatement pstmt = con.prepareStatement(query);
            //파라미터 1에 id값을 넣음
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                b = new BoardDTO();
                b.setId(rs.getInt("id"));
                b.setWriterId(rs.getInt("writerid"));
                b.setTitle(rs.getString("title"));
                b.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return b;
	}
}
