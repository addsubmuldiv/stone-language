package stone.ast;

import java.util.List;
//if的树结构，0,1,2三个子节点分别表示条件，条件为真时的语句，条件为假的时候的语句
public class IfStmnt extends ASTList {
    public IfStmnt(List<ASTree> list) {
        super(list);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }
    //else可以省略
    public ASTree elseBlock() {
        return numChildren()>2? child(2) : null;
    }

    public String toString() {
        return "(if "+condition()+" "+thenBlock()
                +" else "+elseBlock()+")";
    }
}
