package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:29 2017/10/13
 * @Modified_by:
 */
public class Dot extends Postfix {
    public Dot(List<ASTree> list) {
        super(list);
    }
    public String name() {
        return ((ASTLeaf)child(0)).token().getText();
    }
    public String toString() {
        return "."+name();
    }
}
