package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import visitors.FieldDeclarationVisitor;
import visitors.FileVisitor;
import visitors.MethodDeclarationVisitor;
import visitors.MethodInvocationVisitor;
import visitors.PackageDeclarationVisitor;
import visitors.TypeDeclarationVisitor;

public class Analyse {
	private int JavaClassCounter;
	private int JavaClassLinesCounter;
	private int JavaMethodCounter;
	private int MethodLineCounter;
	private int sommeMethodLine;
	private int nbPackages;
	private int LineCounter;
	private int sommeLineCounter;
	private int FieldCounter;
	private int ParametersCounter;
	private String methodName;
	private String methodNameInv;
	private String methodInvocationExpression;
	private String methodInvocationWithoutExp;
	

	private Map<TypeDeclaration, Integer> mapClassNbMethods;
	private Map<TypeDeclaration, Integer> mapClassNbFields;
    private Map<MethodDeclaration, Integer> mapNbLinesByMethod;
    private Map<MethodDeclaration, Integer> mapNbParametersByMethod;
    private Map<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>> mapInvocationMethodsByclass;
    private Map<MethodDeclaration, Set<MethodInvocation>> mapInvocationMethods;
    private Set<MethodInvocation> methodInvocations;
	private ArrayList<String> packages;
	private Set<String> methodsInvocatedNames;
	


	public Analyse() {
		super();
		JavaClassCounter = 0;
		JavaClassLinesCounter = 0;
		JavaMethodCounter = 0;
		MethodLineCounter = 0;
		this.sommeMethodLine = 0;
		this.nbPackages = 0;
		LineCounter = 0;
		this.sommeLineCounter = 0;
		FieldCounter = 0;
		this.methodInvocationWithoutExp = null;
		this.methodInvocationExpression = null;
		this.mapClassNbMethods = new HashMap<TypeDeclaration, Integer>();
		this.mapClassNbFields = new HashMap<TypeDeclaration, Integer>();
		this.mapNbLinesByMethod = new HashMap<MethodDeclaration, Integer>();
		this.mapNbParametersByMethod = new HashMap<MethodDeclaration, Integer>();
		this.packages = new ArrayList<String>();
		this.methodsInvocatedNames = new HashSet<String>();
		this.methodInvocations = new HashSet<MethodInvocation>();
		this.mapInvocationMethods = new HashMap<MethodDeclaration, Set<MethodInvocation>>();
		this.mapInvocationMethodsByclass = new HashMap<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>>();
	}

	public int getJavaClassCounter() {
		return JavaClassCounter;
	}

	public void setJavaClassCounter(int javaClassCounter) {
		JavaClassCounter = javaClassCounter;
	}

	public int getJavaClassLinesCounter() {
		return JavaClassLinesCounter;
	}

	public void setJavaClassLinesCounter(int javaClassLinesCounter) {
		JavaClassLinesCounter = javaClassLinesCounter;
	}

	public int getJavaMethodCounter() {
		return JavaMethodCounter;
	}

	public void setJavaMethodCounter(int javaMethodCounter) {
		JavaMethodCounter = javaMethodCounter;
	}

	public int getMethodLineCounter() {
		return MethodLineCounter;
	}

	public void setMethodLineCounter(int methodLineCounter) {
		MethodLineCounter = methodLineCounter;
	}

	public Map<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>> getMapInvocationMethodsByclass() {
		return mapInvocationMethodsByclass;
	}

	public Map<MethodDeclaration, Set<MethodInvocation>> getMapInvocationMethods() {
		return mapInvocationMethods;
	}

	public Set<MethodInvocation> getMethodInvocations() {
		return methodInvocations;
	}

	public int getSommeMethodLine() {
		return sommeMethodLine;
	}

	public void setSommeMethodLine(int sommeMethodLine) {
		this.sommeMethodLine = sommeMethodLine;
	}

	public int getNbPackages() {
		return nbPackages;
	}

	public void setNbPackages(int nbPackages) {
		this.nbPackages = nbPackages;
	}

	public int getLineCounter() {
		return LineCounter;
	}

	public void setLineCounter(int lineCounter) {
		LineCounter = lineCounter;
	}

