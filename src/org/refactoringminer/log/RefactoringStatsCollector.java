package org.refactoringminer.log;

import gr.uom.java.xmi.diff.*;
import org.refactoringminer.api.RefactoringVisitor;

import java.util.HashSet;
import java.util.Set;

public class RefactoringStatsCollector implements RefactoringVisitor {

    private Set<String> refactoredClasses = new HashSet<>();
    public int anonymousClassToTypeCount = 0;
    public int extractAndMoveOperationCount = 0;
    public int extractOperationCount = 0;
    public int extractSuperclassCount = 0;
    public int inlineOperationCount = 0;
    public int moveAttributeCount = 0;
    public int moveClassCount = 0;
    public int moveOperationCount = 0;
    //public int moveSourceFolderCount = 0;


    @Override
    public void visitConvertAnonymousClassToType(ConvertAnonymousClassToTypeRefactoring refactoring) {
        anonymousClassToTypeCount++;
        refactoredClasses.add(refactoring.getAddedClass().getName());

    }

    @Override
    public void visitExtractAndMoveOperation(ExtractAndMoveOperationRefactoring refactoring) {
        extractAndMoveOperationCount++;
        refactoredClasses.add(refactoring.getSourceOperationBeforeExtraction().getClassName());
        refactoredClasses.add(refactoring.getExtractedOperation().getClassName());
    }

    @Override
    public void visitExtractOperation(ExtractOperationRefactoring refactoring) {
        extractOperationCount++;
        refactoredClasses.add(refactoring.getSourceOperationBeforeExtraction().getClassName());
    }

    @Override
    public void visitExtractSuperclass(ExtractSuperclassRefactoring refactoring) {
        extractSuperclassCount++;
        refactoredClasses.add(refactoring.getExtractedClass().getName());
        refactoredClasses.addAll(refactoring.getSubclassSet());
    }

    @Override
    public void visitInlineOperation(InlineOperationRefactoring refactoring) {
        inlineOperationCount++;
        refactoredClasses.add(refactoring.getTargetOperationAfterInline().getClassName());
    }

    @Override
    public void visitMoveAttribute(MoveAttributeRefactoring refactoring) {
        moveAttributeCount++;
        refactoredClasses.add(refactoring.getSourceClassName());
        refactoredClasses.add(refactoring.getTargetClassName());
    }

    @Override
    public void visitMoveClass(MoveClassRefactoring refactoring) {
        moveClassCount++;
        refactoredClasses.add(refactoring.getMovedClassName());
    }

    @Override
    public void visitMoveOperation(MoveOperationRefactoring refactoring) {
        moveOperationCount++;
        refactoredClasses.add(refactoring.getOriginalOperation().getClassName());
        refactoredClasses.add(refactoring.getMovedOperation().getClassName());
    }

    @Override
    public void visitMoveSourceFolder(MoveSourceFolderRefactoring refactoring) {

    }

    @Override
    public void visitRenameClass(RenameClassRefactoring refactoring) {

    }

    @Override
    public void visitRenameOperation(RenameOperationRefactoring refactoring) {

    }

    @Override
    public void visitRenamePackage(RenamePackageRefactoring refactoring) {

    }

    @Override
    public void visitExtractClassOperation(ExtractClassRefactoring extractClassRefactoring) {

    }

    @Override
    public void visitExtractVariableOperation(ExtractVariableRefactoring extractVariableRefactoring) {

    }

    @Override
    public void visitInlineVariableOperation(InlineVariableRefactoring inlineVariableRefactoring) {

    }

    @Override
    public void visitMoveAndRenameClassOperation(MoveAndRenameClassRefactoring moveAndRenameClassRefactoring) {

    }

    @Override
    public void visitRenameAttributeOperation(RenameAttributeRefactoring renameAttributeRefactoring) {

    }

    @Override
    public void visitRenameVariableOperation(RenameVariableRefactoring renameVariableRefactoring) {

    }

    public Set<String> getRefactoredClasses() {
        return refactoredClasses;
    }

    public int getRefactoringCount(){
        return anonymousClassToTypeCount + extractAndMoveOperationCount
                + extractOperationCount + extractSuperclassCount
                + inlineOperationCount + moveAttributeCount
                + moveClassCount + moveOperationCount;
    }

}
