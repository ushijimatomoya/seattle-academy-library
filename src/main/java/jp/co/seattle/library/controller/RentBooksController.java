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
 * レンタルコントローラー
 */
@Controller /** APIの入り口 */
public class RentBooksController {
	final static Logger logger = LoggerFactory.getLogger(RentBooksController.class);

	@Autowired
	private RentBooksService rentBooksService;
	@Autowired
	private BooksService booksService;

	@Transactional
	@RequestMapping(value = "/rentBooks", method = RequestMethod.POST)
	public String rentBook(Locale locale, 
			@RequestParam("bookId") int bookId, 
			Model model) {
		
		BookRentInfo rentInfo = rentBooksService.rentBookInfo(bookId);

		if (rentInfo == null) {
			rentBooksService.regisRentBook(bookId);
		} else {
			if (rentInfo.getRentDate() == null) {
				rentBooksService.updateRentBook(bookId);
			} else {
				model.addAttribute("error", "貸出済みです。");
			}
		}
		

		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		return "details";
	}

}
