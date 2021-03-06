package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:18 2017/10/16
 * @Modified_by:
 */
public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<ASTree> list) {
        super(list);
    }

    /**
     * 获取数组的大小
     * @return
     */
    public int size() {
        return numChildren();
    }
}
