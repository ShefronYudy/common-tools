package org.shefron.designpattern.behaviour.interpreter;

import java.util.HashMap;

abstract class Expression {
	// �Ի�����Ϊ׼�����������͸������κ�һ�����ʽ
	public abstract boolean interpret(Context ctx);

	// �����������ʽ�ڽṹ���Ƿ���ͬ
	public abstract boolean equals(Object o);

	// ���ر��ʽ��hash code
	public abstract int hashCode();

	// �����ʽת�����ַ���
	public abstract String toString();
}

class Constant extends Expression {
	private boolean value;

	public Constant(boolean value) {
		this.value = value;
	}

	// ���Ͳ���
	public boolean interpret(Context ctx) {
		return value;
	}

	// �����������ʽ�ڽṹ���Ƿ���ͬ
	public boolean equals(Object o) {
		if (o != null && o instanceof Constant) {
			return this.value == ((Constant) o).value;
		}
		return false;
	}

	// ���ر��ʽ��hash code
	public int hashCode() {
		return (this.toString()).hashCode();
	}

	// �����ʽת�����ַ���
	public String toString() {
		return new Boolean(value).toString();
	}
}

class Variable extends Expression {
	private String name;

	public Variable(String name) {
		this.name = name;
	}

	public boolean interpret(Context ctx) {
		return ctx.lookup(this);
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Variable) {
			return this.name.equals(((Variable) o).name);
		}
		return false;
	}

	public int hashCode() {
		return (this.toString()).hashCode();
	}

	public String toString() {
		return name;
	}
}

class And extends Expression {
	public Expression left, right;

	public And(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public boolean interpret(Context ctx) {
		return left.interpret(ctx) && right.interpret(ctx);
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof And) {
			return this.left.equals(((And) o).left)
					&& this.right.equals(((And) o).right);
		}
		return false;
	}

	public int hashCode() {
		return (this.toString()).hashCode();
	}

	public String toString() {
		return "(" + left.toString() + " AND " + right.toString() + ")";
	}
}

class Or extends Expression {
	private Expression left, right;

	public Or(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public boolean interpret(Context ctx) {
		return left.interpret(ctx) || right.interpret(ctx);
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Or) {
			return this.left.equals(((And) o).left)
					&& this.right.equals(((And) o).right);
		}
		return false;
	}

	public int hashCode() {
		return (this.toString()).hashCode();
	}

	public String toString() {
		return "(" + left.toString() + " OR " + right.toString() + ")";
	}
}

class Not extends Expression {
	private Expression exp;

	public Not(Expression exp) {
		this.exp = exp;
	}

	public boolean interpret(Context ctx) {
		return !exp.interpret(ctx);
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Not) {
			return this.exp.equals(((Not) o).exp);
		}
		return false;
	}

	public int hashCode() {
		return (this.toString()).hashCode();
	}

	public String toString() {
		return "(NOT " + exp.toString() + ")";
	}
}

class Context {
	private HashMap<Variable, Boolean> map = new HashMap<Variable, Boolean>();

	public void assign(Variable var, boolean value) {
		map.put(var, new Boolean(value));
	}

	public boolean lookup(Variable var) throws IllegalArgumentException {
		Boolean value = (Boolean) map.get(var);
		if (value == null) {
			throw new IllegalArgumentException();
		}
		return value.booleanValue();
	}
}

// �ͻ���
public class Test {
	private static Context ctx;
	private static Expression exp;

	public static void main(String args[]) {
		ctx = new Context();
		Variable x = new Variable("x");
		Variable y = new Variable("y");

		ctx.assign(x, false);
		ctx.assign(y, true);

		Constant c = new Constant(true);
		exp = new Or(new And(c, x), new And(y, new Not(x)));
		System.out.println("x = " + x.interpret(ctx));
		System.out.println("y = " + y.interpret(ctx));
		System.out.println(exp.toString() + "=" + exp.interpret(ctx));
	}
}
