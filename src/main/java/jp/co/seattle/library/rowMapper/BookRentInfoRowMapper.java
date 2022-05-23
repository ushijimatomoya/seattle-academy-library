package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.BookRentInfo;

@Configuration
public class BookRentInfoRowMapper implements RowMapper<BookRentInfo> {

	@Override
	public BookRentInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Query結果（ResultSet rs）を、オブジェクトに格納する実装
		BookRentInfo bookRentInfo = new BookRentInfo();

		// bookInfoの項目と、取得した結果(rs)のカラムをマッピングする
		bookRentInfo.setBookId(rs.getInt("book_id"));
		bookRentInfo.setTitle(rs.getString("title"));
		bookRentInfo.setRentDate(rs.getString("rent_date"));
		bookRentInfo.setReturnDate(rs.getString("return_date"));
		
		return bookRentInfo;
	}


}