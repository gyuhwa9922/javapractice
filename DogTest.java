package Practice2;

public class DogTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dog[] s = new Dog[5];
		s[0] = new Dog("�߻�", "��Ƽ��");
		s[1] = new Dog("�ǻ�", "��Ƽ��");
		s[2] = new Dog("�ѻ�", "��Ƽ��");
		s[3] = new Dog("����", "��Ƽ��");
		s[4] = new Dog("����", "��Ƽ��");
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i].showDogInfo());
		}
		for (Dog lang : s) {
			System.out.println(lang.showDogInfo());
		}
		//���� for�� Dog lang : s ��� �����ִ°��� Dog Ÿ�Կ� s�ȿ� �ִ� �迭���� ���� lang�� ���ԵǾ ��µǴ°�
	}

}
