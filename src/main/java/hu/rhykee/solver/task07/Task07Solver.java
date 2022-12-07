package hu.rhykee.solver.task07;

import hu.rhykee.solver.Challenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task07Solver implements Challenge {

  private static final Pattern FILE_PATTERN = Pattern.compile("(?<size>\\d+) (?<name>.*)");
  private static final Pattern DIRECTORY_PATTERN = Pattern.compile("dir (?<name>.*)");
  private static final Pattern CHANGE_DIR_PATTERN = Pattern.compile("\\$ cd (?<target>.*)");

  @Override
  public void part1(List<String> lines) {
    Directory root = parseInput(lines);
    System.out.println(getSumOfDirsBelowLimit(Collections.singletonList(root), 100_000L));
  }

  @Override
  public void part2(List<String> lines) {
    Directory root = parseInput(lines);
    long requiredSpace = 30_000_000 - (70_000_000 - root.getSize());
    List<Long> sizes = new ArrayList<>();
    addSizes(root, sizes);
    long currentDelta = Long.MAX_VALUE;
    long chosenDirSize = 0L;
    sizes = sizes.stream()
        .filter(dirSize -> dirSize >= requiredSpace)
        .toList();
    for (Long size : sizes) {
      if (size-requiredSpace < currentDelta){
        currentDelta = size-requiredSpace;
        chosenDirSize = size;
      }
    }
    System.out.println(chosenDirSize);
  }

  private void addSizes(Directory dir, List<Long> sizes) {
    sizes.add(dir.getSize());
    dir.getSubDirectories().values().forEach(directory -> addSizes(directory, sizes));
  }

  private Directory parseInput(List<String> lines) {
    String[] terminalLines = lines.toArray(new String[0]);
    Directory root = new Directory(null, "/");
    Directory currentDirectory = root;
    int rowNumber = 1;
    while (rowNumber < terminalLines.length) {
      String currentRow = terminalLines[rowNumber];
      if (isCommand(currentRow)) {
        if (isListCommand(currentRow)) {
          rowNumber++;
          while (rowNumber < terminalLines.length && !terminalLines[rowNumber].startsWith("$")) {
            String current = terminalLines[rowNumber];
            if (isFile(current)) {
              Matcher matcher = FILE_PATTERN.matcher(current);
              matcher.find();
              currentDirectory.getFiles().add(new File(matcher.group("name"), Long.parseLong(matcher.group("size"))));
            } else {
              Matcher matcher = DIRECTORY_PATTERN.matcher(current);
              matcher.find();
              currentDirectory.getSubDirectories().put(matcher.group("name"), new Directory(currentDirectory, matcher.group("name")));
            }
            rowNumber++;
          }
        } else if (isChangeDirCommand(currentRow)) {
          Matcher matcher = CHANGE_DIR_PATTERN.matcher(currentRow);
          matcher.find();
          String targetDir = matcher.group("target");
          if (targetDir.equals("..")) {
            currentDirectory = currentDirectory.getParent();
          } else {
            currentDirectory = currentDirectory.getSubDirectories().get(targetDir);
          }
          rowNumber++;
        }
      }
    }
    return root;
  }

  private boolean isCommand(String currentRow) {
    return currentRow.startsWith("$");
  }

  private boolean isListCommand(String command) {
    return command.equals("$ ls");
  }

  private boolean isFile(String current) {
    return FILE_PATTERN.matcher(current).matches();
  }

  private boolean isChangeDirCommand(String currentRow) {
    return currentRow.startsWith("$ cd ");
  }

  private long getSumOfDirsBelowLimit(Collection<Directory> directories, long limit) {
    return directories.stream()
        .filter(directory -> directory.getSize() <= limit)
        .mapToLong(Directory::getSize)
        .sum() +
        directories.stream()
            .map(Directory::getSubDirectories)
            .map(Map::values)
            .mapToLong(subDirs -> getSumOfDirsBelowLimit(subDirs, limit))
            .sum();
  }

}
