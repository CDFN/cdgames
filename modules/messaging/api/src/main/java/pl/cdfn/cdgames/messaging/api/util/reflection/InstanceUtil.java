package pl.cdfn.cdgames.messaging.api.util.reflection;

import java.lang.reflect.InvocationTargetException;

public class InstanceUtil {

  /**
   * Creates instance of class using it's empty constructor
   *
   * @param clazz class of which instance is needed, has to have empty public constructor
   * @param <T>   type of object to return
   * @return object of <code>clazz</code>
   */
  public static <T> T createInstance(Class<T> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          String.format("class %s has no empty constructor", clazz.toString()),
          e
      );

    } catch (IllegalAccessException e) {
      throw new IllegalStateException(
          String.format("class %s has non-public constructor", clazz.toString())
      );

    } catch (InstantiationException e) {
      throw new IllegalStateException(
          String.format("failed to instantiate class %s", clazz.toString()),
          e
      );

    } catch (InvocationTargetException e) {
      throw new IllegalStateException(
          String.format("class %s threw exception while being instantiated", clazz.toString()),
          e
      );
    }
  }
}
