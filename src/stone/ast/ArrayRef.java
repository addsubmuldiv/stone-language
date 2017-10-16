package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:26 2017/10/16
 * @Modified_by:
 */
public class ArrayRef extends Postfix {
    public ArrayRef(List<ASTree> list) {
        super(list);
    }
    public ASTree index() {
        return child(0);
    }
    public String toString() {
        return "["+index()+"]";
    }
}
