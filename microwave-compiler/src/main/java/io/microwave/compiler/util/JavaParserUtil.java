package io.microwave.compiler.util;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.utils.SourceRoot;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaParserUtil {
    private JavaParserUtil() {
    }

    public static void handleMicrowaveFactory(Path root, String className) {
        SourceRoot sourceRoot = new SourceRoot(root);
        CompilationUnit cu = sourceRoot.parse("", className);
        cu.accept(new ModifierVisitor<Void>(){

            @Override
            public MethodDeclaration visit(MethodDeclaration md, Void arg){
                md.getAnnotations().forEach(it -> {
                    System.out.println("###:" + md.getNameAsString());
                    System.out.println("###:" + ((ClassOrInterfaceDeclaration)md.getParentNode().get()).getName());
                    System.out.println("###:" + it.getNameAsString());
                });
                md.getName();
                if(CollectionUtils.isEmpty(md.getAnnotations())) {
                    return md;
                }
                int i = 0;
                for(AnnotationExpr ae: md.getAnnotations()) {
                    if(!ae.getNameAsString().equals("Factory")) {
                        break;
                    }
                    md.removeBody();
                    BlockStmt blockStmt = new BlockStmt();
                    NodeList<Statement> statements = new NodeList<>();
                    ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration)md.getParentNode().get();
                    FieldDeclaration bd = (FieldDeclaration)cid.getMember(i++);
                    String name = bd.getVariable(1).getInitializer().get().asStringLiteralExpr().asString();
                    String returnStatement = "new " + name + "()";

                    BlockStmt block = new BlockStmt();

                    ReturnStmt statement = new ReturnStmt(returnStatement);
                    statements.add(statement);
                    blockStmt.setStatements(statements);
                    md.setBody(blockStmt);
                }
                return md;
            }
        }, null);
        sourceRoot.saveAll(root.resolve(Paths.get("output")));
    }
}
