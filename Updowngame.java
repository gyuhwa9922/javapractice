package Practice2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Updowngame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int turncnt = 0;
		ArrayList<Integer> record = new ArrayList<Integer>();
		boolean sw = true;
		Scanner s = new Scanner(System.in);
		while (sw) {
			int rannum = (int) Math.round(Math.random() * 100);
			System.out.println("하실 업무를 눌러주십시오.");
			System.out.println("1. 게임하기 | 2. 기록보기 | 3. 종료");
			int choose = s.nextInt();
			switch (choose) {
			case 1:
				while (true) {
					System.out.println("=========================");
					System.out.println("숫자를 맞춰보십시오. (1~100) ");
					System.out.println("=========================");
					int chonum = s.nextInt();
					System.out.println(rannum);
					turncnt++;
					if (chonum < 0 || chonum>100) {
						System.out.println("1~100까지의 숫자를 입력하여 주십시오.");
						turncnt --;
					} else if (chonum > 0) {
						if (chonum == rannum) {
							System.out.println("정답입니다.");
							record.add(turncnt);
							Collections.sort(record);
							turncnt = 0;
							break;
						} else if (chonum > rannum) {
							System.out.println("정답이 아닙니다.(down)");
						} else if (chonum < rannum) {
							System.out.println("정답이 아닙니다.(up)");
						}
					}
				}
				break;
			case 2:
				if (record.size() == 0) {
					System.out.println("기록이 없습니다.");
				} else if (record.size()>0) {
					System.out.println("기록을 출력합니다.");
					for (int i = 0; i < record.size(); i++) {
						System.out.println((i + 1) + "등 기록은" + record.get(i) + "입니다.");
					}
				}
				break;
			case 3:
				System.out.println("시스템을 종료합니다.");
				sw = false;
				break;
			default:
				System.out.println("1 , 2 , 3 중 하나를 입력하여 주십시오");
				break;
			}

		}

	}

}
