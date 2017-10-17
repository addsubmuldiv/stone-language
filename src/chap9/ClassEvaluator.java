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
         * 类的体，也就是类的定义，这里就和块结构一样了(话说回来这样的话类不是和方法区别其实不大了么……)
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


    /**
     * Dot类也是Postfix类的子类
     */
    @Reviser public static class DotEx extends Dot {
        public DotEx(List<ASTree> list) {
            super(list);
        }

        /**
         * Dot的eval需要两个参数，一个是环境，一个是句点左侧的运算结果,如果句点右侧是"new"，
         * 句点表达式用于创建一个新对象，其中句点左侧是要创建的类,其运算结果是一个ClassInfo对象，
         * eval方法根据ClassInfo对象创建一个新的环境，把ClassInfo对象中保存的环境作为这个环境的
         * 外部环境，然后在新的环境里添加这个新建的stone对象，which is StoneObject类的一个实例；
         *
         * 如果句点右边不是new，该句点表达式将用于方法调用或者字段访问。句点左侧是需要访问的对象，
         * 它的计算结果是一个StoneObject对象,如果是一个字段，那么将调用它的read方法获取字段的值
         * 并返回。或者返回的是一个Function对象，在Stone中字段和方法没有本质区别，只是返回的对象
         * 不一样,我们无需做特别的处理来实现方法调用。
         * @param env
         * @param value
         * @return
         */
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

        /**
         * 对新的对象进行一些初始化工作，包括把它的父类添加到新建的环境中
         * @param ci
         * @param env
         */
        protected void initObject(ClassInfo ci, Environment env) {
            if(ci.superClass()!=null)
                initObject(ci.superClass(),env);
            ((ClassBodyEx)ci.body()).eval(env);
        }
    }


    /**
     * 为Stone中的类添加对其字段进行赋值的功能,先判断左边的是不是一个类对象的字段(看后缀的是不是Dot类的实例)
     * 如果是的话，就把这个类的这个字段赋值为rvalue
     */
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

        /**
         * 获取Dot类对象的名字(就是句点后面的字段名)，然后调用StoneObject对象的write()方法，对其的某一字段
         * 进行赋值
         * @param obj
         * @param expr
         * @param rvalue
         * @return
         */
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

