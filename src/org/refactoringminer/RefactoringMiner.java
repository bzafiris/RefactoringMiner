package org.refactoringminer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.log.AbstractCSVLogger;
import org.refactoringminer.log.RefactoringLogger;
import org.refactoringminer.log.RefactoringLoggerFactory;
import org.refactoringminer.rm1.RefactoringMinerFactory;
import org.refactoringminer.util.GitServiceImpl;

import static org.refactoringminer.util.StringUtils.trimWhitespaces;

public class RefactoringMiner {

    public static final String LOGGER_TYPE_AGGREGATE = "-aggregate";
    public static final String LOGGER_TYPE_DEFAULT = "-default";
    public static final String LOGGER_TYPE_DETAILED = "-detailed";

    private static GitHistoryRefactoringMiner detector;
    private static AbstractCSVLogger csvLogger;

    public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			throw argumentException();
		}

		// prepend logger type as extra parameter
        String csvLoggerType = getLoggerType(args);

        if (csvLoggerType != null){
            csvLogger = RefactoringLoggerFactory.getInstance().getCSVLogger(csvLoggerType);
            args = Arrays.copyOfRange(args, 1, args.length);
        } else {
            csvLogger = RefactoringLoggerFactory.getInstance().getDefaultLogger();
        }

        // handle parameters as before
		final String option = args[0];
		if (option.equalsIgnoreCase("-h") || option.equalsIgnoreCase("--h") || option.equalsIgnoreCase("-help")
				|| option.equalsIgnoreCase("--help")) {
			printTips();
			return;
		}

		if (option.equalsIgnoreCase("-a")) {
			detectAll(args);
		} else if (option.equalsIgnoreCase("-bc")) {
			detectBetweenCommits(args);
		} else if (option.equalsIgnoreCase("-bt")) {
			detectBetweenTags(args);
		} else if (option.equalsIgnoreCase("-c")) {
			detectAtCommit(args);
		} else {
			throw argumentException();
		}

	}


	public static String getLoggerType(String[] args){

        if (args.length == 0){
            return null;
        }
        String loggerType = args[0];
        if (LOGGER_TYPE_AGGREGATE.equals(loggerType) ||
                LOGGER_TYPE_DEFAULT.equals(loggerType) ||
                LOGGER_TYPE_DETAILED.equals(loggerType)){
            return loggerType;
        }
        return null;

    }

    private static void prepareCSVLogger(String folder, String branch, String filePath) throws IOException {
        csvLogger.setCsvFilePath(filePath);
        csvLogger.setProjectName(folder);
        csvLogger.setBranch(branch);
        csvLogger.start();
    }

	private static void detectAll(String[] args) throws Exception {
		if (args.length > 3) {
			throw argumentException();
		}
		String folder = args[1];
		String branch = null;
		if (args.length == 3) {
			branch = args[2];
		}
		GitService gitService = new GitServiceImpl();
		try (Repository repo = gitService.openRepository(folder)) {
			Path folderPath = Paths.get(folder);
			String fileName = (branch == null) ? "all_refactorings.csv" : "all_refactorings_" + branch + ".csv";

			String filePath = folderPath.toString() + File.separator + fileName;

            prepareCSVLogger(folder, branch, filePath);

            detector = RefactoringMinerFactory.createProductionCodeGitHistoryMiner();
			detector.detectAll(repo, branch, new RefactoringHandler() {

				@Override
				public void handle(RevCommit commitData, List<Refactoring> refactorings) {

					if (refactorings.isEmpty()) {
						System.out.println("No refactorings found in commit " + commitData.getId());
					} else {
						System.out.println(refactorings.size() + " refactorings found in commit " + commitData.getId());
						csvLogger.log(commitData, refactorings);
					}
				}
				
				@Override
				public void onFinish(int refactoringsCount, int commitsCount, int errorCommitsCount) {
					System.out.println("Finish mining, result is saved to file: " + filePath);
					System.out.println(String.format("Total count: [Commits: %d, Errors: %d, Refactorings: %d]",
							commitsCount, errorCommitsCount, refactoringsCount));
				}

				@Override
				public void handleException(String commit, Exception e) {
					System.err.println("Error processing commit " + commit);
					e.printStackTrace(System.err);
				}
			});
		}
	}



    private static void detectBetweenCommits(String[] args) throws Exception {
		if (!(args.length == 3 || args.length == 4)) {
			throw argumentException();
		}
		String folder = args[1];
		String startCommit = args[2];
		String endCommit = (args.length == 4) ? args[3] : null;
		GitService gitService = new GitServiceImpl();
		try (Repository repo = gitService.openRepository(folder)) {
			Path folderPath = Paths.get(folder);
			String fileName = null;
			if (endCommit == null) {
				fileName = "refactorings_" + startCommit + "_begin" + ".csv";
			} else {
				fileName = "refactorings_" + startCommit + "_" + endCommit + ".csv";
			}
			String filePath = folderPath.toString() + File.separator + fileName;

            prepareCSVLogger(folder, startCommit + ";" + endCommit, filePath);

			detector = RefactoringMinerFactory.createDefaultGitHistoryMiner();
			detector.detectBetweenCommits(repo, startCommit, endCommit, new RefactoringHandler() {

				@Override
				public void handle(RevCommit commitData, List<Refactoring> refactorings) {

					if (refactorings.isEmpty()) {
						System.out.println("No refactorings found in commit " + commitData.getId());
					} else {
						System.out.println(refactorings.size() + " refactorings found in commit " + commitData.getId());
						csvLogger.log(commitData, refactorings);
					}
					
				}
				
				@Override
				public void onFinish(int refactoringsCount, int commitsCount, int errorCommitsCount) {
					System.out.println("Finish mining, result is saved to file: " + filePath);
					System.out.println(String.format("Total count: [Commits: %d, Errors: %d, Refactorings: %d]",
							commitsCount, errorCommitsCount, refactoringsCount));
				}

				@Override
				public void handleException(String commit, Exception e) {
					System.err.println("Error processing commit " + commit);
					e.printStackTrace(System.err);
				}
			});
		}
	}

	private static void detectBetweenTags(String[] args) throws Exception {
		if (!(args.length == 3 || args.length == 4)) {
			throw argumentException();
		}
		String folder = args[1];
		String startTag = args[2];
		String endTag = (args.length == 4) ? args[3] : null;
		GitService gitService = new GitServiceImpl();
		try (Repository repo = gitService.openRepository(folder)) {
			Path folderPath = Paths.get(folder);
			String fileName = null;
			if (endTag == null) {
				fileName = "refactorings_" + startTag + "_begin" + ".csv";
			} else {
				fileName = "refactorings_" + startTag + "_" + endTag + ".csv";
			}
			String filePath = folderPath.toString() + File.separator + fileName;

            prepareCSVLogger(folder, startTag + ";" + endTag, filePath);

			detector = RefactoringMinerFactory.createDefaultGitHistoryMiner();
			detector.detectBetweenTags(repo, startTag, endTag, new RefactoringHandler() {
				
				@Override
				public void handle(RevCommit commitData, List<Refactoring> refactorings) {

					if (refactorings.isEmpty()) {
						System.out.println("No refactorings found in commit " + commitData.getId());
					} else {
						System.out.println(refactorings.size() + " refactorings found in commit " + commitData.getId());
						csvLogger.log(commitData, refactorings);
					}
					
				}

				@Override
				public void onFinish(int refactoringsCount, int commitsCount, int errorCommitsCount) {
					System.out.println("Finish mining, result is saved to file: " + filePath);
					System.out.println(String.format("Total count: [Commits: %d, Errors: %d, Refactorings: %d]",
							commitsCount, errorCommitsCount, refactoringsCount));
				}

				@Override
				public void handleException(String commit, Exception e) {
					System.err.println("Error processing commit " + commit);
					e.printStackTrace(System.err);
				}
			});
		}
	}

	private static void detectAtCommit(String[] args) throws Exception {
		if (args.length != 3) {
			throw argumentException();
		}
		String folder = args[1];
		String commitId = args[2];
		GitService gitService = new GitServiceImpl();
		try (Repository repo = gitService.openRepository(folder)) {

            Path folderPath = Paths.get(folder);
            String fileName = "refactorings_commit_" + commitId + ".csv";

            String filePath = folderPath.toString() + File.separator + fileName;

		    prepareCSVLogger(folder, commitId, filePath);

			detector = RefactoringMinerFactory.createDefaultGitHistoryMiner();
			detector.detectAtCommit(repo, null, commitId, new RefactoringHandler() {
				@Override
				public void handle(String commitId, List<Refactoring> refactorings) {
					if (refactorings.isEmpty()) {
						System.out.println("No refactorings found in commit " + commitId);
					} else {
						System.out.println(refactorings.size() + " refactorings found in commit " + commitId + ": ");
						for (Refactoring ref : refactorings) {
							System.out.println("  " + ref);
						}
					}
				}

                @Override
                public void handle(RevCommit commitData, List<Refactoring> refactorings) {
                    if (!refactorings.isEmpty()) {
                        csvLogger.log(commitData, refactorings);
                    }
                }

                @Override
				public void handleException(String commit, Exception e) {
					System.err.println("Error processing commit " + commit);
					e.printStackTrace(System.err);
				}
			});
		}
	}

	private static void printTips() {
		System.out.println("-h\t\t\t\t\t\t\t\t\tShow tips");
		System.out.println(
				"<logger-type> -a <git-repo-folder> <branch>\t\t\t\t\tDetect all refactorings at <branch> for <git-repo-folder>. If <branch> is not specified, commits from all branches are analyzed.");
		System.out.println(
				"<logger-type> -bc <git-repo-folder> <start-commit-sha1> <end-commit-sha1>\tDetect refactorings Between <star-commit-sha1> and <end-commit-sha1> for project <git-repo-folder>");
		System.out.println(
				"<logger-type> -bt <git-repo-folder> <start-tag> <end-tag>\t\t\tDetect refactorings Between <start-tag> and <end-tag> for project <git-repo-folder>");
		System.out.println(
				"<logger-type> -c <git-repo-folder> <commit-sha1>\t\t\t\tDetect refactorings at specified commit <commit-sha1> for project <git-repo-folder>");
		System.out.println("Logger types:");
        System.out.println("-default or no <loggerType>: Logs [CommitId;RefactoringType;RefactoringDetail]");
        System.out.println("-detailed: Logs [Project;Branch;CommitId;RefactoringType;RefactoringDetail;Author;Date;GitComment]");
        System.out.println("-aggregate: Aggregates refactorings at revision [Project;Branch;CommitId;RefactoringCount;" +
                "AnonymousClassToType;ExtractMoveMethod;ExtractMethod;ExtractSperclass;InlineOperation;MoveAttribute;MoveClass;MoveOperation;Author;Date;GitComment]");
	}

	private static IllegalArgumentException argumentException() {
		return new IllegalArgumentException("Type `RefactoringMiner -h` to show usage.");
	}

}
