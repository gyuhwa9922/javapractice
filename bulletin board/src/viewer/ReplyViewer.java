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
         String ms = "1. ��� �ۼ� 2. ��� ���� 3. ����";
         int userchoice = ScannerUtil.nextInt(sc, ms);

         if (userchoice == 1) {
            // ���ο� ��� �ۼ� �޼ҵ�
            writeComment(boardId);
         } else if (userchoice == 2) {
            printList();
         } else if (userchoice == 3) {
            System.out.println("����.");
            break;
         }
      }
   }

   public void writeComment(int boardId) {
      ReplyDTO r = new ReplyDTO();
      
      String ms = "����� �ۼ����ּ���.";
      r.setContent(ScannerUtil.nextLine(sc, ms));
      r.setBoardId(boardId);
      r.setWriterId(login.getId());
      
      controller.insert(r);
   }

   public void printList() {
      ArrayList<ReplyDTO> list = controller.selectAll();
      
      if(list.isEmpty()) {
         System.out.println("��ϵ� ����� �����ϴ�.");
      }else {
         for(ReplyDTO r : controller.selectAll()) {
            System.out.println("==========================");
            System.out.println(r.getId()+"��° ���");
            System.out.print("�ۼ��� : ");
            userViewer.printNickname(r.getWriterId());
            System.out.println("����: "+r.getContent());
            System.out.println("==========================");
         }
         String ms = "������ ����� ��ȣ�� �Է��Ͻÿ�. �������� �����ø� 0�� �����ֽÿ�.";
         int userChoice = ScannerUtil.nextInt(sc, ms);
         while(userChoice != 0 && controller.selectOne(userChoice) == null) {
            System.out.println("�߸� �Է��Ͽ����ϴ�.");
            userChoice = ScannerUtil.nextInt(sc, ms);
         }
         if (userChoice != 0) {
            printOne(userChoice);
         }
         
      }
   }
   private void printOne(int id) {
      ReplyDTO r = controller.selectOne(id);
      
      System.out.print("�ۼ���: ");
      userViewer.printNickname(r.getWriterId());
      System.out.println("=========���=========== \n" + r.getContent());
      System.out.println("======================\n");

      String ms;
      if (r.getWriterId() == login.getId()) {
            ms = "1. ���� 2. ����. 3. �ڷΰ���";
            int userChoice = ScannerUtil.nextInt(sc, ms);

            if (userChoice == 1) {
                updateComment(id);
            } else if (userChoice == 2) {
                deleteComment(id);
            } else {
                printList();
            }
        } else {
           
            ms = "�ڽ��� ��۸� ������ �� �ֽ��ϴ�\n"
                  + "1.�ڷΰ���";
            int userChoice = ScannerUtil.nextInt(sc, ms);
            //����ۼ�
            printList();
        }

    }

   private void updateComment(int id) {
      
      ReplyDTO r = controller.selectOne(id);
      r.setContent(ScannerUtil.nextLine(sc, "���ο� ���� �Է�"));
      
      controller.update(r);
   }

   private void deleteComment(int id) {
      String Y_N = ScannerUtil.nextLine(sc, "����� ������ �����Ͻðڽ��ϱ�? y/n");
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