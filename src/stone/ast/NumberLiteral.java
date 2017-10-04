package stone.ast;

import stone.Token;

/**
 * 数字字面量作为终结符，只能是叶节点，其上的操作自然就是获取它的值
 */
public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {return token.getNumber();}
}
