package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class RentBooksService {
	final static Logger logger = LoggerFactory.getLogger(RentBooksService.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 書籍IDを登録する
	 * 
	 * @param bookId 書籍ID
	 */
	public void regisRentBook(int bookId) {

		String sql = "INSERT INTO rentalbooks (book_id) VALUES (?);";

		jdbcTemplate.update(sql, bookId);
	}
	
	/**
	 * 書籍IDを削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteRentBook(Integer bookId) {
		
		String sql = "delete from rentalbooks where book_id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍IDに紐づく情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public int rentBookinfo(int bookId) {

		String sql = "SELECT count(*) FROM rentalbooks where book_id = ?;";
		
		int count = jdbcTemplate.queryForObject(sql, int.class, bookId);
		System.out.println(sql);
		return count;
		

	}

}
