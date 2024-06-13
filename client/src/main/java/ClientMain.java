import ui.Repl;

public class ClientMain {
    public static void main(String[] args) {

        System.out.println("Welcome to the Chess Server:");
        Repl repl = new Repl();
        repl.runMenus();
    }
}