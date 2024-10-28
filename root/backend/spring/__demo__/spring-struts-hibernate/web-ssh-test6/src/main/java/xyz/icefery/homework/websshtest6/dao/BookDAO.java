package xyz.icefery.homework.websshtest6.dao;

import java.util.List;
import xyz.icefery.homework.websshtest6.entity.Book;

/**
 * @author icefery
 */
public interface BookDAO {
    List<Book> selectList();

    Book selectById(Integer id);

    void insert(Book book);

    void updateById(Book book);
}
