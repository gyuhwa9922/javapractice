package Practice2;

public class DogTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dog[] s = new Dog[5];
		s[0] = new Dog("삐삐", "말티자");
		s[1] = new Dog("뽀뽀", "말티즈");
		s[2] = new Dog("뿌뿌", "마티즈");
		s[3] = new Dog("뻐뻐", "말티지");
		s[4] = new Dog("빠빠", "미티즈");
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i].showDogInfo());
		}
		for (Dog lang : s) {
			System.out.println(lang.showDogInfo());
		}
		//향상된 for문 Dog lang : s 라고 적혀있는곳은 Dog 타입에 s안에 있는 배열들을 변수 lang에 대입되어서 출력되는것
	}

}
