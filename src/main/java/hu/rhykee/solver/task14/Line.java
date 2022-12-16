package hu.rhykee.solver.task14;


import lombok.Setter;

import java.util.Objects;

@Setter
public class Line {
  private Coordinate startPoint;
  private Coordinate endPoint;

  public Line(Coordinate startPoint, Coordinate endPoint) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
  }

  public Coordinate startPoint() {
    return startPoint;
  }

  public Coordinate endPoint() {
    return endPoint;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Line) obj;
    return Objects.equals(this.startPoint, that.startPoint) &&
        Objects.equals(this.endPoint, that.endPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startPoint, endPoint);
  }

  @Override
  public String toString() {
    return "Line[" +
        "startPoint=" + startPoint + ", " +
        "endPoint=" + endPoint + ']';
  }


}
