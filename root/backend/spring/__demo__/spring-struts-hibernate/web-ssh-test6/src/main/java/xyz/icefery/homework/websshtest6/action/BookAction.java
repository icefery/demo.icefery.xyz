package xyz.icefery.homework.websshtest6.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import xyz.icefery.homework.websshtest6.entity.Book;
import xyz.icefery.homework.websshtest6.service.BookService;

/**
 * @author icefery
 */
@Controller
@Scope(value = "prototype")
@Namespace(value = "/book")
@ParentPackage(value = "struts-default")
@Results(
    value = {
        @Result(name = "forward:page", location = "/WEB-INF/content/page.jsp", type = "dispatcher"),
        @Result(name = "redirect:list", location = "/book/list", type = "redirect")
    }
)
public class BookAction extends ActionSupport {

    // =================================================================================================================
    // Service
    // =================================================================================================================
    @Autowired
    private BookService bookService;

    // =================================================================================================================
    // Table data
    // =================================================================================================================
    @Getter
    @Setter
    private List<Book> bookList;

    // =================================================================================================================
    // Form data
    // =================================================================================================================
    @Getter
    @Setter
    private Book bookToQuery;

    @Getter
    @Setter
    private Book bookToSave;

    @Getter
    @Setter
    private Book bookToModify;

    // =================================================================================================================
    // Action
    // =================================================================================================================
    @Action(value = "list")
    public String listBook() {
        bookList = bookService.queryBookList();
        return "forward:page";
    }

    @Action(value = "query-by-id")
    public String queryBookById() {
        if (bookToQuery == null || bookToQuery.getId() == null) {
            bookList = bookService.queryBookList();
        } else {
            bookList = Collections.singletonList(bookService.queryBookById(bookToQuery.getId()));
        }
        bookToQuery = null;
        return "forward:page";
    }

    @Action(value = "save")
    public String saveBook() {
        if (bookToSave != null) {
            bookService.saveBook(bookToSave);
        }
        return "redirect:list";
    }

    @Action(value = "modify-by-id")
    public String modifyBookById() {
        if (bookToModify != null && bookToModify.getId() != null) {
            bookService.modifyBookById(bookToModify);
        }
        return "redirect:list";
    }
}
