package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;


@Mapper
public interface StudentMapper
{	
	@Select("SELECT * FROM mahasiswa WHERE npm = #{npm}")
	MahasiswaModel selectMahasiswa (@Param("npm") String npm);
	
	@Select("SELECT m.* FROM mahasiswa m")
	List<MahasiswaModel> selectAllMahasiswa();
	
	@Select("SELECT * FROM fakultas WHERE id = #{id}")
	FakultasModel selectFakultas (@Param("id") int id);
	
	@Select("SELECT f.* FROM fakultas m")
	List<FakultasModel> selectAllFakultas();
	
	@Select("SELECT * FROM program_studi WHERE id = #{id}")
	ProgramStudiModel selectProgramStudi (@Param("id") int id);
	
	@Select("SELECT m.* FROM program_studi m")
	List<ProgramStudiModel> selectAllProgramStudi();
	
	@Select("SELECT * FROM universitas WHERE id = #{id}")
	UniversitasModel selectUniversitas (@Param("id") int id);
	
	@Select("SELECT * FROM universitas m")
	List<UniversitasModel> selectAllUniversitas();
	
	@Select("SELECT substring(npm, 10,3) FROM mahasiswa WHERE npm LIKE '${npm}%' ORDER BY substring(npm, 10, 3) DESC LIMIT 1")
    String selectMahasiswaNpm (@Param("npm")String npm);  

    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, "
    		+ "tahun_masuk, jalur_masuk,status,id_prodi)"
    		+ "VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin},"
   		    + " #{agama}, #{golongan_darah}, #{tahun_masuk}, #{jalur_masuk}, #{status}, #{id_prodi})")
    void addMahasiswa (MahasiswaModel mahasiswa);

    @Update("UPDATE mahasiswa SET npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin},"
       		+ "agama=#{agama},golongan_darah=#{golongan_darah}, tahun_masuk=#{tahun_masuk}, jalur_masuk=#{jalur_masuk},"
       		+ "id_prodi=#{id_prodi} WHERE id = #{id}")
    void updateMahasiswa (MahasiswaModel mahasiswa);
    
    @Select("select count(*) from mahasiswa where tahun_masuk =#{tahun_masuk} and id_prodi= #{prodi} and status = 'Lulus'")
    Integer selectKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
    
    @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{prodi}")
    Integer selectTahunKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);

    @Select("SELECT * from fakultas where id_univ=#{id_univ}")
    List<FakultasModel> selectAllFakultasbyUniv(@Param ("id_univ")int id_univ);
    
    @Select("SELECT * from program_studi where id_fakultas=#{id_fakultas}")
    List<ProgramStudiModel> selectProdibyFak(@Param ("id_fakultas")int id_fakultas);
    
    @Select("SELECT * from mahasiswa where id_prodi=#{id_prodi}")
    List<MahasiswaModel> selectMahasiswabyProdi(@Param ("id_prodi")int id_prodi);
    
    @Select("SELECT * from universitas where id =#{id}")
    int selectUniversitas2 (@Param("id") int id);
    
    @Select("SELECT * from fakultas where id =#{id}")
    int selectFakultas2 (@Param("id") int id);
    
//    @Select("select npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, "
//    		+ "status, tahun_masuk, jalur_masuk, id_prodi from mahasiswa where npm = #{npm}")
//    MahasiswaModel selectMahasiswa (@Param("npm") String npm);
//
//    @Select("select npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, "
//    		+ "status, tahun_masuk, jalur_masuk, id_prodi from mahasiswa")
//    List<MahasiswaModel> selectAllMahasiswa ();

//    @Insert("INSERT INTO mahasiswa (id, npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, "
//    		+ "agama, golongan_darah, status, tahun_masuk, jalur_masuk, "
//    		+ "id_prodi) VALUES (#{id}, #{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
//    		+ "#{jenis_kelamin}, #{agama}, #{golongan_darah}, #{status}, #{tahun_masuk}, #{jalur_masuk})")
//    void addMahasiswa (Mahasiswa mahasiswa);
//    
//    @Delete("DELETE FROM mahasiswa where npm = #{npm}")
//    void deleteMahasiswa (@Param("npm") String npm); 
//    
//    @Update("UPDATE mahasiswa SET nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
//    		+ "jenis_kelamin = #{jenis_kelamin}, agama = #{agama}, golongan_darah = #{golongan_darah}, "
//    		+ "status = #{status}, tahun_masuk = #{tahun_masuk}, jalur_masuk = #{jalur_masuk}, id_prodi = #{id_prodi}"
//    		+ " where npm = #{npm}")
//    void updateMahasiswa (Mahasiswa mahasiswa);
}  
