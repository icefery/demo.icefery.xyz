/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.maven.example;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.codegen.maven.example.tables.Author;
import org.jooq.codegen.maven.example.tables.Book;
import org.jooq.codegen.maven.example.tables.BookStore;
import org.jooq.codegen.maven.example.tables.BookToBookStore;
import org.jooq.codegen.maven.example.tables.Language;
import org.jooq.codegen.maven.example.tables.records.AuthorRecord;
import org.jooq.codegen.maven.example.tables.records.BookRecord;
import org.jooq.codegen.maven.example.tables.records.BookStoreRecord;
import org.jooq.codegen.maven.example.tables.records.BookToBookStoreRecord;
import org.jooq.codegen.maven.example.tables.records.LanguageRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in
 * test.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AuthorRecord> AUTHOR_PKEY = Internal.createUniqueKey(
        Author.AUTHOR,
        DSL.name("author_pkey"),
        new TableField[] { Author.AUTHOR.ID },
        true
    );
    public static final UniqueKey<BookRecord> BOOK_PKEY = Internal.createUniqueKey(Book.BOOK, DSL.name("book_pkey"), new TableField[] { Book.BOOK.ID }, true);
    public static final UniqueKey<BookStoreRecord> BOOK_STORE_NAME_KEY = Internal.createUniqueKey(
        BookStore.BOOK_STORE,
        DSL.name("book_store_name_key"),
        new TableField[] { BookStore.BOOK_STORE.NAME },
        true
    );
    public static final UniqueKey<BookToBookStoreRecord> BOOK_TO_BOOK_STORE_PKEY = Internal.createUniqueKey(
        BookToBookStore.BOOK_TO_BOOK_STORE,
        DSL.name("book_to_book_store_pkey"),
        new TableField[] { BookToBookStore.BOOK_TO_BOOK_STORE.NAME, BookToBookStore.BOOK_TO_BOOK_STORE.BOOK_ID },
        true
    );
    public static final UniqueKey<LanguageRecord> LANGUAGE_PKEY = Internal.createUniqueKey(
        Language.LANGUAGE,
        DSL.name("language_pkey"),
        new TableField[] { Language.LANGUAGE.ID },
        true
    );

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<BookRecord, AuthorRecord> BOOK__FK_BOOK_AUTHOR = Internal.createForeignKey(
        Book.BOOK,
        DSL.name("fk_book_author"),
        new TableField[] { Book.BOOK.AUTHOR_ID },
        Keys.AUTHOR_PKEY,
        new TableField[] { Author.AUTHOR.ID },
        true
    );
    public static final ForeignKey<BookRecord, LanguageRecord> BOOK__FK_BOOK_LANGUAGE = Internal.createForeignKey(
        Book.BOOK,
        DSL.name("fk_book_language"),
        new TableField[] { Book.BOOK.LANGUAGE_ID },
        Keys.LANGUAGE_PKEY,
        new TableField[] { Language.LANGUAGE.ID },
        true
    );
    public static final ForeignKey<BookToBookStoreRecord, BookRecord> BOOK_TO_BOOK_STORE__FK_B2BS_BOOK = Internal.createForeignKey(
        BookToBookStore.BOOK_TO_BOOK_STORE,
        DSL.name("fk_b2bs_book"),
        new TableField[] { BookToBookStore.BOOK_TO_BOOK_STORE.BOOK_ID },
        Keys.BOOK_PKEY,
        new TableField[] { Book.BOOK.ID },
        true
    );
    public static final ForeignKey<BookToBookStoreRecord, BookStoreRecord> BOOK_TO_BOOK_STORE__FK_B2BS_BOOK_STORE = Internal.createForeignKey(
        BookToBookStore.BOOK_TO_BOOK_STORE,
        DSL.name("fk_b2bs_book_store"),
        new TableField[] { BookToBookStore.BOOK_TO_BOOK_STORE.NAME },
        Keys.BOOK_STORE_NAME_KEY,
        new TableField[] { BookStore.BOOK_STORE.NAME },
        true
    );
}
