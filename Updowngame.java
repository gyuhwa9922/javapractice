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
			System.out.println("�Ͻ� ������ �����ֽʽÿ�.");
			System.out.println("1. �����ϱ� | 2. ��Ϻ��� | 3. ����");
			int choose = s.nextInt();
			switch (choose) {
			case 1:
				while (true) {
					System.out.println("=========================");
					System.out.println("���ڸ� ���纸�ʽÿ�. (1~100) ");
					System.out.println("=========================");
					int chonum = s.nextInt();
					System.out.println(rannum);
					turncnt++;
					if (chonum < 0 || chonum>100) {
						System.out.println("1~100������ ���ڸ� �Է��Ͽ� �ֽʽÿ�.");
						turncnt --;
					} else if (chonum > 0) {
						if (chonum == rannum) {
							System.out.println("�����Դϴ�.");
							record.add(turncnt);
							Collections.sort(record);
							turncnt = 0;
							break;
						} else if (chonum > rannum) {
							System.out.println("������ �ƴմϴ�.(down)");
						} else if (chonum < rannum) {
							System.out.println("������ �ƴմϴ�.(up)");
						}
					}
				}
				break;
			case 2:
				if (record.size() == 0) {
					System.out.println("����� �����ϴ�.");
				} else if (record.size()>0) {
					System.out.println("����� ����մϴ�.");
					for (int i = 0; i < record.size(); i++) {
						System.out.println((i + 1) + "�� �����" + record.get(i) + "�Դϴ�.");
					}
				}
				break;
			case 3:
				System.out.println("�ý����� �����մϴ�.");
				sw = false;
				break;
			default:
				System.out.println("1 , 2 , 3 �� �ϳ��� �Է��Ͽ� �ֽʽÿ�");
				break;
			}

		}

	}

}
