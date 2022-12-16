package hu.rhykee.solver.task12;

public class IntNode extends Node<Integer> {

  public IntNode(Integer value, Position position) {
    super(value, position);
  }

  @Override
  protected boolean isTraversable(Node<Integer> other) {
    return other.value <= this.value + 1;
  }

}
