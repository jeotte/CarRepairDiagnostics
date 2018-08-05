import com.ubiquisoft.evaluation.CarDiagnosticEngine;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class CarDiagnosticsEngineTest {

    // Note got the following code about capturing standard out from:
    // https://gist.github.com/ClassCubeGists/b5ae82751a0b6f4baa4b05b1deec97dd
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Turns on stdOut output capture
     */
    private void captureOut() {
        System.setOut( new PrintStream( outContent ) );
    }

    /**
     * Turns off stdOut capture and returns the contents
     * that have been captured
     *
     * @return
     */
    private String getOut() {
        System.setOut( new PrintStream( new FileOutputStream( FileDescriptor.out ) ) );
        return outContent.toString().replaceAll( "\r", "" );

    }

    // Beginning on unit testing

    @Test
    public void executeDiagnostics_validCar() throws Exception{
        captureOut();
        CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
        carDiagnosticEngine.executeDiagnostics(getCar("Car_Valid.xml"));

        String theOutput = getOut();
        String expectedOutput = "The 2006 Explorer Ford has passed diagnostic validation.\n";
        assertTrue(expectedOutput.equals(theOutput));
    }

    @Test
    public void executeDiagnostics_carMissingModel() throws Exception{
        captureOut();
        CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
        carDiagnosticEngine.executeDiagnostics(getCar("Car_MissingModel.xml"));

        String theOutput = getOut();
        String expectedOutput = "The model of the car is missing.\n";
        assertTrue(expectedOutput.equals(theOutput));
    }

    @Test
    public void executeDiagnostics_carMissingParts() throws Exception{
        captureOut();
        CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
        carDiagnosticEngine.executeDiagnostics(getCar("Car_MissingParts.xml"));

        String theOutput = getOut();
        String expectedOutput = "Missing Part(s) Detected: FUEL_FILTER - Count: 1\n" +
                                "Missing Part(s) Detected: OIL_FILTER - Count: 1\n";
        assertTrue(expectedOutput.equals(theOutput));
    }

    @Test
    public void executeDiagnostics_carDamagedParts() throws Exception{
        captureOut();
        CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
        carDiagnosticEngine.executeDiagnostics(getCar("Car_DamagedParts.xml"));

        String theOutput = getOut();
        String expectedOutput = "Damaged Part Detected: ELECTRICAL - Condition: NO_POWER\n" +
                                "Damaged Part Detected: TIRE - Condition: FLAT\n" +
                                "Damaged Part Detected: TIRE - Condition: FLAT\n";
        assertTrue(expectedOutput.equals(theOutput));
    }

    @Test
    public void executeDiagnostics_sampleCar() throws Exception{
        captureOut();
        CarDiagnosticEngine carDiagnosticEngine = new CarDiagnosticEngine();
        carDiagnosticEngine.executeDiagnostics(getCar("SampleCar_Copy.xml"));

        String theOutput = getOut();
        String expectedOutput = "Damaged Part Detected: ENGINE - Condition: USED\n" +
                "Damaged Part Detected: ELECTRICAL - Condition: NO_POWER\n" +
                "Damaged Part Detected: TIRE - Condition: FLAT\n" +
                "Damaged Part Detected: OIL_FILTER - Condition: CLOGGED\n";
        assertTrue(expectedOutput.equals(theOutput));
    }


    // Helper method to get the car from the xml
    private Car getCar(String fileName) throws JAXBException {
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream(fileName);

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load xml");

            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object
        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Car) unmarshaller.unmarshal(xml);
    }
}

