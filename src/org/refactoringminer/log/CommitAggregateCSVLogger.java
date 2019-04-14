package org.refactoringminer.log;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommitAggregateCSVLogger extends AbstractCSVLogger {

    public static final String CSV_FIELD_SEPARATOR = "^";

    static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected String getHeader() {
        return new StringBuffer()
                .append("Project")
                .append(CSV_FIELD_SEPARATOR)
                .append("Branch")
                .append(CSV_FIELD_SEPARATOR)
                .append("CommitId")
                .append(CSV_FIELD_SEPARATOR)
                .append("RefactoringCount")
                .append(CSV_FIELD_SEPARATOR)
                .append("AnonymouClassToType")
                .append(CSV_FIELD_SEPARATOR)
                .append("ExtractMoveMethod")
                .append(CSV_FIELD_SEPARATOR)
                .append("ExtractMethod")
                .append(CSV_FIELD_SEPARATOR)
                .append("ExtractSuperclass")
                .append(CSV_FIELD_SEPARATOR)
                .append("InlineOperation")
                .append(CSV_FIELD_SEPARATOR)
                .append("MoveAttribute")
                .append(CSV_FIELD_SEPARATOR)
                .append("MoveClass")
                .append(CSV_FIELD_SEPARATOR)
                .append("MoveOperation")
                .append(CSV_FIELD_SEPARATOR)
                .append("MoveSourceFolder")
                .append(CSV_FIELD_SEPARATOR)
                .append("RenameClass")
                .append(CSV_FIELD_SEPARATOR)
                .append("RenameOperation")
                .append(CSV_FIELD_SEPARATOR)
                .append("RenamePackage")
                .append(CSV_FIELD_SEPARATOR)
                .append("ExtractClass")
                .append(CSV_FIELD_SEPARATOR)
                .append("ExtractVariable")
                .append(CSV_FIELD_SEPARATOR)
                .append("InlineVariable")
                .append(CSV_FIELD_SEPARATOR)
                .append("MoveAndRenameClass")
                .append(CSV_FIELD_SEPARATOR)
                .append("RenameAttribute")
                .append(CSV_FIELD_SEPARATOR)
                .append("RenameVariable")
                .append(CSV_FIELD_SEPARATOR)
                .append("Author")
                .append(CSV_FIELD_SEPARATOR)
                .append("Date")
                .append(CSV_FIELD_SEPARATOR)
                .append("RefactoredClasses")
                .toString();
    }

    @Override
    public void log(RevCommit commitData, List<Refactoring> refactorings) {

        RefactoringStatsCollector statsCollector = new RefactoringStatsCollector();
        for (Refactoring refactoring : refactorings) {
            refactoring.accept(statsCollector);
        }

        writeToFile(getResultRefactoringDescription(commitData, statsCollector));

    }

    private String getResultRefactoringDescription(RevCommit currentCommit, RefactoringStatsCollector ref) {

        StringBuilder builder = new StringBuilder();
        builder.append(projectName);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(branch);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(currentCommit.getId().getName());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.getRefactoringCount());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.anonymousClassToTypeRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractAndMoveOperationRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractOperationRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractSuperclassRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.inlineOperationRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveAttributeRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveClassRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveOperationRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveSourceFolderRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.renameClassRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.renameOperationRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.renamePackageRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractClassRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractVariableRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.inlineVariableRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveAndRenameClassRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.renameAttributeRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.renameVariableRefactorings);
        builder.append(CSV_FIELD_SEPARATOR);
        PersonIdent authorIdent = currentCommit.getAuthorIdent();
        builder.append(authorIdent.getName()).append(CSV_FIELD_SEPARATOR);
        Date commitDate = authorIdent.getWhen();
        builder.append(format.format(commitDate));
        builder.append(CSV_FIELD_SEPARATOR);
        String refactoredClasses = String.join(";", ref.getRefactoredClasses());
        builder.append(refactoredClasses.isEmpty() ? "-" : refactoredClasses);

        return builder.toString();
    }

}
