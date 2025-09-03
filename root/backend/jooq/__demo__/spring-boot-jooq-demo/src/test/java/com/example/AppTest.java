package com.example;

import cn.hutool.core.io.IoUtil;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.maven.example.Tables;
import org.jooq.codegen.maven.example.tables.Book;
import org.jooq.impl.DSL;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    private static final String INIT_SQL = IoUtil.read(AppTest.class.getResourceAsStream("/sql/init.sql"), StandardCharsets.UTF_8);

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DSLContext context;

    @BeforeAll
    public void beforeAll() throws Exception {
        // init test requirement
        context.dropSchemaIfExists("test").cascade().execute();
        context.createSchema("test").execute();
        context.setSchema("test").execute();
        context.execute(INIT_SQL);

        // generate test code
        Configuration configuration = new Configuration()
            .withJdbc(
                new Jdbc()
                    .withDriver(dataSourceProperties.getDriverClassName())
                    .withUrl(dataSourceProperties.getUrl())
                    .withUsername(dataSourceProperties.getUsername())
                    .withPassword(dataSourceProperties.getPassword())
            )
            .withGenerator(
                new Generator()
                    .withDatabase(
                        new Database().withName("org.jooq.meta.postgres.PostgresDatabase").withIncludes(".*").withExcludes("").withInputSchema("test")
                    )
                    .withTarget(new Target().withPackageName("org.jooq.codegen.maven.example").withDirectory("src/test/java"))
            );
        GenerationTool.generate(configuration);
    }

    @Test
    public void test1() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.POSTGRES);
            ctx.meta().getCatalogs().forEach(System.out::println);
            ctx.meta().getSchemas().forEach(System.out::println);
            ctx.meta().getTables().forEach(System.out::println);
        }
    }

    @Test
    public void test2() {
        context.select().from("language").fetch().forEach(System.out::println);

        context
            .select(Tables.AUTHOR.FIRST_NAME, Tables.AUTHOR.LAST_NAME, Book.BOOK.ID, Book.BOOK.TITLE)
            .from(Tables.AUTHOR)
            .join(Tables.BOOK)
            .on(Tables.AUTHOR.ID.eq(Tables.BOOK.AUTHOR_ID))
            .orderBy(Tables.BOOK.ID.asc())
            .fetch()
            .forEach(System.out::println);
    }
}
