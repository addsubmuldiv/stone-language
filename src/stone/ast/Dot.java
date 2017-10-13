package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition: 看起来像是，.和点后面的标识符，也就是说，name()方法返回的是“.”后面的那个标识符,which is 保存在
 * 第一个子节点
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
