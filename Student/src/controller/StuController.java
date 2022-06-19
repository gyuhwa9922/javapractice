package controller;

import java.util.ArrayList;
import model.StuDTO;

public class StuController {
	//필드
	private ArrayList<StuDTO> list;
	private int nextId;
	
	public StuController() {
		list = new ArrayList<>();
		nextId = 1;
	}
	
	public void insert(StuDTO d) {
		d.setSt_id(nextId++);
		list.add(d);
	}
	
	public ArrayList<StuDTO> selectAll(){
		ArrayList<StuDTO> eee = new ArrayList<>();
		for(StuDTO d : list) {
			eee.add(new StuDTO(d));
		}
		return eee;
	}
	
	public StuDTO selectOne(int st_id) {
		for(StuDTO d : list) {
			//StuDTO의 받은 값이 st_id와 같으면
			if(d.getSt_id() == st_id) {
				return new StuDTO(d);
			}
		}
		return null;
	}
	
	public void update(StuDTO b) {
		list.set(list.indexOf(b), b);
	}
	
	public void delete(int st_id) {
		list.remove(new StuDTO(st_id));
	}
	
}
