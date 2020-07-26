import org.jetbrains.annotations.NotNull;

public class Arithmetic {
    public enum NumberType {
        BYTE, SHORT, INTEGER, LONG, DOUBLE, INVALID_NUMBER
    }
    public static Number add(@NotNull Number n0, @NotNull Number n1) {
        if(isDouble(n0) || isDouble(n1)) {
            return castToDouble(n0) + castToDouble(n1); //doubles must be cast to doubles always
        }
        return performAutoCast(highestPrecedence(n0, n1), castToLong(n0) + castToLong(n1)); //pretty much automatically casts the highest precedence
    }
    public static Number sub(@NotNull Number n0, @NotNull Number n1) {
        if(isDouble(n0) || isDouble(n1)) {
            return castToDouble(n0) - castToDouble(n1); //doubles must be cast to doubles always
        }
        return performAutoCast(highestPrecedence(n0, n1), castToLong(n0) - castToLong(n1)); //pretty much automatically casts the highest precedence
    }
    public static Number mul(@NotNull Number n0, @NotNull Number n1) {
        if(isDouble(n0) || isDouble(n1)) {
            return castToDouble(n0) * castToDouble(n1); //doubles must be cast to doubles always
        }
        return performAutoCast(highestPrecedence(n0, n1), castToLong(n0) * castToLong(n1)); //pretty much automatically casts the highest precedence
    }
    public static Number div(@NotNull Number n0, @NotNull Number n1) {
        if(isDouble(n0) || isDouble(n1)) {
            return castToDouble(n0) / castToDouble(n1); //doubles must be cast to doubles always
        }
        return performAutoCast(highestPrecedence(n0, n1), castToLong(n0) / castToLong(n1)); //pretty much automatically casts the highest precedence
    }
    public static Number rem(@NotNull Number n0, @NotNull Number n1) {
        if(isDouble(n0) || isDouble(n1)) {
            return castToDouble(n0) % castToDouble(n1); //doubles must be cast to doubles always
        }
        return performAutoCast(highestPrecedence(n0, n1), castToLong(n0) % castToLong(n1)); //pretty much automatically casts the highest precedence
    }
    public static Number sin(@NotNull Number n) { return Math.sin(castToDouble(n)); }
    public static Number cos(@NotNull Number n) { return Math.cos(castToDouble(n)); }
    public static Number tan(@NotNull Number n) { return Math.tan(castToDouble(n)); }

    public static Number csc(@NotNull Number n) { return 1/Math.sin(castToDouble(n)); }
    public static Number sec(@NotNull Number n) { return 1/Math.cos(castToDouble(n)); }
    public static Number cot(@NotNull Number n) { return 1/Math.tan(castToDouble(n)); }

    public static @NotNull Byte castToByte(@NotNull Number n) {
        if (n instanceof Byte) {
            return n.byteValue();
        } else if (n instanceof Short) {
            return (byte) n.shortValue();
        } else if (n instanceof Integer) {
            return (byte) n.intValue();
        } else if (n instanceof Long) {
            return (byte) n.longValue();
        } else if (n instanceof Double) {
            return (byte) n.doubleValue();
        } else {
            return 0;
        }
    }

    public static @NotNull Short castToShort(@NotNull Number n) {
        if (n instanceof Byte) {
            return (short) n.byteValue();
        } else if (n instanceof Short) {
            return n.shortValue();
        } else if (n instanceof Integer) {
            return (short) n.intValue();
        } else if (n instanceof Long) {
            return (short) n.longValue();
        } else if (n instanceof Double) {
            return (short) n.doubleValue();
        } else {
            return 0;
        }
    }

    public static @NotNull Integer castToInteger(@NotNull Number n) {
        if (n instanceof Byte) {
            return (int) n.byteValue();
        } else if (n instanceof Short) {
            return (int) n.shortValue();
        } else if (n instanceof Integer) {
            return n.intValue();
        } else if (n instanceof Long) {
            return (int) n.longValue();
        } else if (n instanceof Double) {
            return (int) n.doubleValue();
        } else {
            return 0;
        }
    }

    public static @NotNull Long castToLong(@NotNull Number n) {
        if (n instanceof Byte) {
            return (long) n.byteValue();
        } else if (n instanceof Short) {
            return (long) n.shortValue();
        } else if (n instanceof Integer) {
            return (long) n.intValue();
        } else if (n instanceof Long) {
            return n.longValue();
        } else if (n instanceof Double) {
            return (long) n.doubleValue();
        } else {
            return 0L;
        }
    }

    public static @NotNull Double castToDouble(@NotNull Number n) {
        if (n instanceof Byte) {
            return (double) n.byteValue();
        } else if (n instanceof Short) {
            return (double) n.shortValue();
        } else if (n instanceof Integer) {
            return (double) n.intValue();
        } else if (n instanceof Long) {
            return (double) n.longValue();
        } else if (n instanceof Double) {
            return n.doubleValue();
        } else {
            return 0.0;
        }
    }

    public static boolean isNumber(Object o) { return o instanceof Number; }
    public static boolean isDouble(Number n) { return n instanceof Double; }
    public static boolean isLong(Number n) { return n instanceof Long; }
    public static boolean isInteger(Number n) { return n instanceof Integer; }
    public static boolean isShort(Number n) { return n instanceof Short; }
    public static boolean isByte(Number n) { return n instanceof Byte; }

    public static NumberType getType(Number n) {
        if(n instanceof Byte) {
            return NumberType.BYTE;
        }
        if(n instanceof Short) {
            return NumberType.SHORT;
        }
        if(n instanceof Integer) {
            return NumberType.INTEGER;
        }
        if(n instanceof Long) {
            return NumberType.LONG;
        }
        if(n instanceof Double) {
            return NumberType.DOUBLE;
        }
        return NumberType.INVALID_NUMBER;
    }

    public static int getPrecedence(NumberType type) {
        return switch (type) {
            case BYTE    -> 0;
            case SHORT   -> 1;
            case INTEGER -> 2;
            case LONG    -> 3;
            case DOUBLE  -> 4;
            case INVALID_NUMBER -> 5;
        };
    }

    public static Number performAutoCast(@NotNull NumberType type, @NotNull Number number) {
        return switch(type) {
            case BYTE    -> castToByte(number);
            case SHORT   -> castToShort(number);
            case INTEGER -> castToInteger(number);
            case LONG    -> castToLong(number);
            case DOUBLE  -> castToDouble(number);
            case INVALID_NUMBER -> Double.NaN;
        };
    }

    public static NumberType highestPrecedence(@NotNull Number n0, @NotNull Number n1) {
        NumberType t0 = getType(n0);
        NumberType t1 = getType(n1);
        return (getPrecedence(t0) > getPrecedence(t1)) ? t0 : t1;
    }
}