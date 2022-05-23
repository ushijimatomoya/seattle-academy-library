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

import jp.co.seattle.library.dto.BookRentInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBooksService;

/**
 * リターンコントローラー
 */
@Controller /** APIの入り口 */
public class ReturnBooksController {
	final static Logger logger = LoggerFactory.getLogger(ReturnBooksController.class);

	@Autowired
	private RentBooksService rentBooksService;
	@Autowired
	private BooksService booksService;

	/**
	 * 返却処理
	 *
	 * @param bookId  
	 * @param model 
	 * @return 詳細画面に遷移
	 */
	@Transactional
	@RequestMapping(value = "/returnRentBooks", method = RequestMethod.POST)
	public String rentBook(Locale locale, 
			@RequestParam("bookId") int bookId, 
			Model model) {
         
 		BookRentInfo rentInfo = rentBooksService.rentBookInfo(bookId);
			if (rentInfo == null || rentInfo.getReturnDate() != null) {
				model.addAttribute("error", "貸し出されていません。");
			} else {
	 				rentBooksService.returnRentBook(bookId);
			}
 			

         model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		
		return "details";

	}

}
