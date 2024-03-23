package xyz.icefery.homework.websshtest6.dao;

import xyz.icefery.homework.websshtest6.entity.Book;
import java.util.List;

/**
 * @author icefery
 */
public interface BookDAO {
    List<Book> selectList();

    Book selectById(Integer id);

    void insert(Book book);

    void updateById(Book book);
}
