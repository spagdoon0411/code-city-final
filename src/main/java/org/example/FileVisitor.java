package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
//import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
//import com.github.javaparser.resolution.types.ResolvedReferenceType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileVisitor extends VoidVisitorAdapter<Void> {
    private int numFields;
    private int numMethods;
    private int numLocalVars;
    private String name;
    private String pack;
    private ArrayList<String> deps;
    /**
     * Assume that this file is 
     * @param path
     */
    public FileVisitor(String path){
        numFields = 0;
        numMethods = 0;
        numLocalVars = 0;
        deps = new ArrayList<String>();
        try{
            FileInputStream fIn = new FileInputStream(path);
            JavaParser jp = new JavaParser();
            CompilationUnit cu = jp.parse(fIn).getResult().get();
            this.visit(cu, null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void visit(MethodDeclaration md, Void arg){
        super.visit(md, arg);
        numMethods++;
    }

    @Override
    public void visit(FieldDeclaration fd, Void arg){
        super.visit(fd, arg);
        numFields++;
        /*if(fd.getCommonType().isReferenceType()){
            deps.add(fd.getCommonType().asReferenceType().asString());
        }*/
    }

    @Override
    public void visit(VariableDeclarationExpr vde, Void arg){
        super.visit(vde, arg);
        numLocalVars++;
        /*if(vde.calculateResolvedType().isReferenceType() && vde.calculateResolvedType().asReferenceType().hasName()){
            deps.add(vde.calculateResolvedType().asReferenceType().getQualifiedName());
        }*/
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration coid, Void arg){
        super.visit(coid, arg);
        name = coid.getNameAsString();
    }

    @Override
    public void visit(PackageDeclaration pd, Void arg){
        super.visit(pd, arg);
        pack = pd.getNameAsString();
    }

    @Override
    public void visit(ClassOrInterfaceType cit, Void arg){
        super.visit(cit, arg);
        deps.add(cit.asString());
    }

    public int getNumMethods(){
        return numMethods;
    }
    public int getNumFields(){
        return numFields;
    }
    public int getNumLocalVars(){
        return numLocalVars;
    }
    public String getName(){
        return name;
    }
    public String getPackage(){
        return pack;
    }
    public ArrayList<String> getDependencyList(){
        return deps;
    }
}