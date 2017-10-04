package stone.ast;

import java.util.List;
//while循环体的树结构
public class WhileStmnt extends ASTList {
    public WhileStmnt(List<ASTree> list) {
        super(list);
    }
    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }

    public String toString() {
        return "(while "+ condition() + " " +body()+")";
    }
}
