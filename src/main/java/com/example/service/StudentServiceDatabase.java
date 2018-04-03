package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public MahasiswaModel selectMahasiswa (String npm)
    {
        log.info ("select mahasiswa with npm {}", npm);
        return studentMapper.selectMahasiswa (npm);
    }

    @Override
    public List<MahasiswaModel> selectAllMahasiswa ()
    {
        log.info ("select all mahasiswa");
        return studentMapper.selectAllMahasiswa();
    }
    
    @Override
    public FakultasModel selectFakultas (int id)
    {
        log.info ("select fakultas with npm {}", id);
        return studentMapper.selectFakultas (id);
    }

    @Override
    public List<FakultasModel> selectAllFakultas ()
    {
        log.info ("select all fakultas");
        return studentMapper.selectAllFakultas();
    }
    
    @Override
    public ProgramStudiModel selectProgramStudi (int id)
    {
        log.info ("select program_studi with npm {}", id);
        return studentMapper.selectProgramStudi (id);
    }

    @Override
    public List<ProgramStudiModel> selectAllProgramStudi ()
    {
        log.info ("select all program_studi");
        return studentMapper.selectAllProgramStudi();
    }
    
    @Override
    public UniversitasModel selectUniversitas (int id)
    {
        log.info ("select universitas with npm {}", id);
        return studentMapper.selectUniversitas (id);
    }

    @Override
    public List<UniversitasModel> selectAllUniversitas ()
    {
        log.info ("select all universitas");
        return studentMapper.selectAllUniversitas();
    }
    
    @Override
	public String selectMahasiswaNpm(String npm) {
		return studentMapper.selectMahasiswaNpm (npm);
		
	}
	
	@Override
	public void addMahasiswa(MahasiswaModel mahasiswa) {
		// TODO Auto-generated method stub
		studentMapper.addMahasiswa(mahasiswa);		
	}
	
	@Override
	public void updateMahasiswa(MahasiswaModel mahasiswa) {
		// TODO Auto-generated method stub
		studentMapper.updateMahasiswa(mahasiswa);
	}
	
	@Override
	public Integer selectKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return studentMapper.selectKelulusan(tahun_masuk, prodi);
	}

	@Override
	public Integer selectTahunKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return studentMapper.selectTahunKelulusan(tahun_masuk, prodi);
	}
	
	@Override
	 public List <FakultasModel> selectAllFakultasbyUniv(int id_univ){
	   return studentMapper.selectAllFakultasbyUniv(id_univ);
	  }
	 
	 
	 @Override
	 public List <ProgramStudiModel> selectProdibyFak(int id_fakultas){
	   return studentMapper.selectProdibyFak(id_fakultas);
	  }
	 
	 @Override
	 public List <MahasiswaModel> selectMahasiswabyProdi(int id_prodi){
	   return studentMapper.selectMahasiswabyProdi(id_prodi);
	  }
	 
	@Override
	public int selectUniversitas2(int id) {
		// TODO Auto-generated method stub
		return studentMapper.selectUniversitas2(id);
	}
	
	@Override
	public int selectFakultas2(int id) {
		// TODO Auto-generated method stub
		return studentMapper.selectFakultas2(id);
	}

//
//	@Override
//	public void addMahasiswa(Mahasiswa mahasiswa) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void deleteMahasiswa(String npm) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void updateMahasiswa(Mahasiswa mahasiswa) {
//		// TODO Auto-generated method stub
//		
//	}


//    @Override
//    public void addMahasiswa (Mahasiswa mahasiswa)
//    {
//        studentMapper.addStudent (mahasiswa);
//    }
//
//    @Override
//    public void deleteStudent (String npm)
//    {
//    	log.info ("student"+npm+"deleted");
//    	studentMapper.deleteStudent(npm);
//    }
//
//	@Override
//	public void updateStudent(StudentModel student) 
//	{
//		log.info("Student {} name update to", student.getNpm(), student.getName());
//    	studentMapper.updateStudent(student);
//	} 
	
}