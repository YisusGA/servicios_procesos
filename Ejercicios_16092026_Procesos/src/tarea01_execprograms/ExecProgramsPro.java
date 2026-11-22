package tarea01_execprograms;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ExecProgramsPro {
    static void main(String[] args) {
        for (String arg : args) {
            ProcessBuilder pb = new ProcessBuilder(arg);
            try {
                Process p = pb.start();

                ProcessHandle.Info info = p.info();
                info.command().ifPresent(x -> System.out.println("Nombre app: " + Arrays.stream(x.split("\\\\")).toList().getLast()));
                System.out.println("PID: " + p.pid());
                System.out.println("Momento de inicio: " + info.startInstant().orElseThrow(() -> new NoSuchElementException("No se inició el proceso")));

                p.waitFor(3, TimeUnit.SECONDS);
                p.descendants().forEach(ProcessHandle::destroy);
                p.destroy();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
