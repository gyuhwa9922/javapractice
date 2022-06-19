package model;

public class StuDTO {
	private String name;
	private int st_id;
	private int ko;
	private int en;
	private int ma;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSt_id() {
		return st_id;
	}

	public void setSt_id(int st_id) {
		this.st_id = st_id;
	}

	public int getKo() {
		return ko;
	}

	public void setKo(int ko) {
		this.ko = ko;
	}

	public int getEn() {
		return en;
	}

	public void setEn(int en) {
		this.en = en;
	}

	public int getMa() {
		return ma;
	}

	public void setMa(int ma) {
		this.ma = ma;
	}
	public StuDTO() {
		//기본 생성자
	}
	public StuDTO(int st_id){
		this.st_id = st_id;
		name = new String();
	}
	//깊은 복사
	public StuDTO(StuDTO d) {
		this.st_id = d.st_id;
		this.name = d.name;
		this.en = d.en;
		this.ko = d.ko;
		this.ma = d.ma;
	}
	
	public boolean equals(Object o) {
		if (o instanceof StuDTO) {
			StuDTO b = (StuDTO) o;
			return st_id == b.st_id;
		}
		return false;
	}
	public int sum() {
		return ko + en + ma;
	}
	public double avg() {
		return (double) (sum() / 3.0);
	}
}
