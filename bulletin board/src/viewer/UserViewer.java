package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {

	private Scanner sc;
	private UserController userController;
	private UserDTO login;
	private BoardViewer boardViewer;
	

	public void setBoardViewer(BoardViewer boardViewer) {
		this.boardViewer = boardViewer;
	}

	public UserViewer(Scanner sc) {
		// 기본 생성자
		// 스캐너와 userController를 초기화해줌
		this.sc = sc;
		userController = new UserController();
	}

	public void showIndex() {
		while (true) {
			int userchoice = ScannerUtil.nextInt(sc, "1. 로그인 2. 회원가입 3.종료");

			if (userchoice == 1) {
				// 로그인 메소드
				userlogin();
				if(login != null) {
					boardViewer.setLogin(login);
					showMenu();
				}
			} else if (userchoice == 2) {
				// 회원가입 메소드
				user_regi();
			} else if (userchoice == 3) {
				System.out.println("종료");
				break;
			}

		}
	}

	public void showMenu() {
		while(login != null) {
			int userchoice=ScannerUtil.nextInt(sc, "1.게시판 2.회원정보 상세보기 3.로그아웃");
			if(userchoice ==1) {
				//회원정보 상세보기 메소드
				boardViewer.showMenu();
			}else if(userchoice ==2) {
				printOne();
			}else if(userchoice==3){
				System.out.println("로그아웃 되었습니다.");
				login = null;
			}
		}	
	}
	public void printOne() {
		System.out.println("회원 아이디 : " + login.getUsername()+"  회원 닉네임 : "+login.getNickname());
		int userch = ScannerUtil.nextInt(sc, "1.정보 수정 2.회원 탈퇴 3.뒤로 가기");
		if(userch ==1) {
			update();
		}else if (userch==2) {
			delete();
		}
		
	}
	public void delete() {
		String Y_N = ScannerUtil.nextLine(sc, "정말로 탈퇴하시겠습니까?(Y/N");
		if(Y_N.equalsIgnoreCase("Y")) {
			String oldpassword = ScannerUtil.nextLine(sc, "비밀번호를 입력하시오.");
			if(oldpassword.equals(login.getPassword())) {
				userController.delete(login.getId());
				boardViewer.deleteByWriterId(login.getId());
				login = null;
			}
		}
		if(login !=null) {
			printOne();
		}
	}

	public void update() {
		String oldpassword = ScannerUtil.nextLine(sc, "기존 비밀번호를 입력해주십시오.");
		String password = ScannerUtil.nextLine(sc, "변경하실 비밀번호를 입력하시오.");
		String nickname = ScannerUtil.nextLine(sc, "변경하실 닉네임을 입력하시오,");
		
		if(login.getPassword().equals(oldpassword)) {
			login.setPassword(password);
			login.setNickname(nickname);
			userController.update(login);
			System.out.println("비밀번호가 변경되었습니다.");
		}
		printOne();
		
	}

	public void userlogin() {
		String username = ScannerUtil.nextLine(sc, "아이디를 입력해주십시오.");
		String password = ScannerUtil.nextLine(sc, "비밀번호를 입력해주십시오.");

		while (userController.auth(username, password) == null) {
			System.out.println("잘못입력하셨습니다.");
			String Y_N = ScannerUtil.nextLine(sc, "로그인을 그만하시겠습니까? (Y/N)");
			if (Y_N.equalsIgnoreCase("Y")) {
				password = null;
				break;
			}
			username = ScannerUtil.nextLine(sc, "아이디를 입력해주십시오.");
			password = ScannerUtil.nextLine(sc, "비밀번호를 입력해주십시오.");
		}
		login = userController.auth(username, password);
	}

	public void user_regi() {
		String username = ScannerUtil.nextLine(sc, "사용하실 아이디를 입력해주세요 뒤로 가시려면 x를 입력하여주십시오.");

		while (!username.equalsIgnoreCase("X") && userController.validateUsername(username)) {
			System.out.println("잘못 입력하였습니다.");
			username = ScannerUtil.nextLine(sc, "사용하실 아이디를 입력해주세요 뒤로 가시려면 x를 입력하여주십시오.");
		}
		if (!username.equalsIgnoreCase("X")) {
			String password = ScannerUtil.nextLine(sc, "사용하실 비번을 입력하시오.");
			String nickname = ScannerUtil.nextLine(sc, "사용하실 닉네임을 입력하시오.");
			UserDTO u = new UserDTO();
			u.setPassword(password);
			u.setNickname(nickname);
			u.setUsername(username);

			// id를 하나씩 올리고 list에 u객체를 넣는다.
			userController.insert(u);
		}
	}
	public void printNickname(int id) {
		UserDTO u = userController.selectOne(id);
		System.out.println(u.getNickname());
	}
}
