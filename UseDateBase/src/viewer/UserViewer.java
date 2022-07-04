package viewer;

import java.util.Scanner;

import Connecter.DBConnector;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {
	private Scanner scanner;
	private UserDTO logIn;
	private DBConnector connector;

	public UserViewer(Scanner scanner, DBConnector connector) {
		this.scanner = scanner;
		this.connector = connector;
	}

	public void showIndex() {
		String message = "1.로그인 2. 회원가입 3.종료";
		while (true) {
			int userChoice = ScannerUtil.nextInt(scanner, message);
			if (userChoice == 1) {
				userlogIn();
				if (logIn != null) {
					showMenu();
				}
			} else if (userChoice == 2) {
				register();
			} else if (userChoice == 3) {
				System.out.println("종료.");
				break;
			}
		}
	}

	private void userlogIn() {
		String message;

		message = "아이디를 입력해주세요";
		String username = ScannerUtil.nextLine(scanner, message);
		message = "비밀번호를 입력해주세요";
		String password = ScannerUtil.nextLine(scanner, message);

		UserController controller = new UserController(connector);

		logIn = controller.login(username, password);

		while (logIn == null) {
			System.out.println("잘못 입력하였습니다.");
			username = ScannerUtil.nextLine(scanner, "아이디 를 입력해주세요 뒤로 가시려면 x를 입력해주세요");
			// username에 입력된 값을 대소문자 구분없이 x값이면 참이여서 break실행
			if (username.equalsIgnoreCase("X")) {
				break;
			}
			password = ScannerUtil.nextLine(scanner, "비밀번호 입력");
			logIn = controller.login(username, password);
		}
	}

	private void register() {
		UserDTO u = new UserDTO();
		String message = "사용하실 아이디를 입력해주시오.";
		u.setUsername(ScannerUtil.nextLine(scanner, message));

		message = "사용하실 비밀번호를 입력해주세요.";
		u.setPassword(ScannerUtil.nextLine(scanner, message));

		message = "사용하실 닉네임을 입력해주세요.";
		u.setNickname(ScannerUtil.nextLine(scanner, message));

		UserController controller = new UserController(connector);
		while (!controller.register(u)) {
			System.out.println("잘못 입력하셨습니다.");
			String y_n = ScannerUtil.nextLine(scanner, "새로운 아이디를 입력하시겠습니까? y/n");
			if (y_n.equalsIgnoreCase("n")) {
				break;
			}
			u.setUsername(ScannerUtil.nextLine(scanner, "사용하실 아이디를 입력해주세요."));
		}

	}

	private void showMenu() {
		String message = "1. 게시판 2. 회원 정보 3. 로그아웃";
		while (logIn !=null) {
			int userChoice = ScannerUtil.nextInt(scanner, message);
			if (userChoice == 1) {
				// BoardViewer를 생성하고 생성자에 scanner값과 , connector값 그리고 userDTO의 값을 보내준다.
	        		 BoardViewer boardViewer = new BoardViewer(scanner, connector, logIn);
	        		 boardViewer.showMenu();
			} else if (userChoice == 2) {
				printOne();
			} else if (userChoice == 3) {
				System.out.println("로그아웃 되었습니다.");
				logIn = null;
				break;
			}
		}
	}

	private void printOne() {
		System.out.printf("아이디 : %s\n 닉네임 : %s\n", logIn.getUsername(), logIn.getNickname());
		int userChoice = ScannerUtil.nextInt(scanner, "1.회원정보 수정 2.회원 탈퇴 3.뒤로 가기");
		if (userChoice == 1) {
			update();
		} else if (userChoice == 2) {
			userdelete();
		}
	}

	private void userdelete() {
		String y_n = ScannerUtil.nextLine(scanner, "정말로 탈퇴하시겠습니까? y/n");
		//y_n를 대소문자 구분없이 y이면 
		if(y_n.equalsIgnoreCase("y")) {
			String password = ScannerUtil.nextLine(scanner, "회원탈퇴를 위해 비밀번호를 입력해주세요.");
			UserController controller = new UserController(connector);
			
			if(controller.convertToSha(password).equals(password)) {
				controller.delete(logIn.getId());
				logIn = null;
			}
		}
		
	}

	private void update() {
		String message = "새로운 비밀번호를 입력해주세요.";
		String newpassword = ScannerUtil.nextLine(scanner, message);
		message = "새로운 닉네임을 입력해주세요.";
		String newNickname = ScannerUtil.nextLine(scanner, message);
		message = "기존 비밀번호을 입력해주세요.";
		String oldPassword = ScannerUtil.nextLine(scanner,message);
		UserController controller = new UserController(connector);
		if(controller.convertToSha(oldPassword).equals(logIn.getPassword())) {
			logIn.setPassword(newpassword);
			logIn.setNickname(newNickname);
			
			controller.update(logIn);
		}
	}
}
