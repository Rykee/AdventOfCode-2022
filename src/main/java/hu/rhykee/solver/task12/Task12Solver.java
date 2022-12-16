package hu.rhykee.solver.task12;

import hu.rhykee.solver.Challenge;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Task12Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    Node<Integer> start = null;
    Node<Integer> end = new IntNode(0, new Position(0, 0));
    Map<Position, Node<Integer>> nodes = new HashMap<>();
    for (int i = 0; i < lines.size(); i++) {
      char[] chars = lines.get(i).toCharArray();
      for (int j = 0; j < chars.length; j++) {
        int tempJ = j;
        Position position = new Position(i, j);
        Node<Integer> node = nodes.computeIfAbsent(position, pos -> new IntNode(chars[tempJ] - 97, pos));
        nodes.put(position, node);
        findNeighbours(i, j, lines, nodes, node);
        if (node.getValue() == -14) {
          start = node;
          start.setGCost(0);
          start.setValue(0);
        } else if (node.getValue() == -28) {
          end = node;
          end.setValue(25);
        }
      }
    }
    Position endPosition = end.getPosition();
    nodes.values().forEach(intNode -> {
      Position nodePosition = intNode.getPosition();
      intNode.setHCost(Math.abs(endPosition.i() - nodePosition.i()) + Math.abs(endPosition.j() - nodePosition.j()));
    });
    long startTime = System.currentTimeMillis();
    System.out.println(findBestRoute(start, end));
    System.out.println(System.currentTimeMillis()-startTime);
  }

  @Override
  public void part2(List<String> lines) {
    List<Node<Integer>> startingNodes = new ArrayList<>();
    Node<Integer> endNode = new IntNode(0, new Position(0, 0));
    Map<Position, Node<Integer>> nodes = new HashMap<>();
    for (int i = 0; i < lines.size(); i++) {
      char[] chars = lines.get(i).toCharArray();
      for (int j = 0; j < chars.length; j++) {
        int tempJ = j;
        Position position = new Position(i, j);
        Node<Integer> node = nodes.computeIfAbsent(position, pos -> new IntNode(chars[tempJ] - 97, pos));
        nodes.put(position, node);
        findNeighbours(i, j, lines, nodes, node);
        if (node.getValue() == -14) {
          node.setValue(0);
        } else if (node.getValue() == -28) {
          endNode = node;
          endNode.setValue(25);
        }
        if (node.getValue() == 0) {
          startingNodes.add(node);
        }
      }
    }
    Position endPosition = endNode.getPosition();
    nodes.values().forEach(intNode -> {
      Position nodePosition = intNode.getPosition();
      intNode.setHCost(Math.abs(endPosition.i() - nodePosition.i()) + Math.abs(endPosition.j() - nodePosition.j()));
    });
    Node<Integer> finalEndNode = endNode;
    AtomicInteger counter = new AtomicInteger(1);
    long minimum = startingNodes.stream()
        .mapToLong(startNode -> {
          long startTime = System.currentTimeMillis();
          System.out.print(counter.get()+ "/" + startingNodes.size()+": ");
          startNode.setGCost(0);
          long routeLength = findBestRoute(startNode, finalEndNode);
          nodes.values().forEach(node -> {
            node.setGCost(Integer.MAX_VALUE);
            node.setParent(null);
          });
          System.out.println(System.currentTimeMillis()-startTime);
          counter.addAndGet(1);
          return routeLength;
        })
        .min()
        .getAsLong();
    System.out.println(minimum);
  }

  private void findNeighbours(int i, int j, List<String> lines, Map<Position, Node<Integer>> nodes, Node<Integer> node) {
    if (i != 0) {
      node.getNeighbours().add(getNode(i - 1, j, lines, nodes));
    }
    if (i != lines.size() - 1) {
      node.getNeighbours().add(getNode(i + 1, j, lines, nodes));
    }
    if (j != 0) {
      node.getNeighbours().add(getNode(i, j - 1, lines, nodes));
    }
    if (j != lines.get(i).length() - 1) {
      node.getNeighbours().add(getNode(i, j + 1, lines, nodes));
    }
  }

  private Node<Integer> getNode(int i, int j, List<String> lines, Map<Position, Node<Integer>> nodes) {
    Position neighbourPos = new Position(i, j);
    return nodes.computeIfAbsent(neighbourPos, pos -> new IntNode(lines.get(i).toCharArray()[j] - 97, pos));
  }

  private long findBestRoute(Node<Integer> start, Node<Integer> end) {
    PriorityQueue<Node<Integer>> openNodes = new PriorityQueue<>(Comparator.comparingLong(Node::getFCost));
    Set<Node<Integer>> openNodesSet = new HashSet<>();
    Set<Node<Integer>> closedNodes = new HashSet<>();
    openNodes.add(start);
    openNodesSet.add(start);
    while (true) {
      Node<Integer> currentNode = openNodes.poll();
      if (currentNode == null) { //not traversable
        return Integer.MAX_VALUE;
      }
      openNodesSet.remove(currentNode);
      closedNodes.add(currentNode);
      if (currentNode.equals(end)) {
        return currentNode.getGCost();
      }
      currentNode.getNeighbours().stream()
          .filter(neighbour -> currentNode.isTraversable(neighbour) && !closedNodes.contains(neighbour))
          .forEach(neighbour -> {
            if (neighbour.getFCost() > currentNode.getFCost() + 1 || !openNodesSet.contains(neighbour)) {
              neighbour.setGCost(currentNode.getGCost() + 1);
              neighbour.setParent(currentNode);
              openNodes.add(neighbour);
              openNodesSet.add(neighbour);
            }
          });
    }
  }

}
