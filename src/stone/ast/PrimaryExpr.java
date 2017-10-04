package stone.ast;

import java.util.List;

/**
 * 基本构成元素的树结构，使用create方法避开了
 * 当rule()带有参数时会省略一层子节点的设定
 */
public class PrimaryExpr extends ASTList{
    public PrimaryExpr(List<ASTree> list) {
        super(list);
    }

    public static ASTree create(List<ASTree> c) {
        return c.size() == 1? c.get(0) : new PrimaryExpr(c);
    }
}
