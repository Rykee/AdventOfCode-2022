package hu.rhykee.solver.task12;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public abstract class Node<T> {

  protected T value;
  protected final Position position;
  protected final Set<Node<T>> neighbours = new HashSet<>();
  protected Node<T> parent;
  protected long gCost = Integer.MAX_VALUE; //distance from start node (parent node gCost + 1)
  protected long hCost = Integer.MAX_VALUE; //distance from end node (just use Math.abs(end.i-this.i) + Math.abs(end.j-this.j)

  public Node(T value, Position position) {
    this.value = value;
    this.position = position;
  }

  public long getFCost() {
    return gCost + hCost;
  }

  protected abstract boolean isTraversable(Node<T> other);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node<?> node = (Node<?>) o;
    return position.equals(node.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position);
  }
}
