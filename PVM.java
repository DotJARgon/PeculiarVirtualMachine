import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class PVM { //peculiar virtual machine

    private enum Opcode {
        PSH,
        POP,

        POPTEMP, //pops stack to temp stack
        POPSTACK,//pops temp stack to stack

        CASTTOBYTE,
        CASTTOSHORT,
        CASTTOINTEGER,
        CASTTOLONG,
        CASTTODOUBLE,

        DUPE, //duplicate top of stack
        SWAP, //swap top two

        ADD,
        SUB,
        MUL,
        DIV,
        REM,

        EOF
    }

    private final Object[] bytecode; //Opcode, number types :)
    private final InstMethod[]   instruct;
    private int ptr;
    private Stack<Number> stack, temp;

    public PVM() {
        ptr = 0;
        bytecode = new Object[2048]; //we have 2kb here
        instruct = new InstMethod[2048];
        stack = new Stack<>();
        temp = new Stack<>();
    }

    public void setInstructions(@NotNull Object[] obj) {
        for(int i = 0; i < obj.length; i++) {
            bytecode[i] = obj[i];
            if(obj[i] instanceof Opcode) {
                instruct[i] = getInstructionMethod((Opcode) obj[i]);
            }
        }
    }

    public InstMethod getInstructionMethod(@NotNull Opcode op) {
        return switch (op) {
            case PSH -> (numbers) -> {for(Number n : numbers) {stack.push(n);} return true;};
            case POP -> (numbers) -> {stack.pop(); return true;};

            case POPTEMP -> (numbers) -> {temp.push(stack.pop()); return true;};
            case POPSTACK -> (numbers) -> {stack.push(temp.pop()); return true;};

            case CASTTOBYTE -> (numbers) -> {stack.push(Arithmetic.castToByte(stack.pop())); return true;};
            case CASTTOSHORT -> (numbers) -> {stack.push(Arithmetic.castToShort(stack.pop())); return true;};
            case CASTTOINTEGER -> (numbers) -> {stack.push(Arithmetic.castToInteger(stack.pop())); return true;};
            case CASTTOLONG -> (numbers) -> {stack.push(Arithmetic.castToLong(stack.pop())); return true;};
            case CASTTODOUBLE -> (numbers) -> {stack.push(Arithmetic.castToDouble(stack.pop())); return true;};

            case DUPE -> (numbers) -> {stack.push(stack.peek()); return true;};
            case SWAP -> (numbers) -> {Number n = stack.pop(); Number m = stack.pop(); stack.push(n); stack.push(m); return true;};

            case ADD -> (numbers) -> {stack.push(Arithmetic.add(stack.pop(), stack.pop())); return true;};
            case SUB -> (numbers) -> {stack.push(Arithmetic.sub(stack.pop(), stack.pop())); return true;};
            case MUL -> (numbers) -> {stack.push(Arithmetic.mul(stack.pop(), stack.pop())); return true;};
            case DIV -> (numbers) -> {stack.push(Arithmetic.div(stack.pop(), stack.pop())); return true;};
            case REM -> (numbers) -> {stack.push(Arithmetic.rem(stack.pop(), stack.pop())); return true;};

            case EOF -> (numbers) -> false;
            default -> (numbers) -> {System.out.println("Unimplemented: " + op); return false;};
        };
    }

    @FunctionalInterface
    public interface InstMethod {
        boolean perform(@NotNull Number[] numbers);
    }

}
