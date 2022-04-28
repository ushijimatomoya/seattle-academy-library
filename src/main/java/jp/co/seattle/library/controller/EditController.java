package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * 編集コントローラー
 */
@Controller
public class EditController {
	 final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	
	    @Autowired
	    private BooksService booksService;
	    
	    @Autowired
	    private ThumbnailService thumbnailService;
	 
	     /**
	     * 編集画面に遷移する
	     * @param locale
	     * @param bookId
	     * @param model
	     * @return
	     */
	    @Transactional
	    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
	    public String editBook(Locale locale,
	            @RequestParam("bookId") Integer bookId,
	            Model model) {
	        // デバッグ用ログ
	        logger.info("Welcome editControler.java! The client locale is {}.", locale);       
	        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));      
	        return "editBook";
	    }

	    /**
	     * 書籍情報を更新する
	     * @param locale ロケール情報
	     * @param title 書籍名
	     * @param author 著者名
	     * @param publisher 出版社
	     * @param file サムネイルファイル
	     * @param model モデル
	     * @return 遷移先画面
	     */
	    @Transactional
	    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	    public String updateBook(Locale locale,
	            @RequestParam("title") String title,
	            @RequestParam("author") String author,
	            @RequestParam("publisher") String publisher,
	            @RequestParam("publish_date") String publishdate,
	            @RequestParam("texts") String texts,
	            @RequestParam("isbn") String isbn,
	            @RequestParam("bookId") int bookId,
	            @RequestParam("thumbnail") MultipartFile file,
	            Model model) {
	        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);
	
	        // パラメータで受け取った書籍情報をDtoに格納する。
	        BookDetailsInfo bookInfo = new BookDetailsInfo();
	        bookInfo.setTitle(title);
	        bookInfo.setAuthor(author);
	        bookInfo.setPublisher(publisher);
	        bookInfo.setPublishDate(publishdate);
	        bookInfo.setTexts(texts);
	        bookInfo.setIsbn(isbn);
	        bookInfo.setBookId(bookId);
	    
	        
	        String thumbnail = file.getOriginalFilename();

	        // クライアントのファイルシステムにある元のファイル名を設定する
	        if (!file.isEmpty()) {
	            try {
	                // サムネイル画像をアップロード
	                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
	                // URLを取得
	                String thumbnailUrl = thumbnailService.getURL(fileName);

	                bookInfo.setThumbnailName(fileName);
	                bookInfo.setThumbnailUrl(thumbnailUrl);

	            } catch (Exception e) {

	                // 異常終了時の処理
	                logger.error("サムネイルアップロードでエラー発生", e);
	                model.addAttribute("bookDetailsInfo", bookInfo);
	                return "editBook";
	            }
	        }
	        // 書籍情報を新規登録する
	         
	        String error = booksService.validation(title, author, publisher, publishdate, isbn);
	        
	        if (!error.equals("")) {
	            model.addAttribute("error", error);
	            bookInfo.setThumbnailName("null");
                bookInfo.setThumbnailUrl("null");
	            model.addAttribute("bookDetailsInfo", bookInfo);
	           
	            return "editBook";
	            
	        } 
	        
			booksService.editBook(bookInfo);
			BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId);
	        // TODO 更新した書籍の詳細情報を表示するように実装
	        model.addAttribute("bookDetailsInfo", bookDetailsInfo);
	        //  詳細画面に遷移する
	        return "details";
	    }
}
