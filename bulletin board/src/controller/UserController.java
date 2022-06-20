package controller;

import java.util.ArrayList;

import model.UserDTO;

public class UserController {
    private ArrayList<UserDTO> list;
    private int nextId;

    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(UserDTO u) {
        u.setId(nextId++);
        list.add(u);
    }
    //중복 검사
    public boolean validateUsername(String username) {
        for (UserDTO u : list) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }
    
    public UserDTO auth(String username, String password) {
        for(UserDTO u : list) {
        	if(u.getUsername().equalsIgnoreCase(username)&& u.getPassword().equals(password)) {
        		return new UserDTO(u);
        	}
        }
        return null;
    }
    public void update(UserDTO u) {
		int index = list.indexOf(u);
		//list의 인덱스를 u객체의 index에 저장 그리고 Arraylist의 set(업데이트,수정)기능을 이용해 객체 u를 넣음
		list.set(index, u);
	}
    public void delete(int id) {
		list.remove(new UserDTO(id));	
	}
    public UserDTO selectOne(int id) {
    	UserDTO u = new UserDTO(id);
    	if(list.contains(u)) {
    		return new UserDTO(list.get(list.indexOf(u)));
    	}
    	
    	return null;
    }
    
    
}


















