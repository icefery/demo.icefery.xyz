import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.icefery.demo.App;
import xyz.icefery.demo.message.MyMessage;
import xyz.icefery.demo.producer.MyProducer;

@SpringBootTest(classes = { App.class })
class AppTest {

    @Autowired
    MyProducer producer;

    @Test
    void test1() {
        String raw = "{\"data\": \"This is a raw message\"}";
        producer.sendRaw(raw);
    }

    @Test
    void test2() {
        for (int i = 1; i <= 101; i++) {
            MyMessage object = new MyMessage("This is object message-" + i);
            producer.sendObject(object);
        }
    }

    @Test
    void test3() {
        for (int i = 1; i < 101; i++) {
            String raw = String.format("{\"data\": \"%s\"}", "This is the " + i + "-th message");
            producer.batchingSendRaw(raw);
        }
    }
}
