package chap6;
import javassist.gluonj.*;
import stone.StoneException;
import stone.ast.ASTList;
import stone.ast.ASTree;

import java.util.List;

@Reviser public class BasicEvaluator {
    public static final int TRUE=1;
    public static final int FALES=0;

    @Reviser public static abstract class ASTreeEx extends ASTree {
        public abstract Object eval(Environment env);
    }

    @Reviser public static abstract class ASTListEx extends ASTList {
        public ASTListEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env) {
            throw new StoneException("cannot eval: "+toString(),this);
        }
    }
}
