package org.refactoringminer.log;

import gr.uom.java.xmi.diff.*;
import org.refactoringminer.api.RefactoringVisitor;

import java.util.HashSet;
import java.util.Set;

public class RefactoringStatsCollector implements RefactoringVisitor {

    /**
     * we do not take into account renames (or moves to other packages)
     */
    private Set<String> refactoredClasses = new HashSet<>();
    public int anonymousClassToTypeRefactorings = 0;
    public int extractAndMoveOperationRefactorings = 0;
    public int extractOperationRefactorings = 0;
    public int extractSuperclassRefactorings = 0;
    public int inlineOperationRefactorings = 0;
    public int moveAttributeRefactorings = 0;
    public int moveClassRefactorings = 0;
    public int moveOperationRefactorings = 0;
    public int moveSourceFolderRefactorings = 0;
    public int renameClassRefactorings = 0;
    public int renameOperationRefactorings = 0;
    public int renamePackageRefactorings = 0;
    public int extractClassRefactorings = 0;
    public int extractVariableRefactorings = 0;
    public int inlineVariableRefactorings = 0;
    public int moveAndRenameClassRefactorings = 0;
    public int renameAttributeRefactorings = 0;
    public int renameVariableRefactorings = 0;

    @Override
    public void visitConvertAnonymousClassToType(ConvertAnonymousClassToTypeRefactoring refactoring) {
        anonymousClassToTypeRefactorings++;
        refactoredClasses.add(refactoring.getAddedClass().getName());

    }

    @Override
    public void visitExtractAndMoveOperation(ExtractAndMoveOperationRefactoring refactoring) {
        extractAndMoveOperationRefactorings++;
        refactoredClasses.add(refactoring.getSourceOperationBeforeExtraction().getClassName());
        refactoredClasses.add(refactoring.getExtractedOperation().getClassName());
    }

    @Override
    public void visitExtractOperation(ExtractOperationRefactoring refactoring) {
        extractOperationRefactorings++;
        refactoredClasses.add(refactoring.getSourceOperationBeforeExtraction().getClassName());
    }

    @Override
    public void visitExtractSuperclass(ExtractSuperclassRefactoring refactoring) {
        extractSuperclassRefactorings++;
        refactoredClasses.add(refactoring.getExtractedClass().getName());
        refactoredClasses.addAll(refactoring.getSubclassSet());
    }

    @Override
    public void visitInlineOperation(InlineOperationRefactoring refactoring) {
        inlineOperationRefactorings++;
        refactoredClasses.add(refactoring.getTargetOperationAfterInline().getClassName());
    }

    @Override
    public void visitMoveAttribute(MoveAttributeRefactoring refactoring) {
        moveAttributeRefactorings++;
        refactoredClasses.add(refactoring.getSourceClassName());
        refactoredClasses.add(refactoring.getTargetClassName());
    }

    @Override
    public void visitMoveClass(MoveClassRefactoring refactoring) {
        moveClassRefactorings++;
        refactoredClasses.add(refactoring.getMovedClassName());
    }

    @Override
    public void visitMoveOperation(MoveOperationRefactoring refactoring) {
        moveOperationRefactorings++;
        refactoredClasses.add(refactoring.getOriginalOperation().getClassName());
        refactoredClasses.add(refactoring.getMovedOperation().getClassName());
    }

    @Override
    public void visitMoveSourceFolder(MoveSourceFolderRefactoring refactoring) {
        moveSourceFolderRefactorings++;
    }

    @Override
    public void visitRenameClass(RenameClassRefactoring refactoring) {
        renameClassRefactorings++;
    }

    @Override
    public void visitRenameOperation(RenameOperationRefactoring refactoring) {
        renameOperationRefactorings++;

    }

    @Override
    public void visitRenamePackage(RenamePackageRefactoring refactoring) {
        renamePackageRefactorings++;
    }

    @Override
    public void visitExtractClass(ExtractClassRefactoring extractClassRefactoring) {
        extractClassRefactorings++;
        refactoredClasses.add(extractClassRefactoring.getExtractedClass().getName());
        refactoredClasses.add(extractClassRefactoring.getOriginalClass().getName());
    }

    @Override
    public void visitExtractVariable(ExtractVariableRefactoring extractVariableRefactoring) {
        extractVariableRefactorings++;
        refactoredClasses.add(extractVariableRefactoring.getOperation().getClassName());
    }

    @Override
    public void visitInlineVariable(InlineVariableRefactoring inlineVariableRefactoring) {
        inlineVariableRefactorings++;
        refactoredClasses.add(inlineVariableRefactoring.getOperation().getClassName());
    }

    @Override
    public void visitMoveAndRenameClass(MoveAndRenameClassRefactoring moveAndRenameClassRefactoring) {
        moveAndRenameClassRefactorings++;
    }

    @Override
    public void visitRenameAttribute(RenameAttributeRefactoring renameAttributeRefactoring) {
        renameAttributeRefactorings++;
    }

    @Override
    public void visitRenameVariable(RenameVariableRefactoring renameVariableRefactoring) {
        renameVariableRefactorings++;
    }

    public Set<String> getRefactoredClasses() {
        return refactoredClasses;
    }

    public int getRefactoringCount(){
        return anonymousClassToTypeRefactorings + extractAndMoveOperationRefactorings
                + extractOperationRefactorings + extractSuperclassRefactorings
                + inlineOperationRefactorings + moveAttributeRefactorings
                + moveClassRefactorings + moveOperationRefactorings
                + moveSourceFolderRefactorings + renameClassRefactorings
                + renameOperationRefactorings + renamePackageRefactorings
                + extractClassRefactorings + extractVariableRefactorings
                + inlineVariableRefactorings + moveAndRenameClassRefactorings
                + renameAttributeRefactorings + renameVariableRefactorings;
    }
}
