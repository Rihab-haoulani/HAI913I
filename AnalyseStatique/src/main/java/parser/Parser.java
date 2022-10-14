package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {
	
	//private  String projectPath  = "C:\\Users\\Lenovo\\eclipse-workspace\\Sudoku";
	private  String projectSourcePath ;/* = projectPath + "\\src"; */
	//private  String jrePath = "C:\\Program Files\\Java\\jre1.8.0_51\\lib\\rt.jar";
	private File folder;
	
	
	public Parser(String projectSourcePath,String jrePath ) {
		super();
		this.projectSourcePath = projectSourcePath;
		this.jrePath = jrePath;
		this.folder = new File(projectSourcePath);
	}
	
	public CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		parser.setBindingsRecovery(true);

		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);

		parser.setUnitName("");

		String[] sources = { projectSourcePath };
		String[] classpath = { jrePath };

		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);
		parser.setSource(classSource);

		return (CompilationUnit) parser.createAST(null); // create and parse
	}
	
	// read all java files from specific folder
	public  ArrayList<File> listJavaFilesFromFolder(final File folder) throws FileNotFoundException {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesFromFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}
		return javaFiles;
	}

	public String getProjectSourcePath() {
		return projectSourcePath;
	}

	public void setProjectSourcePath(String projectSourcePath) {
		this.projectSourcePath = projectSourcePath;
	}
    
	public ArrayList<File> listJavaFilesForFolder() throws FileNotFoundException {
        return listJavaFilesFromFolder(this.folder);
    }
	

}

