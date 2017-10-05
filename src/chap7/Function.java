package chap7;

import chap6.Environment;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 15:50 2017/10/5
 * @Modified by:
 */
public class Function {
    protected ParameterList parameters;
    protected BlockStmnt body;
    protected Environment env;
    public Function(ParameterList parameters, BlockStmnt body, Environment env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }
    public ParameterList parameters() { return parameters; }
    public BlockStmnt body() { return body; }
    public Environment makeEnv() { return new NestedEnv(env); }
    @Override
    public String toString() {
        return "<fun:"+hashCode()+">";
    }
}
