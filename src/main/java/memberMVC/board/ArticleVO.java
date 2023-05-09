package memberMVC.board;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	private int level; // 글의 깊이를 저장하는 필드(게시판의 글과 댓글 구분하게 계층형으로 쌓아올리는 용도?)
	private int articleNo; // 글번호 컬럼
	private int parentNo; // 답글번호
	private String title; // 제목
	private String content; // 내용
	private String imageFileName; // 이미지파일이름
	private Date writeDate; // 글생성날짜
	private String id; // 아이디

	public ArticleVO() {
		System.out.println("ArticleVO()");
	}

	public ArticleVO(int level, int articleNo, int parentNo, String title, String content, String imageFileName,
			String id) {
		super();
		this.level = level;
		this.articleNo = articleNo;
		this.parentNo = parentNo;
		this.title = title;
		this.content = content;
		this.imageFileName = imageFileName;
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public int getParentNo() {
		return parentNo;
	}

	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
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

	public String getImageFileName() {
		try { // 이미지 파일이름이 한글로 썻을수도있어서 깨질수있음
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLDecoder.decode(imageFileName, "utf-8"); // 이미지 파일이름을 utf-8로 디코딩
			} else {
				imageFileName = null; // 아닐시(이미지파일이 없을시) null값으로 저장
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("이미지 로딩중 에러!!");
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLEncoder.encode(imageFileName, "utf-8");
			} else {
				imageFileName = null;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("이미지 저장 중 에러!!");
		}
		this.imageFileName = imageFileName;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
