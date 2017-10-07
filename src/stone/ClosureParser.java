package stone;

import stone.ast.Fun;

import static stone.Parser.rule;

/**
 * @Author: Lighters_c
 * @Discrpition: 闭包的语法分析器
 * @Date: Created in 23:58 2017/10/7
 * @Modified_by:
 */
public class ClosureParser extends FuncParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }
}
