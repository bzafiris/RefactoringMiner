package org.refactoringminer.log;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.refactoringminer.util.StringUtils.trimWhitespaces;

public class DetailedCSVLogger extends AbstractCSVLogger {

    public static final String CSV_FIELD_SEPARATOR = "|||";

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
                .append("RefactoringType")
                .append(CSV_FIELD_SEPARATOR)
                .append("RefactoringDetail")
                .append(CSV_FIELD_SEPARATOR)
                .append("Author")
                .append(CSV_FIELD_SEPARATOR)
                .append("Date")
                .append(CSV_FIELD_SEPARATOR)
                .append("GitComment")
                .toString();
    }

    @Override
    public void log(RevCommit commitData, List<Refactoring> refactorings) {
        for(Refactoring ref: refactorings){
            writeToFile(getResultRefactoringDescription(commitData, ref));
        }
    }

    private String getResultRefactoringDescription(RevCommit currentCommit, Refactoring ref) {

        StringBuilder builder = new StringBuilder();
        builder.append(projectName);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(branch);
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(currentCommit.getId().getName());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref.getName());
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(ref);
        builder.append(CSV_FIELD_SEPARATOR);
        PersonIdent authorIdent = currentCommit.getAuthorIdent();
        builder.append(authorIdent.getName()).append(CSV_FIELD_SEPARATOR);
        Date commitDate = authorIdent.getWhen();
        builder.append(format.format(commitDate));
        builder.append(CSV_FIELD_SEPARATOR);
        builder.append(trimWhitespaces(currentCommit.getFullMessage()));

        return builder.toString();
    }
}
