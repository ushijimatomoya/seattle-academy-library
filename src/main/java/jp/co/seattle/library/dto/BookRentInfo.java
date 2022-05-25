package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookRentInfo {
	
	private int bookId;
	
	private String title;
	
	private String rentDate;
	
	private String returnDate;

	public BookRentInfo() {

    }

    public BookRentInfo(int bookId, String title, String rentDate, String returnDate) {
    	this.bookId = bookId;
        this.title = title;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }
}