	public int getSommeLineCounter() {
		return sommeLineCounter;
	}

	public void setSommeLineCounter(int sommeLineCounter) {
		this.sommeLineCounter = sommeLineCounter;
	}

	public int getFieldCounter() {
		return FieldCounter;
	}

	public void setFieldCounter(int fieldCounter) {
		FieldCounter = fieldCounter;
	}

	public Map<TypeDeclaration, Integer> getMapClassNbMethods() {
		return mapClassNbMethods;
	}

	public void setMapClassNbMethods(Map<TypeDeclaration, Integer> mapClassNbMethods) {
		this.mapClassNbMethods = mapClassNbMethods;
	}

	public ArrayList<String> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<String> packages) {
		this.packages = packages;
	}

	public int getParametersCounter() {
		return ParametersCounter;
	}

	public void setParametersCounter(int parametersCounter) {
		ParametersCounter = parametersCounter;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodNameInv() {
		return methodNameInv;
	}


	public Map<MethodDeclaration, Integer> getMapNbParametersByMethod() {
		return mapNbParametersByMethod;
	}

	public Map<TypeDeclaration, Integer> getMapClassNbFields() {
		return mapClassNbFields;
	}

	public String getMethodInvocationExpression() {
		return methodInvocationExpression;
	}

	public String getMethodInvocationWithoutExp() {
		return methodInvocationWithoutExp;
	}

	public void setMapClassNbFields(Map<TypeDeclaration, Integer> mapClassNbFields) {
		this.mapClassNbFields = mapClassNbFields;
	}

	public Map<MethodDeclaration, Integer> getMapNbLinesByMethod() {
		return mapNbLinesByMethod;
	}

	public void setMapNbLinesByMethod(Map<MethodDeclaration, Integer> mapNbLinesByMethod) {
		this.mapNbLinesByMethod = mapNbLinesByMethod;
	}

	// 1
	public int printNumberOfJavaClasses(CompilationUnit parse) {
		visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		for (TypeDeclaration node : visitor.getTypes()) {
			if (!node.isInterface()) {
				JavaClassCounter++;
			}
		}
		return JavaClassCounter;
	}

	// 2
	public int getNumberOfLines(CompilationUnit cu) {
		FileVisitor fileVisitor = new FileVisitor();
		cu.accept(fileVisitor);
		visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		cu.accept(visitor);
		LineCounter += fileVisitor.getNbLinesOfCode();
		// System.out.println("nombre de ligne dans une class : "+LineCounter);
		return LineCounter;
	}

	// 3
	public int printNumberOfMethods(CompilationUnit parse) {
		visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		for (TypeDeclaration node : visitor.getTypes()) {
			visitors.MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
			parse.accept(methodVisitor);
			JavaMethodCounter += methodVisitor.getMethods().size();
			// System.out.println(methodVisitor.getMethods().size());
			mapClassNbMethods.put(node, methodVisitor.getMethods().size());

		}
		return JavaClassCounter;
	}

	// 4
	public int numberOfPackages(CompilationUnit cu) {
		PackageDeclarationVisitor visitorPackage = new PackageDeclarationVisitor();
		cu.accept(visitorPackage);
		nbPackages = visitorPackage.packagesNames.size();

		return nbPackages;
	}

	// 5
	public int AvgMethodsByClass() {
		return JavaMethodCounter / JavaClassCounter;
	}

	public void printNumberOfMethodLines(CompilationUnit cu) {
		visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		cu.accept(visitor);
		for (TypeDeclaration node : visitor.getTypes()) {
			for (MethodDeclaration method : node.getMethods()) {
				int startPos = cu.getLineNumber(method.getStartPosition());
				int endPos = cu.getLineNumber(method.getStartPosition() + method.getLength());
				//System.out.println("nombre de ligne de la methode : " + ((endPos - startPos) + 1));
				sommeMethodLine += ((endPos - startPos) + 1);
				mapNbLinesByMethod.put(method, ((endPos - startPos) + 1));
			}
		}
	}

	// 6
	public int printAvgLinesByMethod() {
		return sommeMethodLine / JavaMethodCounter;
	}

	public void printFields(CompilationUnit cu) {
		visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		cu.accept(visitor);
		for (TypeDeclaration node : visitor.getTypes()) {
			FieldDeclarationVisitor fieldVisitor = new FieldDeclarationVisitor();
			cu.accept(fieldVisitor);
			FieldCounter += fieldVisitor.getFields().size();
			mapClassNbFields.put(node, fieldVisitor.getFields().size());
		}
		// System.out.println(FieldCounter);
	}

	// 7
	public int AvgFields() {
		return FieldCounter / JavaClassCounter;
	}

	public  int get10PercentsOfClasses() {
		return (int) (((double) JavaClassCounter) * 10 / 100);
	}

	public  Map<TypeDeclaration, Integer> sortMapClass(Map<TypeDeclaration, Integer> map, int numberExpected) {

		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 																				// values
				.limit(numberExpected).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
	}
    
	//8
	public  Map<TypeDeclaration, Integer> get10PercentsClassesByMethods() {
		return sortMapClass(mapClassNbMethods, get10PercentsOfClasses());
	}
	
	//9
	public  Map<TypeDeclaration, Integer> get10PercentsClassesByFields() {
		return sortMapClass(mapClassNbFields, get10PercentsOfClasses());
	}
	
   //affichage de classes
	public static void afficherMap(Map<TypeDeclaration, Integer> map) {
		map.entrySet().stream().forEach(i -> System.out.println(i.getKey().getName() + ";" + i.getValue()));
	}
	
    //10
    public Map<TypeDeclaration, Integer> get10PercentsClassesByFieldsAndMethods(Map<TypeDeclaration, Integer> entrymap, Map<TypeDeclaration, Integer> map) {
        return entrymap
                .entrySet()
                .stream()
                .filter(x -> map.containsKey(x.getKey()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
    }
    

    //11
    public Map<TypeDeclaration, Integer> getClassesBybMethodsGreaterThanX(int x) {
        return mapClassNbMethods
                .entrySet()
                .stream()
                .filter(e -> e.getValue()>x)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
    
    private int get10PercentsOfTotalNumberOfMethods() {
        return (int) (((double) JavaMethodCounter)*10/100);
    }
    //sort methodes By line
	public  Map<MethodDeclaration, Integer> sortMapMethod(Map<MethodDeclaration, Integer> map, int numberExpected) {

		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 																				// values
				.limit(numberExpected).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
	}
    
    //12
    public Map<MethodDeclaration, Integer> get10PrecentsOfMethodsByLine() {
        return sortMapMethod(mapNbLinesByMethod,get10PercentsOfTotalNumberOfMethods());
    }
    
	
    //affichage de methodes
  	public static void afficherMethodMap(Map<MethodDeclaration, Integer> map) {
  		map.entrySet().stream().forEach(i -> System.out.println(i.getKey().getName() + ";" + i.getValue()));
  	}
  	
    private void printNumberOfParameters(CompilationUnit cu) {
    	visitors.TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		cu.accept(visitor);
        for(TypeDeclaration node : visitor.getTypes()) {
        	for(MethodDeclaration nodeMethod : node.getMethods()) {
        		ParametersCounter = nodeMethod.parameters().size();
        		mapNbParametersByMethod.put(nodeMethod, ParametersCounter);
        	}
        }
    
    }
    //13
    public Map<MethodDeclaration, Integer> getMaxParameters() {
        return sortMapMethod(mapNbParametersByMethod,1);
    }
    
    
    //pour obtenir les methodes invoquer de chaque methode
	private void printMethodsInvForEachMethod(CompilationUnit cu) throws IOException {
		
	
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		cu.accept(visitor);

		for (TypeDeclaration nodeClass : visitor.getTypes()) {
			MethodDeclarationVisitor visitorMethod = new MethodDeclarationVisitor();
			nodeClass.accept(visitorMethod);
			
			String methodInvoker;
			for (MethodDeclaration nodeMethod : visitorMethod.getMethods()) {
				MethodInvocationVisitor visitorMethodInvoction = new MethodInvocationVisitor();
				nodeMethod.accept(visitorMethodInvoction);
				methodName = nodeMethod.getName().toString();
				methodInvocations = visitorMethodInvoction.getMethodsInvocation();
				
				methodInvoker = nodeClass.getName().toString()+"::"+methodName;
				
				for (MethodInvocation methodInvocation : methodInvocations) {
					String methodInvoked;
					mapInvocationMethods.put(nodeMethod, methodInvocations);
					mapInvocationMethodsByclass.put(nodeClass, mapInvocationMethods);
					
					  if (methodInvocation.getExpression() != null) { 
						  if(methodInvocation.getExpression().resolveTypeBinding() != null) {
							  methodInvocationWithoutExp =
									  methodInvocation.getExpression().resolveTypeBinding().getName();
							  methodInvoked = methodInvocationWithoutExp+"::"+methodInvocation.getName();
							  methodsInvocatedNames.add("\t\""+methodInvoker+"\"->\""+methodInvoked+"\"\n");
						  }
					  }
					   else{if(methodInvocation.resolveMethodBinding()!= null) { 
						   methodInvocationWithoutExp = methodInvocation.resolveMethodBinding().getDeclaringClass().getName();
						   methodInvoked = methodInvocationWithoutExp+"::"+methodInvocation.getName();
						   methodsInvocatedNames.add("\t\""+methodInvoker+"\"->\""+methodInvoked+"\"\n");
					   }
					}
				}

			}
		
		}
	}

	
	public static void displayTheCAllGraph(Map<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>> aCallGraph) {
	    Set<Map.Entry<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>>> set = aCallGraph.entrySet();
	    for (Map.Entry<TypeDeclaration, Map<MethodDeclaration, Set<MethodInvocation>>> mapEntry1 : set) {
	        System.out.println("CLASS : " + mapEntry1.getKey().getName());
	        for (Map.Entry<MethodDeclaration, Set<MethodInvocation>> mapEntry2: mapEntry1.getValue().entrySet()) {
	            System.out.println("\tMETHOD : " + mapEntry2.getKey().getName());
	            for (MethodInvocation methodInvocation : mapEntry2.getValue()) {
	                System.out.println("\t\tCALL : " + methodInvocation.getName());
	                if (methodInvocation.getExpression() != null) {
						if (methodInvocation.getExpression().resolveTypeBinding() != null) {
							  System.out.println( "\t\t\tTYPE : " + methodInvocation.getExpression().resolveTypeBinding().getName()); 
						}
					} else {
						if(methodInvocation.resolveMethodBinding()!= null) {
						System.out.println("\t\t\tTYPE : " + methodInvocation.resolveMethodBinding().getDeclaringClass().getName());
					}
					}
	                
	            }
	        }
	    } 
	}

	
    public void graphToDotFile(String fileGraphPath) throws IOException {
    	System.out.println(methodsInvocatedNames);
        FileWriter fW = new FileWriter(fileGraphPath);
        fW.write("digraph G {\n");
        for (String methodsInvocated :
        	methodsInvocatedNames) {
            fW.write(methodsInvocated);
        }
        fW.write("}");
        fW.close();
    }
    
    public  void dotToPng(String fileGraphPath) {
        try {

            MutableGraph mutableGraph = new Parser().read(new File(fileGraphPath));
            Graphviz.fromGraph(mutableGraph).render(Format.PNG).toFile(new File("graph.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  	
	public void staticAnalysis(String pathProject) throws IOException {
		
		parser.Parser parser = new parser.Parser(pathProject);
		ArrayList<File> javaFiles = parser.listJavaFilesForFolder();
		for (File javaFile : javaFiles) {
			String content = FileUtils.readFileToString(javaFile);
			CompilationUnit parse = new parser.Parser(pathProject).parse(content.toCharArray());

			printNumberOfJavaClasses(parse);
			getNumberOfLines(parse);
			sommeLineCounter += LineCounter;
			printNumberOfMethods(parse);
			numberOfPackages(parse);
			printNumberOfMethodLines(parse);
			printFields(parse);
			printNumberOfParameters(parse);
			printMethodsInvForEachMethod(parse);
			
		}
		graphToDotFile("graph.dot");
	}
}
