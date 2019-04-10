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

    void visitExtractClassOperation(ExtractClassRefactoring extractClassRefactoring);

    void visitExtractVariableOperation(ExtractVariableRefactoring extractVariableRefactoring);

    void visitInlineVariableOperation(InlineVariableRefactoring inlineVariableRefactoring);

    void visitMoveAndRenameClassOperation(MoveAndRenameClassRefactoring moveAndRenameClassRefactoring);

    void visitRenameAttributeOperation(RenameAttributeRefactoring renameAttributeRefactoring);

    void visitRenameVariableOperation(RenameVariableRefactoring renameVariableRefactoring);

}

