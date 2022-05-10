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

	@Transactional
	@RequestMapping(value = "/returnRentBooks", method = RequestMethod.POST)
	public String rentBook(Locale locale, @RequestParam("bookId") int bookId, Model model) {

		int rentBookinfo = rentBooksService.rentBookinfo(bookId);

		if (rentBookinfo == 0) {
			model.addAttribute("error", "貸し出されていません。");
			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		} else {
			rentBooksService.deleteRentBook(bookId);
			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		}
		return "details";

	}

}
