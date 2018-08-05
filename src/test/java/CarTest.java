import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.PartType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.ubiquisoft.evaluation.domain.Part;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarTest {

    @Test
    public void getMissingPartsMap_none() {
        Car testCar = new Car();
        testCar.setParts(getAllParts());

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertTrue(missingParts.isEmpty());
    }

    @Test
    public void getMissingPartsMap_engine() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the Engine from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.ENGINE));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.ENGINE));
        assertEquals(new Integer(1), missingParts.get(PartType.ENGINE));
    }

    @Test
    public void getMissingPartsMap_electrical() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the Electrical from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.ELECTRICAL));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.ELECTRICAL));
        assertEquals(new Integer(1), missingParts.get(PartType.ELECTRICAL));
    }

    @Test
    public void getMissingPartsMap_fuelFilter() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the Fuel Filter from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.FUEL_FILTER));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.FUEL_FILTER));
        assertEquals(new Integer(1), missingParts.get(PartType.FUEL_FILTER));
    }

    @Test
    public void getMissingPartsMap_oilFilter() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the Fuel Filter from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.OIL_FILTER));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.OIL_FILTER));
        assertEquals(new Integer(1), missingParts.get(PartType.OIL_FILTER));
    }

    @Test
    public void getMissingPartsMap_oneTire() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the tires from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.TIRE) && p.getInventoryId() != null &&
                p.getInventoryId().equals("1"));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.TIRE));
        assertEquals(new Integer(1), missingParts.get(PartType.TIRE));
    }

    @Test
    public void getMissingPartsMap_twoTires() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the tires from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.TIRE) && p.getInventoryId() != null &&
                (p.getInventoryId().equals("1") || p.getInventoryId().equals("2")));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.TIRE));
        assertEquals(new Integer(2), missingParts.get(PartType.TIRE));
    }

    @Test
    public void getMissingPartsMap_allTires() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the tires from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.TIRE));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(1, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.TIRE));
        assertEquals(new Integer(4), missingParts.get(PartType.TIRE));
    }

    @Test
    public void getMissingPartsMap_multiple() {
        Car testCar = new Car();
        List<Part> parts = getAllParts();

        // Remove the engine from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.ENGINE));

        // Remove the fuel filter from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.FUEL_FILTER));

        // Remove three tires from the list of parts
        parts.removeIf(p-> p.getType().equals(PartType.TIRE) && p.getInventoryId() != null &&
                (p.getInventoryId().equals("1") || p.getInventoryId().equals("2") || p.getInventoryId().equals("3")));
        testCar.setParts(parts);

        Map<PartType, Integer> missingParts = testCar.getMissingPartsMap();
        assertEquals(3, missingParts.size());

        assertTrue(missingParts.containsKey(PartType.ENGINE));
        assertEquals(new Integer(1), missingParts.get(PartType.ENGINE));

        assertTrue(missingParts.containsKey(PartType.FUEL_FILTER));
        assertEquals(new Integer(1), missingParts.get(PartType.FUEL_FILTER));

        assertTrue(missingParts.containsKey(PartType.TIRE));
        assertEquals(new Integer(3), missingParts.get(PartType.TIRE));
    }

    /**
     * Get a list of all the parts a car needs.
     * Each car requires one of each of the following types:
     *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER and four of the type: TIRE
     */
    private List<Part> getAllParts() {
        List<Part> partsList = new ArrayList<>();

        Part engine = new Part();
        engine.setType(PartType.ENGINE);
        partsList.add(engine);

        Part electrical = new Part();
        electrical.setType(PartType.ELECTRICAL);
        partsList.add(electrical);

        Part fuelFilter = new Part();
        fuelFilter.setType(PartType.FUEL_FILTER);
        partsList.add(fuelFilter);

        Part oilFilter = new Part();
        oilFilter.setType(PartType.OIL_FILTER);
        partsList.add(oilFilter);

        Part tire1 = new Part();
        tire1.setType(PartType.TIRE);
        tire1.setInventoryId("1");
        partsList.add(tire1);

        Part tire2 = new Part();
        tire2.setType(PartType.TIRE);
        tire2.setInventoryId("2");
        partsList.add(tire2);

        Part tire3 = new Part();
        tire3.setType(PartType.TIRE);
        tire3.setInventoryId("3");
        partsList.add(tire3);

        Part tire4 = new Part();
        tire4.setType(PartType.TIRE);
        tire4.setInventoryId("4");
        partsList.add(tire4);

        return partsList;
    }
}
