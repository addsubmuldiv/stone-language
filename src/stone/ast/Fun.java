package stone.ast;

import java.util.List;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 23:59 2017/10/7
 * @Modified_by:
 */
public class Fun extends ASTList {
    public Fun(List<ASTree> c) { super(c); }
    public ParameterList parameters() { return (ParameterList)child(0); }
    public BlockStmnt body() { return (BlockStmnt)child(1); }
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }
}
