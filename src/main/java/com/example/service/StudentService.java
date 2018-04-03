package com.example.service;

import java.util.List;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;

public interface StudentService
{	
	String selectMahasiswaNpm (String npm);
	
    MahasiswaModel selectMahasiswa (String npm);
 
    List<MahasiswaModel> selectAllMahasiswa ();
    
    FakultasModel selectFakultas (int id);
    
    List<FakultasModel> selectAllFakultas ();
    
    ProgramStudiModel selectProgramStudi (int id);
    
    List<ProgramStudiModel> selectAllProgramStudi ();
    
    UniversitasModel selectUniversitas (int id);
    
    List<UniversitasModel> selectAllUniversitas ();

	void addMahasiswa(MahasiswaModel mahasiswa);
	
	void updateMahasiswa(MahasiswaModel mahasiswa);
	
	Integer selectKelulusan (String tahun_masuk, int program_studi);
	   
	Integer selectTahunKelulusan (String tahun_masuk, int program_studi);
	
    int selectUniversitas2 (int id);
    int selectFakultas2 (int id);
    
    List <FakultasModel> selectAllFakultasbyUniv(int id_univ);
    List <ProgramStudiModel> selectProdibyFak(int id_fakultas);
    List <MahasiswaModel> selectMahasiswabyProdi(int id_prodi);


//    void addMahasiswa (Mahasiswa mahasiswa);
//
//
//    void deleteMahasiswa (String npm);
//
//
//	void updateMahasiswa (Mahasiswa mahasiswa);
}
