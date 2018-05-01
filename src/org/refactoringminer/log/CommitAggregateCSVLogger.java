package org.refactoringminer.log;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.refactoringminer.util.StringUtils.trimWhitespaces;

public class CommitAggregateCSVLogger extends AbstractCSVLogger {

    public static final String CSV_FIELD_SEPARATOR = "|||";

    static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected String getHeader() {
        return new StringBuffer()
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
                .append("Author")
                .append(CSV_FIELD_SEPARATOR)
                .append("Date")
                .append(CSV_FIELD_SEPARATOR)
                .append("RefactoredClasses")
                .append(CSV_FIELD_SEPARATOR)
                .append("GitComment")
                .toString();
    }

    @Override
    public void log(RevCommit commitData, List<Refactoring> refactorings) {

        RefactoringStatsCollector statsCollector = new RefactoringStatsCollector();
        for(Refactoring refactoring: refactorings){
            refactoring.accept(statsCollector);
        }

        writeToFile(getResultRefactoringDescription(commitData, statsCollector));

    }

    private static String getResultRefactoringDescription(RevCommit currentCommit, RefactoringStatsCollector ref) {

        StringBuilder builder = new StringBuilder();
        builder.append(currentCommit.getId().getName());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.getRefactoringCount());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.anonymousClassToTypeCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractAndMoveOperationCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractOperationCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.extractSuperclassCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.inlineOperationCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveAttributeCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveClassCount);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.moveOperationCount);
        builder.append(CSV_FIELD_SEPARATOR);
        PersonIdent authorIdent = currentCommit.getAuthorIdent();
        builder.append(authorIdent.getName()).append(CSV_FIELD_SEPARATOR);
        Date commitDate = authorIdent.getWhen();
        builder.append(format.format(commitDate));
        builder.append(CSV_FIELD_SEPARATOR);
        String refactoredClasses = String.join(";", ref.getRefactoredClasses());
        builder.append(refactoredClasses.isEmpty() ? "-" : refactoredClasses);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(trimWhitespaces(currentCommit.getFullMessage()));

        return builder.toString();
    }

}
