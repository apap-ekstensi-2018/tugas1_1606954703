package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramStudiModel {
	private int id;
	private String kode_prodi;
	private String nama_prodi;
	private int id_fakultas;
	
}
