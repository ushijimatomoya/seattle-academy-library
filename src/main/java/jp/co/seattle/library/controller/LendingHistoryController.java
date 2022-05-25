package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBooksService;

/**
 * 履歴コントローラー
 */
@Controller
public class LendingHistoryController {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);

	@Autowired
	private RentBooksService rentBooksService;

	/**
	 * 履歴画面に遷移する
	 * 
	 * @param bookId
	 * @param model
	 * @return　履歴一覧画面に遷移
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String login(Model model) {
		model.addAttribute("bookHistoryList", rentBooksService.getRentBookList());
		return "lendingHistory";
	}
}
