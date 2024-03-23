public class Test {
    public static void main(String[] args) {
        EverythingAPI
            .query("java.exe", false, false, false)
            .forEach(System.out::println);
    }
}

