package stone.ast;

import java.util.List;

/**
 * 基本构成元素的树结构，使用create方法避开了
 * 当只要rule()带有参数时就会省略一层子节点的设定,使得当他不只一个子节点的时候，不会省略
 */
public class PrimaryExpr extends ASTList{
    public PrimaryExpr(List<ASTree> list) {
        super(list);
    }

    public static ASTree create(List<ASTree> c) {
        return c.size() == 1? c.get(0) : new PrimaryExpr(c);
    }
}
