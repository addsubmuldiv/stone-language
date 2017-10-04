package stone.ast;

import java.util.List;
//空语句的结构，为了方便实现
public class NullStmnt extends ASTList{
    public NullStmnt(List<ASTree> list) {
        super(list);
    }
}
