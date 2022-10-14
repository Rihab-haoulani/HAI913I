package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class SuperMethodInvocationVisitor extends ASTVisitor {
	List<SuperMethodInvocation> superMethodsInvocation = new ArrayList<>();

	@Override
	public boolean visit(SuperMethodInvocation node) {
		superMethodsInvocation.add(node);
		return super.visit(node);
	}

	public List<SuperMethodInvocation> getSuperMethodsInvocation() {
		return superMethodsInvocation;
	}

}
