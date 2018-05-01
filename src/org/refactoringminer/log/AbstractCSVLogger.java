package org.refactoringminer.log;

import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class AbstractCSVLogger implements RefactoringLogger {

    private String csvFilePath;
    protected String projectName;
    protected String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    protected abstract String getHeader();

    public void start() throws IOException {
        if (csvFilePath == null) {
            throw new IllegalStateException("CSV Logger file name not set");
        }
        Files.deleteIfExists(Paths.get(csvFilePath));
        writeToFile(getHeader());
    }

    protected void writeToFile(String content) {
        if (content == null) {
            return;
        }
        System.out.println(content);
        Path path = Paths.get(csvFilePath);
        byte[] contentBytes = (content + System.lineSeparator()).getBytes();
        try {
            Files.write(path, contentBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
