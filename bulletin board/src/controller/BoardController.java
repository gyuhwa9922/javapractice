package controller;

import java.util.ArrayList;
import model.BoardDTO;

/*
 * ��Ʈ�ѷ�
 * ��Ʈ�ѷ��� �����ͺ��̽��κ��� ���� �޾Ƽ� �� �ѷ��� ����
 * ��ü�� ���·� �����ְų�
 * �ƴϸ� ���� �Է¹��� ���� ��ü�� ���·� �޾Ƽ�
 * �����ͺ��̽��� �����ִ�
 * ������ �߰��ٸ� ������ �´� Ŭ�����̴�.
 * 
 * ��, ���� ����� �츮���� �����ͺ��̽��� ���ٴ� ������ �ֱ� ������
 * ���� �����ͺ��̽� ������ ���� ArrayLisr<BoardDTO>�� �ʵ�� �ϳ� ������ �ȴ�.*/
public class BoardController {

	private ArrayList<BoardDTO> list;
	private int nextId;

	public BoardController() {
		list = new ArrayList<>();
		nextId = 1;
	}

	public void insert(BoardDTO b) {
		b.setId(nextId++);
		list.add(b);
	}

	public ArrayList<BoardDTO> selectAll() {
		ArrayList<BoardDTO> temp = new ArrayList<>();

		for (BoardDTO b : list) {
			temp.add(new BoardDTO(b));
		}
		return temp;
	}

	/*
	 * Ư�� id ���� ���� BoradDTO ��ü�� �����ϴ� selectOne() �� , �ش�id���� �������� �ʴ´ٸ� null�� ���ϵȴ�.
	 */
	public BoardDTO selectOne(int id) {
		for (BoardDTO b : list) {
			if (b.getId() == id) {
				return new BoardDTO(b);
			}
		}
		return null;
	}

	/*
	 * ����Ʈ�� Ư�� ��ü�� �����ϴ� update() �޼ҵ�
	 */
	public void update(BoardDTO b) {
//		int index = list.indexOf(b);
		list.set(list.indexOf(b), b);

	}

	public void delete(int id) {
		list.remove(new BoardDTO(id));
	}
	//����Ʈ�� Ư�� ȸ���� ���� �����ϴ�
	// deleteByWriterId()
	public void deleteByWriterId(int writerId) {
		for(int i =0; i<list.size(); i++) {
			BoardDTO b = list.get(i);
			if(b.getWriterId() == writerId) {
				list.remove(b);
				i= -1;
			}
		}
	}
}
