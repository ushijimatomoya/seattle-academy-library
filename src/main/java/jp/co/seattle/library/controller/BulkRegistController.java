package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
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

/**
 * 編集コントローラー
 */
@Controller
public class BulkRegistController {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);

	@Autowired
	private BooksService booksService;

//	    @Autowired
//	    private ThumbnailService thumbnailService;

	/**
	 * 編集画面に遷移する
	 * 
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bulkregistBook", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String login(Model model) {
		return "bulkRegist";
	}

	/**
	 * 書籍情報を登録する
	 * 
	 * @param locale    ロケール情報
	 * @param title     書籍名
	 * @param author    著者名
	 * @param publisher 出版社
	 * @param file      サムネイルファイル
	 * @param model     モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/bulkregistinsertBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String insertBook(Locale locale, @RequestParam("upload_file") MultipartFile uploadFile,
			Model model) {
		logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

		// パラメータで受け取った書籍情報をDtoに格納する。
		BookDetailsInfo bookInfo = new BookDetailsInfo();

		List<String[]> booksList = new ArrayList<String[]>();
		List<String> errorbookList = new ArrayList<String>();
		String line = null;
		boolean bline = true;
		int count = 0;
		try {
			InputStream stream = uploadFile.getInputStream();
			Reader reader = new InputStreamReader(stream);
			BufferedReader buf = new BufferedReader(reader);

			if (bline = buf.ready() == false) {
				model.addAttribute("error", "csvに書籍情報がありません。");
				return "bulkRegist";
				
			}

			while ((line = buf.readLine()) != null) {
				count++;
				System.out.println(count);
				final String[] split = line.split(",", -1);
				for (int i = 0; i < split.length; i++) {
					System.out.println(split[i]);
				}
				String error = booksService.validation(split[0], split[1], split[2], split[3], split[4]);
				booksList.add(split);

				if (!error.equals("")) {
					errorbookList.add(count + "行目の書籍登録でエラーが起きました。<br>");
				}

			}

		} catch (IOException e) {
			
			throw new RuntimeException("ファイルが読み込めません", e);
			
		}
		
		if (errorbookList.size() > 0) {
			model.addAttribute("error", errorbookList);
			return "bulkRegist";
		}
		
		// 書籍情報を新規登録する
		for (int i = 0; i < booksList.size(); i++) {
			String[] bookList = booksList.get(i);
			bookInfo.setTitle(bookList[0]);
			bookInfo.setAuthor(bookList[1]);
			bookInfo.setPublisher(bookList[2]);
			bookInfo.setPublishDate(bookList[3]);
			bookInfo.setIsbn(bookList[4]);
			bookInfo.setTexts(bookList[5]);
			System.out.println(i);
			booksService.registBook(bookInfo);

		}

		// home画面に遷移する
		model.addAttribute("bookList", booksService.getBookList());
		return "home";

	}

}