package visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationVisitor extends ASTVisitor {
	Set<MethodInvocation> methodsInvocation = new HashSet();

	@Override
	public boolean visit(MethodInvocation node) {
		methodsInvocation.add(node);
		return super.visit(node);
	}

	public Set<MethodInvocation> getMethodsInvocation() {
		return methodsInvocation;
	}

}
