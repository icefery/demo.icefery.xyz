package xyz.icefery.homework.websshtest6.service;

import xyz.icefery.homework.websshtest6.entity.Book;
import java.util.List;

/**
 * @author icefery
 */
public interface BookService {
    List<Book> queryBookList();

    Book queryBookById(Integer id);

    void saveBook(Book book);

    void modifyBookById(Book book);
}
