package chap9;

import chap6.Environment;
import chap7.FuncEvaluator;
import chap7.NestedEnv;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.StoneException;
import stone.ast.*;
import chap7.FuncEvaluator.EnvEx;
import chap6.BasicEvaluator.ASTreeEx;
import chap9.StoneObject.AccessException;
import java.util.List;
import chap6.BasicEvaluator.BinaryEx;
import chap7.FuncEvaluator.PrimaryEx;
/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 13:33 2017/10/13
 * @Modified_by:
 */
@Require(FuncEvaluator.class)
@Reviser public class ClassEvaluator {
    @Reviser public static class ClassStmntEx extends ClassStmnt {
        public ClassStmntEx(List<ASTree> list) {
            super(list);
        }

        /**
         * 这个eval方法定义一个类，也就是说，先新建一个ClassInfo的对象，保存你定义的类的相关信息，
         * 然后把类名和ClassInfo对象放到环境里
         * @param env
         * @return
         */
        public Object eval(Environment env) {
            ClassInfo ci = new ClassInfo(this,env);
            ((EnvEx)env).put(name(),ci);
            return name();
        }
    }
    @Reviser public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<ASTree> list) {
            super(list);
        }

        /**
         * 这里就和块结构一样了
         * @param env
         * @return
         */
        public Object eval(Environment env) {
            for(ASTree t: this) {
                ((ASTreeEx)t).eval(env);
            }
            return null;
        }
    }
    @Reviser public static class DotEx extends Dot {
        public DotEx(List<ASTree> list) {
            super(list);
        }
        public Object eval(Environment env, Object value) {
            String member=name();
            if(value instanceof ClassInfo) {
                if("new".equals(member)) {
                    ClassInfo ci=(ClassInfo)value;
                    NestedEnv e=new NestedEnv(ci.environment());
                    StoneObject so=new StoneObject(e);
                    e.putNew("this",so);
                    initObject(ci,e);
                    return so;
                }
            }
            else if(value instanceof StoneObject) {
                try {
                    return ((StoneObject)value).read(member);
                } catch (AccessException e) {}
            }
            throw new StoneException("bad member access: "+member,this);
        }
        protected void initObject(ClassInfo ci, Environment env) {
            if(ci.superClass()!=null)
                initObject(ci.superClass(),env);
            ((ClassBodyEx)ci.body()).eval(env);
        }
    }

    @Reviser public static class AssignEx extends BinaryEx {
        public AssignEx(List<ASTree> c) {
            super(c);
        }

        @Override
        protected Object computeAssign(Environment env, Object rvalue) {
            ASTree le=left();
            if(le instanceof PrimaryExpr) {
                PrimaryEx p = (PrimaryEx)le;
                if(p.hasPostfix(0)&&p.postfix(0) instanceof Dot) {
                    Object t=((PrimaryEx)le).evalSubExpr(env,1);
                    if(t instanceof StoneObject)
                        return setField((StoneObject)t,(Dot)p.postfix(0),rvalue);
                }
            }
            return super.computeAssign(env,rvalue);
        }

        protected Object setField(StoneObject obj, Dot expr, Object rvalue) {
            String name=expr.name();
            try {
                obj.write(name,rvalue);
                return rvalue;
            } catch (AccessException e) {
                throw new StoneException("bad member access "+location()+":"+name);
            }
        }
    }
}

