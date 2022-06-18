package model;
//모델
/* 모델 클래스들은 데이터의 틀을 담당하는 클래스이다.

 * 모델 클래스 안에는 어떤 데이터가 저장이 될지 (=필드)
 * 겟터/셋터,
 * 그리고 java.lang.Object의 오버라이드할 메소드만 들어가게 된다.*/

// DTO VS VO
/* Data Transfer Object 데이터 전송 객체 -> 데이터베이스로부터 받은 데이터를 
 * 객체에 담아서 사용자에게 보내줄때 쓰는 틀+ 사용자가 입력한 데이터를 
 * 객체에 담아서 데이터베이스로 보내줄때 쓰는 틀
 * Value Object: 값 객체 - > 데이터베이스로부터 받은 데이터를 객체에 담아서
 * 사용자에게 보내줄때 쓰는 틀*/

public class BoardDTO {
	//필드 
	private int id;
	private String title;
	private String content;
	private String writer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	
	public BoardDTO() {
        title = new String();
        content = new String();
        writer = new String();
    }

    public BoardDTO(int id) {
        this.id = id;
        title = new String();
        content = new String();
        writer = new String();
    }

    public BoardDTO(BoardDTO origin) {
        id = origin.id;
        title = origin.title;
        writer = origin.writer;
        content = origin.content;
    }

    public boolean equals(Object o) {
        if (o instanceof BoardDTO) {
            BoardDTO b = (BoardDTO) o;
            return id == b.id;
        }

        return false;
    }
	
	
	
}
