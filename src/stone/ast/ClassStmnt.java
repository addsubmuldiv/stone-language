package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:19 2017/10/13
 * @Modified_by:
 */
public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> list) {
        super(list);
    }
    public String name() {
        return ((ASTLeaf)child(0)).token().getText();
    }
    public String superClass() {
        if(numChildren() < 3) {
            return null;
        } else {
            return ((ASTLeaf)child(1)).token().getText();
        }
    }
    public ClassBody body() {
        return (ClassBody)child(numChildren()-1);
    }
    @Override
    public String toString() {
        String parent=superClass();
        if(parent==null)
            parent="*";
        return "(class "+name()+" "+parent+" "+body()+")";
    }
}
