package com.example.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;
import com.example.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StudentController
{
    @Autowired
    StudentService mahasiswaDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping("/mahasiswa")
    public String mahasiswa(Model model, @RequestParam(value = "npm", required = false) String npm) {
    	MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
    	ProgramStudiModel program_studi = mahasiswaDAO.selectProgramStudi(mahasiswa.getId_prodi());
    	FakultasModel fakultas = mahasiswaDAO.selectFakultas(program_studi.getId_fakultas());
    	UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
    	if(mahasiswa != null) {
    		model.addAttribute("mahasiswa", mahasiswa);
    		model.addAttribute("program_studi", program_studi);
    		model.addAttribute("fakultas", fakultas);
    		model.addAttribute("universitas", universitas);
    		return "view";
    	}else {
    		model.addAttribute("npm", npm);
    		return "not-found";
    	}
    }
    
    @RequestMapping("/mahasiswa/tambah")
	public String tambah (@ModelAttribute("mahasiswa") MahasiswaModel mahasiswa, Model model)
	{
		if(mahasiswa.getNama()==null) {
			return "addMahasiswa";
		} else {
			mahasiswa.setNpm(generateNpm(mahasiswa));
			mahasiswa.setStatus("Aktif");
			model.addAttribute("npm", mahasiswa.getNpm());
			model.addAttribute("message", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " berhasil ditambahkan");
			mahasiswaDAO.addMahasiswa(mahasiswa);
			return "success-add";
		}
	}
	
	public String generateNpm(MahasiswaModel mahasiswa) {
		ProgramStudiModel prodi = mahasiswaDAO.selectProgramStudi(mahasiswa.getId_prodi());
		FakultasModel fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
		UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
		
		String duadigit  = mahasiswa.getTahun_masuk().substring(2, 4);
		String upDigit = universitas.getKode_univ()+prodi.getKode_prodi();
		String tigadigit="";
		    	if (mahasiswa.getJalur_masuk().equals("Olimpiade Undangan")) {
		    		tigadigit = "53";
		    	}
		    	else if(mahasiswa.getJalur_masuk().equals("Undangan Reguler/SNMPTN")) {
		    		tigadigit="54";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Undangan Paralel/PPKB")) {
		    		tigadigit="55";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Bersama/SBMPTN")) {
		    		tigadigit="57";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Mandiri")) {
		    		tigadigit="62";
		    	}
		    	String npmFiks = duadigit + upDigit + tigadigit;
		    	String cekNpm = mahasiswaDAO.selectMahasiswaNpm(npmFiks);
		    	if(cekNpm != null) {
		    	cekNpm = "" + (Integer.parseInt(cekNpm) + 1);
		    	if (cekNpm.length() == 1) {
    			npmFiks = npmFiks + "00" + cekNpm;
    		}else if (cekNpm.length() == 2) {
    			npmFiks = npmFiks + "0" + cekNpm;
    		}else {
    			npmFiks = npmFiks + cekNpm;
    		}
    	}else {
    		npmFiks = npmFiks + "001";
    	}
		    	return npmFiks;
	}
	
	
	
	@RequestMapping("/mahasiswa/ubah/{npm}")
    public String ubah (@PathVariable(value = "npm") String npm, Model model, 
    		@ModelAttribute("mahasiswa") MahasiswaModel newmahasiswa)
    {
    		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
    		
    		if(newmahasiswa.getNama()==null) {
    			if(mahasiswa==null) {
    				model.addAttribute("npm", npm);
    				return "not-found";
    			}
    			model.addAttribute("mahasiswa", mahasiswa);
    			model.addAttribute("title", "Update Mahasiswa");
    			return "updateMahasiswa";
    		} else {
    			if(!mahasiswa.getTahun_masuk().equals(newmahasiswa.getTahun_masuk()) || 
    					mahasiswa.getId_prodi() != newmahasiswa.getId_prodi() || 
    					!mahasiswa.getJalur_masuk().equals(newmahasiswa.getJalur_masuk())) {
    				newmahasiswa.setNpm(generateNpm(newmahasiswa));

        			mahasiswaDAO.updateMahasiswa(newmahasiswa);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "success-update";
    			}else {
    				newmahasiswa.setNpm(mahasiswa.getNpm());

        			mahasiswaDAO.updateMahasiswa(newmahasiswa);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "success-update";
    			}
    		}
    }
	
	 @RequestMapping("/kelulusan")
	    public String kelulusan(Model model,
	    						@RequestParam(value = "tahun_masuk", required = false) Optional<String> tahun_masuk,
	    						@RequestParam(value = "prodi", required = false) Optional<String> prodi)
	{
		if (tahun_masuk.isPresent() && prodi.isPresent()) {
			int jml_mahasiswa = mahasiswaDAO.selectTahunKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
			int jml_mahasiswaLulus = mahasiswaDAO.selectKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
			double peresent = ((double) jml_mahasiswaLulus / (double) jml_mahasiswa) * 100;
			String presentase = new DecimalFormat("##.##").format(peresent);
			ProgramStudiModel program_studi = mahasiswaDAO.selectProgramStudi(Integer.parseInt(prodi.get()));
			FakultasModel fakultas = mahasiswaDAO.selectFakultas(program_studi.getId_fakultas());
			UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
			model.addAttribute("jml_mahasiswa", jml_mahasiswa);
			model.addAttribute("jml_mahasiswaLulus", jml_mahasiswaLulus);
			model.addAttribute("presentase", presentase);
			model.addAttribute("tahun_masuk", tahun_masuk.get());
			model.addAttribute("prodi", program_studi.getNama_prodi());
			model.addAttribute("fakultas", fakultas.getNama_fakultas());
			model.addAttribute("universitas", universitas.getNama_univ());
			
			return "kelulusanMahasiswa";
			
		}else {
			List<ProgramStudiModel> programStudi = mahasiswaDAO.selectAllProgramStudi();
			model.addAttribute("programStudi", programStudi);
			return "lihatKelulusan";
		}
	 }
	 
	 @RequestMapping("/mahasiswa/cari")
		public String cariMahasiswa(Model model,
				@RequestParam(value = "universitas", required = false) String univ,
				@RequestParam(value = "idfakultas", required = false) String idfakultas,
				@RequestParam(value = "idprodi", required = false) String idprodi)
		{

				List<UniversitasModel> universitas = mahasiswaDAO.selectAllUniversitas();
				model.addAttribute ("universitas", universitas);
	            	if(univ== null) {
	            		return "cari-universitas";
	            	} else {
	            		int idUniv = Integer.parseInt(univ);
	            		UniversitasModel univers = mahasiswaDAO.selectUniversitas(idUniv);
	            		int idUnivv = mahasiswaDAO.selectUniversitas2(idUniv);
	            		List<FakultasModel> fakultas = mahasiswaDAO.selectAllFakultasbyUniv(idUnivv);
	            		if (idfakultas == null) {
	            			model.addAttribute("fakultas", fakultas);
	            			model.addAttribute("selectUniv",idUniv);
	                		return "cari-fakultas";
	            		}  
	            		else {
	            			int idFakul = Integer.parseInt(idfakultas);
	            			FakultasModel fakultass = mahasiswaDAO.selectFakultas(idFakul);
	            			int idFaks = mahasiswaDAO.selectFakultas2(idFakul);
	            			model.addAttribute("selectFak", idFakul);
	            			List<ProgramStudiModel> prodd = mahasiswaDAO.selectProdibyFak(idFaks);
	            			
	            			if(idprodi == null) {
	            				model.addAttribute("fakultas", fakultas);
	                			model.addAttribute("selectUniv",idUniv);
	                			model.addAttribute("prodi",prodd);
	                			
	                    		return "cari-prodi";
	            			}else {
	            				int idprod = Integer.parseInt(idprodi);
	            				ProgramStudiModel prodis = mahasiswaDAO.selectProgramStudi(idprod);
	            				List<MahasiswaModel> mahasiswaByProdi = mahasiswaDAO.selectMahasiswabyProdi(idprod);
	            				model.addAttribute("mahasiswaByProdi", mahasiswaByProdi);
	            				ProgramStudiModel prodiSelectObject = mahasiswaDAO.selectProgramStudi(idprod);
	            				model.addAttribute("prodiSelectObject", prodiSelectObject);
	            				
	            				return "tabelMahasiswa";
	            				
	            			}
	            			
	            		}
	            		
	            	}
	 
}
}
		
	


