package org.refactoringminer.api;

import gr.uom.java.xmi.diff.*;

public interface RefactoringVisitor {

    void visitConvertAnonymousClassToType(ConvertAnonymousClassToTypeRefactoring refactoring);

    void visitExtractAndMoveOperation(ExtractAndMoveOperationRefactoring refactoring);

    void visitExtractOperation(ExtractOperationRefactoring refactoring);

    void visitExtractSuperclass(ExtractSuperclassRefactoring refactoring);

    void visitInlineOperation(InlineOperationRefactoring refactoring);

    void visitMoveAttribute(MoveAttributeRefactoring refactoring);

    void visitMoveClass(MoveClassRefactoring refactoring);

    void visitMoveOperation(MoveOperationRefactoring refactoring);

    void visitMoveSourceFolder(MoveSourceFolderRefactoring refactoring);

    void visitRenameClass(RenameClassRefactoring refactoring);

    void visitRenameOperation(RenameOperationRefactoring refactoring);

    void visitRenamePackage(RenamePackageRefactoring refactoring);

    void visitExtractClass(ExtractClassRefactoring extractClassRefactoring);

    void visitExtractVariable(ExtractVariableRefactoring extractVariableRefactoring);

    void visitInlineVariable(InlineVariableRefactoring inlineVariableRefactoring);

    void visitMoveAndRenameClass(MoveAndRenameClassRefactoring moveAndRenameClassRefactoring);

    void visitRenameAttribute(RenameAttributeRefactoring renameAttributeRefactoring);

    void visitRenameVariable(RenameVariableRefactoring renameVariableRefactoring);

}

