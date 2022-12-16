package hu.rhykee.solver.task11;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@Setter
class Monkey {

  protected final int id;
  protected Function<Long, Long> operation;
  protected final Predicate<Long> predicate;
  protected final int targetIfTrue;
  protected final int targetIfFalse;
  protected final List<Long> items;
  protected long inspectionCount = 0;

  public Monkey(int id, List<Long> items, Function<Long, Long> operation, Predicate<Long> predicate, Integer targetIfTrue, Integer targetIfFalse) {
    this.id = id;
    this.items = items;
    this.operation = operation;
    this.predicate = predicate;
    this.targetIfTrue = targetIfTrue;
    this.targetIfFalse = targetIfFalse;
  }

  public void doRound(Map<Integer, Monkey> monkeys, Function<Long, Long> reducer) {
    for (Long itemValue : items) {
      long afterInspection = reducer.apply(applyOperation(itemValue));
      if (predicate.test(afterInspection)) {
        monkeys.get(targetIfTrue).addItem(afterInspection);
      } else {
        monkeys.get(targetIfFalse).addItem(afterInspection);
      }
      inspectionCount++;
    }
    items.clear();
  }

  protected long applyOperation(long itemValue) {
    return operation.apply(itemValue);
  }

  public void addItem(long itemValue) {
    items.add(itemValue);
  }

}
