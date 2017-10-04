package stone.ast;

import stone.Token;

/**
 * 字符串字面量的树结构，除了构造器，就是就是一个
 */
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }
    public String value() {
        return token().getText();
    }
}
