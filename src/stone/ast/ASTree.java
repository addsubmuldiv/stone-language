package stone.ast;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree>{
    public abstract ASTree child(int i);
    public abstract int numChildren();
    public abstract Iterator<ASTree> children();
    public abstract String location();

    @NotNull
    @Override
    public Iterator<ASTree> iterator() {
        return children();
    }
}
