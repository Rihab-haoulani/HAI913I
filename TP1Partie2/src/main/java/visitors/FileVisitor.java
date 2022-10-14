package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class FileVisitor extends ASTVisitor {
	private int nbLinesOfCode;


    public void FileSourceVisitor() {
        nbLinesOfCode = 0;
    }

    public int getNbLinesOfCode() {
        return nbLinesOfCode;
    }

    public boolean visit(AssertStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(BreakStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(ConstructorInvocation node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(ContinueStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(DoStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(EmptyStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(EnhancedForStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(ExpressionStatement node){
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(ForStatement node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(FieldDeclaration node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(IfStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(ImportDeclaration node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(LabeledStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(MethodDeclaration node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(SuperConstructorInvocation node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(PackageDeclaration node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(ReturnStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(SwitchCase node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(SwitchStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(ThrowStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(TryStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(TypeDeclaration node) {
        nbLinesOfCode++;
        return super.visit(node);
    }

    public boolean visit(SynchronizedStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(TypeDeclarationStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(VariableDeclarationStatement node) {
        nbLinesOfCode++;
        return true;
    }

    public boolean visit(WhileStatement node) {
        nbLinesOfCode++;
        return true;
    }
}



