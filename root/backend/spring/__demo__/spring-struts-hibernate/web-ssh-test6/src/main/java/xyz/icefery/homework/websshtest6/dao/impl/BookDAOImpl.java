package xyz.icefery.homework.websshtest6.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import xyz.icefery.homework.websshtest6.dao.BookDAO;
import xyz.icefery.homework.websshtest6.entity.Book;

/**
 * @author icefery
 */
@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Book> selectList() {
        return hibernateTemplate.loadAll(Book.class);
    }

    @Override
    public Book selectById(Integer id) {
        return hibernateTemplate.get(Book.class, id);
    }

    @Override
    public void insert(Book book) {
        hibernateTemplate.save(book);
    }

    @Override
    public void updateById(Book book) {
        hibernateTemplate.update(book);
    }
}
