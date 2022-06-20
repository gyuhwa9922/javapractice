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
		// �⺻ ������
		// ��ĳ�ʿ� userController�� �ʱ�ȭ����
		this.sc = sc;
		userController = new UserController();
	}

	public void showIndex() {
		while (true) {
			int userchoice = ScannerUtil.nextInt(sc, "1. �α��� 2. ȸ������ 3.����");

			if (userchoice == 1) {
				// �α��� �޼ҵ�
				userlogin();
				if(login != null) {
					boardViewer.setLogin(login);
					showMenu();
				}
			} else if (userchoice == 2) {
				// ȸ������ �޼ҵ�
				user_regi();
			} else if (userchoice == 3) {
				System.out.println("����");
				break;
			}

		}
	}

	public void showMenu() {
		while(login != null) {
			int userchoice=ScannerUtil.nextInt(sc, "1.�Խ��� 2.ȸ������ �󼼺��� 3.�α׾ƿ�");
			if(userchoice ==1) {
				//ȸ������ �󼼺��� �޼ҵ�
				boardViewer.showMenu();
			}else if(userchoice ==2) {
				printOne();
			}else if(userchoice==3){
				System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
				login = null;
			}
		}	
	}
	public void printOne() {
		System.out.println("ȸ�� ���̵� : " + login.getUsername()+"  ȸ�� �г��� : "+login.getNickname());
		int userch = ScannerUtil.nextInt(sc, "1.���� ���� 2.ȸ�� Ż�� 3.�ڷ� ����");
		if(userch ==1) {
			update();
		}else if (userch==2) {
			delete();
		}
		
	}
	public void delete() {
		String Y_N = ScannerUtil.nextLine(sc, "������ Ż���Ͻðڽ��ϱ�?(Y/N");
		if(Y_N.equalsIgnoreCase("Y")) {
			String oldpassword = ScannerUtil.nextLine(sc, "��й�ȣ�� �Է��Ͻÿ�.");
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
		String oldpassword = ScannerUtil.nextLine(sc, "���� ��й�ȣ�� �Է����ֽʽÿ�.");
		String password = ScannerUtil.nextLine(sc, "�����Ͻ� ��й�ȣ�� �Է��Ͻÿ�.");
		String nickname = ScannerUtil.nextLine(sc, "�����Ͻ� �г����� �Է��Ͻÿ�,");
		
		if(login.getPassword().equals(oldpassword)) {
			login.setPassword(password);
			login.setNickname(nickname);
			userController.update(login);
			System.out.println("��й�ȣ�� ����Ǿ����ϴ�.");
		}
		printOne();
		
	}

	public void userlogin() {
		String username = ScannerUtil.nextLine(sc, "���̵� �Է����ֽʽÿ�.");
		String password = ScannerUtil.nextLine(sc, "��й�ȣ�� �Է����ֽʽÿ�.");

		while (userController.auth(username, password) == null) {
			System.out.println("�߸��Է��ϼ̽��ϴ�.");
			String Y_N = ScannerUtil.nextLine(sc, "�α����� �׸��Ͻðڽ��ϱ�? (Y/N)");
			if (Y_N.equalsIgnoreCase("Y")) {
				password = null;
				break;
			}
			username = ScannerUtil.nextLine(sc, "���̵� �Է����ֽʽÿ�.");
			password = ScannerUtil.nextLine(sc, "��й�ȣ�� �Է����ֽʽÿ�.");
		}
		login = userController.auth(username, password);
	}

	public void user_regi() {
		String username = ScannerUtil.nextLine(sc, "����Ͻ� ���̵� �Է����ּ��� �ڷ� ���÷��� x�� �Է��Ͽ��ֽʽÿ�.");

		while (!username.equalsIgnoreCase("X") && userController.validateUsername(username)) {
			System.out.println("�߸� �Է��Ͽ����ϴ�.");
			username = ScannerUtil.nextLine(sc, "����Ͻ� ���̵� �Է����ּ��� �ڷ� ���÷��� x�� �Է��Ͽ��ֽʽÿ�.");
		}
		if (!username.equalsIgnoreCase("X")) {
			String password = ScannerUtil.nextLine(sc, "����Ͻ� ����� �Է��Ͻÿ�.");
			String nickname = ScannerUtil.nextLine(sc, "����Ͻ� �г����� �Է��Ͻÿ�.");
			UserDTO u = new UserDTO();
			u.setPassword(password);
			u.setNickname(nickname);
			u.setUsername(username);

			// id�� �ϳ��� �ø��� list�� u��ü�� �ִ´�.
			userController.insert(u);
		}
	}
	public void printNickname(int id) {
		UserDTO u = userController.selectOne(id);
		System.out.println(u.getNickname());
	}
}
