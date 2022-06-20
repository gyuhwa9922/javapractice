package main;

import java.util.Scanner;

import viewer.BoardViewer;
import viewer.ReplyViewer;
import viewer.UserViewer;;

public class BoardMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		BoardViewer boardViewer = new BoardViewer(sc);
		UserViewer userViewer = new UserViewer(sc);
		ReplyViewer replyViewer = new ReplyViewer(sc);
		
		
		
		replyViewer.setBoardViewer(boardViewer);
		replyViewer.setUserViewer(userViewer);
		boardViewer.setReplyViewer(replyViewer);
		boardViewer.setUserViewer(userViewer);
		userViewer.setBoardViewer(boardViewer);
		
		
		
		
		userViewer.showIndex();
	}
}
