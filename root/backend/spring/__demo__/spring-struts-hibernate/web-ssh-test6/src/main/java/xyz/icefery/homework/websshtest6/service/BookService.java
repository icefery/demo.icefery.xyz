package xyz.icefery.homework.websshtest6.service;

import java.util.List;
import xyz.icefery.homework.websshtest6.entity.Book;

/**
 * @author icefery
 */
public interface BookService {
    List<Book> queryBookList();

    Book queryBookById(Integer id);

    void saveBook(Book book);

    void modifyBookById(Book book);
}
