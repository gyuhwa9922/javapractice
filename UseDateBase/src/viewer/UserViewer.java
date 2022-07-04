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
		String message = "1.�α��� 2. ȸ������ 3.����";
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
				System.out.println("����.");
				break;
			}
		}
	}

	private void userlogIn() {
		String message;

		message = "���̵� �Է����ּ���";
		String username = ScannerUtil.nextLine(scanner, message);
		message = "��й�ȣ�� �Է����ּ���";
		String password = ScannerUtil.nextLine(scanner, message);

		UserController controller = new UserController(connector);

		logIn = controller.login(username, password);

		while (logIn == null) {
			System.out.println("�߸� �Է��Ͽ����ϴ�.");
			username = ScannerUtil.nextLine(scanner, "���̵� �� �Է����ּ��� �ڷ� ���÷��� x�� �Է����ּ���");
			// username�� �Էµ� ���� ��ҹ��� ���о��� x���̸� ���̿��� break����
			if (username.equalsIgnoreCase("X")) {
				break;
			}
			password = ScannerUtil.nextLine(scanner, "��й�ȣ �Է�");
			logIn = controller.login(username, password);
		}
	}

	private void register() {
		UserDTO u = new UserDTO();
		String message = "����Ͻ� ���̵� �Է����ֽÿ�.";
		u.setUsername(ScannerUtil.nextLine(scanner, message));

		message = "����Ͻ� ��й�ȣ�� �Է����ּ���.";
		u.setPassword(ScannerUtil.nextLine(scanner, message));

		message = "����Ͻ� �г����� �Է����ּ���.";
		u.setNickname(ScannerUtil.nextLine(scanner, message));

		UserController controller = new UserController(connector);
		while (!controller.register(u)) {
			System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			String y_n = ScannerUtil.nextLine(scanner, "���ο� ���̵� �Է��Ͻðڽ��ϱ�? y/n");
			if (y_n.equalsIgnoreCase("n")) {
				break;
			}
			u.setUsername(ScannerUtil.nextLine(scanner, "����Ͻ� ���̵� �Է����ּ���."));
		}

	}

	private void showMenu() {
		String message = "1. �Խ��� 2. ȸ�� ���� 3. �α׾ƿ�";
		while (logIn !=null) {
			int userChoice = ScannerUtil.nextInt(scanner, message);
			if (userChoice == 1) {
				// BoardViewer�� �����ϰ� �����ڿ� scanner���� , connector�� �׸��� userDTO�� ���� �����ش�.
	        		 BoardViewer boardViewer = new BoardViewer(scanner, connector, logIn);
	        		 boardViewer.showMenu();
			} else if (userChoice == 2) {
				printOne();
			} else if (userChoice == 3) {
				System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
				logIn = null;
				break;
			}
		}
	}

	private void printOne() {
		System.out.printf("���̵� : %s\n �г��� : %s\n", logIn.getUsername(), logIn.getNickname());
		int userChoice = ScannerUtil.nextInt(scanner, "1.ȸ������ ���� 2.ȸ�� Ż�� 3.�ڷ� ����");
		if (userChoice == 1) {
			update();
		} else if (userChoice == 2) {
			userdelete();
		}
	}

	private void userdelete() {
		String y_n = ScannerUtil.nextLine(scanner, "������ Ż���Ͻðڽ��ϱ�? y/n");
		//y_n�� ��ҹ��� ���о��� y�̸� 
		if(y_n.equalsIgnoreCase("y")) {
			String password = ScannerUtil.nextLine(scanner, "ȸ��Ż�� ���� ��й�ȣ�� �Է����ּ���.");
			UserController controller = new UserController(connector);
			
			if(controller.convertToSha(password).equals(password)) {
				controller.delete(logIn.getId());
				logIn = null;
			}
		}
		
	}

	private void update() {
		String message = "���ο� ��й�ȣ�� �Է����ּ���.";
		String newpassword = ScannerUtil.nextLine(scanner, message);
		message = "���ο� �г����� �Է����ּ���.";
		String newNickname = ScannerUtil.nextLine(scanner, message);
		message = "���� ��й�ȣ�� �Է����ּ���.";
		String oldPassword = ScannerUtil.nextLine(scanner,message);
		UserController controller = new UserController(connector);
		if(controller.convertToSha(oldPassword).equals(logIn.getPassword())) {
			logIn.setPassword(newpassword);
			logIn.setNickname(newNickname);
			
			controller.update(logIn);
		}
	}
}
