package com.quantity_measurement_app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class QuantityInputDTO {

	@NotNull(message = "First quantity (thisQuantityDTO) is required")
	@Valid
	private QuantityDTO thisQuantityDTO;

	@NotNull(message = "Second quantity (thatQuantityDTO) is required")
	@Valid
	private QuantityDTO thatQuantityDTO;

	public QuantityInputDTO() {
	}

	public QuantityInputDTO(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		this.thisQuantityDTO = thisQuantityDTO;
		this.thatQuantityDTO = thatQuantityDTO;
	}

	public QuantityDTO getThisQuantityDTO() {
		return thisQuantityDTO;
	}

	public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
		this.thisQuantityDTO = thisQuantityDTO;
	}

	public QuantityDTO getThatQuantityDTO() {
		return thatQuantityDTO;
	}

	public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
		this.thatQuantityDTO = thatQuantityDTO;
	}

	@Override
	public String toString() {
		return "QuantityInputDTO{" + "thisQuantityDTO=" + thisQuantityDTO + ", thatQuantityDTO=" + thatQuantityDTO
				+ '}';
	}
}