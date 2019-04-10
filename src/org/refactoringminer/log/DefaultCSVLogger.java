package org.refactoringminer.log;

import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.util.List;

public class DefaultCSVLogger extends AbstractCSVLogger {
    @Override
    protected String getHeader() {
        return "CommitId;RefactoringType;RefactoringDetail";
    }

    @Override
    public void log(RevCommit commitData, List<Refactoring> refactorings) {

        String commitId = commitData.getId().getName();
        for(Refactoring ref: refactorings){
            writeToFile(getResultRefactoringDescription(commitId, ref));
        }
    }

    private static String getResultRefactoringDescription(String commitId, Refactoring ref) {
        StringBuilder builder = new StringBuilder();
        builder.append(commitId);
        builder.append(";");
        builder.append(ref.getName());
        builder.append(";");
        builder.append(ref);
        return builder.toString();
    }


}
