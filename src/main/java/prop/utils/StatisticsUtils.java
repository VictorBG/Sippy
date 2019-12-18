package prop.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Author: Sergio Vazquez
 */
public class StatisticsUtils {

  private static final HashMap<TimeUnit, String> TIME_UNITS = new HashMap<TimeUnit, String>() {{
    put(TimeUnit.SECONDS, "s");
    put(TimeUnit.MILLISECONDS, "ms");
    put(TimeUnit.MICROSECONDS, "us");
    put(TimeUnit.NANOSECONDS, "ns");
  }};

  public static String getTime(double time) {
    if (time >= 1) {
      return internalGetTime(time, TimeUnit.SECONDS);
    }

    if (time * 1000.0 >= 1) {
      return internalGetTime(time, TimeUnit.MILLISECONDS);
    }

    if (time * 1000000.0 >= 1) {
      return internalGetTime(time, TimeUnit.MICROSECONDS);
    }

    return internalGetTime(time, TimeUnit.NANOSECONDS);
  }

  private static String internalGetTime(double time, TimeUnit timeUnit) {
    return new DecimalFormat("#.##")
        .format(timeUnit.convert(1, TimeUnit.SECONDS) * time)
        + " "
        + TIME_UNITS.get(timeUnit);
  }

  public static String getSize(double size) {
    if (size <= 0) { return "0 B"; }
    final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
    int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups))
        + " "
        + units[digitGroups];
  }
}
