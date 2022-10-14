package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class PackageDeclarationVisitor  extends ASTVisitor{
	  List<PackageDeclaration> packages = new ArrayList<>();
	  public static List<String> packagesNames = new ArrayList<>();
	  int nbPackages = 0;
	  

	    @Override
	    public boolean visit(PackageDeclaration packageNode){
	    	if (!packagesNames.contains(packageNode.getName().toString())) {
				packagesNames.add(packageNode.getName().toString());
			}
			return true;

	    }

	    public List<PackageDeclaration> getPackages() {
	        return packages;
	    }

}