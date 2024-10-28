package xyz.icefery.homework.websshtest6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.homework.websshtest6.dao.BookDAO;
import xyz.icefery.homework.websshtest6.entity.Book;
import xyz.icefery.homework.websshtest6.service.BookService;

/**
 * @author icefery
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<Book> queryBookList() {
        return bookDAO.selectList();
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDAO.selectById(id);
    }

    @Override
    @Transactional
    public void saveBook(Book book) {
        bookDAO.insert(book);
    }

    @Override
    @Transactional
    public void modifyBookById(Book book) {
        // TODO: 暂不进行存在性判断
        bookDAO.updateById(book);
    }
}
