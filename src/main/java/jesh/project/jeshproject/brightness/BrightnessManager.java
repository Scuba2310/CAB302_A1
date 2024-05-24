package jesh.project.jeshproject.brightness;

import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BrightnessManager {
    private static void ChangeBrightness(int brightness)
            throws IOException {
        //Creates a powerShell command that will set the brightness to the requested value (0-100), after the requested delay (in milliseconds) has passed.
        String s = String.format("$brightness = %d;", brightness)
                + "$delay = 0;"
                + "$myMonitor = Get-WmiObject -Namespace root\\wmi -Class WmiMonitorBrightnessMethods;"
                + "$myMonitor.wmisetbrightness($delay, $brightness)";
        String command = "powershell.exe  " + s;
        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);

        powerShellProcess.getOutputStream().close();

        //Report any error messages
        String line;

        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        line = stderr.readLine();
        if (line != null)
        {
            System.err.println("Standard Error:");
            do
            {
                System.err.println(line);
            } while ((line = stderr.readLine()) != null);

        }
        stderr.close();
    }

    public void testBrightness(ActionEvent actionEvent) {
        try{
            ChangeBrightness(50);
        } catch (Exception exception) {

        }
    }
}