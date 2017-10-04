package stone.ast;

import java.util.List;
//负数的树结构
public class NegativeExpr extends ASTList{
    public NegativeExpr(List<ASTree> list) {
        super(list);
    }
    //省去多余的父节点
    public ASTree operand() {
        return child(0);
    }
    public String toString() {
        return "-"+operand();
    }
}
