package hu.rhykee.util;

import hu.rhykee.solver.Challenge;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReflectionUtils {

    public static Challenge getSolverByDay(int day) {
        try {
            String dayName = day < 10 ? "0" + day : Integer.toString(day);
            Class<? extends Challenge> solverClass = Class.forName("hu.rhykee.solver.task" + dayName + "." + "Task" + dayName + "Solver").asSubclass(Challenge.class);
            Constructor<? extends Challenge> constructor = solverClass.getConstructor();
            return constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
