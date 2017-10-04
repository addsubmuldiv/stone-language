package stone.ast;

import java.util.List;

/**
 * ASTList继承自ASTree，ASTree实现了Iterable接口，实现了这个接口或者是数组，就可以进行
 * 基于范围的for循环进行遍历,所以在BasicEvaluator里面就是直接进行了for-each循环遍历
 */
public class BlockStmnt extends ASTList{
    public BlockStmnt(List<ASTree> list) {
        super(list);
    }
}
