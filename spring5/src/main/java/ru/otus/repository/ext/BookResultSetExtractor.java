package ru.otus.repository.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            Book book = new Book(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("year_issue"),
                    rs.getInt("number_pages"),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

            books.put(book.getId(), book);
        }

        return books;
    }
}