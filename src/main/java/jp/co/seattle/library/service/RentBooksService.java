package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import jp.co.seattle.library.dto.BookRentInfo;
import jp.co.seattle.library.rowMapper.BookRentInfoRowMapper;


@Controller
public class RentBooksService {
	final static Logger logger = LoggerFactory.getLogger(RentBooksService.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 履歴リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookRentInfo> getRentBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookRentInfo> getedRentBookList = jdbcTemplate.query(
				"select book_id, title, rent_date, return_date  from rentalbooks left outer join books on rentalbooks.book_id  = books.id order by title",
				new BookRentInfoRowMapper());

		return getedRentBookList;
	}
	
	/**
	 * 書籍情報を登録する
	 * 
	 * @param bookId 書籍ID
	 * @param title タイトル
	 */
	public void regisRentBook(int bookId) {
			String sql = "INSERT INTO rentalbooks (book_id, rent_date) VALUES (?, now());";
		jdbcTemplate.update(sql, bookId);
	}
	
	
	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookId 書籍ID
	 */
	public void updateRentBook(int bookId) {
		String sql = "update rentalbooks set rent_date = now(), return_date = null where book_id = ?;";
		jdbcTemplate.update(sql, bookId);
	}
	

	/**
	 * 書籍IDに紐づく情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */	
	public BookRentInfo rentBookInfo(int bookId) {
		try {
			String sql = "select book_id, title, rent_date, return_date  from rentalbooks left outer join books on rentalbooks.book_id  = books.id where book_id = ?;";		
			
			BookRentInfo rentInfo = jdbcTemplate.queryForObject(sql, new BookRentInfoRowMapper(), bookId);
			return rentInfo;

		} catch (Exception e) {
			return null;
		
		}
	}
	/**
	 * 書籍IDに紐づく情報を更新する
	 *
	 * @param bookId 書籍ID
	 */
	public void returnRentBook(int bookId) {
		String sql = "update rentalbooks set rent_date = null, return_date = now() where book_id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

}
