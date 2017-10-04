package stone.ast;

import java.util.List;
// 二元运算表达式的树结构，left(child(0))是第一个操作数，operator(child(1))是操作符号，
// right(child(2))是另一个操作数
public class BinaryExpr extends ASTList {
    public BinaryExpr(List<ASTree> list) {
        super(list);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf)child(1)).token.getText();
    }

    public ASTree right() { return child(2); }
}
