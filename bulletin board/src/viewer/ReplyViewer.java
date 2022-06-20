package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.ReplyController;
import model.BoardDTO;
import model.ReplyDTO;
import model.UserDTO;
import util.ScannerUtil;

public class ReplyViewer {

   private Scanner sc;
   private ReplyController controller;
   private BoardViewer boardViewer;
   private UserDTO login;
   private UserViewer userViewer;

   

   public void setUserViewer(UserViewer userViewer) {
      this.userViewer = userViewer;
   }

   public void setBoardViewer(BoardViewer boardViewer) {
      this.boardViewer = boardViewer;
   }

   public void setLogin(UserDTO login) {
      this.login = login;
   }
   public ReplyViewer(Scanner sc) {
      this.sc = sc;
      controller = new ReplyController();
   }
   public void showComment(int boardId) {
      while (true) {
         String ms = "1. 댓글 작성 2. 댓글 수정 3. 종료";
         int userchoice = ScannerUtil.nextInt(sc, ms);

         if (userchoice == 1) {
            // 새로운 댓글 작성 메소드
            writeComment(boardId);
         } else if (userchoice == 2) {
            printList();
         } else if (userchoice == 3) {
            System.out.println("종료.");
            break;
         }
      }
   }

   public void writeComment(int boardId) {
      ReplyDTO r = new ReplyDTO();
      
      String ms = "댓글을 작성해주세요.";
      r.setContent(ScannerUtil.nextLine(sc, ms));
      r.setBoardId(boardId);
      r.setWriterId(login.getId());
      
      controller.insert(r);
   }

   public void printList() {
      ArrayList<ReplyDTO> list = controller.selectAll();
      
      if(list.isEmpty()) {
         System.out.println("등록된 댓글이 없습니다.");
      }else {
         for(ReplyDTO r : controller.selectAll()) {
            System.out.println("==========================");
            System.out.println(r.getId()+"번째 댓글");
            System.out.print("작성자 : ");
            userViewer.printNickname(r.getWriterId());
            System.out.println("내용: "+r.getContent());
            System.out.println("==========================");
         }
         String ms = "수정할 댓글의 번호를 입력하시오. 수정하지 않으시면 0을 눌러주시오.";
         int userChoice = ScannerUtil.nextInt(sc, ms);
         while(userChoice != 0 && controller.selectOne(userChoice) == null) {
            System.out.println("잘못 입력하였습니다.");
            userChoice = ScannerUtil.nextInt(sc, ms);
         }
         if (userChoice != 0) {
            printOne(userChoice);
         }
         
      }
   }
   private void printOne(int id) {
      ReplyDTO r = controller.selectOne(id);
      
      System.out.print("작성자: ");
      userViewer.printNickname(r.getWriterId());
      System.out.println("=========댓글=========== \n" + r.getContent());
      System.out.println("======================\n");

      String ms;
      if (r.getWriterId() == login.getId()) {
            ms = "1. 수정 2. 삭제. 3. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(sc, ms);

            if (userChoice == 1) {
                updateComment(id);
            } else if (userChoice == 2) {
                deleteComment(id);
            } else {
                printList();
            }
        } else {
           
            ms = "자신의 댓글만 수정할 수 있습니다\n"
                  + "1.뒤로가기";
            int userChoice = ScannerUtil.nextInt(sc, ms);
            //댓글작성
            printList();
        }

    }

   private void updateComment(int id) {
      
      ReplyDTO r = controller.selectOne(id);
      r.setContent(ScannerUtil.nextLine(sc, "새로운 내용 입력"));
      
      controller.update(r);
   }

   private void deleteComment(int id) {
      String Y_N = ScannerUtil.nextLine(sc, "댓글을 정말로 삭제하시겠습니까? y/n");
      if (Y_N.equalsIgnoreCase("Y")) {
         controller.delete(id);
         printList();
      } else {
         printOne(id);
      }
      
   }
   public void deleteByReplyId(int writerId) {
      controller.deleteByReplyId(writerId);
   }
   
   
}