## Crawler

## Selenium

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.23.1</version>
</dependency>
```

```scala
import org.openqa.selenium.chrome.ChromeDriver

object Test {
  def main(args: Array[String]): Unit = {
    System.setProperty("webdriver.chrome.driver", "chromedriver-mac-arm64/chromedriver")
    val driver = new ChromeDriver()
    driver.get("https://baidu.com")
    driver.close()
  }
}
```
