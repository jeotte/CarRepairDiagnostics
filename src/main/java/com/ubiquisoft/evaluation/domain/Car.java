package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		Map<PartType, Integer> missingParts = new HashMap<>();

		// If the list of parts doesn't contain 'Engine', add it to the map of missing parts
		if (!parts.stream().anyMatch(p->p.getType().equals(PartType.ENGINE))) {
			missingParts.put(PartType.ENGINE, 1);
		}

		// If the list of parts doesn't contain 'Electrical', add it to the map of missing parts
		if (!parts.stream().anyMatch(p->p.getType().equals(PartType.ELECTRICAL))) {
			missingParts.put(PartType.ELECTRICAL, 1);
		}

		// If the list of parts doesn't contain 'Fuel Filter', add it to the map of missing parts
		if (!parts.stream().anyMatch(p->p.getType().equals(PartType.FUEL_FILTER))) {
			missingParts.put(PartType.FUEL_FILTER, 1);
		}

		// If the list of parts doesn't contain 'Oil Filter', add it to the map of missing parts
		if (!parts.stream().anyMatch(p->p.getType().equals(PartType.OIL_FILTER))) {
			missingParts.put(PartType.OIL_FILTER, 1);
		}

		// If the list of parts doesn't contain 4 'Tires', add it to the map of missing parts
		long countOfTires = parts.stream().filter(p->p.getType().equals(PartType.TIRE)).count();
		if (countOfTires != 4) {
			missingParts.put(PartType.TIRE, (int)(4-countOfTires));
		}

		return missingParts;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
