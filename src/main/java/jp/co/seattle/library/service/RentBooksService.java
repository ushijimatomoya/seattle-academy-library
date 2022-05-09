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
	 * 情報を登録する
	 * 
	 * @param 
	 */
	public void regisRentBook(int bookId) {

		String sql = "INSERT INTO rentalbooks (book_id) VALUES (?);";

		jdbcTemplate.update(sql, bookId);
	}

	public int rentBookinfo(int bookId) {

		String sql = "SELECT count(*) FROM rentalbooks where book_id = ?;";
		
		int count = jdbcTemplate.queryForObject(sql, int.class, bookId);
		System.out.println(sql);
		return count;
		

	}

}