//    @RequestMapping("/student/add")
//    public String add ()
//    {
//        return "form-add";
//    }


//    @RequestMapping("/student/add/submit")
//    public String addSubmit (
//            @RequestParam(value = "npm", required = false) String npm,
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "gpa", required = false) double gpa)
//    {
//        StudentModel student = new StudentModel (npm, name, gpa);
//        studentDAO.addStudent (student);
//
//        return "success-add";
//    }


//    @RequestMapping("/mahasiswa/view")
//    public String view (Model model,
//            @RequestParam(value = "npm", required = false) String npm)
//    {
//        Mahasiswa mahasiswa = mahasiswaDAO.selectMahasiswa (npm);
//
//        if (mahasiswa != null) {
//            model.addAttribute ("mahasiswa", mahasiswa);
//            return "view";
//        } else {
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }
//
//
//    @RequestMapping("/mahasiswa/view/{npm}")
//    public String viewPath (Model model,
//            @PathVariable(value = "npm") String npm)
//    {
//    	Mahasiswa mahasiswa = mahasiswaDAO.selectMahasiswa (npm);
//
//        if (mahasiswa != null) {
//            model.addAttribute ("mahasiswa", mahasiswa);
//            return "view";
//        } else {
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }


//    @RequestMapping("/student/viewall")
//    public String view (Model model)
//    {
//        List<StudentModel> students = studentDAO.selectAllStudents ();
//        model.addAttribute ("students", students);
//        
//        log.info(students.toString());
//
//        return "viewall";
//    }


//    @RequestMapping("/student/delete/{npm}")
//    public String delete (Model model, @PathVariable(value = "npm") String npm)
//    {
//    	StudentModel student = studentDAO.selectStudent (npm);
//
//        if (student != null) {
//        	studentDAO.deleteStudent (npm);
//            return "delete";
//        } else {
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }
//    
//    @RequestMapping("/student/update/{npm}")
//    public String update (Model model, @PathVariable(value = "npm") String npm)
//    {
//    	StudentModel student = studentDAO.selectStudent (npm);
//
//        if (student != null) {
//        	model.addAttribute("student",student);
//            return "form-update";
//        } else {
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }
//    
////    @RequestMapping(value="/student/update/submit", method = RequestMethod.POST)
////    public String updateSubmit (
////            @RequestParam(value = "npm", required = false) String npm,
////            @RequestParam(value = "name", required = false) String name,
////            @RequestParam(value = "gpa", required = false) double gpa)
////    {
////    	StudentModel student = new StudentModel (npm, name, gpa);
////		studentDAO.updateStudent (student);
////        
////        return "success-update";
////    }
//    
//    @RequestMapping(value="/student/update/submit", method = RequestMethod.POST)
//    public String updateSubmit (StudentModel student)
//    {
//		studentDAO.updateStudent (student);
//        
//        return "success-update";
//    }

