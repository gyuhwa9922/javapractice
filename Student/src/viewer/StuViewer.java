package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.StuController;
import model.StuDTO;
import util.ScannerUtil;

public class StuViewer {
	// 전역변수
	private Scanner sc;
	private StuController stcon;

	public StuViewer() {
		stcon = new StuController();
		sc = new Scanner(System.in);
	}

	public void showMenu() {
		while (true) {
			String message = "1.학생 성적 작성 2.학생 성적 보기 3.종료";
			int userch = ScannerUtil.nextInt(sc, message);
			if (userch == 1) {
				// 학생 등록 메소드
				regiStu();
			} else if (userch == 2) {
				// 학생 목록 출력 메소드 호출
				printList();
			} else if (userch == 3) {
				System.out.println("종료");
				break;
			}
		}

	}

	private void printOne(int id) {
		StuDTO s = stcon.selectOne(id);

		System.out.println("========" + s.getSt_id() + "번 학생 : " + s.getName() + "========");
		System.out.println("영어 : " + s.getEn() + "점 | 국어: " + s.getKo()+"점 | 수학: " + s.getMa()+"점");
		System.out.println("총합: " + s.sum());
		System.out.println("평균: " + s.avg());
		System.out.println("===========================");

		String ms = "1. 수정 2.삭제 3.뒤로가기";
		int usch = ScannerUtil.nextInt(sc, ms);

		if (usch == 1) {
			// n번째 값을 업데이트(수정)하는 메소드
			updateStu(id);
		} else if (usch == 2) {
			// n번째 값을 delete(삭제) 하는 메소드
			deleteStu(id);
		} else {
			printList();
		}

	}

	private void updateStu(int st_id) {
		// TODO Auto-generated method stub
		StuDTO d = stcon.selectOne(st_id);

		d.setName(ScannerUtil.nextLine(sc, "변경할 이름 작성"));
		d.setEn(ScannerUtil.nextInt(sc, "변경할 영어 점수를 입력하세요"));
		d.setKo(ScannerUtil.nextInt(sc, "변경할 국어 점수를 입력하세요"));
		d.setMa(ScannerUtil.nextInt(sc, "변경할 수학 점수를 입력하세요"));

		stcon.update(d);
//		printOne(st_id);

	}

	private void deleteStu(int id) {
		// TODO Auto-generated method stub
		String yN = ScannerUtil.nextLine(sc, "정말로 학생 정보를 삭제하시겠습니까?(Y/N)");

		if (yN.equalsIgnoreCase("Y")) {
			stcon.delete(id);
			printList();
		} else {
			printOne(id);
		}

	}

	private void regiStu() {
		StuDTO b = new StuDTO();

		String ms = "학생이름을 등록해주십시오.";
		b.setName(ScannerUtil.nextLine(sc, ms));
		ms = "학생의 영어 점수를 입력해주십시오.";
		b.setEn(ScannerUtil.nextInt(sc, ms));
		ms = "학생의 국어 점수를 입력해주십시오.";
		b.setKo(ScannerUtil.nextInt(sc, ms));
		ms = "학생의 수학 점수를 입력해주십시오.";
		b.setMa(ScannerUtil.nextInt(sc, ms));

		stcon.insert(b);
	}

	private void printList() {
		ArrayList<StuDTO> list = stcon.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("아직 등록된 학생이 없습니다.");
		}else {
			for(StuDTO d : list) {
				System.out.printf("%d번째 학생 : %s\n",d.getSt_id(),d.getName());
			}
			String ms = "학생의 점수를 보고 싶으면 학생의 번호를 눌러주시고 뒤로가실려면 0을 눌러주십시오.";
			int usch = ScannerUtil.nextInt(sc, ms);
			
			while(usch != 0 && stcon.selectOne(usch)==null) {
				System.out.println("잘못입력했또르~");
				for(StuDTO d : list) {
					System.out.printf("%d번째 학생 : %s\n",d.getSt_id(),d.getName());
				}
				usch = ScannerUtil.nextInt(sc, ms);
			}
			if(usch != 0) {
				printOne(usch);
			}
		}
	}

}
