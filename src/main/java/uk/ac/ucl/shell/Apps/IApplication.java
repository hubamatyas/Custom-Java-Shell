package uk.ac.ucl.shell.Apps;

import java.io.IOException;

/**
 * Interface to define Unix Shell Applications. For example:
 * <ul>
 *     <li>{@code ls}</li>
 *     <li>{@code pwd}</li>
 *     <li>{@code cat}</li>
 *     <li>{@code grep}</li>
 * </ul>
 */
public interface IApplication {
    /**
     * Executes Unix Shell application
     *
     * @throws IOException if an IO exception occurs while running the application
     */
    void exec() throws IOException;
}
