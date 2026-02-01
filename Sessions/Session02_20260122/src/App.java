public class App {
    public static void main(String[] args) throws Exception {
        IOHandler console = new IOConsole();
        console.showInfo("Welcome to the IO Calculator!");
        int option = console.getInt("1) Square", "Invalid input. Please try again.");
    }
}
