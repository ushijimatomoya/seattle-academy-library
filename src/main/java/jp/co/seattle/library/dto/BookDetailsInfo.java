package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookDetailsInfo {

    private int bookId;

    private String title;

    private String author;

    private String publisher;

    private String thumbnailUrl;

    private String thumbnailName;
    
    private String publishDate;
    
    private String texts;
    
    private String isbn;
    
    private int rentBookId;

    public BookDetailsInfo() {

    }

    public BookDetailsInfo(int bookId, String title, String author, String publisher,
            String thumbnailUrl, String thumbnailName, String publishDate, String texts, String isbn, int rentBookId) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailName = thumbnailName;
        this.publishDate = publishDate;
        this.texts = texts;
        this.isbn = isbn;
        this.rentBookId = rentBookId;
    }

}