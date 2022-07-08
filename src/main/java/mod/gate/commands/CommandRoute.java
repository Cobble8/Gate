package mod.gate.commands;

public class CommandRoute {
    public String path;
    public Func function;

    public CommandRoute(String path, Func function) {
        this.path = path;
        this.function = function;
    }

    public interface Func {
        void call();
    }
}
