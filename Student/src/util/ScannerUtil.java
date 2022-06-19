package util;

// 스캐너를 사용할 시에 도움이 될 메소드를 정의한 클래스
import java.util.Scanner;

public class ScannerUtil {
    // 0. 입력 받을 내용을 출력하는 printMessage()
	// 리턴값없는 void 메서드 
    public static void printMessage(String message) {
        System.out.println("--------------------------");
        System.out.println(message);
        System.out.println("--------------------------");
        System.out.print("> ");
    }

    // 1. 스캐너 버그를 미리 해결한 nextLine()
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);//위에 선언한 메소드 message를 불러와 출력한다.
        String temp = scanner.nextLine();
        // String 변수/상수 혹은 값의 경우
        // isEmpty() 메소드를 실행시키면
        // 아무런 값도 없으면 true, 아무 글자가 하나라도 있으면 false가 리턴된다.
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        return temp;
    }

    // 2. int 를 처리하는 nextInt()
    //메소드 오버로딩
    public static int nextInt(Scanner scanner, String message) {
        printMessage(message); //위에 8번 메소드에 message안의 값을 넣어서 출력
        int temp = scanner.nextInt();//
        return temp;
    }

    // 3. 특정 범위안의 int를 리턴하는 nextInt()
    //메소드 오버로딩
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        
        while (temp < min || temp > max) {
            System.out.println("잘못 입력하셨습니다."); //출력후
            temp = nextInt(scanner, message);//30번 줄에 있는 메소드
        }

        return temp;
    }
}
