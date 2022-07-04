package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import Connecter.DBConnector;
import controller.BoardController;
import controller.UserController;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

public class BoardViewer {
	private DBConnector connector;
	private Scanner sc;
	private UserDTO logIn;

	public BoardViewer(Scanner scanner, DBConnector connector, UserDTO logIn) {
		//userviewer에서 입력한 값을 boardviewer의 생성자에 값을 복사
		this.connector = connector;
		this.sc = scanner;
		this.logIn = logIn;
	}

	public void showMenu() {
		String message = "1. 새글 쓰기 2. 글 목록보기 3. 뒤로가기";
		while (true) {
			int userChoice = ScannerUtil.nextInt(sc, message);
			//userChoice가 1이면  새글 작성하는 메소드
			if (userChoice == 1) {
				boardwrite();
			} 
			//userChoice가 2이면 글 목록 띄어주는 메소드 
			else if (userChoice == 2) {
				printList();
			} 
			//userChoice가 3이면 
			else if (userChoice == 3) {
				break;//종료
			}
		}
	}

	private void boardwrite() {
		//boardDTO를 생성과 초기화
		BoardDTO b = new BoardDTO();
		//BoardDTO의 Writerid에 userDTO의 id값을 복사
		b.setWriterId(logIn.getId());
		//BoardDTO의 title에 입력받은 값을 넣음
		b.setTitle(ScannerUtil.nextLine(sc, "제목을 입력해주세요."));
		//BoardDTO에 입력받은 Content를 넣음
		b.setContent(ScannerUtil.nextLine(sc, "내용을 입력해주세요."));
		//boardcontroller에 mysql연결
		BoardController boardController = new BoardController(connector);
		//boardcontroller에 insert메서드에 b라는 값을 넣음
		boardController.insert(b);
	}

	private void printList() {
		//boardcontroller에 mysql연결
		BoardController boardController = new BoardController(connector);
		//list에 selecAll이라는 메소드의 값을 넣음
		ArrayList<BoardDTO> list = boardController.selectAll();
		//만약에 list의 값이 없으면
		if (list.isEmpty()) {
			System.out.println("아직 등록된 글이 없습니다.");
		}
		//값이 있으면
		else {
			for (BoardDTO b : list) {
				// 향상된 for문(foreach)를 사용해 순번과 제목을 출력(전부)
				System.out.printf("%d, %s \n", b.getId(), b.getTitle());
			}
			String message = "상세보기할 글의 번호나 뒤로 가실려면 0을 입력해주세요.";
			int userChoice = ScannerUtil.nextInt(sc, message);
			//반복문(userchoice가 0이 아니고 selectOne(userchoice)의 안의 값이 null이 아니면(선택한 아이디가 null이 아니면)
			while (userChoice != 0 && boardController.selectOne(userChoice) == null) {
				System.out.println("잘못입력했습니다.");
				//재입력
				userChoice = ScannerUtil.nextInt(sc, message);
			}
			//만약에 userchoice가 0이 아니면
			if (userChoice != 0) {
				printOne(userChoice);
			}
		}
	}

	private void printOne(int id) {
		//boardcontroller에 mysql연결
		BoardController bcontroller = new BoardController(connector);
		//usercontroller에 mysql연결
		UserController ucontroller = new UserController(connector);
		//boardDTO타입의 b는 printOne에 받아온 id값을 통해 boardcontroller의 selectOne의 메소드의 id값과 같은 dto객체 값을 가져옴
 		BoardDTO b = bcontroller.selectOne(id);
 		//b의 값을 출력
		System.out.println("제목: " + b.getTitle());
		System.out.println("글번호: " + b.getId());
		System.out.println("글 작성자: " + ucontroller.selectOne(b.getWriterId()).getNickname());
		System.out.println("========= 내용 ==========");
		System.out.println(b.getContent());
		//replyviewer를 생성 및 초기화
		ReplyViewer replyViewer = new ReplyViewer(sc, connector, logIn);
		//replyviewer의 printlist메소드에 id값을 보냄
		replyViewer.printList(id);
		//만약에 userDTO의 ID값이 boardDTO ID값과 같다면 (작성자와 로그인한 사람이 같으면)
		if (logIn.getId() == b.getWriterId()) {
			int userChoice = ScannerUtil.nextInt(sc, "1.글 수정하기 2. 글 삭제하기 3. 댓글작성 4.뒤로 가기");
			//userchoice가 1일때
			if (userChoice == 1) {
				//update메소드에 id값을 보냄
				update(id);
			}
			//userchoice가 2일때
			else if (userChoice == 2) {
				//삭제하는 메소드에 id값을 보냄
				delete(id);
			}
			//userchoice가 3일때
			else if(userChoice ==3) {
				replyViewer.write(id);
			}
			//userchoice가 4일때
			else if (userChoice == 4) {
				printList();
			}
		}
		//만약에 userDTO의 ID값이 boardDTO ID값과 같지않다면 (작성자와 로그인한 사람이 같지않으면)
		else {
			int userChoice = ScannerUtil.nextInt(sc, "1.댓글남기기 2. 뒤로 가기");
			//userchoice가 1이면
			if(userChoice == 1) {
				//replyviewer에 있는 write메소드에 id값을 보냄
				replyViewer.write(id);
			}
			//userchoice가 2이면
			else if(userChoice == 2) {
				//printlist메소드 실행
				printList();
			}
		}
	}
	private void update(int id) {
		//mysql연결 boardcontroller를
		BoardController boardController = new BoardController(connector);
		//boardDTO타입의 b를 boardcontroller의 selectOne메소드에 id값을 넣음
		BoardDTO b= boardController.selectOne(id);
		//BoardDTO의 title의 값을 넣음
		b.setTitle(ScannerUtil.nextLine(sc, "새로운 제목을 입력하여주세요."));
		//BoardDTO의 content의 값을 넣음
		b.setContent(ScannerUtil.nextLine(sc, "새로운 내용을 입력하여주세요."));
		//boardcontroller에 update메소드에 b(boardDTO)를 넘겨줌
		boardController.update(b);
		//printOne메소드에 id값을 넣음
		printOne(id);
	}
	  private void delete(int id) {
			//mysql연결 boardcontroller를
	        BoardController controller = new BoardController(connector);
	        String yesNo = ScannerUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
	        //yesNo의 값이 대소문자 구분없이 y이면
	        if(yesNo.equalsIgnoreCase("Y")) {
	        	//boardcontroller의 delete메소드에 id값을 넣어 실행
	            controller.delete(id);
	            //printList메소드 실행
	            printList();
	        } else {
	        	//printOne의 메소드에 id값을 넣고 실행
	            printOne(id);
	        }
	    }
}
