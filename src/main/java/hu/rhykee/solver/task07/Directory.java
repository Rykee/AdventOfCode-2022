package hu.rhykee.solver.task07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory {

  private final Directory parent;
  private final Map<String, Directory> subDirectories = new HashMap<>();
  private final List<File> files = new ArrayList<>();
  private final String name;

  public Directory(Directory parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public long getSize() {
    long filesInDir = files.stream()
        .mapToLong(File::size)
        .sum();
    return filesInDir + subDirectories.values().stream()
        .mapToLong(Directory::getSize)
        .sum();
  }

  public Directory getParent() {
    return parent;
  }

  public Map<String, Directory> getSubDirectories() {
    return subDirectories;
  }

  public List<File> getFiles() {
    return files;
  }

  public String getName() {
    return name;
  }
}
