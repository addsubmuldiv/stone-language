package stone;

import stone.ast.ASTree;

public class StoneException extends RuntimeException {
    public StoneException(String m) {
        super(m);
    }

    /**
     * 显示错误信息及其行号
     * @param m
     * @param t
     */
    public StoneException(String m,ASTree t){
        super(m+" "+t.location());
    }
}
