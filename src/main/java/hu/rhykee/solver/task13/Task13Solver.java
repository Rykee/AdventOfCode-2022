package hu.rhykee.solver.task13;

import hu.rhykee.solver.Challenge;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task13Solver implements Challenge {

  private static final Pattern NUMBER_PATTERN = Pattern.compile("(?<number>\\d+),?");

  @Override
  public void part1(List<String> lines) {
    String fullInput = String.join("\n", lines);
    String[] packets = fullInput.split("\n\n");
    int sum = 0;
    for (int i = 0; i < packets.length; i++) {
      String packet = packets[i];
      String[] parts = packet.split("\n");
      PacketPair packetPair = new PacketPair();
      parsePacket(parts[0].substring(1, parts[0].length() - 1), packetPair.getFirst());
      parsePacket(parts[1].substring(1, parts[1].length() - 1), packetPair.getSecond());
      System.out.println(" ");
    }
  }

  @Override
  public void part2(List<String> lines) {

  }

  private void parsePacket(String part, ListPacket packet) {
    int newListStart = part.indexOf('[');
    String numberPart = part.substring(0, newListStart == -1 ? part.length() : newListStart);
    Matcher matcher = NUMBER_PATTERN.matcher(numberPart);
    while (matcher.find()) {
      packet.getListParts().add(new IntPacket(Integer.parseInt(matcher.group("number"))));
    }
    if (newListStart != -1) {
      ListPacket subPacket = new ListPacket();
      packet.getListParts().add(subPacket);
      int subPacketCloseIndex = getSubPacketCloseIndex(part, newListStart);
      parsePacket(part.substring(newListStart + 1, subPacketCloseIndex), subPacket);
      if (subPacketCloseIndex + 2 <= part.length() - 1) {
        parsePacket(part.substring(subPacketCloseIndex + 2), packet);
      }
    }
  }

  private int getSubPacketCloseIndex(String withoutBrackets, int newListStart) {
    int countOpen = 1;
    int countClose = 0;
    int indexOfClose = 0;
    for (int i = newListStart + 1; i < withoutBrackets.length(); i++) {
      if (withoutBrackets.charAt(i) == '[') {
        countOpen++;
      } else if (withoutBrackets.charAt(i) == ']') {
        countClose++;
      }
      if (countClose == countOpen) {
        indexOfClose = i;
        break;
      }
    }
    return indexOfClose;
  }

  @Getter
  private static class PacketPair {

    private final ListPacket first = new ListPacket();
    private final ListPacket second = new ListPacket();

    public boolean isValid() {
      boolean valid = true;
      for (int i = 0; i < first.getListParts().size(); i++) {
        valid = valid && first.getListParts().get(i).isValid(second.getListParts().get(i));
        if (!valid) {
          return valid;
        }
      }
      return valid;
    }

  }

  private static abstract class PacketPart<T> {

    public abstract boolean isValid(PacketPart<?> packetPart);
  }

  @Getter
  private static class IntPacket extends PacketPart<Integer> {

    private final int value;

    public IntPacket(int value) {
      this.value = value;
    }

    @Override
    public boolean isValid(PacketPart<?> packetPart) {
      switch()
    }
  }

  @Getter
  private static class ListPacket extends PacketPart<List<PacketPart<?>>> {

    private final List<PacketPart<?>> listParts = new ArrayList<>();

  }


}
