package controller;

import java.util.ArrayList;


import model.BoardDTO;
import model.ReplyDTO;

public class ReplyController {
	private ArrayList<ReplyDTO> list;
	private int nextId;
	
	//초기화 메서드
	public ReplyController() {
		list = new ArrayList<>();
		nextId =1;
	}
	public void insert(ReplyDTO r) {
		r.setId(nextId++);
		list.add(r);
	}
	public ArrayList<ReplyDTO> selectAll(){
		ArrayList<ReplyDTO> temp = new ArrayList<>();
		for(ReplyDTO r : list) {
			temp.add(new ReplyDTO(r));
		}
		return temp;
	}
	

	public ReplyDTO selectOne(int id) {
		for (ReplyDTO r : list) {
			if (r.getId() == id) {
				return new ReplyDTO(r);
			}
		}
		return null;
	}
	
	public void update(ReplyDTO r) {
		list.set(list.indexOf(r), r);
	}
	public void delete(int id) {
		list.remove(new ReplyDTO(id));
	}
	
	public void deleteByReplyId(int writerId) {
		for(int i =0; i<list.size(); i++) {
			ReplyDTO r = list.get(i);
			if(r.getWriterId() == writerId) {
				list.remove(r);
				i= -1;
			}
		}
	}
}
