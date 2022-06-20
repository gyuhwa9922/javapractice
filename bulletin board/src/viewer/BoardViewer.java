package viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controller.BoardController;
import model.BoardDTO;
import model.ReplyDTO;
import model.UserDTO;
import util.ScannerUtil;


public class BoardViewer {
   private BoardController con;
   private Scanner sc;
   private UserViewer userViewer;
   private UserDTO login;
   private ReplyDTO comment;
   private ReplyViewer replyViewer;
   

   public void setReplyViewer(ReplyViewer replyViewer) {
      this.replyViewer = replyViewer;
   }

   public void setComment(ReplyDTO comment) {
      this.comment = comment;
   }

   public void setLogin(UserDTO login) {
      this.login = login;
   }

   public void setUserViewer(UserViewer userViewer) {
      this.userViewer = userViewer;
   }

   public BoardViewer(Scanner sc) {
      con = new BoardController();
      this.sc = sc;
   }

   // 게시글 관련 메뉴 메서드
   public void showMenu() {
      while (true) {
         String message = "1. 새글 작성 2. 글 목록 보기 3.종료";
         int userChoice = ScannerUtil.nextInt(sc, message);

         if (userChoice == 1) {
            // 새 글 작성 메소드
            writeBoard();
         } else if (userChoice == 2) {
            // 글 목록 출력 메소드 호출
            printList();
         } else if (userChoice == 3) {
            System.out.println("사용해주셔서 감사합니다.");
            break;
         }
      }
   }

   public void writeBoard() {
      BoardDTO b = new BoardDTO();

      String ms = "글의 제목을 입력해주십시오.";
      b.setTitle(ScannerUtil.nextLine(sc, ms));
      
      b.setWriterId(login.getId());
      
      ms = "글의 내용을 입력해주십시오.";
      b.setContent(ScannerUtil.nextLine(sc, ms));

      con.insert(b);
   }

   private void printList() {
      ArrayList<BoardDTO> list = con.selectAll();

      if (list.isEmpty()) {
         System.out.println("아직 등록된 글이 존재하지 않습니다.");
      } else {
         Collections.reverse(list);

         for (BoardDTO d : list) {
            System.out.printf(" %d번,  제목: %s \n", d.getId(), d.getTitle());
         }
         String ms = "상세보기할 글의 번호나 뒤로 가실려면 0을 입력하시오.";
         int userChoice = ScannerUtil.nextInt(sc, ms);

         while (userChoice != 0 && con.selectOne(userChoice) == null) {
            System.out.println("잘못 입력하였습니다.");
            userChoice = ScannerUtil.nextInt(sc, ms);
         }
         if (userChoice != 0) {
            
            printOne(userChoice);
         }
      }
   }

   private void printOne(int id) {
      BoardDTO b = con.selectOne(id);
      
      
      System.out.println("======" + b.getTitle() + "=======");
      System.out.println("게시글 " + id + "번");
      System.out.print("작성자: ");
      userViewer.printNickname(b.getWriterId());
      System.out.println();      
      System.out.println("=========내용=========== \n" + b.getContent());
      System.out.println("======================\n");
      
      String ms;
      if (b.getWriterId() == login.getId()) {
            ms = "1. 수정 2. 삭제. 3. 댓글 작성  4. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(sc, ms);

            if (userChoice == 1) {
                updateBoard(id);
            } else if (userChoice == 2) {
                deleteBoard(id);
            } else if(userChoice == 3) {
               replyViewer.setLogin(login);
               replyViewer.showComment(id);
            }else{
                printList();
            }
        } else if(b.getWriterId() != login.getId()) {
            ms = "1.댓글 작성 2.뒤로가기";
            int userChoice = ScannerUtil.nextInt(sc, ms);
            if(userChoice == 1) {
               replyViewer.setLogin(login);
               replyViewer.showComment(id);
            }else{
                printList();
            }
        }

    }
      

   private void updateBoard(int id) {
      // TODO Auto-generated method stub
      BoardDTO b = con.selectOne(id);

      b.setTitle(ScannerUtil.nextLine(sc, "새로운 제목 입력"));
      b.setContent(ScannerUtil.nextLine(sc, "새로운 내용 입력"));

      con.update(b);
      printOne(id);
   }

   private void deleteBoard(int id) {
      // TODO Auto-generated method stub
      String yesNO = ScannerUtil.nextLine(sc, "정말로 삭제하게? Y/N");

      if (yesNO.equalsIgnoreCase("Y")) {
         con.delete(id);
         printList();
      } else {
         printOne(id);
      }

   }
   public void deleteByWriterId(int writerId) {
      con.deleteByWriterId(writerId);
   }
   
}
